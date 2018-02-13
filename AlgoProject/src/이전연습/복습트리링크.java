package 이전연습;

import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import 이전연습.복습트리링크.treelink.treenode;

public class 복습트리링크 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	treelink tree = new treelink();
	
	

	
	@Test 
	public void encoding(){
		System.out.println(String.format("file.encoding: %s", System.getProperty("file.encoding")));
		System.out.println(String.format("defaultCharset: %s", Charset.defaultCharset().name()));
	}
	
	/*
    0
      1
        2
      3           
	 */
	char[] data = {'0','{','1','{','2','}','3','}'};
	
	@Test
	public void test1(){
		treenode root = tree.addroot(data[0]);
		analyze(null , data , 0);
		tree.preOrder(root);
	}
	
	@Test
	public void test2(){
		treenode root = tree.addroot(data[0]);
		analyze1(root , data , 1 , 0);
		tree.preOrder(root);
	}
	
	
	//3번째 시도 
	@Test
	public void test3(){
		treenode root = tree.addroot(data[0]);
		analyze3(root , data , 1  , mode);
		tree.preOrder1(root,0);
	}
	
	
	int none = 0; 
	int slibing = 1;
	int child = 2;
	
	int mode = none;
	
	void analyze3(treenode r , char[] data , int idx , int mode) {
		
		if ( data.length == idx ) {
			return; 
		}
		
		if (r == null) return;
		
		if (data[idx] =='{') {
			analyze3(r,data,idx+1,child); 
		}
		
		else if (data[idx] =='}') { 
			analyze3(r,data,idx+1,slibing); 
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
	
	
	
	boolean isReturn = false;
	void analyze1(treenode r , char[] data , int start , int depth) { 
		
		if (data[start] == data.length ) {
			System.out.println("완료");
			return;
		}		
		
		if (data[start] =='{') {
			isReturn=false;
			analyze1(r , data , start+1 , depth+1);
		}
		
		if(data[start] =='}') {
			isReturn = true;
			return;
		}
		
		if (!isReturn) {
			treenode child = tree.addchild( r, data[start] );
			analyze1(child,data,start+1, depth +1);
		}else{
			if ( r.parent != null) { 
				treenode child = tree.addchild(r.parent, data[start]);
				analyze1(child,data,start+1, depth +1);
			}
			
		}
		
	}
	
	
	
	void analyze(treenode n , char[] data , int start){
		
		if (start == 0 ) {
			treenode r= tree.addroot('0');
			analyze(r,data,start+1);
		}
		
		if (data[start] == data.length ) {
			System.out.println("완료");
			return;
		}
		
		//이거면 child 지 
		if (data[start] =='{') {			
			analyze(n , data , start+1);
		}else if(data[start] =='}') {
			System.out.println("올라가야지");
			return;
		}
		else{
			treenode b = tree.addchild( n, data[start] );
			analyze(b , data , start+1);
		}
		
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
				preOrder1(t , depth);	
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
