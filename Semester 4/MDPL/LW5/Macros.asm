text       segment
           assume CS:text, DS:data
           
INIT MACRO data_segment
    mov AX, data_segment
    mov DS, AX
    mov AX, 0
ENDM

END_PROG MACRO
    mov AH,4Ch
    int 21h    
ENDM

PRINT_STRING MACRO msg_outp
    mov dx, offset msg_outp
    mov ah, 09h 
    int 21h
ENDM

begin:    
    INIT data
    mov AX, 0
    mov AL, 3
 
    ;mov msg[9], "L"
    ;mov msg[10], "O"
    ;mov msg[11], "L"
    PRINT_STRING msg   
           
    END_PROG
                                               
text ends
  
  
data       segment 
     ;msg dw "                $"
     msg dw "Print any string$"
     a db 3
     b db 2                                    
data       ends


end begin  
