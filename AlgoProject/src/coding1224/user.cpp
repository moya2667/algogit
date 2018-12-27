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

#define MAX 30

struct Calendar {
	int id[MAX + 1]; //몇개까지 담아야하나
	int idCnt;
	bool deleteFlag = 0;
};

struct ID {
	DATE date[MAX + 1];
	int dateCnt;
	bool deleteFlag = 0;
};

Calendar calendar[100][13][32];
ID IDList[MAX + 1];


extern int daysOfMonth(int y, int m);

void init() {
	printf("init\n");
	//calendar[100][13][32] = { 0,0,0 }; 
	for (int i = 0; i < 100; i++) {
		for (int j = 0; j < 13; j++) {
			for (int k = 0; k < 32; k++) {
				calendar[i][j][k].deleteFlag = 0;
				//calendar[i][j][k].id =  0 ; //구조체 배열 초기화 왜 안돼.!!!!!!

				for (int z = 0; z < MAX + 1; z++) {
					calendar[i][j][k].id[z] = 0;
				}

				calendar[i][j][k].idCnt = 0;
			}
		}
	}

	for (int i = 0; i < MAX + 1; i++) {
		for (int j = 0; j < MAX + 1; j++) {
			IDList[i].date[j] = { 0 , 0, 0 };
		}
		IDList[i].dateCnt = 0;
		IDList[i].deleteFlag = 0;
	}
}


void addSchedule(int id, DATE date, REPEAT repeat) {
	//printf("id = %d %d %d %d repeat = %d %d\n", id, date.year, date.month, date.day, repeat.type, repeat.cnt);
	int lastDay = daysOfMonth(date.year, date.day);
	int y = date.year % 2000;
	int m = date.month;
	int d = date.day;

	//date + repeat 이용하여,  calendar에 id 저장
	for (int i = 0; i < repeat.cnt; i++) {

		if (d > lastDay) {
			//12월이 아닐경우 
			d = d - lastDay;
			if (date.month == 12) {
				y = y - 1; //연도 증가
				m = 1;
			}
			else {
				m = date.month + 1;
			}
		}
		else {
			m = date.month;
		}

		calendar[y][m][d].idCnt = calendar[y][m][d].idCnt + 1;
		int cnt = calendar[y][m][d].idCnt;
		calendar[y][m][d].id[cnt] = id;

		int dateCnt = IDList[id].dateCnt;
		IDList[id].dateCnt = dateCnt + 1;

		//변경된 y , m , d 에 대한 구조체를 할당한다.
		DATE newDate;
		newDate.year = y % 2000;
		newDate.month = m;
		newDate.day = d;
		IDList[id].date[IDList[id].dateCnt] = newDate;

		printf("id = %d %d %d %d repeat = %d %d\n", id, newDate.year, newDate.month, newDate.day, repeat.type, repeat.cnt);

		switch (repeat.type) {
		case NONE:
			d = date.day;
			break;
		case DAILY:
			d = d + 1;
			break;
		case WEEKLY:
			d = d + 7;
			break;
		case MONTHLY:
			break;
		case YEARLY:
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

	}
}

//ID에 대한 DATE 들을 삭제 하고, IDLIST 에서 ID를 삭제한다 
void deleteScheduleByID(int id) {
	printf("delete ID = %d\n", id);
	if (IDList[id].deleteFlag == 1) {
		printf("이미 삭제된 ID 입니다. 존재하지 않습니다\n");
		return;
	}

	int dateCnt = IDList[id].dateCnt; //date count는 그대로 둔다 (무조건 count 까지만 loop 를 돈다)	
									  //date들을 가져온다
	for (int i = 1; i <= dateCnt; i++) {
		DATE date = IDList[id].date[i];

		int y = date.year % 2000;
		int m = date.month;
		int d = date.day;
		//calendar 에서 ID 를 찾아서 해당 ID를 삭제한다 
		int idCnt = calendar[y][m][d].idCnt;
		for (int j = 1; j <= idCnt; j++) {
			//delete 할 ID 와 canlendar 에 저장된 id 가 같으면 삭제한다  (-1로 할당한다)
			if (calendar[y][m][d].id[j] == id) {
				calendar[y][m][d].id[j] = -1;
			}
		}
	}

	//ID List 에서 해당 ID 를 제거한다 (deleteFlag = 1) ; 
	IDList[id].deleteFlag = 1;
}

void deleteScheduleByDate(DATE date) {
	printf("deleteScheduleByDate : %d %d %d \n", date.year, date.month, date.day);

	int y = date.year % 2000;
	int m = date.month;
	int d = date.day;

	if (calendar[y][m][d].deleteFlag == 1) {
		printf("이미 해당 date 은 삭제되어서 id 가 존재하지 않습니다\n");
		return;
	}

	calendar[y][m][d].deleteFlag = 1;

	//for debugging 
	int idCnt = calendar[y][m][d].idCnt;
	//printf("delete flag = %d\n", calendar[y][m][d].deleteFlag);
	for (int i = 1; i <= idCnt; i++) {
		int id = calendar[y][m][d].id[i];
		printf("id = %d delete flag = %d\n", id, calendar[y][m][d].deleteFlag);
	}
}


int searchSchedule(DATE from, DATE to) {
	printf("searchSchedule %d/%d  --> %d/%d\n", from.month, from.day, to.month, to.day);
	//deleteFlag 를 고려필요;
	int y = from.year % 2000;
	int m = from.month;
	int d = from.day;

	int y1 = to.year % 2000;
	int m1 = to.month;
	int d1 = to.day;

	int counting = 0;
	int lastDay = daysOfMonth(from.year, m);

	while (1) {
		//day 계산
		if (d > lastDay) {
			//12월이 아닐경우 
			d = d - lastDay;
			if (from.month == 12) {
				y = y + 1; //역 계산 (년도 증가)
				m = 1; //1월 
			}
			else {
				m = from.month + 1;
			}
		}


		//삭제된 경우가 아니라면 id 의 delete flag 를 제외하고 counting 한다 (주석을 달아야겠다)
		if (calendar[y][m][d].deleteFlag != 1) {
			int idCnt = calendar[y][m][d].idCnt;
			for (int i = 1; i <= idCnt; i++) {
				//id가 존재한다면  (-1 or 0 이 아니면) counting 
				if (calendar[y][m][d].id[i] > 0) {
					printf("id = %d  %d-%d-%d \n", calendar[y][m][d].id[i], y%2000, m, d);
					counting++;
				}
			}

			if (y == y1 && m == m1 && d == d1) {
				printf("To => %d %d %d  까지 왔습니다. 나갑니다\n", y1, m1, d1);
				break;
			}
		}
		//하루씩 증가
		d = d + 1;
	}

	return counting;
}

