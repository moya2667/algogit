package coding0318;

import java.util.Scanner;

public class 바람아멈추어다오 {
	static int CMD_SETUPFLAGS = 1;
	static int CMD_WIND = 0;
	static int CMD_END = 99;

	static int MAX_N = 100;
	static int score;
	static int searchCount;

	static int N;
	static int[][] area = new int[MAX_N][MAX_N];
	static user user = new user();

	static class COORDINATE {
		int x, y;
	};

	static class RESULT {
		int count;
		COORDINATE[] point = new COORDINATE[100];
	};
	
	static Scanner sc = new Scanner(System.in);

	static void run() {

		int cmdCount;
		int correctCount = 0;
		int i, j, cmd;
		int[][] orgArea = new int[MAX_N][MAX_N];

		//Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		for (i = 0; i < N; ++i) {
			for (j = 0; j < N; ++j) {
				area[i][j] = sc.nextInt();
				orgArea[i][j] = area[i][j];
			}
		}

		score = 0;
		searchCount = 0;
		user.init(orgArea, N);
		cmdCount = 0;
		cmd = 0;

		while (true) {
			int changedCount, x, y;

			RESULT result = new RESULT();
			int[][] check = new int[MAX_N][MAX_N];

			int cmdType = sc.nextInt();

			if (cmdType == CMD_END)
				break;
			changedCount = 0;
			i = 0;

			while (true) {
				int n;
				n = sc.nextInt();
				if (n < 0)
					break;
				y = n / N;
				x = n % N;
				++changedCount;
				area[y][x] = cmdType == CMD_SETUPFLAGS ? 1 : 0;
				check[y][x] = 1;
				++i;
			}
			switch (cmdType) {

			case 1: // CMD_SETUPFLAGS:
				result = user.setupflag();
				break;

			case 0: // CMD_WIND:
				result = user.wind();
				break;
			}

			if (result.count == changedCount) {

				for (i = 0; i < changedCount; ++i) {
					if (check[result.point[i].y][result.point[i].x] != 1
							|| area[result.point[i].y][result.point[i].x] == cmdType) {
						break;
					}
					check[result.point[i].y][result.point[i].x] = 0;
				}

				if (i == changedCount)
					++correctCount;
			}

			++cmd;
			++cmdCount;

		}

		int tcCutline;
		sc = new Scanner(System.in);
		tcCutline = sc.nextInt();

		if ((correctCount == cmdCount) && (searchCount <= tcCutline)) {

			score = tcCutline - searchCount;

		}

		else {
			score = -1000000;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int tc, totalScore = 0;

		int T = sc.nextInt();

		for (tc = 1; tc <= T; ++tc) {
			run();
			totalScore += score;
		}

		if (totalScore < 0)
			totalScore = 0;

		System.out.println("#total score : " + totalScore);
		if (totalScore > 0) {
			return; // success
		}
	}
}
