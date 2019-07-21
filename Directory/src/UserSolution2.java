
class UserSolution2 {

	private final static int NAME_MAXLEN = 6;
	private final static int PATH_MAXLEN = 1999;

	int mstrcmp(char[] a, char[] b) {
		int i;
		for (i = 0; a[i] != '\0'; i++) {
			if (a[i] != b[i])
				return a[i] - b[i];
		}
		return a[i] - b[i];
	}

	void mstrcpy(char[] dest, char[] src) {
		int i = 0;
		while (src[i] != '\0') {
			dest[i] = src[i];
			i++;
		}
		dest[i] = src[i];
	}

	class dir {
		dir prev, next;
		dir parent;
		ll childs = new ll();
		char[] path;

		public dir(char[] p) {
			path = new char[p.length];
			mstrcpy(path, p);
		}
	}

	class ll {
		dir chead, ctail;

		dir add(dir d) {
			if (chead == null) {
				chead = d;
				ctail = d;
			} else {
				ctail.next = d;
				d.prev = ctail;
				ctail = d;
			}
			return d;
		}

		void delete(dir sf) {

			if (sf == chead && sf == ctail) {
				chead = null;
				ctail = null;
				return;
			}

			if (sf == chead) {
				dir next = chead.next;
				next.prev = null;
				chead = next;
			} else if (sf == ctail) {
				dir pre = ctail.prev;
				pre.next = null;
				ctail = pre;
			} else {
				dir pre = sf.prev;
				dir ne = sf.next;
				pre.next = ne;
				ne.prev = pre;
			}

			sf.next = null; // 하.. 이게 다른곳에 붙여넣기 할때.이슈가 생기는구나...미리 지워놓자.
			sf.prev = null;

		}
	}

	class tree {

		void find(dir d, char[][] paths, int s, int paths_c) {
			if (d == null)
				return;
			if (s == paths_c) {
				fd = d;
			}
			dir h = d.childs.chead;
			while (h != null) {
				if (mstrcmp(h.path, paths[s]) == 0) {
					find(h, paths, s + 1, paths_c);
				}
				h = h.next;
			}
		}

		dir add(dir f, dir dir) {
			dir.parent = f; // parent 설정
			return f.childs.add(dir);
		}

		void print(dir r, int c) {
			if (r == null)
				return;

			for (int i = 0; i < c; i++) {
				System.out.print("+");
			}
			System.out.println(new String(r.path));

			dir h = r.childs.chead;
			while (h != null) {
				print(h, c + 1);
				h = h.next;
			}
		}

		int cnt;

		int getcnt(dir r) {
			cnt = 0;
			getcntRe(r, 0);
			return cnt;
		}

		public void getcntRe(dir r, int c) {
			if (r == null)
				return;

			dir h = r.childs.chead;
			while (h != null) {
				cnt++;
				getcntRe(h, c + 1);
				h = h.next;
			}
		}

		void copy(dir sf, dir df) {
			if (sf == null)
				return;
			// copy
			dir copydir = new dir(sf.path);
			// df 하위에 , copydir 를 넣는다. 넣은 노드의 리턴값 d (important!)
			dir d = this.add(df, copydir);

			dir h = sf.childs.chead;
			while (h != null) {
				copy(h, d);
				h = h.next;
			}
		}

	}

	int dirc = 0;
	dir root = null;
	dir fd = null; // find directory
	tree mytree;

	void init(int n) {
		dirc = n;
		char[] r = new char[2];
		r[0] = '/';
		r[1] = '\0';
		root = new dir(r);
		mytree = new tree();
	}

	dir myfind(char[] path) {
		// path parsing
		// / 기준으로 나눈다. /a/b/c PATH자체를 짤라가면서..찾아가는게 더 빠를듯...
		char[][] paths = new char[1000][7]; // 총path ->느릴듯. (다른사람들이거 어떻게 풀었는지
											// 봐야겠다.)
		int paths_c = 0;
		paths[paths_c++][0] = '/';
		for (int i = 0; i < path.length; i++) {
			if (path[i] == '\0') {
				break;
			}
			if (path[i] == '/') {
				// get path
				int c = 0;
				int j = 0;
				for (j = i + 1; j < i + 8; j++) {
					if (path[j] == '\0' || path[j] == '/')
						break;
					paths[paths_c][c++] = path[j];
				}

				if (path[j] != '\0')
					paths_c++;

			}
		}
		// System.out.println("");
		fd = null;
		// root 제외
		mytree.find(root, paths, 1, paths_c);

		return fd;
	}

	boolean isRoot(char[] p) {
		if (p[0] == '/' && p[1] == '\0')
			return true;
		return false;
	}

	void cmd_mkdir(char[] path, char[] name) {
		// System.out.println("cmd_mkdir ="+ new String(path) + " , " + new
		dir f = null;
		if (isRoot(path)) {
			f = root;
		} else {
			f = myfind(path);
		}
		if (f == null) {
			//System.out.println("DEBUG: Find is NULL");
		}
		mytree.add(f, new dir(name));
	}

	void cmd_rm(char[] path) {
		// System.out.println("cmd_rm ="+ new String(path) );
		// mytree.print(root, 0);
		dir f = myfind(path);
		f.parent.childs.delete(f);
	}

	void cmd_cp(char[] srcPath, char[] dstPath) {
		// System.out.println("cmd_cp ="+ new String(srcPath) + " , " + new
		// String(dstPath));
		dir sf = myfind(srcPath);
		dir df = myfind(dstPath);

		mytree.copy(sf, df);
		// mytree.print(root, 0);

	}

	void cmd_mv(char[] srcPath, char[] dstPath) {
		// System.out.println("cmd_mv ="+ new String(srcPath) + " , " + new
		dir sf = myfind(srcPath);
		dir df = myfind(dstPath);

		dir sf_parent = sf.parent;
		// delete child
		sf_parent.childs.delete(sf);
		// add child on df
		mytree.add(df, sf);
		// df.childs.add(sf); //하.ㅅㅂ (direct 접근했을때 실수 mytree.add 하는놈이라 node.add하면..)
	}

	int cmd_find(char[] path) {

		dir f = myfind(path);
		return mytree.getcnt(f);

	}
}
