                                                                             
.data
big db 10
small db 3
res db 0
   
.code

calc	proc
    mov al, big
    mov bl, small
cont:      
    sub al, bl
    mov res, al
    
    
    
    
exit:
    ret 
 
main:
	mov	dx,@data
	mov	ds,dx
	call	calc
	mov	ax,4c00h
	int	21h
end	main
