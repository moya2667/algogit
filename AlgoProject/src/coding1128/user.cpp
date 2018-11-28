#include <stdio.h>

extern void memread(unsigned char str[], int pos, int len);
extern void memwrite(unsigned char str[], int pos, int len);

/*
비휘발성 메모리를 가진 IoT 단말의 Data를 관리하는 DataBase API를 구현하라.
구현할 함수
bool init(int N) : 초기화 함수, 각 TC별 처음 1회 호출된다.
void put(unsigned char key_in[], unsigned char value_in[]) : 주어진 key와 value를 Database에 저장한다.
void del(unsigned char key_in[]) : key값으로 주어진 문자열을 Database에서 삭제한다.
void get(unsigned char key_in[], unsigned char value_out[]) : key값을 input으로 value를 찾아서 value_out에 기록한다.
void get_key(unsigned char value_in[], unsigned char key_out[]) : value값을 input으로 value를 찾아서 key_out에 기록한다.

main.cpp에서 주어지는 API
void memread(unsigned char str[], int pos, int len) : pos 위치에서 len만큼 memory를 읽어 str에 저장한다.
void memwrite(unsigned char str[], int pos, int len) : pos 위치에서 len만큼 str에 저장된 값을 memory에 저장한다.


[ 제약사항 / 참고사항 ]
1. 주어진 Key값과 Value값은 Unique한 값이다. (Value가 AAA이 입력으로 들어온 경우, 동일한 AAA를 가진 Value는 입력으로 주이지지 않는다. Key도 동일하다.)
2. 입력 Data 수 N은 최소 10에서 최대 2400까지 주어진다.
3. 명령어 수행은 put, del, get, get_key를 모두 합쳐서 최대 50,000번 호출될 수 있다.
4. 비휘발성 memory size는 65536 byte (64 Kbyte) 이다.
5. Global 변수 또는 Static 변수는 사용할 수 없다. 사용 시, 100점이 나오더라도 Fail로 간주된다.
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
	//'\0' 까지 포함 
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
	// database에 필드를 저장.
	int keyIDX = getHash(key_in);
	// write 할수 있는 공간 확인 후 write 
	// write 할수 있는 공간이 아니면, 다음 hash idx로 이동해서 검사 
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



// key 값으로 주어진 문자열을 통해 database에서 삭제.
void del(unsigned char key_in[])
{
	// key 맞는지 찾고, 맞으면, value_in '\0' 처리하고 , key 도 '\0' 처리한다 
	int keyIDX = getHash(key_in);
	while (1) {
		int loopC = MAX_TABLE - keyIDX;

		while (loopC >= 0) {
			unsigned char read_key[12];
			memread(read_key, getMemIdx(keyIDX), 12);
			//같으면 
			if (mystrcmp(key_in, read_key)) {				
				break;
			}
			keyIDX = keyIDX + 1;
			loopC--;
		}

		//메모리 삭제 '\0' 으로 
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
	//read 할수 있는 공간 확인 후 read
	//read 할수 있는 공간이 아니면, 다음 hash idx로 이동해서 검사 
	while (1) {		
		int loopC = MAX_TABLE - keyIDX;

		while (loopC >= 0) {
			unsigned char read_key[12];
			memread(read_key, getMemIdx(keyIDX), 12);
			//같으면 
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
	// value값을 input으로 value를 찾아서 key_out에 기록.

	//START_VALUEIDX MAX_TABLE * 13
	//read 할수 있는 공간 확인 후 read
	//read 할수 있는 공간이 아니면, 다음 hash idx로 이동해서 검사 
	printf("get_key Command : reqeust value %s ", value_in);
	int cnt = 0;
	for (int i = 0; i< 2400 ; i++) {
		
		unsigned char read_key[12];
		unsigned char read_value[12];		
		
		memread(read_value, START_VALUEIDX + cnt  , 12);
		
		//같으면 
		if (mystrcmp(value_in, read_value)) {
			
			memread(key_out, cnt, 12);
			printf("value = %s key=%s", read_value , key_out);			
			break;
		}
		cnt = cnt + 12;		
	}

	printf("\n");
}



