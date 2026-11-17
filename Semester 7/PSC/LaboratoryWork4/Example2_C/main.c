
#include <avr/io.h>
#include <stdint.h>

// Практическая работа №4
// Пример №2. Мигание светодиода

int main(void)
{
	uint32_t x = 0;     // Счетчик
	uint8_t flag = 0;   // Состояние светодиода

	DDRB = 0x20;        // PB5 - выход

	while (1)
	{
		if (x == 800000UL)
		{
			if (flag == 0)
			{
				PORTB |= (1 << PB5);  // Включить LED
				flag = 1;
			}
			else
			{
				PORTB &= ~(1 << PB5); // Выключить LED
				flag = 0;
			}

			x = 0;  // Сброс счетчика
		}

		x++;
	}
}
