package coding0302;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class datastrcutrue {

	@Test
	public void testHashMap() {
		HashMap map = new HashMap<>();
		map.put('1', "data");
		map.put('2', "data2");
		map.put('3', "data3");
		
		Iterator e = map.keySet().iterator();
		
		while(e.hasNext()){
			System.out.println(e.next());
		}
	}
	
	@Test
	public void testTreeMap() { //이거 sort 빼고 할수 있는게 없네.
		TreeMap map = new TreeMap<>();		
		map.put('1', "data");
		map.put('2', "data2");
		map.put('3', "data3");
		
		System.out.println(map.firstEntry().getKey());
		
		Iterator e = map.keySet().iterator();
		
		while(e.hasNext()){
			Object t = e.next();
			System.out.println(map.get(t));
		}
	}
	@Test
	public void testLinkedList() {		
		LinkedList t = new LinkedList(); 
		t.add(1);
		t.add(2);
		t.add(3);
		
		Iterator iter = t.iterator();
		
		while(iter.hasNext()) { 
			System.out.println(iter.next());
		}
	}
	
	
	@Test
	public void testQueueList() {
		Queue t = new LinkedList();
		t.add(3);
		t.add(4);
		t.add(5); 
		
		while(!t.isEmpty()){ 
			System.out.println(t.poll());
		}
	}
	
	@Test
	public void testLinkedListUser(){
		link link = new link();
		link.add('1');
		link.delete('1');
		link.print();
		
		/*
		link.add('2');
		link.add('3');
		link.add('4');
		link.delete('2');
		link.delete('4');
		link.print();
		*/ 
	}
	
	class node{
		node prev,next;
		char c;
		node(char c){
			this.c = c;
		}
	}
	
	class link{
		node head , tail;
		
		
		void add(char c){
			node n = new node(c);
			if (head == null) {
				head = n ;				
				tail = new node('\0');
				head.next = tail;
				tail.prev = head;
				return;
			}
			
			node t = tail.prev ;
			t.next = n;
			n.prev = t;
			
			n.next = tail;
			tail.prev = n ;
		}
		
		
		void delete(char c){
			node f = find(c);
			
			if (f == null){ 
				System.out.println("not found node" );
				return;
			}
			
			//head 한개만 있을경우 예외처리
			if (f == head && head.next == tail) {
				head = null;
				tail = null;
				return; 
			}
			
			//맨 앞에 있을경우 
			if (f == head) {
				node t = head.next ;
				head = t ;
				t.prev = null;
				return;
			}
			
			//중간에 있을 경우 	
			node pr = f.prev;
			node ne = f.next;
			pr.next = ne; 
			ne.prev = pr; 
			
		}
		
		node find(char c){ 
			node f = head.next;
			while ( f != tail) {
				if ( f.c  == c ) return f;  
				f = f.next;
			}
			return null;
		}
		
		void print(){
			node f = head;
			while ( f != tail) {
				System.out.print(f.c + " ");
				f = f.next;
			}
			System.out.println();
		}
	}
	
	
	

}
