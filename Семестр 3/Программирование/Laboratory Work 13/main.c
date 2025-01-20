#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include "Fraction_Module.h"
#include "Workers_Module.h"

int main() 
{
	system("chcp 1251");
	
	// ����������, ������������ � ������ � �������
	int sign_1, sign_2;
	int n_1, n_2;
	int d_1, d_2;
	Fraction frac_1;
	Fraction frac_2;
	int res;
	Fraction frac_res;
	int choice_frac;
	
	// ����������, ������������ � ������ � �����������
	Employee* workers = NULL;
	int size = 0;
	int command;
	int type;
	int index;
	
	int choice_module;
	do {
		printf("\n����� ������:");
		printf("\n1 - ������ ������ � �������;");
		printf("\n2 - ������ � �����������;");
		printf("\n3 - ��������� ������ ���������:");
		printf("\n�����: ");
		scanf("%d", &choice_module);
		switch (choice_module)
		{
			case 1:
				show_menu_frac();
				do 
				{
					printf("\n����� �������: ");
					scanf("%d", &choice_frac);	
					
					switch (choice_frac) 
					{
						case 1:
							printf("\n���� �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							PrintFraction(&frac_1);
							break;
						case 2:
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							frac_res = AddingFractions(&frac_1, &frac_2);
							PrintFraction(&frac_res);
							break;
						case 3:
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							frac_res = Subtraction_of_fractions(&frac_1, &frac_2);
							PrintFraction(&frac_res);
							break;
						case 4:
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							frac_res = Multiplication_of_fractions(&frac_1, &frac_2);
							PrintFraction(&frac_res);
							break;
						case 5:
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							frac_res = Division_of_fractions(&frac_1, &frac_2);
							PrintFraction(&frac_res);
							break;
						case 6:
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							CommonDenominator(&frac_1, &frac_2);
							PrintFraction(&frac_1);
							PrintFraction(&frac_2);
							break;
						case 7:
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\n���� ������ �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							res = Comparing_fractions(&frac_1, &frac_2);
							if (res == 0) 
							{
								printf("\n����� �����\n");
							}
							else if (res == 1) 
							{
								printf("\n������ ����� ������\n");
							}
							else 
							{
								printf("\n������ ����� ������\n");
							}
							break;
						case 8:
							printf("\n���� �����: \n���������/�����������/����(0 - �����; 1 - ����): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							PrintFraction(&frac_1);
							break;
						case 9:
							break;
						default:
							show_menu_frac();
							break;
					} 
				} while (choice_frac != 9);
				break;
			case 2:
				show_menu_workers() ;
				do 
				{
					printf("\n����� �������: ");
					scanf("%d", &command);
					
					switch (command)
					{
						case 1:
							printf("\n������� ���������:\n (%d - ��������; %d - �����; %d - ������)\n���������: ", DIRECTOR, COOK, COURIER);
							scanf("%d", &type);
							
							PostType new_worker_type = (PostType)type;
							Employee* new_worker = create_worker_card(new_worker_type);
							if (new_worker == NULL) break;
							
							add_worker(&workers, &size, new_worker);
							printf("\n�������� ��������� ���������!\n");
							break;
						case 2:
							if (size == 0)
							{
								printf("\n������ ���������� ����!\n");
								break;
							}
							printf("������� ����� ���������, �������� �������� ����� �������: ");
							scanf("%d", &index);
							remove_worker(&workers, &size, index - 1);
							printf("�������� ��������� �������.\n");
							break;
						case 3:
							if (size == 0)
							{
								printf("\n������ ���������� ����!\n");
								break;
							}
							
							printf("������� ����� ���������, �������� �������� ����� ��������: ");
							scanf("%d", &index);
							update_worker(workers, size, index - 1);
							printf("�������� ���������.\n");
							break;
						case 4:
							if (size == 0)
							{
								printf("\n������ ���������� ����!\n");
								break;
							}
							
							print_all_workers(workers, size);
							break;
						case 5:
							if (size == 0) 
							{
								printf("\n������ ���������� ����!\n");
								break;
							}
							print_workers_by_mask(workers, size);
							break;
						case 6:
							free(workers);
							workers = NULL;
							size = 0;
							break;
						default:
							show_menu_workers();
							break;
					}
					
				} while (command != 6);
				break;
			case 3:
				printf("\n���������� ������ ���������...\n");
				break;
			default:
				continue;
		}
	} while (choice_module != 3);
	
	printf("\n������ ��������� ���������\n");
	return 0;
}
