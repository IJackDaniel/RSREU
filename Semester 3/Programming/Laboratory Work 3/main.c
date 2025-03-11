// ���� ������������ ������ - ���������� � ������������ ����������� �� ����� ���������������� C
// ������� 2
//
// ����������:
// �������:
// x0, y0 (float) - ��������� �������� x � y
// xh, yh (float) - ��� ��������� ��� x � y
// xn, yn (float) - �������� �������� x � y
// a (float) - ���������� ��� ����������� �������� �������
// nm1, nm2 (float) - ������� �������� ���� � ������������
//
// ��������:
// result (double) - ��������� ���������� ���������
// mn, mx (double) - ����������� � ������������ �������� ���������
// xmx, ymx, xmn, ymn (float) - �������� x � y, ��� ������� ��������� ��������� ����������� � ������������ ��������
//
// ������ �������� ������� ������ 3413: ������ ������ ��������
// ����: 07.10.2024

#include <stdio.h>
#include <float.h> 
#include <stdbool.h>
#include <math.h>

int main(int argc, char** argv) {
	float x0 = 0, xh = 0, xn = 0;
	while (x0 > xn && xh > 0 || x0 < xn && xh < 0 || xh == 0) {
		printf("Enter the values: x0 xh xn\n");
		scanf("%f %f %f", &x0, &xh, &xn);
	}
	
	float y0 = 0, yh = 0, yn = 0;
	while (y0 > yn && yh > 0 || y0 < yn && yh < 0 || yh == 0) {
		printf("Enter the values: y0 yh yn\n");
		scanf("%f %f %f", &y0, &yh, &yn);
	}	
	float a, nm1 = 0, nm2 = 0;
	while ((nm1 < 2 || nm1 > 6 ) || (nm2 < 2 || nm2 > 6)) {
		printf("Enter the values: a nm1 nm2\n");
		scanf("%f %f %f", &a, &nm1, &nm2);
	}
	printf("\nExo-output:\n");
	printf("x0 = %-10.3f| xh  = %-10.3f| xn  = %-10.3f\n", x0, xh, xn);
	printf("y0 = %-10.3f| yh  = %-10.3f| yn  = %-10.3f\n", y0, yh, yn);
	printf("a  = %-10.3f| nm1 = %-10.3f| nm2 = %-10.3f\n", a, nm1, nm2);
	
	printf("\n---------------------------------------------\n"); 
	printf("|      x      |      y      |     Result    |\n"); 
	printf("---------------------------------------------\n"); 
	
	if (x0 >= xn && xh < 0) {
		float save1 = x0;
		x0 = xn;
		xn = save1;
		xh = -xh;
	}
	
	if (y0 >= yn && yh < 0) {
		float save2 = y0;
		y0 = yn;
		yn = save2;
		yh = -yh;
	}
	
	float xmx, ymx;
	float xmn, ymn;
	double mx = -FLT_MAX, mn = FLT_MAX;
	float x = x0, y = y0;
	while (x <= xn + xh/2) {
		while (y <= yn + yh/2) {
			double result;
			bool flag = true;
			if (fabs(x) < a){
				result = 0;
				float q = (2 * x + 1);
				int n = 1;
				while (n <= nm1 && flag) {
					if (2 * n == -y) {
						flag = false;
						printf("| %- 11.3f | %- 11.3f |     error     | \n", x, y); 
		   				printf("---------------------------------------------\n"); 
					}
					else {
						result = result + q / (2 * n + y);
						q = q * (2 * x + 1);
						n++;
					}
				}
				result *= ((x + y) / (x * x + 4));
			}
			else {
				if (y == 0.0) {
					printf("| %- 11.3f | %- 11.3f |     error     | \n", x, y); 
		   			printf("---------------------------------------------\n"); 
					flag = false;
				}
				if (flag) {
					result = 1;
					float r = y * x + 1;
					int n = 1;
					while (n <= nm2) {
						result *= 1 + (r / (n + 1));
						r *= y * x + 1;
						n++;
					}
					result *= ((x + 1) / y);
				}
			}
			if (flag) {
				printf("| %- 11.3f | %- 11.3f | %- 10.5le | \n", x, y, result); 
	   			printf("---------------------------------------------\n"); 
	   			if (result > mx) {
	   				mx = result;
					xmx = x;
					ymx = y;	
				}
	   			if (result < mn) {
	   				mn = result;
					xmn = x;
					ymn = y;	
				}
			}
			y += yh;
		}
		x += xh;
		y = y0;
	}
	if (mn != FLT_MAX) {
		printf("The function takes the minimum value = %le, when x = %f; y = %f\n", mn, xmn, ymn);
		printf("The function takes the maximum value = %le, when x = %f; y = %f\n", mx, xmx, ymx);
	}
	else {
		printf("The function doesn't have min and max");
	}
	return 0;
}
