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
	
	@Test
	public void find1bit(){
		//1,2,4(2*2),8(2*2*2)
		int a = 1;
		int[] b = new int[8]; 
		for (int i = 1 ; i <= 7 ;i++) { 
			//System.out.println( a + (i*2));
			b[i]= a + (i*2);
		}
		
		for (int i = 1 ; i <= 7 ;i++) { 
			System.out.println(b[i]);
		}
	}
	
	tri t = new tri(); 
	static int total = 0;
	static int bitdiff = 0;
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
	
	/*
	@Test
	public void test1() {
		//t.insert("hdll".toCharArray(),0);
		t.insert("mell".toCharArray(),0);		
		System.out.println(t.isfind("gcll".toCharArray(),0));
		System.out.println("bit diff = " + bitdiff);
		
		st.size(); 
		for ( int i = 0 ; i < st.size(); i++) { 
			System.out.print((char)st.get(i));
		}
		System.out.println();
		
		
		st.clear();
		bitdiff = 0; 
		System.out.println(t.isfind("hehl".toCharArray(),0));
		System.out.println("bit diff = " + bitdiff);
		
		for ( int i = 0 ; i < st.size(); i++) { 
			System.out.print((char)st.get(i));
		}
		System.out.println();
	}
	*/
	
	@Test
	public void test2() {
		//t.insert("fell".toCharArray(),0);
		t.insert("koll".toCharArray(),0);
		t.insert("ipll".toCharArray(),0);
		
		//System.out.println(t.isfind("fell".toCharArray(),0));
		//System.out.println(t.isfind("cccc".toCharArray(),0));
		System.out.println(t.isfind("gpll".toCharArray(),0));
		System.out.println(t.isfind("ipll".toCharArray(),0));
		//System.out.println("bit diff = " + bitdiff);
		
		/*
		for (int i = 0; i < 9; i++) {
			System.out.print(bit[i]);
		}
		*/
		
		System.out.println(decisionStr);
		/*
		st.size(); 
		for ( int i = 0 ; i < st.size(); i++) { 
			System.out.print((char)st.get(i));
		}
		System.out.println();
		*/
	}	

	static int bitC = 0;
	static int decisionbit = 999;
	static String decisionStr = "";


	class tri{
		
		boolean isfinish = false;
		boolean isnext = false;
		int cnt = 0;
		int childC = 0; 
		boolean isChilds = false;
		char c; 
		
		tri[] t = new tri[130];
		
		
		tri(){
			
		}
		
		public void insert(char[] chars, int start) {
			//다 넣었다면 
			if (chars.length == start) {
				isfinish= true;
				return;
			}
			//다 안넣었다면 넣어야지 
			int idx = chars[start]; 
			isnext = true;
			
			if ( t[idx] == null ) {
				cnt++;
				t[idx] = new tri();
				t[idx].c = chars[start];
			}
			
			/*
			int c = 0;  
			for (int i = 0 ; i < 28 ; i++) { 
				if ( t[i] != null ) {
					//System.out.print(t[i].c + " : ");
					c++;
				}
			}
			*/
			
			//System.out.println(c);
			if (c > 1) isChilds = true;
			 
			t[idx].insert(chars, start+1);
		}
		
		public boolean isfind(char[] chars, int start) {
			// 끝까지 같는데, 진짜 끝이라면 
			if ( chars.length == start && isfinish == true){
				if (decisionbit > bitdiff) {
					decisionStr = "";
					/*
					for (int i = 0 ; i < st.size() ;i++) {
						Object t= st.get(i);
						decisionStr= decisionStr + st.get(i);
					}
					*/			
					decisionbit = bitdiff;
					bitdiff=0;
				}
				return true;
			}else if ( chars.length == start && isfinish == false){
				return false;
			}
			
			
			int idx = chars[start];

			char[] candi = getCandiBit(chars[start]);
			for (int i = 0 ; i <=8 ; i++) { 
				if (candi[i] == 0) continue;					
				idx = candi[i];
				if ( t[idx] != null){
					System.out.println("1bit 후보군을 찾아 서치시작합니다.");
					System.out.println("1bit 후보군은 : " + t[idx].c + " 입니다");
					if ( i != 0 )
						bitdiff++;
					//st.push(candi[i]);
					boolean result = t[idx].isfind( chars , start+1);					
					//st.pop();
				}
				//System.out.println("1bit 후보군 중 맞는 주소록이 없습니다");
			}
			return false;
		}

		
		private char[] getCandiBit(char d) {
			// TODO Auto-generated method stub
			int a = 1 ;
			char[] candi = new char[9];
			int cnt = 1 ;
			
			candi[0] = d; 
			for (int i = 0 ; i < 9 ; i++) {
				int v = (d+(a<<i));
				System.out.print(v +" ");
				if ( v > 129 ) break;
				candi[cnt++]= (char)v;
			}
			return candi;
		}
		
		/*
		private char[] getCandiBit(char d) {
			// TODO Auto-generated method stub			
			char[] b = new char[10];
			//b[0] = (char) (d+1);
			int cnt = 0 ; 
			for (int i = 0 ; i <= 8 ;i++) { 
				//System.out.println( a + (i*2));
				//if (d + (i*2) > 129) continue;
				//b[cnt++]= (char) (d + (i*2));
				if ( cnt == 1 ){
					b[cnt++] = (char)(d+1);					
				}
				if (d + (i*2) > 129) continue;
				b[cnt++]= (char) (d + (i*2));
			}
			
			for (int i = 0 ; i <= 8 ;i++) {				 
				//System.out.println(b[i]);
			}
			
			return b;
		}
		*/	
	
	
	}
	}
