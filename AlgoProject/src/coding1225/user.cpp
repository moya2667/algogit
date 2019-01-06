#include <stdio.h>
#define MAX_CLUB (10)


struct Player {
	Player *next, *prev;
	int location;
	int power;
	int clubID; //Player가 속해있는 club ID
};

struct Club {
	Player *head;
	Player *tail;
	int location;
	int clubID;
	int playerCnt;
};

void print();

Club clubs[MAX_CLUB+1];
Player players[10000];

#define DEBUG 1

static int LOCATION_CAPA; //지역크기
static int CLUB_NUMBER; //클럽수 
static int CLUB_PLAYER_NUMBER; //클럽당 선수 수
static int CLUB_LOCATION[MAX_CLUB+1];

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
			minDiff = diff ;
			minClubId = i;
		}
		//같을경우 이미 club id 가 더 작은것이 들어가있는거이기 때문에 무시해도 된다.
		else if (diff == minDiff) {
			printf("같은 거리 팀 ID 발견 : minclud id = %d , same distance clud id = %d \n", minClubId , i); 
		}
	}

	//9999가 나올수 없다 
	if (DEBUG) { 
		if ( minClubId == 9999) {
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

bool isFull(Club club) {
	if (club.playerCnt != CLUB_PLAYER_NUMBER) {
		return false;
	}
	return true;
}

Player changeClubPlayer(Club club, Player player) {
	return player;
}

void sort(Club club) {
	
}

int AddPlayer(int pid, int location, int power) {
	if (DEBUG) printf("[%s] pid(%d) location(%d) power(%d)\n", __FUNCTION__, pid, location, power);
	players[pid].location = location;
	players[pid].power = power;
	int searchStep =  0;
	while(1){
		//해당 Player 가 가장 가까운 Club ID 를 가져온다 
		int cid = getMinLocationTeam(players[pid], searchStep);

		//해당 club 에 들어갈수 있으면 , 해당 club 에 선수를 넣는다.
		if (!isFull(clubs[cid])) {
			players[pid].clubID = cid;
			clubs[cid].playerCnt++; //clud안에 player 등록 수 증가
			return cid;
		}

		//들어갈수 없으나, 
		sort(clubs[cid]);

		//해당 Club 소속 선수의 능력치가 낮을경우 신규소속 선수 넣는다, 
		//능력치가 동일할 경우 , pid가 더 낮은 선수를 넣는다	
		Player player = changeClubPlayer(clubs[cid], players[pid]);	
	
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
