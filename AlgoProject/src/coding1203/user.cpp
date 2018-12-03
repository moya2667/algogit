#include <iostream>

#define ALLOW_CODE_COUNT		32
#define MAX_CODE_SIZE		20000
#define CODE_LENGTH			6

using namespace std;
#define ASCII 128
char tree[31] = "";
int totalCnt = 0;

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

		int c = str[idx] - 'a';

		if (node[c] == nullptr) {
			node[c] = new trie();
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

		int c = str[idx] - 'a';

		if (node[c] == NULL) return false;

		return node[c]->find(str, idx + 1, len);
	}

	int getlength(char* data) {
		for (int i = 0; i < 1000; i++) {
			if (data[i] == '\0') {
				return i;
			}
		}
	}
};

void getAllowCode(char code[ALLOW_CODE_COUNT + 1]) {

}

void getCode(int N, char code[MAX_CODE_SIZE][CODE_LENGTH + 1]) {

}

void restore(char errorCode[CODE_LENGTH + 1], char prediction[CODE_LENGTH + 1]) {

}
