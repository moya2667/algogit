package coding1030;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testCovertIdx {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test1() { 
		char[] d = {'1','2','3','5','\n','p'}; 
		String sD =new String(d);
		System.out.println(sD);
	}
	
	@Test
	public void testIdxToArray() { 
		//char[] d = {'a','b','c','\0','d','e','f','g','h','\0','i','j'};
		//char[] d = {'a','b','c','\0','d','e','x','y','f','g','h','\0','i','j'};
		char[] d = {'a','b','c','\0',
					'd','e','x','y','f',
					'g','h','\0',
					'i','j','\0',
					'y'};
		
		int row = 0 , col = 0 ;
		int width = 5;		
		
		for (int i = 0 ; i < d.length ; i++) {
			
			if (row == width || d[i] =='\0') {
				row = 0;
				col = col+1;
			}
			
			//d[i] == '\0' 일때 오히려 꼬임
			if (d[i] != '\0') {
				int idxValue= (col*width) + row;;
				System.out.println(d[i] +":" + idxValue +"=>"+ (idxValue/width)+","+(idxValue%width));
				row = row+1;
			}
		}
		
	}	

	

}
