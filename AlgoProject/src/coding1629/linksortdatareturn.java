package coding1629;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class linksortdatareturn {

	/*
1. void Init(int Map_size)  - init 함수 구현
2. void add숙소(int 숙소ID, int price, int 평점, int 위치 )

	 5. int search숙소(int 옵션, int checkin_day, int 숙박일수, int y, int x, int answer[5] )
   - 옵션이 0이면 (y,x)에 해당하는 지역만 검색
   - 옴션이 1이면 (y,x)를 모든 방향 1칸을 검색 ( 9칸 검색 ) 예를들어, (1,1)에 옵션 0이면 (0,0)~(2,2)까지 검색
   - 해당 지역의 숙소 중에서 예약이 가능한 숙소이며, 최저가이며, 가격이 같으면 평점이 높으며, 평점이 같으면 숙소ID가 높은순
   - 5개를 뽑아내서 return 5, 2개만 나왔으면 return 2
   - int answer[5]에다가는 숙소ID를 순서대로 삽입.
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
		//같은 위치 case 
		addhome(1,1000,5, 5,5);
		addhome(2,2000,5, 5,5);
		addhome(6,1000,2, 5,5);
		addhome(7,1000,2, 5,5);
		addhome(8,1000,2, 5,5);
		addhome(9,2000,9, 5,5);
		
		// 1 -> 8 -> 7 -> 6 - > 2 번 순으로 예상값이 나와야 한다.(그러나, 3개만 return 하기때문에 넘길필요없다.)
		addhomedbg(5,5);
		
		//좌표 우측 case 
		addhome(3,2000,5, 5,6);
		
		//좌표 북쪽 case 
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
	
	//9방향
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
			//리스트에 모두 담고 insert sort 가져오는 방법 
			ll homebubble = new ll();
			for (int i = 0; i < dy.length; i++) { //아무생각없이 2중 루프는 모냐.
				
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
			
			home h = homebubble.head.next; //head 가 null일수 없다 
			System.out.println("주변 탐색 inser sort 값 : ");
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
		// 이미 소트되어 들어가 있다.
		home h = map[y][x].head.next; //head 가 null일수 없다 
		
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
		//기본 
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
			
			//3개만 우선순위로 남겨놓으면 되므로, 3개 이상넘어갈경우 ,더 이상 검색할 필요없다.
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
			
			//예외 조건이 계속 추가되면 , 시험볼때 까먹는디.. 별로인디.
			//if ( c > exit ) return;
			
			if ( c <= exit) { 
				last.next = h;
				h.prev = last;
			}
			
		}
		
		//최저가이며, 가격이 같으면 평점이 높으며, 평점이 같으면 숙소ID가 높은순
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
