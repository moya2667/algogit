package coding1115;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class hashMoya {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test1() {
		int hashcode = 1;
		int[] data = new int[10000]; 
		
		for (int i = 0 ; i < data.length ;i++) {			
			int prime = ((i+1)*(i+1)+(i+1)+41);
			hashcode = (hashcode<<5) + data[i]*prime;
			if (data[hashcode] != 0) { 
				System.out.println("conflict =" + data[hashcode]);
			}
			data[hashcode] = i;
		}
		
		System.out.println("no conflict");
		
		
	}
	
	
	

}
