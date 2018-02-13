package 코딩0211;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class 버블소트_구조체 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
	}

	int[] idx = {1,2,3,4,5} ;
	int[] value ={12,13,14,12,11} ;
	
	class node { 
		int idx;
		int value;
		node(int idx , int value){
			this.idx = idx;
			this.value = value;
		}
	}
	
	@Test
	public void test() {
		
		node[] nodelist = new node[10];
		for (int i = 0 ; i < idx.length; i++){
			nodelist[i] = new node(idx[i], value[i]);
		}
		
		for (int i = 0 ; i < idx.length ; i++) { 
			for (int j= 0 ; j < idx.length-1 ; j++) { 
				
				if (nodelist[j].value < nodelist[j+1].value) { 
					node temp = nodelist[j]; 
					nodelist[j] = nodelist[j+1];
					nodelist[j+1] = temp;
				}
			}
		}
		
		for (int i = 0 ; i < idx.length ; i++){
			System.out.println(nodelist[i].value + " : " + nodelist[i].idx);
		}
		
		
	}
	
	@Test
	public void bubbleTest1() {
		int[] idx = {1,2,5,4,2} ;
		
		for (int i = 0 ; i < idx.length ; i++) { 
			for (int j= 0 ; j < idx.length-1 ; j++) { 
				if ( idx[j] < idx[j+1] ) { 
					int temp = idx[j];
					idx[j] = idx[j+1];
					idx[j+1] = temp;
				}
			}
		}
			
		for (int i = 0 ; i < idx.length ; i++){
			System.out.print(idx[i]);
		}
	
	}
	
}
