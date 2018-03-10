package coding0309;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PrintTriTraverse {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	tri tr = new tri(); 
	static int total = 0;
	@Test
	public void test() {
		tr.insert("hell".toCharArray(),0);
		tr.insert("hello".toCharArray(),0);
		tr.insert("heaven".toCharArray(),0);
		tr.insert("abaven".toCharArray(),0);
		tr.insert("abcven".toCharArray(),0);
		
		printTriTraverse(tr.t);
		
		
	}
	Stack<Object> st= new Stack<>();

	private void printTriTraverse(tri[] t) {
		// TODO Auto-generated method stub		
		for(int i = 0 ; i < t.length ; i++ ){ 
			if ( t[i] != null ) { 
				//System.out.println("push = " + t[i].c);
				st.add(t[i].c);
				if (t[i].isfinish && !t[i].isnext) {
					
					for ( int j = 0 ; j < st.size(); j++ ){
						System.out.print(st.get(j));
					}
					System.out.println();
					st.pop();
					return;
					
				//���� ��尡 �����ϹǷ�, ������� Ž���ϱ� ����, Ž���Լ� ȣ���ϴ� �����̾���Ѵ� (return/pop ����)	
				}else if (t[i].isfinish && t[i].isnext) {
					
					for ( int j = 0 ; j < st.size(); j++ ){
						System.out.print(st.get(j));
					}
					System.out.println(); 					
				}
				
				printTriTraverse(t[i].t);
				
				//��� �Լ� ���� ���´� �̹� ���� ���� �Ѿ �����̴�. (backcall)
				st.pop();
			}
		}
	}



	class tri{
		
		boolean isfinish = false;
		boolean isnext = false;
		int cnt = 0;
		int childC = 0; 
		boolean isChilds = false;
		char c; 
		
		tri[] t = new tri[28];
		
		
		tri(){
			
		}		
		

		public void insert(char[] chars, int start) {
			//�� �־��ٸ� 
			if (chars.length == start) { 
				isfinish= true;
				return;
			}
			//�� �ȳ־��ٸ� �־���� 
			int idx = chars[start] - 'a'; 
			isnext = true;
			
			if ( t[idx] == null ) {
				cnt++;
				t[idx] = new tri();
				t[idx].c = chars[start];
				isnext = true;
			}
			
			int c = 0;  
			for (int i = 0 ; i < 28 ; i++) { 
				if ( t[i] != null ) {
					System.out.print(t[i].c + " : ");
					c++;
				}
			}
			
			System.out.println(c);
			if (c > 1) isChilds = true;
			 
			t[idx].insert(chars, start+1);
		}

		public void isfind(char[] chars, int start) {
			// ����Ž���� ���̶�� ��Ϳ��� ����� 
			if ( chars.length == start ){
				return;
			}
			
			//ó���ϰ��, ���Ǿ��� ������ 1�� �Է��ؾ���
			if (start == 0 ) total++; 
			//�˻� ����Ž�� ���ε�, ���� ���� �ƴѰ�� (���� ��尡 �������)���� 1�� �Է� �� (Hell , Hello �� ���) 
			else if ( chars.length != start && isfinish ){
				total++;				
			}
			//���� �ƴϰ�, �ΰ��̻� �����Ұ��
			else if (!isfinish && isChilds ) {
				total++;
			}
			
			int idx = chars[start]- 'a'; 
			
			if (t[idx] == null) return;
			
			else{ 
				t[idx].isfind(chars, start+1);
			}
		}

		
	}
}
