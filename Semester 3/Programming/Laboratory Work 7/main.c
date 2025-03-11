#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>
#include <math.h>

int size_a = 0;
int size_b = 0;

void get_randdata(int* arr, const int len, int mn, int mx) 
{
	srand(time(0));
	int i;
	for (i = 0; i < len; i++) {
		arr[i] = rand() % (mx + 1 - mn) + mn;
	}
}

bool is_prime(int num)
{
	int i;
	int border = sqrt(num) + 1;
	for (i = 2; i <= border; i++)
	{
		if (num % i == 0)
		{
			return false;
		}
	}
	return true;
}

int* edited_A(int* arr)
{
	int* edited_a = NULL;
	edited_a = (int*) malloc(size_a * sizeof(int));
	
	int i = 0;
	for (i; i < size_a; i++)
	{
		if (arr[i] > 1 && is_prime(arr[i]))
		{
			edited_a[size_b] = arr[i];
			size_b++;
		}	
	}	
	
	return edited_a;
}

void reverse(int* arr, const int len)
{
	int iter = len / 2;
	int i = 0;
	for(i; i < iter; i++)
	{
		int temp = arr[i];
		arr[i] = arr[len - i - 1];
		arr[len - i - 1] = temp;
	}
}

void printArray(int* arr, const int len, char name_arr)
{
	int i;
	for (i = 0; i < len; i++)
	{
		printf("%c[%2d] = %-3d; ", name_arr, i, arr[i]);
		if ((i + 1) % 7 == 0) 
		{
			printf("\n");
		}
	}
}

int main() {
	int* A = NULL;
	int* B = NULL;
	
	const int max_len = 100;
	do 
	{
		printf("Enter number from 1 to %d (len of array): ", max_len);
		scanf("%d", &size_a);	
	}
	while (size_a > max_len || size_a < 1);
	
	int mn_val, mx_val;
	printf("\nEnter a range of values: ");
	scanf("%d %d", &mn_val, &mx_val);
	printf("\n");
	if (mn_val > mx_val) 
	{
		int temp;
		temp = mn_val;
		mn_val = mx_val;
		mx_val = temp;
	}
	
	A = (int*) malloc(size_a * sizeof(int));
	
	get_randdata(A, size_a, mn_val, mx_val);
	
	B = edited_A(A);
	reverse(B, size_b);
	
	printArray(A, size_a, 'A');
	printf("\n\n");
	printArray(B, size_b, 'B');
	
	free(A);
	free(B);
	
	return 0;
}
