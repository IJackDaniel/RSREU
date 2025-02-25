text       segment
           assume CS:text,DS:data
begin:      mov AX, data
            mov DS, AX
            
            mov AX, 0
            mov DX, 0
            
            mov DL, len ; длина массива
            mov AL, 0  ; счётчик
            mov SI, 0  ; индекс         
            
            jmp iterations
            ; определить количество нечётных. Если таких нет, то сделать реверс массива
            
iterations:        
    cmp SI, DX
    jge check_count
    
    mov BL, [arr + SI]
    test BL, 1
    jnz odd     ;нечётное
    jz even     ;чётное
    
odd:
    inc AL
    inc SI
    jmp iterations
even:
    inc SI
    jmp iterations
            
check_count:
    mov count, AL
    cmp count, 0
    jle reverse
    jmp end_p

reverse:
    mov SI, 0  ; индекс 1 
      
    mov AX, 0
    mov AL, len
    mov DI, AX
    dec DI  
    
    mov AL, len
    div two
    mov AH, 0
    ; В AL лежит ограничение
    jmp start_reverse

start_reverse:
    cmp SI, AX
    jge check_arr
    
    mov BX, 0 
    mov DX, 0
    mov BL, arr[SI]
    mov DL, arr[DI]
    mov arr[SI], DL
    mov arr[DI], BL
    
    inc SI  
    dec DI
    jmp start_reverse
 
check_arr:
    mov SI, 0  
    mov AX, 0
    mov AL, len 
    mov DX, 0
    jmp start_check

start_check:
    cmp SI, AX
    jge end_p
    
    mov DL, arr[SI]
    inc SI;
    jmp start_check    

end_p:
    mov SI, 0
    
    mov AH,4ch
    int 21h


text       ends

data       segment
        arr db 2, 6, 8, 10, 2       ; Массив чисел
        len db 5                    ; Размер массива 
        count db 0                  ; Счётчик элементов массива 
        two db 2                    ; Просто двойка  
data ends

stk        segment stack
           db 256 dup (0)
stk        ends
           end begin
