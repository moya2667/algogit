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
void init(int N, int picture[MAX_N][MAX_N]) {	
	//1. picture �� ���� visit �迭���� -> �̰� �̹� ã�ºκ� �ݿ��Ǿ��ٴ� �ǹ̷� ǥ���Ǵ°ǰ�.? 
	//2. shape �� ���� 90 / 180 / 270 ���� 
	//3. shape �� ���� ������ �ٰŷ� , ��� ��ġ �����ϱ� 
	//4. picutre �� �m��鼭, ���������� �Ǵ��ϴ� ���� �����ϱ� 
	    //4-1. ����ȭ�� �Ϸ���, picutre �� ���� ��ũ��� ������ �־, �ٷ� ã�� ���� ����ϱ�  
	//5. 

	//printMap(N , picture);

}



POINT find(int M, int shape[MAX_M][MAX_M]) {
	for (int y = 0; y < M; y++) {
		for (int x = 0; x < M; x++) {
			printf("%d ", shape[y][x]);
		}
		printf("\n");
	}
	POINT r;
	r.y = -1;
	r.x = -1;
	return r;
}