text       segment
           assume CS:text,DS:data
begin:      mov AX, data
            mov DS, AX
                      
            mov AL, c
            mov z, AL    ; z = w + c - w = c
            
            cmp z, 0     ; сравнение z и 0
            jl less      ; переход на less, если z < 0
            jmp more     ; переход на more, если z >= 0

less:
        mov AX, 0        ; AX = 0
        mov AL, a        ; a -> AL
        sub AL, b        ; AL - b  -> AL
        mov elem1, AL    ; AL -> elem1
        
        mov AL, b        ; b -> AL
        add AL, c        ; AL + c -> AL
        mov AH, 0        ; AH = 0
        idiv w           ; AL / w -> AX
        mov AH, 0        ; AH -> 0
        mov DX, AX       ; AX -> DX
        
        mov AX, 0        ; AX = 0
        mov AL, c        ; c -> AL
        imul w;          ; AL * w -> AX
        
        add AX, DX       ; AX + DX -> AX
        mov DL, AL       ; AL -> DL
        mov AL, elem1    ; elem1 -> AL
        add AL, DL       ; DL -> AL
        
        mov result, AL   ; AL -> result
        
        jmp end_p        ; условный переход на прерывание
more:          
        mov AX, 0        ; AX = 0
        mov AL, a        ; a -> AL
        add AL, b        ; AL + b -> AL
        mov DL, AL       ; AL -> DL
        
        mov AX, 0        ; AX = 0
        mov AL, a        ; a -> AL
        add AL, b        ; AL + b -> AL
        mul a            ; AL * a -> AX
        
        div DL           ; AL / DL -> AL
        
        mov AH, 0        ; AH = 0
        
        mov DL, AL       ; AL -> DL
        
        mov AX, 0        ; AH = 0
        mov AL, f        ; f -> AL
        sub AL, DL       ; AL - DL -> AL
        add AL, c        ; AL + c -> AL
        mov result, AL   ; AL -> result
           
        
        
        jmp end_p        ; условный переход на прерывание

end_p:
    mov AH,4ch
    int 21h


text       ends

data       segment
        num db 10  
        w db 2
        ;c db 2
        c db -2
        z db 0
        a db 9
        b db 12
        f db 6
        n db 0
        
        elem1 db 0  
        
        result db 0
data ends

stk        segment stack
           db 256 dup (0)
stk        ends
           end begin
