import java.util.ArrayList;

class UserSolution3 {
	
	int MAX = 100001;	

	class user {
		int fc;
		int[] follow;
		ArrayList<Integer> postlist; 
		user (int n) {
			fc = 0 ;
			follow = new int[n];
			postlist = new ArrayList<Integer>();
		}
		void addfollow(int uID2) {
			follow[fc++] = uID2;
		}
		void addpost(int pID) {
			postlist.add(pID);
		}
	}
	
	class post { 
		int uid;
		int pid;
		int timestamp;
		int like = 0;
	}
	
	user[] users = null;
	post[] posts = new post[MAX];
	
	public void init(int N)
	{
		users = new user[N+1];
		for (int i = 1; i <= N; i++) {
			users[i] = new user(N);
		}
		
		for (int i = 0; i < MAX; i++) {
			posts[i] = new post();
		}
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
		users[uID].addpost(pID);
	}


	public void like(int pID, int timestamp)
	{	
		posts[pID].like += 1;
	}

	
	ll result = null;
	public void getFeed(int uID, int timestamp, int pIDList[])
	{
		result = new ll();
		addcandi(users[uID],timestamp);
		
		for (int i = 0; i < users[uID].fc ; i++) {
			int fwid = users[uID].follow[i];
			addcandi(users[fwid],timestamp);
		}
		
		result h = result.head.next;
		int c = 0;
		while(h!=null) {
			if ( c == 10) break;
			pIDList[c++] = h.id;
			h = h.next; 
		}
	}
	
	void addcandi(user u ,int timestamp) { 
		int cnt = u.postlist.size();
		for (int i = 0; i < cnt; i++) {
			int postid = u.postlist.get(i);
			result.add(posts[postid],timestamp);
			result.print();
		}
	}
	
	class result{
		int id ; 
		result prev, next;
	}
	class ll { 
		result head,tail;
		int c  = 0 ;
		
		ll () {
			result r = new result();
			r.id = -1; 
			head = r; tail = r;
		}
		
		public void print() {
			// TODO Auto-generated method stub
			result h = head.next;
			while(h!=null) {
				System.out.print(h.id + ",");
				h = h.next; 
			}
			System.out.println();
		}

		void add(post p , int time) { 
			result r = new result();
			r.id = p.pid; 
			int cnt = 0 ; 
			
			result last = head; 
			result start = head.next;
			
			//while(start!=null && cnt < 10) {
			for (int i = 0 ; i < 9 ; i++ ) { //이게 말이 안됨 
				if (start == null) break; 
				//p를 바꾸려면 조건 처리 
				if (compare( p , posts[start.id] , time)){ 
					result pre = start.prev;
					pre.next = r ;
					r.prev = pre;
					
					r.next = start;
					start.prev = r;
					cnt++;
					return;
				}
				last = start;
				start = start.next;
				cnt++;
			}
			
			last.next = r ;
			r.prev = last; 
			tail = last;	
			c++;
		}

		//p 가 1000초 이내라면,
		boolean compare(post p, post t , int time) {
			// TODO Auto-generated method stub
			int ptime = time - p.timestamp;
			int ttime = time - t.timestamp;
			
			if (ptime < 1000) {
				if (ttime > 1000) return true;
				if (p.like > t.like) return true;
				else if (p.like == t.like ) { 
					if (p.timestamp >t.timestamp) {
						return true;
					}
				}
			}else if (ptime > 1000 && ttime > 1000){ 
				if (p.timestamp > t.timestamp) {
					return true;
				}
			}
			return false;
		}
		
	}
}
