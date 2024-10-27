#include <stdio.h>
#include <stdlib.h>

void get_data(float arr[], int len) {
	int i;
	for (i = 0; i < len; i++) {
		printf("Enter %d array element: ", i);
		scanf("%f", &arr[i]);
	}
}

int iter_bin_search(float arr[], float find, int len) {
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

int recursion_bin_search(float arr[], float find, int l, int r) {
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
	do {
		printf("Enter len of array: ");
		scanf("%d", &ln);
	}
	while (ln <= 0);
	
	float find;
	printf("Enter number: ");
	scanf("%f", &find);
	
	float arr[ln];
	get_data(arr, ln);
	
	
	printf("%d\n", iter_bin_search(arr, find, ln));
	printf("%d\n", recursion_bin_search(arr, find, 0, ln - 1));
	return 0;
}
