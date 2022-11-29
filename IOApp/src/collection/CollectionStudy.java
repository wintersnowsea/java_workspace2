/*Collection Framework 이란?
 * - 객체를 모아서 처리할 때 유용한 api들
 * 
 * sun에서는 이 세상의 모든 객체들이 모여있는 모습을 크게 2가지로 구분
 * 1) 순서있는 집합
 * 		List 유형(배열과 거의 같다)
 * 		-차이점 : (1) 자바의 배열은 기본자료형, 객체자료형 등 모든 자료형을 대상으로 하지만
 * 				   		List는 오직 객체만을 담는다
 * 				   (2) 자바의 배열은 생성시 그 크기를 명시해야 하므로 크기가 고정되어 있지만
 * 						컬렉션들은 js시절처럼 크기가 동적으로 변할 수 있다 (유연하다)
 * 	
 * 2) 순서없는 집합
 * 	    Set 유형
 * 		Map 유형
 * */

package collection;

import java.util.ArrayList;

import javax.swing.JButton;

public class CollectionStudy {
	
	public static void main(String[] args) {
		/*List를 사용해본다
		 * 순서있는 집합을 처리할 때 사용하는 대표적인 자료형
		 * 웹 개발 시 압도적으로 많이 씀
		 * */
		//Generic 타입 : 컬렉션에서 해당 컬렉션객체의 자료형을
		//특정 자료형만으로 한정지을 수 있는 방법
		//1) 객체가 섞여들어갈 수 없도록 컴파일 타임에 방지한다
		//2) 꺼낼 때 번거로운 형변환 과정이 필요 없다
		ArrayList<String> list=new ArrayList<String>(); //String인 ArrayList로 고정
		list.add("apple");
		list.add("banana");
		list.add("berry");
		//list.add(new JButton()); //String형으로 한정을 지었기 때문에 불가능
		System.out.println(list.size()); //배열의 length와 같음
		
		/*for(int i=0;i<list.size();i++) {
			String obj=(String) list.get(i); //배열의 값이 Object형이기 때문에 형변환 해줌
			System.out.println(obj);
		}*/
		
		//java 5 버전부터 improved for 문이 추가됨
		//객체를 대상으로 한 반복문을 좀 더 간결하게
		for(String fruit : list) {
			System.out.println(fruit);
		}
	}
	
}
