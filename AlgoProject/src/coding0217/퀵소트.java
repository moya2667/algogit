package coding0217;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Äü¼ÒÆ® {

	private int[] input;
	private int num;
	private node[] nodelist;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
1
5
48 77 55 80 69
	 */
	@Test
	public void test() {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		num = sc.nextInt();
		input = new int[num];
		for (int i = 0; i < num; i++)
		{
			input[i] = sc.nextInt();
		}

		quickSort(0, num - 1);
		printResult();
	}
	
	class node {
		char c ;
		int n; 
		node (char c , int n){
			this.c = c;
			this.n = n;
		}
	}

	@Test
	public void testNode() {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		num = sc.nextInt();
		nodelist = new node[num];
		
		for (int i = 0; i < num; i++)
		{
			int k  = sc.nextInt();
			nodelist[i] = new node( (char)k , k  );
		}

		quickSort_Node(0, num - 1);
		
		for (int i = 0; i < num; ++i)
		{
			System.out.println(nodelist[i].c + " :  " + nodelist[i].n);
		}
		System.out.println();
	}	
	
	void printResult()
	{
		for (int i = 0; i < num; ++i)
		{
			System.out.print(input[i] + " ");
		}
		System.out.println();
	}	
	
	void quickSort_Node(int first, int last)
	{
		node temp;		
		if (first < last)
		{
			int pivot = first;
			int i = first;
			int j = last;

			while (i < j)
			{
				while ( nodelist[i].n  <= nodelist[pivot].n && i < last)
				{
					i++;
				}
				while (nodelist[j].n > nodelist[pivot].n)
				{
					j--;
				}
				if (i < j)
				{
					temp = nodelist[i];
					nodelist[i] = nodelist[j];
					nodelist[j] = temp;
				}
			}

			temp = nodelist[pivot];
			nodelist[pivot] = nodelist[j];
			nodelist[j] = temp;

			quickSort_Node(first, j - 1);
			quickSort_Node(j + 1, last);
		}
	}	

	void quickSort(int first, int last)
	{
		int temp;		
		if (first < last)
		{
			int pivot = first;
			int i = first;
			int j = last;

			while (i < j)
			{
				while (input[i] <= input[pivot] && i < last)
				{
					i++;
				}
				while (input[j] > input[pivot])
				{
					j--;
				}
				if (i < j)
				{
					temp = input[i];
					input[i] = input[j];
					input[j] = temp;
				}
			}

			temp = input[pivot];
			input[pivot] = input[j];
			input[j] = temp;

			quickSort(first, j - 1);
			quickSort(j + 1, last);
		}
	}

}
