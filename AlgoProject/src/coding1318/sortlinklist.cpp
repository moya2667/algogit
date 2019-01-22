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
	//priorty�� �����. 
	int priorty =  id + value;

	//head �� �̸� ����� ����
	if (ll.head == 0) {
		nodelist[99].id = 999;
		nodelist[99].value = 999;
		ll.head = &nodelist[99];				
	}

	node* h = ll.head;
	node* last = 0;

	//�űԳ�尪 �ִ´�.
	nodelist[id].id = id;
	nodelist[id].value = value;

	while (h != 0) {
		int hPriorty = h->id + h->value;
		//ù��° head �����ϱ� ���� ���� �ƴ϶��,
		if (h->id != 999) { 			
			//ll�� �ִ� ���� �켱������ �ű� ������ ������ �� ���ٸ�
			//�ű� ���� �տ� �ִ´�. n->h  
			if (hPriorty > priorty ){
				node* pe = h->prev;				
				pe->next = &nodelist[id];
				nodelist[id].prev = pe;

				nodelist[id].next = h;
				h->prev = &nodelist[id];
				return; //�߿�
			}
		}
		last = h;
		h = h->next;
	}
	//������� �귯�Դٸ�, �ڿ� �ִ´ٴ� �ǹ�
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