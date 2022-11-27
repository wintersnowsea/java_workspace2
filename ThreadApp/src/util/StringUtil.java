package util;

public class StringUtil {
	//넘겨받은 숫자가 1자리 수이면, 앞에 0 붙이기
	//누군가가 이 메서드를 호출하면, 처리결과를 반환받는다
	public static String getNumString(int num) {
		String str=null;
		if(num<10) { //한자리수
			str="0"+num; //05
		}else { //두자리수
			str=Integer.toString(num); //int를 String로 변환, Wrapper적용
		}
		return str;
	}
	
	/*
	 * public static void main(String[] args) { String result=getNumString(12);
	 * System.out.println(result); }
	 */
	
}
