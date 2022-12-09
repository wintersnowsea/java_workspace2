
public class StringTest {
	
	public static void appendString(String s) {//String 객체를 받음
		s += "abc";//받은 String객체 문자열을 바꿈
		System.out.println("s : "+s);
	}
	
	public static void main(String[] args) {
		String s1 = "";
		System.out.println("s1 : "+s1);
		appendString(s1);//s1을 넘김
		System.out.println("s1 : "+s1);// 이전에 넘긴 s1의 문자열이 바뀌지 않음
	}
}
