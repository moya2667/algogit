package coding0331;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class address {

	static tri t = new tri();
	static char[] fData = new char[6];

	@Test
	public void test() throws FileNotFoundException {
		FileInputStream ip = new FileInputStream("src\\coding0331\\sample_input.txt");
		Scanner sc = new Scanner(ip);
		int c = sc.nextInt();

		for (int i = 0; i < c; i++) {
			char[] data = sc.next().toCharArray();
			System.out.println(data);
			t.insert(data, 0);
		}

		// 0 ow:I;4
		while (true) {
			if (sc.nextInt() == -1) {
				break;
			}
			char[] data = sc.next().toCharArray();
			if (t.isfind(data, 0)) {
				System.out.println("존재합니다 : " + new String(fData));
				fData = new char[6];
			} else {
				System.out.println("존재하지않습니다");
			}
		}
	}

	static class tri {

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

			if (c > 1)
				isChilds = true;

			t[idx].insert(chars, start + 1);
		}

		public boolean isfind(char[] chars, int start) {
			// 끝까지 같는데, 진짜 끝이라면
			if (chars.length == start && isfinish == true) {
				return true;
			} else if (chars.length == start && isfinish == false) {
				return false;
			}

			char[] candi = getCandiBitAgain(chars[start]);
			for (int i = 0; i < 9; i++) {
				int idx = candi[i];
				if (idx < 128 && t[idx] != null) {
					// System.out.println("1bit 후보군을 찾아 서치시작합니다.");
					// System.out.println("1bit 후보군은 : " + t[idx].c + ":" +
					// (int) t[idx].c + " 입니다");
					if (t[idx].isfind(chars, start + 1)) {
						// System.out.println(t[idx].c);
						fData[start] = t[idx].c; // 재귀 리콜을 통한 역순 자동저장
						return true;
					}
				}
				// System.out.println("1bit 후보군 중 맞는 주소록이 없습니다");
			}
			return false;
		}

		private char[] getCandiBitAgain(char d) {
			char[] candi = new char[9];
			// System.out.println(d + "에 대한 후보군입니다");

			int[] bit = new int[] { 0, 1, 2, 4, 8, 16, 32, 64, 128 };

			for (int i = 0; i < bit.length; i++) {
				candi[i] = (char) (d ^ bit[i]);
			}

			// for (int i = 0; i < bit.length; i++) {
			// System.out.print(candi[i] + ":" + (int) candi[i] +" ");
			// }
			// System.out.println();
			return candi;
		}
	}

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
}
