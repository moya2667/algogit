class UserSolution7 {
    
    int MAX = 100001;   

    class user {
        int fc;
        int[] follow;
        //ArrayList<Integer> postlist;
        postlist postlist = new postlist();
        user(int n) {
            fc = 0 ;
            follow = new int[n];
            postlist = new postlist();
        }
        void addfollow(int uID2) {
            follow[fc++] = uID2;
        }        
        void addpost(post p) {
            postlist.add(p);
        }
    }
    
    class postlist {
        post head,tail;
        void add(post p) {
            if (head == null) {
                head = p;    
                tail = p;
            }else{
                tail.next = p;
                p.prev = tail;
                tail = p;
            }    
        }
    }
    class post { 
        post prev,next;
        int uid;
        int pid;
        int timestamp;
        int like = 0;
    }
    
    user[] users = null;
    post[] posts ;    
    int userN;
    public void init(int N)
    {
        userN =  N ;
        users = new user[N+1];
        posts = new post[MAX+1];
        for (int i = 0; i < N+1; i++) {
            //post post = posts[i];
            users[i]= new user(userN);            
        }
        //init        
        for (int i = 0; i < MAX+1; i++) {
            //post post = posts[i];
            posts[i] = new post();
        }
        
        heap = new post[MAX];
        heapSize = 0;
    }

    public void follow(int uID1, int uID2, int timestamp)
    {   
        users[uID1].addfollow(uID2);
    }

    public void makePost(int uID, int pID, int timestamp)
    {   
        posts[pID].uid = uID;
        posts[pID].pid = pID;
        posts[pID].timestamp = timestamp;
        users[uID].addpost(posts[pID]);
    }

    public void like(int pID, int timestamp)
    {   
        posts[pID].like += 1;
    }    
    
    public void getFeed(int uID, int timestamp, int pIDList[])
    {   
        heapSize = 0;
        
        addcandi(users[uID],timestamp);
        
        for (int i = 0; i < users[uID].fc ; i++) {
            int fwid = users[uID].follow[i];
            addcandi(users[fwid],timestamp);
        }
      
        //heapPrint(heap,heapSize);
        
        int index = 0;
        while( index < 10) {
            int value = heapPop(timestamp);
            if( value == -1) {
                break;
            }
            pIDList[index++] = value;
        }
        
    }
    
    void addcandi(user u ,int timestamp) {
        post h = u.postlist.head;
        while(h != null){                
            //heapPush(h.pid,timestamp);
            heapPush(h,timestamp);
            h = h.next;
        }
    }
    
    post heap[] = new post[MAX];
    int heapSize = 0;
    
    
    void heapPrint(int[] heap, int heap_size)
    {
        for (int i = 0; i < heap_size; i++)
        {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
    
    void heapPush(post value, int time)
    {
        if (heapSize + 1 > MAX)
        {
            return;
        }
 
        heap[heapSize] = value;
 
        int current = heapSize;
        //while (current > 0 && heap[current] < heap[(current - 1) / 2]) 
        //while( current > 0 && isValid(posts[heap[current]], posts[heap[(current - 1) / 2]] , time))
        while( current > 0 && isValid(heap[current], heap[(current - 1) / 2] , time))
        {
            post temp = heap[(current - 1) / 2];
            heap[(current - 1) / 2] = heap[current];
            heap[current] = temp;
            current = (current - 1) / 2;
        }
 
        heapSize = heapSize + 1;
    }
    int heapPop(int time)
    {
        if (heapSize <= 0)
        {
            return -1;
        }
 
        post value = heap[0];
        heapSize = heapSize - 1;
 
        heap[0] = heap[heapSize];
 
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
                //child = heap[current * 2 + 1] < heap[current * 2 + 2] ? current * 2 + 1 : current * 2 + 2;
                child = isValid(heap[current * 2 + 1], heap[current * 2 + 2],time) ? current * 2 + 1 : current * 2 + 2;
            }
 
            //if (heap[current] < heap[child])
            if( isValid( heap[current], heap[child],time ))
            {
                break;
            }
 
            post temp = heap[current];
            heap[current] = heap[child];
            heap[child] = temp;
 
            current = child;
        }
        return value.pid;
    }   
    
    int curTimeStamp;
    
    //p 가 1000초 이내라면,
    boolean isValid(post p, post t , int time) {
        // TODO Auto-generated method stub
        int ptime = time - p.timestamp;
        int ttime = time - t.timestamp;
        
        if (ptime <= 1000 && ttime <= 1000) {
            if (p.like > t.like) return true;
            else if (p.like == t.like ) { 
                if (p.timestamp >t.timestamp) {
                    return true;
                }
            }
        }
        else if (ptime > 1000 && ttime > 1000){
            if (p.timestamp > t.timestamp) {
                return true;
            }
        }
        else if(ptime <= 1000 && ttime > 1000) {
            if (p.timestamp >t.timestamp) {
                return true;
            }
        }
        return false;
    }
     
}
