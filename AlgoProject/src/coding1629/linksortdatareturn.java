package coding1629;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class linksortdatareturn {

	/*
1. void Init(int Map_size)  - init �Լ� ����
2. void add����(int ����ID, int price, int ����, int ��ġ )

	 5. int search����(int �ɼ�, int checkin_day, int �����ϼ�, int y, int x, int answer[5] )
   - �ɼ��� 0�̸� (y,x)�� �ش��ϴ� ������ �˻�
   - �ȼ��� 1�̸� (y,x)�� ��� ���� 1ĭ�� �˻� ( 9ĭ �˻� ) �������, (1,1)�� �ɼ� 0�̸� (0,0)~(2,2)���� �˻�
   - �ش� ������ ���� �߿��� ������ ������ �����̸�, �������̸�, ������ ������ ������ ������, ������ ������ ����ID�� ������
   - 5���� �̾Ƴ��� return 5, 2���� �������� return 2
   - int answer[5]���ٰ��� ����ID�� ������� ����.
	 */
	class home { 
		home prev,next; 
		int id;
		int price;
		int score;
		int y;
		int x;
		home(){};
		home(int id,int price,int score,int y,int x) { 
			this.id = id;
			this.price = price;
			this.score= score;
			this.y = y;
			this.x = x;
		}
	}
	
	int MAX = 10;
	
	ll[][] map = null;
	void init() { 
		map = new ll[MAX][MAX];
		for (int y = 0; y < MAX ; y++) {
			for (int x = 0; x < MAX ; x++) {
				map[y][x] = new ll();
			}
		}
	}
	@Test
	public void test() {
		init();
		//���� ��ġ case 
		addhome(1,1000,5, 5,5);
		addhome(2,2000,5, 5,5);
		addhome(6,1000,2, 5,5);
		addhome(7,1000,2, 5,5);
		addhome(8,1000,2, 5,5);
		addhome(9,2000,9, 5,5);
		
		// 1 -> 8 -> 7 -> 6 - > 2 �� ������ ������ ���;� �Ѵ�.(�׷���, 3���� return �ϱ⶧���� �ѱ��ʿ����.)
		addhomedbg(5,5);
		
		//��ǥ ���� case 
		addhome(3,2000,5, 5,6);
		
		//��ǥ ���� case 
		addhome(4,1000,2, 4,5);
		
		int[] answer = new int[5];
		search(0,5,5,answer);
		print(answer);
		
		search(1,5,5,answer);
		
		print(answer);
	}
	
	void print(int[] answer) { 
		
		for (int i = 0; i < answer.length; i++) {
			System.out.print(answer[i] + " ");
		}
		System.out.println();
		
		//answer[cnt]
	}
	
	//9����
	int[] dy = {0,-1, -1 ,0 , 1 , 1 , 1 , 0 , -1} ; 
	int[] dx = {0, 0 , 1, 1 , 1 , 0 , -1, -1, -1} ;
	
	void search(int option , int y, int x , int[] answer) { 
		if (option == 0) { 
			home h = map[y][x].head.next;
			int cnt = 0 ; 
			while(h!=null) { 
				System.out.println("id = " + h.id + 
						"price = " + h.price + 
						"score = " + h.score);
				
				answer[cnt++] = h.id; 
				h= h.next; 
				if (cnt > 2) break;
			}
		}else if (option == 1) { 
			//����Ʈ�� ��� ��� insert sort �������� ��� 
			ll homebubble = new ll();
			for (int i = 0; i < dy.length; i++) { //�ƹ��������� 2�� ������ ���.
				
				int myY = y+ dy[i];
				int myX = x +dx[i];
				
			
				home h = map[myY][myX].head.next;
				int cnt = 0 ;
				while(h!=null) { 
					System.out.println("id = " + h.id + 
							"price = " + h.price + 
							"score = " + h.score);
					
					homebubble.addinsertsort(h, 5);
					h= h.next;
				}			
			}
			
			home h = homebubble.head.next; //head �� null�ϼ� ���� 
			System.out.println("�ֺ� Ž�� inser sort �� : ");
			int cnt  = 0 ; 
			while(h!=null && cnt < 5) { 
				System.out.println("id = " + h.id + 
						"price = " + h.price + 
						"score = " + h.score);
				
				answer[cnt] = h.id;
				h= h.next; 
				cnt++;
				
			}
			
		}
	}
	
	private void addhomedbg(int y, int x) {
		// �̹� ��Ʈ�Ǿ� �� �ִ�.
		home h = map[y][x].head.next; //head �� null�ϼ� ���� 
		
		while(h!=null) { 
			System.out.println("id = " + h.id + 
					"price = " + h.price + 
					"score = " + h.score);
			
			h= h.next; 
		}
		
		System.out.println("===========");
		
	}
	void addhome(int id , int price , int score , int y, int x) { 
		home h = new home(id,price,score,y,x);
		map[y][x].addinsertsort(h , 5);
	}
	
	class ll { 
		home head,tail;
		ll() { 
			home init = new home();
			init.id = 0;
			head = init ;
			tail = init ;
		}
		//�⺻ 
		void add(home h) { 
			if (head == null) { 
				head = h ;
				tail = h ; 
			}else{
				tail.next = h;
				h.prev =tail;
				tail = h;
			}
		}
		void addinsertsort(home h , int exit) { 
			
			home s = head.next;
			home last = head;
			
			//3���� �켱������ ���ܳ����� �ǹǷ�, 3�� �̻�Ѿ��� ,�� �̻� �˻��� �ʿ����.
			int c = 1 ;
			
			while(s!= null && c <= exit ) {
				c++;
				if (compare(s,h)) { 
					home pre = s.prev;
					pre.next = h ; 
					h.prev = pre; 
					
					h.next = s; 
					s.prev = h;
					return;
				}
				last = s;
				s= s.next; 
			}
			
			//���� ������ ��� �߰��Ǹ� , ���躼�� ��Դµ�.. �����ε�.
			//if ( c > exit ) return;
			
			if ( c <= exit) { 
				last.next = h;
				h.prev = last;
			}
			
		}
		
		//�������̸�, ������ ������ ������ ������, ������ ������ ����ID�� ������
		boolean compare(home s , home h) { 
			if (s.price > h.price) {
				return true;
			}
			if (s.price == h.price) { 
				if (s.score <h.score){
					return true;
				}else if (s.score == h.score) { 
					if (s.id < h.id) return true;
				}
			}
			return false;
		}
	}

}
