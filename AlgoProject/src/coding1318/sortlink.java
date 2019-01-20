package coding1318;

import java.util.HashMap;

import org.junit.Test;

public class sortlink {

    HashMap map = new HashMap();
    
    //SORT를 Linklist 로 적응시, 기준값은 하나여야 한다 (안그럴경우, 코드가 너무 복잡해진다)
    //TAIL 을 없애고, HEAD에 값을 미리 선언하는 방식으로 해보았다
    //Last NODE을 가지고 있다가, Last까지 코드가 온다면, 바로 뒤에다가 붙인다. 
    //위 코드가 구현할경우, 코드가 간결해진다.
    //9999 -> 1 -> 2 ->3
    
    @Test
    public void test() {        
        link ll = new link();
        ll.add(new node(3));
        ll.add(new node(1));
        ll.add(new node(2));        
        ll.print();
    }
    
    class link {
        node head , tail;
        int cnt = 0;        
        node last;
        
        void add(node n) {        	
            //n prioirty 기준으로 순서대로 들어간다        	
        	if( head ==null){
        		node t = new node(9999); 
        		head = t;  
        	}
        	
        	node h = head;
        	
        	while(h!=null) {        		
        		//시작 head 가 아니고  
        		if (h.priorty != 9999) {
        			//신규 n이 더 작다면, 
        			if (n.priorty < h.priorty ) {
        				node pre = h.prev;        				
        				
        				pre.next = n;
        				n.prev = pre; 
        				
        				h.prev = n;
        				n.next = h;
        				cnt++;
        				return;
        			}
        			
        		}   
        		last = h;
        		h = h.next;
        	}
        	
        	//여기 코드까지 왔으면, 끝에 붙이기. 어떻게 붙일까.
        	//last node 를 백업으로 가지고 있으면, tail은 필요없게 되는데.
        	last.next = n;
        	n.prev = last;        
        }
        
        public void print() {
			node h = head;			
			while(h!=null){				
				System.out.print(h.priorty + " -> ");				
				h = h.next;
			}
			System.out.println();			
		}	             
    }
    
    
    class link22 {
        node head , tail;
        int cnt = 0;
        
        node last;
        
        void add(node n) {        	
            //n prioirty 기준으로 순서대로 들어간다        	
        	if( head ==null){
        		node t = new node(9999);
        		//node t1 = new node(9998);
        		head = t;
        		//tail = t1;
        	}
        	
        	node h = head;
        	
        	while(h!=null) {
        		//처음 넣을경우 
        		/*
        		if (cnt == 0) { 
        			h.next = n;
        			n.prev = h;
        			n.next =tail;
        			tail.prev = n;
        			cnt++;
        			return;
        		}
        		*/
        		
        		//시작 head 가 아니고  
        		if (h.priorty != 9999) {
        			//신규 n이 더 작다면, 
        			if (n.priorty < h.priorty ) {
        				node pre = h.prev;        				
        				
        				pre.next = n;
        				n.prev = pre; 
        				
        				h.prev = n;
        				n.next = h;
        				cnt++;
        				return;
        			}
        			
        		}   
        		last = h;
        		h = h.next;
        	}
        	
        	//여기 코드까지 왔으면, 끝에 붙이기. 어떻게 붙일까.
        	//last node 를 백업으로 가지고 있으면, tail은 필요없게 되는데.
        	last.next = n;
        	n.prev = last;
        	//n.next = tail;
        	//tail.prev = n;
        	
        	
        	/*
        	node pre = tail.prev;
        	pre.next = n;
        	n.prev = pre;
        	
        	n.next = tail;
        	tail.prev = n;
        	cnt++;
        	*/
        }
        
        public void print() {
			node h = head;
			
			while(h!=null){
				
				System.out.print(h.priorty + " -> ");
				
				h = h.next;
			}
			System.out.println();
			
		}	     
        
    }
    
    class node{        
        int priorty; 
        node prev,next;
        node(int i){
        	priorty = i;
        }
    }
    
    
    
}
