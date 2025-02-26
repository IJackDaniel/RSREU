#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// Количество предложений в тексте
// Возможные разделители: точка "."; Восклицательный знак "!"; Вопросительный знак "?"; Конец строки "\0"
int number_of_sentences(const char *str, const int len) 
{
	int result = 0;
	int i;
	for (i = 0; i < len + 1; i++)
	{
		char symb = *(str + i);
		if (symb == '.' || symb == '!' || symb == '?' || symb == '\0')
		{
			result++;
		}
	}
	return result;	
}

// Массив слов, заканчивающихся на гласную букву
char** array_of_words(char *str, const int len, int *number) 
{
	int count = 0, i = 0;
	while (*(str + i) != '\0') 
	{
		char symb = *(str + i);
		if (symb == '.' || symb == '!' || symb == '?' || symb == ' ' || symb == ',')
		{
			count++;
		}
		i++;
	}
	
	char**words = (char**)malloc((count + 1) * sizeof(char*));
	if (words == NULL) 
	{
		return NULL;
	}
	
	int word_ind = 0;
	char* word = strtok(str, " .!?,");
	while (word != NULL) 
	{	
		bool is_word = true;
		int j;
		for (j = 0; *(str + j) != '\0'; j++)
		{
			if (isdigit(*(word + j)))
			{
				is_word = false;
				break;
			}
		}
		
		bool last = false;
		int n = strlen(word);
		if (strchr("aAeEiIuUyYoOjJ", *(word + n - 1))) 
		{
			last = true;
		}
		
		if (is_word && last) 
		{
			*(words + word_ind) = (char*)malloc((strlen(word) + 1) * sizeof(char));
			if (*(words + word_ind) == NULL) 
			{
				int k;
				for (k = 0; k < word_ind; k++) 
				{
					free(*(words + k));
				}	
				free(words);
				return NULL;
			}
			strcpy(*(words + word_ind), word);
			word_ind++;
		}
		word = strtok(NULL, " .!?,");
	}
	
	*number = word_ind;
	return words;
}

// Функция, которая удаляет Nое предложение из текста
char* del_n_sentence(char *str, const int len, const int n)
{
	int count = 0, start = 0, end = 0;
	int i;
	for (i = 0; i < len + 1; i ++) 
	{
		if (strchr(".!?", str[i]) || str[i] == '\0')
		{
			count++;
			if (count == n) 
			{
				end = i + 1;
				if (i == len) 
				{
					end--;
				}
				break;
			}
			
			start = i + 1;
		}  	
	}
	if (count != n) 
	{
		return NULL;
	}
	int new_len = len - (end - start);
	
	char* new_str = (char*)malloc(new_len + 1);
	if (new_str == NULL) 
	{
		return NULL;
	}
	strncpy(new_str, str, start);
	strncpy(new_str + start, str + end, len - end);
	new_str[new_len] = '\0';
	
	return new_str;
}

int main(int argc, char *argv[]) 
{
	if ((!strcmp(argv[2], "-delete") && argc <= 3) || argc <= 2) 
	{
		printf("Error! Insufficient input parameters!\n");
		return 1;
	}
	
	if (strlen(argv[1]) > 300) 
	{
		printf("Error! The input text is too long!\n");
		return 2;
	}
	
	char * command = argv[2];
	
	int len;
	len = strlen(argv[1]);
	
	char* string = (char*) malloc(len);
	if (string == NULL) 
	{
		free(string);
		printf("Memory allocation error!\n");
		return 3;
	}
	
	string = argv[1];
	
	printf("\n");
	
	if (!strcmp(command, "-info")) 
	{
		int count = 0;
		count = number_of_sentences(string, len);
		printf("The number of sentences in the text is %d", count);
	} 
	else if (!strcmp(command, "-create")) 
	{
		int num = 0;
		char **arr = array_of_words(string, len, &num);	
		if (arr == NULL) 
		{
			printf("Error!");
			return 4;
		}
		
		int i;
		for (i = 0; i < num; i++) 
		{	
			puts(arr[i]);
			free(*(arr + i));
		}
		free(arr);
		arr = NULL;
	} 
	else if (!strcmp(command, "-delete")) 
	{
		int n;
		n = atoi(argv[3]);
		char* new_string = del_n_sentence(string, len, n);
		if (new_string == NULL) 
		{
			printf("Error! There is no sentence with this number in the text!");
			return 5;
		}
		puts(new_string);
		free(new_string);
	} 
	else 
	{
		printf("Error! Unknown command!");
		return 6;
	}
	
	free(string);
	
	printf("\n");
	
	return 0;
}

