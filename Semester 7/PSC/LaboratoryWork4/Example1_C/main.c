
#include <avr/io.h>
#include <stdint.h>

int main(void)
{
	uint8_t x = 0;   // Счетчик от 0 до 255

	DDRD = 0xFF;     // Все выводы порта D - OUTPUT

	while (1)
	{
		PORTD = x;   // Вывод текущего значения
		x++;         // Увеличение счетчика
	}
}
