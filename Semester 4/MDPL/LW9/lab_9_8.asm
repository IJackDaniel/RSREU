stacksg segment para stack 'stack'
        dw  32 DUP(?)
stacksg ends

datasg segment para 'Data'
           
PATTERN   DB     '03', '04', '05', 'B4'   
RESULT    DB     81 DUP(?) 
           
datasg ends



codesg segment para 'Code'

main:
	assume CS:CODESG,DS:DATASG,SS:STACKSG,ES:DATASG
	PUSH DS
	SUB AX,AX
	PUSH AX
	MOV AX, DATASG
	MOV DS,AX
	MOV ES,AX
	 
             
    cld
    mov cx, 40 
    LEA DI, RESULT
    LEA SI, PATTERN
    REP MOVSW 
    MOV RESULT[80], '$'
    
    MOV AH, 09h
    MOV DX, OFFSET RESULT
    int 21H 
    
	    
	  
END:
	
	mov	ax,4c00h
	int	21h
end	main     

codes ends