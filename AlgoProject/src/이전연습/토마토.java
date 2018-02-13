package 이전연습;

import java.io.FileInputStream;
import java.util.Scanner;

public class 토마토 {
    static public int[][] map ; 
    static public int[][] visitedmap;
    
    static int NX ;
    static int MY ; 
     
    public static void main(String[] args) throws Exception {
         
        Scanner sc = new Scanner(System.in);        
        NX  = sc.nextInt();
        MY  = sc.nextInt();
        
        int MAX_N = 1000;
        
        map = new int[MY+1][NX+1];
        visitedmap = new int[MY+1][NX+1];
                
        //변 길이 입력
        for (int i = 0 ; i < MY ; i++ ){
        	for (int j = 0 ; j < NX ; j++ ){
        		map[i][j] = sc.nextInt();
        		visitedmap[i][j] = map[i][j] ;
        	}	
        }
        
        int result = 0 ;
        
        point[] pointArray = new point[MY*NX];
        int pointarrayCount = 0; 
       
		//날짜에 따른 숙성도 변화 체크
        for (int i = 0 ; i < MY ; i++ ){
        	for (int j = 0 ; j < NX ; j++ ){
        		
        		if (map[i][j] == 1) { 
        			point startPoint = new point(i,j);
        			pointArray[pointarrayCount++] = startPoint;
        		}
        	}
        }
        
        boolean isAll = false;
        // map 이 모두 1이 존재할경우
        if ( pointarrayCount >= MY*NX) {
        	isAll = true; 
        	result = 0;
        }else {
        	result = bfs (pointArray , pointarrayCount);
        }
        
        //결과값 검사 
        int zeroCount = 0;
        for (int i = 0 ; i < MY ; i++ ){
        	for (int j = 0 ; j < NX ; j++ ){
        		
        		// 1이 존재할경우 
        		if ( visitedmap[i][j] == 1 && isAll == false) {
        			result = -1 ;
        			break; 
        		}
        		if  (visitedmap[i][j] == 0) {
        			zeroCount++;
        		}
        	}
        }
        
        //모두 0인 경우 
        if (zeroCount == MY*NX) result = -1;
        //하나라도 0 이 존재할경우 
	    if (zeroCount > 0 ) result = -1;
        
        System.out.println ( result ) ;
        
        return;
    }
    
    static int[] dy = { 0, 0, 1, -1 };
    static int[] dx = { 1, -1, 0, 0 };
    
    private static int bfs( point[] startArray , int arrayCount ) {
		// TODO Auto-generated method stub
    	queue q = new queue();
    	// 1(익은)을 담은 값을 Queue에 담는다. 
    	for (int i = 0 ; i < arrayCount ; i++) {
    		
    		visitedmap[startArray[i].y][startArray[i].x] = 999;
    		q.queuePush(startArray[i]);
    		
    	}
    	
    	//초기값 start 
    	//visitedmap[start.y][start.x] = 999;
    	
    	int day = 0;
    	
    	while (!q.queueIsEmpty()) {
    		
    	    day++;
    	    
    	    //debugging ( 한번 주변 검색 마다 변경된 토마토 숙성도 표현 )
            /*
    	    System.out.println(day-1);
            for ( int y = 0 ; y < MY ; y++) { 
                for ( int x = 0 ; x < NX ; x++) {
                	System.out.print(visitedmap[y][x] + " ");
                }
                System.out.println();
            }
            System.out.println("=================");
            */
    	    
    	    int queue_size = q.queueSize();
    	    
    	    for (int i = 0; i < queue_size; ++i) {
    	        point p = q.queuePop();
    	        
    	        for (int d = 0; d < 4; ++d) {
    	            point next  = new point();
    	            next.x = p.x + dx[d];
    	            next.y = p.y + dy[d];
    	            
    	            if ( next.x < 0 || next.x >= NX || next.y < 0 || next.y >= MY  ) 
    	            	continue;
    	            
    	            //System.out.println ( "x = " + next.x + " y = " + next.y);
    	            //상자가 감이 없을 경우 -->  벽이 존재할 경우 
    	            if (visitedmap[next.y][next.x] == -1) continue; 
    	            
    	            if (visitedmap[next.y][next.x] != 999){
    	            	//방문 기록 
            	        visitedmap[next.y][next.x] = 999;
            	        q.queuePush(next);
    	            }
    	            
    	        }
    	        
    	        
    	    }
    	}
    	
    	return day -1 ;
		
	}
    
    static public class point{
    	public int y ; 
    	public int x ;
    	public point(){};
    	
    	public point(int y, int x){
    		this.y = y; 
        	this.x = x;
    	}
    	
    }
    
    static public class queue{
    	int MAX_QUEUE_SIZE = MY*NX+1; 
    	//int[] queue = new int[MAX_QUEUE_SIZE + 1] ;
    	
    	point[] queue = new point[MAX_QUEUE_SIZE + 1] ;
    	int queue_head;
    	int queue_tail;

    	public void queueInit() {
    	    queue_head = queue_tail = 0;
    	}

    	public int queueSize() {
    	    return queue_tail - queue_head;
    	}

    	public boolean queueIsEmpty() {
    	    return queueSize() == 0;
    	}

    	public void queuePush(point t) {
    	    queue[queue_tail++] = t;
    	}

    	public point queuePop() {
    	    return queue[queue_head++];
    	}
    }

	
}