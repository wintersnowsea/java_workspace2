/*순서없는 컬렉션 중 Map 을 학습한다*/
package collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MapTest {

	public static void main(String[] args) {
		//Kisses 초콜릿 연상하기
		//Key : String, Value : String
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("apple","사과");
		map.put("banana","바나나");
		map.put("orange","오렌지");
		
		//순서없는 것을 순서있게 처리하여 반복문 사용해보기
		Set<String> set=map.keySet(); //본체가 아닌 Key만 순서없게 모음
		Iterator<String> it=set.iterator(); //Key 값을 늘어뜨리자 Iterator
		
		while(it.hasNext()) {
			String key=it.next();
			//System.out.println(key); //중간점검
			String value=map.get(key); //Key값을 이용해 Value를 뽑아내자
			System.out.println(value);
		}
		
		
		
	}
}
