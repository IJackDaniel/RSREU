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

void get_randdata(float arr[][3], int len) {
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
		arr[i][0] = rand() % (mx_val + 1 - mn_val) + mn_val;
		arr[i][1] = rand() % (mx_val + 1 - mn_val) + mn_val;
		arr[i][2] = 0.0;
	}
}

bool is_in_field(float x, float y, float r) {
	bool result;
	if (fabs(x) <= r && fabs(y) <= r && !((x + r) * (x + r) + (y - r) * (y - r) <= r * r) && !((x - r) * (x - r) + (y + r) * (y + r) <= r * r)) {
			result = true;
	}
	else {
		result = false;
	}
	return result;
}

void view(float arr[][3], int len) {
	int p;
	printf("\nBelong\n");
	for (p = 0; p < len; p++){
		if (arr[p][2] == 1.0) {
			printf("point [%3d] = (%- 11.3f; %- 11.3f)\n", p, arr[p][0], arr[p][1]);
		}
	}
	printf("\nDidn't belong\n");
	for (p = 0; p < len; p++){
		if (arr[p][2] == 0.0) {
			printf("point [%3d] = (%- 11.3f; %- 11.3f)\n", p, arr[p][0], arr[p][1]);
		}
	}
}

int main() {
	int ln;
	do {
		printf("Enter len of array: ");
		scanf("%d", &ln);
	}
	while (ln <= 0);
	
	float r;
	do {
		printf("\nEnter field parameter R: ");
		scanf("%f", &r);
	}
	while (r <= 0);
	
	int choice;
	printf("\nThe format of filling the array: \n1 - manually; 2 - randomly:\n");
	do {
		scanf("%d", &choice);
	}
	while (choice > 2 || choice < 1);
	
	float arr[ln][3]; 
	switch (choice)
	{
		case 1:
			get_data(arr, ln);
			break;
		case 2:
			get_randdata(arr, ln);
			break;
	}
	
	int i;
	for (i = 0; i < ln; i++) {
		if (is_in_field(arr[i][0], arr[i][1], r)) {
			arr[i][2] = 1.0;
		}
	}
	
	view(arr, ln);
	
	return 0;
}
