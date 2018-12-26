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

#define MAX 10

struct Calendar {
	int id[MAX + 1]; //몇개까지 담아야하나
	int idCnt;	
	bool deleteFlag = 0;
};

struct ID {
	DATE date[MAX + 1];
	int dateCnt;
};

Calendar calendar[100][13][32];
ID IDList[MAX + 1];


extern int daysOfMonth(int y, int m);

void init() {
}

void updateCalendar(int id, DATE date, REPEAT repeat) {
	int lastDay = daysOfMonth(date.year, date.day);
	int day = 0;
	int month = 0;
	int year = 2099 - date.year;

	//date + repeat 이용하여,  calendar에 id 저장
	for (int i = 1; i <= repeat.cnt; i++) {
		//= date.day + i;
		switch (repeat.type) {
		case DAILY:
			day = date.day + (i * 1);
			break;
		case WEEKLY:
			day = date.day + (i * 7);
			break;
		case MONTHLY:			
			break;
		case YEARLY:

			break;
		case EVERY3DAYS:
			day = date.day + (i * 3);
			break;
		case EVERY5DAYS:
			day = date.day + (i * 5);
			break;
		case EVERY10DAYS:
			day = date.day + (i * 10);
			break;
		}

		if (day > lastDay) {
			//12월이 아닐경우 
			day = day - lastDay;
			if (date.month == 12) {
				year = year + 1;
				month = 1;
			}
			else {
				//year = year;
				month = date.month + 1;
			}
		}
		else {
			//year = year;
			month = date.month;
		}

		calendar[year][month][day].idCnt = calendar[year][month][day].idCnt + 1;
		int cnt = calendar[year][month][day].idCnt;
		calendar[year][month][day].id[cnt] = id;
	}

}

void addSchedule(int id, DATE date, REPEAT repeat) {
	printf("id = %d %d %d %d repeat = %d %d\n", id, date.year, date.month, date.day, repeat.type, repeat.cnt);
	//id별 년월일 등록된 개수 -> id 별 DATE 저장해야지 바보야. 
	
	int cnt = IDList[id].dateCnt;
	IDList[id].dateCnt = IDList[id].dateCnt + 1;
	IDList[id].date[IDList[id].dateCnt] = date;

	/*
	for (int i = 1; i <= IDList[id].dateCnt; i++) {
		printf("%d,%d,%d", IDList[id].date[i].year, IDList[id].date[i].month, IDList[id].date[i].day);
	}
	*/
	
	updateCalendar(id, date, repeat);
}

void deleteScheduleByID(int id) {
	printf("delete ID = %d\n" , id);
	int cnt = IDList[id].dateCnt; 
	for (int i = 1; i <= cnt; i++) {
		int dateCnt = IDList[i].dateCnt;
		for (int j = 1; j <= dateCnt; j++) {
			int year = 2099 - IDList[i].date[dateCnt].year;
			int month = IDList[i].date[dateCnt].month;
			int day = IDList[i].date[dateCnt].day;
			int idCnt = calendar[year][month][day].idCnt; 
			for (int i = 1; i <= idCnt; i++) {
				if (calendar[year][month][day].id[i] == id) {
					calendar[year][month][day].id[i] = -1;
				}
			}
		}
	}
}

void deleteScheduleByDate(DATE date) {
	printf("deleteScheduleByDate : %d %d %d \n", date.year , date.month ,date.day);
	int year =2099- date.year;
	int month = date.month;
	int day = date.day;
	int idCnt = calendar[year][month][day].idCnt;
	calendar[year][month][day].deleteFlag = 1;
	for (int i = 0; i < idCnt; i++) {
		int id = calendar[year][month][day].id[i];
		printf("id = %d\n", id);
	}
}


int searchSchedule(DATE from, DATE to) {
	//deleteFlag 를 살려야함;
	return 0;
}
