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

; Eiinoaiou
MI_OPEN      equ 101
MI_EXIT      equ 102
MI_SETTINGS  equ 103
ID_DOTS      equ 201
ID_CURVE     equ 202
ID_SCALE     equ 203
IDOK         equ 1
IDCANCEL     equ 2
IDD_SETTINGS equ 300

; Iaainoa?uea eiinoaiou
LB_ADDSTRING    equ 0180h
LB_SETCURSEL    equ 0186h
LB_GETCURSEL    equ 0188h
LBN_SELCHANGE   equ 1
BST_CHECKED     equ 1

; I?ioioeiu ooieoee
WndProc proto :DWORD,:DWORD,:DWORD,:DWORD
OnCommand proto :DWORD,:DWORD,:DWORD
OnPaint proto :DWORD
OnDestroy proto :DWORD
DlgProc proto :DWORD,:DWORD,:DWORD,:DWORD
DlgOnInitDialog proto :DWORD,:DWORD,:DWORD
DlgOnCommand proto :DWORD,:DWORD,:DWORD,:DWORD

; Eieoeaeece?iaaiiua aaiiua
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
nCurrentScale dd 3F800000h ; 1.0 a IEEE float
Mode          dd ID_DOTS
szPercent     db "%d%%",0
flt100        dd 100.0

; Iaeieoeaeece?iaaiiua aaiiua
.data?
wc            WNDCLASS <?>
msg           MSG <?>
hMainWnd      dd ?

.code
start:
    invoke GetModuleHandle, NULL
    mov hInstance, eax
    
    ; ?aaeno?aoey eeanna ieia
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
    
    ; Nicaaiea aeaaiiai ieia
    invoke CreateWindowEx, 0, offset szClassName, offset szTitle,
                         WS_OVERLAPPEDWINDOW,
                         CW_USEDEFAULT, CW_USEDEFAULT, 600, 400,
                         NULL, NULL, hInstance, NULL
    mov hMainWnd, eax
    
    invoke ShowWindow, hMainWnd, SW_SHOWNORMAL
    invoke UpdateWindow, hMainWnd
    
    ; Nicaaiea ia?a
    invoke CreatePen, PS_SOLID, 1, 0000FFh  ; Neiee oaao
    mov hPen, eax
    
    ; Oeee niiauaiee
    .while TRUE
        invoke GetMessage, addr msg, NULL, 0, 0
        .break .if eax == 0
        invoke TranslateMessage, addr msg
        invoke DispatchMessage, addr msg
    .endw
    
    invoke ExitProcess, msg.wParam

; Aeaaiay ieiiiay i?ioaao?a
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

; Ia?aaio?ee eiiaia
OnCommand proc hwnd:DWORD, wparam:DWORD, lparam:DWORD
    LOCAL ofn:OPENFILENAME
    LOCAL hFile:DWORD
    LOCAL nCnt:DWORD
    
    mov eax, wparam
    .if ax == MI_OPEN
        ; Ioe?uoea oaeea
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
                mov bDataOK, TRUE
                
                ; Ianooaae?iaaiea aaiiuo
                finit
                mov ecx, 0
                .while ecx < 500
                    fild word ptr nBuf[ecx*2]
                    fmul dword ptr nCurrentScale
                    fistp word ptr nBufScaled[ecx*2]
                    inc ecx
                .endw
                
                invoke InvalidateRect, hwnd, NULL, TRUE
            .endif
        .endif
    .elseif ax == MI_EXIT
        invoke DestroyWindow, hwnd
    .elseif ax == MI_SETTINGS
        .if hSettingsBox == 0
            invoke CreateDialogParam, hInstance, IDD_SETTINGS, hwnd, 
                                    addr DlgProc, NULL
            mov hSettingsBox, eax
            invoke ShowWindow, hSettingsBox, SW_SHOW
        .endif
    .endif
    ret
OnCommand endp

; Io?eniaea ieia
OnPaint proc hwnd:DWORD
    LOCAL ps:PAINTSTRUCT
    LOCAL hdc:DWORD
    LOCAL hOldPen:DWORD
    LOCAL rect:RECT
    LOCAL i:DWORD
    
    invoke BeginPaint, hwnd, addr ps
    mov hdc, eax
    invoke SelectObject, hdc, hPen
    mov hOldPen, eax
    
    invoke GetClientRect, hwnd, addr rect
    
    .if bDataOK == TRUE
        mov i, 0
        .if Mode == ID_DOTS
            .while i < 500
                mov eax, rect.bottom
                sub eax, 5
                movzx ebx, word ptr nBufScaled[i*2]                
                sub eax, ebx
                invoke SetPixel, hdc, i, eax, 0000FFh  ; Neiee oaao
                inc i
            .endw
        .else
            ; ?a?ei eeiee
            mov eax, rect.bottom
            sub eax, 5
            movzx ebx, word ptr nBufScaled[0]
            sub eax, ebx
            invoke MoveToEx, hdc, 0, eax, NULL
            
            mov i, 1
            .while i < 500
                mov eax, rect.bottom
                sub eax, 5
                movzx ebx, word ptr nBufScaled[i*2]
                sub eax, ebx
                invoke LineTo, hdc, i, eax
                inc i
            .endw
        .endif
    .endif
    
    invoke SelectObject, hdc, hOldPen
    invoke EndPaint, hwnd, addr ps
    ret
