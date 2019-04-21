#ifndef _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <stdio.h>

#define MAX_N 1000 // �ּ� 15
#define MAX_M 20 // �ּ� 5
#define MAX_POINT 20000
#define MAX_GROUP 20
#define MAX_CALL 1000

struct POINT {
	int y, x;
};

extern void init(int N, int picture[MAX_N][MAX_N]);
extern POINT find(int M, int shape[MAX_M][MAX_M]);

/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////

static unsigned int seed = 54321;
static unsigned int pseudo_rand(int max) {
	seed = ((unsigned long long)seed * 1103515245 + 12345) & 0x7FFFFFFF;
	return seed % max;
}

static int tc;
static int picture[MAX_N][MAX_N];
static int point[MAX_CALL][MAX_M][MAX_M];
static int M[MAX_CALL];

static void rotate(int s) {
	char temp[MAX_M][MAX_M] = { 0 };
	for (register int i = 0; i < M[s]; ++i) {
		for (register int j = 0; j < M[s]; ++j) {
			if (point[s][i][j])
				temp[j][M[s] - 1 - i] = point[s][i][j];
		}
	}
	for (register int i = 0; i < M[s]; ++i)
		for (register int j = 0; j < M[s]; ++j)
			point[s][i][j] = temp[i][j];
}

static int run() {
	int score = 100;
	int N, S, P;

	scanf("%d %d %d %d", &N, &S, &P, &seed);

	for (register int i = 0; i < N; ++i)
		for (register int j = 0; j < N; ++j)
			picture[i][j] = 0;

	int MM = N / 2;
	if (MM > 20) MM = 20;
	int totalP = 0;
	int jump;
	scanf("%d", &jump);
	for (int s = 0; s < S; ++s) {
		if (s == jump)
			scanf("%d %d", &seed, &jump);
		M[s] = pseudo_rand(MM) + 5;
		if (M[s] > 20) M[s] = 20;
		for (int i = 0; i < M[s]; ++i)
			for (int j = 0; j < M[s]; ++j)
				point[s][i][j] = 0;
		int y = pseudo_rand(M[s]);
		int x = pseudo_rand(M[s]);
		point[s][y][x] = 9;
		int cntP = pseudo_rand(P) + 5;
		if (cntP > M[s]) cntP = M[s];
		if (cntP > MAX_GROUP) cntP = MAX_GROUP;
		for (int p = 1; p < cntP; ++p) {
			y = pseudo_rand(M[s]);
			x = pseudo_rand(M[s]);
			if (point[s][y][x] == 0)
				point[s][y][x] = 1;
			else
				--p;
		}
		bool overlap = true;
		while (overlap) {
			overlap = false;
			y = pseudo_rand(N - M[s] + 1);
			x = pseudo_rand(N - M[s] + 1);
			for (register int i = 0; !overlap && i < M[s]; ++i) {
				for (register int j = 0; !overlap && j < M[s]; ++j) {
					if (point[s][i][j] && picture[y + i][x + j])
						overlap = true;
				}
			}
		}
		for (register int i = 0; i < M[s]; ++i) {
			for (register int j = 0; j < M[s]; ++j) {
				if (point[s][i][j])
					picture[y + i][x + j] = 1;
			}
		}
		int r = pseudo_rand(4);
		while (r--) rotate(s);
		totalP += cntP;
	}

	int maxP = totalP * 2;;
	if (maxP > MAX_POINT) maxP = MAX_POINT;
	while (totalP++ < maxP) {
		int y = pseudo_rand(N);
		int x = pseudo_rand(N);
		if (picture[y][x] == 0)
			picture[y][x] = 1;
	}

	init(N, picture);

	for (int s = 0; s < S; ++s) {
		int y, x;
		scanf("%d", &y);
		if (y < 0) continue;
		scanf("%d", &x);
		int shape[MAX_M][MAX_M];
		for (register int i = 0; i < M[s]; ++i)
			for (register int j = 0; j < M[s]; ++j)
				shape[i][j] = point[s][i][j];
		POINT r = find(M[s], shape);
		if (y != r.y || x != r.x)
			score = 0;
	}

	return score;
}

int main() {
	setbuf(stdout, NULL);

	int T;
	freopen("sample_input.txt", "r", stdin);
	scanf("%d", &T);

	int totalScore = 0;
	for (tc = 1; tc <= T; ++tc) {
		int score = run();
		totalScore += score;
		printf("#%d : %d\n", tc, score);
	}
	printf("#total score : %d\n", totalScore / T);

	if (totalScore / T != 100) return -1;
	return 0;
}