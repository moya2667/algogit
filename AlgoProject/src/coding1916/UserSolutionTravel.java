import java.util.HashMap;

class UserSolution3 {
	class user {
		int fc = 0;
		int[] friend = new int[21]; // 버그 20개 할당

		void addf(final int uid) {
			friend[fc++] = uid;
		}

		int area[] = new int[11];

		int rc = 0;

		public void addreserve(final product p) {
			rc++;
			area[p.area] += 1; //이게 생각 어려움 
		}
	}

	class product {
		int area;
		int price;
		int pid;
		boolean reserve = false;
	}

	class area {

		int areaid;
		int pc = 0;

		prioirtyqueue pq;

		area() {
			pq = new prioirtyqueue();
		}

		public void addproduct(final product p) {
			pq.push(p);
		}

		public product gettop() {
			product p = pq.getTop();
			while (p != null && p.reserve == true) {
				pq.pop();
				p = pq.getTop();
			}

			return p;

		}
	}

	area areas[];
	user users[];
	product products[];
	HashMap prdHash;
	int AREAS = 0;

	public void init(final int N, final int M) {
		AREAS = M + 1;
		users = new user[N + 1];
		areas = new area[AREAS];

		for (int i = 1; i < N + 1; i++) {
			users[i] = new user();
			users[i].addf(i); // 버그 본인 안넣은거
		}
		for (int i = 1; i < M + 1; i++) {
			areas[i] = new area();
		}

		prdHash = new HashMap<>();

	}

	public void befriend(final int uid1, final int uid2) {
		users[uid1].addf(uid2);
		users[uid2].addf(uid1);
	}

	// package list 에 넣어둔다 4만 이하 (여기에 힌트)
	public void add(final int pid, final int area, final int price) {
		final product p = new product();
		p.pid = pid;
		p.area = area;
		p.price = price;

		areas[p.area].addproduct(p);

		prdHash.put(pid, p);
		return;
	}

	// pa
	public void reserve(final int uid, final int pid) {

		final product p = (product) prdHash.get(pid);
		p.reserve = true;
		users[uid].addreserve(p);

	}

	class areasort {
		int cnt;
		int area;
	}

	public int recommend(final int uid) {
		// System.out.println("recommend = " + uid);
		final areasort[] psort = new areasort[11];
		for (int i = 0; i < psort.length; i++) {
			psort[i] = new areasort();
		}

		// 본인포함이어야 함.uid 친구들이 가장 많이 예약한 지역 : 버그
		for (int i = 0; i < users[uid].fc; i++) {
			final int fuserid = users[uid].friend[i];
			for (int j = 0; j < AREAS; j++) {
				psort[j].cnt += users[fuserid].area[j];
				psort[j].area = j;
			}
		}

		// sort
		for (int i = 0; i < AREAS; i++) {
			final areasort temp = psort[i];
			int j = i - 1;

			while ((j >= 1) && !compare(temp, psort[j])) // insert sort 주의할점// (!compare)
			{
				psort[j + 1] = psort[j];
				j = j - 1;
			}
			psort[j + 1] = temp;
		}

		/*
		 * for (int i = 0; i < AREAS; i++) { System.out.println(psort[i].area +
		 * "=>" + psort[i].cnt); }
		 */

		// 우선순위 가장 높은 지역에 상품이 존재한다면,
		if (psort[1].cnt > 0) {
			for (int i = 1; i < AREAS; i++) {
				final product p = areas[psort[i].area].gettop();
				if (p != null) {
					return p.pid;
				}
			}

		} else {
			int minpid = 1000000000;
			int minprice = 1000000000;

			for (int i = 1; i < AREAS; i++) {
				final product p = areas[i].gettop();
				if (p != null) {
					if (minprice > p.price) {
						minprice = p.price;
						minpid = p.pid;
						// BUG. 아래 코드 알아차리지 못함 // AREA[0] / AREA[1]
					} else if (p.price == minprice) {
						if (p.pid < minpid) {
							minpid = p.pid;
							minprice = p.price;
						}
					}
				}
			}

			return minpid;

		}

		return 0;

	}

	boolean compare(final areasort a, final areasort b) {
		if (a.cnt < b.cnt) {
			return true;
		} else if (a.cnt == b.cnt) {
			final product p = areas[a.area].gettop();
			final product p1 = areas[b.area].gettop();

			if (p == null || p1 == null) {
				return false;
			}
			if (p.price == p1.price) {
				if (p.pid > p1.pid) {
					return true;
				}
			} else if (p.price > p1.price) {
				return true;
			}
		}
		return false;
	}

	// AREA별 Product LIST 관리
	class prioirtyqueue {

		product[] pkgs;
		int heapSize;

		prioirtyqueue() {
			heapSize = 0;
			pkgs = new product[40001];
		}

		public product getTop() {
			if (heapSize <= 0) {
				return null;
			}
			return pkgs[0];
		}

		public product Peek() {
			return pkgs[0];
		}

		public boolean isQueueEmpty() {
			return this.heapSize == 0;
		}

		public product pop() {
			if (heapSize <= 0) {
				return null;
			}

			final product value = pkgs[0];
			heapSize = heapSize - 1;

			pkgs[0] = pkgs[heapSize];

			int current = 0;
			while (current < heapSize && current * 2 + 1 < heapSize) {
				int child;
				if (current * 2 + 2 >= heapSize) {
					child = current * 2 + 1;
				} else {
					child = compare(pkgs[current * 2 + 1], pkgs[current * 2 + 2]) ? current * 2 + 1 : current * 2 + 2;
				}

				if (compare(pkgs[current], pkgs[child])) {
					break;
				}

				final product temp = pkgs[current];
				pkgs[current] = pkgs[child];
				pkgs[child] = temp;

				current = child;
			}
			return value;

		}

		public void push(final product p) {
			if (heapSize + 1 > 40001) {
				return;
			}

			pkgs[heapSize] = p;

			int current = heapSize;
			while (current > 0 && compare(pkgs[current], pkgs[(current - 1) / 2])) {
				final product temp = pkgs[(current - 1) / 2];
				pkgs[(current - 1) / 2] = pkgs[current];
				pkgs[current] = temp;
				current = (current - 1) / 2;
			}
			heapSize = heapSize + 1;

		}

		boolean compare(final product start, final product p) {
			if (start.price < p.price)
				return true;
			else if (start.price == p.price) {
				if (start.pid < p.pid) {
					return true;
				}
			}
			return false;
		}

		void print() {
			System.out.println("area = " + pkgs[0].area);
			for (int i = 0; i < heapSize; i++) {
				System.out.print("(" + pkgs[i].pid + "," + pkgs[i].price + ")" + "->");
			}
			System.out.println();
		}
	}

}
