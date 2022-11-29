package event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ButtonBg extends JFrame{
	JPanel p;
	JButton bt_create, bt_bg;
	int x=0; //버튼 라벨에 순서를 적용 할 멤버변수
	
	JButton[] btn=new JButton[10];
	//배열은 크기가 고정되어 있으므로, 동적으로 채워질 버튼들을 감기엔 한계가 있다
	//따라서, 크기를 자유롭게 동적으로 바꿀 수 있는 컬렉션 프레임웍이 지원하는 객체 중
	//List 중 ArrayList를 사용해 본다
	ArrayList<JButton> list=new ArrayList<JButton>(); //size가 0인 상태

	
	public ButtonBg() {
		p = new JPanel();
		bt_create=new JButton("버튼생성");
		bt_bg=new JButton("배경변경");
		
		
		p.setPreferredSize(new Dimension(500,40));
		p.setBackground(Color.LIGHT_GRAY);
		setLayout(new FlowLayout());
		p.add(bt_create);
		p.add(bt_bg);
		add(p);
		


		
		setSize(500, 600);
		setVisible(true);
		
		//버튼 이벤트 적용
		bt_create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createButton();
			}
		});
		bt_bg.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				setBg();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	//생성된 버튼들을 대상으로 모든 버튼의 색상을 변경한다
	//자바 배열의 단점 : C, C# 등등 일반적인 프로그래밍 언어 배열의 특징은
	//생성시 그 크기를 정해야 한다는 단점이 있다
	//따라서 본 예제는 배열로 해결 불가능 함
	//sun에서는 객체를 모아서 처리하는 데 유용한 api 제공해주는데
	//이를 가리켜 Collection Framework 이라 하며, java.util 패키지에서 지원한다
	public void setBg() {
		/*for(int i=0;i<btn.length;i++) {
			btn[i].setBackground(Color.RED);
		}*/
		
		for(int a=0;a<list.size();a++) {
		JButton bt = list.get(a);	
		bt.setBackground(Color.RED);
		}
	}
	
	//버튼을 동적으로 생성하여, 화면에 붙이기
	public void createButton() {
		JButton bt=new JButton("버튼"+x);
		//btn[x]=bt; //배열에 담기
		list.add(bt); //js push()와 동일
		x++;
		add(bt);
		//개발자가 paint() 메서드를 재정의한 후 , 시스템에게 호출하도록 요청할 때는
		//repaint()메서드를 호출해야하지만,
		//GUI컴포넌트를 부착한 경우에는 paint() 재정의한 것이 아니므로
		//repaint()가 아닌 컴포넌트의 갱신을 요청해야함 updateUI();
		//refresh JFrame!! 프레임을 새로고침해줘!
		SwingUtilities.updateComponentTreeUI(this);
		
	}
	
	public static void main(String[] args) {
		new ButtonBg();
	}
}
