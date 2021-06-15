import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PQLib {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	PriorityQueue<Student> pq;

	@Test
	public void test1() {
		String a = "abc";
		String b = "ttt";
		System.out.println(a.compareTo(b));
		System.out.println(b.compareTo(a));

	}

	@Test
	public void testChar() {
		char a = '1';
		char b = 'b';
		System.out.println((int) a);
		System.out.println((int) b);

	}

	class Data implements Comparable<Data> {
		char t;

		public Data(char c) {
			this.t = c;
		}

		@Override
		public int compareTo(Data o) {
			if ((int) o.t > (int) this.t)
				return 1;
			return -1; // -1
		}
	}

	@Test
	public void test2() {
		String test = "a5491111a";
		char[] testchars = test.toCharArray();
		PriorityQueue<Data> mypq = new PriorityQueue<Data>();
		boolean[] isCheck = new boolean[150];

		for (int i = 0; i < testchars.length; i++) {

			if (!isCheck[testchars[i]]) {
				mypq.add(new Data(testchars[i]));
				isCheck[testchars[i]] = true;
			}
		}

		while (!mypq.isEmpty()) {
			System.out.print(mypq.poll().t);
		}
		System.out.println();
	}

	@Test
	public void test() {
		pq = new PriorityQueue<Student>();
		pq.add(new Student(3, "moya", 0.1));
		pq.add(new Student(2, "zaaa", 0.2));
		pq.add(new Student(1, "zaaa", 0.2));

		while (!pq.isEmpty()) {
			System.out.println(pq.poll().name);
		}
	}

	class Student implements Comparable<Student> {
		int id;
		String name;
		double cpga;

		public Student(int id, String name, double cpga) {
			this.id = id;
			this.name = name;
			this.cpga = cpga;
		}

		@Override
		public int compareTo(Student o) {
			if (o.cpga > this.cpga)
				return 1;

			if (o.cpga == this.cpga) {
				if (o.name.equalsIgnoreCase(this.name)) {
					if (o.id < this.id) {
						return 1;
					}
				}
				if (o.name.compareTo(this.name) < 0) {
					return 1;
				}
			}
			return -1;
		}
	}
	
	@Test
	public void test3() {
		int[][] matrix = {{-9,9,9},
							{0,9,0},
						  {-9,9,9}}; 
		int rowsum = 0;
		int result = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (i == 1) {
					rowsum = matrix[i][1];
					break;
				}

				rowsum += matrix[i][j];
			}
			result += rowsum;
			rowsum = 0;
		}		
		System.out.println("res = " + result);
	}
	
	@Test
	public void test4() {
		int[][] matrix =   { { -9, -9, -9, 1, 1, 1 },
							 { 0, -9, 0, 4, 3, 2 },
							 { -9, -9, -9, 1, 2, 3 },
							 { 0, 0, 8, 6, 6, 0 },
							 { 0, 0, 0, -2, 0, 0 },
						     { 0, 0, 1, 2, 4, 0 } };
		
		int max = -100;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {

				int maxY = i + 3;
				int maxX = j + 3;
				if (maxY > matrix.length || maxX > matrix.length) {
					continue;
				}
				int ret = getMatrix(matrix, i, j);
				if (max < ret) {
					max = ret;
				}
			}
			//System.out.println();
		}
		
		System.out.println("Max : " + max);
		 
	}
	
	
	int getMatrix(int[][] matrix , int y, int x) { 
		int rowsum = 0;
		int result = 0;
		
		int checkpoint = y+1;
		for (int i = y; i < y+3; i++) {
			for (int j = x; j < x+3; j++) {
				if (i == checkpoint) {
					rowsum = matrix[i][x+1];
					break;
				}

				rowsum += matrix[i][j];
			}
			result += rowsum;
			rowsum = 0;
		}		
		//System.out.println("res = " + result);
		return result;
	}
}
