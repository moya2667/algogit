package 이전연습;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class 줄내리기 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//그냥 소트하면 되는구나..간단히
	@Test
	public void 줄내리기(){ 
		//char[] t = {'x','x','x','2','3'} ;
		char[] t = {'1','x','2','x','x'} ;
		
		int len = t.length;
		for (int i = 0 ; i < len ; i ++){
			for (int j = 0 ; j < len-1 ; j ++){  //len-1 주의 
				if ( t[j] == 'x' && t[j+1] != 'x' ){
					char temp;
					temp = t[j];   // i,j 장난치는게 아니라, 그냥 j와 j+1로 장난치는거였네..기억이 잘
					t[j] = t[j+1];
					t[j+1] = temp; 
				}
			}
		}
		
		for (int i = 0 ; i < len ; i++){
			System.out.print(t[i]);
		}
		//System.out.println("t");
		
	}

}
