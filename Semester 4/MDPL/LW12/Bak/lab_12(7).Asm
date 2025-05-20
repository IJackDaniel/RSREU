.386
.model flat, stdcall
option casemap :none

include \masm32\include\windows.inc
include \masm32\include\user32.inc
include \masm32\include\kernel32.inc
include \masm32\include\gdi32.inc
include \masm32\include\msvcrt.inc

includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\gdi32.lib
includelib \masm32\lib\msvcrt.lib

TabulateFunction PROTO
FloatToStr PROTO :REAL4, :DWORD

IDC_TABULATE_BUTTON equ 101
IDC_RESULTS_EDIT equ 102
IDC_STATIC1 equ 103

IDC_START_EDIT equ 201
IDC_END_EDIT equ 202
IDC_STEP_EDIT equ 203
IDC_CALCULATE_BUTTON equ 204
IDC_STATIC2 equ 205
IDC_STATIC3 equ 206
IDC_STATIC4 equ 207

.data
ClassName db "TabFunctionClass", 0
AppName db "App", 0
ChildClassName db "ChildTabFunctionClass", 0
ChildTitle db "Child", 0

szStatic db "STATIC", 0
szEdit db "EDIT", 0
szButton db "BUTTON", 0

szButtonText db "OK", 0
szStartLabel db "START:", 0
szEndLabel db "END:", 0
szStepLabel db "STEP:", 0
szCalculateText db "Calculate", 0
szFormatResult db "f(%s) = %s", 13, 10, 0
szFloatFormat db "%.2f", 0
szErrorInput db "Error", 0
szErrorMessage db "Input correct numbers, h > 0", 0

szDefaultStart db "0", 0
szDefaultEnd db "10", 0
szDefaultStep db "1", 0

dblThree REAL4 3.0
dblTwo REAL4 2.0
dblZero REAL4 0.0

.data?
hInstance HINSTANCE ?
hMainWnd HWND ?
hChildWnd HWND ?
hTabulateButton HWND ?
hResultsEdit HWND ?
hStartEdit HWND ?
hEndEdit HWND ?
hStepEdit HWND ?
hCalculateButton HWND ?
hStartLabel HWND ?
hEndLabel HWND ?
hStepLabel HWND ?
buffer db 256 dup(?)
resultBuffer db 4096 dup(?)
tempBuffer db 256 dup(?)
startVal REAL4 ?
endVal REAL4 ?
stepVal REAL4 ?
currentX REAL4 ?
result REAL4 ?

.code
start:
    invoke GetModuleHandle, NULL
    mov hInstance, eax
    
    call InitMainWindow
    
    .while TRUE
        invoke GetMessage, addr buffer, NULL, 0, 0
        .break .if (!eax)
        invoke TranslateMessage, addr buffer
        invoke DispatchMessage, addr buffer
    .endw
    
    invoke ExitProcess, 0

InitMainWindow proc
    LOCAL wc:WNDCLASSEX
    LOCAL msg:MSG
    
    mov wc.cbSize, SIZEOF WNDCLASSEX
    mov wc.style, CS_HREDRAW or CS_VREDRAW
    mov wc.lpfnWndProc, OFFSET MainWndProc
    mov wc.cbClsExtra, NULL
    mov wc.cbWndExtra, NULL
    push hInstance
    pop wc.hInstance
    mov wc.hbrBackground, COLOR_BTNFACE+1
    mov wc.lpszMenuName, NULL
    mov wc.lpszClassName, OFFSET ClassName
    invoke LoadIcon, NULL, IDI_APPLICATION
    mov wc.hIcon, eax
    mov wc.hIconSm, eax
    invoke LoadCursor, NULL, IDC_ARROW
    mov wc.hCursor, eax
    
    invoke RegisterClassEx, addr wc
    
    invoke CreateWindowEx, WS_EX_CLIENTEDGE, ADDR ClassName, ADDR AppName,\
           WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT, 500, 400, NULL, NULL,\
           hInstance, NULL
    mov hMainWnd, eax
    
    invoke ShowWindow, hMainWnd, SW_SHOWNORMAL
    invoke UpdateWindow, hMainWnd
    
    ret
InitMainWindow endp

