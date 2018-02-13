package 이전연습;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class 트라이 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	tri t = new tri(); 
	@Test
	public void test() {
		t.insert("he".toCharArray(),0);
		t.insert("HellO".toCharArray(),0);
		t.insert("hellov".toCharArray(),0);
		
		System.out.println(t.isfind("he".toCharArray(),0));
		System.out.println(t.isfind("Hell".toCharArray(),0));
		System.out.println(t.isfind("HellO".toCharArray(),0));
		System.out.println(t.isfind("hellovp".toCharArray(),0));
	}

	class tri{
		
		boolean isfinish = false;
		boolean isnext = false;
		tri[] t = new tri[55]; 
		
		tri(){
			
		}
		
		public void insert(char[] chars, int start) {
			//다 넣었다면 
			if (chars.length == start) { 
				isfinish= true;
				return;
			}
			
			//다 안넣었다면 넣어야지 
			int idx = chars[start] - 'A'; 
			isnext = true;
			
			if ( t[idx] == null ) {
				t[idx] = new tri();
			}
			
			t[idx].insert(chars, start+1);
		}

		public boolean isfind(char[] chars, int start) {
			// 끝까지 같는데, 진짜 끝이라면 
			if ( chars.length == start && isfinish ){ 
				return true;
			}
			// 끝까지 같는데, 진짜 끝이 아니라면, 다음이 있다면.?
			else if ( chars.length == start && !isfinish ){
				return false;
			}
			
			int idx = chars[start]- 'A'; 
			
			if (t[idx] == null) return false;
			
			else{ 
				return t[idx].isfind(chars, start+1);
			}
		}

		
	}
}
