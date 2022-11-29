package stream;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

/*리스너 클래스를 프레임에 두지 않고 일부러 별도의 객체로 분리시켜보자*/
public class MyActionListener implements ActionListener {
	JTextArea area; //null -> 값이 비어있음l
	
	//생성자로 넘겨받자! 즉 새로 area를 생성하면 안됨
	public MyActionListener(JTextArea area) {
		this.area=area;
	}
	
	public void actionPerformed(ActionEvent e) {
		//System.out.println("눌렀어?");
		area.append("눌렀어?\n");
	}
	
}
