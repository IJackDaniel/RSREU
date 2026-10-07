;
; LaboratoryWork2.asm
;
; Created: 06.10.2026 22:17:00
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

    ;ldi r16, 0b00100101
	;mov r5, r16
	;call pp2

    ;call pp3
	;call pp4
	;call pp5
	;call pp6
	;call pp7
	;call pp8


    jmp m

pp1:
    ldi r16, 0x0f
    ldi r17, 0x45
    ldi r18, 0x67
    ldi r19, 0x89
    ldi r20, 0x18

    and r17, r16
    andi r17, 0b00000010
    or r18, r16
    tst r16
    eor r19, r16
    eor r19, r19

    mov r17, r20
    and r17, r16
    swap r16
    and r18, r16
    or r17, r18

    bst r16, 7
    bld r20, 2

    sei
    cli
    ret

pp2:
    mov r16, r5
    andi r16, 0b11110000
    cpi r16, 0b00110000
    ret

pp3:
    ; Тестовое значение
    ldi r16, 0xFF

    ; Копии исходного числа
    mov r17, r16
    mov r18, r16
    mov r19, r16
    mov r20, r16
    mov r21, r16

    ; Логический сдвиг вправо
    lsr r17
	lsr r17

    ; Логический сдвиг влево
    lsl r18
	lsl r18

    ; Арифметический сдвиг вправо
    asr r19
	asr r19

    ; Циклический сдвиг вправо через C
    clc
    ror r20
	ror r20

    ; Циклический сдвиг влево через C
    clc
    rol r21
	rol r21

    ret

pp4:
    ldi r16, 62     ; A
    ldi r17, 102    ; B
    ldi r18, 128    ; C
    ldi r19, 232    ; D

    add r16, r17    ; A + B
    add r17, r18    ; B + C
    add r18, r19    ; C + D

    ret

pp5:
    ldi r16, 250
    ldi r17, 200

    mov r0, r16
    add r0, r17
    ror r0

    ret

pp6:
    ldi r16, 250
    ldi r17, 200
    ldi r18, 250
    ldi r19, 200

    eor r1, r1      ; r1 = 0, старший байт суммы
    eor r2, r2      ; r2 = 0, нужен для adc
    mov r0, r16     ; начинаем сумму с 250

    add r0, r17
    adc r1, r2

    add r0, r18
    adc r1, r2

    add r0, r19
    adc r1, r2

    ; деление двухбайтной суммы на 4
    ror r1
    ror r0
    ror r1
    ror r0

    ret

pp7:
    ; A * B
    ldi r16, 62
    ldi r17, 102
    mul r16, r17
    ; результат в r1:r0

    ; B * C
    ldi r16, 102
    ldi r17, 128
    mul r16, r17
    ; результат в r1:r0

    ; C * D
    ldi r16, 128
    ldi r17, 232
    mul r16, r17
    ; результат в r1:r0

    ret

pp8:
    ; +20 * +10 = +200
    ldi r16, 20
    ldi r17, 10
    muls r16, r17
    ; r1:r0 = 0x00C8

    ; +20 * -10 = -200
    ldi r16, 20
    ldi r17, 246      ; -10 в дополнительном коде
    muls r16, r17
    ; r1:r0 = 0xFF38

    ; -20 * -10 = +200
    ldi r16, 236      ; -20 в дополнительном коде
    ldi r17, 246      ; -10
    muls r16, r17
    ; r1:r0 = 0x00C8

    ret