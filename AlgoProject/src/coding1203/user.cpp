#include <iostream>

#define ALLOW_CODE_COUNT		32
#define MAX_CODE_SIZE		20000
#define CODE_LENGTH			6

using namespace std;
#define ASCII 128
char* getCandiBitAgain(char d) {
	char candi[9] = { '\0' };
	//System.out.println(d + "에 대한 후보군입니다");
	int bit[9] = { 0, 1, 2, 4, 8, 16, 32, 64, 128 };


	for (int i = 0; i < 9; i++) {
		//candi[i] = 'a';
		candi[i] = (char)(d ^ bit[i]);
		printf("%c", candi[i]);
	}
	printf("\n");
	//printf("%s\n", candi);
	return candi;
}

struct trie {
	bool isFinal = false;
	int count = 0;
	char word;
	trie *node[ASCII];

	trie() {
		for (int i = 0; i < ASCII; i++)
			node[i] = nullptr;
	};

	~trie() {
		for (int i = 0; i < ASCII; i++)
		{
			if (node[i] != nullptr)
				delete node[i];
		}
	};

	void insert(char* str, int idx, int len) {
		if (idx == len) {
			isFinal = true;
			return;
		}

		int c = str[idx] ;

		if (node[c] == nullptr) {
			node[c] = new trie();
			node[c]->word = str[idx];
		}
		node[c]->insert(str, idx + 1, len);
	}

	bool find(char* str, int idx, int len) {
		if (idx == len && isFinal) {
			return true;
		}

		if (str[idx] == len && !isFinal) {
			return false;
		}

		char c = str[idx];
		char* candi = getCandiBitAgain(c);
		for (int i = 0; i < 9; i++) {
			int idx = candi[i];
			if (idx>=0 && idx < 128 && node[idx] != '\0') {
				printf("1bit 후보군을 찾아 서치시작합니다.\n");
				printf("1bit 후보군은 : %c 입니다\n" , node[idx]->word );
				return node[idx]->find(str, idx + 1 , len);
			}
			// System.out.println("1bit 후보군 중 맞는 주소록이 없습니다");
		}
		return false;
	}

	int getlength(char* data) {
		for (int i = 0; i < 1000; i++) {
			if (data[i] == '\0') {
				return i;
			}
		}
	}
};


struct trie t;
void getAllowCode(char code[ALLOW_CODE_COUNT + 1]) {

}

void getCode(int N, char code[MAX_CODE_SIZE][CODE_LENGTH + 1]) {

	for (int i = 0; i < N; i++) {
		t.insert(code[i], 0, CODE_LENGTH+1);		
	}

	/*
	bool find = t.find(code[0], 0, CODE_LENGTH + 1);
	printf("%d\n", find);
	*/
}

void restore(char errorCode[CODE_LENGTH + 1], char prediction[CODE_LENGTH + 1]) {

	printf(" %d \n" , t.find(errorCode, 0, CODE_LENGTH + 1));

	printf("1");

}
