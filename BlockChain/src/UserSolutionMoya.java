import java.util.HashMap;
import java.util.Iterator;

class UserSolutionMoya {
	// Main API :
	// Solution.calcHash(char buf[], int pos, int len)

	private final static int MAXSERVER = 5;
	private final static int IMAGESZE = 400000;
	private final static int MAXSIZE 	 = 16;
	
	HashMap myhash = null;
	block root = null;
	//hash // block // etc
	void init(){ 
		myhash = new HashMap<>();
		root = null; 
	}
	class transaction { 
		int exchangedid;
		int amount;
	}
	class ll { 
		block head , tail;
		void addchild(block b) { 
			if (head == null) { 
				head = b;
				tail = b;
			}else{
				tail.next = b;
				b.prev = tail;
				tail = b;
			}
		}
	}
	class block {
		int hashpreblock_parent = 0 ;
		int random =0;
		int tranN = 0 ;
		transaction[] translist ;
		int hashkey;
		int hit =0;
		
		//manage child 
		ll childlist;
		block prev,next;
		
		block() {
			translist = new transaction[16];
			childlist = new ll();
			for (int i =0 ; i < 16 ; i++) {
				translist[i] = new transaction();
				translist[i].amount = -1 ; 
				translist[i].exchangedid = -1;
			}
		}
		
		public int addInfo(char[] blockchainimage, int s , int blength) {
			hashpreblock_parent = blockchainimage[s++]*16777216 + blockchainimage[s++]*65536 +  blockchainimage[s++]*256 + blockchainimage[s++];
			random = blockchainimage[s++]*256 + blockchainimage[s++];
			tranN = blockchainimage[s++];
			
			
			//반복
			for (int i = 0 ; i < tranN ; i++) { 
				int exchangid = blockchainimage[s++];
				translist[i].exchangedid =  exchangid;
				//int amount = (blockchainimage[s++]<<8) + blockchainimage[s++];
				//3212
				translist[i].amount  = blockchainimage[s++]*256 + blockchainimage[s++];
			}
			
			return s;
		}
		
	}
	
	public void syncBlockChain(int S, char blockchainimage[][]) {
	
		init();
		//System.out.println("S = " + S);
		for (int i = 0; i < S; i++) {
			//block parsing
			parser( blockchainimage[i] ) ;
		}

		//등록된 hash 값가져와서 tree 구성 -> root를 가져왔으니, tree 구성가능 
		Iterator iter = myhash.keySet().iterator();
		
		int bias = S/2+1; //BUG
		while(iter.hasNext()) { //CHNAGE MYHASH->NEXT CNT
			Object key = iter.next();
			//System.out.println(Integer.toHexString((int)key));
			block b = (block)myhash.get(key);
			if (b.hashpreblock_parent == 0) { 
				continue;
			}else if(b.hit >= bias){				
				block t = (block)myhash.get(b.hashpreblock_parent);
				t.childlist.addchild(b);
			}
		}
		//print(root,0);
	}	
	
	void parser(char[] blockchainimage){
		
		int blength= blockchainimage[0]*16777216 + blockchainimage[1]*65536 +  blockchainimage[2]*256 + blockchainimage[3];
		int pos = 4 ;
		//
		while(blength > pos) {  //difficult 
			block b = new block();
			int len = b.addInfo(blockchainimage ,pos , blength);
			
			int hash = Solution.calcHash(blockchainimage, pos, len-pos);
			b.hashkey = hash;
			if (b.hashpreblock_parent == 0 ) { 
				root = b;
			}				
				
			//System.out.println("added hash " + Integer.toHexString(hash)  );
			block hb = (block)myhash.get(hash);
			if (hb == null){
				b.hit++;
				myhash.put(hash , b);
			}else{
				hb.hit++;
			}
			
			pos = len; 
		}		
	}
	
	public int calcAmount(int hash, int exchangeid) {
		//System.out.println("calcAmount hash : " + hash);		
		block b = (block)myhash.get(hash);
		//print(root,0);
		total= 0; 
		gettotal(b, exchangeid); 
		return total;
	}
	
	int total = 0 ; 
	
	void gettotal(block b , int exchangeid) { 
		if ( b == null) return; 
		int n = b.tranN;
		for (int i = 0 ; i < n ; i++) { 
			if ( b.translist[i].exchangedid == exchangeid) {
				total = total+ b.translist[i].amount;				
			}
		}
		
		block h = b.childlist.head;
		while(h!= null) { 
			gettotal(h , exchangeid);
			h = h.next;
		}
	}
	
	void print(block b , int cnt) { 
		if ( b == null) return; 
		
		for (int i = 0 ; i < cnt ; i++) { 
			System.out.print("+" );
		}
		System.out.print(Integer.toHexString(b.hashkey));
		System.out.println();
		
		block h = b.childlist.head;
		while(h!= null) { 
			print(h , cnt+1);
			h = h.next;
		}
	}
}
