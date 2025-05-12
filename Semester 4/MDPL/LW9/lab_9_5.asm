stacksg segment para stack 'stack'
        dw  32 DUP(?)
stacksg ends

datasg segment para 'Data'
           
CONAME DB 'SPACE EXPLORERS INC.'  
PRLINE DB 'SPACE EXPLORERS INC.' 
ANS    DB   0     
           
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
	 mov cx, 20
	 LEA SI, CONAME
	 LEA DI, PRLINE
	 REPE CMPSB
	 JNE    NE 
	 JMP END 
	 
NE:
    MOV ANS, 1  
END:
	
	mov	ax,4c00h
	int	21h
end	main     

codes ends