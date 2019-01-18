package coding1318;

import org.junit.Test;

public class hashtable1 {

    HashTable hashTable = new HashTable(2);
  
    
    @Test
    public void test() {
        hashTable.add(new Item("aa".toCharArray()));
        hashTable.add(new Item("bb".toCharArray()));
        hashTable.isExist("cc".toCharArray());
        hashTable.delete("bb".toCharArray());
        
        if (!hashTable.isExist("bb".toCharArray()) ){
            System.out.println("not found");
        }
        
        hashTable.add(new Item("cc".toCharArray()));
        hashTable.print();
        
        /*
        hashTable.isfind("aa".toCharArray());        
        hashTable.delete("aa".toCharArray());
        */
    }
    
    class HashTable{        
        int capacity = 0;
        ItemList[] listTable = null;
        
        public HashTable(int capacity) {
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
            int hash = getHash(item.d);
            listTable[hash].add(item);            
        }

        public boolean isExist(char[] d) { 
            int hash = getHash(d);
            if ( listTable[hash].find(d) != null){
                System.out.println("find : " +  new String(d));
                return true;
            }            
            return false;
        }

        public void delete(char[] d) {
            int hash = getHash(d);
            Item f = listTable[hash].find(d);
            
            if ( f!= null) {
                listTable[hash].delete(d);
            }            
            
        }
        
        public void print(){
            for (int i = 0; i < listTable.length; i++) {                
                if ( listTable[i].head != null ) {
                    Item h = listTable[i].head;
                    System.out.println("ItemList :"  + i );
                    while(h != null) { 
                        if (h.delete != true) { 
                            System.out.print(new String(h.d) + " ");
                        }
                        h = h.next;
                    }
                    System.out.println();
                }
            }
        } 
        
    }
    
    class ItemList{
        Item head,tail;        
        void add(Item item) {
            if (head == null) {
                head = item;
                tail = item; 
            }else{
                tail.next = item;
                item.prev = tail; 
                tail = item;
            }
        }
        
        Item find(char[] a) {
            Item h = head;
            while(h!= null) {                
                if (h.delete == false && equalTo(a,h.d)){
                    return h;
                }
                h = h.next;
            }
            return null;
        }
        
        boolean equalTo(char[] ori , char[] tar) {
            if (ori.length != tar.length) return false;
            for (int i = 0 ; i < ori.length ; i++) { 
                if ( ori[i] != tar[i] ) return false;
            }
            return true;
        }
        
        
        void delete(char[] a) { 
           Item f =  find(a);
           if ( f == null) {
               System.out.println("not found item : " + new String(a));
           }
           
           f.delete = true;
           
           /*
           //하나일경우 
           if (f == head && f== tail) {
               
               return;
           }
                       
           if (f == head) {
           
               
           }else if(f == tail){
               
           }
           */
           
           
        }
    }
    
    class Item {
        Item prev,next;
        boolean delete;
        char[] d ; 
        public Item(char[] a){
           d = new char[a.length];
           for (int i = 0; i < a.length; i++) {
               d[i] = a[i];
           }
           
           delete = false;
        }
    }
    
    

}
