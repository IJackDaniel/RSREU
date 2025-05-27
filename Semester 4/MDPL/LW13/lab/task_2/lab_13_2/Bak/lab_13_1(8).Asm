.386
.model flat, stdcall
option casemap:none

include \masm32\include\windows.inc
include \masm32\include\user32.inc
include \masm32\include\kernel32.inc
include \masm32\include\gdi32.inc
include \masm32\include\comdlg32.inc
includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\gdi32.lib
includelib \masm32\lib\comdlg32.lib

MI_OPEN      equ 101
MI_EXIT      equ 102
MI_SETTINGS  equ 103
ID_DOTS      equ 201
ID_CURVE     equ 202
ID_SCALE     equ 203
IDOK         equ 1
IDCANCEL     equ 2
IDD_SETTINGS equ 300

; Прототипы функций
WndProc proto :DWORD,:DWORD,:DWORD,:DWORD
OnCommand proto :DWORD,:DWORD,:DWORD
OnPaint proto :DWORD
OnDestroy proto :DWORD

.data
szClassName   db "MainWindowClass",0
szTitle       db "Program 6-7 - Data Viewer with Controls",0
szMenuName    db "Main",0
szFilter      db "Data files (*.DAT)",0,"*.dat",0,"All files (*.*)",0,"*.*",0,0
szFile        db 128 dup(0)
nBuf          dw 500 dup(0)
nBufScaled    dw 500 dup(0)
nScales       dd 200, 175, 150, 125, 100, 75, 50, 25
bDataOK       dd FALSE
hInstance     dd 0
hSettingsBox  dd 0
hPen          dd 0
nScaleIndex   dd 4
nCurrentScale dd 3F800000h ; 1.0 в IEEE float
Mode          dd ID_DOTS
szPercent     db "%d%%",0
flt100        dd 100.0

.data?
wc            WNDCLASS <?>
msg           MSG <?>
hMainWnd      dd ?

.code
start:
    invoke GetModuleHandle, NULL
    mov hInstance, eax
    
    ; Регистрация класса окна
    mov wc.style, CS_HREDRAW or CS_VREDRAW
    mov wc.lpfnWndProc, offset WndProc
    mov eax, hInstance
    mov wc.hInstance, eax
    invoke LoadIcon, NULL, IDI_APPLICATION
    mov wc.hIcon, eax
    invoke LoadCursor, NULL, IDC_ARROW
    mov wc.hCursor, eax
    mov wc.cbClsExtra, 0
    mov wc.cbWndExtra, 0
    invoke GetStockObject, WHITE_BRUSH
    mov wc.hbrBackground, eax
    mov wc.lpszMenuName, offset szMenuName
    mov wc.lpszClassName, offset szClassName
    
    invoke RegisterClass, addr wc
    
    ; Создание главного окна
    invoke CreateWindowEx, 0, offset szClassName, offset szTitle,
                         WS_OVERLAPPEDWINDOW,
                         CW_USEDEFAULT, CW_USEDEFAULT, 600, 400,
                         NULL, NULL, hInstance, NULL
    mov hMainWnd, eax
    
    invoke ShowWindow, hMainWnd, SW_SHOWNORMAL
    invoke UpdateWindow, hMainWnd
    
    ; Создание пера
    invoke CreatePen, PS_SOLID, 1, 0000FFh  ; синее перо
    mov hPen, eax
    
    ; Цикл сообщений
    .while TRUE
        invoke GetMessage, addr msg, NULL, 0, 0
        .break .if eax == 0
        invoke TranslateMessage, addr msg
        invoke DispatchMessage, addr msg
    .endw
    
    invoke ExitProcess, msg.wParam

; Главная функция обработки сообщений
WndProc proc hwnd:DWORD, umsg:DWORD, wparam:DWORD, lparam:DWORD
    .if umsg == WM_COMMAND
        invoke OnCommand, hwnd, wparam, lparam
    .elseif umsg == WM_PAINT
        invoke OnPaint, hwnd
    .elseif umsg == WM_DESTROY
        invoke OnDestroy, hwnd
    .else
        invoke DefWindowProc, hwnd, umsg, wparam, lparam
        ret
    .endif
    xor eax, eax
    ret
