#include <stdio.h>
using namespace std;

struct MyStruct
{
	int a;
	char* data;
};

void print(MyStruct* pointer) {
	pointer->data = "aaa";
	pointer->a = 10;
}

MyStruct* print(MyStruct pointer) {
	pointer.data = "aaa";
	pointer.a = 10;
	return &pointer;
}

MyStruct print1(MyStruct pointer) {
	pointer.data = "aaa";
	pointer.a = 10;
	return pointer;
}

int main() {
	MyStruct my = { 0 };
	MyStruct* myPointer = new MyStruct();

	print(myPointer);
	printf("%s %d\n", myPointer->data, myPointer->a);

	MyStruct* test = print(my);	
	printf("%s %d\n", test->data, test->a);

	MyStruct test1 = print1(my);
	printf("%s %d\n", test1.data, test1.a);
	return 0;
}



