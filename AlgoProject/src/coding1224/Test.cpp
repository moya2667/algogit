#include <stdio.h>

struct Phone {    // �޴���ȭ ����ü
	int areacode;                 // ������ȣ
	unsigned long long number;    // �޴���ȭ ��ȣ
};

struct Person {    // ��� ����ü
	char name[20];         // �̸�
	int age;               // ����
	struct Phone phone[10];    // �޴���ȭ. ����ü�� ����� ����
};

int main()
{
	struct Person p1;

	p1.phone[0].areacode = 82;          // ����.���.��� ������ �����Ͽ� �� �Ҵ�
	p1.phone[0].number = 3045671234;    // ����.���.��� ������ �����Ͽ� �� �Ҵ�

	printf("%d %llu\n", p1.phone[0].areacode, p1.phone[0].number);    // 82 3045671234

	return 0;
}