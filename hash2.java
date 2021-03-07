import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;

public class hash2 {

	Hashtable myhash = new Hashtable(1000);
	static account[] accounts = new account[1000];
	Hashtable grouphash = new Hashtable(1000);
	int uIdx = 0; 
	
	int getNewUser(char[] u) {
		if (users[uIdx] == null) {
			users[uIdx] = new user(u);
		}		
		return uIdx++;
	}
	
	int getNewGroup(int gid) {
		if (groups[gid] == null) {
			groups[gid] = new group(gid);
		}		
		return uIdx++;
	}
	
	int getNewAccount1(char[] user, int money , int gid) {
		//account 생성 
		if (accounts[nIdx] == null) {
			accounts[nIdx] = new account(money);
		}
		accounts[nIdx].money = money;
		
		//user의 account 연결 
		//int uId = myhash.find(user);
		//if (uId == -1) {
		
		//find 호출없이, 
		//moya의  user idx 넘겨받는 방식으로 처리하고, 따로 구현하면, find 호출없이,성능유지
		int uId = getNewUser(user);
		uId = myhash.add(user, uId);
		//}		
		users[uId].l.add(nIdx);
		
		
		//group에 이름 연결
		if (groups[gid] == null) { 
			groups[gid] = new group(gid);
		}
		
		//if ( groups[gid].find(user) == -1 ) { 
			groups[gid].adduser(user,uId);
		//}		
		return nIdx++;
	}
	
	int getNewAccount(char[] user, int money , int gid) {
		//account 생성 
		if (accounts[nIdx] == null) {
			accounts[nIdx] = new account(money);
		}
		accounts[nIdx].money = money;
		
		//find 호출없이, 
		//moya의  user idx 넘겨받는 방식으로 처리하고, 따로 구현하면, find 호출없이,성능유지
		int uId = getNewUser(user);
		myhash.add(user, uId);	
		users[uId].l.add(nIdx);
		
		
		//group에 이름 연결
		if (groups[gid] == null) { 
			groups[gid] = new group(gid);
		}
		groups[gid].adduser(user,uId);
		
		return nIdx++;
	}	
	
	@Test
	public void test2() {
		//사용자의 계좌를 만들고 ,사용자가 관리하는 계좌들을 링크로 연결하고 출력한다      
		int Id100 = getNewAccount("moya".toCharArray(), 100, 1);
		int Id200 = getNewAccount("moya".toCharArray(), 200, 1);
		int Id300 = getNewAccount("moya".toCharArray(), 300, 1);
		int Id400 = getNewAccount("moya".toCharArray(), 400, 2);
		System.out.println("moya 계좌목록");
		int idx = myhash.find("moya".toCharArray());		
		users[idx].l.print();	
	}		
	
	@Test
	public void test1() {
		//사용자의 계좌를 만들고 ,사용자가 관리하는 계좌들을 링크로 연결하고 출력한다      
		int Id100 = getNewAccount("moya".toCharArray(), 100, 1);
		int Id200 = getNewAccount("hee".toCharArray(), 200, 1);
		int Id300 = getNewAccount("moya".toCharArray(), 300, 1);
		int Id400 = getNewAccount("moya".toCharArray(), 400, 2);

		int yoo100 = getNewAccount("yoo".toCharArray(), 100, 2);
		int yoo300 = getNewAccount("yoo".toCharArray(), 300, 3);

		System.out.println("moya 계좌목록");
		int idx = myhash.find("moya".toCharArray());		
		users[idx].l.print();		
		users[idx].l.update(Id200);
		users[idx].l.print();
		
		System.out.println("Yoo 계좌목록");
		idx = myhash.find("yoo".toCharArray());
		users[idx].l.print();
		
		groups[1].l.printGroup();		
		groups[2].l.printGroup();		
		groups[3].l.printGroup();
		
		//delete test 
		//deleteAccount(Id100);
	}
	user[] users = new user[100];
	group[] groups = new group[20];
	
