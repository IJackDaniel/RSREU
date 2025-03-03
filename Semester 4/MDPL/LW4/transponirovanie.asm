; Код для подсчёта суммы элементов на главной диагонали матрицы
text       segment
           assume CS:text,DS:data
begin:      mov AX, data
            mov DS, AX
            
            ; Инициализация указателей
            mov si, matrix             ; SI указывает на начало исходной матрицы
            mov di, transposed_matrix  ; DI указывает на начало результирующей матрицы

    ; Транспонирование матрицы
    mov cx, 3                  ; Количество строк и столбцов (матрица 3x3)
outer_loop:
    mov bx, 0                  ; Индекс столбца
inner_loop:
    ; Загрузка элемента из исходной матрицы
    mov al, [si + bx]          ; AL = matrix[row][col]

    ; Сохранение элемента в транспонированную матрицу
    mov [di + bx * 3], al      ; transposed_matrix[col][row] = AL

    ; Переход к следующему столбцу
    inc bx
    cmp bx, 3
    jl inner_loop              ; Повторять, пока не пройдем все столбцы

    ; Переход к следующей строке
    add si, 3                  ; Переход к следующей строке исходной матрицы
    inc di                     ; Переход к следующей строке транспонированной матрицы

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