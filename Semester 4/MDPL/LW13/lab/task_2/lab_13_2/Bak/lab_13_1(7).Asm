.386
.model flat, stdcall
option casemap :none

include windows.inc
include user32.inc
include kernel32.inc
include gdi32.inc

includelib user32.lib
includelib kernel32.lib
includelib gdi32.lib

; ���������
ID_DOTS equ 1001
ID_CURVE equ 1002
MI_OPEN equ 1003
MI_EXIT equ 1004

; ���������
PAINTSTRUCT STRUCT
    hdc             DWORD   ?
    fErase          DWORD   ?
    rcPaint         RECT    <>
    fRestore        DWORD   ?
    fIncUpdate      DWORD   ?
    rgbReserved     BYTE    32 dup(?)
PAINTSTRUCT ENDS

; ��������� �������
WinProc proto :DWORD,:DWORD,:DWORD,:DWORD
OnPaint proto :DWORD
OnCommand proto :DWORD,:DWORD
OpenFileDlg proto :DWORD

; ����������
hInstance HINSTANCE ?
hwnd HWND ?
hPen HPEN ?
nBuf WORD 500 dup(?)
nBufScaled WORD 500 dup(?)
nCnt DWORD ?
bDataOK DWORD ?
Mode DWORD ?
nCurrentScale REAL4 1.0

szFile BYTE 256 dup (?)
ofn OPENFILENAME <>
hFile DWORD ?

.data
ClassName db "MyWindowClass", 0
AppName db "Lab 13-1", 0
MenuName db "MainMenu", 0

.code
start:
    invoke GetModuleHandle, NULL
    mov hInstance, eax

    ; ���������� ��������� ������ ����
    LOCAL wc:WNDCLASSEX
    mov wc.cbSize, SIZEOF WNDCLASSEX
    mov wc.style, CS_HREDRAW or CS_VREDRAW
    mov wc.lpfnWndProc, OFFSET WinProc
    mov wc.cbClsExtra, 0
    mov wc.cbWndExtra, 0
    push hInstance
    pop wc.hInstance
    mov wc.hIcon, NULL
    mov wc.hCursor, NULL
    push COLOR_WINDOW+1
    invoke GetStockObject
    mov wc.hbrBackground, eax
    mov wc.lpszMenuName, OFFSET MenuName
    mov wc.lpszClassName, OFFSET ClassName
    mov wc.hIconSm, NULL

    invoke RegisterClassEx, addr wc

    ; �������� ����
    invoke CreateWindowEx, WS_EX_CLIENTEDGE, OFFSET ClassName, OFFSET AppName,
           WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT,
           600, 400, NULL, NULL, hInstance, NULL
    mov hwnd, eax

    ; ����������� ����
    invoke ShowWindow, hwnd, SW_SHOWNORMAL
    invoke UpdateWindow, hwnd

    ; ���� ��������� ���������
    LOCAL msg:MSG
    .while TRUE
        invoke GetMessage, addr msg, NULL, 0, 0
        .break .if eax == 0
        invoke TranslateMessage, addr msg
        invoke DispatchMessage, addr msg
    .endw

    invoke ExitProcess, msg.wParam
end start

; ��������� ��������� ��������� ����
WinProc proc hwnd:DWORD, uMsg:DWORD, wParam:DWORD, lParam:DWORD
    .if uMsg == WM_CREATE
        ; �������� ����
        invoke CreatePen, PS_SOLID, 1, 00000000h ; ������ ����
        mov hPen, eax
        mov Mode, ID_DOTS
    .elseif uMsg == WM_COMMAND
        invoke OnCommand, hwnd, wParam
    .elseif uMsg == WM_PAINT
        invoke OnPaint, hwnd
    .elseif uMsg == WM_DESTROY
        invoke PostQuitMessage, 0
    .else
        invoke DefWindowProc, hwnd, uMsg, wParam, lParam
        ret
    .endif

    xor eax, eax
    ret
WinProc endp

