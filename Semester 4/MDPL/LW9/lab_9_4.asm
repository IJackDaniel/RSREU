                                                     stacksg segment para stack 'stack'
        dw  32 DUP(?)
stacksg ends

datasg segment para 'Data'
           
CONAME DB 'SPAAE EXPLORERS INC.'  
PRLINE DB 'SPAAE EXPLORERS INC.'        
           
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
	 lea SI, [CONAME + 2]
	 LODSW 
	 
	 mov [PRLINE + 5], AH
	 mov [PRLINE + 6], AL
	 
	 
	 RET
	
	
	mov	ax,4c00h
	int	21h
end	main     

codes ends