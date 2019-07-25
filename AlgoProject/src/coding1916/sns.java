
class UserSolution2 {
	
	
	int MAX = 100001;
	time[] times = new time[MAX]; 
	post[] posts = new post[MAX];
	
	user[] users = new user[1001];
	
	class time { 
		int timestamp;
		post p;
		time prev,next;
	}
	
	class post {
		post dprev,dnext;
		post prev,next;
		int uid;
		int like = 0;
		int time = 0;
		int pid;
	}
	
	class user {
		int time = 0;
		followuser head,tail;
		post phead,ptail;
		int uid;
		
		void addfollow(followuser u) {
			if (head ==null) {
				head = u;
				tail = u;
			}else{
				tail.next = u;
				u.prev = tail;
				tail = u;
			}
		}

		public void addpost(post p) {
			if (phead ==null) {
				phead = p;
				ptail = p;
			}else{
				ptail.next = p;
				p.prev = ptail;
				ptail = p;
			}
			
		}
	}
	
	class followuser{
		followuser prev,next;
		int uid;
		int time;		
	}

	
	public void init(int N)
	{
		for (int i = 1 ; i <= N ; i++) { 
			users[i] = new user();
			users[i].uid = i; 
		}
		
		for (int i = 0 ; i < MAX ; i++) { 
			times[i] = new time();
			posts[i] = new post();
		}
		System.out.println("init = " + N);
	}

	public void follow(int uID1, int uID2, int timestamp)
	{	
		followuser f = new followuser(); 
		f.uid = uID2; 
		f.time = timestamp; 
		
		users[uID1].addfollow(f);
		System.out.println("follow = " + uID1 + "," + uID2 +"," + timestamp);
	}

	public void makePost(int uID, int pID, int timestamp)
	{
		System.out.println("makePost = " + uID + "," + pID +"," + timestamp);
		
		posts[pID].time = timestamp;
		posts[pID].uid = uID;
		posts[pID].pid = pID;
		
		users[uID].addpost(posts[pID]); 
	}


	public void like(int pID, int timestamp)
	{
		posts[pID].like += 1; 
		posts[pID].time = timestamp;
		System.out.println("like = " + pID + "," +  timestamp);
	}

	public void getFeed(int uID, int timestamp, int pIDList[])
	{	
		System.out.println("getFeed = " + uID +  "," + timestamp + "," + pIDList[0]) ;

		//uID
		post t = users[uID].ptail;
		while(t!=null) {
			if ( t.time < timestamp ) { 
				addCandi(t);
			}
			t = t.prev;
		}
		
		followuser f = users[uID].head;
		
		while(f!=null) {
			post p = users[f.uid].ptail ;
			while(p != null) {
				if ( p.time < timestamp ) { 
					addCandi(t);
				}
				p = p.prev;
			}
			f = f.next; 
		}
		
		return;
	}
	
	void addCandi(post p) { 
		
	}
	
	class ll {
		post head,tail;
		
		ll(){
			post p = new post();
			p.pid = -1; 
			head = p;
			tail = p;
		}
		//우선순위 비교 t 가 timestamp 가 1000초 이내라면,
		void add(post t , int timestamp) { 
			post last = head; 
			post s = head.dnext; 
			while(s!=null) { 
				if (compare(t , s , timestamp)) { 
					post pre = s.dprev;
					pre.dnext = t; 
					t.dprev = pre; 
					t.dnext = s;
					s.dprev = t;
					return;
				}
				last = s; 
				s= s.dnext; 
			}
						
			last.dnext = t;
			t.dprev = last;
			tail = t;
		}
		
		void print() {
			post h = head ;
			while(h!=null) { 
				System.out.print( h.pid + " , " );
				h= h.dnext;
			}
			System.out.println();
		}
		
		boolean compare(post t, post s , int time){
			int diff = time - t.time;
			if (diff > 1000) { 
				if ( t.time > s.time) {
					return true;
				}
			}else{ 
				if ( t.like > s.like) return true;
				else if ( t.like == s.like){
					if (t.time > s.time) return true;
				}
			}
			return false;
		}
	}
	
	
}
