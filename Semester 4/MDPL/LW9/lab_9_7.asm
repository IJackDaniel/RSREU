stacksg segment para stack 'stack'
        dw  32 DUP(?)
stacksg ends

datasg segment para 'Data'
           
PRLINE DB 'eirjhdrfed'   
ANS    DB   0
I      db   0     
           
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
	mov cx, 10
	LEA DI, PRLINE
	mov AL, 'e'
LOOP1:
	REPNE SCASB
	JE FIND1 
	jmp END
	
FIND1:
    cmp cx, 0
    jle END         
             
    mov BX, DI
    mov DH, PRLINE[BX]
    
    
    
    mov DL, 'r' 
    cmp DL, DH
    mov DI, BX
    je FIND2 
    jmp LOOP1
    
FIND2:
    mov ANS, 1
	    
	  
END:
	
	mov	ax,4c00h
	int	21h
end	main     

codes ends