package coding1425;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;



//L	Ŀ���� �������� �� ĭ �ű� (Ŀ���� ������ �� ���̸� ���õ�)
//D	Ŀ���� ���������� �� ĭ �ű� (Ŀ���� ������ �� ���̸� ���õ�)
//B	Ŀ�� ���ʿ� �ִ� ���ڸ� ������ (Ŀ���� ������ �� ���̸� ���õ�)
//	������ ���� Ŀ���� �� ĭ �������� �̵��� ��ó�� ��Ÿ������, ������ Ŀ���� �����ʿ� �ִ� ���ڴ� �״����
//P $	$��� ���ڸ� Ŀ�� ���ʿ� �߰���

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
			//head �� ��� head �ڿ� 
			if ( cur == head) { 
				node next = head.next;
				next.prev = n;
				n.next = next;
				n.prev = head;
				head.next =  n;
				return; 
			}
			//�߰��� ����			
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
		