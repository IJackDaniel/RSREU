#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

void get_data(int arr[][2], int len) {
	int x, y;
	int i;
	for (i = 0; i < len; i++) {
		printf("Enter %d array element: ", i);
		scanf("%d %d", &x, &y);
		arr[i][0] = x;
		arr[i][1] = y;
	}
}

void get_randdata(int arr[][2], int len, int mn_val, int mx_val) {
	int x, y;
	int i;
	for (i = 0; i < len; i++) {
		arr[i][0] = rand() % (mx_val + 1 - mn_val) + mn_val;
		arr[i][1] = rand() % (mx_val + 1 - mn_val) + mn_val;
	}
}

int main(int argc, char *argv[]) {
	int arr[100][2]; 
	
	int ln;
	do {
		printf("¬ведите длину массива: ");
		scanf("%d", &ln);
	}
	while (ln > 101 || ln < 0);
	
	int choice;
	printf(" ак вы хотите заполн€ть массив? \n1 - вручную; 2 - случайным образом: \n");
	do {
		scanf("%d", &choice);
	}
	while (choice > 2 || choice < 1);
	
	int mn, mx;
	switch (choice)
	{
		case 1:
			get_data(arr, ln);
			break;
		case 2:
			printf("Enter a range of values:\n");
			scanf("%f %f", &mn, &mx);
			get_randdata(arr, ln, mn, mx);
			break;
	}
	
	int p;
	for (p = 0; p < ln; p++){
		printf("%d\n", arr[p]);
	}
	
	return 0;
}
