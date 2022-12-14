package network.multi.katalk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

public class ChatPage extends Page {
	JPanel content; //텍스트와 이모티콘이 붙여질 컨테이너!! -> 원래 TextArea였음
	JScrollPane scroll;
	
	JPanel p_south;
	JComboBox<String> box_emoti;
	JTextField t_input;
	JButton bt_send;
	
	ClientMessageThread cmt;
	
	public ChatPage(ClientMain clientMain) {
		super(clientMain);
		
		//가운데
		content = new JPanel();
		scroll = new JScrollPane(content);

		//남쪽
		p_south = new JPanel();
		box_emoti=new JComboBox<String>();
		t_input = new JTextField();
		bt_send = new JButton("전송");
		createEmoti();
		
		content.setBackground(Color.YELLOW);
		content.setPreferredSize(new Dimension(350,1500));
		
		scroll.setPreferredSize(new Dimension(380, 400));
		t_input.setPreferredSize(new Dimension(230, 35));
		
		
		p_south.add(box_emoti);
		p_south.add(t_input);
		p_south.add(bt_send);
		
		add(scroll);
		add(p_south, BorderLayout.SOUTH);
		
		setVisible(true);
		setPreferredSize(new Dimension(400,500));
		
		//t_input과 리스너 연결
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) { //엔터치면
					send();
				}
			}
		});
	}
	
	//이모티콘 채우기
	public void createEmoti() {
		box_emoti.addItem("이모티콘 선택");
		box_emoti.addItem("cryingicon.png");
		box_emoti.addItem("happyicon.png");
		box_emoti.addItem("kissicon.png");
	}
	
	//채팅서버 접속
	public void connect() {
		String ip="localhost";
		int port=8888;
		
		try {
			Socket socket=new Socket(ip,port);
			//접속성공과 함께 
			cmt=new ClientMessageThread(this,socket);
			cmt.start(); //스레드 가동 (Runnable 상태로 진입시킴)
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		//서버에 데이터 전송 시 메세지 뿐 아니라 사용자 정보도 함께 보내야 한다
		//따라서 복합된 형태의 데이터를 보내야 한다
		//우리만의 프로토콜 만들기
		//"id=batman msg=안녕 filename=cry.png"
		//StringBuffer sb=new StringBuffer(); //다중스레드 환경에서 동기화
		StringBuilder sb=new StringBuilder();
		
		String id=clientMain.chatMember.getId();
		String name=clientMain.chatMember.getName();
		String msg=t_input.getText(); //대화 메세지
		String filename=(String)box_emoti.getSelectedItem();
		
		sb.append("{");
		sb.append("\"member\":{");
		sb.append("\"id\":\""+id+"\",");
		sb.append("\"name\":\""+name+"\"");
		sb.append("},");
		sb.append("\"data\":\""+msg+"\",");
		sb.append("\"filename\":\""+filename+"\"");
		sb.append("}");
		
		cmt.sendMsg(sb.toString()); //제이슨 문자열 보내기!
		
		//다시 입력값 초기화
		t_input.setText("");
	}
	
	//대화내용이 텍스트일 때는 JTextArea를 content라는 패널에 부탁할 예정
	public void addString(JSONObject json ) {
		JSONObject member=(JSONObject)json.get("member");
		String id=(String)member.get("id");
		String name=(String)member.get("name");
		String data=(String)json.get("data");
		
		JTextArea area=new JTextArea();
		area.setText(name+" 님: "+data);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		
		//채팅문자열의 총 길이
		float lineCount=(float)Math.ceil((double)data.length()/30); //정수와 정수는 결과도 정수!
		
		area.setPreferredSize(new Dimension(350, ((int)lineCount*15)));
		
		content.add(area); // 패널에 부착
		content.updateUI(); //갱신
	}
	
	public void addIcon(JSONObject json) {
		JSONObject member=(JSONObject)json.get("member");
		String id=(String)member.get("id");
		String name=(String)member.get("name");
		String filename=(String)json.get("filename");
		
		//패널에 이미지를 부착할 예정
		URL url=this.getClass().getClassLoader().getResource("res/image/"+filename);
		
		try {
			Image image=ImageIO.read(url);
			image=image.getScaledInstance(30, 30, 0);
			ImageIcon icon=new ImageIcon(image);
			//이미지는 UI 컴포넌트가 아니므로 패널에 직접 붙일 수 업삳
			//즉 버튼, 라벨 등등 사용자가 UI조작할 수 있도록 UI컴포넌타만이 붙여질 예상이다
			JLabel la=new JLabel(icon);
			la.setPreferredSize(new Dimension(350,70));
			content.add(la);
			content.updateUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
