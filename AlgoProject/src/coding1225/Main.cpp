#ifndef _CRT_SECURE_NO_WARNINGS 
#define _CRT_SECURE_NO_WARNINGS 
#endif

#include <stdio.h>

static unsigned int seed = 54321;
static unsigned int pseudo_rand(int max) {
	seed = ((unsigned long long)seed * 1103515245 + 12345) & 0x7FFFFFFF;
	return seed % max;
}

#define MAX_LENGTH (10000)
#define MAX_CLUB (10)

extern void Init(int L, int N, int C, int location[MAX_CLUB]);
extern int AddPlayer(int pid, int location, int power);
extern int RemovePlayer(int pid);
extern int GetPlayerCount(int cid);

static int score;
static void init(int L, int N, int C) {
	score = 100;
	int location[10];
	for (int i = 0; i < N; i++) {
		location[i] = pseudo_rand(L);
		for (int j = 0; j < i; ++j) {
			if (location[i] == location[j]) {
				--i;
				break;
			}
		}
	}
	Init(L, N, C, location);
}

static void run(int l, int n, int c, int cnt) {
	init(l, n, c);
	int cmd, cid, location, power, correct, result;
	int orgCnt = cnt;
	bool rm[10000] = { false };
	int pid = 0, id;
	int rmCnt = 0;

	while (cnt--) {
		if (orgCnt - cnt < c * 2)
			cmd = 0;
		else {
			cmd = pseudo_rand(4);
			if (cmd <= 1 && pid >= n*c || rmCnt >= pid / 2) cmd = 3;
		}
		switch (cmd) {
		case 0: case 1: // add player
			location = pseudo_rand(l);
			power = pseudo_rand(l);
			result = AddPlayer(pid, location, power);
			scanf("%d", &correct);
			if (result != correct) score = 0;
			++pid;
			break;
		case 2: // remove player
			do {
				id = pseudo_rand(pid);
			} while (rm[id]);
			rm[id] = true;
			result = RemovePlayer(id);
			scanf("%d", &correct);
			if (result != correct) score = 0;
			++rmCnt;
			break;
		case 3: // get player count
			cid = pseudo_rand(n);
			result = GetPlayerCount(cid);
			scanf("%d", &correct);
			if (result != correct) score = 0;
			break;
		default:
			break;
		}
	}
	cid = pseudo_rand(n);
	result = GetPlayerCount(cid);
	scanf("%d", &correct);
	if (result != correct) score = 0;
}

int main() {
	int T;
	setbuf(stdout, NULL);
	freopen("sample_input.txt", "r", stdin);
	scanf("%d", &T);

	int total = 0;
	for (int test = 1; test <= T; test++)
	{
		int L, N, C, R;
		scanf("%d %d %d %d %d", &L, &N, &C, &R, &seed);
		run(L, N, C, R);
		total += score;
		printf("#%d : %d\n", test, score);
	}
	printf("#total score : %d\n", total / T);
	if (total / T != 100) return -1;
	return 0;
}