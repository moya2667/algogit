#include <stdio.h>
#define MAX_N 1000 // �ּ� 15
#define MAX_M 20 // �ּ� 5
#define PRT 1
#define _DEBUG 0


struct POINT {
	int y, x;
};

void printMap(int n, int data[][MAX_N]) {
	if (PRT) printf("[%s]\n", __FUNCTION__);
	for (int y = 0; y < n; y++) {
		for (int x = 0; x < n; x++) {
			printf("%d ", data[y][x]);
		}
		printf("\n");
	}
}
int visit[MAX_N][MAX_N] = { 0 };

struct node {
	int y, x;
	node* next;
	node* prev;
};


node* head = 0;
node* tail = 0; 

node nodelist[1000 * 1000+1];

int linkC = 0;
//링크 통해, 좌표를 담아놓는다. 해당 좌표만 검사하기 위해
void addShape(int idx , int y, int x) {
	nodelist[idx].y = y;
	nodelist[idx].x = x;
	if (head == 0 ) {
		head = &nodelist[idx];
		tail = &nodelist[idx];
	}
	else {
		tail->next = &nodelist[idx];
		nodelist[idx].prev = tail; 
		tail = &nodelist[idx];
	}
	visit[y][x] = idx;
	linkC++;
	//printll();
}

void printll() {
	node* h = head;
 	while (h != 0) {
		printf("idx : %d | %d,%d\n",visit[h->y][h->x], h->y, h->x);
		h = h->next;
 	}
}

void addVisitMap(int N, int picture[MAX_N][MAX_N]) {
	//initialize
	for (int y = 0; y < MAX_N; y++)
	{
		for (int x = 0; x < MAX_N; x++) {
			visit[y][x] = 0;
		}
	}

	int cnt = 1;
	for (int y = 0; y < N; y++)
	{
		for (int x = 0; x < N; x++) {
			if (picture[y][x] == 1 ) {								
				addShape(cnt++ , y, x);	
			}
		}
	}
	//printll();
}

int PICTURE_N = 0;
//4 -> 0 / 90 / 180 / 270
POINT relShape[4][MAX_M] = { 0 };

void init(int N, int picture[MAX_N][MAX_N]) {	
	
	PICTURE_N = N;	
	//nodelist[1000 * 1000 + 1] = { 0 };
	for (int i = 0 ;  i < 1000 * 1000 + 1; i++) {
		head = 0;
		tail = 0;
		nodelist[i].next = nullptr;
		nodelist[i].prev = nullptr;
		nodelist[i].y = 0;
		nodelist[i].x = 0;
	}

	addVisitMap(N, picture);
	
}

void rotateShape(int M, int shape[MAX_M][MAX_M], int rotate[MAX_M][MAX_M]) {
	int width = M - 1;
	for (int y = 0; y <= width; y++) {
		for (int x = 0; x <= width; x++) {
			rotate[x][width - y] = shape[y][x];
		}
	}
}

void myPoint(int angle, int M, int shape[MAX_M][MAX_M]) {
	
	POINT t;
	for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
			if (shape[y][x] == 9) {
				t.y = y;
				t.x = x;
			}
		}
	}
	
	int cnt = 0;
	for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
			if (shape[y][x] == 1) {
				POINT relPOINT;
				relPOINT.y = y - t.y;
				relPOINT.x = x - t.x;
				
				relShape[angle][cnt++] = relPOINT;
			}
		}
	}
}

