#include <stdio.h>
#define MAX_N 1000 // �ּ� 15
#define MAX_M 20 // �ּ� 5
#define PRT 1

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

struct list {
	node* head = 0;
	node* tail = 0;
};
list ll;
node nodelist[1000 * 1000 + 1];

void addShape(int idx, int y, int x) {
	nodelist[idx].y = y;
	nodelist[idx].x = x;
	if (ll.head == 0) {
		ll.head = &nodelist[idx];
		ll.tail = &nodelist[idx];
	}
	else {
		ll.tail->next = &nodelist[idx];
		nodelist[idx].prev = ll.tail;
		ll.tail = &nodelist[idx];
	}
}

void printll() {
	node* h = ll.head;
	while (h != 0) {
		printf("%d  %d", h->y, h->x);
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

	int cnt = 0;
	for (int y = 0; y < N; y++)
	{
		for (int x = 0; x < N; x++) {
			if (picture[y][x] == 1) {
				visit[y][x] = 1;
				addShape(cnt++, y, x);
			}
		}
	}
	printll();
}

int PICTURE_N = 0;
//4 -> 0 / 90 / 180 / 270
POINT relShape[4][MAX_M] = { 0 };

void init(int N, int picture[MAX_N][MAX_N]) {
	PICTURE_N = N;
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

bool myFind(POINT* r) {
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

						if (visit[y1][x1] != 1) {
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
	if (_DEBUG) {
		printf("[%s]\n", __FUNCTION__);

		for (int y = 0; y < M; y++) {
			for (int x = 0; x < M; x++) {
				printf("%d ", shape[y][x]);
			}
			printf("\n");
		}
	}


	for (int y = 0; y < 4; y++) {
		for (int x = 0; x < MAX_M; x++) {
			relShape[y][x] = { 0 };
		}
	}

	int rotate0[MAX_M][MAX_M] = { 0 };
	int rotate90[MAX_M][MAX_M] = { 0 };
	int rotate180[MAX_M][MAX_M] = { 0 };
	int rotate270[MAX_M][MAX_M] = { 0 };


	myPoint(0, M, shape);


	rotateShape(M, shape, rotate90);
	myPoint(1, M, rotate90);

	rotateShape(M, rotate90, rotate180);
	myPoint(2, M, rotate180);

	rotateShape(M, rotate180, rotate270);
	myPoint(3, M, rotate270);

	POINT r = { 0 };
	if (myFind(&r) == true) {
		if (_DEBUG) {
			printf("find POINT : %2d %2d ", r.y, r.x);
		}
	}
	return r;
}