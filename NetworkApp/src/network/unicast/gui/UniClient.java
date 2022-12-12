package network.unicast.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//텔넷과 웹브라우저는 접속까지는 가능하지만
//대화를 나누기 위한 전용툴이 아니므로 자바로 직접 개발해본다
public class UniClient extends JFrame {
	JPanel p_north;
	JComboBox<String> box_ip;
	JTextField t_port;
	JButton bt_connect; // 접속버튼
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south;
	JTextField t_input; //메시지 입력창
	JButton bt_send; //전송버튼
	Socket socket;
	
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public UniClient() {
		p_north=new JPanel();
		box_ip=new JComboBox<String>();
		t_port=new JTextField("9999",6);
		bt_connect=new JButton("접속");
		area=new JTextArea();
		scroll=new JScrollPane(area);
		p_south=new JPanel();
		t_input=new JTextField(15);
		bt_send=new JButton(" 전송");
		
		p_north.add(box_ip);
		p_north.add(t_port);
		p_north.add(bt_connect);
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		p_south.add(t_input);
		p_south.add(bt_send);
		add(p_south, BorderLayout.SOUTH);
		
		
		createIp(); //ip 주소
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});

		bt_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMsg();
			}
		});
		
		
		
	}
	
	public void createIp() {
		for(int i=3;i<=100;i++) {
			box_ip.addItem("172.30.1."+i);
		}
	}
	
	public void listen() {
		String msg=null;
		 try {
			msg=buffr.readLine();
			area.append(msg+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendMsg() {
		String msg=t_input.getText();
		
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		t_input.setText(""); //입력 초기화
		listen();
	}
	
	public void connect() {
		String ip=(String)box_ip.getSelectedItem();
		int port=Integer.parseInt(t_port.getText());
		try {
			socket=new Socket(ip, port); //접속
			
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new UniClient();
	}
	
	
}
