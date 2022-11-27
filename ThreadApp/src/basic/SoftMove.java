package basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*공이 지정된 위치로 부드럽게 움직이기*/
public class SoftMove extends JFrame{
	JButton bt;
	JPanel p_north ,p_center;
	Thread thread;
	double x;
	double y=30;
	double targetX;
	double targetY=30;
	double a= 0.08;
	
	public SoftMove() {
		bt=new JButton("움직이기");
		p_north=new JPanel();
		p_center=new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D) g;
				
				//지우고
				g2.setColor(Color.YELLOW);
				g2.fillRect(0, 0, 600, 600);
				
				//새로그리기
				g2.setColor(Color.RED);
				g2.fillOval((int)x, (int)y, 30, 30);
			}
		};
		

		thread=new Thread() {
			public void run() {
				while(true) {
					moveCircle();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		
		p_north.add(bt);
		
		add(p_north,BorderLayout.NORTH);
		add(p_center);
		
		setSize(600,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼 리스너 연결
		bt.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				targetX=500;
				targetY=400;
			}
		});
		
		p_center.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				targetX=e.getX();
				targetY=e.getY();
			}
		
		});
		
	}
	
	//원 움직이기
	public void moveCircle() {
		//감속도:현재위치=현재위치+비율(목표지점-현재위치)
		x=x+a*(targetX-x);
		y=y+a*(targetY-y);
		//System.out.println(x+a*(targetX-x));
		repaint();
	}



	public static void main(String[] args) {
		new SoftMove();
	}
}
