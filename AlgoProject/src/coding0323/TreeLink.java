package coding0323;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TreeLink {

	@Test
	public void test() {
		Tree tree = new Tree();
		treenode root = new treenode('1');
		treenode two = tree.addnode( root , '2');
		treenode three = tree.addnode( two , '3');
		
		treenode four = tree.addnode( two , '4');
		treenode three1 = tree.addnode( four , '9');	
		treenode five = tree.addnode( two , '5');
		
		tree.print(root, 0 );
		
		treenode fi =  tree.find(root,three1);
		if (fi != null) System.out.println(fi.c);
		
		
		tree.print(root, 0 );
	}


	class Tree{
		
		treenode root ;

		public treenode addnode(treenode pa, char c) {			
			// TODO Auto-generated method stub			
			treenode n = new treenode(c);
			if ( root == null) root = n ;
			
			n.parent = pa ;			
			return pa.list.add(n);
		}
		
		public void delete(treenode f) {
			// TODO Auto-generated method stub
			treenode n = find(root,f);
			treenode pa = n.parent;
			linkedlist list = pa.list;
			
		}

		public void print(treenode root, int i) {
			// TODO Auto-generated method stub
			if( root == null) return;
			for ( int j = 0 ; j < i ; j++ ) System.out.print(" ");
			System.out.println(root.c);
			
			treenode s = root.list.head;
			if ( s != null) { 
				print(s,i+1);
			}
			
			while( s!= null) {
				s = s.next;
				print(s,i+1);
			}
		}
		boolean isfind = false;
		public treenode find(treenode root , treenode f) {
			// TODO Auto-generated method stub
			if (root == null) return null;
			if (root == f) {
				isfind = true;
				return root;			
			}
			
			treenode s = root.list.head;
			
			treenode t = find(s,f);
			if ( isfind == true ) {
				System.out.println("found");
				return t; 
			}
			
			while( s!= null) {
				s = s.next;
				treenode x = find(s,f);
				if (isfind == true){
					System.out.println("found");
					return x;
				}
			}
			
			return null;
		}
	}
	
	class treenode{
		treenode parent;
		linkedlist list = new linkedlist();
		treenode next , prev;
		
		char c; 
		treenode(char c){
			this.c= c;
		}		
	}
	
	class linkedlist{
		public treenode head , tail;
		
		linkedlist(){
		}
		
		treenode add(treenode n) {
			if ( head == null) { 
				head = new treenode('!');
				tail = new treenode('@');
				head.next = n;
				n.prev = head;
				tail.prev = n;
				n.next = tail;
			}else{
				treenode t = tail.prev;
				t.next = n;
				n.prev = t; 
						
				n.next = tail;
				tail.prev = n;
			}
			
			return n;
		}
		
		 
		/*
		treenode add(treenode n) {
			if ( head == null) { 
				head = n;
				tail = n;
			}else{
				tail.next = n ; 
				n.prev = tail;
				tail = n;
			}
			
			return n;
		}
		*/
	}
	

}
