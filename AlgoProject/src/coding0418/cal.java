package coding0418;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class cal {

	@Test
	public void test() throws FileNotFoundException {
		FileInputStream ip = new FileInputStream("src\\coding0418\\input.txt");		
        Scanner sc = new Scanner(ip);
        
        char[] data = sc.next().toCharArray();
		Queue<char[]> q = new LinkedList();		
				
		//init
		char[] t = new char[4];
		for (int i = 0 ; i < 4 ; i++ ) {
			t[i] = '0';
		}
		
		int cnt = 3;
		int len = data.length-1;
		
		
		for (int j = len ; j >=0 ; j--) {
			t[cnt] = data[j];			
			
			if ( cnt == 0 || j == 0) {				
				q.add(t);				
				cnt = 3;
				//√ ±‚»≠
				t = new char[4];
				for (int i = 0 ; i < 4 ; i++ ) {
					t[i] = '0';
				}
				continue; //avoid cnt-- 
			}
			cnt--;
		}
		
		while(!q.isEmpty()) {
			char[] da = q.poll();
			System.out.println(da);
			int v = 0 ;
			int[] z = {1,10,100,1000};
			int j = 0 ;
			for (int i = 3 ; i >= 0 ; i--) {
				
				int temp = da[i] -'0';				
				v += temp*z[j];
				j++;
			}
			
			System.out.println("int = " + v);
			
		}
	}

}