MainWndProc proc hWnd:HWND, uMsg:UINT, wParam:WPARAM, lParam:LPARAM
    .if uMsg == WM_CREATE
        invoke CreateWindowEx, NULL, ADDR szButton, ADDR szButtonText,\
               WS_CHILD or WS_VISIBLE or BS_PUSHBUTTON, 10, 10, 150, 25,\
               hWnd, IDC_TABULATE_BUTTON, hInstance, NULL
        mov hTabulateButton, eax
        
        invoke CreateWindowEx, WS_EX_CLIENTEDGE, ADDR szEdit, NULL,\
               WS_CHILD or WS_VISIBLE or WS_VSCROLL or ES_MULTILINE or ES_AUTOVSCROLL or ES_READONLY,\
               10, 45, 470, 300, hWnd, IDC_RESULTS_EDIT, hInstance, NULL
        mov hResultsEdit, eax
        
    .elseif uMsg == WM_COMMAND
        mov eax, wParam
        .if ax == IDC_TABULATE_BUTTON
            mov edx, wParam
            shr edx, 16
            .if dx == BN_CLICKED
                call CreateChildWindow
            .endif
        .endif
        
    .elseif uMsg == WM_DESTROY
        invoke PostQuitMessage, NULL
        
    .else
        invoke DefWindowProc, hWnd, uMsg, wParam, lParam
        ret
    .endif
    
    xor eax, eax
    ret
MainWndProc endp

CreateChildWindow proc
    LOCAL wc:WNDCLASSEX
    
    mov wc.cbSize, SIZEOF WNDCLASSEX
    mov wc.style, CS_HREDRAW or CS_VREDRAW
    mov wc.lpfnWndProc, OFFSET ChildWndProc
    mov wc.cbClsExtra, NULL
    mov wc.cbWndExtra, NULL
    push hInstance
    pop wc.hInstance
    mov wc.hbrBackground, COLOR_BTNFACE+1
    mov wc.lpszMenuName, NULL
    mov wc.lpszClassName, OFFSET ChildClassName
    invoke LoadIcon, NULL, IDI_APPLICATION
    mov wc.hIcon, eax
    mov wc.hIconSm, eax
    invoke LoadCursor, NULL, IDC_ARROW
    mov wc.hCursor, eax
    
    invoke RegisterClassEx, addr wc

    invoke CreateWindowEx, WS_EX_DLGMODALFRAME, ADDR ChildClassName, ADDR ChildTitle,\
           WS_POPUP or WS_CAPTION or WS_SYSMENU or WS_VISIBLE, CW_USEDEFAULT,\
           CW_USEDEFAULT, 300, 200, hMainWnd, NULL, hInstance, NULL
    mov hChildWnd, eax

    invoke CreateWindowEx, NULL, ADDR szStatic, ADDR szStartLabel,\
           WS_CHILD or WS_VISIBLE or SS_LEFT, 10, 20, 120, 20,\
           hChildWnd, IDC_STATIC2, hInstance, NULL
    mov hStartLabel, eax
    
    invoke CreateWindowEx, WS_EX_CLIENTEDGE, ADDR szEdit, NULL,\
           WS_CHILD or WS_VISIBLE or ES_AUTOHSCROLL, 140, 20, 140, 20,\
           hChildWnd, IDC_START_EDIT, hInstance, NULL
    mov hStartEdit, eax
    
    invoke CreateWindowEx, NULL, ADDR szStatic, ADDR szEndLabel,\
           WS_CHILD or WS_VISIBLE or SS_LEFT, 10, 50, 120, 20,\
           hChildWnd, IDC_STATIC3, hInstance, NULL
    mov hEndLabel, eax
    
    invoke CreateWindowEx, WS_EX_CLIENTEDGE, ADDR szEdit, NULL,\
           WS_CHILD or WS_VISIBLE or ES_AUTOHSCROLL, 140, 50, 140, 20,\
           hChildWnd, IDC_END_EDIT, hInstance, NULL
    mov hEndEdit, eax
    
    invoke CreateWindowEx, NULL, ADDR szStatic, ADDR szStepLabel,\
           WS_CHILD or WS_VISIBLE or SS_LEFT, 10, 80, 120, 20,\
           hChildWnd, IDC_STATIC4, hInstance, NULL
    mov hStepLabel, eax
    
    invoke CreateWindowEx, WS_EX_CLIENTEDGE, ADDR szEdit, NULL,\
           WS_CHILD or WS_VISIBLE or ES_AUTOHSCROLL, 140, 80, 140, 20,\
           hChildWnd, IDC_STEP_EDIT, hInstance, NULL
    mov hStepEdit, eax
    
    invoke CreateWindowEx, NULL, ADDR szButton, ADDR szCalculateText,\
           WS_CHILD or WS_VISIBLE or BS_DEFPUSHBUTTON, 100, 120, 100, 30,\
           hChildWnd, IDC_CALCULATE_BUTTON, hInstance, NULL
    mov hCalculateButton, eax
    
    invoke SetWindowText, hStartEdit, ADDR szDefaultStart
    invoke SetWindowText, hEndEdit, ADDR szDefaultEnd
    invoke SetWindowText, hStepEdit, ADDR szDefaultStep
    
    invoke SetFocus, hStartEdit
    
    ret
