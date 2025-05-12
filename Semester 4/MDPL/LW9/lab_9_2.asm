stacksg segment para stack 'stack'
        dw  32 DUP(?)
stacksg ends

datasg segment para 'Data'
           
CONAME DB 'SPACE EXPLORERS INC.'  
PRLINE DB 20 DUP(' ')       
           
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
	
	 STD
	 mov cx, 20
	 lea DI,[PRLINE + 19]
	 lea SI, [CONAME + 19]
	 REP MOVSB
	
	
	mov	ax,4c00h
	int	21h
end	main     

codes ends