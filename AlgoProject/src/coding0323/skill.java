package coding0323;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

public class skill {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@org.junit.Test
	public void test() {
		System.out.println((3+6)/2);
		
		int c = 16; 
		for ( int i = c   ; i > 1 ; i = i/2) { 
	    	System.out.println(i);
	    }
		
		while(c>1) {
			System.out.println(c);
			c= c/2;
		}
		
		int d = 0 ;		
	}
	
	//2진수구하기 skill
	@org.junit.Test
	public void test2() {
		
		int i = 0 ;
		int[] bin = new int[8];
		int a = 8;
		while ( a >=1 ) { 
			bin[i++]= a%2;
			a = a/2;
		}
		
		for (i = 7 ; i >= 0 ; i-- ) {
			System.out.print(bin[i]);
		}
		System.out.println();
	}
	
	//t값에 대한 1bit 틀린거 구하기 (각 자리수마다 1을 더한다. 0001,0010,0100,1000 -> 1, 2, 4, 8 -> loop 4번 반복 x<<1  
	@org.junit.Test
	public void test3() {
		int a = 1 ;
		int t = 30;
		for (int i = 0 ; i <= 7 ; i++) {
			//System.out.print(a);
			System.out.print(t + ",");
			a = a<<1;			
			t = t+a;
		}
	}
	
	
	/**
	 * while 의 조건문을 만드는데 시간이 좀 걸린다..
	 * pattern 을 잘 보자. start 기준 / stop 기준 
	 */
	@org.junit.Test
	public void test1(){
		String t = "011001";		
		int len = t.length() -1 ;
		
		boolean start = false;
		int c = 0 ; 
		
		while (len>=0) {
			// start 기준 
			if (!start && t.charAt(len) =='0')
				start = true;			
			if (start) {
				//stop 기준 
				if (t.charAt(len) =='1') break;				
				c++;
			}				
			len--;
		}
		
		System.out.println(c);
		
		c = 0 ;
		start=false;
		for (int i = t.length()-1; i >= 0  ; i--) {
			// start 기준 
			if (!start && t.charAt(i) =='0')
				start = true;
			
			if (start) {
				//stop 기준 
				if (t.charAt(i) =='1') break;				
				c++;
			}
		}
		System.out.println(c);
	}
	
}
	