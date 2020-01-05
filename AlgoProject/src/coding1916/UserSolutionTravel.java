import java.util.HashMap;


class UserSolution3
{

    int USERC = 0 ;
    int AREAC = 0 ;

    product product[] = new product[100007]; //?
    
    class user {
        int fric = 0 ;
        int[] fri = new int[21];
        void addf(int uid) { 
            fri[fric++] = uid;
        }
        
        int[] area = new int[11];
        void addArea(int areaid) { 
            area[areaid] += 1;
        }
    }
    
    class area {
        //productlist 가 있다.
    	prioirtyqueue pq;
        int pc; 
        area() { 
            pq = new prioirtyqueue();
        }
        
        void add(product p) { 
            pq.push(p);
        }
        public product getTop() { 
        	return pq.getTop();
        }
        public product getTopProductID() {
        	product p = pq.Peek();
        	while(p!= null && p.reserve == true) { 
        		p = pq.pop();
        	}
        	if (p == null) return null; 
        	
        	return pq.Peek();
        }        
    }   
    
    //AREA별 Product LIST 관리 
    class prioirtyqueue { 
        
        product[] pkgs;
        int heapSize;
        
        prioirtyqueue() {
        	heapSize = 0;
        	pkgs = new product[40001];
        }               
        public product getTop(){
        	if (heapSize <= 0) {
        		return null;
        	}
        	return pkgs[0];
        }
        
        public product Peek()
        {
            return pkgs[0];
        }
        
        public boolean isQueueEmpty()
        {
            return this.heapSize == 0;
        }
        
        public product pop() {
    		if (heapSize <= 0)
    		{
    			return null;
    		}

    		product value = pkgs[0];
    		heapSize = heapSize - 1;

    		pkgs[0] = pkgs[heapSize];

    		int current = 0;
    		while (current < heapSize && current * 2 + 1 < heapSize)
    		{
    			int child;
    			if (current * 2 + 2 >= heapSize)
    			{
    				child = current * 2 + 1;
    			}
    			else
    			{
    				child = compare(pkgs[current * 2 + 1] , pkgs[current * 2 + 2]) ? current * 2 + 1 : current * 2 + 2;
    			}

    			if (compare(pkgs[current] , pkgs[child]))
    			{
    				break;
    			}

    			product temp = pkgs[current];
    			pkgs[current] = pkgs[child];
    			pkgs[child] = temp;

    			current = child;
    		}
    		return value;			
			
		}

		public void push(product p) {
			if (heapSize + 1 > 40001)
			{
				return;
			}

			pkgs[heapSize] = p;

			int current = heapSize;
			while (current > 0 && compare(pkgs[current] , pkgs[(current - 1) / 2])) 
			{
				product temp = pkgs[(current - 1) / 2];
				pkgs[(current - 1) / 2] = pkgs[current];
				pkgs[current] = temp;
				current = (current - 1) / 2;
			}
			heapSize = heapSize + 1;
			
		}


		boolean compare(product start, product p) {
            if (start.price < p.price ) return true; 
            if (start.price == p.price ) { 
                if ( start.pid < p.pid ) { 
                    return true;
                }
            }
            return false;
        }

        void print() { 
           
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
        
        productHash = new HashMap<>();
        
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
    
    HashMap<Integer,product> productHash;
    
    //package list 에 넣어둔다  4만 이하 (여기에 힌트) 
    public void add(int pid, int area, int price)
    {
        //System.out.println("add  p : " + pid + "\ta: " +  area + "\tprice: " + price ); 
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
        //System.out.println("reserve = " + uid +" , "  +  pid );
        //product list에서 빼내고
        product p = productHash.get(pid);       
        p.reserve = true;       
        
        //어따 쓰는물건인고
        users[uid].addArea( p.area );
    }
    
    public int recommend(int uid)
    {
        System.out.println("recommend =  " + uid ); 
        // uid와 친구들이 예약한 상품 중 가장많이 예약한 지역
        sort[] sortedArea = getAreaSort(uid);                
        
        boolean isExist = false; 
        // 해당 area에 상품이 존재하는지 check
        if ( sortedArea[0].c > 0) { 
            isExist = true;
        }
        
        if (isExist) {            
            for (int i = 0; i < sortedArea.length; i++) {
                // 가장 싼 상품의 id 를 던진다
                product top = areas[sortedArea[i].area].getTopProductID();
                if ( top != null) {
                    return top.pid;
                }           
            } 
        } else {
            int minprice = 1000000000;
            int minpid = 1000000000;
            for (int j = 1; j < AREAC; j++) {                
                product p = areas[j].getTopProductID();
                if (p != null) {
                    if (p.price < minprice ) {
                        minprice = p.price;
                        minpid = p.pid;
                    }else if (p.price == minprice ) {
                        if (p.pid < minpid) { 
                            minpid = p.pid;
                            minprice = p.price;
                        }
                    }
                }
            }
            //System.out.println("pid = " + minpid + " price = " + minprice);
            return minpid;
        }

        return -1;
        
    }
    
    class sort{ 
        int area = -1;
        int c = 0 ; 
        sort(int i) {
            area = i;
        }
    }    
    
    sort[] sorts;
    
    private sort[] getAreaSort(int uid ) {
        sorts = new sort[AREAC];
        //sort 된 area 로 담기위한 초기화 
        for (int i = 0 ; i < AREAC ; i++) { 
            sorts[i] = new sort(i);
        }
        
        for (int i = 0 ;  i < users[uid].fric ;i++) {
            int fid = users[uid].fri[i];
            for (int j = 0 ; j < AREAC ; j++) { 
                sorts[j].c +=  users[fid].area[j]; 
            }
        }
        
        //bubble
        //quickSort(0,AREAC-1);
        
        for (int i = 0 ; i < AREAC ; i++) {
            for (int j = 0 ; j < AREAC ; j++) {
                if ( compare( sorts[i] , sorts[j] )) {
                    sort temp = sorts[i] ;
                    sorts[i] = sorts[j];
                    sorts[j] = temp;
                }
            }
        }
        
        
        
        //debugging
        for (int j = 0 ; j < AREAC ; j++) { 
            System.out.println( sorts[j].area +  "번째 지역:  " +  sorts[j].c ); 
        } 
          
        
        return sorts;
       
    }
    
    boolean compare(sort a, sort b) { 
        if ( a.c > b.c ) { 
            return true;
        }else if ( a.c == b.c ) { 
            product p = areas[ a.area ].getTop();
            product p1 = areas[ b.area ].getTop();
            if (p == null || p1 == null) return false;
            if( p.price == p1.price) { 
                if ( p.pid < p1.pid){ 
                    return true;
                }
            }else if (p.price < p1.price) { 
                return true;
            }
        }
        
        return false; 
    } 
}