WndProc endp

; Обработчик команд
OnCommand proc hwnd:DWORD, wparam:DWORD, lparam:DWORD
    LOCAL ofn:OPENFILENAME
    LOCAL hFile:DWORD
    LOCAL nCnt:DWORD
    
    mov eax, wparam
    .if ax == MI_OPEN
        ; Открытие файла
        mov ofn.lStructSize, sizeof OPENFILENAME
        mov eax, hwnd
        mov ofn.hwndOwner, eax
        mov ofn.hInstance, 0
        lea eax, szFilter
        mov ofn.lpstrFilter, eax
        mov ofn.lpstrCustomFilter, 0
        mov ofn.nMaxCustFilter, 0
        mov ofn.nFilterIndex, 0
        lea eax, szFile
        mov ofn.lpstrFile, eax
        mov ofn.nMaxFile, sizeof szFile
        mov ofn.lpstrFileTitle, 0
        mov ofn.nMaxFileTitle, 0
        mov ofn.lpstrInitialDir, 0
        mov ofn.lpstrTitle, 0
        mov ofn.Flags, OFN_PATHMUSTEXIST or OFN_FILEMUSTEXIST
        mov ofn.nFileOffset, 0
        mov ofn.nFileExtension, 0
        mov ofn.lpstrDefExt, 0
        mov ofn.lCustData, 0
        mov ofn.lpfnHook, 0
        mov ofn.lpTemplateName, 0
        
        invoke GetOpenFileName, addr ofn
        .if eax
            invoke CreateFile, addr szFile, GENERIC_READ, 0, NULL, 
                              OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL
            mov hFile, eax
            .if eax != INVALID_HANDLE_VALUE
                invoke ReadFile, hFile, addr nBuf, 1000, addr nCnt, NULL
                invoke CloseHandle, hFile
                
                .if nCnt > 0
                    mov bDataOK, TRUE
                    
                    ; Масштабирование данных
                    finit
                    mov ecx, 0
                    mov eax, nCnt
                    shr eax, 1  ; делим на 2 (размер word)
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
    .elseif ax == MI_EXIT
        invoke DestroyWindow, hwnd
    .endif
    ret
OnCommand endp

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
            ; Режим точек
            .while i < 500
                mov eax, rect.bottom
                sub eax, 20
                mov ecx, i
                movzx ebx, word ptr nBufScaled[ecx*2]
                sub eax, ebx
                mov yPos, eax
                mov eax, i
                add eax, 10  ; отступ слева
                mov xPos, eax
                
                ; Проверяем границы
                ;.if yPos > 0 && yPos < rect.bottom && xPos > 0 && xPos < rect.right
                    ;invoke SetPixel, hdc, xPos, yPos, 0000FFh  ; красный цвет
                ;.endif
                
                inc i
            .endw
        .else
            ; Режим кривой
            mov eax, rect.bottom
            sub eax, 20
            movzx ebx, word ptr nBufScaled[0]
            sub eax, ebx
            mov yPos, eax
            
            ; Перемещаем в начальную позицию
            invoke MoveToEx, hdc, 10, yPos, NULL
            
            mov i, 1
            .while i < 500
                mov eax, rect.bottom
                sub eax, 20
                mov ecx, i
                movzx ebx, word ptr nBufScaled[ecx*2]
                sub eax, ebx
                mov yPos, eax
                mov eax, i
                add eax, 10  ; отступ слева
                mov xPos, eax
                
                ; Проверяем границы
                ;.if yPos > 0 && yPos < rect.bottom && xPos > 0 && xPos < rect.right
                    ;invoke LineTo, hdc, xPos, yPos
                ;.endif
                
                inc i
            .endw
        .endif
    .endif
    
    ; Восстанавливаем старое перо
    invoke SelectObject, hdc, hOldPen
    
    invoke EndPaint, hwnd, addr ps
    ret
OnPaint endp

; Обработчик закрытия
OnDestroy proc hwnd:DWORD
    .if hSettingsBox != 0
        invoke DestroyWindow, hSettingsBox
    .endif
    
    invoke DeleteObject, hPen
    invoke PostQuitMessage, 0
    ret
OnDestroy endp

end start
