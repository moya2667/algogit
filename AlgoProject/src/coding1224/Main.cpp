#ifndef _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#endif

#define DEBUG_OUT 10

#include <stdio.h>

#define MAX_ID 10000
#define MAX_REPEAT_COUNT 100

static unsigned int seed = 12345;
static unsigned int pseudo_rand(int max) {
	seed = ((unsigned long long)seed * 1103515245 + 12345) & 0x7FFFFFFF;
	return seed % max;
}

enum COMMAND {
	ADD,
	DELETE_ID,
	DELETE_DAY,
	SEARCH,
	ADD2, // for probability
	COMMAND_MAX
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

struct DATE {
	int year;
	int month;
	int day;
};

struct REPEAT {
	REPEAT_TYPE type;
	int cnt;
};

extern void init();
extern void addSchedule(int id, DATE date, REPEAT repeat);
extern void deleteScheduleByID(int id);
extern void deleteScheduleByDate(DATE date);
extern int searchSchedule(DATE from, DATE to);

int daysOfMonth(int y, int m) {
	if (m == 2) {
		if (((y % 4 == 0) && (y % 100 != 0)) || (y % 400 == 0))
			return 29;
		else
			return 28;
	}
	else if (m == 4 || m == 6 || m == 9 || m == 11)
		return 30;
	return 31;
}

static int tc;
static DATE newDate() {
	DATE date;

	if (tc <= 5) date.year = 2018;
	else date.year = pseudo_rand(100) + 2000;

	if (tc <= 2) date.month = 7;
	else date.month = pseudo_rand(12) + 1;

	date.day = pseudo_rand(daysOfMonth(date.year, date.month)) + 1;

	return date;
}

static REPEAT newRepeat(DATE date) {
	REPEAT repeat;
	do {
		do {
			repeat.type = (REPEAT_TYPE)pseudo_rand(TYPE_MAX);
		} while ((tc <= 2 && repeat.type == MONTHLY) ||
			(tc <= 5 && repeat.type == YEARLY) ||
			(repeat.type == MONTHLY && date.day > 28) ||
			(repeat.type == YEARLY && date.month == 2 && date.day > 28));
		repeat.cnt = 1;
		if (repeat.type) {
			int cnt = 100;
			int max = MAX_REPEAT_COUNT;
			if (tc > 10) max = MAX_REPEAT_COUNT - 1;
			if (tc > 5) max = 20;
			else max = 5;
			repeat.cnt = pseudo_rand(max) + 2;
			switch (repeat.type) {
			case DAILY:
				if (date.year >= 2099 && date.month >= 9 && date.day >= 20)
					cnt = (12 - date.month) * 30 + daysOfMonth(date.year, date.month) - date.day;
				break;
			case WEEKLY:
				if (date.year >= 2097)
					cnt = ((2099 - date.year) * 365 + (12 - date.month) * 30 + daysOfMonth(date.year, date.month) - date.day) / 7;
				break;
			case MONTHLY:
				if (date.year >= 2091)
					cnt = (2099 - date.year) * 12 + 12 - date.month;
				break;
			case YEARLY:
				cnt = 2099 - date.year;
				break;
			case EVERY3DAYS:
				if (date.year >= 2099)
					cnt = ((2099 - date.year) * 365 + (12 - date.month) * 30 + daysOfMonth(date.year, date.month) - date.day) / 3;
				break;
			case EVERY5DAYS:
				if (date.year >= 2098)
					cnt = ((2099 - date.year) * 365 + (12 - date.month) * 30 + daysOfMonth(date.year, date.month) - date.day) / 5;
				break;
			case EVERY10DAYS:
				if (date.year >= 2096)
					cnt = ((2099 - date.year) * 365 + (12 - date.month) * 30 + daysOfMonth(date.year, date.month) - date.day) / 10;
				break;
			}
			if (repeat.cnt > cnt) repeat.cnt = cnt;
		}
	} while (repeat.type != NONE && repeat.cnt < 2);
	return repeat;
}

static DATE endDate(DATE date) {
	DATE date2;
	date2 = date;
	int days = pseudo_rand(30);
	if (days > 30) days = 30;
	date2.day += days;
	days = daysOfMonth(date2.year, date2.month);
	if (date2.day > days) {
		date2.day -= days;
		if (date2.month == 12) {
			if (date2.year == 2099)
				date2.day = 31;
			else {
				++date2.year;
				date2.month = 1;
			}
		}
		else
			++date2.month;
	}
	return date2;
}

static DATE loadDate() {
	DATE date;
	int number;
	scanf("%d", &number);
	date.day = number % 100;
	number /= 100;
	date.month = number % 100;
	number /= 100;
	date.year = number + 2000;
	return date;
}

static int preN = 10;
static int run() {
	int N, score;
	int cmd;
	int id = 1, target;
	bool removed[MAX_ID] = { false };

	DATE date, date2;
	REPEAT repeat;
	int debugMode;

	init();

	scanf("%d %d %d", &N, &seed, &debugMode);
	score = 100;
	while (1) {
		if (debugMode) {
			scanf("%d", &cmd);
			if (cmd == 99) break;
		}
		else {
			if (id <= N / 2)
				cmd = 0;
			else
				cmd = pseudo_rand(COMMAND_MAX);
			if (cmd == ADD2) cmd = ADD;
			if (cmd == 0 && id >= N) break;
		}
		switch (cmd) {
		case ADD:
			if (debugMode) {
				date = loadDate();
				scanf("%d %d", &repeat.type, &repeat.cnt);
			}
			else {
				date = newDate();
				repeat = newRepeat(date);
			}
			addSchedule(id++, date, repeat);
			break;
		case DELETE_ID:
			if (debugMode)
				scanf("%d", &target);
			else {
				target = pseudo_rand(id - 1) + 1;
				while (removed[target]) {
					++target;
					if (target == id) target = 1;
				}
			}
			deleteScheduleByID(target);
			removed[target] = true;
			break;
		case DELETE_DAY:
			if (debugMode) {
				date = loadDate();
			}
			else
				date = newDate();
			deleteScheduleByDate(date);
			break;
		case SEARCH:
			if (debugMode) {
				date = loadDate();
				date2 = loadDate();
			}
			else {
				date = newDate();
				date2 = endDate(date);
			}
			int result = searchSchedule(date, date2);
			int correct;
			scanf("%d", &correct);
			if (result != correct) score = 0;
			break;
		}
	}

	return score;
}

int main() {
	int T;

	setbuf(stdout, NULL);
	freopen("sample_input.txt", "r", stdin);
	scanf("%d", &T);

	int totalScore = 0;
	for (tc = 1; tc <= T; tc++) {
		if (run() == 100) {
			//printf("#%d : success\n");
			totalScore += 100;
		}
		else
			printf("#%d : failure\n", tc);
	}
	printf("#total score : %d\n", totalScore / T);

	if (totalScore / T != 100) return -1;
	return 0;
}