#include <stdio.h>

extern void memread(unsigned char str[], int pos, int len);
extern void memwrite(unsigned char str[], int pos, int len);

/*
���ֹ߼� �޸𸮸� ���� IoT �ܸ��� Data�� �����ϴ� DataBase API�� �����϶�.
������ �Լ�
bool init(int N) : �ʱ�ȭ �Լ�, �� TC�� ó�� 1ȸ ȣ��ȴ�.
void put(unsigned char key_in[], unsigned char value_in[]) : �־��� key�� value�� Database�� �����Ѵ�.
void del(unsigned char key_in[]) : key������ �־��� ���ڿ��� Database���� �����Ѵ�.
void get(unsigned char key_in[], unsigned char value_out[]) : key���� input���� value�� ã�Ƽ� value_out�� ����Ѵ�.
void get_key(unsigned char value_in[], unsigned char key_out[]) : value���� input���� value�� ã�Ƽ� key_out�� ����Ѵ�.

main.cpp���� �־����� API
void memread(unsigned char str[], int pos, int len) : pos ��ġ���� len��ŭ memory�� �о� str�� �����Ѵ�.
void memwrite(unsigned char str[], int pos, int len) : pos ��ġ���� len��ŭ str�� ����� ���� memory�� �����Ѵ�.


[ ������� / ������� ]
1. �־��� Key���� Value���� Unique�� ���̴�. (Value�� AAA�� �Է����� ���� ���, ������ AAA�� ���� Value�� �Է����� �������� �ʴ´�. Key�� �����ϴ�.)
2. �Է� Data �� N�� �ּ� 10���� �ִ� 2400���� �־�����.
3. ��ɾ� ������ put, del, get, get_key�� ��� ���ļ� �ִ� 50,000�� ȣ��� �� �ִ�.
4. ���ֹ߼� memory size�� 65536 byte (64 Kbyte) �̴�.
5. Global ���� �Ǵ� Static ������ ����� �� ����. ��� ��, 100���� �������� Fail�� ���ֵȴ�.
*/

#define MAX_TABLE 2400
#define START_VALUEIDX MAX_TABLE*13


bool init(int N)
{
	return true;
}

unsigned long getHash(unsigned char* data)
{
	unsigned long hash = 5381;
	int c;

	while (c = *data++)
	{
		hash = (((hash << 5) + hash) + c) % MAX_TABLE;
	}

	return hash % MAX_TABLE;
}

int getMemIdx(int keyIdx) {
	return keyIdx * 12;
}

bool canWrite(long idx) {
	unsigned char temp[12];
	memread(temp, getMemIdx(idx), 1);
	if (temp[0] != '\0') {
		return false;
	}
	return true;
}


int mystrlen(unsigned const char *str)
{
	int len = 0;
	while (*(str + len) != '\0')
	{
		len++;
	}
	//'\0' ���� ���� 
	return len + 1;
}

bool mystrcmp(unsigned char *ori, unsigned char *target) {
	int len = mystrlen(ori);
	for (int i = 0; i < len; i++) {
		if (ori[i] != target[i]) {
			return false;
		}
	}
}



void put(unsigned char key_in[], unsigned char value_in[])
{
	// database�� �ʵ带 ����.
	int keyIDX = getHash(key_in);
	// write �Ҽ� �ִ� ���� Ȯ�� �� write 
	// write �Ҽ� �ִ� ������ �ƴϸ�, ���� hash idx�� �̵��ؼ� �˻� 
	while(1){ 
		if (!canWrite(keyIDX)) {
			keyIDX = keyIDX + 1;
		}
		memwrite(key_in, getMemIdx(keyIDX), 12);		
		memwrite(value_in, START_VALUEIDX + getMemIdx(keyIDX), 12);
		printf("put command key = %s %d, value=%s %d\n", key_in, keyIDX, value_in, START_VALUEIDX + getMemIdx(keyIDX));
		break;
	}
}



// key ������ �־��� ���ڿ��� ���� database���� ����.
void del(unsigned char key_in[])
{
	// key �´��� ã��, ������, value_in '\0' ó���ϰ� , key �� '\0' ó���Ѵ� 
	int keyIDX = getHash(key_in);
	while (1) {
		int loopC = MAX_TABLE - keyIDX;

		while (loopC >= 0) {
			unsigned char read_key[12];
			memread(read_key, getMemIdx(keyIDX), 12);
			//������ 
			if (mystrcmp(key_in, read_key)) {				
				break;
			}
			keyIDX = keyIDX + 1;
			loopC--;
		}

		//�޸� ���� '\0' ���� 
		unsigned char data[12] = { '\0', };		

		/*
		unsigned char testkey[12];
		unsigned char testvalue[12];
		memread(testkey, keyIDX, 12);
		memread(testvalue, START_VALUEIDX + keyIDX, 12);
		printf("command key = %s , data  = %s \n", testkey, testvalue );
		*/
		memwrite(data, getMemIdx(keyIDX), 12);
		memwrite(data, START_VALUEIDX + getMemIdx(keyIDX), 12);	

		printf("del command key = %s %d, data  = %s %d\n", key_in, getMemIdx(keyIDX), data, START_VALUEIDX + getMemIdx(keyIDX));		
		break;
	}
}

void get(unsigned char key_in[], unsigned char value_out[])
{	
	int keyIDX = getHash(key_in);
	//read �Ҽ� �ִ� ���� Ȯ�� �� read
	//read �Ҽ� �ִ� ������ �ƴϸ�, ���� hash idx�� �̵��ؼ� �˻� 
	while (1) {		
		int loopC = MAX_TABLE - keyIDX;

		while (loopC >= 0) {
			unsigned char read_key[12];
			memread(read_key, getMemIdx(keyIDX), 12);
			//������ 
			if (mystrcmp(key_in, read_key)) {				
				break;
			}
			keyIDX = keyIDX + 1;
			loopC--;
		}

		unsigned char data[12];
		memread(data, keyIDX * 12, 12);
		memread(data, START_VALUEIDX + getMemIdx(keyIDX), 12);

		printf("get command key = %s %d, data  = %s %d\n", key_in ,getMemIdx(keyIDX) , data , START_VALUEIDX + getMemIdx(keyIDX));
		break;
	}	
	
}

void get_key(unsigned char value_in[], unsigned char key_out[])
{
	// value���� input���� value�� ã�Ƽ� key_out�� ���.

	//START_VALUEIDX MAX_TABLE * 13
	//read �Ҽ� �ִ� ���� Ȯ�� �� read
	//read �Ҽ� �ִ� ������ �ƴϸ�, ���� hash idx�� �̵��ؼ� �˻� 
	printf("get_key Command : reqeust value %s ", value_in);
	int cnt = 0;
	for (int i = 0; i< 2400 ; i++) {
		
		unsigned char read_key[12];
		unsigned char read_value[12];		
		
		memread(read_value, START_VALUEIDX + cnt  , 12);
		
		//������ 
		if (mystrcmp(value_in, read_value)) {
			
			memread(key_out, cnt, 12);
			printf("value = %s key=%s", read_value , key_out);			
			break;
		}
		cnt = cnt + 12;		
	}

	printf("\n");
}



