;
; LaboratoryWork3.asm
;
; Created: 07.10.2026 2:24:27
; Author : Даниил
;

.include "m328pdef.inc"

; Инициализация стека
ldi r16, 0x08
out sph, r16
ldi r16, 0xff
out spl, r16

m:
    ;call pp1
    ;call pp2
    ;call pp3
    ;call pp4
    jmp m

pp1:
    ldi xh, 1
    ldi yh, 1
    ldi zh, 1

    ldi xl, 0x00
    ldi yl, 0x10
    ldi zl, 0x20

    ldi r17, 10
    ldi r18, 20

m1:
    st x+, r17
    st y+, r18

    mov r19, r17
    add r19, r18
    st z+, r19

    subi r17, -3
    subi r18, -5

    cpi xl, 0x10
    brne m1

    ret

pp2:
    ldi xh, 0x01
    clr xl

    ldi r16, 1
    ldi r17, 0

m2:
    st x+, r16

    add r17, r16
    brcs m3

    st x+, r17

    add r16, r17
    brcs m3

    rjmp m2

m3:
    ret

pp3:
    ldi xh, 0x01
    clr xl

    clr r16               

m4:
    st x+, r16
    inc r16

    cpi xh, 0x02
    brne m4

    cpi xl, 0x00
    brne m4

    ret

pp4:
    ldi xh, 0x01
    clr xl

    clr r5

m5:
    ld r16, x+

    cp r5, r16
    brcc no_update

    mov r5, r16

no_update:
    cpi xh, 0x02
    brne m5

    cpi xl, 0x00
    brne m5

    ret
