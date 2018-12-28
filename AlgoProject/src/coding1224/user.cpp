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

#define MAX 100

struct Calendar {
	int id[MAX + 1]; //����� ��ƾ��ϳ�
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
				//calendar[i][j][k].id =  0 ; //����ü �迭 �ʱ�ȭ �� �ȵ�.!!!!!!

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
	printf("addSchedule :  id = %d %d %d %d repeat = %d %d\n", id, date.year, date.month, date.day, repeat.type, repeat.cnt);
	//printf("id = %d %d %d %d repeat = %d %d\n", id, date.year, date.month, date.day, repeat.type, repeat.cnt);
	//int lastDay = daysOfMonth(date.year, date.month);
	int y = date.year % 2000;
	int m = date.month;
	int d = date.day;

	//date + repeat �̿��Ͽ�,  calendar�� id ����
	for (int i = 0; i < repeat.cnt; i++) {

		int lastDay = daysOfMonth(y, m);

		if (d > lastDay) {
			//12���� �ƴҰ�� 
			d = d - lastDay;
			if (date.month == 12) {
				y++; //���� ����
				m = 1;				
			}
			else {
				m++;
				//lastDay = daysOfMonth(y, m);
			}
		}
		

		calendar[y][m][d].idCnt++;
		int cnt = calendar[y][m][d].idCnt;
		calendar[y][m][d].id[cnt] = id;
		calendar[y][m][d].deleteFlag = 0;

		
		IDList[id].dateCnt++;		

		//����� y , m , d �� ���� ����ü�� �Ҵ��Ѵ�.
		DATE newDate;
		newDate.year = y % 2000;
		newDate.month = m;
		newDate.day = d;
		IDList[id].date[ IDList[id].dateCnt ] = newDate;
		IDList[id].deleteFlag = 0;

		//printf("id = %d %d %d %d repeat = %d %d\n", id, newDate.year, newDate.month, newDate.day, repeat.type, repeat.cnt);

		switch (repeat.type) {
		case NONE:			
			break;
		case DAILY:
			d = d + 1;
			break;
		case WEEKLY:
			d = d + 7;
			break;
		case MONTHLY:
			if (m == 12) {
				m = 1;
				y++;
			}else{
				m++;
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

	}
}

//ID�� ���� DATE ���� ���� �ϰ�, IDLIST ���� ID�� �����Ѵ� 
void deleteScheduleByID(int id) {
	printf("delete ID = %d\n", id);
	if (IDList[id].deleteFlag == 1) {
		printf("�̹� ������ ID �Դϴ�. �������� �ʽ��ϴ�\n");
		return;
	}

	int dateCnt = IDList[id].dateCnt; //date count�� �״�� �д� (������ count ������ loop �� ����)	
									  //date���� �����´�
	for (int i = 1; i <= dateCnt; i++) {
		DATE date = IDList[id].date[i];

		int y = date.year % 2000;
		int m = date.month;
		int d = date.day;
		//calendar ���� ID �� ã�Ƽ� �ش� ID�� �����Ѵ� 
		int idCnt = calendar[y][m][d].idCnt;
		for (int j = 1; j <= idCnt; j++) {
			//delete �� ID �� canlendar �� ����� id �� ������ �����Ѵ�  (-1�� �Ҵ��Ѵ�)
			if (calendar[y][m][d].id[j] == id) {
				calendar[y][m][d].id[j] = -1;
			}
		}
	}

	//ID List ���� �ش� ID �� �����Ѵ� (deleteFlag = 1) ; 
	IDList[id].deleteFlag = 1;
}

void deleteScheduleByDate(DATE date) {
	printf("deleteScheduleByDate : %d %d %d \n", date.year, date.month, date.day);

	int y = date.year % 2000;
	int m = date.month;
	int d = date.day;

	if (calendar[y][m][d].deleteFlag == 1) {
		printf("�̹� �ش� date �� �����Ǿ id �� �������� �ʽ��ϴ�\n");
		return;
	}

	calendar[y][m][d].deleteFlag = 1;

	//for debugging 
	int idCnt = calendar[y][m][d].idCnt;
	//printf("delete flag = %d\n", calendar[y][m][d].deleteFlag);
	for (int i = 1; i <= idCnt; i++) {
		int id = calendar[y][m][d].id[i];
		printf("id = %d id's delete flag = %d\n", id, IDList[id].deleteFlag);
	}
}


int searchSchedule(DATE from, DATE to) {
	printf("searchSchedule %d/%d  --> %d/%d\n", from.month, from.day, to.month, to.day);
	//deleteFlag �� ����ʿ�;
	int y = from.year % 2000;
	int m = from.month;
	int d = from.day;
	if (_DEBUG) { 
		if (m == 2 && d == 11) {
			printf("a\n");
		}
	}	

	int y1 = to.year % 2000;
	int m1 = to.month;
	int d1 = to.day;

	int counting = 0;
	

	while (1) {
		int lastDay = daysOfMonth(y, m);
		//day ���
		if (d > lastDay) {
			//12���� �ƴҰ�� 
			d = d - lastDay;
			if (m == 12) {
				y++; //�� ��� (�⵵ ����)
				m = 1; //1�� 
			}
			else {
				m++;
			}
		}


		//������ ��찡 �ƴ϶�� id �� delete flag �� �����ϰ� counting �Ѵ� (�ּ��� �޾ƾ߰ڴ�)
		if (calendar[y][m][d].deleteFlag != 1) {
			int idCnt = calendar[y][m][d].idCnt;
			for (int i = 1; i <= idCnt; i++) {
				//id�� �����Ѵٸ�  (-1 or 0 �� �ƴϸ�) counting 
				if (calendar[y][m][d].id[i] > 0) {
					printf("id = %d  %d-%d-%d \n", calendar[y][m][d].id[i], y%2000, m, d);
					counting++;
				}
			}
		}

		if (y == y1 && m == m1 && d == d1) {
			//printf("To => %d %d %d  ���� �Խ��ϴ�. �����ϴ�\n", y1, m1, d1);
			break;
		}

		//�Ϸ羿 ����
		d = d + 1;
	}

	return counting;
}

