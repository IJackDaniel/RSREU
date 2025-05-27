.386
.model flat, stdcall
option casemap:none

include \masm32\include\windows.inc
include \masm32\include\user32.inc
include \masm32\include\kernel32.inc
include \masm32\include\gdi32.inc
include \masm32\include\comdlg32.inc
include \masm32\macros\macros.asm

includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\gdi32.lib
includelib \masm32\lib\comdlg32.lib

; ????????? ????
MI_OPEN     equ 101
MI_EXIT     equ 102

; ????????? ???????
WinMain PROTO :DWORD, :DWORD, :DWORD, :DWORD
WndProc PROTO :HWND, :UINT, :WPARAM, :LPARAM
OnCommand PROTO :HWND, :WPARAM, :LPARAM
OnPaint PROTO :HWND
OnDestroy PROTO :HWND

.data
    szClassName     db "MainWindow", 0
    szTitle         db "Program 6-6 - Data Viewer", 0
    szFilter        db "Data files (*.DAT)", 0, "*.dat", 0
                    db "All files (*.*)", 0, "*.*", 0, 0
    szOpenTitle     db "Select data file", 0
    szErrorReg      db "Window class registration error!", 0
    szErrorCreate   db "Window creation error!", 0
    szErrorTitle    db "Error", 0
    szErrorSize     db "File has incorrect size!", 13, 10
                    db "Expected 1000 bytes (500 short values).", 0
    szErrorRead     db "File read error!", 0
    szErrorOpen     db "Cannot open file!", 0
    szNoDataMsg     db "No data loaded. Select 'File -> Open...' to load data.", 0
    szInfoFormat    db "Points: 500, Min: %d, Max: %d", 0
    szTitleFormat   db "%s - %s", 0
    fScale          REAL4 1.0

.data?
    hInstance       dd ?
    hPen            dd ?
    szFile          db MAX_PATH dup(?)
    nBuf            dw 500 dup(?)
    bDataOK         dd ?
    szNewTitle      db 300 dup(?)
    szInfo          db 100 dup(?)

.code

start:
    invoke GetModuleHandle, NULL
    mov hInstance, eax
    
    invoke WinMain, hInstance, NULL, NULL, SW_SHOWDEFAULT
    invoke ExitProcess, eax

WinMain proc hInst:DWORD, hPrevInst:DWORD, CmdLine:DWORD, CmdShow:DWORD
    LOCAL wc:WNDCLASSEX
    LOCAL msg:MSG
    LOCAL hwnd:DWORD
    
    mov eax, hInst
    mov hInstance, eax
    
    ; ????????????? ????????? ?????? ????
    mov wc.cbSize, SIZEOF WNDCLASSEX
    mov wc.style, CS_HREDRAW or CS_VREDRAW
    mov wc.lpfnWndProc, OFFSET WndProc
    mov wc.cbClsExtra, 0
    mov wc.cbWndExtra, 0
    mov eax, hInst
    mov wc.hInstance, eax
    invoke LoadIcon, NULL, IDI_APPLICATION
    mov wc.hIcon, eax
    invoke LoadCursor, NULL, IDC_ARROW
    mov wc.hCursor, eax
    invoke GetStockObject, WHITE_BRUSH
    mov wc.hbrBackground, eax
    mov wc.lpszMenuName, 1000
    mov wc.lpszClassName, OFFSET szClassName
    mov wc.hIconSm, 0
    
    ; ??????????? ?????? ????
    invoke RegisterClassEx, addr wc
    .if eax == 0
        invoke MessageBox, NULL, addr szErrorReg, addr szErrorTitle, MB_OK or MB_ICONERROR
        mov eax, 0
        ret
    .endif
    
    ; ???????? ???????? ????
    invoke LoadMenu, hInst, 1000
    mov ebx, eax
    invoke CreateWindowEx, 0, addr szClassName, addr szTitle,
           WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT,
           800, 600, NULL, ebx, hInst, NULL
    mov hwnd, eax
    
    .if eax == 0
        invoke MessageBox, NULL, addr szErrorCreate, addr szErrorTitle, MB_OK or MB_ICONERROR
        mov eax, 0
        ret
    .endif
    
    ; ??????????? ????
    invoke ShowWindow, hwnd, CmdShow
    invoke UpdateWindow, hwnd
    
    ; ???????? ?????? ???? ??? ????????? ???????
    invoke CreatePen, PS_SOLID, 1, 000FF0000h  ; RGB(0, 0, 255)
    mov hPen, eax
    
    ; ???????? ???? ????????? ?????????
    .while TRUE
        invoke GetMessage, addr msg, NULL, 0, 0
        .break .if eax == 0
        invoke TranslateMessage, addr msg
        invoke DispatchMessage, addr msg
    .endw
    
    mov eax, msg.wParam
    ret