CreateChildWindow endp

ChildWndProc proc hWnd:HWND, uMsg:UINT, wParam:WPARAM, lParam:LPARAM
    LOCAL inputBuffer[64]:BYTE
    
    .if uMsg == WM_COMMAND
        mov eax, wParam
        .if ax == IDC_CALCULATE_BUTTON
            mov edx, wParam
            shr edx, 16
            .if dx == BN_CLICKED
                invoke GetWindowText, hStartEdit, ADDR inputBuffer, 64
                invoke crt_atof, ADDR inputBuffer
                fstp startVal
                
                invoke GetWindowText, hEndEdit, ADDR inputBuffer, 64
                invoke crt_atof, ADDR inputBuffer
                fstp endVal
                
                invoke GetWindowText, hStepEdit, ADDR inputBuffer, 64
                invoke crt_atof, ADDR inputBuffer
                fstp stepVal
                
                fld startVal
                fld endVal
                fcompp
                fstsw ax
                sahf
                jbe error_input
                
                fld dblZero
                fld stepVal
                fcompp
                fstsw ax
                sahf
                jbe error_input
                
                invoke TabulateFunction
                invoke DestroyWindow, hChildWnd
                jmp done
                
error_input:
                invoke MessageBox, hWnd, ADDR szErrorMessage, ADDR szErrorInput, MB_ICONERROR
                
done:
            .endif
        .endif
        
    .elseif uMsg == WM_CLOSE
        invoke DestroyWindow, hWnd
        
    .else
        invoke DefWindowProc, hWnd, uMsg, wParam, lParam
        ret
    .endif
    
    xor eax, eax
    ret
ChildWndProc endp

FloatToStr proc fValue:REAL4, lpBuffer:DWORD

    fld fValue
    sub esp, 8
    fstp qword ptr [esp]
    
    push offset szFloatFormat
    push lpBuffer
    call crt_sprintf
    add esp, 16
    
    ret
FloatToStr endp


TabulateFunction proc
    LOCAL valueStr[32]:BYTE
    LOCAL resultStr[32]:BYTE
    

    mov resultBuffer[0], 0
    
    fld startVal
    fstp currentX
    
loop_tabulate:
    fld currentX
    fld endVal
    fcompp
    fstsw ax
    sahf
    jbe done_tabulate
    
    ; f(x) = x^2 - 3x + 2
    fld currentX
    fld currentX
    fmulp st(1), st(0)     ; x^2
    
    fld currentX
    fld dblThree
    fmulp st(1), st(0)     ; 3x
    
    fsubp st(1), st(0)     ; x^2 - 3x
    
    fld dblTwo
    faddp st(1), st(0)     ; x^2 - 3x + 2
    
    fstp result
    
    invoke FloatToStr, currentX, ADDR valueStr
    invoke FloatToStr, result, ADDR resultStr
    
    invoke wsprintf, ADDR tempBuffer, ADDR szFormatResult, ADDR valueStr, ADDR resultStr
    invoke lstrcat, ADDR resultBuffer, ADDR tempBuffer
    
    fld currentX
    fld stepVal
    faddp st(1), st(0)
    fstp currentX
    
    jmp loop_tabulate
    
done_tabulate:
    invoke SetWindowText, hResultsEdit, ADDR resultBuffer
    ret
TabulateFunction endp

end start