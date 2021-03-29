class UserSolution2 {

	/// Constants
	final static int MAX_VALUE_LEN = 100001;

	/// Variables
	node[] nodes;
	Hashtable ht;
	PQ pq;
	int count;
	node[] popped = new node[10];
	/// Methods

	class node {
		int value;
		int pqIndex;

		public node(int value) {
			this.value = value;
			this.pqIndex = -1;
		}
	}

	public void init() {
		nodes = new node[MAX_VALUE_LEN];
		ht = new Hashtable(MAX_VALUE_LEN + 6);
		pq = new PQ();
		pq.heapSize = 0;
		count = 0;
	}

	public void push(int key, int value) {
		int index = ht.find(key);
		if (index == -1) {
			nodes[count] = new node(value);
			ht.add(key, count);
			pq.push(nodes[count]);
			count++;
		} else {
			nodes[index] = new node(value);
			pq.push(nodes[index]);
		}

	}

	public int top(int K) {
		int ret = 0;

		for (int i = 0; i < K; i++) {
			node value = pq.pop();
			popped[i] = value;
			ret += value.value;
		}

		for (int i = 0; i < K; i++) {
			pq.push(popped[i]);
		}
		return ret;
	}

	public void erase(int key) {
		int index = ht.find(key);
		pq.popByIndex(nodes[index].pqIndex);
	}

	public void modify(int key, int value) {
		int index = ht.find(key);
		nodes[index].value = value;
		pq.update(nodes[index].pqIndex);
	}

	class PQ {
		node heap[];
		int heapSize = 0;

		public PQ() {
			heapSize = 0;
			heap = new node[MAX_VALUE_LEN];
		}

		boolean canPushSwap(int current) {
			if (heap[current].value > heap[(current - 1) / 2].value)
				return true;
			return false;
		}

		void upgrade(int current) {
			while (current > 0 && canPushSwap(current)) {
				node temp = heap[(current - 1) / 2];
				heap[(current - 1) / 2] = heap[current];
				heap[(current - 1) / 2].pqIndex = (current - 1) / 2;
				heap[current] = temp;
				heap[current].pqIndex = current;
				current = (current - 1) / 2;
			}
		}

		void push(node value) {
			if (heapSize + 1 > MAX_VALUE_LEN) {
				return;
			}

			heap[heapSize] = value;
			heap[heapSize].pqIndex = heapSize;

			int current = heapSize;
			upgrade(current);
			heapSize = heapSize + 1;
		}

		boolean canPopSwap(int current) {
			if (heap[current * 2 + 1].value > heap[current * 2 + 2].value)
				return true;
			return false;
		}

		void downgrade(int current) {
			while (current < heapSize && current * 2 + 1 < heapSize) {
				int child;
				if (current * 2 + 2 >= heapSize) {
					child = current * 2 + 1;
				} else {
					child = canPopSwap(current) ? current * 2 + 1 : current * 2 + 2;
				}

				if (heap[current].value > heap[child].value) {
					break;
				}

				node temp = heap[current];
				heap[current] = heap[child];
				heap[current].pqIndex = current;
				heap[child] = temp;
				heap[child].pqIndex = child;
				current = child;
			}
		}

		node pop() {
			if (heapSize <= 0) {
				return null;
			}

			node value = heap[0];
			heapSize = heapSize - 1;

			heap[0] = heap[heapSize];
			heap[0].pqIndex = 0;

			downgrade(0);

			return value;
		}

		node popByIndex(int index) {
			if (heapSize <= 0) {
				return null;
			}

			node value = heap[index];
			heapSize = heapSize - 1;

			heap[index] = heap[heapSize];
			heap[index].pqIndex = index;

			upgrade(index);
			downgrade(index);

			return value;
		}

		void update(int index) {
			upgrade(index);
			downgrade(index);
		}
	}

	class Hashtable {
		class Hash {
			int key;
			int data;
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

		private int hash(int k) {
			int hash = 5381;
			hash = ((hash << 5) + hash) + k;
			if (hash < 0)
				hash *= -1;
			return hash % capacity;
		}

		public int find(int key) {
			int h = hash(key);
			int cnt = capacity;
			while (tb[h].key != 0 && (--cnt) != 0) {
				if (tb[h].key == key) {
					return tb[h].data;
				}
				h = (h + 1) % capacity;
			}
			return -1;
		}

		void add(int key, int data) {
			int h = hash(key);
			while (tb[h].key != 0) {
				if (tb[h].key == key) {
					return;
				}
				h = (h + 1) % capacity;
			}
			tb[h].key = key;
			tb[h].data = data;
			return;
		}
	}
}
