package coding1402;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test1 {
	@Test
	public void testIdx() {
		//맨날 해깔리는거 
		//2행 3열 -> height / width 로 봐야한다.(중요) ->이부분을 항상 기억못하여 , 나중에 계산시 어려워함.
		//행열 (y,x) 를 idx 로 치환하는거 
		//2행 4열일경우  -> (1,2)은 -> 6 으로 변환되어야한다.    
		//width 4이니까 , 4*(y=1) + 2 -> 6 이어야함	
		//0 | 1 | 2 | 3  -> (0,0) (0,1) (0,2) (0,3) -> width 4 / height 2 이다..
		//4 | 5 | 6 | 7     (1,0) (1,1) (1,2) (1,3) 
		
		//이거다... 
		//(width*y) + x = idx; 
		//idx / width = y; 
		//idx % width = x;
		
	}
	
	@Test
	public void test() {
		HashMap map = new HashMap();
		char[] t = {'1','2'};		
		map.put(new String(t), "111");
		map.put("22", "222");
		
		System.out.println(map.get("12"));
		
//		if ( map.containsKey('2') ) {
//			System.out.println("exist");
//		}
		
		Iterator iter = map.keySet().iterator();
		while(iter.hasNext()) {
			Object key = iter.next();
			System.out.println(key);
		}
	}
	
	static node[] nodes = new node[10];
	
	@Test
	public void llTest() { 
		linklist ll = new linklist();
		ll.add("11".toCharArray());
		ll.add("2".toCharArray());
		ll.add("3".toCharArray());
		ll.print();
		
		ll.delete("2".toCharArray());
		
		System.out.println();
		//System.out.println(ll.delete("1".toCharArray()));
		
		ll.print();
	}
	
	class linklist{
		node head , tail;
		int cnt = 0 ; 
		public void add(char[] s) {
			nodes[cnt] = new node(s);
			nodes[cnt].d = s;
			if (head == null) {
				head = nodes[cnt];
				tail = nodes[cnt];
			}else{
				tail.next = nodes[cnt];
				nodes[cnt].prev = tail;
				tail = nodes[cnt];
			}			
			cnt++;
		}

		public void delete(char[] s) {
			node t = head ;
			
			while(t!=null){
				if ( equalTo(s, t.d) ) {
					t.delete  = true;
				}
				t = t.next;
			}
		}

		public void print() {
			node t = head ;
			
			while(t!=null){
				if ( t.delete == false ){ 
					System.out.println(new String(t.d));
				}
				t = t.next;
			}
		}
		
		private boolean equalTo(char[] ori , char[] tar){ 
			if ( ori.length != tar.length) return false;
			
			for (int i = 0 ; i <ori.length ;i++) { 
				if ( ori[i] != tar[i] ) return false;
			}
			
			return true;
		}

		
	}
	
	class node{
		boolean delete = false;
		node next,prev;
		char[] d ;
		node() {
			d= new char[10];
		}
		
		node(char[] t) { 
			d= new char[t.length]; 
			for(int i=0;i<t.length;i++){
				d[i] = t[i];
			}
		}
	}

}
