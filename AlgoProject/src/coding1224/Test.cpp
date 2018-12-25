#include <stdio.h>

struct Phone {    // 휴대전화 구조체
	int areacode;                 // 국가번호
	unsigned long long number;    // 휴대전화 번호
};

struct Person {    // 사람 구조체
	char name[20];         // 이름
	int age;               // 나이
	struct Phone phone[10];    // 휴대전화. 구조체를 멤버로 가짐
};

int main()
{
	struct Person p1;

	p1.phone[0].areacode = 82;          // 변수.멤버.멤버 순으로 접근하여 값 할당
	p1.phone[0].number = 3045671234;    // 변수.멤버.멤버 순으로 접근하여 값 할당

	printf("%d %llu\n", p1.phone[0].areacode, p1.phone[0].number);    // 82 3045671234

	return 0;
}