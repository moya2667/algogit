import org.junit.Test;

import junit.framework.Assert;

public class calcuator {
	@Test
	public void test2() {
		String myName = "3+2*2";
		String p = myName.replace(myName.charAt(2), 'x');
		System.out.println(p);
	}
	
	@Test
	public void calcuatoarTest() {
		calculate("3+2*2");
		//calculate(" 3/2 ");
		//calculate(" 3+5 / 2 ");
	}

	//안되는건구나.. (char --> 255 밖에 담을수 없고,,ㅋㅋㅋㅋㅋ 바복... StringBuildre 사용하자..)
	public int calculate(String s) {
		s = s.replaceAll(" ", "");		
		char[] covChars = s.toCharArray();
		char[] cov = new char[covChars.length];
		for (int i = 0; i < covChars.length ; i++) {
			if (covChars[i] == '*' || covChars[i] == '/') {
				cov[i-1] = covertCal(covChars, i);
				
				i = i + 2 ; //skip next value			}
			}
			else cov[i] = covChars[i];			
		}
		System.out.println(cov);

		return -1;
	}
	
	private String covertCal(char[] cov,int i) {
		char prev = cov[i-1];
		char next = cov[i+1];		
		int prevInt = Integer.parseInt(String.valueOf(prev));
		int nextInt = Integer.parseInt(String.valueOf(next));
		int ret = -1; 
		if ( cov[i] =='*' ){
			ret = prevInt*nextInt;
		}else if ( cov[i]=='/'){
			ret = prevInt/nextInt;
		}
		return Integer.toString(ret);
	}
	

	private String covertCal(String cov,int i) {
		char prev = cov.charAt(i-1);
		char next = cov.charAt(i+1);		
		int prevInt = Integer.parseInt(String.valueOf(prev));
		int nextInt = Integer.parseInt(String.valueOf(next));
		int ret = -1; 
		if ( cov.charAt(i) =='*' ){
			ret = prevInt*nextInt;
		}else if ( cov.charAt(i)=='/'){
			ret = prevInt/nextInt;
		}
		return Integer.toString(ret);
	}
}
