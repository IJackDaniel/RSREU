#ifndef WORKERS_MODULE
#define WORKERS_MODULE

// Перечисление флагов
typedef enum 
{
	FLAG1 = 1,
	FLAG2 = 2,
	FLAG3 = 4,
	FLAG4 = 8,
	FLAG5 = 16,
} Flags;
// Флаг      | FLAG1 = 1     | FLAG2 = 2        | FLAG3 = 4         | FLAG4 = 8        | FLAG5 = 16   | 
// Значение  | Ответственный | Коммуникабельный | Стрессоустойчивый | Целеустремлённый | Компетентный |

// Перечисление должностей
typedef enum 
{
	DIRECTOR = 1,
	COOK,
	COURIER,
} PostType;

// Структуры работников с разными полями
struct DIRECTOR 
{
	char surname[25];  		// ФИО директора
	int work_experience; 	// Стаж работы
};

struct COOK
{
	float rating;  			// Рейтинг повара
	int work_experience;  	// Стаж работы
};

struct COURIER 
{
	int salary;  			// Зарплата
	char district[50];  	// Район работы
};

// Объединение для различных параметров в зависимости от должности
union Worker 
{
	struct DIRECTOR director;
	struct COOK cook;
	struct COURIER courier;
};

// Структура Employee (основная структура)
typedef struct 
{
	PostType post; 				// Занимаемая должность
	union Worker param; 		// Параметры работника
	unsigned int flags : 5;   	// Флаги в битовом поле
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
