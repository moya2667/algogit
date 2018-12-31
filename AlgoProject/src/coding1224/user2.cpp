/*
void addSchedule(int id, DATE date, REPEAT repeat);
������ �߰��Ѵ�.
id: 1~10,000 ���� ������ �ߺ��� ����.
date: ���� ������. DATE ������ ������ ������ ������ ����.
struct DATE {
int year; // 2000~2099
int month; // 1~12
int day; // 1~31 ������ �ٸ�.
};
repeat: REPEAT ������ ������ ������ ������ ����.
struct REPEAT {
REPEAT_TYPE type; // �ݺ� ����
int cnt; // 1~100 �ݺ� Ƚ��
};
type�� �ݺ� �����̸� ���� ���� �� �ϳ��� ������.
enum REPEAT_TYPE {
NONE,
DAILY, // ����
WEEKLY, // ���� �����ϰ� ���� ����
MONTHLY, // �ſ� ������ ��¥
YEARLY, // �ų� ���� ����
EVERY3DAYS, // �����Ϻ��� 3�ϸ���
EVERY5DAYS, // �����Ϻ��� 5�ϸ���
EVERY10DAYS, // �����Ϻ��� 10�ϸ���
TYPE_MAX
};
type�� NONE�� ��� cnt�� �ݵ�� 1�̴�.
������ �ݺ��Ǵ� ��� 2099�� 12�� 31���� ���ı��� �ݺ��Ǵ� ������ �־����� �ʴ´�.
type�� MONTHLY�� ��� day�� 28 ���� ���� ������.
type�� YEARLY�� ��� month�� 2��� day���� 29�� ������ ���� ����
*/
#include <stdio.h>
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

struct DATE {
	int year;
	int month;
	int day;
};

struct REPEAT {
	REPEAT_TYPE type;
	int cnt;
};

#define MAX 10001

//��¥��� task ��� ���� 
//calendar[100][13][366]
// + task 1 
// + task 2
// + task 3

struct task {
	task* prev;
	task* next;
	int id;	
	DATE date;
	bool deleteFlag = 0;
};

struct tasklist {
	task* head;
	task* tail;
};

struct calendar {
	tasklist list;
	bool deleteFlag = 0;
};

struct IDLIST {
	task tasks[1000];	//������ �����ΰ�.?	
	int cnt = 0;
	bool deleteFlag = 0;
};


calendar cal[100][13][366];
IDLIST tList[MAX];
 
//task ��� date ��� ���� 
//task[MAX]
// + date 1
// + date 2
// + date 3


extern int daysOfMonth(int y, int m);

void init() {
	/*�ʱ�ȭ����� ���߿� ���� */
	for (int i = 0; i < 100; i++) {
		for (int j = 0; j < 13; j++) {
			for (int k = 0; k < 366; k++) {
				cal[i][j][k].deleteFlag = 0;
				cal[i][j][k].list.head = nullptr;
				cal[i][j][k].list.tail = nullptr;					
			}
		}
	}

	for (int i = 0; i < 100; i++) {
		tList[i].cnt = 0;
		tList[i].deleteFlag = 0;
		for (int j = 0; j < 1000; j++) {
			tList[i].tasks[j].deleteFlag = 0;
			tList[i].tasks[j].id= 0;
			tList[i].tasks[j].next = nullptr;
			tList[i].tasks[j].prev = nullptr;
			tList[i].tasks[j].date.year = NULL;
			tList[i].tasks[j].date.month = NULL;
			tList[i].tasks[j].date.day = NULL;
		}
		
	}	
}


void addCalToTask(tasklist* ll , task* t) {
	if (ll->head == nullptr) {
		ll->head = t;
		ll->tail = t;
	}
	else {
		ll->tail->next = t;
		t->prev = ll->tail;
		ll->tail = t;
		t->next = nullptr;

	}
	return;
}

void printDebugll(tasklist ll) {
	task* h = ll.head;
	while (h != nullptr) {
		printf("debug id:%d %d-%d-%d\n", h->id , h->date.year, h->date.month, h->date.day);
		h = h->next;
	}
}