; ��������� ��������� WM_PAINT
OnPaint proc hwnd:DWORD
    LOCAL ps:PAINTSTRUCT
    LOCAL hdc:DWORD
    LOCAL hOldPen:DWORD
    LOCAL rect:RECT
    LOCAL i:DWORD
    LOCAL yPos:DWORD
    LOCAL xPos:DWORD
    
    invoke BeginPaint, hwnd, addr ps
    mov hdc, eax
    invoke SelectObject, hdc, hPen
    mov hOldPen, eax
    
    invoke GetClientRect, hwnd, addr rect
    
    .if bDataOK == TRUE
        mov i, 0
        .if Mode == ID_DOTS
            ; ����� �����
            .while i < 500
                mov eax, rect.bottom
                sub eax, 10
                mov ecx, i
                movzx ebx, word ptr nBufScaled[ecx*2]
                sub eax, ebx
                mov yPos, eax
                mov eax, i
                add eax, 10  ; ������ �����
                mov xPos, eax
                
                ; ������ ����� ���������
                invoke SetPixel, hdc, xPos, yPos, 000000FFh  ; ������� ����
                
                inc i
            .endw
        .else
            ; ����� ������
            mov eax, rect.bottom
            sub eax, 10
            movzx ebx, word ptr nBufScaled[0]
            sub eax, ebx
            mov yPos, eax
            
            ; ���������� � ��������� �������
            invoke MoveToEx, hdc, 10, yPos, NULL
            
            mov i, 1
            .while i < 500
                mov eax, rect.bottom
                sub eax, 10
                mov ecx, i
                movzx ebx, word ptr nBufScaled[ecx*2]
                sub eax, ebx
                mov yPos, eax
                mov eax, i
                add eax, 10  ; ������ �����
                mov xPos, eax
                
                ; ������ �����
                invoke LineTo, hdc, xPos, yPos
                
                inc i
            .endw
        .endif
    .endif
    
    ; ��������������� ������ ����
    invoke SelectObject, hdc, hOldPen
    
    invoke EndPaint, hwnd, addr ps
    ret
OnPaint endp

; ��������� ��������� WM_COMMAND
OnCommand proc hwnd:DWORD, wParam:DWORD
    LOCAL wID:WORD

    mov eax, wParam
    movzx wID, ax

    .if wID == ID_DOTS
        mov Mode, ID_DOTS
        invoke InvalidateRect, hwnd, NULL, TRUE
    .elseif wID == ID_CURVE
        mov Mode, ID_CURVE
        invoke InvalidateRect, hwnd, NULL, TRUE
    .elseif wID == MI_OPEN
        ; ������������� ��������� OPENFILENAME
        mov ofn.lStructSize, SIZEOF OPENFILENAME
        mov ofn.hwndOwner, hwnd
        mov ofn.hInstance, hInstance
        mov ofn.lpstrFilter, addr szFile ; ���������� szFile ��� ����� ��� �������
        mov ofn.lpstrCustomFilter, NULL
        mov ofn.nMaxCustFilter, 0
        mov ofn.nFilterIndex, 0
        mov ofn.lpstrFile, addr szFile
        mov ofn.nMaxFile, SIZEOF szFile
        mov ofn.lpstrFileTitle, NULL
        mov ofn.nMaxFileTitle, 0
        mov ofn.lpstrInitialDir, NULL
        mov ofn.lpstrTitle, addr AppName
        mov ofn.Flags, OFN_FILEMUSTEXIST or OFN_PATHMUSTEXIST
        mov ofn.nFileOffset, 0
        mov ofn.nFileExtension, 0
        mov ofn.lpstrDefExt, NULL
        mov ofn.lCustData, 0
        mov ofn.lpfnHook, NULL
        mov ofn.lpTemplateName, NULL
        
        invoke GetOpenFileName, addr ofn
        .if eax
            invoke CreateFile, addr szFile, GENERIC_READ, 0, NULL, 
                              OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL
            mov hFile, eax
            .if eax != INVALID_HANDLE_VALUE
                ; ������ ������
                invoke ReadFile, hFile, addr nBuf, 1000, addr nCnt, NULL
                invoke CloseHandle, hFile
                
                ; ���������, ��� ������ �����������
                .if nCnt > 0
                    mov bDataOK, TRUE
                    
                    ; ������������ ������
                    finit
                    mov ecx, 0
                    mov eax, nCnt
                    shr eax, 1  ; ����� �� 2, ��� ��� ������ �����
                    .while ecx < eax && ecx < 500
                        fild word ptr nBuf[ecx*2]
                        fmul dword ptr nCurrentScale
                        fistp word ptr nBufScaled[ecx*2]
                        inc ecx
                    .endw
                    
                    invoke InvalidateRect, hwnd, NULL, TRUE
                .endif
            .endif
        .endif
    .elseif wID == MI_EXIT
        invoke DestroyWindow, hwnd
    .endif

    ret
OnCommand endp

; ��������� �������� ����� (�� ������������, ��� ��������� � OnCommand)
OpenFileDlg proc hwnd:DWORD
    ret
OpenFileDlg endp

end start
