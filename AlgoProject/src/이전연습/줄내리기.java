package ��������;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class �ٳ����� {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//�׳� ��Ʈ�ϸ� �Ǵ±���..������
	@Test
	public void �ٳ�����(){ 
		//char[] t = {'x','x','x','2','3'} ;
		char[] t = {'1','x','2','x','x'} ;
		
		int len = t.length;
		for (int i = 0 ; i < len ; i ++){
			for (int j = 0 ; j < len-1 ; j ++){  //len-1 ���� 
				if ( t[j] == 'x' && t[j+1] != 'x' ){
					char temp;
					temp = t[j];   // i,j �峭ġ�°� �ƴ϶�, �׳� j�� j+1�� �峭ġ�°ſ���..����� ��
					t[j] = t[j+1];
					t[j+1] = temp; 
				}
			}
		}
		
		for (int i = 0 ; i < len ; i++){
			System.out.print(t[i]);
		}
		//System.out.println("t");
		
	}

}
