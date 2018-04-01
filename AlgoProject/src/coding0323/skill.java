package coding0323;


import static org.junit.Assert.*;

import java.util.Stack;

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
	
	//2�������ϱ� skill
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
	
	/*
	//t���� ���� 1bit Ʋ���� ���ϱ� (�� �ڸ������� 1�� ���Ѵ�. 0001,0010,0100,1000 -> 1, 2, 4, 8 -> loop 4�� �ݺ� x<<1
	//�� �̰� ��ư� ���߳�.
	@org.junit.Test
	public void test3() {
		int a = 1 ;
		int t = 30;
		for (int i = 0 ; i <= 7 ; i++) {
			//System.out.print(a);
			System.out.print(t + ",");
			t = t+a;
			a = a<<1;
		}
	}
	*/
	
	//�Ʒ� �ڵ尡 �� ����ϴ�.
	//t���� ���� 1bit Ʋ���� ���ϱ� (�� �ڸ������� 1�� ���Ѵ�. 0001,0010,0100,1000 -> 1, 2, 4, 8 -> loop 4�� �ݺ� x<<1
	@org.junit.Test
	public void test4() {
		int a = 1 ;
		int t = 30;
		for (int i = 0 ; i <= 7 ; i++) {			
			System.out.print(t+(a<<i) + ",");
		}
	}
	
	@org.junit.Test
	public void test5() {
		String val = "3*4+1";
		
		Stack<Object> numbers = new Stack<>();
		Stack<Object> oper = new Stack<>();
		
		int len = val.length();
		boolean isPriority = false; 
		for (int i = 0 ; i < len ; i++) { 
			char t = val.charAt(i);
			
			//oper
			if ( t == '+') { 
				oper.add(t);
			}else if (t == '-'){
				oper.add(t);
			}else if (t == '*') {
				oper.add(t);
				isPriority = true;
			}else if (t == '/') {
				oper.add(t);
				isPriority = true;
			//number	
			}else{
				numbers.add(t);
				//number �� ���ߴµ�, priorty flag �� ����2���� ����, ����ϰ� �ٽ� �ִ´�.  
				if (isPriority){
					int calcval = 0; 
					char op = (char)oper.pop();
					char n1 = (char)numbers.pop();
					char n2 = (char)numbers.pop();
					if (op =='*'){
						calcval = (n1-48)*(n2-48);						
					}else if (op =='/'){
						calcval = (n1-48)/(n2-48);
					}
					numbers.add(Integer.toString(calcval));
					
					isPriority = false;
				}
			}
		}
		
		len = numbers.size();
		for (int i = 0; i < len; i++) {
			System.out.print(numbers.get(i) + ",");
			numbers.get(i);
			oper.get(i);
			
		}
		System.out.println();
		len = oper.size();
		for (int i = 0; i < len; i++) {
			System.out.print(oper.get(i) +",");
		}
		
	}
	
	
	/**
	 * while �� ���ǹ��� ����µ� �ð��� �� �ɸ���..
	 * pattern �� �� ����. start ���� / stop ���� 
	 */
	@org.junit.Test
	public void test1(){
		String t = "011001";		
		int len = t.length() -1 ;
		
		boolean start = false;
		int c = 0 ; 
		
		while (len>=0) {
			// start ���� 
			if (!start && t.charAt(len) =='0')
				start = true;			
			if (start) {
				//stop ���� 
				if (t.charAt(len) =='1') break;				
				c++;
			}				
			len--;
		}
		
		System.out.println(c);
		
		c = 0 ;
		start=false;
		for (int i = t.length()-1; i >= 0  ; i--) {
			// start ���� 
			if (!start && t.charAt(i) =='0')
				start = true;
			
			if (start) {
				//stop ���� 
				if (t.charAt(i) =='1') break;				
				c++;
			}
		}
		System.out.println(c);
	}
	
}
	