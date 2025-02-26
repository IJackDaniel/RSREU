#include <stdio.h>
#include <stdlib.h>

typedef enum 
{
	FLAG1 = 1,
	FLAG2 = 2,
	FLAG3 = 4,
	FLAG4 = 8,
	FLAG5 = 16,
} Flags;
// ����      | FLAG1 = 1     | FLAG2 = 2        | FLAG3 = 4         | FLAG4 = 8        | FLAG5 = 16   | 
// ��������  | ������������� | ���������������� | ����������������� | ��������������� | ������������ |

// ������������ ����������
typedef enum 
{
	DIRECTOR = 1,
	COOK,
	COURIER,
} PostType;

// ��������� ���������� � ������� ������
struct DIRECTOR 
{
	char surname[25];  		// ��� ���������
	int work_experience; 	// ���� ������
};

struct COOK
{
	float rating;  			// ������� ������
	int work_experience;  	// ���� ������
};

struct COURIER 
{
	int salary;  			// ��������
	char district[50];  	// ����� ������
};

// ����������� ��� ��������� ���������� � ����������� �� ���������
union Worker 
{
	struct DIRECTOR director;
	struct COOK cook;
	struct COURIER courier;
};

// ��������� Employee (�������� ���������)
typedef struct 
{
	PostType post; 				// ���������� ���������
	union Worker param; 		// ��������� ���������
	unsigned int flags : 5;   	// ����� � ������� ����
} Employee;

void show_menu() 
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

int main() 
{
	system("chcp 1251");	// ������� �������� �������� �����
	
	Employee* workers = NULL;
	int size = 0;
	int command;
	int type;
	int index;
	
	show_menu();
	
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
				printf("\n����� �� ���������...\n");
				free(workers);
				workers = NULL;
				break;
			default:
				show_menu();
				break;
		}
		
	} while (command != 6);
	
	printf("\n������ ��������� ���������!");
	
	return 0;
}
