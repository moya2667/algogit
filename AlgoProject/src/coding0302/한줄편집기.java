package coding0302;

import java.io.BufferedReader;
import java.io.InputStreamReader;


class 한줄편집기
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

//			L	커서를 왼쪽으로 한 칸 옮김 (커서가 문장의 맨 앞이면 무시됨)
//			D	커서를 오른쪽으로 한 칸 옮김 (커서가 문장의 맨 뒤이면 무시됨)
//			B	커서 왼쪽에 있는 문자를 삭제함 (커서가 문장의 맨 앞이면 무시됨)
//				삭제로 인해 커서는 한 칸 왼쪽으로 이동한 것처럼 나타나지만, 실제로 커서의 오른쪽에 있던 문자는 그대로임
//			P $	$라는 문자를 커서 왼쪽에 추가함
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
				//head 이전 check 
				if (cursor.prev == null) {
					cursor = head;
					head = cursor;
				}
				//하나밖에 없을경우 (head라고 판명날경우) (head만 존재/ tail 은 존재하고 
				else if ( cursor.prev == head ){
					cursor.prev = null;	
					head = cursor;					
					cursor = head;
				//head /tail 아닌 1개이상 존재할경우 (ex 1(head) /2/Tail) )   
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
						//커서는 그대로 tail 유지하므로 그대로  
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
			
			node t = tail.prev ; //이전꺼 먼지 모르니. 
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