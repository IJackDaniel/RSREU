; ��� ��� �������� ����� ��������� �� ������� ��������� �������
text       segment
           assume CS:text,DS:data
begin:      mov AX, data
            mov DS, AX
            
            mov CX, 3  ; �����������          
            mov SI, 0  ; ������ i - ������         
            mov DI, 0  ; ������ j - �������
 
count_elem_diagonal:
    mov AX, SI         ; �������� ������ ������ � ax
    mul n              ; �������� ax �� ������ ������ (��������� � ax)
    add AX, DI         ; ��������� ������ �������
    mov BX, AX         ; �������� ��������� � bx
    mov AH, array[BX]  ; �������� ������� �������
    
    add count, AH
    
    inc SI
    inc DI
    
    loop count_elem_diagonal
           
 

end_p:
    mov SI, 0
    
    mov AH,4ch
    int 21h


text       ends

data       segment
        array   db 1, 2, 3
                db 4, 5, 6
                db 7, 8, 9     
        count db 0
        n db 3  
data ends

stk        segment stack
           db 256 dup (0)
stk        ends
           end begin