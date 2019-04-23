#include <stdio.h>
#define MAX_N 1000 // 최소 15
#define MAX_M 20 // 최소 5
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
void addVisitMap(int N, int picture[MAX_N][MAX_N]) {
	for (int y = 0; y < N; y++)
	{
		for (int x = 0; x < N; x++) {
			if (picture[y][x] == 1) {
				visit[y][x] = 1;
			}
		}
	}
}

int PICTURE_N = 0;

void init(int N, int picture[MAX_N][MAX_N]) {
	//1. picture 에 대한 visit 배열생성 -> 이건 이미 찾는부분 반영되었다는 의미로 표현되는건가.? 
	printf("[%s]\n", __FUNCTION__);
	PICTURE_N = N;

	addVisitMap(N, picture);
	//2. shape 에 대한 90 / 180 / 270 변경 
	//3. shape 에 대한 기준점 근거로 , 상대 위치 저장하기 
	//4. picutre 를 훝어보면서, 기준점인지 판단하는 로직 생성하기 
	//4-1. 최적화를 하려면, picutre 의 값을 링크드로 가지고 있어서, 바로 찾는 방향 고려하기  


	//printMap(N , picture);

}

void rotateShape(int M, int shape[MAX_M][MAX_M], int rotate[MAX_M][MAX_M]) {
	//90도 변경하여, t 값의 0번째 주소에 값 전달하기
	int width = M - 1;
	//90 도 
	for (int y = 0; y <= width; y++) {
		for (int x = 0; x <= width; x++) {
			rotate[x][width - y] = shape[y][x];
		}
	}

	/*
	for (int y = 0; y < M; y++) {
	for (int x = 0; x < M; x++) {
	printf("%d ", rotate[y][x]);
	}
	printf("\n");
	}
	*/
}

//4 -> 0 / 90 / 180 / 270
POINT relShape[4][MAX_M] = { 0 };

void myPoint(int angle, int M, int shape[MAX_M][MAX_M]) {
	//기준점 찾기
	POINT t;
	for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
			if (shape[y][x] == 9) {
				t.y = y;
				t.x = x;
			}
		}
	}

	//기준점 근거로 상대좌표 구하기 
	int cnt = 0;
	for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
			if (shape[y][x] == 1) {
				POINT relPOINT;
				relPOINT.y = y - t.y;
				relPOINT.x = x - t.x;
				//y1 과 x1 담기 
				relShape[angle][cnt++] = relPOINT;
			}
		}
	}
}

bool myFind(POINT* r) {
	//4. picutre 를 훝어보면서, 기준점인지 판단하는 로직 생성하기 -> 나중에 link 로 해결 
	for (int y = 0; y < PICTURE_N; y++) {
		for (int x = 0; x < PICTURE_N; x++) {
			//1 일 경우, 기준점이라 생각하여, 상대좌표값이 매칭 되는지 검사하기 ( 4가지 방향 )
			if (visit[y][x] == 1) {
				//angle 순서대로 매칭 검사 
				for (int angle = 0; angle < 4; angle++) {

					bool isfind = true;
					for (int c = 0; c < MAX_M; c++) {
						if (relShape[angle][c].y == 0 && relShape[angle][c].x == 0) {
							break;
						}
						//y , x 와 상대좌표 y , x 값을 더하면, 해당 좌표에 1이 존재하면 성공 
						int y1 = relShape[angle][c].y + y;
						int x1 = relShape[angle][c].x + x;

						//존재안하면 틀린 좌표이다.
						if (visit[y1][x1] != 1) {
							isfind = false;
							break;
						}
						else {
							printf("1 match");
						}
					}

					if (isfind) {
						printf("FIND");
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
	printf("[%s]\n", __FUNCTION__);

	for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
			printf("%d ", shape[y][x]);
		}
		printf("\n");
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
		printf("find POINT : %2d%2d ", r.y, r.x);
	}
	return r;
}