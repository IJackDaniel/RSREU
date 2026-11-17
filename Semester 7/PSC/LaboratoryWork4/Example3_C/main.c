
#include <avr/io.h>

// Практическая работа №4
// Пример №3. Управление LED кнопкой

int main(void)
{
	DDRB = 0x20;          // PB5 - выход, PB0 - вход
	PORTB |= (1 << PB0); // PULL-UP на PB0

	while (1)
	{
		if ((PINB & (1 << PB0)) == 0)
		{
			PORTB |= (1 << PB5);    // LED ON
		}
		else
		{
			PORTB &= ~(1 << PB5);   // LED OFF
		}
	}
}
