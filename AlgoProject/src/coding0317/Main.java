package coding0317;

import java.io.FileInputStream;
import java.util.Scanner;
 

/*
4
1 0 1 1
0 0 0 0
0 0 1 0
1 0 1 1

8
1 0 1 0 1 0 1 1
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
1 0 1 1 1 0 1 1
0 0 0 0 1 1 0 0
0 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0

16
1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0
1 0 1 1 0 0 0 0 1 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0

 */
public class Main {   
     
	static int MAX_N = 20;
	static int count = 0; 
	public static int[][] map ; 
	static int testcase = 0 ; 
	static int ALREADY_CHECK = 500;

     
    public static void main(String[] args) throws Exception {
         
        Scanner sc = new Scanner(System.in);
        int c  = sc.nextInt();       
        
        map = new int[c][c]; 
        for (int y = 0 ; y < c ; y++) {         
        	for (int x = 0 ; x < c ; x++) {
        		map[y][x] = sc.nextInt();
        	}
        }
        
        checkRectanle ( 0 , 0 , c , LUp) ;
        
//        while(c != 1) { 
//	        System.out.println( "좌측위 개수 = " + memo[c][LUp][LUp] );
//	        System.out.println( "우측위 개수 = " + memo[c][LUp][RUp] );
//	        System.out.println( "좌측아래 개수 = " + memo[c][LUp][LDown] );
//	        System.out.println( "우측아래 개수 = " + memo[c][LUp][RDown] );
//	        System.out.println( "Total Count = " + memo[c][LUp][Total] );
//	       c= c/2;
//        }
        
        for ( int i = c   ; i > 1 ; i = i/2) { 
        	
        	for ( int d = 0 ; d < 5 ; d++) { 
        		//for ( int count = 0 ; count < 5 ; count++) {
//        			System.out.println(i + " 의 " + d + " 방향" + "의 좌측위 개수 = " + memo[i][d][0]);
//        			System.out.println(i + " 의 " + d + " 방향" + "의 우측위 개수 = " + memo[i][d][1]);
//        			System.out.println(i + " 의 " + d + " 방향" + "의 좌측아래 개수 = " + memo[i][d][2]);
//        			System.out.println(i + " 의 " + d + " 방향" + "의 우측아래  개수 = " + memo[i][d][3]);
//        			
        			System.out.println(i + " 의 " + d + " 방향" + "의 총  개수 = " + memo[i][d][4]);
        		//}
        	}
        }
        
//        System.out.println(mMinusCount);
//        System.out.println(mZeroCount);
//        System.out.println(mOneCount);    
        
        return;
    }
    
    static private int mOneCount =0; 
    static private int mZeroCount =0;
    static private int mMinusCount =0;
    static private int[][][] memo = new int[50][5][5]; 
    
    static int LUp = 0 ;
    static int RUp = 1 ;
    static int LDown = 2 ;
    static int RDown = 3 ;
    static int Total = 4 ;
    
    private static int checkRectanle(int y, int x, int size , int direction) {
    	
    	if (size == 1) { 
    		//System.out.println("마지막까지 갔음");
    		if (map[y][x] == 0) {
    			mZeroCount++;
    			return 0;
    		}else if (map[y][x] == 1) {
    			mOneCount++;
    			return 1;
    		}
    	}
    	
    	int c0 = checkRectanle(y,x,size/2 , LUp);
    	int c1 = checkRectanle(y,x+size/2 , size/2 , RUp);
    	int c2 = checkRectanle(y+size/2,x,size/2 , LDown);    	
    	int c3 = checkRectanle(y+size/2,x+size/2 , size/2 , RDown);
    	
    	
    	//if (size != 1) {    		
    		//System.out.println(c0+c1+c2+c3);
    		memo[size][direction][LUp] = c0;
    		memo[size][direction][RUp] = c1;
    		memo[size][direction][LDown] = c2;
    		memo[size][direction][RDown] = c3;
    		memo[size][direction][Total] = c0+c1+c2+c3;
    	//}
    	
    	/*
    	if (size == 4) { 
    		System.out.println(c0+c1+c2+c3);
    	}
    	*/
    	
    	/*
    	if ( size != 1) { 
	    	memo[size][0] = c0;
			memo[size][1] = c1;
			memo[size][2] = c2;
			memo[size][3] = c3;
			memo[size][4] = c0+c1+c2+c3;
    	}
    	*/
		
    	
    	return c0+c1+c2+c3;
	}
}