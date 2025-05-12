stacksg segment para stack 'stack'
        dw  32 DUP(?)
stacksg ends

datasg segment para 'Data'
           
CONAME DB 'SPACE EXPLORERS INC.'  
PRLINE DB 20 DUP(' ')       
           
datasg ends



codesg segment para 'Code'

main:
	assume CS:CODESG,DS:DATASG,SS:STACKSG,ES:DATASG                         5
	PUSH DS
	SUB AX,AX
	PUSH AX
	MOV AX, DATASG
	MOV DS,AX
	MOV ES,AX
	
	 cld
	 mov cx, 20
	 lea DI, PRLINE
	 lea SI, CONAME
	 REP MOVSB
	
	
	mov	ax,4c00h
	int	21h
end	main     

codes ends