.386
.model flat, stdcall
option casemap:none

IDD_DIALOG equ 500

include \masm32\include\windows.inc
include \masm32\include\user32.inc
include \masm32\include\kernel32.inc
include \masm32\include\gdi32.inc

includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\gdi32.lib

DlgProc PROTO :DWORD, :DWORD, :DWORD, :DWORD
DlgOnInitDialog PROTO :DWORD, :DWORD, :DWORD
DlgOnCommand PROTO :DWORD, :DWORD, :DWORD, :DWORD
DlgOnPaint PROTO :DWORD

IDCANCEL equ 2
WM_INITDIALOG equ 110h
WM_COMMAND equ 111h
WM_PAINT equ 0Fh
PS_SOLID equ 0

.data
    hPen dd 0              
    
    cos_table dd 1000, 999, 995, 989, 981, 970, 956, 940, 924, 903, 881, 857, 831, 803, 773, 740
          dd 707, 671, 634, 595, 556, 515, 471, 427, 383, 336, 290, 242, 195, 146, 98, 49
          dd 0, -49, -98, -146, -195, -242, -290, -336, -383, -427, -471, -515, -556, -595, -634, -671
          dd -707, -740, -773, -803, -831, -857, -881, -903, -924, -940, -956, -970, -981, -989, -995, -999
          dd -1000, -999, -995, -989, -981, -970, -956, -940, -924, -903, -881, -857, -831, -803, -773, -740
          dd -707, -671, -634, -595, -556, -515, -471, -427, -383, -336, -290, -242, -195, -146, -98, -49
          dd 0, 49, 98, 146, 195, 242, 290, 336, 383, 427, 471, 515, 556, 595, 634, 671
          dd 707, 740, 773, 803, 831, 857, 881, 903, 924, 940, 956, 970, 981, 989, 995, 999

    sin_table dd 0, 49, 98, 146, 195, 242, 290, 336, 383, 427, 471, 515, 556, 595, 634, 671
          dd 707, 740, 773, 803, 831, 857, 881, 903, 924, 940, 956, 970, 981, 989, 995, 999
          dd 1000, 999, 995, 989, 981, 970, 956, 940, 924, 903, 881, 857, 831, 803, 773, 740
          dd 707, 671, 634, 595, 556, 515, 471, 427, 383, 336, 290, 242, 195, 146, 98, 49
          dd 0, -49, -98, -146, -195, -242, -290, -336, -383, -427, -471, -515, -556, -595, -634, -671
          dd -707, -740, -773, -803, -831, -857, -881, -903, -924, -940, -956, -970, -981, -989, -995, -999
          dd -1000, -999, -995, -989, -981, -970, -956, -940, -924, -903, -881, -857, -831, -803, -773, -740
          dd -707, -671, -634, -595, -556, -515, -471, -427, -383, -336, -290, -242, -195, -146, -98, -49

.code

start:
    invoke GetModuleHandle, NULL
    invoke DialogBoxParam, eax, IDD_DIALOG, NULL, addr DlgProc, NULL
    invoke ExitProcess, 0

DlgProc proc hwnd:DWORD, msg:DWORD, wParam:DWORD, lParam:DWORD
    
    .if msg == WM_INITDIALOG
        invoke DlgOnInitDialog, hwnd, wParam, lParam
        mov eax, 1
        ret
    .elseif msg == WM_COMMAND
        invoke DlgOnCommand, hwnd, wParam, lParam, 0
        mov eax, 1
        ret
    .elseif msg == WM_PAINT
        invoke DlgOnPaint, hwnd
        mov eax, 1
        ret
    .else
        mov eax, 0
        ret
    .endif
    
DlgProc endp

DlgOnInitDialog proc hwnd:DWORD, wParam:DWORD, lParam:DWORD
    
    invoke CreatePen, PS_SOLID, 1, 006464FFh  
    mov hPen, eax
    
    mov eax, 1
    ret
    
DlgOnInitDialog endp

DlgOnCommand proc hwnd:DWORD, wParam:DWORD, lParam:DWORD, unused:DWORD
    
    mov eax, wParam
    and eax, 0FFFFh
    
    .if eax == IDCANCEL
        invoke EndDialog, hwnd, 0
    .endif
    
    ret
    
DlgOnCommand endp

