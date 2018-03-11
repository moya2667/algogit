package coding0311;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

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

public class PyoPyo {	
	
	static int[][] visited ; 
	static char[][] map ;
	static int NOTViSITED = 0 ; 
	static int ViSITED = 1 ;
	
	static Queue<node> changedlist = new LinkedList<>(); 
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		map = new char[12][6];
		visited = new int[12][6];
		for (int i = 0 ; i < 12 ; i ++) {
			s = br.readLine();
			map[i] = s.toCharArray();
		}
		
		boolean isDone = true; 
		while(isDone){ 
			for (int y= 0 ; y < 12 ; y++) { 
				for (int x= 0 ; x < 6 ; x++) {
					if (map[y][x] != '.' && visited[y][x] == NOTViSITED) {
						//4개 이상 연결되어 있다면,연결된 4개 이상 배열값을 'x' 로 변경한다 
						if (bfs(y,x)){
							updateMaptoX();
							isDone = false;
						}
					}					
				}
			}
			
			for (int y= 0 ; y < 12 ; y++) { 
				for (int x= 0 ; x < 6 ; x++) {
					System.out.print(map[y][x]);
				}
				System.out.println();
			}
			System.out.println("----------------");
			
			System.out.println("-----visited-----");
			for (int y= 0 ; y < 12 ; y++) { 
				for (int x= 0 ; x < 6 ; x++) {
					System.out.print(visited[y][x]);
				}
				System.out.println();
			}
			System.out.println("----------------");			
			
			//x값 찾아서 내리기 //어떻게 내려지.?
			//sort 하고 , 순서 변경된 배열 y열 의 'x'->'.'으로 고쳐버리면 된다. // 잘안되네..내일해봐야지.
			for (int x = 5 ; x <=0 ; x--){
				for (int i = 11 ; i <=0 ; i--){
					for (int j = 11 ; j <=0 ; j--){
						if (map[j][x] == 'x' && map[j-1][x] != 'x'){ 
							char temp = map[j-1][x];
							map[j-1][x] = map[j][x];
							map[j][x] = temp;
						}
					}
				}
			}
				
			for (int y= 0 ; y < 12 ; y++) { 
				for (int x= 0 ; x < 6 ; x++) {
					System.out.print(map[y][x]);
				}
				System.out.println();
			}
			System.out.println("----------------");
			
		}
		
	}
	
	static int[] dy = {1,0,-1,0};
	static int[] dx = {0,1,0,-1};
	
	public static boolean bfs(int y ,int x) {
		Queue<node> q = new LinkedList();
		q.add(new node(y,x , map[y][x])); 
		
		int isCharCount = 0; 
		while(!q.isEmpty()) { 
			node n = q.poll();
			
			for (int i = 0 ; i < 4 ; i++) { 
				int y1 = n.y + dy[i];
				int x1 = n.x + dx[i];
				
				if ( y1 >= 12 || y1 <0 || x1 >= 6 || x1 < 0) continue;
				
				if (map[y1][x1] == n.c && visited[y1][x1] == NOTViSITED) { 
					isCharCount++;
					visited[y1][x1] = ViSITED;
					q.add(new node(y1,x1,map[y1][x1]));
					changedlist.add(new node(y1,x1)); 
				}
			}
		}
		
		if (isCharCount >= 4) {
			return true;
		}
		
		//초기화
		changedlist = new LinkedList<>(); 
		return false;
	}
	
	public static void updateMaptoX(){
		while (!changedlist.isEmpty()){
			node n = changedlist.poll();
			map[n.y][n.x] ='x';
		}
	}
	
	static class node{		
		int y;
		int x; 
		char c;
		node(int y, int x){
			this.y = y;
			this.x = x;
		}
		node(int y, int x, char c){
			this.y = y;
			this.x = x;
			this.c = c; 
		}
	}

}
