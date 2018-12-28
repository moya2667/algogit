#define nullptr 0
#define MAX_HT 991232

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

struct EVENT{
	int id;
	DATE date;
	REPEAT rep;
	EVENT* dnext;
	EVENT* dprev;
	EVENT* enext;
	EVENT* eprev;
};

EVENT* ht[MAX_HT];
int ht_cnt[MAX_HT];
EVENT events_array[1000000]; // 100~1000000 100°³
EVENT* event_head[10000];

int mdaysOfMonth(int y, int m) {
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

int hash(DATE date) {
	int year = date.year % 2000;
	int month = date.month;
	int day = date.day;
	return year * 10000 + month * 100 + day;
}

DATE getNewDate(DATE date, REPEAT_TYPE type) {
	if (type == REPEAT_TYPE::YEARLY) {
		date.year++;
		return date;
	}
	else if (type == REPEAT_TYPE::MONTHLY) {
		date.month++;
		if (date.month == 13) {
			date.month = 1;
			date.year++;
		}
		return date;
	}
	else {
		int gap = 0;
		if (type == REPEAT_TYPE::DAILY)
			gap = 1;
		else if (type == REPEAT_TYPE::EVERY3DAYS)
			gap = 3;
		else if (type == REPEAT_TYPE::EVERY5DAYS)
			gap = 5;
		else if (type == REPEAT_TYPE::WEEKLY)
			gap = 7;
		else if (type == REPEAT_TYPE::EVERY10DAYS)
			gap = 10;

		date.day += gap;
		int maxM = mdaysOfMonth(date.year, date.month);
		if (date.day > maxM) {
			date.day -= maxM;
			date.month++;
			if (date.month == 13) {
				date.month = 1;
				date.year++;
			}
		}
		
		return date;
	}
}

extern int daysOfMonth(int y, int m);
int max_id;
EVENT* getNewEvent(int id, int cnt, DATE date, REPEAT_TYPE type) {
	if (id > max_id)
		max_id = id;
	EVENT* newEvent = &events_array[id];
	newEvent->id = id;
	newEvent->date.day = date.day;
	newEvent->date.month = date.month;
	newEvent->date.year = date.year;
	newEvent->rep.cnt = 0;
	newEvent->rep.type = type;
	newEvent->dnext = nullptr;
	newEvent->dprev = nullptr;
	newEvent->enext = nullptr;
	newEvent->eprev = nullptr;
	return newEvent;
}

void init(){
	max_id = 0;
	for (int i = 0; i < MAX_HT; i++)
	{
		ht[i] = nullptr;
		ht_cnt[i] = 0;
	}

	for (int i = 0; i < 10000; i++)
	{
		event_head[i] = nullptr;
	}
}

void insertToHash(EVENT* newEvent, DATE date) {
	int hashval = hash(date);
	EVENT* head = ht[hashval];
	if (head == nullptr)
	{
		ht[hashval] = newEvent;
		ht_cnt[hashval]++;
		return;
	}
	// insert to front
	head->dprev = newEvent;
	newEvent->dnext = head;
	// update head
	ht[hashval] = newEvent;

	ht_cnt[hashval]++;
}

int getId(int id) { return id = (id - 1) * 100; }


void addSchedule(int id, DATE date, REPEAT repeat){
	int ogid = id;
	id = (id - 1) * 100;
	// add the first one
	EVENT* newEvent = getNewEvent(id, repeat.cnt, date, repeat.type);
	insertToHash(newEvent, date);
	event_head[ogid] = newEvent;
	// add the repeats
	for (int i = 1; i < repeat.cnt; i++)
	{
		// create new event
		id++;
		date = getNewDate(date, repeat.type);
		EVENT* repEvent = getNewEvent(id, repeat.cnt, date, repeat.type);

		// calculate the new date, then add it to date		
		insertToHash(repEvent, date);
		
		// connect with previous event
		newEvent->enext = repEvent;
		repEvent->eprev = newEvent;
		newEvent = repEvent;
	}
}

void removeFromDate(EVENT* revent) {
	int hashval = hash(revent->date);
	ht_cnt[hashval]--;
	if (revent->dprev == nullptr) {
		ht[hashval] = revent->dnext;
		if (revent->dnext != nullptr) {
			revent->dnext->dprev = nullptr;
		}
		return;
	}
	revent->dprev->dnext = revent->dnext;
	if (revent->dnext != nullptr) {
		revent->dnext->dprev = revent->dprev;
	}
}

void deleteScheduleByID(int id){
	EVENT* revent = event_head[id];

	while (revent != nullptr) {
		removeFromDate(revent);
		revent = revent->enext;
	}

	event_head[id] = nullptr;
}

void removeFromEvent(EVENT* revent) {
	int ogid = revent->id / 100 + 1;

	if (revent->eprev == nullptr && revent->enext == nullptr) {
		event_head[ogid] = nullptr;
		return;
	}

	if (revent == event_head[ogid]) {
		if (revent->enext != nullptr) {
			revent->enext->eprev = nullptr;
		}
		event_head[ogid] = revent->enext;
		return;
	}

	revent->eprev->enext = revent->enext;
	if (revent->enext != nullptr) {
		revent->enext->eprev = revent->eprev;
	}
}

void deleteScheduleByDate(DATE date){
	int hashval = hash(date);
	EVENT* revent = ht[hashval];

	while (revent != nullptr) {
		removeFromEvent(revent);
		revent = revent->dnext;
	}
	// clear head
	ht[hashval] = nullptr;
	ht_cnt[hashval] = 0;
}

int searchSchedule(DATE from, DATE to){
	int answer = 0;
	int hashval;
	while (from.year != to.year || from.month != to.month || from.day != to.day) {
		hashval = hash(from);
		answer += ht_cnt[hashval];
		from = getNewDate(from, REPEAT_TYPE::DAILY);
	}
	hashval = hash(from);
	answer += ht_cnt[hashval];
	
	return answer;
}