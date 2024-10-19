// ���� ������������ ������ - ������� ���������, ������� ��������� �������� ��������� �� ������ � � ����������
// ������� 2
// ����������:
// A, B, C, D, E, F - ����� ����� (int), �������� �������������
// res - ����� ����� (int) - ��������� ����������
// ������ �������� ������� ������ 3413: ������ ������ ��������
// ����: 09.09.2024
#include <stdio.h>

int main(int argc, char** argv) {
	// ���������� ���� ����������
  	int A, B, C, D, E, F, res;
  	
  	// ��������� ��������
  	printf("Input A, B, C, D, E, F values:\n");
  	scanf("%d %d %d %d %d %d", &A, &B, &C, &D, &E, &F);
  	
  	// �������� �����
  	printf("\nA = %d; B = %d; C = %d;\nD = %d; E = %d; F = %d;\n", A, B, C, D, E, F);
  	// ����� �������
  	printf("F / (A * B + C) - (D * C + E) / E\n");
  	
  	// �������� �������� �������� �� ���
  	if (E == 0 || (A * B + C) == 0) {
  		printf("\nIncorrect input! Division by zero!");
  		return 1;
	}
  	
  	// ������������ ������� 
	/*  F / (A * B + C) � (D * C + E) / E */
  	asm("movl %1, %%eax;"  		// A -> EAX
    	"movl %2, %%ebx;"  		// B -> EBX
      	"imull %%ebx;"   		// EAX * EBX -> EAX
      	
		"addl %3, %%eax;" 		// C + EAX -> EAX
		
		"movl %%eax, %%ebx;" 	// EAX -> EBX
		"movl %4, %%eax;"		// F -> EAX
		"cdq;"
		"idivl %%ebx;" 			// EAX / EBX -> EAX
		"movl %%eax, %%ecx;"	// EAX -> ECX
		
		"movl %5, %%eax;"		// C -> EAX
		"imull %6;"				// EAX * D -> EAX
			
		"addl %7, %%eax;"		// EAX + E -> EAX
		
		"cdq;"
     	"idivl %8;"				// EAX / E -> EAX
     	
     	"subl %%eax, %%ecx;"	// ECX - EAX -> ECX
     	
     	"movl %%ecx, %0;"		// ECX -> res
     	
     	: "=r" (res)
     	: "r" (A), "r" (B), "r" (C), "r" (F), "r" (C), "r" (D), "r" (E), "r" (E)
     	: "%eax", "%ebx", "%ecx", "%edx"
     	);
  	
  	// ����� ���������� ������� �� ����� ����������
    printf("\nResult on Assembler: %d / (%d * %d + %d) - (%d * %d + %d) / %d = %d", F, A, B, C, D, C, E, E, res);
    
    // ������ �������� ��������� �� �
    res = 0;
    res = F / (A * B + C) - (D * C + E) / E;
    // ����� ���������� ������� �� ����� �
  	printf("\nResult on C: %d / (%d * %d + %d) - (%d * %d + %d) / %d = %d", F, A, B, C, D, C, E, E, res);
  	
    return 0;
}


