.data

a dw 81
b dw 273
x dw 0


.code  

start:
    push 3                  ;c=3
    push b                  ;b
    push a                  ;a
    call primer             ;????? ?????????
    mov x,ax                ;x=(a+b)/
 
    mov ax,4C00h            ;\
    int 21h 
 
primer:
    push bp                 ;?????????? ???????? BP
    mov bp,sp               ;BP=SP
    push dx
 
    mov ax,[bp+4]           ;AX=a
    add ax,[bp+6]           ;AX=(a+b)
    cwd                     ;DX:AX=(a+b)
 
    pop dx
    pop bp
    ret 6  