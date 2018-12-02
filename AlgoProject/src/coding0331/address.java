package coding0331;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class address {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	tri t = new tri();
	static int total = 0;
	static int bitdiff = 0;

	@Test
	public void test() {
		t.insert("hell".toCharArray(), 0);
		t.insert("hello".toCharArray(), 0);
		t.insert("heaven".toCharArray(), 0);

		t.isfind("hello".toCharArray(), 0);
		System.out.println(total);
		total = 0;

		t.isfind("hell".toCharArray(), 0);
		System.out.println(total);
		total = 0;

		t.isfind("heaven".toCharArray(), 0);
		System.out.println(total);
		total = 0;
	}

	/*
	 * @Test public void test1() { //t.insert("hdll".toCharArray(),0);
	 * t.insert("mell".toCharArray(),0);
	 * System.out.println(t.isfind("gcll".toCharArray(),0)); System.out.println(
	 * "bit diff = " + bitdiff);
	 * 
	 * st.size(); for ( int i = 0 ; i < st.size(); i++) {
	 * System.out.print((char)st.get(i)); } System.out.println();
	 * 
	 * 
	 * st.clear(); bitdiff = 0;
	 * System.out.println(t.isfind("hehl".toCharArray(),0)); System.out.println(
	 * "bit diff = " + bitdiff);
	 * 
	 * for ( int i = 0 ; i < st.size(); i++) {
	 * System.out.print((char)st.get(i)); } System.out.println(); }
	 */

	@Test
	public void getCandi1Bit() {
		char value = 'a';
		System.out.println((int) value);
		int[] bit = new int[] { 1, 2, 4, 8, 16, 32, 64, 128 };

		for (int i = 0; i < bit.length; i++) {
			int v = value ^ bit[i];
			System.out.print(v + ".");
		}
		System.out.println();

	}

	@Test
	public void test2() {
		// t.insert("fell".toCharArray(),0);
		t.insert("gtnh".toCharArray(), 0);
		t.insert("ipll".toCharArray(), 0);

		// System.out.println(t.isfind("fell".toCharArray(),0));
		// System.out.println(t.isfind("cccc".toCharArray(),0));
		System.out.println(t.isfind("fpll".toCharArray(), 0));
		System.out.println(t.isfind("ipll".toCharArray(), 0));
		// System.out.println("bit diff = " + bitdiff);

		/*
		 * for (int i = 0; i < 9; i++) { System.out.print(bit[i]); }
		 */

		System.out.println(decisionStr);
		/*
		 * st.size(); for ( int i = 0 ; i < st.size(); i++) {
		 * System.out.print((char)st.get(i)); } System.out.println();
		 */
	}

	static int bitC = 0;
	static int decisionbit = 999;
	static String decisionStr = "";

	class tri {

		boolean isfinish = false;
		boolean isnext = false;
		int cnt = 0;
		int childC = 0;
		boolean isChilds = false;
		char c;

		tri[] t = new tri[128];

		tri() {

		}

		public void insert(char[] chars, int start) {
			// 다 넣었다면
			if (chars.length == start) {
				isfinish = true;
				return;
			}
			// 다 안넣었다면 넣어야지
			int idx = chars[start];
			isnext = true;

			if (t[idx] == null) {
				cnt++;
				t[idx] = new tri();
				t[idx].c = chars[start];
			}

			/*
			 * int c = 0; for (int i = 0 ; i < 28 ; i++) { if ( t[i] != null ) {
			 * //System.out.print(t[i].c + " : "); c++; } }
			 */

			// System.out.println(c);
			if (c > 1)
				isChilds = true;

			t[idx].insert(chars, start + 1);
		}

		public boolean isfind(char[] chars, int start) {
			// 끝까지 같는데, 진짜 끝이라면
			if (chars.length == start && isfinish == true) {
				/*
				 * if (decisionbit > bitdiff) { decisionStr = "";
				 * 
				 * decisionbit = bitdiff; bitdiff=0; }
				 */
				return true;
			} else if (chars.length == start && isfinish == false) {
				return false;
			}

			// int idx = chars[start];

			char[] candi = getCandiBitAgain(chars[start]);
			for (int i = 0; i < 9; i++) {
				int idx = candi[i];				
				if (idx < 128 && t[idx] != null) {
					System.out.println("1bit 후보군을 찾아 서치시작합니다.");
					System.out.println("1bit 후보군은 : " + t[idx].c + ":" + (int) t[idx].c + " 입니다");
					return t[idx].isfind(chars, start + 1);

				}
				// System.out.println("1bit 후보군 중 맞는 주소록이 없습니다");
			}
			return false;
		}

		private char[] getCandiBitAgain(char d) {
			char[] candi = new char[9];
			System.out.println(d + "에 대한 후보군입니다");

			int[] bit = new int[] { 0, 1, 2, 4, 8, 16, 32, 64, 128 };

			for (int i = 0; i < bit.length; i++) {
				candi[i] = (char) (d ^ bit[i]);
			}

			for (int i = 0; i < bit.length; i++) {
				System.out.print(candi[i] + ":" + (int) candi[i] + " . ");
			}
			System.out.println();
			return candi;
		}
		
		private char[] getCandiBit(char d) {
			int a = 1;
			char[] candi = new char[9];
			int cnt = 1;

			candi[0] = d;
			for (int i = 0; i < 9; i++) {
				int v = (d + (a << i));
				System.out.print(v + " ");
				if (v > 129)
					break;
				candi[cnt++] = (char) v;
			}
			return candi;
		}
	}
}
