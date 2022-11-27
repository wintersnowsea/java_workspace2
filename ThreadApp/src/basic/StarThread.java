package basic;

//쓰레드를 정의한다!!
public class StarThread extends Thread {
	String star;
	
	public StarThread(String star) {
		this.star=star; //누군가 쓰레드를 new할 때 문자로 ★을 넘겨라
	}
	
	//개발자는 독립실행을 원하는 코드를 run()에 작성해놓기만 하면 된다
	public void run() {
		while(true) {
			System.out.println(Thread.currentThread().getName()+star);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
