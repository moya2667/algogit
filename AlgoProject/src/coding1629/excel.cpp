#include <stdio.h>
#define MAXR		50
#define MAXC		26
#define MAXL		400
#define PRT 1

void init(int C, int R, char data[MAXR][MAXL]) {
	if (PRT) printf("[%s]\n", __FUNCTION__);	
	int cnt = R;
	for (int y = 0; y < C; y++) {
		//for (int a = 0; a < R; a++) {		
		//��� �߶�, �����ؾߴ�.?
		for (int x = 0; x < MAXL; x++) {
			while (cnt > 0) {
				if (data[y][x] == ' ') {
					cnt--;
					break;
				}
			}
			printf("%c ", data[y][x]);
		}
		printf("\n");
		
		//}
	}
}

//? Cell �̶� (B3 �̶� �ǹ� 3�� B�� / B�� 3��)
void printfmodifyCell(char* value) {
	if (!PRT) return;
	for (int y = 0; y < MAXR; y++) {
		printf("%d ", value[y]);
	}
	printf("\n");
}

void modifyCell(char input[MAXL]) {
	//printfmodifyCell(input);
}

void printfSheet(int value[MAXR][MAXC]) {
	if (!PRT) return;
	for (int y = 0; y < MAXR; y++)
	{
		for (int x = 0; x < MAXR; x++) {
			printf("%d ", value[y][x]);
		}
		printf("\n");
	}
}

void getSheet(int value[MAXR][MAXC]) {
	//printfSheet(value);
}