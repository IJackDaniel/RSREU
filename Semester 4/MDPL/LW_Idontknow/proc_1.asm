                                                                             
.data
n1 db 3
n2 db 10
res db 0
   
.code

calc	proc
    mov al, n1
    mov bl, n2
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
