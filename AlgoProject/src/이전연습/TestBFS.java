package 이전연습;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBFS {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	

/*
......
......
......
......
......
......
......
......
.Y....
.YG...
RRYG..
RRYGG.
*/

	
	char[][] map ;
	int[][] visitmap = new int[12][6];
	int VISITED = 1;
	int NOTVISITED = 0;

	@Test
	public void test() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		map = new char[12][6];
		
		
		for (int i = 0 ; i < 12 ; i ++) {
			s = br.readLine();
			map[i] = s.toCharArray();
		}		
	
		
		for (int y = 0 ; y < 12 ; y++) {
			for (int x = 0 ; x < 6 ; x++) {
				
				if (map[y][x] == 'R' && visitmap[y][x] == NOTVISITED) { 
					int v = bfs(y,x);
					
					if ( v < 3) { 
						continue;
					}
					
					System.out.println("R count = " + v);
					
				}
			}
		}
		
		
	}
	
	int[] dy = {1,0,-1,0};
	int[] dx = {0,1,0,-1};
	
	public int bfs(int y, int x){
		Queue<Node> q = new LinkedList<Node>();
		q.offer(new Node(y,x,map[y][x]));		
		int c = 0 ; 
		
		while(!q.isEmpty()) {
			
			Node n = q.poll();
			
			for (int i = 0 ; i < 4 ; i++){
				int y1  = n.y + dy[i];
				int x1  = n.x + dx[i];
				
				if ( y1 < 0 || y1 >= 12) { 
					continue; 
				}
				if ( x1 < 0 || x1 >=6 ) { 
					continue; 
				}
				
				if ( map[y1][x1] == n.c && visitmap[y1][x1] == NOTVISITED ) {
					visitmap[y1][x1] = VISITED; 
					q.add( new Node( y1, x1, map[y1][x1]) );
					c++;
				}
			}
			
		}
		
		return c;
		
	}
	
	class Node{
		int y;
		int x;
		int c;
		Node (int y,int x ,int c){
			this.y=y;
			this.x=x;
			this.c=c;
		}
	}
	
	
	@Test
	public void t() {
		Queue<Node> q = new LinkedList<Node>();
		Node e = new Node(1,1,5);
		q.add(e);
		
		Node x = q.poll();
		System.out.println(x.c);
	}

}
