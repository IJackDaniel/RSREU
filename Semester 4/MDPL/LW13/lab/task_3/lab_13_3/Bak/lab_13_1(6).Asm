.386
.model flat, stdcall
option casemap:none

; ��������� ��� �������
IDD_DIALOG equ 500

; ����������� ����������� ���������
include \masm32\include\windows.inc
include \masm32\include\user32.inc
include \masm32\include\kernel32.inc
include \masm32\include\gdi32.inc

includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\gdi32.lib

; ��������� �������
DlgProc PROTO :DWORD, :DWORD, :DWORD, :DWORD
DlgOnInitDialog PROTO :DWORD, :DWORD, :DWORD
DlgOnCommand PROTO :DWORD, :DWORD, :DWORD, :DWORD
DlgOnPaint PROTO :DWORD

; ���������
IDCANCEL equ 2
WM_INITDIALOG equ 110h
WM_COMMAND equ 111h
WM_PAINT equ 0Fh
PS_SOLID equ 0

.data
    hPen dd 0                   ; ���������� �������� ����
    
    ; ������� ������� � ��������� (���������� �� 1000 ��� ��������)
    ; ��� ����� �� 0 �� 2*PI � ����� PI/128 (256 �����)
    cos_table dd 1000, 1000, 999, 999, 998, 996, 995, 993, 990, 988, 985, 982, 978, 974, 970, 965
          dd 961, 956, 951, 946, 940, 934, 928, 921, 914, 906, 898, 891, 882, 874, 866, 857
          dd 848, 839, 829, 819, 809, 799, 788, 777, 766, 755, 743, 731, 719, 707, 695, 682
          dd 669, 656, 643, 629, 616, 602, 588, 574, 559, 545, 530, 515, 500, 485, 469, 454
          dd 438, 423, 407, 391, 375, 358, 342, 326, 309, 292, 276, 259, 242, 225, 208, 191
          dd 174, 156, 139, 122, 105, 87, 70, 52, 35, 17, 0, -17, -35, -52, -70, -87
          dd -105, -122, -139, -156, -174, -191, -208, -225, -242, -259, -276, -292, -309, -326, -342, -358
          dd -375, -391, -407, -423, -438, -454, -469, -485, -500, -515, -530, -545, -559, -574, -588, -602
          dd -616, -629, -643, -656, -669, -682, -695, -707, -719, -731, -743, -755, -766, -777, -788, -799
          dd -809, -819, -829, -839, -848, -857, -866, -874, -882, -891, -898, -906, -914, -921, -928, -934
          dd -940, -946, -951, -956, -961, -965, -970, -974, -978, -982, -985, -988, -990, -993, -995, -996
          dd -998, -999, -999, -1000, -1000, -1000, -999, -999, -998, -996, -995, -993, -990, -988, -985, -982
          dd -978, -974, -970, -965, -961, -956, -951, -946, -940, -934, -928, -921, -914, -906, -898, -891
          dd -882, -874, -866, -857, -848, -839, -829, -819, -809, -799, -788, -777, -766, -755, -743, -731
          dd -719, -707, -695, -682, -669, -656, -643, -629, -616, -602, -588, -574, -559, -545, -530, -515
          dd -500, -485, -469, -454, -438, -423, -407, -391, -375, -358, -342, -326, -309, -292, -276, -259
          dd -242, -225, -208, -191, -174, -156, -139, -122, -105, -87, -70, -52, -35, -17, 0, 17
          dd 35, 52, 70, 87, 105, 122, 139, 156, 174, 191, 208, 225, 242, 259, 276, 292
          dd 309, 326, 342, 358, 375, 391, 407, 423, 438, 454, 469, 485, 500, 515, 530, 545
          dd 559, 574, 588, 602, 616, 629, 643, 656, 669, 682, 695, 707, 719, 731, 743, 755
          dd 766, 777, 788, 799, 809, 819, 829, 839, 848, 857, 866, 874, 882, 891, 898, 906
          dd 914, 921, 928, 934, 940, 946, 951, 956, 961, 965, 970, 974, 978, 982, 985, 988
          dd 990, 993, 995, 996, 998, 999, 999, 1000

    sin_table dd 0, 17, 35, 52, 70, 87, 105, 122, 139, 156, 174, 191, 208, 225, 242, 259
          dd 276, 292, 309, 326, 342, 358, 375, 391, 407, 423, 438, 454, 469, 485, 500, 515
          dd 530, 545, 559, 574, 588, 602, 616, 629, 643, 656, 669, 682, 695, 707, 719, 731
          dd 743, 755, 766, 777, 788, 799, 809, 819, 829, 839, 848, 857, 866, 874, 882, 891
          dd 898, 906, 914, 921, 928, 934, 940, 946, 951, 956, 961, 965, 970, 974, 978, 982
          dd 985, 988, 990, 993, 995, 996, 998, 999, 999, 1000, 1000, 1000, 999, 999, 998, 996
          dd 995, 993, 990, 988, 985, 982, 978, 974, 970, 965, 961, 956, 951, 946, 940, 934
          dd 928, 921, 914, 906, 898, 891, 882, 874, 866, 857, 848, 839, 829, 819, 809, 799
          dd 788, 777, 766, 755, 743, 731, 719, 707, 695, 682, 669, 656, 643, 629, 616, 602
          dd 588, 574, 559, 545, 530, 515, 500, 485, 469, 454, 438, 423, 407, 391, 375, 358
          dd 342, 326, 309, 292, 276, 259, 242, 225, 208, 191, 174, 156, 139, 122, 105, 87
          dd 70, 52, 35, 17, 0, -17, -35, -52, -70, -87, -105, -122, -139, -156, -174, -191
          dd -208, -225, -242, -259, -276, -292, -309, -326, -342, -358, -375, -391, -407, -423, -438, -454
          dd -469, -485, -500, -515, -530, -545, -559, -574, -588, -602, -616, -629, -643, -656, -669, -682
          dd -695, -707, -719, -731, -743, -755, -766, -777, -788, -799, -809, -819, -829, -839, -848, -857
          dd -866, -874, -882, -891, -898, -906, -914, -921, -928, -934, -940, -946, -951, -956, -961, -965
          dd -970, -974, -978, -982, -985, -988, -990, -993, -995, -996, -998, -999, -999, -1000, -1000, -1000
          dd -999, -999, -998, -996, -995, -993, -990, -988, -985, -982, -978, -974, -970, -965, -961, -956
          dd -951, -946, -940, -934, -928, -921, -914, -906, -898, -891, -882, -874, -866, -857, -848, -839
          dd -829, -819, -809, -799, -788, -777, -766, -755, -743, -731, -719, -707, -695, -682, -669, -656
          dd -643, -629, -616, -602, -588, -574, -559, -545, -530, -515, -500, -485, -469, -454, -438, -423
          dd -407, -391, -375, -358, -342, -326, -309, -292, -276, -259, -242, -225, -208, -191, -174, -156
          dd -139, -122, -105, -87, -70, -52, -35, -17

