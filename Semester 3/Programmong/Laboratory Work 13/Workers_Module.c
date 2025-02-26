#include <stdio.h>
#include <stdlib.h>

#include "Workers_Module.h"

void show_menu_workers() 
{
	printf("\nМеню\n");
	printf("1. Добавить карточку работника\n");
	printf("2. Удалить карточку работника\n");
	printf("3. Обновить карточку работника\n");
	printf("4. Вывести инфомрацию обо всех работниках\n");
	printf("5. Вывести информацию о работниках, обладающих определёнными характеристиками\n");
	printf("6. Выход из программы\n");
}

// Создание карточки работника
Employee* create_worker_card(PostType post) 
{
	Employee* worker = (Employee*)malloc(sizeof(Employee));
	if (worker == NULL) 
	{
		printf("Ошибка!");
		exit(1);
	}
	
	worker->post = post;
	worker->flags = 0;
	
	printf("\nВвод данных о работнике\n");
	switch (post) 
	{
		case DIRECTOR:
			// ФИО и стаж работы
			printf("\nФамилия директора: ");
			scanf("%s", &worker->param.director.surname);
			do 
			{	
				printf("Стаж работы: ");
				scanf("%d", &worker->param.director.work_experience);
			} while (worker->param.director.work_experience < 0);
			break;
		case COOK:
			// Рейтинг и стаж работы
			do 
			{
				printf("\nРейтинг повара: ");
				scanf("%f", &worker->param.cook.rating);
			} while (worker->param.cook.rating < 0.0 || worker->param.cook.rating > 5.0);
			
			do 
			{
				printf("Стаж работы: ");
				scanf("%d", &worker->param.cook.work_experience);
			} while(worker->param.cook.work_experience < 0);
			break;
		case COURIER:
			// Зарплата и район работы
			do 
			{
				printf("\nЗарплата курьера: ");
				scanf("%d", &worker->param.courier.salary);
			} while(worker->param.courier.salary < 0);
			printf("\nРайон доставки: ");
			scanf("%s", &worker->param.courier.district);
			break;
		default:
			printf("\nНеизвестная должность! Операция отменена! \n");
			free(worker);
			return NULL;
	}
	
	int property;
	unsigned int mask = 0;
	printf("Ввод характеристики работника: \n");
    printf("Работник компетентный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG5 : mask;
    
    printf("Работник целеустремлённый? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG4 : mask;
    
    printf("Работник стрессоустойчивый? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG3 : mask;
    
    printf("Работник коммуникабельный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG2 : mask;
    
    printf("Работник ответственный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG1 : mask;
	
	worker->flags = mask;
	
	return worker;
}

void add_worker(Employee** data, int* size, Employee* worker) 
{
	*data = (Employee*)realloc(*data, (*size + 1) * sizeof(Employee));
	if (data == NULL) 
	{
		printf("Недостаточно памяти!\n");
		exit(1);
	}
	
	(*data)[*size] = *worker;
	(*size)++;
}

void remove_worker(Employee** data, int* size, const int index) 
{
	if (index < 0 || index >= *size) 
	{
		printf("\nНеизвестный индекс!\n");
		return;
	}
	
	int i = index;
	for (i; i < *size - 1; i++) 
	{
		(*data)[i] = (*data)[i + 1];
	}
	
	*data = (Employee*)realloc(*data, (*size - 1) * sizeof(Employee));
	(*size)--;
}

void update_worker(Employee* data, const int size, const int index) 
{
	if (index < 0 || index >= size) 
	{
		printf("\nНеверный индекс!\n");
		return;
	}
	
	Employee* cur_worker_ptr = &data[index];
	printf("\nВвод данных о продукте\n");
	switch (cur_worker_ptr->post)
	{
		case DIRECTOR:
			// ФИО и стаж работы
			printf("\nФамилия директора: ");
			scanf("%s", &cur_worker_ptr->param.director.surname);
			do 
			{	
				printf("Стаж работы: ");
				scanf("%d", &cur_worker_ptr->param.director.work_experience);
			} while (cur_worker_ptr->param.director.work_experience < 0);
			break;
		case COOK:
			// Рейтинг и стаж работы
			do 
			{
				printf("\nРейтинг повара: ");
				scanf("%f", &cur_worker_ptr->param.cook.rating);
			} while (cur_worker_ptr->param.cook.rating < 0.0 || cur_worker_ptr->param.cook.rating > 5.0);
			
			do 
			{
				printf("Стаж работы: ");
				scanf("%d", &cur_worker_ptr->param.cook.work_experience);
			} while(cur_worker_ptr->param.cook.work_experience < 0);
			break;
		case COURIER:
			// Зарплата и район работы
			do 
			{
				printf("\nЗарплата курьера: ");
				scanf("%d", &cur_worker_ptr->param.courier.salary);
			} while(cur_worker_ptr->param.courier.salary < 0);
			printf("\nРайон доставки: ");
			scanf("%s", &cur_worker_ptr->param.courier.district);
			break;
		default:
			printf("\nНеизвестная должность! Операция отменена! \n");
			return;	
	}
	
	int property;
	unsigned int mask = 0;
	printf("Ввод характеристики работника: \n");
    printf("Работник компетентный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG5 : mask;
    
    printf("Работник целеустремлённый? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG4 : mask;
    
    printf("Работник стрессоустойчивый? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG3 : mask;
    
    printf("Работник коммуникабельный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG2 : mask;
    
    printf("Работник ответственный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG1 : mask;
	
	cur_worker_ptr->flags = mask;
}

void print_worker(const Employee* worker) 
{
	PostType type = worker->post;
	char* type_string = NULL;
	switch (type)
	{
		case DIRECTOR:
			type_string = "Директор";
			break;
		case COOK:
			type_string = "Повар";
			break;
		case COURIER:
			type_string = "Курьер";
			break;
	}
	
	printf("\nДолжность: %d. %s\n", type, type_string);
	
	switch (type)
	{
		case DIRECTOR:
			printf("Фамилия: %s\n", worker->param.director.surname);
			printf("Стаж работы: %d\n", worker->param.director.work_experience);
			break;
		case COOK:
			printf("Рейтинг: %3.2f\n", worker->param.cook.rating);
			printf("Стаж работы: %d\n", worker->param.cook.work_experience);
			break;
			break;
		case COURIER:
			printf("Зарплата: %d руб.\n", worker->param.courier.salary);
			printf("Район: %s\n", worker->param.courier.district);
			break;
	}
	
	printf("Особые характеристики работника: \n");
    if (worker->flags & FLAG5)
        printf("\tКомпетентный.\n");
    if (worker->flags & FLAG4)
        printf("\tЦелеустремлённый.\n");
    if (worker->flags & FLAG3)
        printf("\tСтрессоустойчивый.\n");
    if (worker->flags & FLAG2)
        printf("\tКоммуникабельный.\n");
    if (worker->flags & FLAG1)
        printf("\tОтветственный.\n");
}

void print_all_workers(const Employee* data, const int size) 
{
	printf("\nСписок всех работников:\n\n");
	int i = 0;
	for (i; i < size; i++)
	{
		printf("Работник %d", i + 1);
		print_worker(data + i);
		printf("\n");
	}
}

void print_workers_by_mask(const Employee* data, const int size)
{
	printf("Для вывода работников по качествам необходимо составтить маску.\nОтветьте на следующие вопросы:\n");

    int property = 0;
    unsigned int mask = 0;
    printf("Ввод характеристики работника: \n");
    printf("Работник компетентный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG5 : mask;
    
    printf("Работник целеустремлённый? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG4 : mask;
    
    printf("Работник вспыльчивый? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG3 : mask;
    
    printf("Работник коммуникабельный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG2 : mask;
    
    printf("Работник ответственный? (0 - нет, 1 - да): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG1 : mask;
    
    int i;
    for (i = 0; i < size; i++)
    {
    	if ((data[i].flags & mask) == mask) 
		{
			printf("\nРаботник номер %d\n", i + 1);
			print_worker(&data[i]);
			printf("\n");
		}
	}
}