WinMain endp

WndProc proc hWnd:HWND, uMsg:UINT, wParam:WPARAM, lParam:LPARAM
    .if uMsg == WM_COMMAND
        invoke OnCommand, hWnd, wParam, lParam
        mov eax, 0
        ret
    .elseif uMsg == WM_PAINT
        invoke OnPaint, hWnd
        mov eax, 0
        ret
    .elseif uMsg == WM_DESTROY
        invoke OnDestroy, hWnd
        mov eax, 0
        ret
    .endif
    
    invoke DefWindowProc, hWnd, uMsg, wParam, lParam
    ret
WndProc endp

OnCommand proc hWnd:HWND, wParam:WPARAM, lParam:LPARAM
    LOCAL ofn:OPENFILENAME
    LOCAL hFile:HANDLE
    LOCAL dwBytesRead:DWORD
    
    mov eax, wParam
    and eax, 0FFFFh  ; ???????? ??????? ?????
    .if eax == MI_OPEN
        ; ????????????? ????????? ??? ??????? ???????? ?????
        mov ofn.lStructSize, SIZEOF OPENFILENAME
        mov eax, hWnd
        mov ofn.hwndOwner, eax
        mov ofn.hInstance, 0
        mov ofn.lpstrFilter, OFFSET szFilter
        mov ofn.lpstrCustomFilter, 0
        mov ofn.nMaxCustFilter, 0
        mov ofn.nFilterIndex, 1
        mov ofn.lpstrFile, OFFSET szFile
        mov ofn.nMaxFile, MAX_PATH
        mov ofn.lpstrFileTitle, 0
        mov ofn.nMaxFileTitle, 0
        mov ofn.lpstrInitialDir, 0
        mov ofn.lpstrTitle, OFFSET szOpenTitle
        mov ofn.Flags, OFN_PATHMUSTEXIST or OFN_FILEMUSTEXIST or OFN_HIDEREADONLY
        mov ofn.nFileOffset, 0
        mov ofn.nFileExtension, 0
        mov ofn.lpstrDefExt, 0
        mov ofn.lCustData, 0
        mov ofn.lpfnHook, 0
        mov ofn.lpTemplateName, 0
        
        ; ??????? ?????? ????? ?????
        invoke RtlZeroMemory, addr szFile, MAX_PATH
        
        ; ????? ??????? ???????? ?????
        invoke GetOpenFileName, addr ofn
        .if eax != 0
            ; ???????? ?????????? ?????
            invoke CreateFile, addr szFile, GENERIC_READ, FILE_SHARE_READ,
                   NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL
            mov hFile, eax
            
            .if eax != INVALID_HANDLE_VALUE
                ; ?????? ?????? ?? ?????
                invoke ReadFile, hFile, addr nBuf, 1000, addr dwBytesRead, NULL
                .if eax != 0
                    .if dwBytesRead == 1000
                        mov bDataOK, TRUE
                        ; ?????????? ????????? ???? ? ?????? ?????
                        invoke wsprintf, addr szNewTitle, addr szTitleFormat, addr szTitle, addr szFile
                        invoke SetWindowText, hWnd, addr szNewTitle
                        ; ??????????? ????
                        invoke InvalidateRect, hWnd, NULL, TRUE
                    .else
                        invoke MessageBox, hWnd, addr szErrorSize, addr szErrorTitle, MB_OK or MB_ICONWARNING
                        mov bDataOK, FALSE
                    .endif
                .else
                    invoke MessageBox, hWnd, addr szErrorRead, addr szErrorTitle, MB_OK or MB_ICONERROR
                    mov bDataOK, FALSE
                .endif
                invoke CloseHandle, hFile
            .else
                invoke MessageBox, hWnd, addr szErrorOpen, addr szErrorTitle, MB_OK or MB_ICONERROR
                mov bDataOK, FALSE
            .endif
        .endif
        
    .elseif eax == MI_EXIT
        invoke DestroyWindow, hWnd
    .endif
    
    ret
