                                                                                                                                                                                       
.data
a db 0
b db 1
c db 0
f db 0
   
.code
              ; (a or not(b) or c) and not(a) and b and c
calc	proc
    mov al, a
    mov bl, b 
    not bl    
    or al, bl
    mov bl, c
    or al, bl
    
    mov bl, a
    not bl
    and al, bl
    mov bl, b
    and al, bl
    mov bl, c
    and al, bl                                                                               
    
     
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
