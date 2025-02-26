#include <stdio.h>
#include <stdlib.h>

// Функция для заполнения статического массива вручную
void get_data(float arr[], const int len) {
	int i;
	for (i = 0; i < len; i++) {
		printf("Enter %d array element: ", i);
		scanf("%f", &arr[i]);
	}
}

// Функция для заполнения статического массива псевдослучайными числами
void get_randdata(float arr[], const int len) {
  int mn_val, mx_val;
  printf("\nEnter a range of values: ");
  scanf("%d %d", &mn_val, &mx_val);
  if (mn_val > mx_val) {
    int temp;
    temp = mn_val;
    mn_val = mx_val;
    mx_val = temp;
  }
  int i;
  for (i = 0; i < len; i++) {
    arr[i] = rand() % (mx_val + 1 - mn_val) + mn_val;
	}
}

// Функция пузырьковой сортировки массива
void bubble_sort(float arr[], const int len) {
	int i, j;
	for (i = 0; i < len - 1; i++) {
		for (j = 0; j < len - 1; j++) {
			if (arr[j] > arr[j + 1]) {
				float save;
				save = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = save;
			}
		}
	}
}

// Бинарный поиск через итеративный подход
int iter_bin_search(float arr[], const float find, const int len) {
	int l = 0, r = len - 1, m;
	while (r - l > 1) {
		m = (l + r) / 2;
		if (arr[m] <= find) {
			l = m;
		}
		else {
			r = m;
		}
	}
	if (arr[l] == find) {
		return l;
	}
	else {
		return -1;
	}
}

// Бинарный поиск через рекурсию
int recursion_bin_search(float arr[], const float find, int l, int r) {
	if (r - l == 1) {
		if (arr[l] == find) {
			return l;
		}
		else {
			return -1;
		}
	}
	int m = (l + r) / 2;
	if (arr[m] <= find) {
		return recursion_bin_search(arr, find, m, r);
	}
	else {
		return recursion_bin_search(arr, find, l, m);
	}
}

int main() {
	int ln;
	do 
	{
		printf("Enter the len of array: ");
		scanf("%d", &ln);
	}
	while (ln <= 0);
	
	float find;
	printf("Enter a number: ");
	scanf("%f", &find);
	
	int choice;
	printf("\nThe format of filling the array: \n1 - manually; 2 - randomly:\n");
	do 
	{
		scanf("%d", &choice);
	}
	while (choice > 2 || choice < 1);
	
	float arr[ln]; 
	switch (choice)
	{
		case 1:
	    	get_data(arr, ln);
	    	break;
	    case 2:
	      	get_randdata(arr, ln);
	      	break;
	}
	
	bubble_sort(arr, ln);
	
//	int a;
//	for(a = 0; a < ln; a++) 
//	{
//		printf("arr[%d] = %f\n", a, arr[a]);
//	}
	
	int res1;
	res1 = iter_bin_search(arr, find, ln);
	if (res1 != -1) 
	{
		printf("\nThe result of an iterative binary search: arr[%d] = %f\n", res1, find);
	}
	else
	{
		printf("\nThe element was not found by the iterative binary search method.\n");
	}
	
	int res2;
	res2 = recursion_bin_search(arr, find, 0, ln - 1);
	if (res2 != -1) 
	{
		printf("The result of a  recursive binary search: arr[%d] = %f", res2, find);
	}
	else
	{
		printf("The element was not found by the recursive binary search method.");
	}
	return 0;
}
