class UserSolution {
	
	
	int MAX = 100001;
	timeregion[] times = new timeregion[1001]; 
	postregion[] posts = new postregion[1001]; 
	user[] users = new user[1001];

	class timeregion {
		time head,tail;
		timeregion(){ 
			time t= new time();
			t.timestamp = -1;
			head = t;
			tail = t;
		}
		void addtime(time t) {
			tail.next = t;
			t.prev = tail;
			tail = t;
		}
		void remove(int timestamp, post p) {
			// TODO Auto-generated method stub
			time h = head.next;
			while(h!=null) { 
				if ( h.timestamp == timestamp ){
					h.p = null;
					return;
				}
				h = h.next;
			}
		}
		void addinsertsort(post p) {
			int timestamp = p.time;
			
			time s = head.next;
			time last = head;
			
			//신규타임
			time t = new time();
			t.timestamp = p.time; 
			t.p = p; //동일time에 p 가 없겠지.
			
			while(s!=null){ 
				if (s.timestamp > p.time) {
					time pre = s.prev;
					pre.next = t;
					t.prev = pre;
					t.next = s;
					s.prev = t;
					return;
				}
				s = s.next;
			}
			last.next = t;
			t.prev = last;
			tail = t;
		}
		public void find(int uID, int timestamp) {
			time h = tail;
			while(h!=null) {
				//클경우, 그 이전 time stamp 값 담기 
				if (h.timestamp < timestamp && h.timestamp != -1){
					if (h.p != null){ 
						System.out.println("담아야한다 : " + h.p.pid + "," + h.p.time );		
					}
				}
				h = h.prev;
			}
			
		}
	}
	class postregion {
		post head,tail;
		void addpost(post p) {
			if (head ==null) {
				head = p;
				tail = p;
			}else{
				tail.next = p;
				p.prev = tail;
				tail = p;
			}
		}
		public post find(int pID) {
			post h = head;
			while(h!=null) { 
				if ( h.pid == pID ){
					return h;
				}
				h = h.next;
			}
			return null;
		}
	}
	
	class time { 
		int timestamp;
		post p;
		time prev,next;
	}
	
	class post { 
		post prev,next;
		int uid;
		int like = 0;
		int time = 0;
		int pid;
	}
	
	class user {
		int time = 0;
		followuser head,tail;
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
		}
		for (int i = 0 ; i <= 1000 ; i++) { 
			times[i] = new timeregion();
			posts[i] = new postregion();
		}
		System.out.println("init = " + N);
	}

	public void follow(int uID1, int uID2, int timestamp)
	{
		followuser u = new followuser();
		u.uid = uID2;
		u.time= timestamp;		
		users[uID1].addfollow(u);
		System.out.println("follow = " + uID1 + "," + uID2 +"," + timestamp);
	}

	public void makePost(int uID, int pID, int timestamp)
	{
		System.out.println("makePost = " + uID + "," + pID +"," + timestamp);
		int idx = getregionIdx(pID) ; //영역으로
		post p = new post();
		p.time = timestamp; 
		p.uid = uID;
		p.pid = pID;
		
		posts[idx].addpost(p); 
		
		time t = new time();
		t.p = p; 
		t.timestamp = timestamp;
		
		idx = getregionIdx(timestamp);
		times[idx].addtime(t);
		
		
	}
	
	int getregionIdx(int t) {
		return t/100;
	}

	public void like(int pID, int timestamp)
	{
		//PID의 P 찾기
		int pidx = getregionIdx(pID);		
		//max 100개 서치
		post p = posts[pidx].find(pID);		
		//p.time = timestamp;
		
		//TIMESTAMP에서 ,해당 PID 찾아서 , 제거하기 
		int tidx = getregionIdx(p.time);
		times[tidx].remove(p.time,p);	
		
		
		//신규 TIMESTAMP에 헤딩 PID 넣기.
		tidx = getregionIdx(timestamp);
		times[tidx].addinsertsort(p);
		
		System.out.println("like = " + pID + "," +  timestamp);
	}

	public void getFeed(int uID, int timestamp, int pIDList[])
	{	
		System.out.println("getFeed = " + uID +  "," + timestamp + "," + pIDList[0]) ;
		if (timestamp == 2476) {
			System.out.println("");
		}
		//timestamp 를 찾아서 그 이전 10개 구하기잖아. 
		int tidx = getregionIdx(timestamp);
		
		
		for (int i = tidx ; i > 0 ; i-- ){ 
			times[i].find(uID, timestamp); 
		}
		
		/*
		for(int i = timestamp ; i < 0 ; i--) { 
			if ( times[timestamp].p == null ) {
				continue;
			}
			// uid 및 uid follow id 가  p 에 존재하는지 확인하고 , 존재하면, insert ll 에 넣는다.
			// timestamp 가 1000초 미만일경우만. 
			
		}
		*/
	}
}