.code

start:
    ; ������ ������� ��� �������� ���� ����������
    invoke GetModuleHandle, NULL
    invoke DialogBoxParam, eax, IDD_DIALOG, NULL, addr DlgProc, NULL
    invoke ExitProcess, 0

; ������� ��������� ��������� ��������� �������
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

; ������������� ����������� ����
DlgOnInitDialog proc hwnd:DWORD, wParam:DWORD, lParam:DWORD
    
    ; ������� ������� ���� ��� ������ �����������
    invoke CreatePen, PS_SOLID, 1, 006464FFh  ; RGB(255, 100, 100)
    mov hPen, eax
    
    mov eax, 1
    ret
    
DlgOnInitDialog endp

; ��������� ������
DlgOnCommand proc hwnd:DWORD, wParam:DWORD, lParam:DWORD, unused:DWORD
    
    mov eax, wParam
    and eax, 0FFFFh
    
    .if eax == IDCANCEL
        invoke EndDialog, hwnd, 0
    .endif
    
    ret
    
DlgOnCommand endp

; ������� ��������� ����������
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
    
    ; �������� ���������
    invoke BeginPaint, hwnd, addr ps
    mov hdc, eax
    
    ; �������� ��� ���������
    invoke MoveToEx, hdc, 0, 70, NULL
    invoke LineTo, hdc, 180, 70     ; ��� X
    invoke MoveToEx, hdc, 100, 0, NULL
    invoke LineTo, hdc, 100, 150    ; ��� Y
    
    ; �������� ������ ����������� (������ ������)
    ; x = (2*cos(t) - 3*cos(2*t)) * 15
    ; y = (2*sin(t) - 3*sin(2*t)) * 15
    mov i, 0
    FirstCurveLoop:
        ; �������� cos(t) � sin(t)
        mov eax, i
        and eax, 255          ; ������������ ������ (0-255)
        shl eax, 2           ; �������� �� 4
        mov ebx, cos_table[eax]
        mov cosT, ebx
        mov ebx, sin_table[eax]
        mov sinT, ebx
        
        ; �������� cos(2*t) � sin(2*t)
        mov eax, i
        shl eax, 1           ; 2*i
        and eax, 255          ; ������������ ������ (0-255)
        shl eax, 2           ; �������� �� 4
        mov ebx, cos_table[eax]
        mov cos2T, ebx
        mov ebx, sin_table[eax]
        mov sin2T, ebx
        
        ; ��������� x = (2*cos(t) - 3*cos(2*t)) * 15 / 1000
        mov eax, cosT
        shl eax, 1           ; 2*cos(t)
        mov ebx, cos2T
        mov ecx, ebx
        shl ebx, 1
        add ebx, ecx         ; 3*cos(2*t)
        sub eax, ebx         ; 2*cos(t) - 3*cos(2*t)
        mov ebx, 15
        imul ebx             ; �������� �� 15
        mov ebx, 1000
        cdq
        idiv ebx             ; ����� �� 1000
        add eax, 100         ; �������� �� X
        mov x1, eax
        add eax, 2
        mov x2, eax
        
        ; ��������� y = -(2*sin(t) - 3*sin(2*t)) * 15 / 1000
        mov eax, sinT
        shl eax, 1           ; 2*sin(t)
        mov ebx, sin2T
        mov ecx, ebx
        shl ebx, 1
        add ebx, ecx         ; 3*sin(2*t)
        sub eax, ebx         ; 2*sin(t) - 3*sin(2*t)
        neg eax              ; �����������
        mov ebx, 15
        imul ebx             ; �������� �� 15
        mov ebx, 1000
        cdq
        idiv ebx             ; ����� �� 1000
        add eax, 70          ; �������� �� Y
        mov y1, eax
        add eax, 2
        mov y2, eax
        
        ; ������ ��������� 2x2 �������
        invoke Rectangle, hdc, x1, y1, x2, y2
        
        inc i
        cmp i, 256
        jl FirstCurveLoop
    
    ; ������������� �� ������� ���� ��� ������ �����������
    invoke SelectObject, hdc, hPen
    
    ; �������� ������ ����������� (������� ������)
    ; x = (2*cos(t) - 1.5*cos(2*t)) * 15
    ; y = (2*sin(t) - 1.5*sin(2*t)) * 15
    mov i, 0
    SecondCurveLoop:
        ; �������� cos(t) � sin(t)
        mov eax, i
        and eax, 255          ; ������������ ������ (0-255)
        shl eax, 2           ; �������� �� 4
        mov ebx, cos_table[eax]
        mov cosT, ebx
        mov ebx, sin_table[eax]
        mov sinT, ebx
        
        ; �������� cos(2*t) � sin(2*t)
        mov eax, i
        shl eax, 1           ; 2*i
        and eax, 255          ; ������������ ������ (0-255)
        shl eax, 2           ; �������� �� 4
        mov ebx, cos_table[eax]
        mov cos2T, ebx
        mov ebx, sin_table[eax]
        mov sin2T, ebx
        
        ; ��������� x = (2*cos(t) - 1.5*cos(2*t)) * 15 / 1000
        mov eax, cosT
        shl eax, 1           ; 2*cos(t)
        mov ebx, cos2T
        mov ecx, ebx
        shr ecx, 1           ; cos(2*t)/2
        add ebx, ecx         ; 1.5*cos(2*t)
        sub eax, ebx         ; 2*cos(t) - 1.5*cos(2*t)
        mov ebx, 15
        imul ebx             ; �������� �� 15
        mov ebx, 1000
        cdq
        idiv ebx             ; ����� �� 1000
        add eax, 100         ; �������� �� X
        mov x1, eax
        add eax, 2
        mov x2, eax
        
        ; ��������� y = -(2*sin(t) - 1.5*sin(2*t)) * 15 / 1000
        mov eax, sinT
        shl eax, 1           ; 2*sin(t)
        mov ebx, sin2T
        mov ecx, ebx
        shr ecx, 1           ; sin(2*t)/2
        add ebx, ecx         ; 1.5*sin(2*t)
        sub eax, ebx         ; 2*sin(t) - 1.5*sin(2*t)
        neg eax              ; �����������
        mov ebx, 15
        imul ebx             ; �������� �� 15
        mov ebx, 1000
        cdq
        idiv ebx             ; ����� �� 1000
        add eax, 70          ; �������� �� Y
        mov y1, eax
        add eax, 2
        mov y2, eax
        
        ; ������ ��������� 2x2 ������� ������� ������
        invoke Rectangle, hdc, x1, y1, x2, y2
        
        inc i
        cmp i, 256
        jl SecondCurveLoop
    
    ; ��������� ���������
    invoke EndPaint, hwnd, addr ps
    
    ret
    
DlgOnPaint endp

end start
