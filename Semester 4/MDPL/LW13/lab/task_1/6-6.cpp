#include <windows.h>
#include <windowsx.h>
#include <commdlg.h>
#include "6-6.h"

char szClassName[] = "MainWindow";
char szTitle[] = "Program 6-6 - Data Viewer";
char szFile[MAX_PATH] = "";
short nBuf[500];
BOOL bDataOK = FALSE;
HINSTANCE hI;
HPEN hPen;

int WINAPI WinMain(HINSTANCE hInst, HINSTANCE hPrevInst, LPSTR lpCmdLine, int nCmdShow)
{
    MSG msg;
    WNDCLASS wc;
    HWND hwnd;
    
    hI = hInst;

    ZeroMemory(&wc, sizeof(wc));
    wc.style = CS_HREDRAW | CS_VREDRAW;
    wc.lpfnWndProc = WndProc;
    wc.hInstance = hInst;
    wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
    wc.hCursor = LoadCursor(NULL, IDC_ARROW);
    wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
    wc.lpszMenuName = "Main";
    wc.lpszClassName = szClassName;

    if (!RegisterClass(&wc))
    {
        MessageBox(NULL, "Window class registration error!", "Error", MB_OK | MB_ICONERROR);
        return 0;
    }

    hwnd = CreateWindow(
        szClassName, 
        szTitle, 
        WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, CW_USEDEFAULT, 
        800, 600, 
        NULL, NULL, hInst, NULL
    );

    if (!hwnd)
    {
        MessageBox(NULL, "Window creation error!", "Error", MB_OK | MB_ICONERROR);
        return 0;
    }

    ShowWindow(hwnd, nCmdShow);
    UpdateWindow(hwnd);

    hPen = CreatePen(PS_SOLID, 1, RGB(0, 0, 255));

    while (GetMessage(&msg, NULL, 0, 0))
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }

    return (int)msg.wParam;
}

LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    switch (msg)
    {
        HANDLE_MSG(hwnd, WM_COMMAND, OnCommand);
        HANDLE_MSG(hwnd, WM_PAINT, OnPaint);
        HANDLE_MSG(hwnd, WM_DESTROY, OnDestroy);
        
        default:
            return DefWindowProc(hwnd, msg, wParam, lParam);
    }
}

void OnCommand(HWND hwnd, int id, HWND hwndCtl, UINT codeNotify)
{
    OPENFILENAME ofn;
    char szFilter[] = "Data files (*.DAT)\0*.dat\0All files (*.*)\0*.*\0";
    HANDLE hFile;
    DWORD dwBytesRead;

    switch (id)
    {
        case MI_OPEN:
            ZeroMemory(&ofn, sizeof(OPENFILENAME));
            ofn.lStructSize = sizeof(OPENFILENAME);
            ofn.hwndOwner = hwnd;
            ofn.lpstrFilter = szFilter;
            ofn.lpstrFile = szFile;
            ofn.nMaxFile = sizeof(szFile);
            ofn.lpstrTitle = "Select data file";
            ofn.Flags = OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST | OFN_HIDEREADONLY;

            if (GetOpenFileName(&ofn))
            {
                hFile = CreateFile(
                    szFile, 
                    GENERIC_READ, 
                    FILE_SHARE_READ, 
                    NULL, 
                    OPEN_EXISTING, 
                    FILE_ATTRIBUTE_NORMAL, 
                    NULL
                );

                if (hFile != INVALID_HANDLE_VALUE)
                {
                    if (ReadFile(hFile, nBuf, sizeof(nBuf), &dwBytesRead, NULL))
                    {
                        if (dwBytesRead == sizeof(nBuf))
                        {
                            bDataOK = TRUE;
                            char szNewTitle[MAX_PATH + 50];
                            wsprintf(szNewTitle, "%s - %s", szTitle, szFile);
                            SetWindowText(hwnd, szNewTitle);
                            
                            InvalidateRect(hwnd, NULL, TRUE);
                        }
                        else
                        {
                            MessageBox(hwnd, "File has incorrect size!\nExpected 1000 bytes (500 short values).", 
                                     "Error", MB_OK | MB_ICONWARNING);
                            bDataOK = FALSE;
                        }
                    }
                    else
                    {
                        MessageBox(hwnd, "File read error!", "Error", MB_OK | MB_ICONERROR);
                        bDataOK = FALSE;
                    }
                    CloseHandle(hFile);
                }
                else
                {
                    MessageBox(hwnd, "Could not open file!", "Error", MB_OK | MB_ICONERROR);
                    bDataOK = FALSE;
                }
            }
            break;

        case MI_EXIT:
            DestroyWindow(hwnd);
            break;
    }
}

void OnPaint(HWND hwnd)
{
    PAINTSTRUCT ps;
    HDC hdc;
    RECT r;
    HPEN hOldPen;
    int i;
    int x, y;
    int maxVal = 0, minVal = 0;
    float scale;

    hdc = BeginPaint(hwnd, &ps);
    
    GetClientRect(hwnd, &r);
    
    if (bDataOK)
    {
        hOldPen = (HPEN)SelectObject(hdc, hPen);
        
        for (i = 0; i < 500; i++)
        {
            if (nBuf[i] > maxVal) maxVal = nBuf[i];
            if (nBuf[i] < minVal) minVal = nBuf[i];
        }
        
        if (maxVal > minVal)
        {
            scale = (float)(r.bottom - 40) / (maxVal - minVal);
        }
        else
        {
            scale = 1.0f;
        }
        
        for (i = 0; i < 500; i++)
        {
            x = (r.right * i) / 500;
            y = r.bottom - 20 - (int)((nBuf[i] - minVal) * scale);
            
            if (y < 10) y = 10;
            if (y > r.bottom - 10) y = r.bottom - 10;
            
            SetPixel(hdc, x, y, RGB(0, 0, 255));
        }
        
        SelectObject(hdc, hOldPen);
        
        char szInfo[100];
        wsprintf(szInfo, "Points: 500, Min: %d, Max: %d", minVal, maxVal);
        TextOut(hdc, 10, 10, szInfo, lstrlen(szInfo));
    }
    else
    {
        char szMsg[] = "No data loaded. Select 'File -> Open...' to load data.";
        RECT textRect = r;
        DrawText(hdc, szMsg, -1, &textRect, DT_CENTER | DT_VCENTER | DT_SINGLELINE);
    }
    
    EndPaint(hwnd, &ps);
}

void OnDestroy(HWND hwnd)
{
    if (hPen)
    {
        DeleteObject(hPen);
    }
    
    PostQuitMessage(0);
}
