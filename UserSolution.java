class UserSolution {
	
	int MAX = 100002;
	Hashtable myhash;
	pq mypq;
	node[] nodes;
	int cnt;
	
	public void init() {
		myhash = new Hashtable(200000);
		mypq = new pq();
		
		nodes = new node[100002];
		cnt  = 0 ;
	}  
	
	class node{
		int key ;
		int value ;
		boolean delete = false;
		node(){};
	}
	
	private int createnode(int k , int v) { 
		if (nodes[cnt] == null) { 
			nodes[cnt] = new node();
		}
		nodes[cnt].key = k ; 
		nodes[cnt].value = v;
		return cnt++;
	}

	public void push(int key, int value) {
		int idx = createnode(key,value);
		myhash.add(key, idx);
		mypq.heapPush(idx);
	}

	public int top(int K) {
		int c = 1;
		int sum = 0; 
		while(c <= K) {
			int idx = mypq.heapPop();
			if (nodes[idx].delete == false) { 
				sum+=nodes[idx].value;
				c++; 
			}
		}
 		return sum;
	}

	public void erase(int key) {
		System.out.println("erase");
		int idx = myhash.find(key);
		nodes[idx].delete = true;
		myhash.delete(key);
	}

	public void modify(int key, int value) {		
		System.out.println("modify: " + key + " , " + value);
		int idx = myhash.find(key);
		nodes[idx].value = value;
		
		int[] check  = new int[mypq.heapSize];
		int c = 0;
		while(true){
			int popIdx = mypq.heapPop();
			check[c++] = popIdx;
			if ( popIdx == idx ) {
				for (int i = 0; i < c ; i++) {
					mypq.heapPush(check[i]);
				}
				break;
			}
		}
	}

	class Hashtable {
		class Hash {
			int key;
			int value;
		}

		int capacity;
		Hash tb[];

		public Hashtable(int capacity) {
			this.capacity = capacity;
			tb = new Hash[capacity];
			for (int i = 0; i < capacity; i++) {
				tb[i] = new Hash();
			}
		}

		private int hash(int key) {
			return key % capacity;
		}

		public int find(int key) {
			int h = hash(key);
			int cnt = capacity;
			while (tb[h].key != 0 && (--cnt) != 0) {
				if (tb[h].key == key) {
					return tb[h].value;
				}
				h = (h + 1) % capacity;
			}
			return 0;
		}

		public int delete(int key) {
			int h = hash(key);
			int cnt = capacity;
			while (tb[h].key != 0 && (--cnt) != 0) {
				if (tb[h].key == key) {
					int v = tb[h].value;
					tb[h] = new Hash();
					return v;
				}
				h = (h + 1) % capacity;
			}
			return 0;
		}

		int add(int key, int value) {
			int h = hash(key);
			while (tb[h].key != 0) {
				if (tb[h].key == key) {
					return value;
				}
				h = (h + 1) % capacity;
			}
			tb[h].key = key;
			tb[h].value = value;
			return value;
		}

	}
	
	class pq {
		int heap[] = new int[MAX];
		int heapSize = 0;

		void heapInit() {
			heapSize = 0;
		}

		void heapPush(int value) {
			if (heapSize + 1 > MAX) {
				return;
			}

			heap[heapSize] = value;

			int current = heapSize;
			while (current > 0 && iscompare(heap[current] , heap[(current - 1) / 2])) {
				int temp = heap[(current - 1) / 2];
				heap[(current - 1) / 2] = heap[current];
				heap[current] = temp;
				current = (current - 1) / 2;
			}

			heapSize = heapSize + 1;
		}

		int heapPop() {
			if (heapSize <= 0) {
				return -1;
			}

			int value = heap[0];
			heapSize = heapSize - 1;

			heap[0] = heap[heapSize];

			int current = 0;
			while (current < heapSize && current * 2 + 1 < heapSize) {
				int child;
				if (current * 2 + 2 >= heapSize) {
					child = current * 2 + 1;
				} else {
					child = iscompare(heap[current * 2 + 1] , heap[current * 2 + 2]) ? current * 2 + 1 : current * 2 + 2;
				}

				if (iscompare(heap[current] , heap[child])) {
					break;
				}

				int temp = heap[current];
				heap[current] = heap[child];
				heap[child] = temp;

				current = child;
			}
			return value;
		}
		
		boolean iscompare(int id, int id2) { 
			if (nodes[id].value > nodes[id2].value) { 
				return true;
			}
			return false;
		}

		void heapPrint(int[] heap, int heap_size) {
			for (int i = 0; i < heap_size; i++) {
				System.out.print(heap[i] + " ");
			}
			System.out.println();
		}
	}
	
}