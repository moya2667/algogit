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
	//1. picture �� ���� visit �迭���� -> �̰� �̹� ã�ºκ� �ݿ��Ǿ��ٴ� �ǹ̷� ǥ���Ǵ°ǰ�.? 
	printf("[%s]\n", __FUNCTION__);
	PICTURE_N = N;

	addVisitMap(N, picture);
	//2. shape �� ���� 90 / 180 / 270 ���� 
	//3. shape �� ���� ������ �ٰŷ� , ��� ��ġ �����ϱ� 
	//4. picutre �� �m��鼭, ���������� �Ǵ��ϴ� ���� �����ϱ� 
	//4-1. ����ȭ�� �Ϸ���, picutre �� ���� ��ũ��� ������ �־, �ٷ� ã�� ���� ����ϱ�  


	//printMap(N , picture);

}

void rotateShape(int M, int shape[MAX_M][MAX_M], int rotate[MAX_M][MAX_M]) {
	//90�� �����Ͽ�, t ���� 0��° �ּҿ� �� �����ϱ�
	int width = M - 1;
	//90 �� 
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
	//������ ã��
	POINT t;
	for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
			if (shape[y][x] == 9) {
				t.y = y;
				t.x = x;
			}
		}
	}

	//������ �ٰŷ� �����ǥ ���ϱ� 
	int cnt = 0;
	for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
			if (shape[y][x] == 1) {
				POINT relPOINT;
				relPOINT.y = y - t.y;
				relPOINT.x = x - t.x;
				//y1 �� x1 ��� 
				relShape[angle][cnt++] = relPOINT;
			}
		}
	}
}

bool myFind(POINT* r) {
	//4. picutre �� �m��鼭, ���������� �Ǵ��ϴ� ���� �����ϱ� -> ���߿� link �� �ذ� 
	for (int y = 0; y < PICTURE_N; y++) {
		for (int x = 0; x < PICTURE_N; x++) {
			//1 �� ���, �������̶� �����Ͽ�, �����ǥ���� ��Ī �Ǵ��� �˻��ϱ� ( 4���� ���� )
			if (visit[y][x] == 1) {
				//angle ������� ��Ī �˻� 
				for (int angle = 0; angle < 4; angle++) {

					bool isfind = true;
					for (int c = 0; c < MAX_M; c++) {
						if (relShape[angle][c].y == 0 && relShape[angle][c].x == 0) {
							break;
						}
						//y , x �� �����ǥ y , x ���� ���ϸ�, �ش� ��ǥ�� 1�� �����ϸ� ���� 
						int y1 = relShape[angle][c].y + y;
						int x1 = relShape[angle][c].x + x;

						//������ϸ� Ʋ�� ��ǥ�̴�.
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
	//2. shape �� ���� 90 / 180 / 270 ���� �� �����ǥ ��� 
	int rotate0[MAX_M][MAX_M] = { 0 };
	int rotate90[MAX_M][MAX_M] = { 0 };
	int rotate180[MAX_M][MAX_M] = { 0 };
	int rotate270[MAX_M][MAX_M] = { 0 };

	//������ ã�� �����ǥ ���Ͽ� ���
	myPoint(0, M, shape);

	//90�� ������ ���� ������ ã�� �����ǥ ���Ͽ� ���
	rotateShape(M, shape, rotate90);
	myPoint(1, M, rotate90);
	//180�� ������ ���� ������ ã�� �����ǥ ���Ͽ� ���
	rotateShape(M, rotate90, rotate180);
	myPoint(2, M, rotate180);
	//270�� ������ ���� ������ ã�� �����ǥ ���Ͽ� ���
	rotateShape(M, rotate180, rotate270);
	myPoint(3, M, rotate270);

	//4. picutre �� �m��鼭, ���������� �Ǵ��ϴ� ���� �����ϱ� 
	POINT r = { 0 };
	if (myFind(&r) == true) {
		printf("find POINT : %2d%2d ", r.y, r.x);
	}
	return r;
}