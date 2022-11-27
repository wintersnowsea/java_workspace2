package basic;

//카운터 증가시키기
public class ThreadTest extends Thread{
	int count;
	
	//쓰레드로 실행하고픈 코드를 run(0에 작성해 놓으면 jvm알아서 실행여부를 결정해준다
	//(개발자는 오직 맡기기만 하면 됨)
	//쓰레드의 생명주기에서 run()의 닫는 블레이스를 만나면 쓰레드는 소멸한다
	//이때 내부적으로 destroy()를 호출하며 죽는다
	public void run() {
		while(true) {
			count++;
			System.out.println(count);
			try {
				Thread.sleep(1000); //1000분의 1초까지 표현가능
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 			
		}
	}
	
	public static void main(String[] args) {
		ThreadTest t =new ThreadTest(); //쓰레드 생성
		t.start(); // jvm에게 맡기기(Runnable영역으로 진입)
	}
}