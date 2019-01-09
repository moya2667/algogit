#include <stdio.h>
#define MAX_CLUB (10)


struct Player {
	Player *next, *prev;
	int location;
	int power;
	int clubID; //Player�� �����ִ� club ID
	int pid; //���� id
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

static int LOCATION_CAPA; //����ũ��
static int CLUB_NUMBER; //Ŭ���� 
static int CLUB_PLAYER_NUMBER; //Ŭ���� ���� ��
static int CLUB_LOCATION[MAX_CLUB + 1];

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
			minDiff = diff;
			minClubId = i;
		}
		//������� �̹� club id �� �� �������� ���ִ°��̱� ������ �����ص� �ȴ�.
		else if (diff == minDiff) {
			printf("���� �Ÿ� �� ID �߰� : minclud id = %d , same distance clud id = %d \n", minClubId, i);
		}
	}

	//9999�� ���ü� ���� 
	if (DEBUG) {
		if (minClubId == 9999) {
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
		//player�� �� ũ�ٸ� ,�־�� ��
		if (p->priority < player->priority) {
			Player* pe = p->prev;
			Player* ne = p->next;
			//������ null �� �ƴ϶�� pe �� ���� 
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


//prioirty �� ���� ������ �� �տ� �ֵ��� ��
void addClubToPlayer(int cid, Player* player) {
	int clubPlayerCount = clubs[cid].playerCnt;
	clubs[cid].playerCnt++; //clud�ȿ� player ��� �� ����

	Player* p = clubs[cid].head;
	//�ƹ��͵� ���� ���¶��
	if (clubs[cid].head == nullptr) {
		clubs[cid].head = player;
		clubs[cid].tail = player;
		return;
	}	

	//2�� �̻��ϰ�� ���ʴ�� �켱������ �˻��Ͽ�, ���� �ִ´�.	
	while (p != nullptr) {
		//player�� �� ũ�ٸ� ,�־�� ��
		if (p->priority < player->priority) {
			//head ��� head �տ� 			
			if (p == clubs[cid].head) {
				player->next = clubs[cid].head;
				clubs[cid].head->prev = player;
				clubs[cid].head = player;				
			}
			//head �ƴҰ�� �Ʒ� ó�� 
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

	//���������� ������� (������ �켱�������)
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
		//�ش� Player �� ���� ����� Club ID �� �����´� 
		int cid = getMinLocationTeam(players[pid], searchStep);
		players[pid].priority = power * 10001 + 10000 - pid;

		//�ش� club �� ���� ������ , �ش� club �� ������ �ִ´�.
		if (!isFull(clubs[cid])) {
			players[pid].clubID = cid;
			//TO DO LIST : �Ÿ��� ���� ����� club�� Player�� ADD�ϴ� �ڵ� �ۼ� (Linked����)
			addClubToPlayer(cid, &players[pid]);
			print(cid);
			return cid;
		}

		//���� ������, 
		//sort(clubs[cid]);

		//�ش� Club �Ҽ� ������ �ɷ�ġ�� ������� �űԼҼ� ���� �ִ´�, 
		//�ɷ�ġ�� ������ ��� , pid�� �� ���� ������ �ִ´�	
		print(&players[pid]);
		Player* player = changeClubPlayer(clubs[cid], &players[pid]);
		print(player);

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


//prioirty �� ���� ������ �� �տ� �ֵ��� ��
void addClubToPlayerTemp(int cid, Player* player) {
	int clubPlayerCount = clubs[cid].playerCnt;
	clubs[cid].playerCnt++; //clud�ȿ� player ��� �� ����

	Player* p = clubs[cid].head;
	//�ƹ��͵� ���� ���¶��
	if (clubPlayerCount == 0) {
		clubs[cid].head = player;
		clubs[cid].tail = player;
		return;
	}
	//1�� �ϰ��
	else if (clubPlayerCount == 1) {
		//head ��� head �տ�	
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

	//2�� �̻��ϰ�� ���ʴ�� �켱������ �˻��Ͽ�, ���� �ִ´�.	
	while (p != nullptr) {
		//player�� �� ũ�ٸ� ,�־�� ��
		if (p->priority < player->priority) {
			//head ��� head �տ� 			
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
			//�߰���
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

	//���������� ������� (������ �켱�������)
	clubs[cid].tail->next = player;
	player->prev = clubs[cid].tail;
	clubs[cid].tail = player;
	return;
}