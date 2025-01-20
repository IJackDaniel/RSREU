#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define LEN(array) array[0]
#define ELEM(array, i) array[i + 1]
#define TYPE int

#define VAR_F1 1
#define VAR_F4 1

#include "func.inc"

int main() 
{
	// Ввод размерности двумерного массива
	int A, B;
	do
	{
		printf("Input values of A and B:\n");
		scanf("%d %d", &A, &B);
	}	
	while (A <= 0 || B <= 0);
	
	// Ввод диапазона значений
	TYPE mn_val, mx_val;
	printf("Input min and max value of elements:\n");
	scanf("%d %d", &mn_val, &mx_val);
	if (mn_val > mx_val) 
	{
		TYPE temp = mn_val;
		mn_val = mx_val;
		mx_val = temp;
	}

	// Выделение динамической памяти для двумерного массива A*B
	TYPE** array = (TYPE**)malloc(A * sizeof(TYPE*));
	if (!array) 
	{
		printf("Error!");
		return 1;
	}
	int z;
	for (z = 0; z < A; z++) 
	{
		array[z] = (TYPE*)malloc((B + 1) * sizeof(TYPE));
		if (!array[z]) 
		{
			printf("Error!");
			return 2;
		}
		array[z][0] = B;
	}
	
	// Заполнение двумерного массива
	srand(time(0));
	int i;
	for (i = 0; i < A; i++) 
	{
		array[i] = rand_data(array[i], mn_val, mx_val);
	}
	
	// Вывод двумерного массива
	printf("\n");
	int a;
	for (a = 0; a < A; a++) 
	{
		int b;
		for (b = 1; b <= B; b++) 
		{
			printf("%- 5d ", array[a][b]);
		}
		printf("\n");
	}
	printf("\n");
	
	int k, m;
	printf("\nInput k and m: \n");
	scanf("%d %d", &k, &m);

	// Обработка массива
	int q;
	for (q = 0; q < A; q++) 
	{
		int choice = (q + 1) % 4;
		switch(choice) 
		{
			case 1:
				array[q] = del(array[q]);
				break;
			case 2:
				array[q] = add(array[q], k, mn_val, mx_val); 
				break;
			case 3:
				array[q] = move(array[q], m);
				break;
			case 0:
				array[q] = find(array[q]);
				break;
			default:
				printf("ERROR!\n");
				break;
		}
	}
	#undef VAR_F1
	#undef VAR_F4

	// Вывод обработанного двумерного массива
	printf("\n");
	TYPE size;
	int c;
	for (c = 0; c < A; c++) 
	{
		size = LEN(array[c]);
		int d;
		for (d = 0; d < size; d++) 
		{
			printf("%- 5d ", ELEM(array[c], d));
		}
		printf("\n");
	}
	printf("\n");
	
	int e;
	for (e = 0; e < A; e++) 
	{
		free(array[e]);
	}
	free(array);
	array = NULL;
	
	return 0;
}

