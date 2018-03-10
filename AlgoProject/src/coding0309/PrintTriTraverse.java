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
					
				//다음 노드가 존재하므로, 다음노드 탐색하기 위해, 탐색함수 호출하는 로직이어야한다 (return/pop 배제)	
				}else if (t[i].isfinish && t[i].isnext) {
					
					for ( int j = 0 ; j < st.size(); j++ ){
						System.out.print(st.get(j));
					}
					System.out.println(); 					
				}
				
				printTriTraverse(t[i].t);
				
				//재귀 함수 뒤의 상태는 이미 이전 노드로 넘어간 상태이다. (backcall)
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
			//다 넣었다면 
			if (chars.length == start) { 
				isfinish= true;
				return;
			}
			//다 안넣었다면 넣어야지 
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
			// 문자탐색이 끝이라면 재귀에서 벗어나자 
			if ( chars.length == start ){
				return;
			}
			
			//처음일경우, 조건없이 자판은 1번 입력해야함
			if (start == 0 ) total++; 
			//검색 문자탐색 끝인데, 실제 끝인 아닌경우 (다음 노드가 있을경우)자판 1번 입력 함 (Hell , Hello 의 경우) 
			else if ( chars.length != start && isfinish ){
				total++;				
			}
			//끝이 아니고, 두개이상 존재할경우
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
