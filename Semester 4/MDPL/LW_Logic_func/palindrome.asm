                                                                                                                                                                                       
.data
a db 0
b db 0
c db 1
f1 db 0
f2 db 0 
f db 0
   
.code

calc	proc
    mov al, a
    mov bl, b 
    or al, bl
    not al
    mov cl, al                                                                               
    
    mov al, a
    mov bl, c 
    or al, bl 
    not al
    
    mov bl, cl 
    mov f1, bl
    mov f2, al
     
    xor al, bl
    mov f, al
    
exit:
    ret 

main:
	mov	dx,@data
	mov	ds,dx
	call	calc
	mov	ax,4c00h
	int	21h
end	main
