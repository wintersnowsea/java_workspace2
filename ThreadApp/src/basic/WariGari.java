package basic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*왔다갔다 하는 공 만들기*/
public class WariGari extends JFrame {
	JPanel p;
	Thread thread;
	int x; //원의 x축 좌표
	int y=100; //원의 y축 좌표
	boolean flag=true;
	
	public WariGari() {
		p=new JPanel() { //직접그리기위해 내부입력
			//AWT에서는 paint()를 사용하고
			//Swing 에서는 paintComponent를 사용하자
			//Swing에서도 paint()메서드 호출은 가능하지만,
			//Swing에 최적화되어 있지는 않다!!
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g; //down casting
				
				//기존그림 삭제 후
				g2.setColor(Color.YELLOW);
				g2.clearRect(0, 0, 600, 500);
				
				
				//새로그리기
				g2.setColor(Color.RED);
				g2.fillOval(x, y, 30, 30);
			}
		};
		//쓰레드 정의
		thread=new Thread() {
			public void run() {
				while(true) { //무한루프
					moveCircle();
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start(); //Runnable로 진입
		
		add(p); //BorderLayout.CENTER에 붙음 (대왕만하게)
		setSize(600,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//원 움직이기
	public void moveCircle() {
		//500에 도달하면 다시 돌아가게
		//0을 만나면 다시 증가
		int velX=(flag)? 2:-2;
		x+=velX;
		if(x>=500 || x<=0) {
			flag=!flag;
		}

		p.repaint(); //update() --> paintComponent() 호출			
	}
	
	public static void main(String[] args) {
		new WariGari();
	}
}
