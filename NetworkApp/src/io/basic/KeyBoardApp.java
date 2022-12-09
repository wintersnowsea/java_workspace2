package io.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

//지금까지 우리가 다루어왔던 입출력은 파일에 국한되었으나
//사실 실행중인 프로그램으로 데이터를 입력받거나 출력하는 것은
//파일뿐만아니라 상당히 다양한 대상으로 이루어질 수 있다
//이번 예제에서는 키보드를 대상으로 입력을 처리해보자!
public class KeyBoardApp {
	//Sun에서는 추후 어떤 디바이스가 개발될 지 예측할 수 없으므로
	//KeyBoard를 비롯한 다양한 디바이스들에 대한 스트림 처리 클래스를 각각 정의할 수 없으며
	//스트림 객체 중 가장 추상적이면서 최상위 객체인
	//InputStream과 OutputStream으로 이 문제를 해결하고 있다
	
	public static void main(String[] args) {
		//그냥 입력 스트림
		
		
		//키보드 관련된 스트림은 개발자가 생성하지 않아도 이미 생성되어 있기 때문에
		//그냥 얻어오면 된다
		//키보드 스트림은 OS 켜질 때 생성되며,
		//개발자는 단지 생성된 스트림을 얻어오면 된다
		InputStream is=System.in;
	
		//문자기반으로 업그레이드
		InputStreamReader reader=null;
		reader=new InputStreamReader(is);
		
		//한줄 씩 처리하도록 업그레이드
		BufferedReader buffr=null;
		buffr=new BufferedReader(reader);
		
		//바이트 기반 출력스트림
		OutputStream os=System.out; //현재로선 모니터의 출력을 제어
		
		//문자기반 출력스트림
		OutputStreamWriter writer=null;
		writer=new OutputStreamWriter(os);
		
		//버퍼처리 출력스트림
		BufferedWriter buffw=null;
		buffw=new BufferedWriter(writer);
		
		String data=null;
		try {
			//키보드에서 입력한 문자들을 버퍼에 쌓아가다가
			//사용자가 문자열의 끝임을 알리는 '엔터'를 치면
			//실행중인 프로그램으로 데이터가 들어옴
			data=buffr.readLine();
			System.out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
