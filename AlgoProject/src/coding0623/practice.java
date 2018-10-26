package coding0623;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class practice {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	static HashMap map = new HashMap();
	@Test
	public void test() {
		
		String a = "aaaa";
		char[] b = new char[10]; 		
		String a1 = new String(b);
		
		tree tr = new tree();
		treenode root = new treenode("root".toCharArray());
		treenode de1 = tr.add(root,"aa".toCharArray());		
		treenode de2 = tr.add(root,"bb".toCharArray());
		treenode de1_1 = tr.add(de1,"aaa".toCharArray());
		treenode de1_1_1 = tr.add(de1_1,"aaaa".toCharArray());
		tr.print(root, 0);
		
		treenode f = tr.find("bb".toCharArray());
		System.out.println(f.parent.val);
	}
	
	class treenode {
		treenode parent;
		treenode next , prev;		
		char[] val ; 
		llist childlist ; 
		
		public treenode (char[] t) { 
			val = t; //reference
			childlist = new llist();
		}
	}
	class llist {
		treenode head, tail; 
		treenode add(treenode pa , char[] d) {
			String strD = new String(d);
			treenode ne = new treenode(d);
			ne.parent = pa ;
			
			if ( head == null) { 
				head = ne;
				tail = ne;
				map.put(strD, ne);
				return ne;
			}
			tail.next = ne ; 
			ne.prev = tail;
			tail = ne;
			
			map.put(strD, ne);
			return ne;
		}
	}

	class tree {
		treenode add (treenode cur , char[] d) {
			return cur.childlist.add(cur , d);
		}
		
		boolean delete(treenode d) {
			treenode f = find(d.val);
			if ( f == null) return false;
			
			return true;
			
		}
		treenode find(char[] f) {
			Object t = map.get(new String(f));
			return (treenode)t;
		}
		
		void print (treenode n , int de ) {
			if ( n == null) return;
			
			for ( int i = 0 ; i < de ; i++) {
				System.out.print("+");
			}
			System.out.println(n.val);			
			
			treenode h = n.childlist.head;
			
			if ( h == null) return;
			
			print(h , de+1);			
			
			while( h != null) { 
				h = h.next;
				print(h , de+1);
			}
			
		}
	}
	

}
