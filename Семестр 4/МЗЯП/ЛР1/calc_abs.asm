text       segment
           assume CS:text,DS:data
begin:      mov AX, data
            mov DS, AX
            
            cmp num, 0
            jl reverse  
            jmp end_p   

reverse:
           neg num
           jmp end_p

end_p:
    mov AH,4ch
    int 21h


text       ends

data       segment
        num db 10 
data ends

stk        segment stack
           db 256 dup (0)
stk        ends
           end begin
