package collection;

import java.util.HashSet;
import java.util.Iterator;

//순서없는 컬렉션 자료형 중 Set을 학습해보자
public class SetTest {	
	public static void main(String[] args) {
		//순서가 없는 컬렉션
		HashSet<String> set=new HashSet<String>();
		set.add("apple");
		set.add("banana");
		set.add("lemon");
		
		for(String s : set) {
			System.out.println(s);
		}
		
		/*
		//순서없는 Set을 나열해보자
		//Iterator 이용하여
		Iterator<String> it= set.iterator();
		
		//그 다음요소가 존재하는 지 여부 알려줌
		while(it.hasNext()) { //존재여부 판단
			String fruit=it.next(); //한칸 이동
			System.out.println(fruit);
		}*/
	}
}
