package coding0415;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class family {
	tree tree = new tree();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//1,0,8,70,82,65,78,75,76,73,78,0,4,71,65,82,89
	@Test
	public void test() throws FileNotFoundException {	 
		FileInputStream ip = new FileInputStream("src\\coding0415\\input2.txt");		
        Scanner sc = new Scanner(ip);
              
        String[] data = sc.next().split(",");
        
        int[] d = new int[data.length+1] ; 
        char[] family = new char[data.length+1];
        int cnt = 0; 
        for ( int i = 0 ; i < data.length ; i++){
        	d[cnt++] = Integer.parseInt(data[i]);
        }
        
        for (int i = 0; i < d.length; i++) {
        	System.out.print((char)d[i] + ",");
        	family[i] = (char)d[i];
		}       
        System.out.println();
        
        parse(family , d.length);
	}
	
	static int MAX_FAMILY_TREE_LENGTH = 30000;
	static int MAX_NAME_LENGTH = 12;
/*
- 결혼 여부(SOLOTYPE:1, MARRIEDTYPE:2, 1 bytes)
- 이름 길이(최대 값은 11, 2 bytes)
- 이름(최대 길이 11, n bytes)
- 미혼인 경우 . best friend 이름의 길이(최대 값은 11, 2 bytes)
 . best friend 이름(최대 길이 11, n bytes)
- 기혼인 경우
 . 다음세대 자녀 수(2 bytes)
 . 자녀들의 연속된 데이터(구성원 구조 형태). 나이 순으로 정렬되어 있다. (장자->막내 순)
 1,   0,8,   70,82,65,78,75,76,73,78,   0,4,    71,65,82,89
 type 이름길이   이름						     친구길이           친구이름 
*/
	int SOLOTYPE = 1; 
	int MARRIEDTYPE = 2; 
	void parse(char[] orgFamilyTree, int size) {
		treenode root = new treenode('r');	
		parse(root , orgFamilyTree , size, 0 );		
		tree.print(root, 0);
	}

	private int parse(treenode node , char[] orgFamilyTree, int size, int cnt) {
		// TODO Auto-generated method stub	 
		int type = (int)orgFamilyTree[cnt++];
		node.setType(type);
		
		int firstNameLen = (int)orgFamilyTree[cnt++];
		int secondNameLen = (int)orgFamilyTree[cnt++];		
		int nameLen = firstNameLen*10 + secondNameLen;
		
		
		char[] name = new char[nameLen]; 
		for (int i = 0 ; i < nameLen ; i++) { 
			name[i] = orgFamilyTree[cnt++];
		}
		node.setName(name);
		node.setNameLen(nameLen);
		
		if (type == SOLOTYPE) { 
			int firstfriendNameLen = (int)orgFamilyTree[cnt++];
			int secondfriendNameLen = (int)orgFamilyTree[cnt++];
			int friend_NameLen = firstfriendNameLen*10 + secondfriendNameLen;
					
			node.setFriendNameLen(friend_NameLen);
			char[] friendName = new char[friend_NameLen]; 
			for (int i = 0 ; i < friend_NameLen ; i++) { 
				friendName[i] = orgFamilyTree[cnt++];
			}		
			node.setFriendName(friendName);
			
		}else if (type == MARRIEDTYPE){
			int firstNumber = (int)orgFamilyTree[cnt++];
			int secondNumber = (int)orgFamilyTree[cnt++];
			int childsCount = firstNumber*10 + secondNumber;
			
			node.setChildsCount(childsCount);
			
			for ( int i = 0 ; i < childsCount ; i++) {
				//System.out.println("A");
				//child 
				treenode child = tree.add(node, (char)i);				
				int returnCnt = parse( child , orgFamilyTree, size, cnt);
				cnt = returnCnt;
			}
		}
		
		return cnt;
	}
	
	
	class treenode{
		treenode parent;
		treenode next , prev;
		link link = new link();
		int type = 0 ; 
		char n; 
		char[] name ;
		int nameLen;
		char[] friendname;
		int childsCount;
		
		treenode(char n){
			this.n = n;
		}
		public void setChildsCount(int childsCount) {
			// TODO Auto-generated method stub
			 this.childsCount = childsCount;
			
		}
		public void setFriendName(char[] friendName) {
			// TODO Auto-generated method stub
			this.friendname = friendName; //이게 바로 할당 되니.?
//			for (int i = 0 ; i < this.friendname.length ; i++) {
//				System.out.print(this.friendname[i]);
//			}
//			System.out.println();
		}
		public void setFriendNameLen(int friend_NameLen) {
			// TODO Auto-generated method stub
			
		}
		public void setNameLen(int nameLen) {
			// TODO Auto-generated method stub
			this.nameLen = nameLen;
		}
		public void setName(char[] name) {
			// TODO Auto-generated method stub
			this.name = name; //이게 바로 할당 되니.?
//			for (int i = 0 ; i < this.name.length ; i++) {
//				System.out.print(this.name[i]);
//			}
//			System.out.println();			
		}
		void setType(int type) { 
			this.type = type; 
		}
		int getType(){
			return this.type;
		}
		
		void print(){
			System.out.println("type = " + this.type );
			System.out.println("name lenth = " + nameLen );
			System.out.print("name = ");
			for (int i = 0 ; i < nameLen ; i++) {
				System.out.print(name[i]);
			}
			System.out.println();
		}
	}

	class tree {		

		treenode root; 
		
		public treenode add(treenode pa, char c) {
			// TODO Auto-generated method stub
			//if ( root == null ) {
			//	root = pa;
				//return root;
			//}
			
			return pa.link.add(pa, c);
		}
		
		public void print(treenode t , int depth) {
			// TODO Auto-generated method stub
			if ( t == null ) return;
			for ( int i = 0 ; i < depth ; i++ ) System.out.print(" +");
			
			for(int i = 0 ; i < t.nameLen ; i++) {
				System.out.print(t.name[i]);
			}
			
			System.out.print( " : " ) ;
			if ( t.getType() == SOLOTYPE ){
				 
				System.out.print( t.friendname);
			}else{
				System.out.print( t.childsCount );
			}
			
			System.out.println();
			
			treenode f = t.link.head;
			
			if ( f == null) return; 
				
			if ( f != null ){
				print(f , depth+1);
			}
			
			while ( f != null) { 
				f = f.next;
				print(f , depth+1);
			}
		}
		
	}
	
	class link { 
		treenode head,tail;
		
		public treenode add(treenode pa , char c) {
			// TODO Auto-generated method stub
			treenode n = new treenode(c);
			pa = n.parent;
			
			if ( head == null) { 
				head = n;
				tail = n;
			}else{
				tail.next = n ; 
				n.prev = tail;
				tail = n;
			}
			
			return n;
		}
	}	

}



