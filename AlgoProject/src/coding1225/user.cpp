#include <stdio.h>
#define MAX_CLUB (10)


struct Player {
	Player *next, *prev;
	int location;
	int power;
	int clubID; //Player가 속해있는 club ID
	int pid; //본인 id
	int priority;
};

struct Club {
	Player *head;
	Player *tail;
	int location;
	int clubID;
	int playerCnt;
};

void print();

Club clubs[MAX_CLUB + 1];
Player players[10000];

#define DEBUG 1

static int LOCATION_CAPA; //지역크기
static int CLUB_NUMBER; //클럽수 
static int CLUB_PLAYER_NUMBER; //클럽당 선수 수
static int CLUB_LOCATION[MAX_CLUB + 1];

int getMinLocationTeam(Player player, int serchStep) {
	int minDiff = 9999;
	int minClubId = 9999;
	//주변 club 위치를 파악 한다 
	int clubCnt = 0;
	for (int i = 0; i < CLUB_NUMBER; i++) {
		//하 씨..두가지 case 존재 (이런걸 조심해야하는데.)
		int a = LOCATION_CAPA - clubs[i].location - player.location;
		if (a < 0) a = a*-1;
		int b = clubs[i].location - player.location;
		if (b < 0) b = b*-1;

		int diff = 9999;
		if (a < b) diff = a;
		else {
			diff = b;
		}

		//diff 가 더 최소값이라면, 
		if (diff < minDiff) {
			minDiff = diff;
			minClubId = i;
		}
		//같을경우 이미 club id 가 더 작은것이 들어가있는거이기 때문에 무시해도 된다.
		else if (diff == minDiff) {
			printf("같은 거리 팀 ID 발견 : minclud id = %d , same distance clud id = %d \n", minClubId, i);
		}
	}

	//9999가 나올수 없다 
	if (DEBUG) {
		if (minClubId == 9999) {
			printf("Clud ID가 9999 어떻게 된일\n");
		}
		else {
			printf("최종선택된 Club ID : %d\n", minClubId);
		}
	}
	return minClubId;
}

void Init(int L, int N, int C, int location[MAX_CLUB]) {
	//지역크기 
	LOCATION_CAPA = L;
	CLUB_NUMBER = N;
	CLUB_PLAYER_NUMBER = C;

	for (int i = 0; i < N; i++) {
		clubs[i].location = location[i];
		clubs[i].clubID = i;
		clubs[i].head = nullptr;
		clubs[i].tail = nullptr;
	}

	print();
}

void print() {
	for (int i = 0; i < CLUB_NUMBER; i++) {
		printf("club id= %d location = %d cnt = %d\n", clubs[i].clubID, clubs[i].location, clubs[i].playerCnt);

	}
}

void print(int cid) {
	Player* h = clubs[cid].head;
	while (h != nullptr) {
		printf("player id: %d priority : %d location : %d power= %d \n", h->pid, h->priority, h->location, h->power);
		h = h->next;
	}
}

void print(Player* player) {	
	printf("player id: %d priority : %d location : %d power= %d \n", player->pid, player->location,player->priority , player->power);
}

bool isFull(Club club) {
	if (club.playerCnt != CLUB_PLAYER_NUMBER) {
		return false;
	}
	return true;
}

Player* changeClubPlayer(Club club, Player* player) {
	//print(player);
	Player* p = club.head;
	
	while (p != nullptr) {
		//player가 더 크다면 ,넣어야 함
		if (p->priority < player->priority) {
			Player* pe = p->prev;
			Player* ne = p->next;
			//이전께 null 이 아니라면 pe 와 연결 
			if (pe != nullptr) {
				pe->next = player; 
				player->prev = pe;				
			}
			if (ne != nullptr) {
				ne->prev = player;
				player->next = ne;
			}
			//print(p);
			return p;
		}
		p = p->next;
	}

	return player;
}


//prioirty 가 가장 높은게 맨 앞에 있도록 함
void addClubToPlayer(int cid, Player* player) {
	int clubPlayerCount = clubs[cid].playerCnt;
	clubs[cid].playerCnt++; //clud안에 player 등록 수 증가

	Player* p = clubs[cid].head;
	//아무것도 없는 상태라면
	if (clubs[cid].head == nullptr) {
		clubs[cid].head = player;
		clubs[cid].tail = player;
		return;
	}	

	//2개 이상일경우 차례대로 우선순위를 검사하여, 끼어 넣는다.	
	while (p != nullptr) {
		//player가 더 크다면 ,넣어야 함
		if (p->priority < player->priority) {
			//head 라면 head 앞에 			
			if (p == clubs[cid].head) {
				player->next = clubs[cid].head;
				clubs[cid].head->prev = player;
				clubs[cid].head = player;				
			}
			//head 아닐경우 아래 처리 
			else {
				Player* pre = p->prev;
				pre->next = player;
				player->prev = pre;
				player->next = p;								
			}			
			return;
		}
		p = p->next;
	}

	//마지막까지 같을경우 (최하위 우선순위라면)
	clubs[cid].tail->next = player;
	player->prev = clubs[cid].tail;
	clubs[cid].tail = player;
	return;
}

