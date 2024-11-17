#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>
#include <time.h>

void get_data(float (*arr)[3], const int len) {
	int i;
	for (i = 0; i < len; i++) {
		printf("Enter %d array element: ", i);
		scanf("%f %f", &(*(*(arr + i))), &(*(*(arr + i) + 1)));
		*(*(arr + i) + 2) = 0.0;
	}
}

void get_randdata(float (*arr)[3], const int len) {
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
	srand(time(0));
	for (i = 0; i < len; i++) {
		*(*(arr + i)) = rand() % (mx_val + 1 - mn_val) + mn_val;
		*(*(arr + i) + 1) = rand() % (mx_val + 1 - mn_val) + mn_val;
		*(*(arr + i) + 2) = 0.0;
	}
}

bool is_in_field(float x, float y, float r) {
	return (fabs(x) <= r && fabs(y) <= r && !((x + r) * (x + r) + (y - r) * (y - r) <= r * r) && !((x - r) * (x - r) + (y + r) * (y + r) <= r * r)) ? true: false;
}

void view(float (*arr)[3], const int len) {
	int p;
	printf("\nBelong\n");
	for (p = 0; p < len; p++){
		if (*(*(arr + p) + 2) == 1.0) {
			printf("point [%3d] = (%- 11.3f; %- 11.3f)\n", p, *(*(arr + p)), *(*(arr + p) + 1));
		}
	}
	printf("\nDidn't belong\n");
	for (p = 0; p < len; p++){
		if (*(*(arr + p) + 2) == 0.0) {
			printf("point [%3d] = (%- 11.3f; %- 11.3f)\n", p, *(*(arr + p)), *(*(arr + p) + 1));
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
	void (*inp_points) (float*, int);
	switch (choice)
	{
		case 1:
			inp_points = get_data;
			inp_points(&arr, ln);
			break;
		case 2:
			inp_points = get_randdata;
			inp_points(&arr, ln);
			break;
	}
	
	int i;
	for (i = 0; i < ln; i++) {
		if (is_in_field(*(*(arr + i)), *(*(arr + i) + 1), r)) {
			*(*(arr + i) + 2) = 1.0;
		}
	}
	
	void (*result) (float*, int);
	result = view;
	result(&arr, ln);
	
	return 0;
}

