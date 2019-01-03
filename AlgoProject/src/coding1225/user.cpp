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

Club clubs[MAX_CLUB+1];
Player players[10000];

#define DEBUG 1

int LOCATION_CAPA; //����ũ��
int CLUB_NUMBER; //Ŭ���� 
int CLUB_PLAYER_NUMBER; //Ŭ���� ���� ��
int CLUB_LOCATION[MAX_CLUB+1];

int getMinLocationTeam(Player player, int serchStep) {
	int minDiff = 9999;
	int minClubIDs[MAX_CLUB + 1] = { 0 }; //�̷��� �ؾ��ϳ�.
	//�ֺ� club ��ġ�� �ľ� �Ѵ� 
	int clubCnt = 0;
	for (int i = 1; i <= CLUB_NUMBER; i++) {
		int diff = clubs[i].location - player.location;
		if (diff < 0) diff = diff*(-1);

		if (diff <= minDiff) {
			minDiff = diff ;
			minClubIDs[clubCnt++] = i;
		}
	}

	//club ��ġ�� ������ ���, cid �� ���� club �� ���� �Ͽ�, ������� �ش�
	int clubID = 9999;
	int cnt = 0;
	while (clubCnt > 0) {
		if (clubID > minClubIDs[cnt]) {
			clubID = minClubIDs[cnt];			
		}
		cnt++;
		clubCnt--; //�ڵ� ������ �ȵ��.
	}	

	//9999�� ���ü� ���� 
	if ( DEBUG && clubID == 9999) {
		printf("Clud ID�� 9999 ��� ����\n");
	}
 	return clubID;
}

void Init(int L, int N, int C, int location[MAX_CLUB]) {
	//����ũ�� 
	LOCATION_CAPA = L;
	CLUB_NUMBER = N;
	CLUB_PLAYER_NUMBER = C;
	int cnt = 1; 
	for (int i = 0; i < N; i++) {
		clubs[cnt].location = location[i];
		clubs[cnt].clubID = i;
		clubs[cnt].head = nullptr;
		clubs[cnt].tail = nullptr;
		cnt++;
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
