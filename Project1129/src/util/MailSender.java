package util;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

//구글이 제공해주는 구글메일서버를 이용한 자바 메일발송
public class MailSender {
	//내가 사용중인  Gmail
	String myAccountMail="haeyoung425@gmail.com"; 
	String password="ouqdkopvrvwhrvpe"; //나의 비번
	
	
	public void sendMail(String to) {
		String subject="가입해주셔서 감사합니다"; //메일 제목
		String from ="zino1187@naver.com"; //보낸 사람 이메일
		String fromName="young"; //보낸 사람
		//String to="haeyoung425@gamil.com"; //받을 사람 이메일, (콤마로 다수의 메일 가능)
		
		StringBuffer content=new StringBuffer();
		content.append("<h1>고객님 안녕하세요, 쇼핑 닷컴입니다</h1>");
		content.append("<p>입금부탁드립니다</p>");
		
		//메일 설정
		Properties props=new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //메일보낼 때 쓰는 Properties, Gmail 서버 사용
		props.put("mail.smtp.port", "587"); //포트번호 설정
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); //보안인증방법, TLS사용
		
		//구글 메일을 사용하기 위한 인증절차
		//java 메일 api에서 지원하는 Session 객체 생성
		Session mailSession=Session.getInstance(props,new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountMail, password);
				
			}
		}); //외부 api
		
		//보내기 위한 세팅
		MimeMessage message=new MimeMessage(mailSession);
		
		//보내는사람, 받는 사람 정보 세팅
		try {
			message.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName,"UTF-8","B")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject); //메일제목설정
			//HTML형식으로 보냄
			message.setContent(content.toString(),"text/html;charset=UTF-8");
			message.setSentDate(new Date()); //보낸날짜
			
			//전송하기
			Transport trans=mailSession.getTransport("smtp");
			trans.connect(myAccountMail,password);
			trans.send(message,message.getAllRecipients());
			trans.close();
			System.out.println("메일발송성공");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		MailSender sender=new MailSender();
//		sender.sendMail();
//	}
}
