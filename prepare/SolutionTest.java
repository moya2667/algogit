import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SolutionTest {

    Solution solution;
    
    @Before
    public void setUp() {
        solution = new Solution();
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
    /*
    Input: l1 = [2,4,3], l2 = [5,6,4]
    Output: [7,0,8]
    Explanation: 342 + 465 = 807.
    */
    @Test
    public void addTwoNumbers_sameNumberOfDigits() {
        ListNode l1 = createList(2, 4 ,3);
        ListNode l2 = createList(5, 6, 4);

        ListNode result = solution.addTwoNumbers(l1, l2);

        checkResult(result, 7, 0 ,8);
    }

    @Test
    public void addTwoNumbers_onlyZeros() {
        ListNode l1 = createList(0);
        ListNode l2 = createList(0);

        ListNode result = solution.addTwoNumbers(l1, l2);

        checkResult(result, 0);
    }

    /*
    Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
    Output: [8,9,9,9,0,0,0,1]
    
    [9]
	[1,9,9,9,9,9,9,9,9,9]

     */
    @Test
    public void addTwoNumbers_differentNumberOfDigits2() {
        ListNode l1 = createList(9);
        ListNode l2 = createList(1,9,9,9,9,9,9,9,9,9);

        ListNode result = solution.addTwoNumbers(l1, l2);


        //[0,0,0,0,0,0,0,0,0,0,1]
		
        checkResult(result, 8,9,9,9,0,0,0,1);
    }

    
    @Test
    public void addTwoNumbers_differentNumberOfDigits() {
        ListNode l1 = createList(9, 9, 9, 9, 9, 9, 9);
        ListNode l2 = createList(9, 9, 9, 9);

        ListNode result = solution.addTwoNumbers(l1, l2);

        checkResult(result, 8,9,9,9,0,0,0,1);
    }

    @Test
    public void addTwoNumbers_whenOneListIsNull() {
        ListNode l2 = createList(0, 1);

        ListNode result = solution.addTwoNumbers(null, l2);

        checkResult(result, 0, 1);
    }

    // undefined in requirements
    @Test
    public void addTwoNumbers_whenBothListsAreNull() {

        ListNode result = solution.addTwoNumbers(null, null);

        assertNull(result);
    }

    @Test
    public void addTwoNumbers_extraCarry() {
        ListNode l1 = createList(9, 9);
        ListNode l2 = createList(1);

        ListNode result = solution.addTwoNumbers(l1, l2);

        checkResult(result, 0, 0, 1);
    }

    private void checkResult(ListNode result, int... entries) {
        assertNotNull(result);
        ListNode current = result;
        for (int entry : entries) {
            assertNotNull(current);
            System.out.println(entry + " = " + current.val);
            assertEquals(entry, current.val);
            current = current.next;
        }
    }

    private ListNode createList(int... entries) {
        ListNode result = new ListNode(0);
        ListNode current = result;
        for (int entry : entries) {
            current.next = new ListNode(entry);
            current = current.next;
        }
        return result.next;
    }
    
    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {        

            Integer v1 = getDigitValue(l1);
            Integer v2 = getDigitValue(l2);
            
            int num = v1+v2 ;         
            System.out.println(num);
            ll l= new ll(); 
            if (num == 0) {
            	l.add(0);
            }
    		while (num > 0) {
    			int v = num % 10;
    			num = num / 10;            
    			l.add(v);			
    		}
            //l.print();
            return l.h;
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
    				System.out.print(n.val + " , ");
    				n = n.next;
    			}
    			System.out.println();
    		}
        }
        public int getDigitValue(ListNode l) {        
            int c = 0;
            int result = 0 ; 
            ListNode h = l;
            while (h != null) {
                if (c == 0) { 
                    c = 1;
                }                    
                else {
                    c = c * 10;
                }            
                result += h.val * c;
                h = h.next;
            }
            return result;
        }

    }    
}

