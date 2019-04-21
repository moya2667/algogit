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
void init(int N, int picture[MAX_N][MAX_N]) {	
	//1. picture 에 대한 visit 배열생성 -> 이건 이미 찾는부분 반영되었다는 의미로 표현되는건가.? 
	//2. shape 에 대한 90 / 180 / 270 변경 
	//3. shape 에 대한 기준점 근거로 , 상대 위치 저장하기 
	//4. picutre 를 훝어보면서, 기준점인지 판단하는 로직 생성하기 
	    //4-1. 최적화를 하려면, picutre 의 값을 링크드로 가지고 있어서, 바로 찾는 방향 고려하기  
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