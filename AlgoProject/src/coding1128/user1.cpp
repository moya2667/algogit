/*
user.cpp 작성하기
흐름 파악
*/

#define MEMORY_SIZE 65536
#define MAX_LEN 12
#define MAX_TABLE 2400 //  65536/2/15 이것인가? MAX_TABLE 결정하는 방법에 대해서
#define MAP_INDEX 57600 // 이것은 무엇

extern void memread(unsigned char str[], int pos, int len);
extern void memwrite(unsigned char str[], int pos, int len);
extern int mstrcmp(unsigned char str[], unsigned char[]);

int mystrlen(unsigned char * str) {
	int len = 0;
	while (*str++) {
		len++;
	}// str의 값이 빌때까지 ++ 해서 '\0' 을 만나면 종료됨
	return len;
}

void mystrcpy(unsigned char * tgt, unsigned char * src) {
	while (*src) { // 복사를 진행한 후
		*tgt++ = *src++;
	}
	*tgt = '\0'; // 문자열을 완성함
}

// 정형적인 해쉬함수 string을 입력받아서 hash 값을 리턴함
unsigned short myhash(unsigned char *str) { // 리턴 형태 0~65,535 해쉬값이 이 사이에 존재함
	unsigned short hash = 5381;
	int c; // str 값을 하나씩 입력 받음
	int len = mystrlen(str);
	for (int i = 0; i < len; i++) {
		c = *str++;
		hash = (((hash << 5)) + c + ((i*i)+i+41)) % MAX_TABLE;
	}
	/*
	while (c = *str++) {
		hash = (((hash << 5) + hash) + c) % MAX_TABLE;
	}
	*/
	return hash % MAX_TABLE;
}

/*
mem을 null로 초기화함
*/
bool init(int N)
{
	unsigned char zero[MEMORY_SIZE] = { '\0', };
	memwrite(zero, 0, MEMORY_SIZE);
	return true;
}

/*
1. key의 hash값을 구해서 그 위치에서 1byte를 읽어서 NULL인지 확인함
2. NULL 이라면 12byte buffer에 key를 copy해서 저장함
3. NULL 아니면 NULL을 찾을 때까지 다음 위치를 넣어봄 (hash + 1)%MAX_TABLE
4. value 에 대해서도 1~3의 작업을 진행함
5. 4번에서 구한 value_hash 값을 mem 배열의 key_hash *2 + MAP_INDEX 의 위치에 길이 2바이트 크기만큼 저장함
*/
unsigned short memsave(unsigned char str[])
{
	unsigned short hash;
	hash = myhash(str);
	unsigned char temp_buff[MAX_LEN] = { '\0', };
	memread(temp_buff, hash * MAX_LEN, 1);

	while (1) {
		if (temp_buff[0] == '\0') {
			memwrite(str, hash * MAX_LEN, MAX_LEN);
			break;
		}
		else {
			hash = (hash + 1) % MAX_TABLE;
			memread(temp_buff, hash * MAX_LEN, 1);
		}
	}
	return hash;
}

void put(unsigned char key_in[], unsigned char value_in[])
{
	unsigned short key_hash;
	unsigned short value_hash;
	key_hash = memsave(key_in);
	value_hash = memsave(value_in);
	memwrite((unsigned char*)&value_hash, key_hash * 2 + MAP_INDEX, 2);
}

/*
1. key의 hash 값을 구해서 hash 에 해당하는 index에서 12바이트를 읽어와서 key 와 동일한지 비교함
2. 동일하지 않으면 hash + 1 % MAX_TABLE 처리해서 key 와 동일한 12바이트가 나올 때까지 memread를 반복함
3. 그렇게 구한 hash 값으로 index해서 2바이트 저장된 value의 id를 가져옴
4. 키값을 지우고, value의 id를 이용해서 value 값도 지움
*/

unsigned short find_str(unsigned char * str) {
	unsigned short hash = myhash(str);
	unsigned char temp_buff[MAX_LEN] = { '\0', };
	memread(temp_buff, hash * MAX_LEN, MAX_LEN);

	while (1) {
		if (mstrcmp(temp_buff, str) == 1)
			return hash;
		else {
			hash = (hash + 1) % MAX_TABLE;
			memread(temp_buff, hash * MAX_LEN, MAX_LEN);
		}
	}
}

void del(unsigned char key_in[])
{
	unsigned short key_hash = find_str(key_in);
	unsigned short value_hash = 0;
	unsigned char delete_buff[MAX_LEN] = { '\0', };

	memread((unsigned char *)&value_hash, key_hash & 2 + MAP_INDEX, 2);
	memwrite(delete_buff, key_hash * MAX_LEN, MAX_LEN);
	memwrite(delete_buff, value_hash * MAX_LEN, MAX_LEN);
}

/*
1. delte의 3번까지 수행함
2. value id 값을 이용해 value 값을 가져와 return 함
*/
void get(unsigned char key_in[], unsigned char value_out[])
{
	unsigned short key_hash = find_str(key_in);
	unsigned short value_hash = 0;
	memread((unsigned char *)&value_hash, key_hash * 2 + MAP_INDEX, 2);

	memread(value_out, value_hash * MAX_LEN, MAX_LEN); // hash 값에서 12바이트를 곱하면 그 위치인가 봄
}

/*
1. delete의 3번까지 하되 key가 아닌 value 값으로 찾음
2. value의 id 값을 가지고 mapping tabble에서 key의 id를 찾음
3. key의 id를 이용해 index*12바이트를 하여 retrun 함
*/
void get_key(unsigned char value_in[], unsigned char key_out[])
{
	unsigned short value_hash = find_str(value_in);
	unsigned short value_find = 0;
	register int i = 0;
	for (i = 0; i < MAX_TABLE; i++) {
		memread((unsigned char *)&value_find, i * 2 + MAP_INDEX, 2);
		if (value_find == value_hash)
			break;
	}
	memread(key_out, i*MAX_LEN, MAX_LEN);
}