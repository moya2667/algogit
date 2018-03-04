package coding0302;

import java.util.Scanner;

class node{

	

	char val;

	node left;

	node right;

	

	node(char v, node l, node r)
	{
		val = v;
		left = l;
		right = r;
	}

}



public class 한줄편집기Refer {



	public static void main(String[] args) {
		

		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		int numOA = sc.nextInt();

		

		node root = new node('0', null, null);
		node cursor = root;

		

		for(int i=0; i<str.length(); i++)
		{
			cursor.right = new  node(str.charAt(i), cursor, null);
			cursor = cursor.right;
		}        

		sc.nextLine();

		for(int i=0; i<numOA; i++)
		{

			String command = sc.nextLine();
			switch(command.charAt(0))
			{
			case 'L': 

				if(cursor.left==null){break;}

				else{
					cursor = cursor.left;
					break;
				}

			case 'D':

				if(cursor.right==null){break;}
				else{
					cursor = cursor.right;
					break;
				}

			case 'B':

				if(cursor.left==null){break;}

				else if(cursor.right==null){

					cursor.left.right = null;
					cursor = cursor.left;
					break;

				}

				else{

					cursor.left.right = cursor.right;
					cursor.right.left = cursor.left;
					cursor = cursor.left;
					break;
				}

			case 'P':

				if(cursor.right==null){
					cursor.right = new node(command.charAt(2), cursor, null);
					cursor = cursor.right;
					break;
				}

				else{
					cursor.right = new node(command.charAt(2), cursor, cursor.right);
					cursor.right.right.left = cursor.right;
					cursor = cursor.right;

					break;

				}		

			}

			

		}

		do{
			System.out.print(root.right.val);
			root = root.right;

		}while(root.right!=null);		

    }
}



