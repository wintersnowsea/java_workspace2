package basic;

public class ThreadTest2 {
	public static void main(String[] args) {
		/*
		 * for(int i=0;i<100;i++) { System.out.println(i); } //메인쓰레드
		 * 
		 * //개발자가 정의한 쓰레드 생성 //비동기방식 : 순서를 지키지 않는다 Thread t=new Thread() { public void
		 * run() { System.out.println("A"); } }; t.start();
		 * 
		 * System.out.println("B");
		 */
		//메인쓰레드 : 프로그램 운영을 담당,
		// 개발자가 생성한 것이 아니라 시스템이 이미 지원하는 실행부이다
		//GUI 프로그램에서는 만일 메인쓰레드를 루프나 대기상태에 두면
		//이벤트나 그래픽처리가 먹통, 즉 동작하지 못한다
		//일반적인 응용프로그래밍 분야에서는 아예 금지사항이다!
		while(true) {
			System.out.println("★");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
