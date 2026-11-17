
; Практическая работа №4
; Задание №4. Счетчик с двумя кнопками

start:
    clr r0             ; Начальное значение счетчика = 0

    ldi r16, 0xFF
    out DDRD, r16      ; PORTD - все выводы OUTPUT

    clr r16
    out DDRB, r16      ; PORTB - все выводы INPUT

    ldi r16, 0x03
    out PORTB, r16     ; PULL-UP на PB0 и PB1

    out PORTD, r0      ; Выводим начальное значение

loop:
    in r17, PINB       ; Читаем состояние кнопок

    sbrs r17, 0        ; Если PB0 = 0, кнопка нажата
    rjmp increase

    sbrs r17, 1        ; Если PB1 = 0, кнопка нажата
    rjmp decrease

    rjmp loop          ; Если кнопки не нажаты

increase:
    inc r0             ; Увеличиваем счетчик
    out PORTD, r0      ; Выводим новое значение

wait_inc:
    in r17, PINB
    sbrs r17, 0        ; Ждем отпускания PB0
    rjmp wait_inc
    rjmp loop

decrease:
    dec r0             ; Уменьшаем счетчик
    out PORTD, r0      ; Выводим новое значение

wait_dec:
    in r17, PINB
    sbrs r17, 1        ; Ждем отпускания PB1
    rjmp wait_dec
    rjmp loop
