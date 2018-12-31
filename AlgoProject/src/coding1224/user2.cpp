/*
void addSchedule(int id, DATE date, REPEAT repeat);
일정을 추가한다.
id: 1~10,000 값을 가지며 중복이 없다.
date: 일정 시작일. DATE 구조를 가지며 구조는 다음과 같다.
struct DATE {
int year; // 2000~2099
int month; // 1~12
int day; // 1~31 월별로 다름.
};
repeat: REPEAT 구조를 가지며 구조는 다음과 같다.
struct REPEAT {
REPEAT_TYPE type; // 반복 형태
int cnt; // 1~100 반복 횟수
};
type은 반복 형태이며 다음 값들 중 하나를 가진다.
enum REPEAT_TYPE {
NONE,
DAILY, // 매일
WEEKLY, // 매주 시작일과 같은 요일
MONTHLY, // 매월 동일한 날짜
YEARLY, // 매년 동월 동일
EVERY3DAYS, // 시작일부터 3일마다
EVERY5DAYS, // 시작일부터 5일마다
EVERY10DAYS, // 시작일부터 10일마다
TYPE_MAX
};
type이 NONE인 경우 cnt는 반드시 1이다.
일정이 반복되는 경우 2099년 12월 31일을 이후까지 반복되는 일정은 주어지지 않는다.
type이 MONTHLY인 경우 day는 28 이하 값을 가진다.
type이 YEARLY인 경우 month가 2라면 day값은 29를 가지는 경우는 없다
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

//날짜기반 task 기록 구조 
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
	task tasks[1000];	//기준이 무엇인가.?	
	int cnt = 0;
	bool deleteFlag = 0;
};


calendar cal[100][13][366];
IDLIST tList[MAX];
 
//task 기반 date 기록 구조 
//task[MAX]
// + date 1
// + date 2
// + date 3


extern int daysOfMonth(int y, int m);

void init() {
	/*초기화방법은 나중에 보자 */
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

//2시까지
void addSchedule(int taskId, DATE date, REPEAT repeat) {
	//cal 에 taskid 등록 
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
	
		//calendar 에 task 들 기록 		
		addCalToTask(&cal[y][m][d].list, &tList[taskId].tasks[cnt]);
		cal[y][m][d].deleteFlag = 0;		

		switch (repeat.type) {
		case NONE : 
			//이미 당일 저장됨
			break;
		case DAILY:
			d++;
			break;
		case WEEKLY:				
			d = d + 7;
			break;
		case MONTHLY:
			m++;
			if (m == 13) {  //여기 찾는거 ㅜㅜ
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

		//날짜는 위에서 계산되면서 년/월이 바뀔수 있기때문에..호출되어야한다.
		int endDay = daysOfMonth(y+2000, m);
		if (d > endDay) {
			d = d - endDay;
			//마지막 달일경우 년/월 변경
			if (m == 12) {
				y++;
				m = 1;				
			}
			//월만 변경 
			else {
				m++;
			}
		}		
	}
}

//ID에 대한 DATE 가져와 Calendar에서 삭제 
//IDLIST 에서 ID를 삭제한다 
void deleteScheduleByID(int id) {	
	int cnt = tList[id].cnt;
	for (int i = 1; i <= cnt; i++) {
		task t = tList[id].tasks[i];
		int y = t.date.year;
		int m = t.date.month;
		int d = t.date.day;
		//cal 자료구조에서 해당 id 를 찾아내어, 삭제한다.(flag)
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
		//calendar 에 등록되었는 taskID 가 있다면 삭제 
		calH->deleteFlag = 1;		
		calH = calH->next;
	}
	
	//위 로직이 필요한가.-> 필요하다,Date 삭제후, 다시 task 생성할수있으니.
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

	//d+1에 변화에 따른 월 / 년 / 일 변경 로직 
	while (1) {
		//년/월/일 변경 로직 
		int lastDay = daysOfMonth(y, m);
		if (d > lastDay) {
			d = d - lastDay;
			//12월 일경우 
			if (m == 12) {
				m = 1;
				y++;
			}
			else {
				m++;
			}
		}

		//여기서 counting 하고 
		if (cal[y][m][d].deleteFlag == 0 ) {
			counting = counting + getCalDayCount(cal[y][m][d].list);
		}

		if (y == y1 && m == m1 && d == d1) {
			//printf("완료\n");
			break;
		}	

		d = d + 1;
	}
	return counting;
}


