#ifndef WORKERS_MODULE
#define WORKERS_MODULE

// ������������ ������
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

void show_menu_workers();

Employee* create_worker_card(PostType post);

void add_worker(Employee** data, int* size, Employee* worker);

void remove_worker(Employee** data, int* size, const int index);

void update_worker(Employee* data, const int size, const int index);

void print_worker(const Employee* worker);

void print_all_workers(const Employee* data, const int size);

void print_workers_by_mask(const Employee* data, const int size);

#endif
