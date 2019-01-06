#include <stdio.h>
#define MAX_CLUB (10)


struct Player {
	Player *next, *prev;
	int location;
	int power;
	int clubID; //Player�� �����ִ� club ID
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

static int LOCATION_CAPA; //����ũ��
static int CLUB_NUMBER; //Ŭ���� 
static int CLUB_PLAYER_NUMBER; //Ŭ���� ���� ��
static int CLUB_LOCATION[MAX_CLUB+1];

int getMinLocationTeam(Player player, int serchStep) {
	int minDiff = 9999;
	int minClubId = 9999;
	//�ֺ� club ��ġ�� �ľ� �Ѵ� 
	int clubCnt = 0;
	for (int i = 0; i < CLUB_NUMBER; i++) {
		//�� ��..�ΰ��� case ���� (�̷��� �����ؾ��ϴµ�.)
		int a = LOCATION_CAPA - clubs[i].location - player.location;
		if (a < 0) a = a*-1;		
		int b = clubs[i].location - player.location;
		if (b < 0) b = b*-1;

		int diff = 9999;
		if (a < b) diff = a; 
		else {
			diff = b;
		}		

		//diff �� �� �ּҰ��̶��, 
		if (diff < minDiff) {
			minDiff = diff ;
			minClubId = i;
		}
		//������� �̹� club id �� �� �������� ���ִ°��̱� ������ �����ص� �ȴ�.
		else if (diff == minDiff) {
			printf("���� �Ÿ� �� ID �߰� : minclud id = %d , same distance clud id = %d \n", minClubId , i); 
		}
	}

	//9999�� ���ü� ���� 
	if (DEBUG) { 
		if ( minClubId == 9999) {
			printf("Clud ID�� 9999 ��� ����\n");
		}
		else {
			printf("�������õ� Club ID : %d\n", minClubId);
		}
	}
 	return minClubId;
}

void Init(int L, int N, int C, int location[MAX_CLUB]) {
	//����ũ�� 
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
		//�ش� Player �� ���� ����� Club ID �� �����´� 
		int cid = getMinLocationTeam(players[pid], searchStep);

		//�ش� club �� ���� ������ , �ش� club �� ������ �ִ´�.
		if (!isFull(clubs[cid])) {
			players[pid].clubID = cid;
			clubs[cid].playerCnt++; //clud�ȿ� player ��� �� ����
			return cid;
		}

		//���� ������, 
		sort(clubs[cid]);

		//�ش� Club �Ҽ� ������ �ɷ�ġ�� ������� �űԼҼ� ���� �ִ´�, 
		//�ɷ�ġ�� ������ ��� , pid�� �� ���� ������ �ִ´�	
		Player player = changeClubPlayer(clubs[cid], players[pid]);	
	
		//���� ������ ���ų�, ���� ���ٸ�, �� ���� club �� ���� ����� ������ �ִ� club id�� ã�´�. 
		searchStep++;
	}
	return 0;
}


int RemovePlayer(int pid) {
	int cid = players[pid].clubID;

	//Ŭ������ �ش� PID �����Ѵ�.

	//�׳� ������ ������ �Ǵ°� �ƴѰ�..�ƴѰ�..	
	Player* pre = players[pid].prev;
	Player* next = players[pid].next;
	
	//clubs ���� ��� ã�� �ʰ�..
	clubs[cid].head;

	//Ŭ���ȿ� Player ��� �� ����
	clubs[cid].playerCnt--;
	return cid;
}

int GetPlayerCount(int cid) {
	return clubs[cid].playerCnt;	
}
