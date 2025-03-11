// ���� ������������ ������ - ���������� � ��������� ��������� � ������� �� ����� ����������
// ������� 2
// ����������:
// A, B, C - ����� ����� (int), �������� �������������
// res - ����� ����� (int) - ��������� ����������
// ������ �������� ������� ������ 3413: ������ ������ ��������
// ����: 22.09.2024
#include <stdio.h>

int main(int argc, char** argv) {
	// ���������� ���� ����������
  	int A, B, C, res;
  	
  	// ��������� ��������
  	printf("Input A, B, C values:\n");
  	scanf("%d %d %d", &A, &B, &C);
  	
  	// �������� �������� �� ���
  	if (A + B * C < 0 && A - B == 0) {
  		printf("\nUncorrect input! Devision by zero!");
  		return 1;
	  }
  	
  	// ���-����� A, B, C, D, E, F
  	printf("\nEcho output:\n");
  	printf("A = %d; B = %d; C = %d;\n", A, B, C);
  	// ����� �������
  	printf("\nFormula:\n");
  	printf("Z = A + B * C\n");
  	printf("If Z >= 0: Calculate the sum of the squares of the numbers from A to B\n");
  	printf("Else: C / (A - B) + A\n");
  	
  	// ������������ ������� 
  	asm("movl %1, %%eax;"			// C -> EAX
  		"imull %2;"					// EAX * B -> EAX
  		"add %3, %%eax;"			// A + EAX -> EAX
  		"movl %%eax, %0;"			// EAX -> res
  		
  		"movl $0, %%ebx;"			// 0 -> EBX
  		
  		"cmp %%ebx, %0;"			// Compare EBX and res
  		"jl less_than;"				// if Z < 0, jump to "less_than"
  		
  		// Z >= 0
  		// ���� ������� ����� ��������� ����� �� A �� B
  		"movl $0, %%ebx;"			// 0 -> EBX
		"movl %3, %%eax;"			// A -> ECX
		"cmp %2, %3;"				// Compare A and B
		"jle sum_square;"			// if A <= B
		
		"movl %%ebx, %0;"			// EBX -> res
		"jmp exit;"
  		
  		// Z < 0
  		// ���� ������� C/(A-B) + A
  		"less_than:"
			"movl %3, %%eax;"		// A -> EAX
			"subl %2, %%eax;"		// EAX - B -> EAX
			"movl %%eax, %%ebx;"	// EAX -> EBX
			
			"movl %4, %%eax;"		// C -> EAX
			"cdq;"
			"idivl %%ebx;"			// EAX / EBX -> EAX
			
			"addl %3, %%eax;"		// A + EAX -> EAX
			
			"movl %%eax, %0;"		// EAX -> res
			"jmp exit;"
  		
  		"sum_square:"
  			"movl %3, %%eax;"		// A -> EAX
  			"imull %%eax;"			// EAX * EAX -> EAX
  			"addl %%eax, %%ebx;"	// EAX + EBX -> EBX
  			
  			"incl %3;"				// A = A + 1
  			
  			"cmp %2, %3;"			// Compare A and B
			"jle sum_square;"		// if A <= B
  			
  			"movl %%ebx, %0;"		// EBX -> res
  			"jmp exit;"
  		
  		"exit:"
		: "=r" (res)
		: "r" (C), "r" (B), "r" (A), "r" (C)
		: "%eax", "%ebx", "%ecx", "%edx"
	);
  	// ����� ���������� ������� �� ����� ����������
    printf("\nResult on Assembler: %d", res);
    // ������ �������� ��������� �� �
    res = 0;
    int Z =  A + B * C;
    if (Z >= 0) {
		while (A <= B) {
			res = res + A * A;
			A++;
		}
	} else {
		res = C / (A - B) + A;
	}
    // ����� ���������� ������� �� ����� �
    printf("\nResult on C: %d", res);
  	
    return 0;
}

