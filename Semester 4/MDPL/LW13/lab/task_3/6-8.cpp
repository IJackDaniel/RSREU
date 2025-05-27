#include <windows.h>
#include <windowsx.h>
#include <math.h>
#include "6-8.h"

HPEN hPen;

int WINAPI WinMain(HINSTANCE hInst, HINSTANCE, LPSTR, int)
{
    DialogBox(hInst, "Dlg", NULL, DlgProc);
    return 0;
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
        case WM_PAINT:
            DlgOnPaint(hwnd);
            return TRUE;
        default:
            return FALSE;
    }
}

BOOL DlgOnInitDialog(HWND, HWND, LPARAM)
{
    hPen = CreatePen(PS_SOLID, 1, RGB(255, 100, 100));
    return TRUE;
}

void DlgOnCommand(HWND hwnd, int id, HWND, UINT)
{
    if (id == IDCANCEL)
        EndDialog(hwnd, 0);
}

void DlgOnPaint(HWND hwnd)
{
    PAINTSTRUCT ps;
    HDC hdc = BeginPaint(hwnd, &ps);
    
    MoveToEx(hdc, 0, 70, NULL);
    LineTo(hdc, 180, 70);
    MoveToEx(hdc, 100, 0, NULL);
    LineTo(hdc, 100, 150);
    
    for (float t = 0; t < 6.28f; t += 0.01f)
    {
        int x = (int)((2 * cos(t) - 3 * cos(2 * t)) * 15);
        int y = -((int)((2 * sin(t) - 3 * sin(2 * t)) * 15));
        Rectangle(hdc, x + 100, 70 - y, x + 102, 72 - y);
    }
    
    HPEN hOldPen = (HPEN)SelectObject(hdc, hPen);
    for (float t = 0; t < 6.28f; t += 0.01f)
    {
        int x = (int)((2 * cos(t) - 1.5 * cos(2 * t)) * 15);
        int y = -((int)((2 * sin(t) - 1.5 * sin(2 * t)) * 15));
        Rectangle(hdc, x + 100, 70 - y, x + 102, 72 - y);
    }
    SelectObject(hdc, hOldPen);
    
    EndPaint(hwnd, &ps);
}
