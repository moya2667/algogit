package coding1916;
import java.util.Scanner;

class Solution {	
	private final static int MAXSERVER   = 5;
	private final static int IMAGESIZE   = 400000;
	
	private final static char blockchainimage[][] = new char[MAXSERVER][IMAGESIZE];
	
	private static int S, Q;
	private static int score;
	
	private final static UserSolutionMoya usersolution = new UserSolutionMoya();
	
	public static int calcHash(char buf[], int pos, int len) {
		int hash = 0;
		
		for (int i = 0; i < len; ++i) {
			hash = buf[pos + i] + (hash << 6) + (hash << 16) - hash; // hash = buf[pos + i] + 65599 * hash;
			hash &= 0x7fffffff;
		}
		
		return hash;
	}
	
	private static int get16(char c) {
		return c >= 'a' ? 10 + c - 'a' : c - '0';
	}
	
	private static boolean run(Scanner sc) {
		int corrected = 0;
	
		S = sc.nextInt();
		Q = sc.nextInt();
		for (int server = 0; server < S; ++server) {
			int len = sc.nextInt();
			for (int p = 0; p < len;) {
				String buf = sc.next();
				for (int i = 0; i < 64 && p < len; i += 2, ++p)
					blockchainimage[server][p] =  (char) ((get16(buf.charAt(i)) << 4) | get16(buf.charAt(i + 1)));
			}
		}
		
		usersolution.syncBlockChain(S, blockchainimage);
		
		for (int q_count = 0; q_count < Q; ++q_count) {
			int hash = sc.nextInt();
			int exchangeid = sc.nextInt();
			int answer = sc.nextInt();
			int result = usersolution.calcAmount(hash, exchangeid);
			if (result == answer)
				++corrected;
		}
		
		return corrected == Q;
	}

	public static void main(String[] args) throws Exception {
		int TC, totalscore;

		System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
		Scanner sc = new Scanner(System.in);
		TC = sc.nextInt();
        
        totalscore = 0;
		for (int testcase = 1; testcase <= TC; ++testcase) {
            int  score = 0;
            
            if (run(sc))
            	score = 100;
            totalscore += score;
            System.out.println("#" + testcase + " " + score);
		}
        System.out.println("total score = " + totalscore / TC);
		sc.close();
	}
}
