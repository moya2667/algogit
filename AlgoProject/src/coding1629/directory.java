package coding1629;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class directory {
	/*
	 * 
	 *���丮 ����/����/�ű�� ��� 
	 * 
	 * 
	1. char[] �迭�� parsing �� ������ ���� �ʾҴ�. �ͼ�ġ �ʴ�
	2. link �̵� / ���� (�޸𸮺���) ���� �ͼ�ġ �ʾҴ� 
	3. ���ٹ���� Ʋ�ȴ�. (�������� ���ϴ� �κ����� �����߾��..) 
	4. "/aa/bb\0" "dd\0" -> full path�� �־��µ�, �� �ڸ��� �ʾҳ�..-find�� unique key(���� �ٷ� �����ҷ���-hash)�� �̿��ؾ��Ѵ� �����ߴ�.
	    (find�������ҷ���)
	5. 4�� �������� ������ /aa/bb/dd\0 ���� �������..(�����غ���. /aa/bb/dd\0 �� parsing�ϴ°� ������..��� �����ϴٺ��� �˰ԵȰſ���.)
	6   �ᱹ \0 ������� ���Ͽ�, ����ġ ���� �ð��� ���� �귶��. (�� �ǹ̴� ���� �Ľ�/ \0������� �ͼ�ġ�ʾƼ�...�ϼ���)
	7. �� ������ move�� fullpath(unique key)�� �ٲ�� ������, move���� full path�� ��� �ٲ���� �ϴ� �۾��� �ʿ��ϴ�..
	8. copy ���� child ���� �����Ѵٸ�, recursion���� copy ������Ѵ�.
	 
	10. �⺻ TREE �����ϴµ� �ð��� �����ɷȴ�.(�� �׷�����...��� �����ϴ��ǵ�.. ���ڰ��� ��� ���������ϴ��� ��Ȯ���� �ʾƼ�..)
	
	find ����ȭ�� , path���� ã�ư��ų� (1588/6 = depth�� �־��ϰ��, 260���� �����ִ�)
	
	**/
	
	boolean debug = true;
	moyatree tree ;	
	void init(){
		tree = new moyatree();		
	}
	@Test
	public void test() {
		init();
		// /a path �� a1 directory ����
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
		
		//root �ϰ�� 
		if (isRoot(cPaths)){
			tree.add(tree.root ,cDirectory);
		//root �ƴҰ�� ��� ã�� ,child �� ���   	
		}else{
			tnode f = tree.find(cPaths);
			//f�� null�� ��� , �θ� ��带 �˰� , �����ؾ��Ѵ�. ?????
			
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
		
		//p�� �ڽĳ�带 �����ϰ�, v �� �Ҵ� 
		tnode add(tnode p , char[] v){
			//child node ���� 
			tnode n = new tnode(v);			
			//p node�� child �� ����
			n.parent = p ;
			
			if (p.cHead == null) {
				p.cHead = n;
				p.cTail = n;
			}else{
				//���Ⱑ �� �ر򸮳�.
				p.cTail.next = n; 
				n.prev = p.cTail; 
				p.cTail = n;
			}
			hash.put(v, n);
			return n;
		}
		//v�� �ش��ϴ� �� ã�Ƽ�, ���� 
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
			
			//child �����Ѵٸ� 
			tnode t = r.cHead;
			while (t!= null) {
				//child�� ���� 
				print(t , dep+1);
				//childs �� �ִٸ� 
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
			v = new char[va.length]; //indexing hashing ? (�̰Ǳ׳�)
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
			//�̰� �ܼ��� �迭List�� �����Ѱű���.
			list = new ItemList[capa];			
			for (int i = 0; i < list.length; i++) {
				//���⼭ �����ϴ°�.
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
			tail = v; //�� ���ƹ����ڳ�.
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
