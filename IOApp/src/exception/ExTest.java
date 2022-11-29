package exception;

public class ExTest {
	
	public ExTest() {
		int[] arr=new int[3];
		try{
			arr[0]=1;
			arr[1]=2;
			arr[2]=3;
			arr[3]=4; //비정상 종료
			System.out.println("안녕");
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("배열의 크기를 넘어섰어");
		}
		
	}
	
	public static void main(String[] args) {
		new ExTest();
	}
}
