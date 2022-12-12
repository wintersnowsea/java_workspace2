package network.unicast.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//텔넷과 웹브라우저는 접속까지는 가능하지만
//대화를 나누기 위한 전용툴이 아니므로 자바로 직접 개발해본다
public class UniServer extends JFrame {
	JPanel p_north;
	JTextField t_port;
	JButton bt_start; // 시작버튼
	JTextArea area;
	JScrollPane scroll;
	ServerSocket server;
	Thread serverThread; //메인 쓰레드 대신 대기상태에 빠질 접속자 감지용 쓰레드
	
	public UniServer() {
		p_north=new JPanel();
		t_port=new JTextField("9999",6);
		bt_start=new JButton("서버가동");
		area=new JTextArea();
		scroll=new JScrollPane(area);
		
		p_north.add(t_port);
		p_north.add(bt_start);
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		serverThread=new Thread() {
			public void run() {
				startServer();
			}
		};
		
		
		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start();
			}
		});
	}
	
	public void startServer() {
		int port=Integer.parseInt(t_port.getText());
		try {
			server=new ServerSocket(port);
			area.append("서버가동\n");
			
			//접속자를 무제한 받는다
			//접속자만 감지한다!
			while(true) {
				Socket socket=server.accept();
				String ip=socket.getInetAddress().getHostAddress();
				area.append(ip+"님 접속");
				
				//접속과 동시에 대화용 쓰레드가 탄생!!
				MessageThread mt=new MessageThread(socket);
				mt.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new UniServer();
	}
	
	
}
