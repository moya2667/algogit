package coding0522;

import static org.junit.Assert.*;

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

	link ll = new link(); 
	@Test
	public void test() {
		char[] a0 = {'1','1'};
		char[] a1 = {'3','3'};
		char[] a2 = {'5','5'};
		char[] a3 = {'7','7'};
		
		ll.add(a0);
		ll.add(a1);
		ll.add(a2);
		ll.add(a3);
		
		ll.print();
		
		ll.delete(a3);
		ll.print();
	}
	
	class link {
		node head , tail; 
		
		void add(char[] a){
			node n = new node(a);
			if (head == null) {
				head = n; 
				tail = n; 
			}
			
			tail.next = n; 
			n.prev = tail; 
			tail = n;
		}
		
		public void print() {
			// TODO Auto-generated method stub
			node p = head;
			
			while (p != null) {
				System.out.print(p.data);
				System.out.print("|");
				p = p.next;
			}
			
			System.out.println();
		}

		void delete(char[] t){
			node f =  find (t);
			if ( f == null) {
				System.out.println("not found ");
				return;
			}
			
			//한개일경우 
			if ( f == head && f == tail) { 
				head = null;
				tail = null;
				return;
			}
			
			//맨 앞일경우 
			if ( f == head) { 
				head = f.next ;				
				f.next.prev = null;
				return; 
			}
			//맨 마지막일경우 
			else if ( f== tail ){ 
				node p = f.prev;
				p.next = null; 
				tail = p; 
				return;
			}
			
			//중간일경우
			node pr = f.prev;
			node ne = f.next;
			
			pr.next = ne; 
			ne.prev = pr;
			f = null;
		}
		
		node find(char[] t) {
			
			node f = head; 
			
			boolean isfind = false; 
			while(f != null) {
				
				char[] fdata = f.data;
				int len = t.length ;
				
				//find
				for (int i = 0; i < len ; i++) {
					if ( t[i] != fdata[i]) {						
						break;
					}
					isfind = true;
					return f;
				}
				//search
				f = f.next;
			}
			return null;
		}
	}
	
	class node { 
		char[] data ;
		node prev,next;
		node (char[] data) { 
			this.data = new char[data.length];
			for (int i = 0 ; i < data.length;i++){
				if (data[i] == '\0') break; 
				this.data[i]= data[i];
			}
		}
	}

}
