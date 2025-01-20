#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Функция из группы "удаление"
// Удаление минимального элемента (включая его повторения)
int* del(int* arr) 
{
	int mn = INT_MAX, i;
	for (i = 1; i <= *(arr); i++) 
	{
		if (*(arr + i) < mn)
		{
			mn = *(arr + i);
		}
	}
	
	i = 1;
	while (i <= *(arr))
	{
		if (*(arr + i) == mn) 
		{
			int j;
			for (j = i; j <= *(arr) - 1; j++) 
			{
				*(arr + j) = *(arr + j + 1);
			}	
			*(arr) -= 1;
		}
		else {
			i++;	
		}
	}
	arr = (int*)realloc(arr, (*(arr)) * sizeof(int));
	return arr;
}

// Функция из группы "добавление"
// К случайных элементов в конец массива
int* add(int* arr, int k, int mn, int mx) 
{
	arr = (int*)realloc(arr, ((*(arr) + k) * sizeof(int)));
	int i = *(arr);
	for (i; i < *(arr) + k; i++) 
	{
		*(arr + i + 1) = (int) (rand() % (mx + 1 - mn) + mn);
	}
	*(arr) += k;
	return arr;
}

// Функция из группы "Перестановка"
// Сдвинуть циклически на M элементов вправо
void move(int* arr, int m)
{
	int temp;
	int i;
	for (i = 1; i <= m; i++) 
	{
		temp = *(arr + (*(arr)));
		int j;
		for (j = *(arr); j >= 2 ; j--) 
		{
			*(arr + j) = *(arr + j - 1);
		}
		*(arr + 1) = temp;
	}
}

// Функция из группы "Поиск"
// Занулить последний чётный элемент
void find(int* arr) 
{
	int i, last = -1;
	for (i = 1; i <= *(arr); i++) 
	{
		if (*(arr + i) % 2 == 0) 
		{
			last = i;
		}
	}
	if (last != -1) 
	{
		*(arr + last) = 0;
	}
}

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
	int mn_val, mx_val;
	printf("Input min and max value of elements:\n");
	scanf("%d %d", &mn_val, &mx_val);
	if (mn_val > mx_val) 
	{
		int temp = mn_val;
		mn_val = mx_val;
		mx_val = temp;
	}
	
	// Выделение динамической памяти для двумерного массива A*B
	int** array = (int**)malloc((A + 1) * sizeof(int*));
	int z;
	for (z = 1; z < A  + 1; z++) 
	{
		*(array + z) = (int*)malloc((B + 1) * sizeof(int*));
		**(array + z) = B;
	}
	
	// Заполнение двумерного массива
	srand(time(0));
	int i;
	for (i = 1; i < A + 1; i++) 
	{
		int j;
		for (j = 1; j < B + 1; j++) 
		{
			*(*(array + i) + j) = (int) (rand() % (mx_val + 1 - mn_val) + mn_val);
		}
	}
	
	// Вывод двумерного массива
	printf("\n");
	int a;
	for (a = 1; a < A + 1; a++) 
	{
		int b;
		for (b = 1; b < B + 1; b++) 
		{
			printf("%- 5d ", *(*(array + a) + b));
		}
		printf("\n");
	}
	printf("\n");
	
	// Обработка массива
	int k, m;
	int q;
	for (q = 1; q < A + 1; q++) 
	{
		int choice = q % 4;
		switch(choice) 
		{
			case 1:
				*(array + q) = del(*(array + q));
				break;
			case 2:
				printf("Input count of new elements: ");
				scanf("%d", &k);
				*(array + q) = add(*(array + q), k, mn_val, mx_val);
				break;
			case 3:
				printf("Input m: ");
				scanf("%d", &m);
				move(*(array + q), m);
				break;
			case 0:
				find(*(array + q));
				break;
			default:
				printf("ERROR!\n");
				break;
		}
	}

	// Вывод обработанного двумерного массива
	printf("\n");
	int c, size;
	for (c = 1; c < A + 1; c++) 
	{
		size = **(array + c);
		int d;
		for (d = 1; d < size + 1; d++) 
		{
			printf("%- 5d ", *(*(array + c) + d));
		}
		printf("\n");
	}
	printf("\n");
	
	int e;
	for (e = 1; e < A + 1; e++) 
	{
		free(*(array + e));
	}
	free(array);
	array = NULL;
	
	return 0;
}
