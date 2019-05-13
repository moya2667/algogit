package coding1629;
import org.junit.Test;

public class tree {
	/*
	1. 기본 TREE 구성해보기
	2. 정적 배열(TNODE)로 표현해보기 
	 */
	
	@Test
	public void test() {
		moyatree tree = new moyatree();
		tnode r= new tnode("root".toCharArray());
		tnode o = tree.add(r , "1".toCharArray());
		tnode TWO = tree.add(r , "2".toCharArray());	
		
		
		tnode o1 = tree.add( o , "11".toCharArray());
		tnode o2 = tree.add( o , "12".toCharArray());
		
		tnode o21 = tree.add( o1, "123".toCharArray());
		tree.print(r, 0);
		
	}
	class moyatree { 
		//p의 자식노드를 생성하고, v 값 할당 
		tnode add(tnode p , char[] v){
			//child node 생성 
			tnode n = new tnode(v);
			//p node의 child 로 지정 
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
		//v에 해당하는 값 찾아서, 삭제 
		void delete(char[] v){
			if (find(v)!=null ){ 
				System.out.println("find node and delete node : " + new String(v) );
			}
			System.out.println("failed to find : " + new String(v));
			return;
		}
		
		tnode find(char[] v){
			
			return null;
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
		tnode cHead,cTail; //TNode child
		tnode next,prev; 
		public tnode(char[] va){
			v = new char[va.length]; //indexing hashing ? (이건그냥)
			for(int i =0; i < va.length ;i++){
				v[i]= va[i];
			}
		}		
	}
}
