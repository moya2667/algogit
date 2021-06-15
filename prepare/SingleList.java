
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SingleList {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ll l = new ll();
		l.add(1);
		l.add(2);
		l.add(3);
		
		//l.print();
		ListNode n = l.h ; 
		while(n!=null) { 
			System.out.print(n.val);
			n = n.next;
		}
		System.out.println();
		
				
	}
	
	class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
	
    class ll { 
        public ListNode h , t; 
        void add(int num) {
            ListNode n = new ListNode(num);
            if (h == null) { 
                h = n; 
                t = n; 
            }else{
                t.next = n ; 
                t = n ;
            }
        }
		public void print() {
			// TODO Auto-generated method stub
			ListNode n = h ; 
			while(n!=null) { 
				System.out.print(n.val);
				n = n.next;
			}
			System.out.println();
		}
    }

}
