package coding0318;

import coding0318.stopwind.RESULT;

public class user {
	int MAX_N = 100;

	void init(int[][] area ,  int n) {
		for (int y = 0; y < MAX_N; y++) {
			for (int x = 0; x < MAX_N; x++) {
				System.out.print(" "  + area[y][x]);
			}
			System.out.println();
		}
	}

	static RESULT wind() {
		return null; 
	}



	static RESULT setupflag() {
		return null;
	}

}
