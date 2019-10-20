package coding1916;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;


import org.junit.Test;

public class HashKey_Link {

	HashMap<String, ArrayList<Integer>> map = new HashMap<>(); 
	char[] data = "1111123".toCharArray();
	
	@Test
	public void test() {
		
		init(data);
		if(!testfind("111")){
			System.out.println("not found");
		}
		if(!testfind("123")){
			System.out.println("not found");
		}
		
		printdata();
		
		change("123","345"); 
		
		printdata();
	}
	
	private void printdata() {
		// TODO Auto-generated method stub
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]);
		}
		System.out.println();
	}

	private void change(String key, String key2) {
		//index 를 찾아서 모두 바꾼다.
		ArrayList<Integer> idxlist = map.get(key);
		
		if (idxlist == null) { 
			System.out.println("there is no key");
			return; 
		}
		
		int c = idxlist.size();
		for (int i = 0; i < c; i++) {
			int idx = idxlist.get(i);
			//해당 idx 의 주변 -2 ~ 2 구간의 key를 모두 바꾼다.
			mychange(idx , key2);
		}
		
	}
	
	void mychange(int idx , String key2) { 
		//delete key 
		for (int i = idx-2 ; i <= idx+2 ; i++){
			if ( i != 0 || i >= 0 || i < data.length) { 
				String key = getString(i);
				if (key != null) { 
					deletekey(i , key);
				}
			}
		}
		
		//update db data
		char[] updatekey = key2.toCharArray();
		data[idx] = updatekey[0];
		data[idx++] = updatekey[1];
		data[idx++] = updatekey[2];
		
		
		//add new key
		for (int i = idx-2 ; i <= idx+2 ; i++){
			if ( i != 0 || i >= 0 || i < data.length) { 
				String key = getString(i);
				if (key != null) { 
					addkey(i,key);
				}
			}
		}
		
	}
	private void addkey(int idx , String key) {
		//key의  idx 가 존재하면 붙여넣는다. 
		ArrayList<Integer> idxlist = map.get(key);
		if (idxlist == null) { 
			idxlist = new ArrayList<Integer>();
		}		
		idxlist.add(idx);
		map.put(key, idxlist); 
	}

	private void deletekey(int idx, String key) {
		// key 의 idxlist size() 가  0 일경우 key 를 삭제하고 , 0이 아닐경우 , 해당 idx 를  찾아서 삭제한다  
		ArrayList<Integer> idxlist = map.get(key);
		if (idxlist == null) { 
			System.out.println("[deletekey] not found key");
			return; 
		}
		int c = idxlist.size();
		if ( c == 0 ) { 
			map.remove(key);
			System.out.println("[key] delete key");
		}
		
		for (int i = 0; i < c; i++) {
			if ( idxlist.get(i) == idx ) { 
				idxlist.remove(i);
				System.out.println("[key] delete idx");
				return;
			}
		}
	}

	String getString(int idx) { 
		String key = "" ;
		for (int i = idx ; i<=idx+2 ; i++){
			if (i  == data.length ){
				return null;
			}
			key += data[i];
		}
		
		return key;
	}

	private boolean testfind(String key) {
		ArrayList<Integer> l = map.get(key);
		if (l == null) { 
			return false;
		}
		
		for (int i = 0; i < l.size(); i++) {
			int idx = l.get(i);
			System.out.print(idx + " , ");
			
		}
		System.out.println();		
		return true;
	}

	void init(char[] data) { 
		for (int i = 0; i < data.length; i++) {
			char[] ckey = new char[3];
			int cnt = 0; 
			for(int j=i; j <i+3 ; j++) {
				if (j  == data.length ){
					return;
				}
				ckey[cnt++] = data[j];
			}
			//temp key 
			String key = new String(ckey);
			//add hash
			ArrayList<Integer> idxlist = map.get(key);
			if(idxlist == null){
				idxlist = new ArrayList<Integer>();
			}
			
			idxlist.add(i);
			
			//key 에 idx 등록
			System.out.println("add key = " + key);
			map.put(key,idxlist);			
		}
	}

}
