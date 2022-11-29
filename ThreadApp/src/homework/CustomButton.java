package homework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

//JButton 현재 우리의 상황에 맞지 않기 때문에
//우리가 원하는 버튼으로 그림을 재정의 하기 위함
public class CustomButton extends JButton {
	int width;
	int height;
	int index; //내가 몇번째 버튼인지 알 수 있는 기준값
	ShurekGallery shurekGallery;
	
	
	public CustomButton(ShurekGallery shurekGallery,int index, int width, int height) {
		//매개변수로 날라온 width, height를 변수로 받아서 사용
		this.width=width;
		this.height=height;
		this.shurekGallery=shurekGallery;

		this.setBorder(null);
		this.setPreferredSize(new Dimension(width,height));

		
		//버튼, 리스너 연결
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(index*-500+"부터 그릴꺼야");
				shurekGallery.targetX=index*-500;
			}
		});
		
	}
	protected void paintComponent(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.CYAN);
		g.fillOval(0, 0, width, height);
		
	}
	
}
