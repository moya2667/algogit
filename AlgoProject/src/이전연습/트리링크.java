package 이전연습;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import 이전연습.링크리스트삭제.node;

public class 트리링크 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		tree tree = new tree();
		treenode root = new treenode('r');
		treenode t = tree.add( root , 'a');
		treenode d1= tree.add(t ,'b');
		tree.add(t ,'c');
		tree.add(d1,'d');
		tree.add(root,'d');	
		tree.print(root, 0);
	}
	
	class treenode{
		treenode parent;
		treenode next , prev;
		link link = new link();
		char n; 
		treenode(char n){
			this.n = n;
		}
	}

	class tree {		

		treenode root; 
		
		public treenode add(treenode pa, char c) {
			// TODO Auto-generated method stub
			if ( root == null ) {
				root = pa;
				//return root;
			}
			
			return pa.link.add(pa, c);
		}
		
		public void print(treenode t , int depth) {
			// TODO Auto-generated method stub
			if ( t == null ) return;
			for ( int i = 0 ; i < depth ; i++ ) System.out.print(" ");
			System.out.println(t.n);
			
			treenode f = t.link.head;
			
			if ( f == null) return; 
				
			if ( f != null ){
				print(f , depth+1);
			}
			
			while ( f != null) { 
				f = f.next;
				print(f , depth+1);
			}
		}
		
	}
	
	class link { 
		treenode head,tail;
		
		public treenode add(treenode pa , char c) {
			// TODO Auto-generated method stub
			treenode n = new treenode(c);
			pa = n.parent;
			
			if ( head == null) { 
				head = n;
				tail = n;
			}else{
				tail.next = n ; 
				n.prev = tail;
				tail = n;
			}
			
			return n;
		}
	}
}
