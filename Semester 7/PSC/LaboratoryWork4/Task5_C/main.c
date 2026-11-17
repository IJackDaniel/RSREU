
#include <avr/io.h>
#include <stdint.h>

// Практическая работа №4
// Задание №5. Управление тремя светодиодами

int main(void)
{
	// PD0, PD1 - выходы для светодиодов
	DDRD |= (1 << PD0) | (1 << PD1);

	// PD2, PD3, PD4 - входы для кнопок
	DDRD &= ~((1 << PD2) | (1 << PD3) | (1 << PD4));

	// PB5 - выход для светодиода L
	DDRB |= (1 << PB5);

	// Включаем PULL-UP для трех кнопок
	PORTD |= (1 << PD2) | (1 << PD3) | (1 << PD4);

	// Начальное состояние светодиодов - OFF
	PORTD &= ~((1 << PD0) | (1 << PD1));
	PORTB &= ~(1 << PB5);

	uint8_t mask =
	(1 << PD2) | (1 << PD3) | (1 << PD4);

	uint8_t previous = PIND & mask;

	while (1)
	{
		uint8_t current = PIND & mask;

		// Находим новые нажатия (переход 1 -> 0)
		uint8_t pressed = previous & ~current & mask;

		if (pressed)
		{
			current = PIND & mask;
			pressed &= ~current;

			// Кнопка PD2 -> светодиод PD0
			if (pressed & (1 << PD2))
			{
				PORTD ^= (1 << PD0);
			}

			// Кнопка PD3 -> светодиод PD1
			if (pressed & (1 << PD3))
			{
				PORTD ^= (1 << PD1);
			}

			// Кнопка PD4 -> светодиод PB5
			if (pressed & (1 << PD4))
			{
				PORTB ^= (1 << PB5);
			}
		}

		// Запоминаем состояние кнопок
		previous = PIND & mask;
	}
}
