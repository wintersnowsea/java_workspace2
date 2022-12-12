package network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//에코서버를 정의하자!!
//클라이언트가 소켓으로 접속을 시도하면 서버는 클라이언트의 접속을 감지해야 대화할 수 있는 소켓을 얻을 수 잇는데
//이와 같이 접속 감지용 소켓을 가리켜 ServerSocket이라 한다

//서버는 포트번호만 명시하면되고
//클라이언트는 id와 포트번호를 치고 들어가면 됨
public class EchoServer {
	int port=9999; //포트번호란 현재 컴퓨터에서 실행되고 있는
						// 모든 네트워크 프로그램 간 교섭이 발생하지 않도록 구분해주는 네트워크 구분 번호이다
						//개발자는 1~1024번은 사용하면 안된다 : 이미 시스템이 점유하고 있으니깐!!!
	ServerSocket server; //접속자를 감지하는 객체
	
	public EchoServer() {
		try {
			server=new ServerSocket(port);
			System.out.println("서버 가동 !!");
			
			//접속자를 감지해보자!!
			Socket socket=server.accept(); //접속자가 들어올 때까지 실행부는 무한대기상태에 빠진다
									// 따라서 System.out.println("접속자 감지"); 를 해도 나오지 않는다
			InetAddress inet=socket.getInetAddress();
			String ip=inet.getHostAddress(); // 접속자 ip
			
			System.out.println(ip+"접속자 감지");
			
			//현재 이 접속과 관련된 정보를 가진 socket 객체로부터 스트림을 뽑자
			InputStream is=socket.getInputStream();
			//문자기반 입력스트림
			InputStreamReader reader=new InputStreamReader(is);
			//버퍼처리된 문자기반 입력스트림
			BufferedReader buffr=new BufferedReader(reader); //3단계 빨대 완성!
			
			String msg=null;
			msg=buffr.readLine();
			
			System.out.println(msg);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoServer();
	}
}
