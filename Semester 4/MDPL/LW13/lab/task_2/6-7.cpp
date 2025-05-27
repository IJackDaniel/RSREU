#include <windows.h>
#include <windowsx.h>
#include <math.h>
#include "6-7.h"

char szClassName[] = "MainWindow";
char szTitle[] = "Program 6-7 - Data Viewer with Controls";
char szFile[128];
short nBuf[500];
BOOL bDataOK = FALSE;
HINSTANCE hI;
short nBufScaled[500];
int nScales[8] = {200, 175, 150, 125, 100, 75, 50, 25};
int nScaleIndex = 4;
float nCurrentScales = 1;
HWND hSettingsBox = NULL;
GraphModes Mode = DOTS;
HPEN hPen;

int WINAPI WinMain(HINSTANCE hInst, HINSTANCE, LPSTR, int)
{
    MSG msg;
    WNDCLASS wc;
    hI = hInst;
    
    ZeroMemory(&wc, sizeof(wc));
    wc.style = CS_VREDRAW;
    wc.lpfnWndProc = WndProc;
    wc.hInstance = hInst;
    wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
    wc.hCursor = LoadCursor(NULL, IDC_ARROW);
    wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
    wc.lpszMenuName = "Main";
    wc.lpszClassName = szClassName;
    
    RegisterClass(&wc);
    
    HWND hwnd = CreateWindow(szClassName, szTitle, WS_OVERLAPPEDWINDOW,
        0, 0, 600, 400, HWND_DESKTOP, NULL, hInst, NULL);
    
    ShowWindow(hwnd, SW_SHOWNORMAL);
    hPen = CreatePen(PS_SOLID, 1, RGB(0, 0, 255));
    
    while (GetMessage(&msg, NULL, 0, 0))
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
    return 0;
}

LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    switch (msg)
    {
        case WM_COMMAND:
            OnCommand(hwnd, LOWORD(wParam), (HWND)lParam, HIWORD(wParam));
            return 0;
        case WM_PAINT:
            OnPaint(hwnd);
            return 0;
        case WM_DESTROY:
            OnDestroy(hwnd);
            return 0;
        default:
            return DefWindowProc(hwnd, msg, wParam, lParam);
    }
}

void OnCommand(HWND hwnd, int id, HWND, UINT)
{
    OPENFILENAME ofn;
    char szFilter[] = "Data files (*.DAT)\0*.dat\0All files (*.*)\0*.*\0";
    DWORD nCnt;
    
    switch (id)
    {
        case MI_OPEN:
            ZeroMemory(&ofn, sizeof(OPENFILENAME));
            ofn.lStructSize = sizeof(OPENFILENAME);
            ofn.hwndOwner = hwnd;
            ofn.lpstrFilter = szFilter;
            ofn.lpstrFile = szFile;
            ofn.nMaxFile = sizeof(szFile);
            ofn.Flags = OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST;
            
            if (GetOpenFileName(&ofn))
            {
                HANDLE hFile = CreateFile(szFile, GENERIC_READ, 0, 0, OPEN_EXISTING, 0, NULL);
                if (hFile == INVALID_HANDLE_VALUE) break;
                ReadFile(hFile, nBuf, 2 * 500, &nCnt, NULL);
                CloseHandle(hFile);
                bDataOK = TRUE;
                
                for (int i = 0; i < 500; i++)
                    nBufScaled[i] = (short)(nBuf[i] * nCurrentScales);
                
                InvalidateRect(hwnd, NULL, TRUE);
                break;
            }
            else break;
            
        case MI_EXIT:
            DestroyWindow(hwnd);
            break;
            
            case MI_SETTINGS:
            if (!hSettingsBox) {
                hSettingsBox = CreateDialog(hI, MAKEINTRESOURCE(IDD_SETTINGS), 
                                          hwnd, DlgProc);
                if (!hSettingsBox) {
                    MessageBox(hwnd, "Dialog creation failed!", "Error", MB_OK);
                } else {
                    ShowWindow(hSettingsBox, SW_SHOW);
                }
            }
            break;
    }
}

void OnPaint(HWND hwnd)
{
    RECT r;
    PAINTSTRUCT ps;
    HDC hdc = BeginPaint(hwnd, &ps);
    HPEN hOldPen = (HPEN)SelectObject(hdc, hPen);
    
    GetClientRect(hwnd, &r);
    
    if (bDataOK)
    {
        switch (Mode)
        {
            case DOTS:
                for (int i = 0; i < 500; i++)
                    SetPixel(hdc, i, r.bottom - 5 - nBufScaled[i], RGB(0, 0, 255));
                break;
                
            case CURVE:
                MoveToEx(hdc, 0, r.bottom - 5 - nBufScaled[0], NULL);
                for (int i = 1; i < 500; i++)
                    LineTo(hdc, i, r.bottom - 5 - nBufScaled[i]);
                break;
        }
    }
    
    SelectObject(hdc, hOldPen);
    EndPaint(hwnd, &ps);
}

void OnDestroy(HWND)
{
    DeleteObject(hPen);
    PostQuitMessage(0);
}

INT_PTR CALLBACK DlgProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    switch (msg)
    {
        case WM_INITDIALOG:
            return DlgOnInitDialog(hwnd, (HWND)wParam, lParam);
        case WM_COMMAND:
            DlgOnCommand(hwnd, LOWORD(wParam), (HWND)lParam, HIWORD(wParam));
            return TRUE;
        default:
            return FALSE;
    }
}

BOOL DlgOnInitDialog(HWND hwnd, HWND, LPARAM)
{
    char S[10];
    SendDlgItemMessage(hwnd, Mode, BM_SETCHECK, 1, 0L);
    
    for (int i = 0; i < 8; i++)
    {
        wsprintf(S, "%d%%", nScales[i]);
        SendDlgItemMessage(hwnd, ID_SCALE, LB_ADDSTRING, 0, (LPARAM)S);
    }
    
    SendDlgItemMessage(hwnd, ID_SCALE, LB_SETCURSEL, nScaleIndex, 0L);
    return TRUE;
}

void DlgOnCommand(HWND hwnd, int id, HWND, UINT codeNotify)
{
    switch (id)
    {
        case IDOK:
        case IDCANCEL:
            DestroyWindow(hwnd);
            hSettingsBox = NULL;
            break;
            
        case ID_DOTS:
            Mode = DOTS;
            InvalidateRect(GetParent(hwnd), NULL, TRUE);
            break;
            
        case ID_CURVE:
            Mode = CURVE;
            InvalidateRect(GetParent(hwnd), NULL, TRUE);
            break;
            
        case ID_SCALE:
            if (codeNotify == LBN_SELCHANGE)
            {
                nScaleIndex = (int)SendDlgItemMessage(hwnd, ID_SCALE, LB_GETCURSEL, 0, 0L);
                nCurrentScales = (float)nScales[nScaleIndex] / 100;
                
                for (int i = 0; i < 500; i++)
                    nBufScaled[i] = (short)(nBuf[i] * nCurrentScales);
                
                InvalidateRect(GetParent(hwnd), NULL, TRUE);
            }
            break;
    }
}
