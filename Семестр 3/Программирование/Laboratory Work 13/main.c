#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include "Fraction_Module.h"
#include "Workers_Module.h"

int main() 
{
	system("chcp 1251");
	
	// Переменные, используемые в модуле с дробями
	int sign_1, sign_2;
	int n_1, n_2;
	int d_1, d_2;
	Fraction frac_1;
	Fraction frac_2;
	int res;
	Fraction frac_res;
	int choice_frac;
	
	// Переменные, используемые в модуле с работниками
	Employee* workers = NULL;
	int size = 0;
	int command;
	int type;
	int index;
	
	int choice_module;
	do {
		printf("\nВыбор модуля:");
		printf("\n1 - модуль работы с дробями;");
		printf("\n2 - модуль с работниками;");
		printf("\n3 - завершить работу программы:");
		printf("\nВыбор: ");
		scanf("%d", &choice_module);
		switch (choice_module)
		{
			case 1:
				show_menu_frac();
				do 
				{
					printf("\nВыбор команды: ");
					scanf("%d", &choice_frac);	
					
					switch (choice_frac) 
					{
						case 1:
							printf("\nВвод дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							PrintFraction(&frac_1);
							break;
						case 2:
							printf("\nВвод первой дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\nВвод второй дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							frac_res = AddingFractions(&frac_1, &frac_2);
							PrintFraction(&frac_res);
							break;
						case 3:
							printf("\nВвод первой дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\nВвод второй дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							frac_res = Subtraction_of_fractions(&frac_1, &frac_2);
							PrintFraction(&frac_res);
							break;
						case 4:
							printf("\nВвод первой дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\nВвод второй дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							frac_res = Multiplication_of_fractions(&frac_1, &frac_2);
							PrintFraction(&frac_res);
							break;
						case 5:
							printf("\nВвод первой дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\nВвод второй дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							frac_res = Division_of_fractions(&frac_1, &frac_2);
							PrintFraction(&frac_res);
							break;
						case 6:
							printf("\nВвод первой дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\nВвод второй дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							CommonDenominator(&frac_1, &frac_2);
							PrintFraction(&frac_1);
							PrintFraction(&frac_2);
							break;
						case 7:
							printf("\nВвод первой дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_1, &d_1, &sign_1);
							frac_1 = CreateFraction(n_1, d_1, sign_1);
							
							printf("\nВвод второй дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
							scanf("%d %d %d", &n_2, &d_2, &sign_2);
							frac_2 = CreateFraction(n_2, d_2, sign_2);
							
							res = Comparing_fractions(&frac_1, &frac_2);
							if (res == 0) 
							{
								printf("\nДроби равны\n");
							}
							else if (res == 1) 
							{
								printf("\nПервая дробь больше\n");
							}
							else 
							{
								printf("\nПервая дробь меньше\n");
							}
							break;
						case 8:
							printf("\nВвод дроби: \nЧислитель/Знаменатель/Знак(0 - минус; 1 - плюс): ");
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
					printf("\nВыбор команды: ");
					scanf("%d", &command);
					
					switch (command)
					{
						case 1:
							printf("\nВведите должность:\n (%d - Директор; %d - Повар; %d - Курьер)\nДолжность: ", DIRECTOR, COOK, COURIER);
							scanf("%d", &type);
							
							PostType new_worker_type = (PostType)type;
							Employee* new_worker = create_worker_card(new_worker_type);
							if (new_worker == NULL) break;
							
							add_worker(&workers, &size, new_worker);
							printf("\nКарточка работника добавлена!\n");
							break;
						case 2:
							if (size == 0)
							{
								printf("\nСписок работников пуст!\n");
								break;
							}
							printf("Введите номер работника, карточку которого нужно удалить: ");
							scanf("%d", &index);
							remove_worker(&workers, &size, index - 1);
							printf("Карточка работника удалена.\n");
							break;
						case 3:
							if (size == 0)
							{
								printf("\nСписок работников пуст!\n");
								break;
							}
							
							printf("Введите номер работника, карточку которого нужно обновить: ");
							scanf("%d", &index);
							update_worker(workers, size, index - 1);
							printf("Карточка обновлена.\n");
							break;
						case 4:
							if (size == 0)
							{
								printf("\nСписок работников пуст!\n");
								break;
							}
							
							print_all_workers(workers, size);
							break;
						case 5:
							if (size == 0) 
							{
								printf("\nСписок работников пуст!\n");
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
				printf("\nЗавершение работы программы...\n");
				break;
			default:
				continue;
		}
	} while (choice_module != 3);
	
	printf("\nРабота программы завершена\n");
	return 0;
}
