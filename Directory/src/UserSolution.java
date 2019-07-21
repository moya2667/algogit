
class UserSolution {

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
		int childcnt = 0;

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
				mytree.updatecnt(sf.parent,-(sf.childcnt)-1);
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

			sf.next = null; 
			sf.prev = null;
			
			//본인포함 -1
			mytree.updatecnt(sf.parent,-(sf.childcnt)-1);
			

		}
	}

	class tree {

		void updatecnt(dir d , int cnt) {
			//System.out.println("update start");
			while(d!=null) {
				d.childcnt+=cnt;
				d = d.parent;
			}			
		}
		
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
			updatecnt(f,dir.childcnt+1);
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
	void myfind(dir d , char[] path ,int s ) {
		if (d==null) return;
		
		char[] p = new char[7]; 
		int c = getpath(s, path , p);
		if (p[0]=='\0'){
			fd = d;
			return;
		}
		
		dir h = d.childs.chead;
		while(h!=null) { 
			if(mstrcmp(h.path, p )==0) { 
				myfind(h , path ,  c+1);
			}
			h= h.next;
		}
		
	}
	
	int getpath(int s , char[] path, char[] p) { 
		int i = 0;
		int c = 0;
		for (i = s; i < s+7; i++) {
			if (path[i] =='\0' || path[i] =='/') break;
			p[c++] = path[i];
		}
		return i;
	}

	dir myfind(char[] path) {
		fd = null;
		myfind(root, path, 1);
		return fd;
	}

	boolean isRoot(char[] p) {
		if (p[0] == '/' && p[1] == '\0')
			return true;
		return false;
	}

	void cmd_mkdir(char[] path, char[] name) {
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
		
		//System.out.println(root.childcnt);
	}

	void cmd_rm(char[] path) {
		//System.out.println("cmd_rm ="+ new String(path));
		dir f = myfind(path);
		f.parent.childs.delete(f);
	}

	void cmd_cp(char[] srcPath, char[] dstPath) {
		dir sf = myfind(srcPath);
		dir df = myfind(dstPath);
		mytree.copy(sf, df);
	}

	void cmd_mv(char[] srcPath, char[] dstPath) {
		dir sf = myfind(srcPath);
		dir df = myfind(dstPath);

		dir sf_parent = sf.parent;
		// delete child
		sf_parent.childs.delete(sf);

		// add child on df
		mytree.add(df, sf);

	}

	int cmd_find(char[] path) {
		dir f = myfind(path);
		return f.childcnt;
	}
}