DlgOnPaint proc hwnd:DWORD
    LOCAL ps:PAINTSTRUCT
    LOCAL hdc:DWORD
    LOCAL i:DWORD
    LOCAL x1:DWORD
    LOCAL y1:DWORD
    LOCAL x2:DWORD
    LOCAL y2:DWORD
    LOCAL cosT:DWORD
    LOCAL sinT:DWORD
    LOCAL cos2T:DWORD
    LOCAL sin2T:DWORD
    LOCAL tempX:DWORD
    LOCAL tempY:DWORD
    LOCAL index2:DWORD
    
    invoke BeginPaint, hwnd, addr ps
    mov hdc, eax
    
    invoke MoveToEx, hdc, 0, 70, NULL
    invoke LineTo, hdc, 180, 70    
    invoke MoveToEx, hdc, 100, 0, NULL
    invoke LineTo, hdc, 100, 150   
    
    ; x = (2*cos(t) - 3*cos(2*t)) * 15
    ; y = (2*sin(t) - 3*sin(2*t)) * 15
    mov i, 0
    FirstCurveLoop:
        mov eax, i
        and eax, 127         
        shl eax, 2         
        mov ebx, cos_table[eax]
        mov cosT, ebx
        mov ebx, sin_table[eax]
        mov sinT, ebx
        
        mov eax, i
        shl eax, 1          
        and eax, 127         
        shl eax, 2           
        mov ebx, cos_table[eax]
        mov cos2T, ebx
        mov ebx, sin_table[eax]
        mov sin2T, ebx
        
        mov eax, cosT
        shl eax, 1           
        mov ebx, cos2T
        mov ecx, ebx
        shl ebx, 1
        add ebx, ecx       
        sub eax, ebx       
        mov ebx, 15
        imul ebx             
        mov ebx, 1000
        cdq
        idiv ebx             
        add eax, 100       
        mov x1, eax
        add eax, 2
        mov x2, eax
        
        mov eax, sinT
        shl eax, 1           ; 2*sin(t)
        mov ebx, sin2T
        mov ecx, ebx
        shl ebx, 1
        add ebx, ecx         ; 3*sin(2*t)
        sub eax, ebx         ; 2*sin(t) - 3*sin(2*t)
        neg eax           
        mov ebx, 15
        imul ebx           
        mov ebx, 1000
        cdq
        idiv ebx            
        add eax, 70          
        mov y1, eax
        add eax, 2
        mov y2, eax
        
        invoke Rectangle, hdc, x1, y1, x2, y2
        
        inc i
        cmp i, 256
        jl FirstCurveLoop
    
    invoke SelectObject, hdc, hPen
    
    ; x = (2*cos(t) - 1.5*cos(2*t)) * 15
    ; y = (2*sin(t) - 1.5*sin(2*t)) * 15
    mov i, 0
    SecondCurveLoop:

        mov eax, i
        and eax, 127          
        shl eax, 2           
        mov ebx, cos_table[eax]
        mov cosT, ebx
        mov ebx, sin_table[eax]
        mov sinT, ebx
        
        mov eax, i
        shl eax, 1
        and eax, 127
        shl eax, 2
        mov ebx, cos_table[eax]
        mov cos2T, ebx
        mov ebx, sin_table[eax]
        mov sin2T, ebx
        
        mov eax, cosT
        shl eax, 1           ; 2*cos(t)
        mov ebx, cos2T
        mov ecx, ebx
        shr ecx, 1           ; cos(2*t)/2
        add ebx, ecx
        sub eax, ebx
        mov ebx, 15
        imul ebx
        mov ebx, 1000
        cdq
        idiv ebx
        add eax, 100
        mov x1, eax
        add eax, 2
        mov x2, eax
        
        mov eax, sinT
        shl eax, 1           ; 2*sin(t)
        mov ebx, sin2T
        mov ecx, ebx
        shr ecx, 1           ; sin(2*t)/2
        add ebx, ecx         ; 1.5*sin(2*t)
        sub eax, ebx         ; 2*sin(t) - 1.5*sin(2*t)
        neg eax            
        mov ebx, 15
        imul ebx           
        mov ebx, 1000
        cdq
        idiv ebx
        add eax, 70
        mov y1, eax
        add eax, 2
        mov y2, eax
        
        invoke Rectangle, hdc, x1, y1, x2, y2
        
        inc i
        cmp i, 256
        ;jl SecondCurveLoop
    
    invoke EndPaint, hwnd, addr ps
    
    ret
    
DlgOnPaint endp

end start
