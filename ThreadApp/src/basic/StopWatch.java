package basic;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import util.StringUtil;

//초를 카운트하는 스탑워치 구현하기
public class StopWatch extends JFrame {
	//지금까지 흔히 얘기해왔던 자바의 실행부는 사실 시스템에 의해 제공되는 메인 쓰레드 였다
	//즉 자바는 쓰레드기반 언어다
	//메인쓰레드는 개발자가 정의하는 객체가 아니라서 동작에 제한이 있다
	//메인쓰레드의 목적은 프로그램의 운영에 있다
	//[절대하지 말아야 할 것]
	//1) 무한루프에 빠지게 해서는 안된다
	//2) 대기상태에 빠지게 해서는 안된다
	//특히 메인쓰레드는 GUI 프로그램에서 이벤트를 감지하거나
	//화면에 그래픽 처리를 담당하는 등의 역할을 수행하므로
	//개발자가 정의한 쓰레드로 이벤트나 GUI처리를 하는 것은 다른 언어에서는 금지사항이다
	//ex) 아이폰, 안드로이드에서는 아예 에러사항이다!
	JButton bt;
	JLabel la;
	Thread thread;
	int sec; //초를 증가시킬 인스턴스 변수
	int min; //분을 증가시킬 인스턴스 변수
	boolean flag=false; //스탑워치의 동작여부를 결정하는 논리값
	
	public StopWatch() {
		bt=new JButton("Start");
		la=new JLabel("00:00", SwingConstants.CENTER);
		thread=new Thread() {
			//내부입력클래스, 이름없는 클래스
			public void run() {
				while(true) { //언제나 무한루프
					printTime(); //시작,정지 조절하기 위해 따로 메서드 정의
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
		};
		thread.start(); //쓰레드를 Runnable상태로 진입!!
		
		la.setPreferredSize(new Dimension(480,250));
		la.setFont(new Font("Verdana", Font.BOLD, 120));
		
		setLayout(new FlowLayout());
		add(bt);
		add(la);
	
		
		setSize(500,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼과 리스너 연결
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag=!flag;
				//버튼의 텍스트를 전환하기 (Stop, Start)
				String title=(flag)? "Stop":"Start";
				bt.setText(title);
			}
		});
	}
	
	public void printTime() {
		if(flag) {
			sec++;
			if(sec>=60) {
				sec=0;
				min++; //분 증가
			}
			
			la.setText(StringUtil.getNumString(min)+":"+StringUtil.getNumString(sec));
		}
	}
	
	public static void main(String[] args) {
		/*메인쓰레드 확인
		int[] arr=new int[2]; //비어있는 배열
		arr[2]=5;*/
		new StopWatch();
	}
}
