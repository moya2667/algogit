package coding1629;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class directory1 {
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
	 --> �׷���........
	6   �ᱹ \0 ������� ���Ͽ�, ����ġ ���� �ð��� ���� �귶��. (�� �ǹ̴� ���� �Ľ�/ \0������� �ͼ�ġ�ʾƼ�...�ϼ���)
	 --> �׷���........
	7. �� ������ move�� fullpath(unique key)�� �ٲ�� ������, move���� full path�� ��� �ٲ���� �ϴ� �۾��� �ʿ��ϴ�..
	8. copy ���� child ���� �����Ѵٸ�, recursion���� copy ������Ѵ�.
	 
	10. �⺻ TREE �����ϴµ� �ð��� �����ɷȴ�.(�� �׷�����...��� �����ϴ��ǵ�.. ���ڰ��� ��� ���������ϴ��� ��Ȯ���� �ʾƼ�..)
	
	find ����ȭ�� , path���� ã�ư��ų� (1588/6 = depth�� �־��ϰ��, 260���� �����ִ�)
	
	**/
	
	boolean debug = false;
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
		
		//tree.print(tree.root, 0);
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
		
		//root �ϰ�� root parent �� �ְ� ������ /a �� ���� 
		if (isRoot(path.toCharArray())){
			tree.add(tree.root ,cDirectory);
		//root �ƴҰ�� ��� ã�� ,child �� ���   	
		}else{
			tree.fi = null;
			tree.find(tree.root,cPaths);
			tnode f = tree.getfind();
			if (f == null) { 
				System.out.println("can't find parent path");
				return;
			}
			//�θ� f �� ���� ��� ���� -> ���� ��忡�� �����ؼ� �и��ؾ��ϱ���. 
			tree.add(f, cDirectory);
		}
	}
	
	
	boolean isRoot(char[] path){		
		int len = path.length;
		for (int i = 0; i < len ; i++) {
			if ( path[i] != tree.root.dname[i] ){ 
				return false;
			}
			
			if (path[i] == '\0' && tree.root.dname[i] == '\0') break;
		}
		return true;
	}
	
	void move(String path , String path2) { 
		char[] cPath = new char[10];
		char[] cPath2 = new char[10];
		change(path , path2, cPath , cPath2 );
	}
	
	class moyatree {
		
		tnode root;
		public moyatree(){
			//root 
			char[] r = new char[10];
			r[0] ='/';
			r[1] ='\0';		
			root = new tnode(r);
			root.fullpath = r ;
		}
		
		//p�� �ڽĳ�带 �����ϰ�, v �� �Ҵ� 
		tnode add(tnode p , char[] v){
			//child node ���� 
			tnode n = new tnode(p.fullpath , v);			
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
		
		//path �� �߶� ã�� �ڵ尡 �ʿ�
		tnode find(char[] path){
			//for hashing
			//if ( t == null) return null;
			
			return null;
		}
		
		tnode fi = null;
		tnode getfind(){
			return fi;
		}
		
		boolean find(tnode t , char[] path){
			//for hashing			
			if ( t == null) return false;
			
			if (isEqual(t.fullpath , path) ) {				
				fi = t;
				return true;
			}
			
			tnode h = t.cHead;
			while(h!=null) {
				find(h,path);
				if (fi != null) return true;
				h = h.next;
			}
			return false;
		}
				
		
		boolean isEqual(char[] fullpath, char[] path) {
			for (int i = 0; i < fullpath.length; i++) {
				if (fullpath[i] != path[i]) return false;				
				if (fullpath[i]=='\0'&& path[i] =='\0') break;
			}
			return true;
		}

		boolean equalTo(char[] ori , char[] tar) {
			for (int i = 0 ; i < ori.length ; i++) { 
				if (ori[i] != tar[i] ) return false;
			}
			return true;
		}
		
		void print(tnode r, int dep){ 
			if (r == null) { 
				return; 
			}
			for (int i = 0 ; i < dep ; i++) { 
				System.out.print("+");
			}
			System.out.println(new String(r.fullpath));
			
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
		char[] dname;
		char[] fullpath;
		tnode parent;
		tnode cHead,cTail; //TNode child
		tnode next,prev; 
		public tnode(char[] va){
			dname = new char[va.length]; //indexing hashing ? (�̰Ǳ׳�)
			for(int i =0; i < va.length ;i++){
				dname[i]= va[i];
			}
		}
		public tnode(char[] parentPath, char[] directory) {
			fullpath = new char[20];
			dname = new char[directory.length];
			int cnt = 0;
			for (int i = 0; i < parentPath.length; i++) {
				if (parentPath[i] == '\0'){
					if (!isRoot(parentPath)){
						fullpath[cnt++] = '/';
					}
					break;
				}
				fullpath[cnt++]= parentPath[i];				
			}
			
			for (int i = 0; i < directory.length; i++) {
				fullpath[cnt++] = directory[i];
				//name ���� ���� 
				this.dname[i] = directory[i];
				if (directory[i] == '\0') {
					//name�� \0 ��������
					this.dname[i] = directory[i];
					break;
				}
			}
		}
		
		boolean isRoot(char[] parentPath){
			if(parentPath[0] =='/' && parentPath[1]=='\0') return true;
			return false;
		}
		
		boolean equalTo(char[] ori , char[] tar) {
			for (int i = 0 ; i < ori.length ; i++) { 
				if (ori[i] != tar[i] ) return false;
			}
			return true;
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
}
