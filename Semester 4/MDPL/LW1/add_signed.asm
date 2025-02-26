text       segment
           assume CS:text,DS:data
begin:     mov AX,data
           mov DS,AX
           mov	al,summand_2
           add	summand_1, al
           jc	@@cf1_of1
	       jo	@@cf0_of1
r_true:	jmp	end_p	;результат -> summand_1
@@cf1_of1:
	jno	@@cf1_of0
;cf=1 of=1 -> результат неверный
	mov	carry,0ffh	;расширение знака д.б. =1, результат ->sum_w
	jmp	end_p
@@cf1_of0:
;cf=1 of=0 -> результат верный
	jmp	r_true	;результат -> summand_1
@@cf0_of1:
;cf=0 of=1 -> результат неверный
	mov	carry,0	;расширение знака д.б. =0, результат ->sum_w
	jmp	end_p
end_p:
    mov AH,4ch
    int 21h

text       ends

data       segment
            summand_1 db -3
            summand_2 db 10
            sum_w label word
            sum_b db 0
            carry db 0
data ends

stk        segment stack
           db 256 dup (0)
stk        ends
           end begin
