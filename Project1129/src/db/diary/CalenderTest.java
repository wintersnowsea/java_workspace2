package db.diary;

import java.util.Calendar;

public class CalenderTest {
	
	
	
	public static void main(String[] args) {
		//날짜 객체는 생성시 디폴트로 현재 날짜 정보를 가진다
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.MONTH, 10); //월 조작 -> 11월
		cal.set(Calendar.DATE, 25); //날짜 조작
		
		
		int yy=cal.get(Calendar.YEAR); //현재 연도
		System.out.println("연도는 "+yy);
		
		//현재월
		int mm=cal.get(Calendar.MONTH);
		System.out.println("월은 "+mm); //월은 0부터 시작
		
		//날 수
		int dd=cal.get(Calendar.DATE); //현재날
		System.out.println(dd+"일");
		
		//요일
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		System.out.println("요일은"+dayOfWeek); //요일은 1부터 시작 (1:일요일)
	}
}
