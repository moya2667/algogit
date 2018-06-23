package coding0415;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testtree {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		treenode n = new treenode('r');
		treenode o1 = n.li.add(n, '1');
		treenode o2 = n.li.add(n, '2');
		
		treenode o11= o1.li.add(o1, '5');
		treenode o12= o2.li.add(o2, '6');
		
		n.li.print();
		print(n , 0);
	}
	
	private void print(treenode r ,int dep) {
		if (r == null) return;
		
		if ( r != null ){
			for (int i = 0 ; i < dep ; i++) { 
				System.out.print("+");
			}
			System.out.println(r.c);
		}
		
		treenode h = r.li.head;
		
		if (h != null) {
			print(h , dep+1);
		}
		
		while( h != null) {
			h = h.next;
			print(h , dep+1);
		}
		
	}
	
	class treenode {
		treenode parent;
		link li = new link();
		treenode next , prev;
		char c; 
		treenode (char c){
			this.c = c;
		}
	}
	
	class link{
		treenode head , tail;
		treenode parent;
		
		treenode add(treenode pa , char c) {
			treenode n = new treenode(c);
			pa = n.parent;
			
			if (head ==null ) {
				head = n ;
				tail = n ; 
			}
			else{ 
				tail.next = n ;
				n.prev = tail;
				tail = n; 
			}
			return n;
		}
		
		boolean delete(char c) {
			return false;
		}
		boolean delete(treenode d) {
			return false;
		}		
		void print() { 
			treenode n = head;
			while( n != null ) {
				System.out.print(n.c);
				n = n.next ; 
			}
			System.out.println();
		}
	}

}
