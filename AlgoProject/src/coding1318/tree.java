package coding1318;

import java.util.HashMap;

import org.junit.Test;

public class tree {

    HashMap map = new HashMap();
    
    @Test
    public void test() {
        
        treeT tr = new treeT();
        node r = new node("r".toCharArray());
        tr.setRoot(r);
        node one = tr.addchild(r,"1".toCharArray());
        node two = tr.addchild(r,"2".toCharArray());
        node oneone = tr.addchild(one,"11".toCharArray());
        node twotwo = tr.addchild(two,"22".toCharArray());        
        tr.print(r,0);
        
        tr.delete("1".toCharArray());        
        tr.print(r,0);
        
    }
    
    class treeT {
        node root; 
        public void setRoot(node r) {
            root = r;
            //map.(new String(r.data) , root);
            map.put(new String(r.data), root);
        }
        
        public void delete(char[] chars) {
            //node f = find(root, chars);
            node f = find(chars);
            if (f == root) { 
                System.out.println("can not delete root node");
                return;
            }
            if (f !=null) { 
                node p = f.parent;
                p.link.delete(f);
                return;
            }
            
            System.out.println("not found node : " + new String(chars) );
        }

        public void print(node r , int c) {
            if (r == null) return;
            
            for (int i = 0 ; i < c ; i++) { 
                System.out.print("+");
            }
            System.out.println(new String(r.data));
            
            node h = r.link.head;
            if ( h!= null ) { 
                print(h , c+1);
                
                while (h!= null) { 
                    h = h.next;
                    print(h , c+1);
                }
            }
        }
        
        public node addchild(node r, char[] t) {
            node n = new node(t);
            n.parent = r ;
            r.link.add(n); 
            
            map.put( new String(n.data), n); 
            return n;
        }
        
        node find(char[] r) {
            return (node)map.get(new String(r));
        }
        
        node find(node r, char[] a){
            if (r == null) return null;           
            
            System.out.println(new String(r.data));
            if ( equalTo(r.data,a) ){
                System.out.println("find");
                return r;
            }
            
            node h = r.link.head;
            if ( h!= null ) { 
                if ( find(h , a) != null ){
                    return h;
                }
                
                while (h!= null) { 
                    h = h.next;
                    if ( find(h , a) != null ){
                        return h;
                    }
                }
            }
            return null;
        }
        
        boolean equalTo(char[] ori, char[] tar) {
            if ( ori.length != tar.length ) return false;
            
            for (int i = 0; i < tar.length; i++) {
                if (ori[i] != tar[i] ) {
                    return false;
                }
            }
            
            return true;
        }
    }
    
    
    class link {
        node head, tail;        
        
        void add(node n) {
            if (head == null) { 
                head = n ;
                tail = n ;
            }else{
                tail.next = n;
                n.prev = tail;
                tail = n;
            }
        }
        
        void delete(node t) {            
            if (t == null) { 
                System.out.println("not found = " + new String(t.data));
                return;
            }
            //窍唱老版快 
            if (t == head && t == tail) { 
                head = null;
                tail = null;
                return;
            }
            
            //head老版快
            if (t == head) {
                node ne = t.next;
                ne.prev = null;
                t.next = null;
                head = ne;            
            //tail老版快
            }else if ( t == tail){
                node pre = t.prev;
                pre.next = null;                 
                pre = tail;
             // 吝埃老版快    
            }else{
               node pe = t.prev;
               node ne = t.next;
               
               pe.next = ne; 
               ne.prev = pe;
            }            
            t = null;
            return;
        }
        
        node find(char[] a){
            node h = head; 
            
            while( h != null) {
                if (equalTo(a , h.data ) ) {
                    return h;
                }
                h = h.next;
            }
            return null;
        }

        boolean equalTo(char[] ori, char[] tar) {
            if ( ori.length != tar.length ) return false;
            
            for (int i = 0; i < tar.length; i++) {
                if (ori[i] != tar[i] ) {
                    return false;
                }
            }
            
            return true;
        }
    }
    
    class node{
        link link;
        char[] data; 
        node prev,next;
        node parent;        
        public node(char[] a) {
            link = new link();  
            data = new char[a.length];
            for(int i = 0; i< a.length ; i++) {
                data[i] = a[i];
            }
        }
    }
    
    
    
}
