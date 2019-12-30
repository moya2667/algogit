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
            fri[fric++] = uid;
        }
        
        int[] area = new int[11];
        void addArea(int areaid) { 
            area[areaid] += 1;
        }
    }
    
    class area {
        //productlist 가 있다.
        ll plist;
        int pc; 
        area() { 
            plist = new ll();
        }
        
        void add(product p) { 
            plist.addinsert(p);
        }
        public product getTopProductID() {
            if (plist.head.next != null) { 
                return plist.head.next;
            }
            return null;
        }

        public boolean isExist() {
            if (plist.head.next != null) { 
                return true;
            }
            return false;
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
            product start = head.next;   
            product last = head;
            
            while(start!=null&&start!=tail) { 
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
        
        boolean compare(product start, product p) {
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
            while(h!=tail) { 
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
        System.out.println("recommend =  " + uid ); 
        // uid와 친구들이 예약한 상품 중 가장많이 예약한 지역
        sort[] sortedArea = getAreaSort(uid);                
        
        // 해당 area에 상품이 존재하는지 check
        boolean isExist = areas[sortedArea[0].area].isExist();
        
        if (isExist) {            
            for (int i = 0; i < sortedArea.length; i++) {
                // 가장 싼 상품의 id 를 던진다
                product top = areas[sortedArea[i].area].getTopProductID();
                while(true) {
                    if (top.reserve == true) {
                        top = top.next;                        
                    }else {                    
                        break;
                    }
                }
                if (top.pid != -1) {
                    return top.pid;
                }           
            } 
        } else {
            int minprice = 10000;
            int minpid = 10000;
            for (int j = 1; j < AREAC; j++) {
                product p = areas[j].getTopProductID();
                if (p != null) {
                    if (p.price < minprice && p.pid < minpid) {
                        minprice = p.price;
                        minpid = p.pid;
                    }
                }
            }
            System.out.println("pid = " + minpid + " price = " + minprice);
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
        quickSort(0,AREAC-1);
        /*
        for (int i = 0 ; i < AREAC ; i++) {
            for (int j = 0 ; j < AREAC ; j++) {
                if ( sorts[i].c > sorts[j].c ) {
                    sort temp = sorts[i] ;
                    sorts[i] = sorts[j];
                    sorts[j] = temp;
                }
            }
        }
        */
        
        //debugging
        for (int j = 0 ; j < AREAC ; j++) { 
            System.out.println( sorts[j].area +  "번째 지역:  " +  sorts[j].c ); 
        }   
        
        return sorts;
       
    }
    
    void quickSort(int first, int last)
    {
        sort temp;       
        if (first < last)
        {
            int pivot = first;
            int i = first;
            int j = last;

            while (i < j)
            {   
                while (sorts[i].c >= sorts[pivot].c && i < last)
                {
                    i++;
                }
                while (sorts[j].c < sorts[pivot].c)
                {
                    j--;
                }
                if (i < j)
                {
                    temp = sorts[i];
                    sorts[i] = sorts[j];
                    sorts[j] = temp;
                }
            }

            temp = sorts[pivot];
            sorts[pivot] = sorts[j];
            sorts[j] = temp;

            quickSort(first, j - 1);
            quickSort(j + 1, last);
        }
    }    
    
}
