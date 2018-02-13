package 이전연습;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class 라이브러리사용 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLinkedlist() {
		LinkedList t = new LinkedList<>();
		t.add("t");
		t.add("d");
		
		
		System.out.println(t.contains("t"));
		System.out.println(t.contains("a"));
		System.out.println(t.contains("d"));
		
		Iterator it = t.iterator();
		
		while(it.hasNext()) { 
			System.out.println(it.next());			
		}
	}
	
	@Test
	public void testHashMap() { 
		HashMap t = new HashMap<>(); 
		t.put(1, "a");
		t.put(2, "b");
		
		System.out.println( t.get(1));
		System.out.println( t.get(2));
		System.out.println( t.get(3));
		
		Iterator e = t.keySet().iterator();
		while(e.hasNext()){
			System.out.println(e.next());
		}
	}

}
