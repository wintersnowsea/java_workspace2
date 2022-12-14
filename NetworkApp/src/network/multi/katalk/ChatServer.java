package network.multi.katalk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatServer extends JFrame{
	JPanel p_north;
	JTextField t_port; //포트번호 입력
	JButton bt_start; //시작버튼
	JTextArea area;
	JScrollPane scroll;
	ServerSocket server; //접속자를 감지하기 위한 서버소켓
	Thread serverThread; //메인쓰레드 대신 대기상태를 처리하기 위한 개발자 정의 쓰레드
	Vector<ServerMessageThread> vec=new Vector<ServerMessageThread>(); //접속한 사용자마다 1:1 대응되는 MessageThread를 담을 컬렉션
	
	public ChatServer() {
		p_north=new JPanel();
		t_port=new JTextField("8888", 8);
		bt_start=new JButton("Start");
		area=new JTextArea();
		scroll=new JScrollPane(area);
		
		p_north.add(t_port);
		p_north.add(bt_start);
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		
		setSize(400,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//스레드 정의
		serverThread=new Thread() {
			public void run() {
				startServer();
			}
		};
		
		//버튼과 리스너 연결
		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//스레드를 runnable로 진입시키자
				serverThread.start();
			}
		});
	}
	
	//서버 가동 메서드
	public void startServer() {
		int port=Integer.parseInt(t_port.getText()); //포트번호 : 내 pc에서 돌아가는 네트워크를 구분하기 위한 번호
		try {
			server=new ServerSocket(port); //서버생성
			area.append("서버 가동!\n");
			
			while(true) {
				Socket socket=server.accept(); //접속자가 들어올때까지 대기상태에 빠진다
				area.append("접속자감지\n");
				ServerMessageThread smt=new ServerMessageThread(this,socket);
				smt.start(); //쓰레드 시작(Runnable 상태로 진입)
				
				//생성된 쓰레드를 명단에 추가
				vec.add(smt);
				area.append("현재 참여중인 회원 수 "+vec.size()+"\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}
}
