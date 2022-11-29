package event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EvebtTest extends JFrame {
	JButton bt;
	JTextField t;
	JPanel p;
	int x;
	int y;
	
	public EvebtTest() {
		bt=new JButton("버튼");
		t=new JTextField(20);
		p=new JPanel() { //앞에 부모, 뒤는 이름이 없는 자식
			public void paint(Graphics g) {
				g.clearRect(0, 0, 290, 300); //패널만한 크기의 사각형으로 전체 배경색을 칠하기
				g.drawOval(x, y, 100, 100);
			}
		};
		p.setPreferredSize(new Dimension(290,300));
		p.setBackground(Color.YELLOW);
		
		setLayout(new FlowLayout());
		add(bt);
		add(t);
		add(p);
		setSize(300, 400);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//버튼과 리스너 연결_이벤트처리코드를 안에 넣어버리자
		bt.addActionListener(new ActionListener() { //부모에 해당
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("눌렀어!!");
				//내부익명클래스는 외부클래스의 멤버들을 자기 것처럼 쓴다
				x+=2;
				y+=2;
				p.repaint(); //다시그리기 update()->paint()
			}
		});
		
		//텍스트 필드와 리스너와 연결
		//이벤트구현시, 개발자가 재정의할 메서드 수가 3개 이상 넘어가면
		//sun에서 이미 해당 인터페이스를 구현해놓은 즉 재정의해놓은 객체를 지원해주는데
		//이 객체들을 가리켜 어댑터라한다
		//KeyListener 를 우리 대신 구현한 어댑터 -> KeyAdapter 클래스
		//WindowListener 를 대신 구현한 어댑터 -> WindowAdapter 클래스
		t.addKeyListener(new KeyAdapter() {
			//우리가 짊어질 재정의 의무를 Adapter들이 재정의 했으므로
			//개발자는 필요한 메서드만 다시 제정의 하면 된다
			public void keyReleased(KeyEvent e) {
				System.out.println("키 눌렀어!");
			}
		});
		
		//윈도우와 리스너와 연결
		this.addWindowListener(new WindowAdapter() {
			//윈도우 x버튼 누를 때(윈도우창 닫을 때)
			public void windowClosing(WindowEvent e) {
				//프로세스 종료
				System.exit(0); //프로세스 종료
			}
		});
	}
	
	
	public static void main(String[] args) {
		new EvebtTest();
	}
}
