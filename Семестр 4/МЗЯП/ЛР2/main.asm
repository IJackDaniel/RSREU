text       segment
           assume CS:text,DS:data
begin:      mov AX, data
            mov DS, AX
                      
            mov AL, c
            mov z, AL
            
            cmp z, 0
            jl less
            jmp more

less:
        mov AL, a
        sub AL, b  
        mov elem1, AL
        
        mov AL, b
        add AL, c   
        mov AH, 0 
        idiv w  
        mov AH, 0 
        mov DX, AX
        
        mov AX, 0
        mov AL, c
        imul w;
        
        add AX, DX
        mov DL, AL
        mov AL, elem1
        add AL, DL
        
        mov result, AL
        
        jmp end_p  
more:          
        mov AX, 0
        mov AL, a
        add AL, b
        mov DL, AL
        
        mov AX, 0
        mov AL, a
        add AL, b
        mul a
        
        div DL
        
        mov AH, 0
        
        mov DL, AL
        
        mov AX, 0
        mov AL, f
        sub AL, DL
        add AL, c
        mov result, AL
           
        
        
        jmp end_p  

end_p:
    mov AH,4ch
    int 21h


text       ends

data       segment
        num db 10  
        w db 2
        c db 2
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
