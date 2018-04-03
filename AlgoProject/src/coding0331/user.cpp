#include <iostream>

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

	void insert(char* str, int idx , int len) {
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

	bool find(char* str, int idx , int len) {
		if ( idx == len && isFinal) {
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

struct node {
	int idx;
	char *b;
};

int main() {

	struct trie t;
	t.insert("abc", 0, 3);
	t.insert("ab1", 0, 3);
	t.insert("ab2", 0, 3);

	printf("%d \n" , t.find("abc", 0, 3));
	printf("%d \n", t.find("ab1", 0, 3));
	printf("%d \n", t.find("ab3", 0, 3));

	char* a = "aaaa";
//	printf("length = %d ", strlen(a));
	printf(" length = %d\n" ,t.getlength("bbbb"));
//	int a = 0; 
	printf("%d, AAA\n" , 0);	
	struct node n; 
	n.idx = 1;
	n.b = "bbbb";
	printf("%d , %s \n",n.idx, n.b);
	return 0;
}