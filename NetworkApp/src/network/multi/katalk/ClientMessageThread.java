package network.multi.katalk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//나 아닌 다른 사람들이 보낸 메세지를 실시간 청취하려면
//listen() 메서드가 무한루프로 동작하고
//이 동작을 메인쓰레드에서 하면 프로그램이 멈주기 때문에
//메인쓰레드 대신 무한루프로 청취할 쓰레드를 정의한다
public class ClientMessageThread extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	ChatPage chatPage;
	boolean flag=true;
	
	public ClientMessageThread(ChatPage chatPage, Socket socket) {
		this.chatPage=chatPage;
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
			msg=buffr.readLine();
			
			//json 문자열 파싱하기!!
			JSONParser jsonParser=new JSONParser(); //파서 생성
			try {
				JSONObject json=(JSONObject)jsonParser.parse(msg);
				String data=(String)json.get("data");
				//System.out.println("클라이언에 전송된 대화내용은 "+data);
				
				//data의 길이가 0보다 크다면, 이모티콘 사용이 아닌 대화메세지  요청이므로
				if(data.length()>0) {
					chatPage.addString(json); //메세지가 전송된 경우
				}else {
					chatPage.addIcon(json);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//chatPage.addIcon(); //이모티콘일 경우
			//로그남기기

			
		} catch (IOException e) {
			flag=false;
			//e.printStackTrace();
		}
	}
	
	//말하기
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//ClientMessageThread 클래스에서 run정의하기
	public void run() {
		while(flag) { //flag으로 바꿀 예정
			listen();
		}
	}
	
}