void deletenode(int y, int x) {	
	int idx = visit[y][x];
	node* h = &nodelist[idx];
	if (h == head) {
		head = h->next;
	}
	else if (h == tail) { 
		node* t = h->prev;
		t->next = 0;
		tail = t;		
	}else{ 
		node* pe = h->prev;
		node* ne = h->next;
		pe->next = ne;
		ne->prev = pe;
	}
	visit[y][x] = 0;
	linkC--;
}
//링크이용한 찾기 
bool myFind(POINT* r) {
	//printf("linkC %d \n", linkC);
	node* h = head;
	
	//순차 검색하여, 찾기 
	while (h != 0) {
		int y = h->y;
		int x = h->x;		
		//angle 
		for (int angle = 0; angle < 4; angle++) {

			bool isfind = true;
			POINT backup[MAX_M] = { 0 };
			int cnt = 0;
			for (int c = 0; c < MAX_M; c++) {
				if (relShape[angle][c].y == 0 && relShape[angle][c].x == 0) {
					break;
				}

				int y1 = relShape[angle][c].y + y;
				int x1 = relShape[angle][c].x + x;

				if (visit[y1][x1] == 0 || y1 < 0 || x1 < 0 || y1 > PICTURE_N || x1>PICTURE_N) {
					isfind = false;
					break;
				}

				backup[cnt].y = y1;
				backup[cnt].x = x1;
				cnt++;
			}

			if (isfind) {

				for (int i = 0; i < cnt; i++) {					
					deletenode(backup[i].y, backup[i].x);					
				}
				//important				
				deletenode(y, x);				
				r->y = y;
				r->x = x;
				return true;
			}
		}
		h = h->next;		
	}
	//printf("\n");
	return false;
}

//for 문 이용한 찾기 
bool myFind1(POINT* r) {	
	for (int y = 0; y < PICTURE_N; y++) {
		for (int x = 0; x < PICTURE_N; x++) {
			
			if (visit[y][x] == 1) {
				
				//angle 
				for (int angle = 0; angle < 4; angle++) {

					bool isfind = true;
					POINT backup[MAX_M] = { 0 };
					int cnt = 0;
					for (int c = 0; c < MAX_M; c++) {
						if (relShape[angle][c].y == 0 && relShape[angle][c].x == 0) {
							break;
						}
						
						int y1 = relShape[angle][c].y + y;
						int x1 = relShape[angle][c].x + x;
						
						if (visit[y1][x1] == 0 || y1 < 0 || x1 < 0 || y1 > PICTURE_N || x1>PICTURE_N) {
							isfind = false;
							break;
						}
						
						backup[cnt].y = y1;
						backup[cnt].x = x1;
						cnt++;
					}

					if (isfind) {
						
						for (int i = 0; i < cnt; i++) {
							visit[backup[i].y][backup[i].x] = 0;
						}
						//important
						visit[y][x] = 0;

						r->y = y;
						r->x = x;
						return true;
					}
				}
			}
		}
	}

	return false;
}

POINT find(int M, int shape[MAX_M][MAX_M]) {
	/*
	if (_DEBUG){
		printf("[%s]\n", __FUNCTION__);
	
		for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
		printf("%d ", shape[y][x]);
		}
		printf("\n");
		}
	}
	*/
	

	for (int y = 0; y < 4; y++) {
		for (int x = 0; x < MAX_M; x++) {
			relShape[y][x] = { 0 };
		}
	}

	//2. shape 에 대한 90 / 180 / 270 변경 및 상대좌표 담기 
	int rotate0[MAX_M][MAX_M] = { 0 };
	int rotate90[MAX_M][MAX_M] = { 0 };
	int rotate180[MAX_M][MAX_M] = { 0 };
	int rotate270[MAX_M][MAX_M] = { 0 };

	//기준점 찾고 상대좌표 구하여 담기
	myPoint(0, M, shape);

	//90도 변경한 값을 기준점 찾고 상대좌표 구하여 담기
	rotateShape(M, shape, rotate90);
	myPoint(1, M, rotate90);
	//180도 변경한 값을 기준점 찾고 상대좌표 구하여 담기
	rotateShape(M, rotate90, rotate180);
	myPoint(2, M, rotate180);
	//270도 변경한 값을 기준점 찾고 상대좌표 구하여 담기
	rotateShape(M, rotate180, rotate270);
	myPoint(3, M, rotate270);

	//4. picutre 를 훝어보면서, 기준점인지 판단하는 로직 생성하기 
	POINT r = { 0 };
	if (myFind(&r) == true) {
		//printf("find POINT : %2d%2d ", r.y, r.x);
	}
	return r;
}