int AddPlayer(int pid, int location, int power) {
	if (DEBUG) printf("[%s] pid(%d) location(%d) power(%d)\n", __FUNCTION__, pid, location, power);
	players[pid].location = location;
	players[pid].power = power;
	players[pid].pid = pid;
	int searchStep = 0;
	while (1) {
		//해당 Player 가 가장 가까운 Club ID 를 가져온다 
		int cid = getMinLocationTeam(players[pid], searchStep);
		players[pid].priority = power * 10001 + 10000 - pid;

		//해당 club 에 들어갈수 있으면 , 해당 club 에 선수를 넣는다.
		if (!isFull(clubs[cid])) {
			players[pid].clubID = cid;
			//TO DO LIST : 거리가 가장 가까운 club에 Player를 ADD하는 코드 작성 (Linked연결)
			addClubToPlayer(cid, &players[pid]);
			print(cid);
			return cid;
		}

		//들어갈수 없으나, 
		//sort(clubs[cid]);

		//해당 Club 소속 선수의 능력치가 낮을경우 신규소속 선수 넣는다, 
		//능력치가 동일할 경우 , pid가 더 낮은 선수를 넣는다	
		print(&players[pid]);
		Player* player = changeClubPlayer(clubs[cid], &players[pid]);
		print(player);

		//기존 선수를 뺐거나, 들어갈수 없다면, 그 다음 club 가 가장 가까운 지역에 있는 club id를 찾는다. 
		searchStep++;
	}
	return 0;
}


int RemovePlayer(int pid) {
	int cid = players[pid].clubID;

	//클럽에서 해당 PID 제거한다.

	//그냥 연결을 끊으면 되는거 아닌가..아닌가..	
	Player* pre = players[pid].prev;
	Player* next = players[pid].next;

	//clubs 에서 모두 찾지 않고..
	clubs[cid].head;

	//클럽안에 Player 등록 수 감소
	clubs[cid].playerCnt--;
	return cid;
}

int GetPlayerCount(int cid) {
	return clubs[cid].playerCnt;
}


//prioirty 가 가장 높은게 맨 앞에 있도록 함
void addClubToPlayerTemp(int cid, Player* player) {
	int clubPlayerCount = clubs[cid].playerCnt;
	clubs[cid].playerCnt++; //clud안에 player 등록 수 증가

	Player* p = clubs[cid].head;
	//아무것도 없는 상태라면
	if (clubPlayerCount == 0) {
		clubs[cid].head = player;
		clubs[cid].tail = player;
		return;
	}
	//1개 일경우
	else if (clubPlayerCount == 1) {
		//head 라면 head 앞에	
		if (p->priority < player->priority) {
			player->next = clubs[cid].head;
			clubs[cid].head->prev = player;
			clubs[cid].head = player;
		}
		else {
			clubs[cid].tail->next = player;
			player->prev = clubs[cid].tail;
			clubs[cid].tail = player;
		}
		return;
	}

	//2개 이상일경우 차례대로 우선순위를 검사하여, 끼어 넣는다.	
	while (p != nullptr) {
		//player가 더 크다면 ,넣어야 함
		if (p->priority < player->priority) {
			//head 라면 head 앞에 			
			if (p == clubs[cid].head) {
				player->next = clubs[cid].head;
				clubs[cid].head->prev = player;
				clubs[cid].head = player;
				return;
			}
			else if (p == clubs[cid].tail) {
				Player* pe = p->prev;
				pe->next = player;
				player->prev = pe;
				player->next = clubs[cid].tail;
				clubs[cid].tail->prev = player;
				return;
			}
			//중간에
			Player* pe = p->prev;
			Player* ne = p->next;

			pe->next = player;
			player->prev = pe;
			player->next = ne;
			ne->prev = player;
			return;
		}
		p = p->next;
	}

	//마지막까지 같을경우 (최하위 우선순위라면)
	clubs[cid].tail->next = player;
	player->prev = clubs[cid].tail;
	clubs[cid].tail = player;
	return;
}