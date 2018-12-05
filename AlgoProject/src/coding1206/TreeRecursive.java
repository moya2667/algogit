package coding1206;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TreeRecursive {

	// Tree 구조 및 Linkedlist 및 노드화
	@Test
	public void test() {
		node root = new node("0".toCharArray());
		tree t = new tree(root);
		node one = t.addchild(root, "1".toCharArray());
		node two = t.addchild(root, "2".toCharArray());
		node third = t.addchild(root, "3".toCharArray());

		node oneone = t.addchild(one, "11".toCharArray());

		t.printTree(root, 0);

	}

	class tree {
		node root = null;

		tree(node r) {
			root = r;
		}

		public node addchild(node n, char[] chars) {
			return n.childlist.add(n, chars);
		}

		public void printTree(node r, int depth) {
			if (r == null)
				return;

			for (int i = 0; i < depth; i++)
				System.out.print("+");

			System.out.println(new String(r.data));

			node h = r.childlist.head;

			if (h != null) { //고려 
				printTree(h, depth + 1);

				while (h != null) { //고려 
					h = h.next;
					printTree(h, depth + 1);
				}

			}

		}

	}

	class link {
		node head, tail;

		link() {
			head = null;
			tail = null;
		}

		node add(node p, char[] d) {
			node ne = new node(d);
			ne.parent = p;
			if (head == null) {
				head = ne;
				tail = ne;
			} else {
				tail.next = ne;
				ne.prev = tail;
				tail = ne; // 고려
			}
			return ne;
		}
	}

	class node {
		node parent;
		node prev, next;
		char[] data;
		link childlist = null;

		node(char[] d) {
			childlist = new link();
			data = new char[d.length];
			for (int i = 0; i < d.length; i++) {
				if (d[i] == '\0')
					break;
				data[i] = d[i];
			}
		}
	}

}
