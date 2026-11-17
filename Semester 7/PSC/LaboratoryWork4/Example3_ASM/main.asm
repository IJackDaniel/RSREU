; Практическая работа №4
; Пример №3. Управление LED кнопкой

start:
    ldi r16, 0x20
    out DDRB, r16      ; PB5 - выход, PB0 - вход

    ldi r16, 0x01
    out PORTB, r16     ; Включаем PULL-UP на PB0

loop:
    in r17, PINB       ; Читаем состояние порта B
    bst r17, 0         ; Копируем бит PB0 во флаг T

    brtc led_on        ; T = 0: кнопка нажата

led_off:
    cbi PORTB, 5       ; Выключить светодиод
    rjmp loop

led_on:
    sbi PORTB, 5       ; Включить светодиод
    rjmp loop