OnCommand endp

OnPaint proc hWnd:HWND
    LOCAL ps:PAINTSTRUCT
    LOCAL hdc:HDC
    LOCAL rect:RECT
    LOCAL hOldPen:HPEN
    LOCAL i:DWORD
    LOCAL x:DWORD
    LOCAL y:DWORD
    LOCAL maxVal:SDWORD
    LOCAL minVal:SDWORD
    LOCAL temp:DWORD
    LOCAL range:DWORD
    
    invoke BeginPaint, hWnd, addr ps
    mov hdc, eax
    
    ; ????????? ???????? ?????????? ???????
    invoke GetClientRect, hWnd, addr rect
    
    .if bDataOK == TRUE
        ; ????? ?????? ????
        invoke SelectObject, hdc, hPen
        mov hOldPen, eax
        
        ; ????? ???????????? ? ????????????? ????????
        mov maxVal, 0
        mov minVal, 0
        mov i, 0
        .while i < 500
            mov eax, i
            movsx ebx, word ptr nBuf[eax*2]
            
            mov ecx, maxVal
            .if ebx > ecx
                mov maxVal, ebx
            .endif
            mov ecx, minVal
            .if ebx < ecx
                mov minVal, ebx
            .endif
            
            inc i
        .endw
        
        ; ?????????? ?????????
        mov eax, maxVal
        sub eax, minVal
        mov range, eax
        
        ; ????????? ??????? ???????
        mov i, 0
        .while i < 500
            ; ?????????? ????????? ?????
            mov eax, rect.right
            mul i
            mov ebx, 500
            div ebx
            mov x, eax
            
            ; ?????????? Y ??????????
            mov eax, i
            movsx ebx, word ptr nBuf[eax*2]
            sub ebx, minVal
            
            .if range > 0
                mov eax, rect.bottom
                sub eax, 40
                mul ebx
                div range
                mov temp, eax
            .else
                mov temp, 0
            .endif
            
            mov eax, rect.bottom
            sub eax, 20
            sub eax, temp
            mov y, eax
            
            ; ??????????? ?????????
            .if y < 10
                mov y, 10
            .endif
            mov eax, rect.bottom
            sub eax, 10
            .if y > eax
                mov y, eax
            .endif
            
            ; ????????? ?????
            invoke SetPixel, hdc, x, y, 000FF0000h  ; RGB(0, 0, 255)
            
            inc i
        .endw
        
        ; ?????????????? ??????? ????
        invoke SelectObject, hdc, hOldPen
        
        ; ??????????? ?????????? ? ??????
        invoke wsprintf, addr szInfo, addr szInfoFormat, minVal, maxVal
        invoke TextOut, hdc, 10, 10, addr szInfo, eax
        
    .else
        ; ??????????? ????????? ? ???, ??? ?????? ?? ?????????
        invoke DrawText, hdc, addr szNoDataMsg, -1, addr rect, DT_CENTER or DT_VCENTER or DT_SINGLELINE
    .endif
    
    invoke EndPaint, hWnd, addr ps
    ret
OnPaint endp

OnDestroy proc hWnd:HWND
    ; ???????? ?????????? ????
    .if hPen != 0
        invoke DeleteObject, hPen
    .endif
    
    ; ?????????? ??????????
    invoke PostQuitMessage, 0
    ret
OnDestroy endp

end start
