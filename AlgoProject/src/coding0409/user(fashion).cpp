#include <stdio.h>
#include <string.h>
#include <memory.h>

#define MAX_CODE 10
#define MAX_KEY 10
#define MAX_DATA 128
#define MAX_TABLE 5500
using namespace std;
#pragma warning(disable : 4996)

typedef struct {
	char hat[MAX_CODE];
	char top[MAX_CODE];
	char pants[MAX_CODE];
	char shoes[MAX_CODE];
	char accessory[MAX_CODE];
}FASHION;

extern int calcSimilarity(const char code1[MAX_CODE], const char code2[MAX_CODE]);

typedef struct
{
	char key[MAX_KEY + 1];
	char value[MAX_KEY + 1];
	int idx[5000];
	int count = 0;
}Hash;

struct HASHTABLE {
	Hash tb[MAX_TABLE];
	HASHTABLE() {		
		for (int i = 0; i < MAX_TABLE; i++) {
			tb[i].key[0] = '\0';
			tb[i].value[0] = '\0';
		}
	};

	~HASHTABLE() {
	};

	unsigned long hash(const char *str)
	{
		unsigned long hash = 5381;
		int c;
		while (c = *str++)
		{
			hash = (((hash << 5) + hash) + c) % MAX_TABLE;
		}
		return hash % MAX_TABLE;
	}



	bool find(const char *key, Hash *data)
	{

		unsigned long h = hash(key);
		int cnt = MAX_TABLE;
		int step = 1;

		while (tb[h].key[0] != 0 && cnt--)
		{

			if (strcmp(tb[h].key, key) == 0)
			{
				//data = &tb[h];				
				
				strcpy(data->key , tb[h].key);
				strcpy(data->value, tb[h].value);
				data->count = tb[h].count;
				for (int i = 0; i <= tb[h].count; i++) {
					data->idx[i] = tb[h].idx[i];
				}
				
				return 1;
			}

			h = (h + 1) % MAX_TABLE;
		}
		return 0;
	}



	int add(const char *key, char *data, int idx)
	{
		Hash* test = new Hash();		
		unsigned long h = hash(key);
		int step = 1;
		int i = 0;

		while (tb[h].key[0] != 0)
		{
			if (strcmp(tb[h].key, key) == 0)
			{
				tb[h].count = tb[h].count+1;
				tb[h].idx[tb[h].count] = idx;
				printf("same\n");
				return 0;
			}
			h = (h + 1) % MAX_TABLE;

		}

		strcpy(tb[h].key, key);
		strcpy(tb[h].value, data);

		if (tb[h].count >= 1) {
			tb[h].count = tb[h].count + 1;
		}

		tb[h].idx[tb[h].count] = idx;
		return 1;
	}
};

HASHTABLE* hat = new HASHTABLE();
HASHTABLE* top;
HASHTABLE* pants;

void init() {
}


int start = 0;

void addCatalog(FASHION fashion) {
	Hash* test = new Hash();	
	printf("added hat key = %s\n", fashion.hat);
	hat->add(fashion.hat, "", start++);

	if (hat->find(fashion.hat, test)) {
		printf("key = %s %d\n", test->key , test->count);
		for (int i = 0; i <= test->count; i++) {
			printf("idx = %d\n", test->idx[i]);
		}
	}
}


int newFashion(FASHION fashion) {
	return 0;
}

