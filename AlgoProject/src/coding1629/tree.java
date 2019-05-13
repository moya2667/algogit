package coding1629;
import org.junit.Test;

public class tree {
	/*
	1. �⺻ TREE �����غ���
	2. ���� �迭(TNODE)�� ǥ���غ��� 
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
		//p�� �ڽĳ�带 �����ϰ�, v �� �Ҵ� 
		tnode add(tnode p , char[] v){
			//child node ���� 
			tnode n = new tnode(v);
			//p node�� child �� ���� 
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
		tnode cHead,cTail; //TNode child
		tnode next,prev; 
		public tnode(char[] va){
			v = new char[va.length]; //indexing hashing ? (�̰Ǳ׳�)
			for(int i =0; i < va.length ;i++){
				v[i]= va[i];
			}
		}		
	}
}
