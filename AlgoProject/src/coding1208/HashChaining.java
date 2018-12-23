package coding1208;

import java.util.HashMap;

import org.junit.Test;

public class HashChaining {
	node[] nodelist1 = new node[100];
	node[] nodelist2 = new node[100];
	static char[] b = new char[] { '1', '2', '3', '4', '5' };

	@Test
	public void testStaticChar() {
		String t = new String(b);
		t = t + "1";
		char[] c = b;
		System.out.println(new String(b));
		c[1] = 'p';
		System.out.println(t);
	}

	@Test
	public void testKeyData() {
		// TODO implement HashChaining
		HashMap map = new HashMap();
		node t = new node();
		nodelist1[0] = t;
		nodelist1[0].idx = 1;
		nodelist1[0].data = "test".toCharArray();
		map.put("1", nodelist1);
		node[] list = (node[]) map.get("1");
		System.out.println(list[0].idx + "," + new String(list[0].data));
	}

	class node {
		char[] data;
		int idx;
	}

	@Test
	public void test1() {
		// TODO implement HashChaining
		HashChainingTable hsC = new HashChainingTable(2);
		hsC.add(new Item("1".toCharArray(), "aaa".toCharArray()));
		hsC.add(new Item("2".toCharArray(), "bbb".toCharArray()));
		hsC.add(new Item("3".toCharArray(), "ccc".toCharArray()));
		hsC.add(new Item("4".toCharArray(), "ddd".toCharArray()));
		hsC.print();

		Item t = hsC.get("2".toCharArray());
		System.out.println(new String(t.data));

		char[] key = "3".toCharArray();
		if (hsC.isContain(key)) {
			hsC.delete(key);
		}

		hsC.delete("1".toCharArray());

		hsC.print();
	}

	@Test
	public void test() {
		// TODO implement HashChaining
		HashChainingTable hsC = new HashChainingTable(5);
		hsC.add(new Item("1".toCharArray(), "aaa".toCharArray()));
		hsC.add(new Item("2".toCharArray(), "bbb".toCharArray()));
		hsC.add(new Item("3".toCharArray(), "ccc".toCharArray()));
		hsC.add(new Item("4".toCharArray(), "ddd".toCharArray()));
		hsC.add(new Item("5".toCharArray(), "ddd".toCharArray()));
		hsC.print();

		Item t = hsC.get("2".toCharArray());
		System.out.println(new String(t.data));

		char[] key = "3".toCharArray();
		if (hsC.isContain(key)) {
			hsC.delete(key);
		}
		hsC.print();

		hsC.delete("1".toCharArray());
		hsC.print();

		hsC.delete("2".toCharArray());
		hsC.print();
		
		hsC.delete("4".toCharArray());
		hsC.print();
	}

	class HashChainingTable {

		ItemList[] listTable = null;
		ItemList headList , tailList;
		int capacity;

		public HashChainingTable(int capacity) {
			this.capacity = capacity;
			listTable = new ItemList[capacity];
			for (int i = 0; i < capacity; i++) {
				listTable[i] = new ItemList();
			}
		}

		private int getHash(char[] str) {
			int hash = 5381;
			for (int i = 0; i < str.length; i++) {
				int c = str[i];
				hash = (hash << 5) + c * ((i * i) + i + 41);
			}
			if (hash < 0)
				hash *= -1;
			return hash % capacity;
		}

		public void add(Item item) {
			int hash = getHash(item.key);
			
			// ITEMLIST를 NEXT로 가리키게 하면, HASHTABLE 모든 Loop 안거치고, 출력가능
			// head가 null이라는 의미는 새로운 ITEMLIST 라는 의미로, ITEM값 할당전에,NEXTLIST로써, 가리키게 한다.
			// 그냥 똑같이 LinkedList 구현하면 된다 그냥 똑같이 해..그게 간편하다.
			if (listTable[hash].head == null){
				if ( headList == null ) {
					headList = listTable[hash];
					tailList = listTable[hash];
				}else{
					tailList.nextList = listTable[hash]; 
					tailList = listTable[hash];					
				}
			}
			
			listTable[hash].add(item);
		}

		public void delete(char[] key) {
			int hash = getHash(key);
			listTable[hash].delete(key);
		}

		public boolean isContain(char[] key) {
			int hash = getHash(key);
			Item h = listTable[hash].head;

			while (h != null) {
				if (isEqualTo(key, h.key)) {
					return true;
				}
				h = h.next;
			}
			return false;
		}

		public Item get(char[] key) {
			int hash = getHash(key);
			Item h = listTable[hash].head;

			while (h != null) {
				if (isEqualTo(key, h.key)) {
					return h;
				}
				h = h.next;
			}
			return null;
		}

		public void print() {
			//ITEMLIST를 NEXT로 가리키게 하면, HASHTABLE 모든 Loop 안거치고, 출력가능
			ItemList list = headList; 
			int c = 0;
			while( list != null ) {
				Item h = list.head;
				System.out.println("# INDEX - " + c++);
				while (h != null) {
					System.out.println("## " + new String(h.key) + ":" + new String(h.data));
					h = h.next;
				}				
				list = list.nextList;
			}
			/*
			for (int c = 0; c < this.capacity; c++) {
				
				ItemList list = listTable[c];

				if (list.head != null) {
					System.out.println("# INDEX - " + c);
					Item h = list.head;

					while (h != null) {
						System.out.println("## " + new String(h.key) + ":" + new String(h.data));
						h = h.next;
					}
				}
			}
			*/

		}

		boolean isEqualTo(char[] ori, char[] target) {
			if (ori.length == target.length) {
				for (int i = 0; i < ori.length; i++) {
					if (ori[i] != target[i])
						return false;
				}
				return true;
			}
			return false;
		}

	}

	class ItemList {
		Item head, tail;
		ItemList nextList = null;
		void add(Item nItem) {
			if (head == null) {
				head = nItem;
				tail = nItem;
			} else {
				tail.next = nItem;
				nItem.prev = tail;
				tail = nItem;
			}
		}

		void delete(char[] key) {

			Item f = find(key);

			if (f == head && f == tail) {
				head = null;
				tail = null;
				return;
			}

			if (f == head) {
				head = f.next;
				f.next.prev = null;
			} else if (f == tail) {
				Item prev = tail.prev;
				prev.next = null;
				tail = prev;
			} else {
				Item pr = f.prev;
				Item ne = f.next;
				pr.next = ne;
				ne.prev = pr;
			}
		}

		Item find(char[] key) {
			Item h = head;
			while (h != null) {
				if (isEqualTo(h.key, key)) {
					return h;
				}
				h = h.next;
			}
			return null;
		}

		boolean isEqualTo(char[] ori, char[] target) {
			if (ori.length == target.length) {
				for (int i = 0; i < ori.length; i++) {
					if (ori[i] != target[i])
						return false;
				}				
				return true;
			}
			return false;
		}

	}

	class Item {
		Item prev, next;
		char[] key;
		char[] data;

		public Item(char[] key, char[] data) {
			this.key = new char[key.length];
			this.data = new char[data.length];
			for (int i = 0; i < key.length; i++) {
				this.key[i] = key[i];
			}

			for (int i = 0; i < data.length; i++) {
				this.data[i] = data[i];
			}
		}
	}

}
