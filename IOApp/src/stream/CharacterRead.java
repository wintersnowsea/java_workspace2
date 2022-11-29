package stream;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CharacterRead extends JFrame {
	JButton bt;
	JTextArea area;
	FileInputStream fis;

	public CharacterRead() {
		bt=new JButton("읽기");
		area = new JTextArea();
		area.setPreferredSize(new Dimension(480,550));
		Font font=new Font("Verdana", Font.BOLD, 30);
		area.setFont(font);
		setLayout(new FlowLayout());
		add(bt);
		add(area);
		
		setVisible(true);
		setSize(500, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼과 리스너와의 연결
		//내부익명클래스란?
		//클래스 코드 안에 또 다은 이름없는 클래스를 둘 수 있는데
		//이러한 용도의 클래스를 가리켜 내부 익명클래스라 한다
		//내부익명클래스는 특히 이벤트구현시 재사용성이 없는 클래스를
		//물리적인 .java 파일로까지 만들필요가 없으므로 자주 사용된다
		//new 뒤에 부모자료형이 오고 뒤에 오는 {}를 자식클래스로 봄
		//내부익명클래스의 장점 - 내부익명클래스 자신이 소속되어 있는 외부 클래스의 멤버변수를
		//내것처럼 맘대로 접근할 수 있다
		bt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//System.out.println("test");
					//area.append("눌렀어?");
					readFild();
				}
			});
			
	}
	
	//메모장 파일 읽기(한글도 포함된)
	public void readFild() {
		//지금까지 써왔던 FileInputStream은 바이트 기반 스트림이었다
		//따라서 데이터 처리를 1byte 씩 처리한다
		//때문에 처리대상 파일에 영분자가 포함되어 있을 경우 문자화 시켜도 아무런 문제 가 없었다
		//하지만 한글의 경우는 2byte로 구성되어 있으므로 문자화 시킬 경우 한글을 제대로 표현하지 못한다(깨진다)
		//주의) 읽어드린 1byte의 데이터를 char형으로 변경할 때 깨지는 것 뿐이지
		//파일 복사시 원본을 그대로 복원하는 경우는 중간에 문자로 변환하는 것이 아니기 때문에
		//깨질일 없다
		//읽어들인 데이터를 대상으로 한글로 변환하기 위해서는 문자기반 스트림을 이용하면 된다
		//FileInputStream fis=null;
		FileReader reader=null; //전세계 모든 문자가 깨지지 않는 문자인식 스트림
		String path = "C:/java_workspace2/IOApp/data/memo.txt";
		
		//스트림을 생성하자!
		try {
			//fis = new FileInputStream(path);
			reader = new FileReader(path);
			System.out.println("스트림 생성 성공");
			
			int data=-1;
			
			data=reader.read(); //1byte 읽기
			System.out.println(Character.toString((char)data));
			data=reader.read(); //1byte 읽기
			System.out.println(Character.toString((char)data));
			data=reader.read(); //1byte 읽기
			System.out.println(Character.toString((char)data));
			data=reader.read(); //1byte 읽기
			System.out.println(Character.toString((char)data));
			data=reader.read(); //1byte 읽기
			System.out.println(Character.toString((char)data));
			data=reader.read(); //1byte 읽기
			System.out.println(Character.toString((char)data));
			data=reader.read(); //1byte 읽기
			System.out.println(Character.toString((char)data));
			data=reader.read(); //1byte 읽기
			System.out.println(Character.toString((char)data));

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new CharacterRead();
	}
}
