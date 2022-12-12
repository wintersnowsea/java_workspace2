package network.echo.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

//텔넷과 웹브라우저는 접속까지는 가능하지만
//대화를 나누기 위한 전용툴이 아니므로 자바로 직접 개발해본다
public class EchoServer extends JFrame {
	JPanel p_north;
	JTextField t_port;
	JButton bt_start; // 시작버튼
	JTextArea area;
	JScrollPane scroll;
	int port=9999;
	ServerSocket server;
	//이 쓰레드가 없을 경우 메인쓰레드가 대기 상태에 빠지게 된다
	Thread serverThread; //접속자 대기를 위한 전용 쓰레드
	
	public EchoServer() {
		p_north=new JPanel();
		t_port=new JTextField(Integer.toString(port),6);
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
		
		//쓰레드 생성 및 재정의
		serverThread = new Thread() {
			public void run() {
				startServer();
			}
		};
		
		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start(); //쓰레드 시작, Runnable로 진입시킴
				bt_start.setEnabled(false); //한번 눌렀을 때 못쓰도록 막음
			}
		});
		
		
	}
	
	//클라이언트의 접속을 감지하기 위한 서버소캣을 생성하고 접속자를 기다리자!
	public void startServer() {
		try {
			server=new ServerSocket(port);
			area.append("서버생성\n");
			//메인 쓰레드는 1.루프 2.대기상태 에 빠트려서는 안된다
			//메인 쓰레드는 프로그래밍 운영 쓰레드이므로 그래픽처리, 이벤트 감지 등를 처리해야 하니깐..
			
			Socket socket=server.accept(); //접속자를 기다린다!
			
			InetAddress inet=socket.getInetAddress();
			String ip=inet.getHostAddress();
			area.append(ip+"님 접속\n");
			
			//소켓으로부터 입,출력 스트림 얻기
			BufferedReader buffr=null;
			InputStreamReader reader=null;
			InputStream is=null;
			
			BufferedWriter buffw=null;
			OutputStreamWriter write=null;
			OutputStream os=null;
			
			is=socket.getInputStream();
			reader=new InputStreamReader(is);
			buffr=new BufferedReader(reader);
			
			os=socket.getOutputStream();
			write=new OutputStreamWriter(os);
			buffw=new BufferedWriter(write);
			
			
			//클라이언트의 메세지 듣기 (입력)
			String msg=null;
			while(true) {
				//클라이언트의 메세지가 도착해야
				//아래의 readLine() 대기 상태가 풀린다
				msg=buffr.readLine();
				
				//서버에 로그 남기기
				area.append(msg+"\n");;

				//클라이언트의 메세지 말하기 (출력)
				buffw.write(msg+"\n");
				
				//버퍼처리된 출력스트림 계열은 반드시 flush
				buffw.flush(); //버퍼비우기
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		new EchoServer();
	}
	
	
}