OnPaint endp

; Ia?aaio?ee cae?uoey
OnDestroy proc hwnd:DWORD
    .if hSettingsBox != 0
        invoke DestroyWindow, hSettingsBox
    .endif
    
    invoke DeleteObject, hPen
    invoke PostQuitMessage, 0
    ret
OnDestroy endp

; I?ioaao?a aeaeiaa
DlgProc proc hwnd:DWORD, umsg:DWORD, wparam:DWORD, lparam:DWORD
    .if umsg == WM_INITDIALOG
        invoke DlgOnInitDialog, hwnd, wparam, lparam
        mov eax, TRUE
        ret
    .elseif umsg == WM_COMMAND
        invoke DlgOnCommand, hwnd, wparam, lparam, 0
        mov eax, TRUE
        ret
    .endif
    
    mov eax, FALSE
    ret
DlgProc endp

; Eieoeaeecaoey aeaeiaa
DlgOnInitDialog proc hwnd:DWORD, hwndFocus:DWORD, lparam:DWORD
    LOCAL szBuffer[10]:BYTE
    LOCAL i:DWORD
    
    ; Onoaiiaea ?a?eia ioia?a?aiey
    mov eax, Mode
    invoke CheckDlgButton, hwnd, eax, 1  ; BST_CHECKED = 1
    
    ; Caiieiaiea nienea ianooaaia
    mov i, 0
    .while i < 8
        mov eax, i
        invoke wsprintf, addr szBuffer, addr szPercent, nScales[eax*4]
        invoke SendDlgItemMessage, hwnd, ID_SCALE, 0180h, 0, addr szBuffer  ; LB_ADDSTRING
        inc i
    .endw
    
    ; Onoaiiaea oaeouaai ianooaaa
    mov eax, nScaleIndex
    invoke SendDlgItemMessage, hwnd, ID_SCALE, 0186h, eax, 0  ; LB_SETCURSEL
    
    mov eax, TRUE
    ret
DlgOnInitDialog endp

; Ia?aaioea eiiaia aeaeiaa
DlgOnCommand proc hwnd:DWORD, cmdid:DWORD, hwndCtl:DWORD, codeNotify:DWORD
    LOCAL i:DWORD
    LOCAL temp:DWORD
    
    .if cmdid == IDOK || cmdid == IDCANCEL
        invoke DestroyWindow, hwnd
        mov hSettingsBox, 0
    .elseif cmdid == ID_DOTS
        mov Mode, ID_DOTS
        invoke GetParent, hwnd
        invoke InvalidateRect, eax, NULL, TRUE
    .elseif cmdid == ID_CURVE
        mov Mode, ID_CURVE
        invoke GetParent, hwnd
        invoke InvalidateRect, eax, NULL, TRUE
    .elseif cmdid == ID_SCALE
        .if codeNotify == 1  ; LBN_SELCHANGE = 1
            ; Iieo?aiea aua?aiiiai ianooaaa
            invoke SendDlgItemMessage, hwnd, ID_SCALE, 0188h, 0, 0  ; LB_GETCURSEL
            mov nScaleIndex, eax
            
            ; I?aia?aciaaiea a float
            mov eax, nScales[eax*4]
            mov temp, eax
            finit
            fild temp
            fld1
            fld dword ptr flt100
            fdivp st(1), st(0)
            fmulp st(1), st(0)
            fstp dword ptr nCurrentScale
            
            ; Ia?aianooaae?iaaiea aaiiuo
            .if bDataOK == TRUE
                mov i, 0
                .while i < 500
                    mov eax, i
                    fild word ptr nBuf[eax*2]
                    fmul dword ptr nCurrentScale
                    fistp word ptr nBufScaled[eax*2]
                    inc i
                .endw
                
                invoke GetParent, hwnd
                invoke InvalidateRect, eax, NULL, TRUE
            .endif
        .endif
    .endif
    
    mov eax, TRUE
    ret
DlgOnCommand endp

end start