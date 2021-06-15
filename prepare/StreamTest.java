import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StreamTest {

	@Test
	public void test_StreamBefore1() {
		int n = 2;
		System.out.println((n%2==1) || (n>=6 && n<=20) ? "Weird" : "Not Weird");
		
	}
	@Test
	public void test_StreamBefore() {
		// Stream 사용 전
		String[] nameArr = {"IronMan", "Captain", "Hulk", "Thor"};
		List<String> nameList = Arrays.asList(nameArr);

		// 원본의 데이터가 직접 정렬됨
		Arrays.sort(nameArr);
		Collections.sort(nameList);

		for (String str: nameArr) {
		  System.out.println(str);
		}

		for (String str : nameList) {
		  System.out.println(str);
		}		
		
	}
	
	@Test
	public void test_Stream() {
		// Stream 사용 후 
		String[] nameArr = {"IronMan", "Captain", "Hulk", "Thor"};
		List<String> nameList = Arrays.asList(nameArr);		
		
		// 원본의 데이터가 아닌 별도의 Stream을 생성함		
		Stream<String> nameStream = nameList.stream(); 
		Stream<String> arrayStream = Arrays.stream(nameArr);
		
		nameStream.sorted().forEach(System.out::println);
		arrayStream.sorted().forEach(System.out::println);
	}	

}