//2�ñ���
void addSchedule(int taskId, DATE date, REPEAT repeat) {
	//cal �� taskid ��� 
	int y = date.year%2000;
	int m = date.month;
	int d = date.day;	

	for (int i=0 ; i < repeat.cnt ; i++) {		
		
		int cnt = ++tList[taskId].cnt;
		tList[taskId].tasks[cnt].id = taskId;
		tList[taskId].tasks[cnt].date.year = y;
		tList[taskId].tasks[cnt].date.month = m;
		tList[taskId].tasks[cnt].date.day = d;
		tList[taskId].tasks[cnt].deleteFlag = 0;
	
		//calendar �� task �� ��� 		
		addCalToTask(&cal[y][m][d].list, &tList[taskId].tasks[cnt]);
		cal[y][m][d].deleteFlag = 0;		

		switch (repeat.type) {
		case NONE : 
			//�̹� ���� �����
			break;
		case DAILY:
			d++;
			break;
		case WEEKLY:				
			d = d + 7;
			break;
		case MONTHLY:
			m++;
			if (m == 13) {  //���� ã�°� �̤�
				m = 1;
				y++;
			}
			break;
		case YEARLY:				
			y++;
			break;
		case EVERY3DAYS:				
			d = d + 3;
			break;
		case EVERY5DAYS:				
			d = d + 5;
			break;
		case EVERY10DAYS:				
			d = d + 10;
			break;
		}

		//��¥�� ������ ���Ǹ鼭 ��/���� �ٲ�� �ֱ⶧����..ȣ��Ǿ���Ѵ�.
		int endDay = daysOfMonth(y+2000, m);
		if (d > endDay) {
			d = d - endDay;
			//������ ���ϰ�� ��/�� ����
			if (m == 12) {
				y++;
				m = 1;				
			}
			//���� ���� 
			else {
				m++;
			}
		}		
	}
}

//ID�� ���� DATE ������ Calendar���� ���� 
//IDLIST ���� ID�� �����Ѵ� 
void deleteScheduleByID(int id) {	
	int cnt = tList[id].cnt;
	for (int i = 1; i <= cnt; i++) {
		task t = tList[id].tasks[i];
		int y = t.date.year;
		int m = t.date.month;
		int d = t.date.day;
		//cal �ڷᱸ������ �ش� id �� ã�Ƴ���, �����Ѵ�.(flag)
		task* h = cal[y][m][d].list.head;
		while (h != nullptr) {
			if (h->id == id) {
				h->deleteFlag = 1;
				break;
			}
			h = h->next;
		}
	}
	tList[id].deleteFlag = 1;
}

void deleteScheduleByDate(DATE date) {
	int y = date.year%2000;
	int m = date.month;
	int d = date.day;
	
	tasklist calTasklist = cal[y][m][d].list;
	task* calH = calTasklist.head;
	while (calH != nullptr) {
		//calendar �� ��ϵǾ��� taskID �� �ִٸ� ���� 
		calH->deleteFlag = 1;		
		calH = calH->next;
	}
	
	//�� ������ �ʿ��Ѱ�.-> �ʿ��ϴ�,Date ������, �ٽ� task �����Ҽ�������.
	cal[y][m][d].deleteFlag = 1;
}

int getCalDayCount(tasklist list) {
	int c = 0;
	task* h = list.head;
	while (h != nullptr) {
		if (h->deleteFlag == 0) {
			//printf("id: %d %d-%d-%d\n", h->id , h->date.year, h->date.month, h->date.day);
			c++;
		}
		h= h->next; 
	}
	return c;
}

int searchSchedule(DATE from, DATE to) {
	
	int y = from.year%2000;
	int m = from.month;
	int d = from.day;

	int y1 = to.year%2000;
	int m1 = to.month;
	int d1 = to.day;

	int counting = 0;

	//d+1�� ��ȭ�� ���� �� / �� / �� ���� ���� 
	while (1) {
		//��/��/�� ���� ���� 
		int lastDay = daysOfMonth(y, m);
		if (d > lastDay) {
			d = d - lastDay;
			//12�� �ϰ�� 
			if (m == 12) {
				m = 1;
				y++;
			}
			else {
				m++;
			}
		}

		//���⼭ counting �ϰ� 
		if (cal[y][m][d].deleteFlag == 0 ) {
			counting = counting + getCalDayCount(cal[y][m][d].list);
		}

		if (y == y1 && m == m1 && d == d1) {
			//printf("�Ϸ�\n");
			break;
		}	

		d = d + 1;
	}
	return counting;
}


