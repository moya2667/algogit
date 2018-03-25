package coding0317;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

public class Test {

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
		
	}
	
	

}
