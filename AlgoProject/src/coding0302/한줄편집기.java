package coding0302;

import java.io.BufferedReader;
import java.io.InputStreamReader;


class ����������
{
	
	static node head = null;
	static node tail = null;
	static node cursor = null;
	
	
	static class node {
		char v;
		node next;
		node prev;
		public node(char c) {
			v = c;
		}
	}
	
	public static void main(String args[]) throws Exception
	{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;	
		link l = new link();
		
		s = br.readLine();
		int length = s.length();
		
		for (int i = 0 ; i < length  ; i++) { 
			 l.add(s.charAt(i));
		}		
		
		int c = Integer.parseInt(br.readLine());
		
		for ( int i = 0 ; i < c ; i++) {
			
			String[] ab = br.readLine().split(" ");

//			L	Ŀ���� �������� �� ĭ �ű� (Ŀ���� ������ �� ���̸� ���õ�)
//			D	Ŀ���� ���������� �� ĭ �ű� (Ŀ���� ������ �� ���̸� ���õ�)
//			B	Ŀ�� ���ʿ� �ִ� ���ڸ� ������ (Ŀ���� ������ �� ���̸� ���õ�)
//				������ ���� Ŀ���� �� ĭ �������� �̵��� ��ó�� ��Ÿ������, ������ Ŀ���� �����ʿ� �ִ� ���ڴ� �״����
//			P $	$��� ���ڸ� Ŀ�� ���ʿ� �߰���
			//System.out.print(ab[0] + " : ");
			if (ab[0].equalsIgnoreCase("L")){
				if (cursor.prev != null){ 
					cursor = cursor.prev;
				}

			}else if (ab[0].equalsIgnoreCase("D")){
				
				if (cursor != tail) {
					cursor = cursor.next;
				}
				
			}else if (ab[0].equalsIgnoreCase("B")){
				//head ���� check 
				if (cursor.prev == null) {
					cursor = head;
					head = cursor;
				}
				//�ϳ��ۿ� ������� (head��� �Ǹ����) (head�� ����/ tail �� �����ϰ� 
				else if ( cursor.prev == head ){
					cursor.prev = null;	
					head = cursor;					
					cursor = head;
				//head /tail �ƴ� 1���̻� �����Ұ�� (ex 1(head) /2/Tail) )   
				}else{
					node pe = cursor.prev.prev; 
					pe.next = cursor; 
					cursor.prev = pe;
				}
					
			}else if (ab[0].equalsIgnoreCase("P")){
					String moveC = ab[1];
					char t = moveC.charAt(0);
					node newC = new node(t);
					
					if (cursor == head) { 
						newC.next = cursor; 
						cursor.prev = newC; 
						head = newC;
						cursor = newC;
					}else if (cursor == tail) { 
						node pe = cursor.prev ;  
						pe.next = newC; 
						newC.prev = pe;
						
						newC.next = cursor; 
						cursor.prev = newC;
						//Ŀ���� �״�� tail �����ϹǷ� �״��  
					}
					//
					else{						
						node pe = cursor.prev ;  
						pe.next = newC; 
						newC.prev = pe;
						
						newC.next = cursor; 
						cursor.prev = newC; 
						//cursor = newC;
					}
			}
			//printInfo();
		}
			
		
		printInfo();
	
	}
	static class link{
	
		void add(char c){
			node n = new node(c);
			if (head == null) { 
				head = n ; 
				tail = new node(',');
				head.next = tail;
				tail.prev = head;
				cursor = tail; //
				return;
			}
			
			node t = tail.prev ; //������ ���� �𸣴�. 
			t.next = n ; 
			n.prev = t;
			
			n.next = tail;
			tail.prev = n;
			
			cursor = tail; //
		}
	}

	public static void printInfo(){
		node t = head;
	
		while (t != tail){
			System.out.print(t.v);
			t=t.next;
		}
		System.out.println();
		//System.out.println("cursor : " + cursor.v);
		//System.out.println("-------");
	}

}