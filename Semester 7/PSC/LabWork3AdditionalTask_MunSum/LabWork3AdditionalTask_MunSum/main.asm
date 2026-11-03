;
; LabWork3AdditionalTask_MinSum.asm
;
; Created: 09.10.2026 14:21:17
; Author : Даниил
; Group  : 3413
; Brigade: 2 (Афонин Д.О. и Барышев Г.А.)
;
; Задание:
; Сформировать массив из 20 знаковых чисел и найти пару элементов,
; для которой |a[i] + a[j]| имеет минимальное значение.
;
; Массив:
; SRAM 0x0100 - 0x0113
;
; Результат:
; r4 - первый элемент пары
; r5 - второй элемент пары
; r7:r6 - модуль суммы
;

.include "m328pdef.inc"

; Инициализация стека
ldi r16, 0x08
out sph, r16
ldi r16, 0xFF
out spl, r16

main:
    call fill_array
    call find_pair

stop:
    rjmp stop

; Заполнение массива из 20 элементов
fill_array:
    ldi xh, 0x01
    clr xl                  ; X = 0x0100

    ldi r18, 20             ; количество элементов
    ldi r16, 73             ; начальное значение для генератора значений

fill_loop:
    st x+, r16

    ; Следующее значение
    subi r16, -37

    dec r18
    brne fill_loop

    ret

; Поиск пары с суммой, ближайшей к 0
find_pair:

    ; Начальное минимальное значение 0x7FFF
    ldi r18, 0xFF
    mov r6, r18

    ldi r18, 0x7F
    mov r7, r18

    ; X указывает на первый элемент массива
    ldi xh, 0x01
    clr xl                  ; X = 0x0100

    ; Для первого элемента остаётся 19 пар
    ldi r20, 19

outer_loop:

    ; Первый элемент пары
    ld r16, X

    ; Y = X
    mov yl, xl
    mov yh, xh

    ; Y указывает на следующий элемент
    inc yl

    ; Количество элементов для внутреннего цикла
    mov r21, r20

inner_loop:

    ; Второй элемент пары
    ld r17, Y+

    ; Знаковое расширение первого числа
    mov r18, r16
    clr r19

    tst r16
    brpl first_positive

    ser r19

first_positive:

    ; Знаковое расширение второго числа
    clr r22

    tst r17
    brpl second_positive

    ser r22

second_positive:

    ; 16-битная сумма
    add r18, r17
    adc r19, r22

    ; Проверка знака суммы
    tst r19
    brpl abs_ready

    ; Если сумма отрицательная,
    ; получаем её модуль
    com r18
    com r19

    inc r18
    brne abs_ready

    inc r19

abs_ready:

    ; Сначала сравниваем старшие байты
    cp r19, r7
    brcs save_pair
    brne next_pair

    ; Если старшие байты равны,
    ; сравниваем младшие
    cp r18, r6
    brcs save_pair

    rjmp next_pair

save_pair:

    ; Новый минимальный модуль суммы
    mov r6, r18
    mov r7, r19

    ; Сохраняем элементы найденной пары
    mov r4, r16
    mov r5, r17

    ; Если сумма равна 0,
    ; лучше пары уже не найти
    tst r6
    brne next_pair

    tst r7
    breq finish

next_pair:

    dec r21
    brne inner_loop

    ; Следующий первый элемент
    inc xl

    dec r20
    brne outer_loop

finish:
    ret