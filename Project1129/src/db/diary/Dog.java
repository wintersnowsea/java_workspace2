package db.diary;

//싱글톤
public class Dog {
	String name="치와와";
	private static Dog instance; //변수명을 instance로 주는 이유 : 개발자들의 습관같은 것
	
	private Dog() {
	}
	
	//여러번 new하지 못하도록 은닉화된 클래스를 다른 클래스에서 사용가능하도록 하는 메서드
	public static Dog getInstance() {
		//만일 instance변수가 null이면 여기서 new를 해주자
		if(instance==null) {
			//나는 접근이 가능하므로 인스턴스 생성해버리자!
			instance=new Dog();
		}
		return instance;
	}
}