	class account {
		account next, prev;		
		int money;
		account(int money) { 
			this.money = money;
		}
	}

	class group {
		int gid;		
		ll l = new ll();
		Hashtable grouphash = new Hashtable(1000);		
		group(int gid) {
			this.gid = gid;
		}
		public void adduser(char[] user, int uId) {
			grouphash.add(user, uId);
			groups[gid].l.addgroup(uId);
		}
		
		public int find(char[] user) {
			return grouphash.find(user);
		}
	}
	
	class node {
		node prev , next;
		int idx;
		node(int idx) {
			this.idx = idx;
		}
	}

	class user{
		char[] user = new char[10];
		ll l = new ll(); 
		user(char[] u) {
			for (int i = 0; i < u.length; i++) {
				user[i] = u[i];
			}
		}
	}

	int nIdx = 0;
	
	class ll {
		account head, tail;
		node h , t ; 
		void addgroup(int idx) { 
			node n = new node(idx);
			if (h == null) { 
				h = n ; 
				t = n ; 
			}else{ 
				t.next = n ;
				n.prev = t; 
				t = n ; 
			}
		}

		public boolean findGroup(int uId) {
			node n = h;
			while (n != null) {
				if(n.idx == uId) return true;
				n = n.next;
			}
			return false;
		}

		void add(int idx) {
			account n = accounts[idx];

			if (head == null) {
				head = new account(-1);
				tail = new account(-1);
				head.next = n;
				n.prev = head;
			} else {
				account prev = tail.prev;
				prev.next = n;
				n.prev = prev;
			}

			n.next = tail;
			tail.prev = n;
		}

		public void update(int aId) {
			// 띠어내기
			account pre = accounts[aId].prev;
			account ne = accounts[aId].next;
			pre.next = ne;
			ne.prev = pre;

			// 붙이기
			account first = head.next;
			head.next = accounts[aId];
			accounts[aId].prev = head;
			accounts[aId].next = first;
			first.prev = accounts[aId];
		}

		public void printGroup() {
			node n = h;
			while (n != null) {
				System.out.println(users[n.idx].user );
				n = n.next;
			}
			System.out.println();
		}

		public void print() {
			account he = head;
			while (he != null) {
				System.out.print(he.money + " ");
				he = he.next;
			}
			System.out.println();
		}
	}



	class Hashtable {
		class Hash {
			long lstr;
			char[] key;
			int idx;
		}

		int capacity;
		Hash tb[];

		public Hashtable(int capacity) {
			this.capacity = capacity;
			tb = new Hash[capacity];
			for (int i = 0; i < capacity; i++) {
				tb[i] = new Hash();
			}
		}

		long getUll(char[] text) {
			long ret = 0;
			for (int i = 0; i < text.length; i++) {
				ret = (ret << 5) | (text[i] - 'a' + 1);
			}
			return ret;
		}

		public int find(char[] key) {
			long lstr = getUll(key);
			int h = (int) (lstr % capacity);

			int cnt = capacity;
			while (tb[h].key != null && (--cnt) != 0) {
				if (tb[h].lstr == lstr) {
					return tb[h].idx;
				}
				h = (h + 1) % capacity;
			}
			return -1;
		}

		int add(char[] key, int idx) {
			long lstr = getUll(key);
			int h = (int) (lstr % capacity);

			while (tb[h].key != null) {
				if (tb[h].lstr == lstr) {					
					//return -1;
					return tb[h].idx;
				}
				h = (h + 1) % capacity;
			}
			tb[h].lstr = lstr;
			tb[h].key = key;
			tb[h].idx = idx;			
			return idx;
		}

		boolean delete(char[] key) {
			long lstr = getUll(key);
			int h = (int) (lstr % capacity);
			int cnt = capacity;
			while (tb[h].key != null && (--cnt) != 0) {
				if (tb[h].lstr == lstr) {
					tb[h] = new Hash();
					return true;
				}
				h = (h + 1) % capacity;
			}
			return false;
		}
	}

}
