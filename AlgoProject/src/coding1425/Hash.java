package coding1425;


import org.junit.Test;

public class Hash {

	@Test
	public void test() {
		MOYAHash h = new MOYAHash(2);
		h.put("aa".toCharArray() ,1);
		h.put("bb".toCharArray() ,2 );		
		h.put("cc".toCharArray() ,3);
		h.put("dd".toCharArray() ,4);		
		h.put("ee".toCharArray() ,5);		
		h.put("f".toCharArray() ,5);		
		h.put("g".toCharArray() ,5);
		h.put("h".toCharArray() ,5);
		h.put("i".toCharArray() ,5);

		//h.delete("aa".toCharArray());
		//h.update("bb".toCharArray(), 123);

 		Item item = h.get("bb".toCharArray());
		System.out.println("item = " + item.v);
		h.print();
	}

	class MOYAHash {
		ItemList[] list;   
		int capacity = 0 ;
		MOYAHash(int capa){
			capacity= capa;
			//이건 단순히 배열List를 선언한거구나.
			list = new ItemList[capa];			
			for (int i = 0; i < list.length; i++) {
				//여기서 생성하는거.
				list[i] = new ItemList();
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
		void put(char[] charArray , int v) {
			int h = getHash(charArray);
			list[h].put(charArray ,v);
		}

		void delete(char[] charArray) {
			int h = getHash(charArray);
			if ( list[h].delete(charArray) ){
				System.out.println("Delete Success");
				return;
			}
			System.out.println("There is no key");
		}

		void update(char[] charArray, int i) {
			int h = getHash(charArray);
			if ( list[h].update(charArray , i) ){
				System.out.println("Update : " + i);
				return;
			}
			System.out.println("Update Success");
		}

		void print() {
			for (int i = 0; i < capacity; i++) {
				ItemList l = list[i];
				Item h = l.head;
				if (h !=null) {					
					System.out.println("ItemList : "  + i);
					while( h != null ) {
						if ( h.deleteF == false ){
							System.out.println("["+new String(h.d) +"]" + h.v);
						}
						h = h.next;
					}
				}
			}
		}

		Item get(char[] charArray) {
			int h = getHash(charArray);
			return list[h].get(charArray);
		}

	}

	class ItemList {
		Item head, tail;

		void put(char[] p , int v) {
			Item e = new Item(p , v);
			if (head ==null){
				head = e;
				tail = e;
				return;
			}
			tail.next = e;
			e.prev = tail;
			tail = e; //아 돌아버리겠네.
		}

		public boolean update(char[] charArray, int i) {
			Item h = head;
			while( h != null) {
				if (h.deleteF == false && equalto(charArray, h.d)) {
					h.v = i;
					return true;
				}
				h = h.next;
			}
			return false;
		}

		public boolean delete(char[] charArray) {
			Item h = head;
			while( h != null) {
				if (equalto(charArray, h.d)) {
					h.deleteF = true;
					return true;
				}
				h = h.next;
			}
			return false;
		}

		public Item get(char[] charArray) {
			Item h = head;
			while( h != null) {
				if (h.deleteF == false && equalto(charArray, h.d)) {
					return h;
				}
				h = h.next;
			}
			return null;
		}
		
		boolean equalto(char[] o , char[] t) { 
			if ( o.length != t.length ) return false;
			
			for (int i = 0; i < t.length; i++) {
				if (o[i] != t[i]) return false;
			}
			return true;
		}
	}

	class Item {
		Item next, prev;
		int v;
		char[] d;
		boolean deleteF = false;

		Item(char[] chars , int value) {
			int len = chars.length;
			d = new char[len];
			for (int i = 0; i < len ; i++) {
				d[i] = chars[i];
			}
			this.v = value;
		}
	}

}
