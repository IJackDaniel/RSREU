#include <stdio.h>
#include <stdlib.h>

#include "Workers_Module.h"

void show_menu_workers() 
{
	printf("\n����\n");
	printf("1. �������� �������� ���������\n");
	printf("2. ������� �������� ���������\n");
	printf("3. �������� �������� ���������\n");
	printf("4. ������� ���������� ��� ���� ����������\n");
	printf("5. ������� ���������� � ����������, ���������� ������������ ����������������\n");
	printf("6. ����� �� ���������\n");
}

// �������� �������� ���������
Employee* create_worker_card(PostType post) 
{
	Employee* worker = (Employee*)malloc(sizeof(Employee));
	if (worker == NULL) 
	{
		printf("������!");
		exit(1);
	}
	
	worker->post = post;
	worker->flags = 0;
	
	printf("\n���� ������ � ���������\n");
	switch (post) 
	{
		case DIRECTOR:
			// ��� � ���� ������
			printf("\n������� ���������: ");
			scanf("%s", &worker->param.director.surname);
			do 
			{	
				printf("���� ������: ");
				scanf("%d", &worker->param.director.work_experience);
			} while (worker->param.director.work_experience < 0);
			break;
		case COOK:
			// ������� � ���� ������
			do 
			{
				printf("\n������� ������: ");
				scanf("%f", &worker->param.cook.rating);
			} while (worker->param.cook.rating < 0.0 || worker->param.cook.rating > 5.0);
			
			do 
			{
				printf("���� ������: ");
				scanf("%d", &worker->param.cook.work_experience);
			} while(worker->param.cook.work_experience < 0);
			break;
		case COURIER:
			// �������� � ����� ������
			do 
			{
				printf("\n�������� �������: ");
				scanf("%d", &worker->param.courier.salary);
			} while(worker->param.courier.salary < 0);
			printf("\n����� ��������: ");
			scanf("%s", &worker->param.courier.district);
			break;
		default:
			printf("\n����������� ���������! �������� ��������! \n");
			free(worker);
			return NULL;
	}
	
	int property;
	unsigned int mask = 0;
	printf("���� �������������� ���������: \n");
    printf("�������� ������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG5 : mask;
    
    printf("�������� ���������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG4 : mask;
    
    printf("�������� �����������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG3 : mask;
    
    printf("�������� ����������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG2 : mask;
    
    printf("�������� �������������? (0 - ���, 1 - ��): ");
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
		printf("������������ ������!\n");
		exit(1);
	}
	
	(*data)[*size] = *worker;
	(*size)++;
}

void remove_worker(Employee** data, int* size, const int index) 
{
	if (index < 0 || index >= *size) 
	{
		printf("\n����������� ������!\n");
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
		printf("\n�������� ������!\n");
		return;
	}
	
	Employee* cur_worker_ptr = &data[index];
	printf("\n���� ������ � ��������\n");
	switch (cur_worker_ptr->post)
	{
		case DIRECTOR:
			// ��� � ���� ������
			printf("\n������� ���������: ");
			scanf("%s", &cur_worker_ptr->param.director.surname);
			do 
			{	
				printf("���� ������: ");
				scanf("%d", &cur_worker_ptr->param.director.work_experience);
			} while (cur_worker_ptr->param.director.work_experience < 0);
			break;
		case COOK:
			// ������� � ���� ������
			do 
			{
				printf("\n������� ������: ");
				scanf("%f", &cur_worker_ptr->param.cook.rating);
			} while (cur_worker_ptr->param.cook.rating < 0.0 || cur_worker_ptr->param.cook.rating > 5.0);
			
			do 
			{
				printf("���� ������: ");
				scanf("%d", &cur_worker_ptr->param.cook.work_experience);
			} while(cur_worker_ptr->param.cook.work_experience < 0);
			break;
		case COURIER:
			// �������� � ����� ������
			do 
			{
				printf("\n�������� �������: ");
				scanf("%d", &cur_worker_ptr->param.courier.salary);
			} while(cur_worker_ptr->param.courier.salary < 0);
			printf("\n����� ��������: ");
			scanf("%s", &cur_worker_ptr->param.courier.district);
			break;
		default:
			printf("\n����������� ���������! �������� ��������! \n");
			return;	
	}
	
	int property;
	unsigned int mask = 0;
	printf("���� �������������� ���������: \n");
    printf("�������� ������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG5 : mask;
    
    printf("�������� ���������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG4 : mask;
    
    printf("�������� �����������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG3 : mask;
    
    printf("�������� ����������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG2 : mask;
    
    printf("�������� �������������? (0 - ���, 1 - ��): ");
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
			type_string = "��������";
			break;
		case COOK:
			type_string = "�����";
			break;
		case COURIER:
			type_string = "������";
			break;
	}
	
	printf("\n���������: %d. %s\n", type, type_string);
	
	switch (type)
	{
		case DIRECTOR:
			printf("�������: %s\n", worker->param.director.surname);
			printf("���� ������: %d\n", worker->param.director.work_experience);
			break;
		case COOK:
			printf("�������: %3.2f\n", worker->param.cook.rating);
			printf("���� ������: %d\n", worker->param.cook.work_experience);
			break;
			break;
		case COURIER:
			printf("��������: %d ���.\n", worker->param.courier.salary);
			printf("�����: %s\n", worker->param.courier.district);
			break;
	}
	
	printf("������ �������������� ���������: \n");
    if (worker->flags & FLAG5)
        printf("\t������������.\n");
    if (worker->flags & FLAG4)
        printf("\t���������������.\n");
    if (worker->flags & FLAG3)
        printf("\t�����������������.\n");
    if (worker->flags & FLAG2)
        printf("\t����������������.\n");
    if (worker->flags & FLAG1)
        printf("\t�������������.\n");
}

void print_all_workers(const Employee* data, const int size) 
{
	printf("\n������ ���� ����������:\n\n");
	int i = 0;
	for (i; i < size; i++)
	{
		printf("�������� %d", i + 1);
		print_worker(data + i);
		printf("\n");
	}
}

void print_workers_by_mask(const Employee* data, const int size)
{
	printf("��� ������ ���������� �� ��������� ���������� ���������� �����.\n�������� �� ��������� �������:\n");

    int property = 0;
    unsigned int mask = 0;
    printf("���� �������������� ���������: \n");
    printf("�������� ������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG5 : mask;
    
    printf("�������� ���������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG4 : mask;
    
    printf("�������� �����������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG3 : mask;
    
    printf("�������� ����������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG2 : mask;
    
    printf("�������� �������������? (0 - ���, 1 - ��): ");
    scanf("%d", &property);
    mask = property ? mask|FLAG1 : mask;
    
    int i;
    for (i = 0; i < size; i++)
    {
    	if ((data[i].flags & mask) == mask) 
		{
			printf("\n�������� ����� %d\n", i + 1);
			print_worker(&data[i]);
			printf("\n");
		}
	}
}

