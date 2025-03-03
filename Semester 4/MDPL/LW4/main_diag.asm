; Код для подсчёта суммы элементов на главной диагонали матрицы
text       segment
           assume CS:text,DS:data
begin:      mov AX, data
            mov DS, AX
            
            mov CX, 3  ; Размерность          
            mov SI, 0  ; Индекс i - строка         
            mov DI, 0  ; Индекс j - столбец
 
count_elem_diagonal:
    mov AX, SI         ; Копируем индекс строки в ax
    mul n              ; Умножаем ax на размер строки (результат в ax)
    add AX, DI         ; Добавляем индекс столбца
    mov BX, AX         ; Копируем результат в bx
    mov AH, array[BX]  ; Получаем элемент массива
    
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