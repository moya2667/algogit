package coding1629;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class directory {
	/*
	 * 
	 *디렉토리 생성/삭제/옮기기 기능 
	 * 
	 * 
	1. char[] 배열을 parsing 할 생각이 나지 않았다. 익숙치 않다
	2. link 이동 / 복사 (메모리복사) 등이 익숙치 않았다 
	3. 접근방법이 틀렸다. (문제에서 요하는 부분으로 접근했어야..) 
	4. "/aa/bb\0" "dd\0" -> full path을 주었는데, 왜 자르지 않았나..-find를 unique key(나중 바로 접근할려고-hash)를 이용해야한다 생각했다.
	    (find빠르게할려고)
	5. 4번 생각으로 억지로 /aa/bb/dd\0 으로 만들었다..(생각해보라. /aa/bb/dd\0 로 parsing하는게 쉬운지..허긴 구현하다보니 알게된거였다.)
	6   결국 \0 고려하지 못하여, 예상치 못한 시간이 많이 흘렀다. (그 의미는 문자 파싱/ \0고려등이 익숙치않아서...일수도)
	7. 위 로직은 move시 fullpath(unique key)가 바뀌기 때문에, move하위 full path도 모두 바꿔줘야 하는 작업이 필요하다..
	8. copy 또한 child 까지 존재한다면, recursion으로 copy 해줘야한다.
	 
	10. 기본 TREE 구현하는데 시간이 오래걸렸다.(왜 그랬을까...계속 연습하던건데.. 인자값을 어떻게 가져가야하는지 명확하지 않아서..)
	
	find 최적화냐 , path별로 찾아갈거냐 (1588/6 = depth가 최악일경우, 260까지 갈수있다)
	
	**/
	
	boolean debug = true;
	moyatree tree ;	
	void init(){
		tree = new moyatree();		
	}
	@Test
	public void test() {
		init();
		// /a path 에 a1 directory 생성
		add("/\0","a\0");
		add("/\0","b\0");
		
		add("/a\0","a1\0");
		add("/a\0","a2\0");
		add("/a/a2\0","aa1\0");
		add("/b\0","b1\0");
		
		tree.print(tree.root, 0);
		/*
		// move 		
		move("/a/a2\0","/b/");
		// copy
		copy("/b/a2\0","/a1");
		*/
		
		// count
		
	}
	
	 void copy(String path, String path2) {
		char[] cPath = new char[10];
		char[] cPath2 = new char[10];
		change(path , path2, cPath , cPath2);
		//tnode f = tree.find(cPath);
		//tree.add(f, cPath2);
	}

	void add(String path , String directory) {
		char[] cPaths = new char[10];
		char[] cDirectory = new char[10];		
		change(path , directory , cPaths , cDirectory );
		
		//root 일경우 
		if (isRoot(cPaths)){
			tree.add(tree.root ,cDirectory);
		//root 아닐경우 노드 찾고 ,child 로 등록   	
		}else{
			tnode f = tree.find(cPaths);
			//f가 null일 경우 , 부모 노드를 알고 , 생성해야한다. ?????
			
			tree.add(f, cDirectory);
		}
		
	}
	boolean isRoot(char[] path){		
		int len = path.length;
		for (int i = 0; i < len ; i++) {
			if ( path[i] != tree.root.v[i] ){ 
				return false;
			}
			
			if (path[i] == '\0' && tree.root.v[i] == '\0') break;
		}
		return true;
	}
	
	void move(String path , String path2) { 
		char[] cPath = new char[10];
		char[] cPath2 = new char[10];
		change(path , path2, cPath , cPath2 );
	}
	
	class moyatree {
		MOYAHash hash;
		tnode root;
		public moyatree(){			
			hash = new MOYAHash(71993);			
			//root 
			char[] r = new char[2];
			r[0] ='/';
			r[1] ='\0';		
			root = new tnode(r);
			hash.put(r, root);
		}
		
		//p의 자식노드를 생성하고, v 값 할당 
		tnode add(tnode p , char[] v){
			//child node 생성 
			tnode n = new tnode(v);			
			//p node의 child 로 지정
			n.parent = p ;
			
			if (p.cHead == null) {
				p.cHead = n;
				p.cTail = n;
			}else{
				//보기가 더 해깔리네.
				p.cTail.next = n; 
				n.prev = p.cTail; 
				p.cTail = n;
			}
			hash.put(v, n);
			return n;
		}
		//v에 해당하는 값 찾아서, 삭제 
		void delete(char[] v){
			if (find(v)!=null ){ 
				System.out.println("find node and delete node : " + new String(v) );
			}
			System.out.println("failed to find : " + new String(v));
			return;
		}
		
		tnode find(char[] path){
			//hashing			
			return hash.get(path);
		}
		
		void print(tnode r, int dep){ 
			if (r == null) { 
				return; 
			}
			for (int i = 0 ; i < dep ; i++) { 
				System.out.print("+");
			}
			System.out.println(new String(r.v));
			
			if (r.cHead == null) return; 
			
			//child 존재한다면 
			tnode t = r.cHead;
			while (t!= null) {
				//child로 들어가기 
				print(t , dep+1);
				//childs 도 있다면 
				t = t.next;
			}	
		
		}
	}
	class tnode{
		char[] v;
		tnode parent;
		tnode cHead,cTail; //TNode child
		tnode next,prev; 
		public tnode(char[] va){
			v = new char[va.length]; //indexing hashing ? (이건그냥)
			for(int i =0; i < va.length ;i++){
				v[i]= va[i];
			}
		}		
	}	

	void change(String ori , String ori2, char[] a , char[] b) { 
		char[] o = ori.toCharArray();
		char[] o1 = ori2.toCharArray();
		for (int i = 0; i < o.length; i++) {
			a[i] = o[i];
		}
		for (int i = 0; i < o1.length; i++) {
			b[i] = o1[i];
		}
		if (debug) { 
			//debugging
			for (int i = 0; i < o.length; i++) {
				System.out.print(a[i]);
				if (a[i] == '\0') {
					System.out.println();
					break;
				}
			}
			
			for (int i = 0; i < o.length; i++) {
				System.out.print(b[i]);
				if (b[i] == '\0') {
					System.out.println();
					break;
				}
			}
		
		}
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
		void put(char[] charArray , tnode v) {
			int h = getHash(charArray);
			list[h].put(charArray ,v);
		}

		void delete(char[] charArray) {
			int h = getHash(charArray);			
			//list[h].delete(charArray);
		}

		/*
		void update(char[] charArray, int i) {
			int h = getHash(charArray);
			if ( list[h].update(charArray , i) ){
				System.out.println("Update : " + i);
				return;
			}
			System.out.println("Update Success");
		}
		*/
		

		/*
		void print() {
			for (int i = 0; i < capacity; i++) {
				ItemList l = list[i];
				tnode h = l.head;
				if (h !=null) {					
					System.out.println("ItemList : "  + i);
					while( h != null ) {
						if ( h.deleteF == false ){
							//System.out.println("["+new String(h.d) +"]" + h.v);
						}
						h = h.next;
					}
				}
			}
		}
		*/

		tnode get(char[] charArray) {
			int h = getHash(charArray);
			return list[h].get(charArray);
		}

	}

	class ItemList {
		tnode head, tail;

		void put(char[] p , tnode v) {			
			if (head ==null){
				head = v;
				tail = v;
				return;
			}
			tail.next = v;
			v.prev = tail;
			tail = v; //아 돌아버리겠네.
		}		


		public tnode get(char[] charArray) {
			tnode h = head;
			while( h != null) {
				if (equalto(charArray, h.v)) {
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
