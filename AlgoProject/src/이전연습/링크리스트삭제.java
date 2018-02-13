package 이전연습;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class 링크리스트삭제 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		link li = new link();
		li.add('a');
		li.add('b');
		li.add('c');
		
		li.delete('a');
		li.delete('c');
		
		li.print(); 
	}
	
	class link{

		node head,tail;
		
		public void add(char c) {
			// TODO Auto-generated method stub
			node n = new node(c);
			if ( head == null) { 
				head = n;
				tail = n;
			}else{
				tail.next = n ; 
				n.prev = tail;
				tail = n;
			}
		}

		public void delete(char c) {
			// TODO Auto-generated method stub
			node d = find(c);
			if ( d == null ) {
				System.out.println("nothing");
				return;
			}
			
			//한개일경우 처리
			if ( d == head && d == tail) {
				head = null;
				tail = null; 
				d = null;
				return;
			}
			
			//그대로
			if ( d == head ) { 
				head = head.next;
				return;
			}else if ( d == tail){				
				tail = tail.prev;
				return;
			}
			
			node pr = d.prev;
			node ne = d.next;
			pr.next = ne; 
			ne.prev = pr; 
		}

		public node find(char c) {
			node h = head ;
			while( h != null) {
				if ( h.c  == c){
					return h;
				}
				if ( h == tail) break;
				h = h.next ;
			}
			
			return null;
			
		}
		public void print() {
			// TODO Auto-generated method stub
			node h = head ;
			
			while( h != null) { //tail 검색처리 
				System.out.print(h.c); 
				if ( h == tail) break;				
				h = h.next ;
			}
			
			System.out.println();
		}
		
	}
	class node{
		node next , prev; 
		char c; 
		node(char c){
			this.c = c;
		}
	}
}
