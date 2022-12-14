package network.multi.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

//소켓을 보관하고 메세지를 주고 받기 위한 스트림을 보유한다!
public class ServerMessageThread extends Thread{
	Socket socket;
	InputStream is; //바이트기반 입력스트림
	InputStreamReader reader; //문자기반 입력 스트림
	BufferedReader buffr; //버퍼처리된 문자기반 입력 스트림
	
	OutputStream out; //바이트기반 출력스트림
	OutputStreamWriter writer; //문자기반 출력스트림
	BufferedWriter buffw; //버퍼처리된 문자기반 출력스트림
	MultiServer multiServer;
	
	boolean flag=true;
	
	public ServerMessageThread(MultiServer multiServer, Socket socket) {
		this.multiServer=multiServer;
		
		//넘겨받은 소켓을 이용하여 스트림 뽑기!!
		this.socket=socket; //소켓 보관해두기
		try {
			//듣기용 스트림
			is=socket.getInputStream();
			reader=new InputStreamReader(is);
			buffr=new BufferedReader(reader);
			
			//말하기용 스트림
			out=socket.getOutputStream();
			writer=new OutputStreamWriter(out);
			buffw=new BufferedWriter(writer);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//듣는 메서드
	public void listen() {
		String msg=null;
		try {
			msg=buffr.readLine();
			for(int i=0;i<multiServer.vec.size();i++) { //'나'도 포함되어 있기 때문에 같이 반복문 돌리자
				//다른 스레드의 sendMsg(msg)도 호출하자!!
				//다른 스레드는 Vector에 보관되어 있으므로 벡터에 접근해보자
				ServerMessageThread smt=multiServer.vec.get(i);
				
				smt.sendMsg(msg); //방금 수신한 메세지를 다시 클라이언트에게 보내기(출력) --> '나'
				
			}
			
			//서버에 로그 출력
			multiServer.area.append(msg+"\n");
			
		} catch (IOException e) {
			flag=false; //쓰레드 소멸시키기 위해서
			multiServer.removeClient(this);
			//e.printStackTrace();
		}
	}
	
	//말하는 메서드
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n"); //"\n"을 주지않으면 무한대기상태에 빠지게 됨
			buffw.flush(); //(버퍼) 처리된 (출력)스트림의 경우
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//개발자는 독립수행하고자 하는 코드를 run에 명시해야 한다
	public void run() {
		while(flag) { //스레드 소멸 방지하기 위함
			listen();
		}
	}
}
