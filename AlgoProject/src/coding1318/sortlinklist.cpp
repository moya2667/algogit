#ifndef _CRT_SECURE_NO_WARNINGS 
#define _CRT_SECURE_NO_WARNINGS 
#endif

#include <stdio.h>

#define PRT 1

struct node {
	node* next;
	node* prev;
	int value;
	int id;
};

struct linklist {
	node *head;
	node *tail;	
};

linklist ll;
node nodelist[100];

void printll() {
	if (PRT) printf("[%s]\n", __FUNCTION__);
	node* h = ll.head;

	while (h != 0) {
		if (h->id != 999) {
			printf("%d : %d\n", h->id, h->value);
		}	
		h = h->next;
	}
}

void addsort(int id, int value) {
	//priorty로 만든다. 
	int priorty =  id + value;

	//head 값 미리 만들어 놓기
	if (ll.head == 0) {
		nodelist[99].id = 999;
		nodelist[99].value = 999;
		ll.head = &nodelist[99];				
	}

	node* h = ll.head;
	node* last = 0;

	//신규노드값 넣는다.
	nodelist[id].id = id;
	nodelist[id].value = value;

	while (h != 0) {
		int hPriorty = h->id + h->value;
		//첫번째 head 구분하기 위한 값이 아니라면,
		if (h->id != 999) { 			
			//ll에 있는 값의 우선순위가 신규 들어오는 값보다 더 낮다면
			//신규 값을 앞에 넣는다. n->h  
			if (hPriorty > priorty ){
				node* pe = h->prev;				
				pe->next = &nodelist[id];
				nodelist[id].prev = pe;

				nodelist[id].next = h;
				h->prev = &nodelist[id];
				return; //중요
			}
		}
		last = h;
		h = h->next;
	}
	//여기까지 흘러왔다면, 뒤에 넣는다는 의미
	last->next = &nodelist[id];
	nodelist[id].prev = last;
}

int main() {
	ll.head = 0;
	ll.tail = 0;

	if (PRT) printf("[%s] \n", __FUNCTION__);

	addsort(1, 100);	
	addsort(2, 100);
	addsort(3, 300);
	addsort(4, 30);
	printll();

}