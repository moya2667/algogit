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
				System.out.println("�����մϴ� : " + new String(fData));
				fData = new char[6];
			} else {
				System.out.println("���������ʽ��ϴ�");
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
			// �� �־��ٸ�
			if (chars.length == start) {
				isfinish = true;
				return;
			}
			// �� �ȳ־��ٸ� �־����
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
			// ������ ���µ�, ��¥ ���̶��
			if (chars.length == start && isfinish == true) {
				return true;
			} else if (chars.length == start && isfinish == false) {
				return false;
			}

			char[] candi = getCandiBitAgain(chars[start]);
			for (int i = 0; i < 9; i++) {
				int idx = candi[i];
				if (idx < 128 && t[idx] != null) {
					// System.out.println("1bit �ĺ����� ã�� ��ġ�����մϴ�.");
					// System.out.println("1bit �ĺ����� : " + t[idx].c + ":" +
					// (int) t[idx].c + " �Դϴ�");
					if (t[idx].isfind(chars, start + 1)) {
						// System.out.println(t[idx].c);
						fData[start] = t[idx].c; // ��� ������ ���� ���� �ڵ�����
						return true;
					}
				}
				// System.out.println("1bit �ĺ��� �� �´� �ּҷ��� �����ϴ�");
			}
			return false;
		}

		private char[] getCandiBitAgain(char d) {
			char[] candi = new char[9];
			// System.out.println(d + "�� ���� �ĺ����Դϴ�");

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
