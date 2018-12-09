package coding1208;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HashChaining {
	node[] nodelist1 = new node[100];
	node[] nodelist2 = new node[100];
	static char[] b = new char[]{'1','2','3','4','5'};
	@Test
	public void testStaticChar() {
		String t = new String(b);
		t = t+"1";
		char[] c = b;
		System.out.println(new String(b));
		c[1] = 'p';		
		System.out.println(t);
	}
	@Test
	public void testKeyData() {
		//TODO implement HashChaining
		HashMap map = new HashMap();
		node t= new node();
		nodelist1[0] = t; 
		nodelist1[0].idx = 1;
		nodelist1[0].data = "test".toCharArray();		
		map.put("1", nodelist1);		
		node[] list = (node[])map.get("1");
		System.out.println(list[0].idx +"," + list[0].data);		
	}
	
	class node {
		char[] data;
		int idx;
	}

	@Test
	public void test() {
		//TODO implement HashChaining
	}
	
	class HashChain{
		
	}

}

