; ��� ��� �������� ����� ��������� �� ������� ��������� �������
text       segment
           assume CS:text,DS:data
begin:      mov AX, data
            mov DS, AX
            
            ; ������������� ����������
            mov si, matrix             ; SI ��������� �� ������ �������� �������
            mov di, transposed_matrix  ; DI ��������� �� ������ �������������� �������

    ; ���������������� �������
    mov cx, 3                  ; ���������� ����� � �������� (������� 3x3)
outer_loop:
    mov bx, 0                  ; ������ �������
inner_loop:
    ; �������� �������� �� �������� �������
    mov al, [si + bx]          ; AL = matrix[row][col]

    ; ���������� �������� � ����������������� �������
    mov [di + bx * 3], al      ; transposed_matrix[col][row] = AL

    ; ������� � ���������� �������
    inc bx
    cmp bx, 3
    jl inner_loop              ; ���������, ���� �� ������� ��� �������

    ; ������� � ��������� ������
    add si, 3                  ; ������� � ��������� ������ �������� �������
    inc di                     ; ������� � ��������� ������ ����������������� �������

    loop outer_loop               
 

end_p:
    mov SI, 0
    
    mov AH,4ch
    int 21h


text       ends

data       segment
        array   db 1, 2, 3
                db 4, 5, 6
                db 7, 8, 9
        transposed_matrix db 9 dup(0)     
data ends

stk        segment stack
           db 256 dup (0)
stk        ends
           end begin