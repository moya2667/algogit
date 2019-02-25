package coding1425;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;



//L	커서를 왼쪽으로 한 칸 옮김 (커서가 문장의 맨 앞이면 무시됨)
//D	커서를 오른쪽으로 한 칸 옮김 (커서가 문장의 맨 뒤이면 무시됨)
//B	커서 왼쪽에 있는 문자를 삭제함 (커서가 문장의 맨 앞이면 무시됨)
//	삭제로 인해 커서는 한 칸 왼쪽으로 이동한 것처럼 나타나지만, 실제로 커서의 오른쪽에 있던 문자는 그대로임
//P $	$라는 문자를 커서 왼쪽에 추가함

/*
abcd
3
P x
L
P y
https://www.acmicpc.net/problem/1406
*/

class TextEditor
{	
	static public void main(String args[]) throws Exception
	{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;		
		s = br.readLine();
		int c = Integer.parseInt(br.readLine());
		
		int length = s.length();
		link ll  = new link();
		
		for (int i = 0 ; i < length  ; i++) { 
			 ll.add(s.charAt(i));
		}
		
		for ( int i = 0 ; i < c ; i++) {
			
			String[] ab = br.readLine().split(" ");
			
			if (ab[0].equals("L")) {
				ll.left();
			}else if (ab[0].equals("D")) {
				ll.right();
			}else if (ab[0].equals("B")) {
				ll.delete();				
			}else if (ab[0].equals("P")) {
				ll.paste(ab[1]);
			}
		}
		
		ll.print();
		
	}
	
	static class node {
		node prev,next;
		String v;
		node(String v) {
			this.v = v;
		}
	}
	
	static class link{
		node head,tail;
		node cur;
		
		link(){
		}
		
		public void add(char charAt) {			
			String c = String.valueOf(charAt);
			node n = new node(String.valueOf(c));
			
			if (head == null) { 
				head = new node("0");
				tail = new node("1");
				head.next = n;
				n.prev = head;
				n.next = tail;	
				tail.prev = n;
				cur = tail;
				return;
			}
			
			paste(c);
			
		}

		public void print() {
			node h = head.next;
			
			while( h!= null) { 
				if (h == tail) break;
				System.out.print(h.v);
				h = h.next;
			}			
			System.out.println();
			
		}
		
		public void left() {
			if ( cur == head ) return;
			cur = cur.prev;
		}		

		public void paste(String string) {
			node n = new node(string);
			//head 일 경우 head 뒤에 
			if ( cur == head) { 
				node next = head.next;
				next.prev = n;
				n.next = next;
				n.prev = head;
				head.next =  n;
				return; 
			}
			//중간에 삽입			
			node pre = cur.prev;
			pre.next =n ; 
			n.prev = pre;			
			n.next = cur;
			cur.prev = n;
		}

		public void delete() {
			if (cur == head) return;
			if (cur.prev == head ) return;
			node n = cur.prev.prev;
			n.next = cur;
			cur.prev = n;
		}

		public void right() {
			if (cur == tail) return;
			cur = cur.next;
		}
		
		
	}
}
		