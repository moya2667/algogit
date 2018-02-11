package 슈퍼코딩20211;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import 슈퍼코딩20211.해쉬디비.DB.nodeinfo;

public class 해쉬디비 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test1() {
		char[] a = {'1','2','3'};				
		System.out.println("a = " + String.valueOf(a));		
	}

	@Test
	public void test() {
		
		DB db = new DB();
		
		db.add("key".toCharArray(), "data".toCharArray()); 
		db.add("key1".toCharArray(), "databb".toCharArray());
		nodeinfo in = (nodeinfo)db.find("key".toCharArray());
		System.out.println(in.t);
		
		db.update("key".toCharArray() , "db".toCharArray());
		in = (nodeinfo)db.find("key".toCharArray());
		System.out.println(in.t);
		
		db.delete("key".toCharArray());		
		Object t = db.find("key".toCharArray());
		
	}
	
	class DB {
		
		Hashtable hs = new Hashtable(4096);

		int addcnt = 0 ; 
		public boolean add(char[] charArray, char[] charArray2) {
			// TODO Auto-generated method stub
			nodeinfo info = new nodeinfo(charArray2 , addcnt++);
			hs.add(charArray, info);
			return true;
		}

		public Object find(char[] charArray) {
			// TODO Auto-generated method stub
			return hs.find(charArray);
		}

		public boolean delete(char[] charArray) {
			// TODO Auto-generated method stub
			return hs.delete(charArray);
		}

		public boolean update(char[] charArray, char[] charArray2) {
			// TODO Auto-generated method stub
			nodeinfo f = hs.find(charArray);
			if ( f == null) return false;
			f.t = charArray2; 
			return true;
		}		
		

		class nodeinfo { 
			char[] t;
			int idx; 
			nodeinfo(char[] t , int idx){
				this.t = t;
				this.idx = idx;
			}	
		}
		
		class Hashtable
		{
			class Hash {
				char[] key;
				nodeinfo info;
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
			
			private int hash(char[] str)
			{
				int hash = 5381;
				
				for (int i = 0; i < str.length; i++)
				{
					int c = (int)str[i];
					hash = ((hash << 5) + hash) + c;
				}
				if (hash < 0) hash *= -1;
				return hash % capacity;
			}
			
			public nodeinfo find(char[] key){
				int h = hash(key);
				int cnt = capacity;				
				while(tb[h].key != null && (--cnt) != 0)
				{
					if ( isCharSame(tb[h].key , key)) { 
						return tb[h].info;
					}
					
					h = (h + 1) % capacity;
				}
				return null;
			}
			
			private boolean isCharSame(char[] t , char[] b) { 
				
				for ( int i = 0 ; i < t.length ; i++) {
					if ( t[i] != b[i] ) return false; 
				}
				
				return true;
			}
			
			public boolean delete(char[] key){
				int h = hash(key);
				int cnt = capacity;				
				while(tb[h].key != null && (--cnt) != 0)
				{
					if (tb[h].key.equals(key)){
						tb[h]= null;
						return true;
					}
					h = (h + 1) % capacity;
				}
				System.out.println("there is no key : " + String.valueOf(key));
				return false;
			}
			
			
			boolean add(char[] key, nodeinfo data)
			{
				int h = hash(key);				
				while(tb[h].key != null)
				{
					if (tb[h].key.equals(key)){
						return false;
					}
					h = (h + 1) % capacity;
				}
				tb[h].key = key;
				tb[h].info = data;
				return true;
			}
		}
		
	}

}
