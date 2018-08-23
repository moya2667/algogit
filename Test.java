import static org.junit.Assert.*;

import java.util.HashMap;


public class Test {
	
	@org.junit.Test
	public void test1() {
		treenode[][][] tlist = new treenode[100][100][100];
		treenode t1= new treenode("a1");
		tlist[0][0][1] = t1;
		
		String d = tlist[0][0][1].get();
		System.out.println(d);		
	}
	
	@org.junit.Test
	public void hash() {		
		HashMap<char[] , Integer> t = new HashMap();
		char[] a1 = "a".toCharArray();
		t.put(a1, 1);
		t.put("b".toCharArray(), 2);
		t.put("c".toCharArray(), 3);		
		char[] e = "a".toCharArray();
		//hash key of char[] was not supported
		System.out.println(t.get(e));		
	}
	
	@org.junit.Test
	public void hashString() {
		HashMap<String , Integer> t = new HashMap();
		String a1 = "aa";
		t.put(a1, 1);
		t.put("bb", 2);
		t.put("cc", 3);		
		System.out.println(t.get("aa"));	
		System.out.println(t.get("bb"));
		System.out.println(t.get("cc"));	
		
	}	
	

	@org.junit.Test
	public void test() {
		
		treenode root = new treenode("123");
		Tree tr =  new Tree(root);
		
		treenode one = tr.add(root , "1");
		treenode two = tr.add(root , "2");
		treenode three = tr.add(root , "3");
		
		//tr.delete("1".toCharArray()); 
		treenode two_child = tr.add(two, "21");
		
		tr.print(root, 0);
		
		tr.delete("2");		
		tr.print(root, 0);		
		
		
		tr.delete("3");
		tr.print(root, 0);
		
		tr.delete("1");
		tr.print(root, 0);
		
		
	}
	
	class Tree{ 
		treenode root;
		Tree(treenode root) { 
			this.root = root;
		}
		public void print(treenode r , int dep) {
			
			if ( r == null ) return; 
			
			for (int i = 0; i < dep; i++) {
				System.out.print("+");
			}
			System.out.println(r.get());
			
			treenode h = r.clist.head;
			if (h != null ) { 
				print(h , dep+1);
			}
			
			while(h != null) { 
				h = h.next;
				print(h,dep+1);
			}
			
			
		}	
		
		
		public boolean delete(String d) {			
			treenode n = map.get(d);
			if (n == null) { 
				System.out.println("not found : " + d);
				return false;
			}
			//현재 부모노드의 list 를 가져와서 해당 노드 연결을 끊어버린다 
			n.parent.clist.delete(n);
			
			return false;
			
		}
		public treenode add(treenode t, String data) {
			
			treenode n = new treenode(data);
			
			n.parent = t;
			
			
			return t.clist.add(n);
		}
	}
	
	HashMap<String,treenode> map = new HashMap();
	
	
	class ll { 
		treenode head,tail; 
		treenode add(treenode n){			
						
			if( head == null) {
				head = n;
				tail = n;				
			}else{ 			
				tail.next = n; 
				n.prev = tail; 
				tail = n;
			}
			
			map.put(n.get() , n );
			
			return n;
		}
		void delete(treenode n){			 
			
			//하나만 존재 
			if (n == head && n==tail) { 
				head = null;
				tail = null;
				return;
			}
			
			if (n == head) {				
				head = n.next ;
				n.next.prev = null; 
				return;
			}
			
			if (n == tail) {
				treenode p = n.prev;
				p.next = null ; 
				tail = p ;
				return;								
			}
			
			//중간일경우
			treenode ne = n.next;
			treenode pr = n.prev;
			
			pr.next = ne;
			ne.prev = pr;
			n = null;
			
		}		
	}	
	
	
	class treenode {
		treenode parent;
		treenode next,prev;
		String charA;
		ll clist = null;		
		public treenode(){};
		public treenode(String charArray) {
			// TODO Auto-generated constructor stub
			this.charA=charArray;
			clist = new ll();			
		} 
		public String get(){
			return this.charA;
		}
		
	}

}
