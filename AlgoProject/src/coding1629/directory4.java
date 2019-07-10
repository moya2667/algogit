package coding1629;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class directory4 {
	int MAX = 10;
	tree mytree = new tree();
	@Test
	public void test() {
		add("/\0", "a\0");
		add("/\0", "b\0");
		add("/a\0", "a1\0");
		//mytree.print(mytree.root, 0);

		add("/a\0", "a2\0");
		// mytree.print(root, 0);
		add("/a/a2\0", "aa0\0");
		add("/a/a2\0", "aa1\0");
		add("/a/a2\0", "aa2\0");

		add("/b\0", "b1\0");

		mytree.print(mytree.root, 0);
		
		add("/a/a2/a3\0" ,"cc\0");
	}

	
	void add(String p, String c) {
		char[] pa_ = new char[MAX];
		char[] ch_ = new char[MAX];
		change(p,c, pa_ ,ch_);
		
		node pNode = null; 
		if (isRoot(pa_)){
			pNode = mytree.root;
		}else{
			pNode = mytree.find(pa_);
		}
		
		if (pNode  == null) { 
			System.out.println("not found : " + new String(pa_));
			return;
		}
		mytree.add(pNode , ch_);
		
	}
	
	boolean isRoot(char[] d) { 
		if (d[0]=='/' &&d[1]=='\0') return true;
		return false;
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
	
	class node { 
		char[] v ;
		node parent;
		node childhead,childtail;
		node prev, next;
		
		public node(char[] ch_) {
			v = new char[ch_.length];
			for (int i = 0; i < ch_.length; i++) {
				v[i] = ch_[i];
			}
		}
		
		void addchild(node n) { 
			if (childhead == null ) { 
				childhead = n;
				childtail = n;
			}else{
				childtail.next = n ;
				n.prev = childtail;
				childtail = n;
			}
		}
	}
	
	class tree {

		node root;
		
		public tree() {
			char[] r = new char[MAX];
			r[0] = '/';
			r[1] = '\0';
			root = new node(r);
		}

		public void print(node r, int cnt) {
			// TODO Auto-generated method stub
			if (r == null) return; 
			for (int i = 0; i < cnt; i++) {
				System.out.print("+");
				
			}
			System.out.println(new String(r.v));
			
			node h = r.childhead;
			
			while(h!=null) { 
				print(h , cnt+1);					
				h=h.next;				
			}
			
		}

		void add(node pNode, char[] ch_) {
			// TODO Auto-generated method stub
			node n = new node(ch_);
			n.parent = pNode;
			pNode.addchild(n);
		}

		node find(char[] pa_) {
			//parsing pa_  '/a/b/c\0'-> a , b, c
			char[][] data = new char[MAX][MAX];
			int cnt = 0 ;
			//data[cnt++][0] ='/';
			
			for (int i = 0; i < pa_.length; i++) {
				if ( pa_[i] == '/' ) {
					int raw = 0;
					for (int j = i+1 ; j < pa_.length ; j++) {
						if ( pa_[j] == '/' ) break;
						data[cnt][raw++] = pa_[j];
					}
					cnt++;
				}else if (pa_[i] == '\0') {
					break;
				}
			}
			
			f = null; 
			
			//1은 루트노드 검색안할려고
			myfind1(root , data ,0 , cnt);
			return f;
		} 
		
		node f = null;  
		
		void myfind1(node r , char[][] data , int start , int depth) { 
			
			if ( start == depth ) {
				f = r;
				return;
			}
			
			node h = r.childhead;
			while(h!=null) {
				
				//찾는것이 존재한다면, 다음 하위노드  들어가기 위해서, 함수재호출  
				if ( equal(h.v , data[start])) {
					myfind1(h , data , start+1 , depth);
				}
				
				//break 최적화
				if ( f != null) break;
				
				h = h.next;
			}
			
		}
		
		void myfind____TT_TT(node r , char[][] data , int start , int depth) { 
			if ( r == null) {
				return;
			}
			
			if ( start == depth ) {
				return;
			}
			
			node h = r;
			while(h!=null) {
				
				if ( equal(h.v , data[start])) {
					int c = start+1;
					//child 가 존재하고, 끝까지 안갔다면 , child 들어간다 
					if (h.childhead != null && c != depth) { 
						myfind(h.childhead , data , c , depth);
					}
					
					if ( c == depth ) {
						f = h;
					}
				}
				
				if ( f != null) break;
				
				
				h = h.next;
			}
			
		}
		
		boolean equal(char[] o , char[] t) {
			for (int i = 0; i < t.length; i++) {
				if ( o[i] != t[i]) return false;
				
				if ( o[i] =='\0' && t[i]=='\0' ) break;
			}
			
			return true;
		}
		
	}

}
