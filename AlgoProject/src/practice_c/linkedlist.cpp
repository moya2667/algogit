#ifndef _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <stdio.h>
#include <stdlib.h>
struct node {
	node* prev;
	node* next;
	char v;
};

struct linkedlist {
	node* head;
	node* tail;
};

linkedlist ll;

void addNode(char v) {
	node* n = (node *)malloc(sizeof(node));
	n->v = v;
	if (ll.head == nullptr) {
		ll.head = n;
		ll.tail = n;
	}
	else {
		ll.tail->next = n;
		n->prev =ll.tail;
		ll.tail = n;
		n->next = nullptr;
	}
}

int main() {
	
	addNode('a');
	addNode('b');
	addNode('c');

	node* h = ll.head;
	while (h != nullptr) {
		printf("%c ->", h->v);
		h = h->next;	
	}
	printf("\n");
	return 0;
}
