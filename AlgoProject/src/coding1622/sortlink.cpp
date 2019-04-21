#ifndef _CRT_SEURE_NO_WARNINGS 
#define _CRT_SECURE_NO_WARNINGS 
#endif

#include <stdio.h>

#define PRT 1

struct node{
	node* next, *prev;
	int value;
	int num;
};


struct link
{
	node *head, *tail;
};

link ll;
node nodes[100];

void add(int num, int value) {
	//head 가 값이 지정되어 있지 않다면, 미리 넣고 계산한다.
	if (ll.head == 0) {
		nodes[0].value = 0;
		nodes[0].num = 0;
		ll.head = &nodes[0];
	}

	

	node* h = ll.head;
	node* last = 0;

	//정적배열에 신규 들어오는 값을 인덱싱배열로 선 할당
	nodes[num].num = num;
	nodes[num].value = value; 
	int nPri = nodes[num].num + nodes[num].value;

	while (h != 0) {
		int hPri = h->num + h->value;
		//내림차순 (큰순 -> 작은순)
		if (h != ll.head && hPri < nPri ) {
			node* prev = h->prev;			
			prev->next = &nodes[num];
			nodes[num].prev = prev; 

			nodes[num].next = h;
			h->prev = &nodes[num];
			return;
		}
		last = h;
		h = h->next;
	}

	last->next = &nodes[num];
	nodes[num].prev = last;
}

void pirntll() {
	printf("[%s]\n", __FUNCTION__);
	node* h = ll.head;
	while (h != 0) {
		//skip head 
		if (h->num != 0) {
			printf("%d %d %d\n", h->num, h->value, h->num + h->value);
		}
		h = h->next;
		
	}
}

int main() {
	add(1, 10);
	add(2, 10);
	add(5, 50);
	pirntll();
}	