package coding1629;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class directory3 {
	
	int MAX = 20;
	node root ;
	
	tree mytree = new tree();
	
	@Test
	public void test(){
		add("/\0","a\0");		
		add("/\0","b\0");
		add("/a\0","a1\0");
		//mytree.print(root, 0);
		
		add("/a\0","a2\0");
		//mytree.print(root, 0);
		add("/a/a2\0","aa0\0");
		add("/a/a2\0","aa1\0");
		add("/a/a2\0","aa2\0");
		
		add("/b\0","b1\0");
		
		mytree.print(root, 0);
		

		// move 		
		move("/a/a2\0","/b/b1\0");
		
		mytree.print(root, 0);
		
		//copy 
		copy("/b/b1/a2\0","/a/a1\0");
		
		mytree.print(root, 0);
		
	}
	
	void add(String ori, String tar) {
		char[] cori = new char[MAX];
		char[] ctar = new char[MAX];
		//char ȭ
		change(ori , tar , cori , ctar);
		
		//ori���(/a) �� tar directory(aa) ���� �Ѵ� 
		// /a �� ��� ���������ΰ� -> / -> a directory -> aa directory �ǹ��̴�
		
		//root �ϰ�� 
		if ( isRoot(cori) ) {
			mytree.add(root , ctar);
		}else{ 
			node f = find(cori);			
			if ( f == null) {
				System.out.println("not found f " + new String(cori));
				return;
			}				
			mytree.add(f,ctar);
			//System.out.println(f.folder);
		}
	}
	
	boolean isRoot(char[] t) {
		for (int i = 0; i < t.length; i++) {
			if (root.folder[i] != t[i] ){ 
				return false;
			}
			
			if (root.folder[i] == '\0' && t[i] =='\0'){
				break;
			}
		}
		return true;
	}
	

	node find(char[] cori) {
		// / �� �����ϱ�
		char[][] paths = new char[MAX][MAX];
		
		paths[0][0] = '/';  //root start 
		int c = 0 ;
		int nameC = 0 ;
		for (int i = 1; i < cori.length; i++) {
			if ( cori[i] =='\0' ) break;
			
			if ( cori[i] =='/' ) {
				c++;
				nameC = 0;
			}else{
				paths[c][nameC++] = cori[i];
			}
		}
		//c exit ���� 
		mytree.f = null; 
		//�� count �̷����� debugging �ϴµ� �ð��� �ɸ�.(�ʱ�ȭ �̷��ſ�...)
		mytree.find(root,paths , 0 , c+1);
		return mytree.f;
	}

	void move(String ori, String tar) {
		char[] cori = new char[MAX];
		char[] ctar = new char[MAX];
		//char ȭ
		change(ori , tar , cori , ctar);
		
		node ori_f = find(cori);
		node tar_f = find(ctar);
		
		if (ori_f != null && tar_f != null) { 
			mytree.move(ori_f , tar_f);
			return;
		}
		System.out.println("we can not move node ");
		return;
	}

	void copy(String ori, String tar) {
		char[] cori = new char[MAX];
		char[] ctar = new char[MAX];
		//char ȭ
		change(ori , tar , cori , ctar);
		node ori_f = find(cori);
		node tar_f = find(ctar);
		if (ori_f != null && tar_f != null) {
			mytree.copy(ori_f,tar_f);
		}
	}
	
	
	
	class tree {
		
		node f = null; 
		
		tree() { 
			char[] r = new char[MAX];
			r[0] = '/';
			r[1] = '\0';
			root = new node(r);
		}
		
		//ori_f ���� tar_f�� child �ٿ��־�� �Ѵ� �� �־�� �Ѵ�.
		//tar_f �� ���� ���� ���ڷ� �ѱ�� , ori_f�� ���氪�� ���ڷ� �ѱ�鼭 ���� ����ϴ� ��� 
		void copy(node ori_f, node tar_f) {
			
			node n = add(tar_f, ori_f.folder);
			
			node h = ori_f.childs.head;
			
			while (h!=null){
				//System.out.println(h.folder +"�� copy�մϴ�" );
				copy(h,n);
				h = h.next;
			}
		}

		void move(node ori_f, node tar_f) {
			node oriP = ori_f.parent;
			oriP.childs.deleteChild(ori_f);
			
			this.print(root, 0);			
			
			tar_f.childs.addChild(ori_f);
		}

		node add(node p, char[] ori) {
			node n = new node(ori);
			n.parent = p;
			link ll = p.childs;
			ll.addChild(n);
			return n;
		}
		
		void print(node r , int depth) {
			if (r == null) return; 
			
			for (int i = 0; i < depth; i++) {
				System.out.print("+");				
			}
			System.out.println(new String(r.folder));
			
			node h = r.childs.head;
			while(h!=null) {
				print(h,depth+1);
				h = h.next;
			}
		}
		
		//paths �� �̰ɷ� �ϴ°�..
		void find(node r , char[][] paths, int start, int exit) {
			
			if (start == exit) {
				f = r;
				return ; 
			}
			
			node h = r.childs.head;
			
			while(h!=null) {
				if ( equal (h.folder , paths[start] ) ) {					
					find(h , paths , start+1 , exit);
					
					//������ �ڵ尡 ����...
				}
				h=h.next;
			}			
			return;
		}

		boolean equal(char[] folder, char[] cs) {
			if (folder.length != cs.length) return false;
			for (int i = 0; i < folder.length; i++) {
				if (folder[i] != cs[i] ){ 
					return false;
				}
				
				if (folder[i] == '\0' && cs[i] =='\0'){
					break;
				}
			}
			
			return true;
		}
			
		
		void move(){
			
		}
		
		void copy(){
			
		}		
	}
	
	class node { 
		node parent;
		node prev,next;
		link childs = new link();
		char[] folder = new char[MAX];
		public node(char[] p) {
			for (int i = 0; i < MAX; i++) {
				folder[i] = p[i];				
			}
		}
	}
	
	class link { 
		node head, tail;		
	
		node addChild(node n) {
			if (head == null) { 
				head = n;
				tail = n;
				
			}else{
				tail.next = n;
				n.prev =tail;
				tail = n;
			}
			return n;
		}

		void deleteChild(node n){
			node h = head;
			while(h!=null) { 
				if ( equal(h.folder , n.folder) ) {
					if (h == head && h == tail) {
						head = null;
						tail = null;
						return;
					}
					
					if (h == head) {
						head = h.next ;
						head.prev = null;
					}else if(h == tail) {
						tail = h.prev;
						tail.next = null;
					}else{
						node pe = h.prev;
						node ne  = h.next;
						pe.next = ne;
						ne.prev = pe;
					}					
					
					break;
				}
				
				h = h.next;
			}
		}
		boolean equal(char[] o , char[] t) { 
			for (int i = 0; i < t.length; i++) {
				if (o[i] != t[i]) return false; 
				if (o[i] =='\0') break;
			}
			return true;
		}
	}
	
	void change(String ori, String tar, char[] cori, char[] ctar) {
		char[] o = ori.toCharArray();
		char[] t = tar.toCharArray();
		
		for (int i = 0; i < o.length; i++) {
			cori[i] = o[i];
		}
		
		for (int i = 0; i < t.length; i++) {
			ctar[i] = t[i];
		}
	}
	

}

