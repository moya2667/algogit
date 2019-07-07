package coding1629;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class sort {


	link ll = new link();
	@Test
	public void testHash() {
		HashMap map = new HashMap();
		map.put(1, "2");
		map.put(2 ,"3");
		
		System.out.println(map.get(1));
		System.out.println(map.get(2));
		
		Iterator iter = map.keySet().iterator();
		while(iter.hasNext()) { 
			Object t = iter.next();
			System.out.println(map.get(t));
		}
		
	}
	
	@Test
	public void test() {
		ll.addSort(11);		
		ll.addSort(22);
		ll.addSort(333);
		ll.addSort(5);
		ll.addSort(0);
		ll.addSort(4);
		ll.addSort(1);		
		ll.addSort(2);
		ll.addSort(3);
		ll.addSort(8);
		ll.print();
	}
	
	class node {
		int v;
		node next , prev;
		node(int i) {
			this.v = i;
		}
	}
	static int initV = 9999999;
	class link{
		node head,tail;
		public void addSort(int i) {			
			node n = new node(i);
			
			if (head == null) { 
				head =new node(initV);  
			}
			
			//head next
			node h = head.next;
			//last = head 로 
			node last = head;
			//cnt 로 5번째까지만 insert sort 
			int cnt = 1; 
			
			while(h!= null && cnt <= 5) {
				cnt++;
				if (h.v < n.v ) {
					node pre = h.prev;
					pre.next = n ;
					n.prev = pre;
					
					n.next = h;
					h.prev = n;
					return;
				}				
				last = h;
				h = h.next;
				
			}
			
			last.next = n;
			n.prev = last;
		}
		
		public void add(int i) {			
			node n = new node(i);
			if (head == null) { 
				head = n ;
				tail = n ;
			}else{
				tail.next = n;
				n.prev = tail; 
				tail = n;
			}
		}

		public void print() {
			node h = head.next;
			while(h!=null){
				System.out.print(h.v +" ");
				h =h.next;
			}
			System.out.println();
		}
		
	}

}

