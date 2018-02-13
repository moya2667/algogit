package 이전연습;

import java.util.LinkedList;
import java.util.Queue;


public class MyTree {

	private char c;

	private MyTree parent = null;

	private MyTree child = null, sibling = null;

	

	public MyTree(){
		c = 0;
		parent = null;
		child = null;
		sibling = null;
	}

	public void add(String input){

		int i = 0;

		if(input.length() == 0){
			return;
		}

		if(input.charAt(i) == '}'){
			if(this.parent != null){
				parent.add(input.substring(i+1, input.length()));
			}
		}

		else {

			if(input.charAt(i) == '{') i++;
			
			if(child == null){
				child = new MyTree();
				child.c = input.charAt(i);
				child.parent = this;
				child.add(input.substring(i+1, input.length()));

			} else {

				MyTree node = child;
				while(node.sibling != null){
					node = node.sibling;
				}

				node.sibling = new MyTree();
				node.sibling.c = input.charAt(i);
				node.sibling.parent = this;
				node.sibling.add(input.substring(i+1, input.length()));

			}

		}

	}

	
	public static void printTree(MyTree tr , int depth) { 
		if (tr == null) return;
		
		for ( int i = 0 ; i < depth ; i++ ) System.out.print(" "); 				
		System.out.println(tr.c); 
		
		//link clist = root.getchildlist();
		if ( tr.child == null) return;
		
		
		if ( tr.child != null ){
			printTree(tr.child , depth+1);
		}
		
		MyTree node = tr.child;
		while (node.sibling != null){
			node = node.sibling;
			printTree(node , depth+1);	
		}		
	}

	

	public static void printTreee(MyTree tr){

		MyTree node;

		Queue<MyTree> myQ = new LinkedList<MyTree>();

		if(tr.child != null)
			myQ.offer(tr);	

		while(myQ.peek() != null){

			node = myQ.poll();

			System.out.print("Value : " + node.c);

			if(node.parent != null)		System.out.print(", Parent : " + node.parent.c); 	else System.out.print(", Parent : ");

			if(node.child != null)		System.out.print(", Child : " + node.child.c);		else System.out.print(", Child : ");

			if(node.sibling != null)	System.out.println(", Sibling : " + node.sibling.c);else System.out.println(", Sibling : ");

			if(node.child != null) myQ.offer(node.child);

			node = node.sibling;

			while(node != null){

				myQ.offer(node);

				node = node.sibling;

			}

		}

	}

	public static void main(String[] args) {

//		String input = "{1{2}{3}{4}}";

//		String input = "{0{1{2}}{3}}";

//		String input = "{0{1}{2}}";

		String input = "{1{2}{3{4}{5}}{6}}{7}}";

		MyTree treee = new MyTree();

		treee.add(input);

		printTree(treee,0);

	}





}