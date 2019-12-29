import java.util.HashMap;

class UserSolution
{

	int USERC = 0 ;
	int AREAC = 0 ;

	product product[] = new product[100007]; //?
	
	class user {
		int fric = 0 ;
		int[] fri = new int[21];
		void addf(int uid) { 
			fri[fric] = uid;
			fric++;
		}
		
		int[] area = new int[11]; 
		
		void addArea(int areaid) { 
			area[areaid] += 1; 
		}
		/*
		int reservePrc = 0; 
		int[] reservePr = new int[40001];  
		
		public void addreservePIdx(int pidx) {
			reservePr[reservePrc++] = pidx;
		}
		*/
	}
	
	class area {
		//productlist 가 있다.
		ll plist;
		int pc; 
		area() { 
			plist = new ll();
			pc = 0; 
		}
		
		void add(product p) { 
			plist.addinsert(p);
			pc++;
		}

		public int getproduct_c() {
			return pc;
		}

		public product getTopProductID() {
			if (plist.head.next != null) { 
				return plist.head.next;
			}
			return null;
		}
	}	
	
	//AREA별 Product LIST 관리 
	class ll { 
		product head,tail;
		
		ll() { 
			product h = new product(-1,-1,-1);
			product t = new product(-1,-1,-1);
			head = h ;
			tail = t; 
		}
		
		void addinsert(product p) { 
			product start = head.next; //아 이제 다 까먹었네.ㅜㅜ (product next?) -> area 별 next 라면.  
			product last = head;
			
			while(start!=null) { 
				if (compare(start ,p)) {
					product pre = start.prev;						
					pre.next = p ; 
					p.prev = pre;
					
					p.next = start; 
					start.prev = p;
					return;
				}
				
				last = start;
				start = start.next;
			}
			
			last.next =  p;
			p.prev = last;
			p.next = tail;
			tail.prev = p;
		}
		
		private boolean compare(product start, product p) {
			if (start.price > p.price ) return true; 
			if (start.price == p.price ) { 
				if ( start.pid > p.pid ) { 
					return true;
				}
			}
			return false;
		}

		void print() { 
			product h = head.next;
			
			while(h!=null) { 
				System.out.print( "["+ h.pid +"," + h.price +"]" ) ; 
				h = h.next;
			}
			System.out.println();
		}
	}
	
	
	class product {
		product prev,next;
		int pid ; 
		int area;
		int price;
		boolean reserve = false;;
		
		public product(int pid2, int area2, int price2) {
			pid = pid2;
			area = area2;
			price = price2;
		}
	}
	
	user[] users;
	area[] areas;
	public void init(int N, int M)
	{
		//user 초기화 
		USERC = N+1;
		AREAC = M+1;
		users = new user[USERC];
		areas = new area[AREAC];
		
		for (int i = 0 ; i < USERC ; i++) { 
			users[i] = new user();
			//자기자신 id 등록 (나중계산쉽게하기위해)
			users[i].addf(i);
		}
		
		for (int i = 0 ; i < AREAC ; i++) { 
			areas[i] = new area();
		}
		
		/*
		for (int i = 0 ; i < 100007 ; i++) {
			product[i] = new product();
		}
		*/
		
	}
	
	public void befriend(int uid1, int uid2)
	{		
		users[uid1].addf(uid2);
		users[uid2].addf(uid1);
	}
	
	HashMap<Integer,product> productHash = new HashMap<>();
	HashMap<Integer,product> reservelist = new HashMap<>();
	
	
	//package list 에 넣어둔다  4만 이하 (여기에 힌트) 
	public void add(int pid, int area, int price)
	{
		//package 
		product p = new product(pid, area, price);
		 
		//area 에 등록된 p (우선순위로 넣는다.) 
		areas[area].add(p);
		
		//product hash 
		productHash.put(pid, p);
	}
	
	//pa
	public void reserve(int uid, int pid)
	{
		System.out.println("reserve = " + uid +" , "  +  pid );
		//product list에서 빼내고
		product p = productHash.get(pid);		
		p.reserve = true;		
		
		//어따 쓰는물건인고
		users[uid].addArea( p.area );
	}
	
	public int recommend(int uid)
	{
		//uid와 친구들이 예약한 상품 중 가장많이 예약한 지역
		sort[] sortedArea = getAreaSort(uid);
		
		for (int i = 0 ; i < AREAC ; i++) {
			int areaTopIdx = sortedArea[i].c;
			//해당 area 에 상품이 존재하는지 check 
			int c =  areas[areaTopIdx].getproduct_c();		
			if (c > 0) { 
				//가장 싼 상품의 id 를 던진다
				product top = areas[areaTopIdx].getTopProductID();
				return top.pid;
				
			//c == 0 일경우 (예약된 상품이 없을경우)
			}else {
				for (int j = 1 ; j < AREAC ; j++) { 
					product p = areas[j].getTopProductID();
					
					if (p != null ) { 
						System.out.println("area = " + j + " pid = " + p.pid + " price : " + p.price );	
						
					}
				}
				
			}
		}
		return -1;
		
	}
	
	class sort{ 
		int c = 0 ; 
	}
	
	
	
	private sort[] getAreaSort(int uid ) {
		sort[] sorts = new sort[AREAC];
		//sort 된 area 로 담기위한 초기화 
		for (int i = 0 ; i < AREAC ; i++) { 
			sorts[i] = new sort();
		}
		
		for (int i = 0 ;  i < users[uid].fric ;i++) {
			int fid = users[uid].fri[i];
			for (int j = 0 ; j < AREAC ; j++) { 
				sorts[j].c +=  users[fid].area[j]; 
			}
		}
		
		for (int j = 0 ; j < AREAC ; j++) { 
			System.out.println( j +  "번째 지역:  " +  sorts[j].c ); 
		}
		
		/*
		//uid와 친구들이 예약한 상품 중 가장 많이 예약한 지역
		for (int i = 0 ;  i < users[uid].fric ;i++) {
			int fid = users[uid].fri[i];
			//해당 user 가 예약한 product 
			int pc = users[fid].;
			for (int j = 0 ; j < pc ; j++) { 
				int pidx = users[fid].reservePr[j];				
				product p = product[pidx];			
				
				//area[0] -> 5
				//area[1] -> 5
				//area[2] -> 1
				sorts[p.area].c = sorts[p.area].c+1;   
			}
		}
		*/
		
		//sort...
		
		
		return sorts;
		//return 0;
	}
	
}

