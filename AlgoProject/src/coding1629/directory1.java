package coding1629;

import static org.junit.Assert.*;

import java.io.ObjectInputStream.GetField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class directory1 {
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
	 --> 그러나........
	6   결국 \0 고려하지 못하여, 예상치 못한 시간이 많이 흘렀다. (그 의미는 문자 파싱/ \0고려등이 익숙치않아서...일수도)
	 --> 그러나........
	7. 위 로직은 move시 fullpath(unique key)가 바뀌기 때문에, move하위 full path도 모두 바꿔줘야 하는 작업이 필요하다..
	8. copy 또한 child 까지 존재한다면, recursion으로 copy 해줘야한다.
	 
	10. 기본 TREE 구현하는데 시간이 오래걸렸다.(왜 그랬을까...계속 연습하던건데.. 인자값을 어떻게 가져가야하는지 명확하지 않아서..)
	
	find 최적화냐 , path별로 찾아갈거냐 (1588/6 = depth가 최악일경우, 260까지 갈수있다)
	
	**/
	
	boolean debug = false;
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
		add("/a/a2\0","aa0\0");
		add("/a/a2\0","aa1\0");
		add("/a/a2\0","aa2\0");
		
		add("/b\0","b1\0");
		tree.print(tree.root, 0);
		
		// move 		
		//move("/a\0","/b/b1\0");
		//tree.print(tree.root, 0);
		
	}
	
	 void copy(String path, String path2) {
		System.out.println("copy ~~ ");
		char[] cPath = new char[20];
		char[] cPath2 = new char[20];
		change(path , path2, cPath , cPath2);
		
		tnode ori = tree.getFind(cPath);
		tnode target = tree.getFind(cPath2);
		
		//ori 노드를 target 노드로 복사한다.
		copyRecursion(target , ori);

	}
	 
	 void move(String path , String path2) { 
		System.out.println("move");
		 
		char[] cPath = new char[20];
		char[] cPath2 = new char[20];
		change(path , path2, cPath , cPath2 );
		
		tnode ori = tree.getFind(cPath);
		tnode target = tree.getFind(cPath2);
		
		//target 을 ori의 child 에 넣어라.
		tree.move(ori , target);		
	}
	 
	 
	void copyRecursion(tnode t , tnode ori) { 
		
		if (t == null )return;
		
		//null 이 아니면 붙여넣는다.
		//tnode n = add( new String(t.fullpath) , new String(ori.dname) );
		tnode n = tree.add(t, ori.dname);
		
		//복사할 대상에 child가 있다면, child 도 넣는다.
		tnode o = ori.cHead;
		
		//child 노드가 존재한다면 
		while(o!=null) {
			//복사된 노드를 찾아서, 그 하위에 넣는다.
			//tnode f = tree.getFind(n.fullpath);
			copyRecursion(n,o);
			
			System.out.println("부모 = " + new String(ori.dname) + "자식 = " + new String(o.dname) );
			o = o.next;
		}
	}

	tnode add(String path , String directory) {
		char[] cPaths = new char[20];
		char[] cDirectory = new char[20];		
		change(path , directory , cPaths , cDirectory );		
		
		//root 일경우 root parent 를 넣고 하위에 /a 값 생성 
		if (isRoot(path.toCharArray())){
			return tree.add(tree.root ,cDirectory);
		//root 아닐경우 노드 찾고 ,child 로 등록   	
		}else{
			
			tnode f = tree.getFind(cPaths);
			
			if (f == null) { 
				System.out.println("can't find parent path");
				return null;
			}
			//부모 f 에 하위 노드 생성 -> 아하 노드에서 생성해서 분리해야하구나. 
			return tree.add(f, cDirectory);
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
	
	
	
	class moyatree {
		
		tnode root;
		public moyatree(){
			//root 
			char[] r = new char[20];
			r[0] ='/';
			r[1] ='\0';		
			root = new tnode(r);
			root.fullpath = r ;
		}
		
		public tnode getFind(char[] cPath) {			
			fi = null;
			char[][] map = new char[10][10];
			int cnt = 1 ;
			int dataC = 0 ;
			for (int i = 1; i < cPath.length; i++) {
				if( cPath[i] =='\0')
					break;
					
				if ( cPath[i] =='/'){
					cnt++;
					dataC=0;									 
				}else{
					map[cnt][dataC++] = cPath[i];
				}
			}
			
			findTree( root, map , cnt , 0);
			return fi;
		}

		//p의 자식노드를 생성하고, v 값 할당 
		tnode add(tnode p , char[] v){
			//child node 생성 
			tnode n = new tnode(p.fullpath , v);			
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
			return n;
		}
		
		//or 노드 하위에 t 노드를  붙인다  
		tnode move(tnode t , tnode or){			
			
			//t의 부모 노드에서 child 제거
			tnode pH = t.parent.cHead;
			tnode head = t.parent.cHead;
			tnode tail = t.parent.cTail;
			while(pH!=null) {
				//같으면, 링크를 전달한다.
				if (isEqual(pH.dname, t.dname)){
					//하나일경우 
					if (pH == head && pH == tail) { 
						
						t.parent.cHead = null;
						print(tree.root, 0);
						break;
					}
					
					if (pH == head) {
						t.parent.cHead = pH.next ;
						pH.prev = null;						
					}else if (pH == tail) {						
						t.parent.cTail = pH.prev; 
						t.parent.cTail.next = null;						
					}else{
					
						tnode pe = pH.prev;
						tnode ne = pH.next;
						pe.next = ne;
						ne.prev = pe;
					}
					break;
				}
				
				pH = pH.next;
			}
			
			//or 하위에 t node 붙이기
			t.parent = or ;
			//양쪽 끈기 
			t.next =  null;
			t.prev =  null;
			
			if (or.cHead == null) {
				or.cHead = t;
				or.cTail = t;
			}else{
				//보기가 더 해깔리네.
				or.cTail.next = t; 
				t.prev = or.cTail; 
				or.cTail = t;
			}						
			return or;
		}

		//v에 해당하는 값 찾아서, 삭제 
		void delete(char[] v){
			if (find(v)!=null ){ 
				System.out.println("find node and delete node : " + new String(v) );
			}
			System.out.println("failed to find : " + new String(v));
			return;
		}
		
		//path 를 잘라서 찾는 코드가 필요
		tnode find(char[] path){
			//for hashing
			//if ( t == null) return null;
			
			return null;
		}
		
		tnode fi = null;
		tnode getfind(){
			return fi;
		}
		
		void findTree(tnode t , char[][] path , int exit , int depth ){
			//for hashing			
			if ( t == null) return;
			
			if (depth == exit ) {
				fi = t;
				return ; 
			}
			
			tnode child = t.cHead;
			
			while(child != null) { 
				if ( isEqual(child.dname , path[depth+1] )) {
					findTree(child , path , exit , depth+1);
					return;
				}				
				child = child.next;
			}
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
			System.out.println(new String(r.dname));
			
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
		char[] dname;
		char[] fullpath;
		tnode parent;
		tnode cHead,cTail; //TNode child
		tnode next,prev; 
		public tnode(char[] va){
			dname = new char[va.length]; //indexing hashing ? (이건그냥)
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
				//name 따로 저장 
				this.dname[i] = directory[i];
				if (directory[i] == '\0') {
					//name은 \0 까지저장
					this.dname[i] = directory[i];
					break;
				}
			}
		}
		
		void setFullPath(tnode p , char[] directory) {
			fullpath = new char[20]; //init
			char[] parentPath = p.fullpath;
			
			int cnt =0 ;
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
				//name 따로 저장 
				this.dname[i] = directory[i];
				if (directory[i] == '\0') {
					//name은 \0 까지저장
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
