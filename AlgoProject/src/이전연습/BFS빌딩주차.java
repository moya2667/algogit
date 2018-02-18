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

public class BFS빌딩주차 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/*
xxxxxx
xxxxoo
s+x+oo
xooxdo
xoo+xx
	*/
	
	int EAST  = 2; 
	int WEST  = 4;
	int SOUTH  = 3;
	int NORTH = 1;
	int HOLD = 0;
	char[][] map ;
	int[][] visited;
	int minDis = 0 ;
		
	@Test
	public void test() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		
		map = new char[5][6];
		visited = new int[5][6];		
		
		for (int i = 0 ; i < 5 ; i ++) {
			s = br.readLine();
			map[i] = s.toCharArray();
		}
		
		for (int y = 0 ; y < 5 ; y++){
			for (int x = 0 ; x < 6 ; x++){
				//start 
				if ( map[y][x] == 's') { 
					node n = new node( y, x , EAST ,0);
					bfs bf = new bfs();
					bf.start(n);
					System.out.println(bf.min);
					break;
				}
			}
		}
		
		
	}	
	
	int[] dy = { 999,1,0,-1,0};
	int[] dx = { 999,0,1,0,-1};
	
	class bfs {
				
		int min = 999999; 
		
		void start (node s) {
			
			Queue<node> q = new LinkedList<>();
			q.add(s); 
			
			while(!q.isEmpty()) {
				
				node t = q.poll();
				
				int y = t.y ;
				int x = t.x ; 
				int dir = t.dir;
				int distance = t.distance;
				
				//checking 우측 사이드에 d 가 존재하는가.				 
				if ( x < 6 && map[y][x+1] == 'd'){
					System.out.println("find = " + y + " , " + x + " " );
					if ( min > t.distance ) min = t.distance;
					continue; 
				}
					
				//일반도로일경우 
				if ( map[t.y][t.x] == 'x' || map[t.y][t.x] == 's'){
					
					if ( dir == EAST) { 
						x = x+1;						
					}else if (dir == WEST){
						x = x-1;
					}else if (dir == NORTH){
						y = y+1;
					}else if (dir == SOUTH){
						y = y-1;
					}
					
					if ( y == 5 || y < 0 || x < 0 || x ==6) continue; 
					
					if (visited[y][x] < 3) { 
						visited[y][x] = visited[y][x]+1; 
						q.add(new node(y,x,dir , distance+1));
						print();
					}
				}				
				//교차로 일경우 
				else if ( map[t.y][t.x] == '+'){
					//동서남북 다 넣기
					for ( int  i = 1 ; i <=4 ; i++) { 
						int y1 = y+ dy[i];
						int x1 = x+ dx[i];
						
						if ( y1 == 5 || y1 < 0 || x1 < 0 || x1 ==6) continue;
						
						if ( map[y1][x1] =='o') continue;
						
						if (visited[y1][x1] < 3) { 
							visited[y1][x1] = visited[y1][x1]+1; 
							q.add(new node(y1,x1,i,distance+1));
							print();
						}
						
					}
				}
				
			}
		}
		
		void print (){
			for (int y=0;y<5;y++){
				for (int x=0;x<6;x++){
					System.out.print(visited[y][x]);
				}
				System.out.println();
			}
			
			System.out.println("--------------");
		}
	}
	
	class node{		
		int y,x;
		int dir;		
		int distance;
		node (int y, int x , int dir , int distance){
			this.y = y;
			this.x = x;
			this.dir = dir;
			this.distance = distance;
		}
	}
	
}
