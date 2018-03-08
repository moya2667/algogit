package coding0309;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class 자판 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	tri t = new tri(); 
	static int total = 0;
	@Test
	public void test() {
		t.insert("hell".toCharArray(),0);
		t.insert("hello".toCharArray(),0);
		t.insert("heaven".toCharArray(),0);		
		
		t.isfind("hello".toCharArray(),0);
		System.out.println(total);
		total = 0;
		
		t.isfind("hell".toCharArray(),0);
		System.out.println(total);
		total = 0;
		
		t.isfind("heaven".toCharArray(),0);
		System.out.println(total);
		total = 0;		
		
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
			// 끝까지 같는데, 진짜 끝이라면 
			if ( chars.length == start ){
				return;
			}
			
			if (start == 0 ) total++; // 처음은 무조건 입력
			// 진짜 끝이 아니라면, 다음이 있다면.?
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
