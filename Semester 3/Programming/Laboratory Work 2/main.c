// Цель лабораторной работы - знакомство с командами ветвления и циклами на языке ассемблера
// Вариант 2
// Переменные:
// A, B, C - целые числа (int), вводимые пользователем
// res - целое число (int) - результат вычислений
// Работу выполнил студент группы 3413: Афонин Даниил Олегович
// Дата: 22.09.2024
#include <stdio.h>

int main(int argc, char** argv) {
	// Объявление всех переменных
  	int A, B, C, res;
  	
  	// Получение значений
  	printf("Input A, B, C values:\n");
  	scanf("%d %d %d", &A, &B, &C);
  	
  	// Проверка значений по ОДЗ
  	if (A + B * C < 0 && A - B == 0) {
  		printf("\nUncorrect input! Devision by zero!");
  		return 1;
	  }
  	
  	// Эхо-вывод A, B, C, D, E, F
  	printf("\nEcho output:\n");
  	printf("A = %d; B = %d; C = %d;\n", A, B, C);
  	// Вывод формулы
  	printf("\nFormula:\n");
  	printf("Z = A + B * C\n");
  	printf("If Z >= 0: Calculate the sum of the squares of the numbers from A to B\n");
  	printf("Else: C / (A - B) + A\n");
  	
  	// Ассемблерная вставка 
  	asm("movl %1, %%eax;"			// C -> EAX
  		"imull %2;"					// EAX * B -> EAX
  		"add %3, %%eax;"			// A + EAX -> EAX
  		"movl %%eax, %0;"			// EAX -> res
  		
  		"movl $0, %%ebx;"			// 0 -> EBX
  		
  		"cmp %%ebx, %0;"			// Compare EBX and res
  		"jl less_than;"				// if Z < 0, jump to "less_than"
  		
  		// Z >= 0
  		// Блок расчёта суммы квадратов чисел от A до B
  		"movl $0, %%ebx;"			// 0 -> EBX
		"movl %3, %%eax;"			// A -> ECX
		"cmp %2, %3;"				// Compare A and B
		"jle sum_square;"			// if A <= B
		
		"movl %%ebx, %0;"			// EBX -> res
		"jmp exit;"
  		
  		// Z < 0
  		// Блок расчёта C/(A-B) + A
  		"less_than:"
			"movl %3, %%eax;"		// A -> EAX
			"subl %2, %%eax;"		// EAX - B -> EAX
			"movl %%eax, %%ebx;"	// EAX -> EBX
			
			"movl %4, %%eax;"		// C -> EAX
			"cdq;"
			"idivl %%ebx;"			// EAX / EBX -> EAX
			
			"addl %3, %%eax;"		// A + EAX -> EAX
			
			"movl %%eax, %0;"		// EAX -> res
			"jmp exit;"
  		
  		"sum_square:"
  			"movl %3, %%eax;"		// A -> EAX
  			"imull %%eax;"			// EAX * EAX -> EAX
  			"addl %%eax, %%ebx;"	// EAX + EBX -> EBX
  			
  			"incl %3;"				// A = A + 1
  			
  			"cmp %2, %3;"			// Compare A and B
			"jle sum_square;"		// if A <= B
  			
  			"movl %%ebx, %0;"		// EBX -> res
  			"jmp exit;"
  		
  		"exit:"
		: "=r" (res)
		: "r" (C), "r" (B), "r" (A), "r" (C)
		: "%eax", "%ebx", "%ecx", "%edx"
	);
  	// Вывод результата расчёта на языке ассемблера
    printf("\nResult on Assembler: %d", res);
    // Расчёт значения выражения на С
    res = 0;
    int Z =  A + B * C;
    if (Z >= 0) {
		while (A <= B) {
			res = res + A * A;
			A++;
		}
	} else {
		res = C / (A - B) + A;
	}
    // Вывод результата расчёта на языке С
    printf("\nResult on C: %d", res);
  	
    return 0;
}

