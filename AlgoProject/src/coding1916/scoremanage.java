
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class scoremanage {

	

	@After
	public void tearDown() throws Exception {
	}
	
	//1. data 만들기 (o)
	//2. hash 만들기  (15분) (O)
	//3. member 데이터 만들기 (o) 
	//4. score 계산하기 (O)
	//5. 계산된 score 자료구조 만들기
	//    5-1.1,000,000,000 점수 1000 으로 나눈 자료구조 만들기
	//    5-2  해당 자료구조에 데이터 담기 (ll) 
	//    5-3 특정 영역에 속한 자료구조 sort 하기 
	//    5-4 특정 점수 등수 리턴하기 
	//    5-5 특정 점수 영역대에 있는 사람들 수 리턴하기
	Hashtable myhash;
	ll[][] subjectscorelist;
	void init() {
		myhash = new Hashtable(93719);
		subjectscorelist = new ll[5][1000000];
		
		for (int i = 0; i < subjectscorelist.length; i++) {
			for (int j = 0; j < subjectscorelist.length; j++) {
				subjectscorelist[i][j] = new ll();
			}
		}
		
	}
	
	@Test
	public void test() {
		init();
		int[] t = {10,20,30,40};
		this.add("moya" , new int[]{10,20,30,40}) ; 
		this.add("eva" , new int[]{5,10,5,10}) ; 
		this.add("gyeonmo" , new int[]{40,10,10,10}) ; 
		this.add("junghee" , new int[]{1,1,1,8}) ; 
		
		System.out.println( myhash.find("moya").name + " " +  myhash.find("moya").score[0]); 
		
		myhash.delete("moya");
		
		System.out.println( myhash.find("moya"));
		this.add("moya" , new int[]{50,20,30,40}) ; 
		System.out.println( myhash.find("moya").name + " " +  myhash.find("moya").score[0]);
		
		myhash.getProject(0,20).print();;
	}
	
	void add(String name , int[] scores) { 
		member m = new member(name,scores);
		myhash.add(name, m);
		myhash.addsubject(m);
	}
	
	class member {
		
		String name; 
		int[] score = new int[5] ; 
		int total;
		boolean delete = false; 
		public member(String name, int[] score) { 
			this.name = name; 
			for (int i = 0; i < 4; i++) {
				 this.score[i]= score[i];
				 total += this.score[i];
			}
		}
	}
	
	class score { 
		public score(member m , int score) {
			// TODO Auto-generated constructor stub
			this.score = score;
			this.m = m;
			
		}
		score prev, next;
		int score;
		member m;
	}
	
	class ll { 
		score h , t ; 
		void add(score m) { 
			if (h == null) { 
				h = m ;
				t = m; 
			}else{
				t.next = m ;
				m.prev = t; 
				t = m; 
			}
		}
		public void print() {
			// TODO Auto-generated method stub
			score t = h ; 
			while(t!=null) {
				if ( t.m.delete != true) { 
					System.out.print(t.score + " , ");
				}
				t = t.next;
			}
			System.out.println();
			
		}
		public int getcount(int v) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	class Hashtable
	{
		class Hash {
			String key;
			member mem;
			long mykey;
		}

		int capacity;
		Hash tb[];
		
		public Hashtable(int capacity){
			this.capacity = capacity;
			tb = new Hash[capacity];
			for (int i = 0; i < capacity; i++){
				tb[i] = new Hash();
			}
		}
		
		
		public ll getProject(int subject , int i) {
			// TODO Auto-generated method stub
			return subjectscorelist[subject][getscope(i)];
		}
		
		public int getProjectCount(int subject, int v) {
			// TODO Auto-generated method stub
			return subjectscorelist[subject][getscope(v)].getcount(v);
		}
		
		int getscope(int v){
			return v/10000;
		}


		public void addsubject(member m) {
			// TODO Auto-generated method stub
			for (int j = 0; j < m.score.length; j++) {
				int scope = m.score[j]/10000;
				subjectscorelist[j][scope].add(new score(m , m.score[j]));
			}
		}

		private int hash(String str)
		{
			int hash = 5381;
			
			for (int i = 0; i < str.length(); i++)
			{
				int c = (int)str.charAt(i);
				hash = ((hash << 5) + hash) + c;
			}
			if (hash < 0) hash *= -1;
			return hash % capacity;
		}
		
		public member find(String key){
			int h = hash(key);
			long longkey = getLongKey(key);
			int cnt = capacity;
			while(tb[h].key != null && (--cnt) != 0)
			{
				if (tb[h].mykey == longkey){
					if (tb[h].mem.delete == true) { 
						return null;
					}
					return tb[h].mem;
				}
				h = (h + 1) % capacity;
			}
			return null;
		}
		
		long getLongKey(String key) {
			long t = 0;
			for (int i = 0; i < key.length(); i++) {
				char c = key.charAt(i);
				t += c-'a' +1;
				t = t<<5;
			}
			return t;
		}
		
		boolean add(String key, member data)
		{
			int h = hash(key);
			long longkey = getLongKey(key);
			while(tb[h].key != null)
			{
				//if (tb[h].key.equals(key)){
				if (tb[h].mykey == longkey){
					if ( tb[h].mem.delete == true ) {
						break;
					}
				}
				h = (h + 1) % capacity;
			}
		
			tb[h].key = key;
			tb[h].mem = data;
			tb[h].mykey = longkey;
			
			//addScore(data);
			
			return true;
		}
		boolean delete(String key)
		{
			int h = hash(key);
			long longkey = getLongKey(key);
			while(tb[h].key != null)
			{
				if (tb[h].mykey == longkey){
					tb[h].mem.delete = true;
					return true;
				}
				h = (h + 1) % capacity;
			}
			return false;
		}
	}
	

}
