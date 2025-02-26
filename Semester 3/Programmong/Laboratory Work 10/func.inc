TYPE* rand_data(TYPE* arr, TYPE mn, TYPE mx) 
{
	int len = LEN(arr);
	int i;
	for (i = 0; i < len; i++) 
	{
		ELEM(arr, i) = (TYPE)(rand() % (mx + 1 - mn) + mn);
	}
	return arr;
}

TYPE* del(TYPE* arr) 
{
	TYPE val;
	#if VAR_F1 == 1
		TYPE mn = INT_MAX;
		int i;
		for (i = 0; i < LEN(arr); i++) 
		{
			if (ELEM(arr, i) < mn)
			{
				mn = ELEM(arr, i);
			}
		}
		val = mn;
	#else
		TYPE mx = -INT_MAX;
		int i;
		for (i = 0; i < LEN(arr); i++) 
		{
			if (ELEM(arr, i) > mx)
			{
				mx = ELEM(arr, i);
			}
		}
		val = mx;
	#endif

	i = 0;
	while (i < LEN(arr)) //-1
	{
		if (ELEM(arr, i) == val) 
		{
			int j;
			for (j = i; j < LEN(arr) - 1; j++) 
			{
				ELEM(arr, j) = ELEM(arr, j + 1);
			}	
			LEN(arr)--;
		}
		else 
		{
			i++;	
		}
	}
	arr = (TYPE*)realloc(arr, (LEN(arr)) * sizeof(TYPE));
	return arr;
}

TYPE* add(TYPE* arr, int k, TYPE mn, TYPE mx) 
{
	int len = LEN(arr) + k;
	arr = (TYPE*)realloc(arr, len * sizeof(TYPE));

	int i;
	for (i = LEN(arr) - 1; i < LEN(arr) + k - 1; i++) 
	{
		ELEM(arr, i + 1) = (TYPE)(rand() % (mx + 1 - mn) + mn);
	}
	LEN(arr) += k;
	return arr;
}

TYPE* move(TYPE* arr, int m)
{
	if (m > LEN(arr)) 
	{
		m = m % LEN(arr);
	}
	TYPE temp;
	int i;
	for (i = 1; i <= m; i++) 
	{
		temp = ELEM(arr, (LEN(arr) - 1));
		int j;
		for (j = LEN(arr) - 1; j >= 1 ; j--) 
		{
			ELEM(arr, j) = ELEM(arr, (j - 1));
		}
		ELEM(arr, 0) = temp;
	}
	return arr;
}

TYPE* find(TYPE* arr) 
{
	int val;
	#if VAR_F4 == 1
		val = 0;
	#else 
		val = 1;
	#endif
	
	int i, last = -1;
	for (i = 0; i < LEN(arr); i++) 
	{
		if (abs(ELEM(arr, i) % 2) == val && (ELEM(arr, i)) != 0) 
		{
			last = i;
		}
	}
	if (last != -1) 
	{
		ELEM(arr, last) = 0;
	}
	return arr;
}
