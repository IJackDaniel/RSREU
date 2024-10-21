#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>

void get_data(float arr[][3], int len) {
	int i;
	for (i = 0; i < len; i++) {
		printf("Enter %d array element: ", i);
		scanf("%f %f", &arr[i][0], &arr[i][1]);
		arr[i][2] = 0.0;
	}
}

void get_randdata(float arr[][3], int len, int mn_val, int mx_val) {
	int i;
	for (i = 0; i < len; i++) {
		arr[i][0] = rand() % (mx_val + 1 - mn_val) + mn_val;
		arr[i][1] = rand() % (mx_val + 1 - mn_val) + mn_val;
		arr[i][2] = 0.0;
	}
}

void is_in_field(float arr[][3], int len, float r, float a, float b) {
	float x, y;
	int i;
	for (i = 0; i < len; i++) {
		x = arr[i][0];
		y = arr[i][1];
		if (fabs(x) <= a && fabs(y) <= b && !((x + a) * (x + a) + (y - b) * (y - b) <= r * r) && !((x - a) * (x - a) + (y + b) * (y + b) <= r * r)) {
			arr[i][2] = 1.0;
		}
	}
}

void view(float arr[][3], int len) {
	int p;
	for (p = 0; p < len; p++){
		printf("point [%3d] = (%- 11.3f; %- 11.3f): result - %1.0f\n", p, arr[p][0], arr[p][1], arr[p][2]);
	}
}

int main() {
	int ln;
	do {
		printf("Enter len of array: ");
		scanf("%d", &ln);
	}
	while (ln < 0);
	
	float r, a, b;
	do {
		printf("Enter field parameters: r, a, b: ");
		scanf("%f %f %f", &r, &a, &b);
	}
	while (r <= 0 || a <= 0 || b <= 0);
	
	int choice;
	printf("The format of filling the array: \n1 - manually; 2 - randomly: \n");
	do {
		scanf("%d", &choice);
	}
	while (choice > 2 || choice < 1);
	
	float arr[ln][3]; 
	int mn, mx;
	switch (choice)
	{
		case 1:
			get_data(arr, ln);
			break;
		case 2:
			printf("Enter a range of values:\n");
			scanf("%d %d", &mn, &mx);
			get_randdata(arr, ln, mn, mx);
			break;
	}
	
	is_in_field(arr, ln, r, a, b);
	
	view(arr, ln);
	
	return 0;
}
