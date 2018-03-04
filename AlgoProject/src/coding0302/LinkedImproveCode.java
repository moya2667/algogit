package coding0302;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedImproveCode {

	@Test
	public void test() {
		link link = new link();
		link.add('1');
		link.delete('1');
		link.print();
		
		
		link.add('2');
		link.add('3');
		link.add('4');
		//link.delete('2');
		link.delete('4');
		link.print();
	}
	
	class link{
		node head , tail;
		//아래처럼 head/tail 따로 가져갈경우
		void add(char c){
			node n = new node(c); 
			if ( head == null ) {
				head = new node('!');
				head.next = n ; 
				n.prev = head; 
				
				tail = new node('\0');
				n.next = tail;
				tail.prev = n; 
				return; 
			}
			
			tail.prev.next = n ; 
			n.prev = tail.prev; 
			
			n.next = tail;
			tail.prev = n ;
		}
		
		void delete(char c){
			node f = find(c);
			
			if (f == null){ 
				System.out.println("not found node" );
				return;
			}			
			//항상 Node가 중간에 있어서 예외 코드들이 필요없어진다.(Head/Tail) 	
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

	class node{
		node prev,next;
		char c;
		node(char c){
			this.c = c;
		}
	}

}
