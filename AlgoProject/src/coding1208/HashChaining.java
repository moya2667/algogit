package coding1208;

import java.util.HashMap;

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
		System.out.println(list[0].idx +"," + new String(list[0].data));		
	}
	
	class node {
		char[] data;
		int idx;
	}
	

    @Test
    public void test1() {
        //TODO implement HashChaining
        HashChainingTable hsC= new HashChainingTable(2);
        hsC.add(new Item( "1".toCharArray() , "aaa".toCharArray()));        
        hsC.add(new Item( "2".toCharArray() , "bbb".toCharArray()));
        hsC.add(new Item( "3".toCharArray() , "ccc".toCharArray()));
        hsC.add(new Item( "4".toCharArray() , "ddd".toCharArray()));
        hsC.print();
        
        Item t = hsC.get("2".toCharArray());
        System.out.println(new String(t.data));
        
        char[] key = "3".toCharArray();
        if(hsC.isContain(key)) { 
            hsC.delete(key);   
        }
        
        hsC.delete("1".toCharArray());
        
        hsC.print();
    }	

	@Test
	public void test() {
		//TODO implement HashChaining
	    HashChainingTable hsC= new HashChainingTable(2);
	    hsC.add(new Item( "1".toCharArray() , "aaa".toCharArray()));	    
	    hsC.add(new Item( "2".toCharArray() , "bbb".toCharArray()));
	    hsC.add(new Item( "3".toCharArray() , "ccc".toCharArray()));
	    hsC.add(new Item( "4".toCharArray() , "ddd".toCharArray()));
	    hsC.print();
	    
	    Item t = hsC.get("2".toCharArray());
	    System.out.println(new String(t.data));
	    
	    char[] key = "3".toCharArray();
	    if(hsC.isContain(key)) { 
	        hsC.delete(key);   
	    }
	    
	    hsC.print();
	}
	
	class HashChainingTable{

	    ItemList[] listTable = null;
	    int capacity;
        public HashChainingTable(int capacity) {
            this.capacity = capacity;
            listTable = new ItemList[capacity];
            for (int i = 0; i < capacity; i++) {
                listTable[i] = new ItemList();
            }
        }
        
        private int getHash(char[] str)
        {
            int hash = 5381;            
            for (int i = 0; i < str.length; i++)
            {
                int c = str[i];
                hash = (hash << 5) + c * ((i*i)+i+41);
            }
            if (hash < 0) hash *= -1;
            return hash % capacity;
        }
        
        
        public void add(Item item) {
            int hash = getHash(item.key);
            //예외case가 존재하나..?
            listTable[hash].add(item);            
        }
        
        public void delete(char[] key) {
            
            int hash = getHash(key);
            Item f = listTable[hash].head;            
            
            while(f !=null) {
                
                //같으면 찾았고, 연결관계 끝는다. 
                if (isEqualTo(f.key , key)) {
                    //하나일경우 
                    if (f == listTable[hash].head && f== listTable[hash].tail) {
                        listTable[hash].head = null;
                        listTable[hash].tail = null;
                        return;
                    }
                    
                    //head 일경우                    
                    if (f == listTable[hash].head){
                        listTable[hash].head = f.next;
                        f.next.prev = null;
                        return;
                    }                    
                    //tail 일경우
                    if (f == listTable[hash].tail){
                        listTable[hash].tail = f.prev;
                        f.prev.next = null;
                    }                    
                    /*
                    Item prev = h.prev;
                    Item next = h.next;
                    prev.next = next;
                    next.prev = prev;
                    */
                }  
                
                f = f.next;
            }
        }

        public boolean isContain(char[] key) {
            int hash = getHash(key);
            Item h = listTable[hash].head;
            
            while( h != null) { 
                if ( isEqualTo(key, h.key)){
                    return true;
                }
                h =  h.next;
            }
            return false;
        }

        public Item get(char[] key) {
            int hash = getHash(key);
            Item h = listTable[hash].head;
            
            while( h != null) { 
                if ( isEqualTo(key, h.key)){
                    return h;
                }
                h =  h.next;
            }            
            return null;
        }
        
        public void print() {
            for (int c = 0; c < this.capacity; c++) {
                ItemList list = listTable[c];                                
                
                if ( list.head != null) {
                    System.out.println("# INDEX - " + c);
                    Item h = list.head;
                    
                    while(h!=null) {
                        System.out.println("## " + new String(h.key) + ":" + new String(h.data));
                        h= h.next;
                    }
                    System.out.println();
                }

                /*
                while (list != null) {
                    Item item = list.head;

                    System.out.println("## ITEM LIST - " + (itemid++));

                    while (item != null) {
                        char name[] = item.name;
                        int id = item.id;

                        System.out.print(" - " + id + " ");
                        System.out.print(new String(name) + " ");
                        System.out.println();

                        item = item.next;
                    }
                    list = list.lnext;
                }
                */

            }            
            
        }
        
        boolean isEqualTo(char[] ori , char[] target) {
            if ( ori.length == target.length ){
                for (int i = 0 ; i < ori.length ; i++) { 
                    if ( ori[i] != target[i] ) return false;
                }
                //System.out.println("equal to : " + new String(target));
                return true;
            }
            return false; 
        }
		
	}
	
	class ItemList{
	    Item head,tail; 
	    
	    void add(Item nItem) {
	        if (head == null) {
                head = nItem;
                tail = nItem;
            }else{
                tail.next = nItem;
                nItem.prev = tail; 
                tail = nItem;
            }
	    }
	    
	    void addHead(Item nItem) {	        
	        if (head == null) {
	            head = nItem;
	        }else{
	            //검증필요
	            Item ori = head;
	            nItem= ori.prev;
	            nItem.next = ori;
	            head = nItem;
	        }	        
	    }
	    
	}

    class Item {
        Item prev,next;
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

