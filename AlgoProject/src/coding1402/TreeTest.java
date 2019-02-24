package coding1402;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coding1402.Test1.node;

public class TreeTest {

	@Test
	public void test() {
		Tree tree= new Tree();
		node ori = tree.addchild( new node("root".toCharArray()) , new node("1".toCharArray()) );

	}
	
	class Tree{

		public node addchild(node o, node t) {
			return null;
		}
		
		
		
	}
	
	
	
	
	class linklist{
		node head , tail;
		int cnt = 0 ; 
		public void add(char[] s) {
			node t = new node(s);
			if (head == null) {
				head = t;
				tail = t;
			}else{
				tail.next = t;
				t.prev = tail;
				tail = t;
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
