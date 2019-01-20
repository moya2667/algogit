package coding1318;

import java.util.HashMap;

import org.junit.Test;

public class sortlink {

    HashMap map = new HashMap();
    
    //SORT�� Linklist �� ������, ���ذ��� �ϳ����� �Ѵ� (�ȱ׷����, �ڵ尡 �ʹ� ����������)
    //TAIL �� ���ְ�, HEAD�� ���� �̸� �����ϴ� ������� �غ��Ҵ�
    //Last NODE�� ������ �ִٰ�, Last���� �ڵ尡 �´ٸ�, �ٷ� �ڿ��ٰ� ���δ�. 
    //�� �ڵ尡 �����Ұ��, �ڵ尡 ����������.
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
            //n prioirty �������� ������� ����        	
        	if( head ==null){
        		node t = new node(9999); 
        		head = t;  
        	}
        	
        	node h = head;
        	
        	while(h!=null) {        		
        		//���� head �� �ƴϰ�  
        		if (h.priorty != 9999) {
        			//�ű� n�� �� �۴ٸ�, 
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
        	
        	//���� �ڵ���� ������, ���� ���̱�. ��� ���ϱ�.
        	//last node �� ������� ������ ������, tail�� �ʿ���� �Ǵµ�.
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
            //n prioirty �������� ������� ����        	
        	if( head ==null){
        		node t = new node(9999);
        		//node t1 = new node(9998);
        		head = t;
        		//tail = t1;
        	}
        	
        	node h = head;
        	
        	while(h!=null) {
        		//ó�� ������� 
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
        		
        		//���� head �� �ƴϰ�  
        		if (h.priorty != 9999) {
        			//�ű� n�� �� �۴ٸ�, 
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
        	
        	//���� �ڵ���� ������, ���� ���̱�. ��� ���ϱ�.
        	//last node �� ������� ������ ������, tail�� �ʿ���� �Ǵµ�.
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
