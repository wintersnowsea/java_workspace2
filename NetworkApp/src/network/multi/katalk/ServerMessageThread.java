package network.multi.katalk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//접속한 클라이언트마다 1:1 대응하여 대화를 주고 받는 스레드
//이 스레드는 대화를 주고받기 위해서는 Stream이 필요하고,
//Stream을 얻기 위해서는 Socket이 필요하다
public class ServerMessageThread extends Thread{
	Socket socket;
	//닫는것에 대한 부담은 개발자가 할 필요는 없다!
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag=true; //이 쓰레드는 생사 여부를 결정짓는 논리값
	ChatServer chatServer;
	
	public ServerMessageThread(ChatServer chatServer, Socket socket) {
		this.chatServer=chatServer;
		this.socket=socket;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//듣기
	public void listen() {
		String msg=null;
		try {
			msg=buffr.readLine(); //json 문자열로 받아짐, 따라서 분석하려면 파싱할 필요가 있음
			
			//로그남기기
			chatServer.area.append(msg+"\n");
			
			//broadCasting : 다수의 사용자에 동시에 메세지를 보낸 것
			for(int i=0;i<chatServer.vec.size();i++) {
				ServerMessageThread smt=chatServer.vec.get(i);
				smt.sendMsg(msg); //접속한 모든 자의
			}
			
		} catch (IOException e) {
			//사용자가 대화 중단하고 나갔으므로, 추후 명단에서 제거할 것이고
			//현재 쓰레드는 소멸시켜야 한다!
			chatServer.vec.remove(this);
			chatServer.area.append("현재 참여중인 회원 수 : "+chatServer.vec.size()+"\n");
			flag=false;
			
			//e.printStackTrace();
		}
	}
	
	//말하기
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n"); //버퍼처리된 아이는 한 줄의 끝임을 알려주는 "\n"을 꼭 붙여줘야 한다
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//죽지않고 지속적으로 듣고 말하고를 반복한다
	//단, 사용자가 접속을 끊고 나가면 이 쓰레드는 죽어야한다
	//따라서 while()문 실행여부를 결정짓는 논리값이 필요하다
	public void run() {
		while(flag) {
			listen();
		}
	}
	
}
