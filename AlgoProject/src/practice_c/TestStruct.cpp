#include <stdio.h>
using namespace std;

struct MyStruct
{
	int a;
	char* data;
};

void print(MyStruct* pointer) {
	pointer->data = "void print";
	pointer->a = 10;
}


//���� | ����ü ������ �޸� �ּ� ��ȯ?
//�Լ��� ������ ����ü ������ ������ϴ�.���� & (�ּ� ������)�� ����ü ������ �޸� �ּҸ� ��ȯ�ϸ� �� �˴ϴ�.
//https://dojang.io/mod/page/view.php?id=531
MyStruct* print(MyStruct pointer) {
	pointer.data = "MyStruct* print";
	pointer.a = 10;
	return &pointer;
}

MyStruct* printTT(MyStruct *pointer) {
	pointer->data = "MyStruct* print";
	pointer->a = 10;
	return pointer;
}

MyStruct print1(MyStruct pointer) {
	pointer.data = "print1";
	pointer.a = 10;
	return pointer;
}

int main() {
	MyStruct my = { 0 };
	MyStruct* myPointer = new MyStruct();

	print(myPointer);
	printf("%s %d\n", myPointer->data, myPointer->a);

	MyStruct test1 = print1(my);
	printf("%s %d\n", test1.data, test1.a);

	MyStruct* test = printTT(myPointer);
	printf("%s %d\n", test->data, test->a);
	
	//MyStruct* test = print(my);
	//printf("%s %d\n", test->data, test->a);	

	return 0;
}

