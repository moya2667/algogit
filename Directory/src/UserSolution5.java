
class UserSolution5 {

	private final static int NAME_MAXLEN = 7;
	private final static int PATH_MAXLEN = 1999;

	int mstrcmp(char[] a, char[] b) {
		int i;
		for (i = 0; a[i] != '\0'; i++) {
			if (a[i] != b[i])
				return a[i] - b[i];
		}
		return a[i] - b[i];
	}

	void mstrcpy(char[] dest, char[] src) {
		int i = 0;
		while (src[i] != '\0') {
			dest[i] = src[i];
			i++;
		}
		dest[i] = src[i];
	}
	
	long getID( char[] p) {
		long id = 0 ; 
		for (int i = 0; i < p.length; i++) {
			if (p[i] == '\0') break;
			id += p[i]-'a'+1;  // id + 이다  
			id = id << 5;
		}
		return id;
	}
	
	long getChar(char[] n , char[] p) {
		long id = 0 ; 
		for (int i = 0; i < p.length; i++) {
			if (p[i] == '\0') break;
			n[i] = p[i];
			id += p[i]-'a'+1;  // id + 이다  
			id = id << 5;
		}
		return id;
	}

	//1. tree 자료 구조 
	//2. node
	//3. 
	class node {
		node parent; 
		char[] n = new char[7]; 
		ll childs = new ll();
		node prev,next;
		long id ; 
		
		void setname(char[] p) { 
			//mstrcpy(n,p);
			id = getChar(n,p);
		}
		
	}
	
	node[] nodes = new node[50000]; //최적화 
	
	class ll { 
		node h ,t ;
		void add(node n){ 
			if (h == null) { 
				h = n;
				t = n; 
			}else{
				t.next = n ; 
				n.prev = t;
				t = n;
			}
		}
		void delete(node n) {
			//TODO 아래 조건은 h / t 이 미리 지정하였을경우 (지정할것인가)
			if (h == n&& t == n) { 
				h = null ; t = null ; return;
			}
			
			if (h == n) { 
				node te = h.next;// point
				h = te;
				h.prev = null;
			}else if (t == n) {
				node te = t.prev;
				t = te;
				te.next = null;
			}else{
				node pre = n.prev;
				node ne = n.next;			
				pre.next = ne ;
				ne.prev = pre;	
			}
			n.prev = null;
			n.next = null;
		}
		node find(char[] f) {
			long id = getID(f);
			node h = this.h;
			while(h!=null) {
				if (compareID(h.id , id)) { 
					return h; 
				}
				h = h.next ;
			}
			return null;
		}
		boolean compareID(long id , long id2) {
			if (id != id2) return false;
			return true;
		}
		
		boolean compare(char[] s , char[] t) {
			if (s.length != t.length) return false;
			for(int i=0 ; i < s.length ; i++) { 
				if ( s[i] != t[i] ) return false; 
			}
			return true;
		}
	}
	class tree {
		node r; 
		tree() {
			char[] root = new char[7];
			root[0] = '/';
			root[1] ='\0';
			r = new node();
			r.setname(root);
		}
		node getroot() { return r; }

		node find ;
		public node find(char[] path) {
			//path = /a/b/
			if (path[0] =='/' && path[1] =='\0') return this.getroot();
			find = null; 
			myfind( r , 1,  path);
			return find;
		}
		void myfind( node r , int s , char[] path) { 
			//path 추출
			char[] t = new char[7];
			int c = 0;
			int i = 0;
			for (i = s; i < s+7; i++) {
				if(path[i] == '/' || path[i] == '\0') { 
					break;
				}
				t[c++] = path[i];
			}
			
			//node childs 검색해야하기 때문에 s index를 1부터 시작 . r = root / s = 1
			node f = r.childs.find(t);
			if (f!=null) { //  null 을 없애고 간단하게
				find = f; 
				myfind(f , i+1, path );
			}
		}
		
		
		public node mkdir(node f, char[] name) {
			node n = new node();
			n.setname(name);
			n.parent = f;
			f.childs.add(n);
			return n;
		}
		public void rm(node f) {
			// TODO Auto-generated method stub
			node p = f.parent;
			p.childs.delete(f);
		}
		
		void print(node r , int s) { 
			for (int i = 0; i < s; i++) {
				System.out.print("+");
			}
			print(r.n);
			
			node h = r.childs.h;
			while(h!=null) {
				print(h,s+1);
				h= h.next;
			}
		}
		void print(char[] t){
			for (int i = 0; i < t.length; i++) {
				if(t[i]=='\0') break;
				System.out.print(t[i]);
			}
			System.out.println();
		}
		public void mv(node s, node d) {
			// TODO Auto-generated method stub
			node sp = s.parent;
			sp.childs.delete(s); // 부모로부터 삭제 
			
			s.parent = d; //새로운부모 등록
			d.childs.add(s); // 붙이기 
		}
		//dstPath[] 디렉터리의 하위에 srcPath[] 의 디렉터리와 그 모든 하위 디렉터리를 복사한다
		public void cp(node s, node d) {
			// TODO Auto-generated method stub
			if (s == null )return; 
			
			node t = this.mkdir(d, s.n);
			
			node h = s.childs.h;
			while(h!=null) {
				cp(h , t);
				h = h.next; 
			}
		}
		int findC = 0;
		public int findc(node s) {
			findC = 0 ;
			findrec(s);
			return findC;
		}
		
		void findrec(node s) {
			if ( s == null )  return;
			
			node h = s.childs.h;
			while(h!=null) {
				findC++;
				findrec(h);
				h = h.next; 
			}
		}
	}
	
	tree mytree;
	
	void init(int n) {
		mytree = new tree();
		/*
		for (int i = 0; i < n ; i++) {
			nodes[i] = new node();
		}
		*/
	}

	
	void cmd_mkdir(char[] path, char[] name) {
		//System.out.println("cmd_mkdir");
		//print(path , name);
		node f = mytree.find(path);
		mytree.mkdir(f , name);
		//mytree.print(mytree.getroot(), 0);
	}

	void cmd_rm(char[] path) {
		//System.out.println("cmd_rm : " + new String(path));
		
		node f = mytree.find(path);
		mytree.rm(f);
		//mytree.print(mytree.getroot(), 0);
	}

	void cmd_cp(char[] srcPath, char[] dstPath) {
		//System.out.println("cmd_cp");
		node s = mytree.find(srcPath);
		node d = mytree.find(dstPath);
		mytree.cp(s,d);
	}

	void cmd_mv(char[] srcPath, char[] dstPath) {
		//System.out.print("cmd_mv :");
		node s = mytree.find(srcPath);
		node d = mytree.find(dstPath);
		mytree.mv(s,d);
		
		//mytree.print(mytree.getroot(),0);
		
	}
	
	void print(char[] s , char[] t) { 
		for (int i = 0; i < s.length; i++) {
			if ( s[i] =='\0' ) break; 
			System.out.print(s[i]);
		}
		for (int i = 0; i < t.length; i++) {
			if ( t[i] =='\0' ) break; 
			System.out.print(t[i]);
		}
		
		System.out.println();
	}

	int cmd_find(char[] path) {
		//System.out.println("cmd_find " + new String(path));
		node s = mytree.find(path);
		return mytree.findc(s);
		
	}
}
