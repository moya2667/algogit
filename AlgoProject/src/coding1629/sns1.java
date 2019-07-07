package coding1629;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class sns1 {

    user[] users = new user[100];;
    String[] message = new String[100];
    hash myhash = null;
    void init() {
        myhash = new hash(1000);
        int time = 11;
        
        for (int i = 1; i < 100; i++) {
            message[i]= String.valueOf(i);
        }
                
        for (int i = 1; i < 100; i++) {
            users[i] = new user(time++ , i , message[i].toCharArray());             
        }
    }
    

	@Test
	public void test() {
        init();
        follow(1,2);
        follow(1,3);
        follow(2,1);
        
        followprint(1);
        //followprint(2);
        
        addmessage(88,"113".toCharArray());
        addmessage(1,"113".toCharArray());
        addmessage(2,"200".toCharArray());
        addmessage(3,"113".toCharArray());
        addmessage(4,"113".toCharArray());
        addmessage(5,"113".toCharArray());
        addmessage(6,"200".toCharArray());
        
        //본인 포함 / follow 들 message까지 
        messageprint(1);
     
        findSearchbyTag("113");        
        findSearchbyTag("200");
	}

	void getStoChar(String s, char[] msg) {
		for (int i = 0; i < s.length(); i++) {
			msg[i] = s.charAt(i);
		}
	}

	void addmessage(int id, char[] msg) {
		users[id].addmessage(msg);
	}

	void messageprint(int id) {
		System.out.println("------message-------");
		users[id].printmessage();

		user followh = users[id].followll.headfollow;
		while (followh != null) {
			// System.out.println("id = " + followh.id );
			users[followh.id].printmessage();
			followh = followh.next;
		}
		System.out.println("-----------------");

	}

	void follow(int id, int id2) {
		users[id].followll.add(users[id2]);
	}

	void followprint(int id) {
		user h = users[id].followll.headfollow;
		while (h != null) {
			System.out.println("id = " + h.id + " time = " + h.time);
			h = h.next;
		}
	}

	void findSearchbyTag(String tag) {
		char[] msg = new char[10];
        getStoChar(tag, msg);        
        
		tag r = myhash.get(msg);

		tagid h = r.idlist.idhead.next;

		System.out.println(new String(r.message) + "에 연결된 id = "  );
		while (h != null) {
			System.out.print(h.id + " ");
			h= h.next;
		}
		System.out.println();

	}
	
    class user {
        user next , prev;
        ll followll;
        ll messagell; 
        int time , id ;
        user (int time,  int id , char[] msg) { 
            this.time = time;
            this.id = id;
            
            followll = new ll();
            messagell = new ll();            
            //tag 존재하는 checking 
            tag t =new tag(this.id,msg);
            messagell.addmessage(t);
            myhash.put(t);
        }
        
        public void printmessage() {
            tag h = messagell.htag;
            while(h!=null) { 
                System.out.println("id = " + h.id  + " message = " + new String(h.message));
                h = h.next;
            }
            
        }

        void addmessage(char[] msg) {
            tag t =new tag(this.id,msg);
            messagell.addmessage(t);
            myhash.put(t);
        }
    }
    class tagid {
    	tagid prev,next;
    	int id; 
    	tagid(int id) { 
    		this.id = id;
    	}
    }
    
    class tag { 
    	int id;
        tag prev ,next;
        char[] message = new char[10]; 
        ll idlist = new ll();
        tag (int id , char[] msg) {
        	this.id = id;
            for (int i = 0; i < msg.length; i++) {
                message[i] = msg[i];
            }            
            idlist.addsortId(id);
        }
    }    

    
    class ll { 
        user headfollow, tailfollow; 
        tag htag , ttag;
        
        tagid idhead, idtail; 
        
        void addsortId(int id) {
        	tagid tagid = new tagid(id);
            if (idhead == null) {
            	tagid init = new tagid(9999);
            	idhead = init ;
                idtail = init ;
            }
            
            tagid start = idhead.next;
            tagid last = idhead;
            while(start != null)  {
            	
                if( start.id < tagid.id ) {
                    tagid prev = start.prev;
                    prev.next = tagid;
                    tagid.prev = prev;
                    tagid.next = start;
                    start.prev = tagid;
                    return;
                }
                
                last = start; 
                start = start.next;
            }
            
            last.next = tagid;
            tagid.prev = last;
            
        }
        
        void addmessage(tag msg) {   
            if (htag == null ) { 
                htag = msg;
                ttag = msg;
            }else {
                ttag.next = msg;
                msg.prev = ttag;
                ttag = msg;
            }
        }
        
        
        void add(user n) { 
            if (headfollow == null) { 
                headfollow = n; 
                tailfollow = n; 
            }else {
                tailfollow.next = n ; 
                n.prev =tailfollow; 
                tailfollow = n;
            }
        }
    }
    
    class hash { 
        int capa; 
        hashlist[] list = null; 
        public hash(int capa) { 
            this.capa = capa;
            list = new hashlist[capa]; 
            for (int i = 0; i < capa; i++) {
                list[i] = new hashlist();
            }
        }
        
        int hash(char[] str)
        {
            int hash = 5381;
            int len = str.length;
            for (int i = 0; i < len; i++)
            {
                int c = (int)str[i];
                hash = ((hash << 5) + hash) + c + ((i*i)+i+41);
            }
            if (hash < 0) hash *= -1;
            return hash % capa;
        }
        
        void put(tag msg) {
            int key = hash(msg.message);             
            list[key].put(msg);
        }
        
        tag get(char[] msg) { 
            int key = hash(msg);
            return list[key].get(msg);
        }
    }
    
    class hashlist { 
        
        tag head , tail;
        
        public void put(tag msg) {
            //hmm.
            if (head == null) { 
                head = msg; 
                tail = msg; 
            }else { 
                //key search 
                tag h = head;
                while( h!= null) { 
                    //if key naming is same, add link as insert sort
                    if ( equal(h.message , msg.message) ) {
                        h.idlist.addsortId(msg.id);
                        return;
                    }
                    h= h.next;
                }
               
            }
        }
        
        public tag get(char[] message) {
            tag h = head;
            while( h!= null) { 
                //if key naming is same, add link
                if ( equal(h.message , message) ) { 
                   return h;
                }
                h= h.next;
            }
            return null;
        }
        
        boolean equal(char[] a ,char[] b ) { 
            int len = a.length;
            for (int i = 0; i < len; i++)  {
                if ( a[i] != b[i] ) return false; 
            }
            return true;
        }
    }	

}
