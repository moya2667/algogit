package 이전연습;


import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import 이전연습.트리인서트_JSON.treelink.treenode;


public class 트리인서트_JSON {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	treelink tree = new treelink();	

	/*
    0
      1
        2
      3           
	 */
	//char[] data = {'0','{','1','{','2','}','3','}'};	
	
	
	char[] data = {'0','{','1','{','2','}','}','{','3','}','{','4','}'};
	char[] data1 = {'0','{','1','{','2','{','3','}','}','}'};	
	

	//3번째 시도 
	@Test
	public void test3(){
		treenode root = tree.addroot(data[0]);
		analyze3(root , data1 , 1 );
		tree.preOrder1(root,0);
		
	}
	
	
	int none = 0; 
	int slibing = 1;
	int child = 2;
	
	int mode = none;
	
	void analyze3(treenode r , char[] data , int idx ) {
		if ( data.length == idx || r == null ) {
			return;
		}
		if (data[idx] =='{') {
			idx = idx+1; //{ 면 다음 idx를 가르켜서, data 사전에 넣는다.
			treenode rChild = tree.addchild(r, data[idx]);
			analyze3(rChild,data,idx+1);
		}		

		else if (data[idx] =='}') {
			analyze3(r.parent,data,idx+1);
		}
	}
	
	
	//0{1{2}}{3}	
	void analyze3(treenode r , char[] data , int idx , int mode) {
		
		if ( data.length == idx || r == null ) {
			return; 
		}
		
		if (data[idx] =='{') {
			analyze3(r,data,idx+1,child); 
		}
		
		else if (data[idx] =='}') { 
			analyze3(r,data,idx+1,slibing); 
			return;
		}
		
		//아닐경우 
		treenode n = null;
		if ( mode  == slibing) { 
			n = tree.addchild(r.parent, data[idx]);
		}else if (mode== child){
			n = tree.addchild(r, data[idx]);
		}
		
		analyze3(n,data,idx+1,none);
		
	}


	@Test
	public void test(){ 
				
		treenode root = tree.addroot('0');
		treenode chA= tree.addchild(root,'a');
		treenode chB= tree.addchild(root,'b');
		
		treenode chC= tree.addchild(root,'c');
		
		tree.addchild(chA,'1');
		tree.addchild(chA,'2');
		tree.addchild(chA,'3');
		
		//tree.preOrder1(root);
		
		tree.serachchild(chA);
		System.out.println("childcnt = " + tree.cnt);
		
	}
	
	class treelink{
		treenode root;	
		int cnt = 0 ; 
		class treenode{
			treenode parent;			
			link childlist = new link();
			treenode next , prev;
			char c;
			
			treenode(char c){
				this.c = c ; 
			}
			
			int getchildC(){
				return childlist.size;
			}
			link getchildlist(){
				return childlist;
			}
		}
		
		class link {
			int size = 0 ; 
			treenode head;
			treenode tail; 
			
			treenode add(treenode n){
				
				if (head == null) { 
					head = n;
					tail = n;
				}else{
					tail.next = n;
					n.prev = tail;
					tail = n;
				}
				
				return n;
			}
		}
		
		treenode addchild(treenode node, char c) {
			// TODO Auto-generated method stub
			treenode n = new treenode(c);
			n.parent = node;
			return node.getchildlist().add(n);
		}
		
		public void preOrder(treenode root) {
			// TODO Auto-generated method stub
			if (root == null) return;
			System.out.println(root.c); 
			link clist = root.getchildlist();
			
			if ( clist == null) return;
			
			treenode t = clist.head;
			
			while (t != null){
				System.out.println(t.c);
				t = t.next;
				preOrder(t);	
			}
			
			
			if ( t != null ){
				preOrder(t);
			}
			
			 
			
		}		
		
		public void preOrder1(treenode root , int depth) {
			// TODO Auto-generated method stub
			if (root == null) return;
			for ( int i = 0 ; i < depth ; i++ ) System.out.print(" "); 				
			System.out.println(root.c); 
			
			link clist = root.getchildlist();
			
			if ( clist == null) return;
			
			treenode t = clist.head;
			
			if ( t != null ){
				preOrder1(t , depth+1);
			}
			
			 
			while (t != null){
				t = t.next;
				preOrder1(t , depth+1);	
			}
		}
		
		
		public void serachchild(treenode root) {
			// TODO Auto-generated method stub
			if (root == null) return;
			System.out.println(root.c);
			cnt++;
			link clist = root.getchildlist();
			
			if ( clist == null) return;
			
			treenode t = clist.head;
			
			if ( t != null ){
				serachchild(t);
			}
			 
			while (t != null){
				t = t.next;
				serachchild(t);	
			}
		}

		treenode addroot(char c) {
			// TODO Auto-generated method stub
			root = new treenode(c);
			return root;
		}
		
		treenode getroot() {
			return root; 
		}		
	}

}
