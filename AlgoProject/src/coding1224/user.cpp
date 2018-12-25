#include <stdio.h>
using namespace std;
struct DATE {
	int year;
	int month;
	int day;
	bool deleteFlag = 0;
};

enum REPEAT_TYPE {
	NONE,
	DAILY,
	WEEKLY,
	MONTHLY,
	YEARLY,
	EVERY3DAYS,
	EVERY5DAYS,
	EVERY10DAYS,
	TYPE_MAX
};

struct REPEAT {
	REPEAT_TYPE type;
	int cnt;
};

#define MAX 10

struct Calendar {
	int id[MAX+1]; //����� ��ƾ��ϳ�
};

struct ID {
	DATE date[MAX+1];
	int cnt = 0;
};

Calendar calendar[100][13][32];
ID IDList[MAX + 1];
int cnt = 0;

void init() {	
	
}

//������ id �� �ٽ� �������� �ʳ�.
void addSchedule(int id, DATE date, REPEAT repeat) {	
	printf("id = %d %d %d %d repeat = %d\n", id , date.year , date.month , date.day  , repeat.type);	
	//id�� ����� ��ϵ� ���� -> id �� DATE �����ؾ��� �ٺ���. 
	int cnt = IDList[id].cnt;
	IDList[id].cnt = IDList[id].cnt + 1;
	IDList[id].date[ IDList[id].cnt ] = date ;
		
	for (int i = 1; i <= IDList[id].cnt; i++) {
		printf("%d,%d,%d", IDList[id].date[i].year , IDList[id].date[i].month , IDList[id].date[i].day);
	}
	
	//date + repeat �̿��Ͽ�,  calendar�� id ����
}

void deleteScheduleByID(int id) {
	printf("deleteScheduleByID");
}

void deleteScheduleByDate(DATE date) {

}

int searchSchedule(DATE from, DATE to) {
	return 0;
}
