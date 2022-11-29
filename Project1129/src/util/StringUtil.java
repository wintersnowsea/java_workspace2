package util;

public class StringUtil {
	// 넘겨받은 숫자가 1자리 수이면, 앞에 0 붙이기
	// 누군가가 이 메서드를 호출하면, 처리결과를 반환받는다
	public static String getNumString(int num) {
		String str = null;
		if (num < 10) { // 한자리수
			str = "0" + num; // 05
		} else { // 두자리수
			str = Integer.toString(num); // int를 String로 변환, Wrapper적용
		}
		return str;
	}

	/*--------------------------------------------------------------------
	 * 확장자 추출하여 반환받기
	 * -------------------------------------------------------------------*/
	public static String getExtend(String filename) {
		//String filename = "a.a...a..aaaa..a.png";

		int lastIndex = filename.lastIndexOf(".");
		System.out.println(lastIndex);
		
		return filename.substring(lastIndex+1, filename.length());
	}

	
	/*
	 * public static void main(String[] args) {
	 * System.out.println(getExtend("a.a...a..aaaa..a.png")); }
	 */
}