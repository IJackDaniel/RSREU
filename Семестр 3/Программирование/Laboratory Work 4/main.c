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

void get_randdata(int arr[100][2], int len) {
	
}

int main(int argc, char *argv[]) {
	int arr[100][2]; 
	
	int ln;
	do {
		printf("������� ����� �������: ");
		scanf("%d", &ln);
	}
	while (ln > 101 || ln < 0);
	
	int choice;
	printf("��� �� ������ ��������� ������? \n1 - �������; 2 - ��������� �������: \n");
	do {
		scanf("%d", &choice);
	}
	while (choice > 2 || choice < 1);
	
	switch (choice)
	{
		case 1:
			get_data(arr, ln);
			break;
		case 2:
//			get_randdata(arr);
			break;
	}
	
	return 0;
}
