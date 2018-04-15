#include <stdio.h>
#include <string.h>
#include <memory.h>
#include <stdlib.h>

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
	//key , Temp[count] = id 값 
	int id[5000];
	int count = 1;
}Hash;

struct HASHTABLE {
	Hash tb[MAX_TABLE] = { 0 };
	HASHTABLE() {
		/*
		for (int i = 0; i < MAX_TABLE; i++) {
		tb[i].key[0] = '\0';
		tb[i].value[0] = '\0';
		}
		*/
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



	bool find(const char *key, Hash* data)
	{

		unsigned long h = hash(key);
		int cnt = MAX_TABLE;
		int step = 1;

		while (tb[h].key[0] != 0 && cnt--)
		{

			if (strcmp(tb[h].key, key) == 0)
			{
				*data = tb[h];	//약간 어색한 코드.			
				return 1;
			}

			h = (h + 1) % MAX_TABLE;
		}
		return 0;
	}



	int add(const char *key, int fashionID)
	{
		Hash* test = new Hash();
		unsigned long h = hash(key);
		int i = 0;

		while (tb[h].key[0] != 0)
		{
			if (strcmp(tb[h].key, key) == 0)
			{
				tb[h].id[tb[h].count] = fashionID;
				tb[h].count++;
				return 0;
			}
			h = (h + 1) % MAX_TABLE;
		}

		//신규 fashion 이라면
		strcpy(tb[h].key, key);
		tb[h].id[tb[h].count] = fashionID;
		tb[h].count++;

		/* 왜 썼지.
		if (tb[h].count >= 1) {
		tb[h].count = tb[h].count + 1;
		}
		*/
		return 1;
	}
};

HASHTABLE hat;
HASHTABLE accessory;
HASHTABLE top;
HASHTABLE pants;
HASHTABLE shoes;
int fashionID = 1;
int max = 0;

FASHION fa[5500];

void init() {
	fashionID = 1;
	max = 0;

}

void addCatalog(FASHION fashion) {
	strcpy(fa[fashionID].accessory, fashion.accessory);
	strcpy(fa[fashionID].hat, fashion.hat);
	strcpy(fa[fashionID].pants, fashion.pants);
	strcpy(fa[fashionID].shoes, fashion.shoes);
	strcpy(fa[fashionID].top, fashion.top);

	//fashion Idx -> Fashion ID
	hat.add(fashion.hat, fashionID);
	accessory.add(fashion.accessory, fashionID);
	pants.add(fashion.pants, fashionID);
	shoes.add(fashion.shoes, fashionID);
	top.add(fashion.top, fashionID);
	fashionID++;
}

int* addCandidate(Hash* fa, int* candidate, int start) {
	int end = start + fa->count;

	for (int i = 1; i < end; i++) {
		candidate[start++] = fa->id[i];
		//*candidate = *candidate + 1;
	}

	return candidate;
}


int* getCandidate(int* candidate, int c) {

	int can[500];	//최적화 하고 싶어도 못하겠네

	for (int i = 1; i <= c; i++) {
		int idx = candidate[i];
		printf("%d", can[idx]);
		if (can[idx] == 0) {
			can[idx] = 1;
		}
	}
	return can;
}

int MemoSimiar[5][501][501];

int newFashion(FASHION fashion) {

	Hash* fashionPtr = new Hash();
	int candidate[500] = { 0 };
	max = 0;
	hat.find(fashion.hat, fashionPtr);
	int count = fashionPtr->count;
	addCandidate(fashionPtr, candidate, 1);

	accessory.find(fashion.accessory, fashionPtr);
	addCandidate(fashionPtr, candidate, count);
	//하 코드 진짜..
	count = (count - 1) + (fashionPtr->count - 1);

	pants.find(fashion.pants, fashionPtr);
	addCandidate(fashionPtr, candidate, count);
	count = (count - 1) + (fashionPtr->count - 1);

	shoes.find(fashion.shoes, fashionPtr);
	addCandidate(fashionPtr, candidate, count);
	count = (count - 1) + (fashionPtr->count - 1);

	top.find(fashion.top, fashionPtr);
	addCandidate(fashionPtr, candidate, count);
	count = (count - 1) + (fashionPtr->count - 1);

	int* data = getCandidate(candidate, count);

	HASHTABLE memo;

	//candidate
	for (int i = 1; i <= count; i++) {
		int idx = candidate[i];

		Hash* t = new Hash();
		if (memo.find(fa[idx].accessory, t)) {
			int y = t->count;
			int x = -1;
			if (memo.find(fashion.accessory, t)) {
				x = t->count;
			}
			if (MemoSimiar[1][y][x] != 0) {
				value = MemoSimiar[1][y][x];
			}
		}
		else {
			memo.add(fa[idx].accessory, t);
		}


		int a = calcSimilarity(fa[idx].accessory, fashion.accessory);
		int b = calcSimilarity(fa[idx].hat, fashion.hat);
		int c = calcSimilarity(fa[idx].pants, fashion.pants);
		int d = calcSimilarity(fa[idx].shoes, fashion.shoes);
		int e = calcSimilarity(fa[idx].top, fashion.top);

		if (a == 99 | b == 99 | c == 99 | d == 99 | e == 99) continue;

		int total = a + b + c + d + e;

		if (max < total) {
			max = total;
		}
		//candidate[i]; 		
	}


	//나눈 값을 하나로 모은다.
	return max;
}



/*Debugging 코드
max = 0;
for (int i = 1; i < 5000; i++) {
int a = calcSimilarity(fa[i].accessory, fashion.accessory);
int b = calcSimilarity(fa[i].hat, fashion.hat);
int c = calcSimilarity(fa[i].pants, fashion.pants);
int d = calcSimilarity(fa[i].shoes, fashion.shoes);
int e = calcSimilarity(fa[i].top, fashion.top);

if (a == 99 | b == 99 | c == 99 | d == 99 | e == 99) continue;

int total = a + b + c + d + e;

if (max < total) {
max = total;
}
//candidate[i];
}
*/