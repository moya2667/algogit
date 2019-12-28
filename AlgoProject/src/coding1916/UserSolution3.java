
public class UserSolution3 {
	Hash[][][] myhash; 
	char[] mystr;
	int MAXN;
	
	void addhash(int pos , char[] str){ 
		int f = str[pos]-'a';
		int s = str[pos+1 ]-'a';
		int t = str[pos+2 ]-'a';
		
		if ( myhash[f][s][t] == null) { 
			myhash[f][s][t] = new Hash(pos);
		}else{
			myhash[f][s][t].getidxlist().addsort(pos);
		}
	}
	Hash gethash(char k1 ,char k2,char k3){
		int f = k1 -'a';
		int s = k2 -'a';
		int t = k3 -'a';
		return myhash[f][s][t];
	}

	Position[] positions ; 
	void init(int N, char init_string[]) {

		mystr = new char[N]; 
		positions = new Position[N];
		MAXN = N;

		myhash = new Hash[26][26][26]; // key

		for (int i = 0; i < MAXN - 2; i++) {
			addhash(i, init_string);
			mystr[i] = init_string[i];
		}

		mystr[MAXN - 2] = init_string[MAXN - 2];
		mystr[MAXN - 1] = init_string[MAXN - 1];
	}
	
	
	int mstrcmp(char[] a, char[] b) {
		int i;
		for (i = 0; a[i] != '\0'; i++) {
			if (a[i] != b[i])
				return a[i] - b[i];
		}
		return a[i] - b[i];
	}	

	/*
	 * string_A : 규칙을 이루는 문자열에서 찾아 변환시킬 문자열이다. string_B : string_A 를 대체할 문자열
	 * (string_A, string_B) 의 각 길이는 3이다.
	 */
	int change(char string_A[], char string_B[]) {
		int ret = 0 ;
		//1. string A의  key에 대한 idx 정보 검색 ,
		//2. 검색된 idx 기반으로, -2~2까지 영향을 끼치는 key 를 제거 
		//3. mystr 정보 업데이트
		//4. mystr 기반으로, -2~2 pos위치에 있는 key 를 추가 
		Hash strAKey = gethash(string_A[0],string_A[1],string_A[2]);		
		if (strAKey == null) return 0;
		
		//1. string A의  key에 대한 idx 정보 검색 ,
		ll strAll = strAKey.getidxlist();		
		int prev = -1; //이전 position 값
		Position s = strAll.head.next;
		
		while(s.pos != -1) {			
			int pos = s.pos;
			if (prev != -1) {
				if ( pos - prev <= 2 ) {
					s = s.next;
					continue;
				}
			}
			
			//2. 검색된 idx 기반으로, -2~2까지 영향을 끼치는 key 를 제거 	
			s = s.next; //미리 넘겨놓음
			deleteHash(pos);
			
			prev = pos;
			
			//3. mystr 정보 업데이트
			mystr[pos] = string_B[0];
			mystr[pos+1] = string_B[1];
			mystr[pos+2] = string_B[2];
			
			//4. mystr 기준으로, -2~2 pos위치에 있는 key 를 추가 
			updateHash(pos);
			ret++;
		}
		
		return ret;
	}
	
	void updateHash(int pos) {
		int min = pos-2;
		int max = pos+2;
		for (int i = min ; i < max+1 ; i++ ) {
			if (i < 0 || i >= MAXN-2 ){
				continue;
			}
			addhash(i , mystr);
		}
		
	}
	void deleteHash(int pos) {
		//2. 검색된 idx 기반으로, mystr의 -2~2까지 영향을 끼치는 key 를 제거
		int min = pos-2;
		int max = pos+2;
		for (int i = min; i < max+1; i++) {
			if (i < 0 || i >= MAXN-2 ) continue;
			/* ll 통해 , 접근하는 방법 
			Hash h = gethash(mystr[i], mystr[i+1], mystr[i+2]);
			Position f = h.myidxlist.find(i);
			h.myidxlist.delete(positions[i]);
			if (f == null) return;
			h.myidxlist.delete(f);
			*/		
			
			// ll아닌 바로 접근하는방법 
			deleteHash(positions[i]);
		}
	}
	
	void deleteHash(Position p) {
		Position pre = p.prev;
		Position ne = p.next;
		pre.next = ne;
		ne.prev = pre;
	}
	
	void result(char ret[]) {
		for (int i = 0; i < MAXN; i++) {
			ret[i] = mystr[i];
		}		
	}
	
	class Hash {
		ll myidxlist;
		int pos = -1;
		public Hash(int pos) {
			myidxlist = new ll();
			myidxlist.addsort(pos);
			this.pos = pos;
		};
		
		ll getidxlist() {
			return myidxlist;
		}
		
		int getCnt() { 
			return myidxlist.cnt;
		}
	}
	
	class Position {
		Position next,prev;
		int pos= -1;
		Position(int idx) {
			pos= idx;
		}
	}
	
	class ll { 
		Position head,tail;
		int cnt = 0;
		void addsort(int idx) {
			Position n = new Position(idx);
			
			positions[idx] = n; //이렇게 담아놓으면 0.3 opti
			
			if (head == null) {
				Position s = new Position(-1);
				Position t = new Position(-1);
				head = s; 
				tail = t; 
			}
			
			Position last = head; 
			Position s = head.next;
			cnt++;
			
			while(s!=null && s!=tail ) {
				if (s.pos > n.pos ) { 
					Position pre = s.prev;
					pre.next = n ; 
					n.prev = pre;
					
					n.next = s;
					s.prev = n;
					return;
				}
				last = s; 
				s = s.next;
			}
			
			last.next = n ;
			n.prev = last; 
			
			n.next = tail;
			tail.prev = n;
		}
		void delete(Position h) { 
			Position pre = h.prev;
			Position ne = h.next;
			pre.next = ne;
			ne.prev = pre;
					
			cnt--;
		}
		public Position find(int pos) {
			Position h = head.next;
			while(h.pos!=-1) { 
				if ( h.pos == pos ) return h;
				h = h.next;
			}
			return null;
		}
		
		void print() {
			Position h = head.next;
			while(h!=null && h!=tail) {
				System.out.print(h.pos +"->");
				h= h.next;
			}
			System.out.println();
			
		}
	}
		
}

