package 슈퍼코딩20211;

import java.util.Scanner;

/*
1
10 
10 49 38 17 56 92 8 1 13 55   
 */
class 우선순위큐_구조체 {
	static Scanner sc;

	static final int MAX_SIZE = 100;

	//static int heap[] = new int[MAX_SIZE];
	static node[] heap = new node[MAX_SIZE];
	static int heapSize = 0;

	static void heapInit()
	{	
		heapSize = 0;
	}
	


	static void heapPush(node node)
	{
		if (heapSize + 1 > MAX_SIZE)
		{
			return;
		}

		//heap[heapSize].value = node.value;
		heap[heapSize] = node;

		int current = heapSize;
		while (current > 0 && heap[current].value > heap[(current - 1) / 2].value) 
		{
			//int temp = heap[(current - 1) / 2];
			node temp = heap[(current - 1) / 2];
			heap[(current - 1) / 2] = heap[current];
			heap[current] = temp;
			current = (current - 1) / 2;
		}

		heapSize = heapSize + 1;
	}

	static node heapPop()
	{
		if (heapSize <= 0)
		{
			return null;
		}

		//int value = heap[0].value;
		node n = heap[0];
		heapSize = heapSize - 1;

		heap[0] = heap[heapSize];

		int current = 0;
		while (current < heapSize && current * 2 + 1 < heapSize)
		{
			int child;
			if (current * 2 + 2 >= heapSize)
			{
				child = current * 2 + 1;
			}
			else
			{
				child = heap[current * 2 + 1].value > heap[current * 2 + 2].value ? current * 2 + 1 : current * 2 + 2;
			}

			if (heap[current].value > heap[child].value)
			{				
				break;
			}

			node temp = heap[current];
			heap[current] = heap[child];
			heap[child] = temp;
			current = child;
		}
		
		//System.out.println( n.value + " : " + " idx = " + n.idx );
		return n;
	}

	static void heapPrint(int[] heap, int heap_size)
	{
		for (int i = 0; i < heap_size; i++)
		{
			System.out.print(heap[i] + " ");
		}
		System.out.println();
	}


	static class node { 
		int idx;
		int value;
		node(int idx , int value){
			this.idx = idx;
			this.value = value;
		}
	}
	
	public static void main(String arg[]) throws Exception {
		sc = new Scanner(System.in);
		
		int T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++)
		{
			int N = sc.nextInt();
			
			heapInit();
			
			
			int[] idx = {1,2,3,4,5} ;
			int[] ntimes ={12,13,14,70,70} ;
			
			
			for (int i = 0; i < N; i++)
			{	
				heapPush( new node(idx[i],ntimes[i]));
			}

			
			
			//System.out.print("#" + test_case + " ");
			
			
			
			//후보군 추출 코드
			node[] candidate = new node[N];
			
			node f = heapPop();
			int cnt = 0 ; 
			candidate[cnt++]  = f; 
			for (int i = 0; i < N; i++)
			{
				node p = heapPop();
				if ( f.value != p.value ){ 
					break;
				}
				candidate[cnt++] = p;
			}
			
			
			for (int i = 0 ; i < N ; i++) { 
				if( candidate[i] == null ){
					break;
				}
				System.out.println("candidate = " + candidate[i].value + ": " + candidate[i].idx);
			}
			
			
			
			/*
			for (int i = 0; i < N; i++)
			{
				node n = heapPop();
				System.out.println( n.value + " : " + " idx = " + n.idx );
			}
			System.out.println();
			*/
			
			
		
		}
		sc.close();
	}
}