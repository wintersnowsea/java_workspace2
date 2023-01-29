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

//������ �������ִ� ���۸��ϼ����� �̿��� �ڹ� ���Ϲ߼�
public class MailSender {
	//���� �������  Gmail
	String myAccountMail="haeyoung425@gmail.com"; 
	String password="ouqdkopvrvwhrvpe"; //���� ���

	
	
	public void sendMail(String to) {
		
		String subject="�̸���������ȣ"; //���� ����
		String from ="zino1187@naver.com"; //���� ��� �̸���
		String fromName="SoMi"; //���� ���
		//String to="haeyoung425@gamil.com"; //���� ��� �̸���, (�޸��� �ټ��� ���� ����)
		
		StringBuffer content=new StringBuffer();
		content.append("<h1>�ȳ��ϼ���, SoMi�Դϴ�</h1>");
		content.append("<p>������ ������ȣ�� �˾�â�� �Է����ּ���</p>");
		
		//���� ����
		Properties props=new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //���Ϻ��� �� ���� Properties, Gmail ���� ���
		props.put("mail.smtp.port", "587"); //��Ʈ��ȣ ����
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); //�����������, TLS���
		
		//���� ������ ����ϱ� ���� ��������
		//java ���� api���� �����ϴ� Session ��ü ����
		Session mailSession=Session.getInstance(props,new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountMail, password);
				
			}
		}); //�ܺ� api
		
		//������ ���� ����
		MimeMessage message=new MimeMessage(mailSession);
		
		//�����»��, �޴� ��� ���� ����
		try {
			message.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName,"UTF-8","B")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject); //����������
			//HTML�������� ����
			message.setContent(content.toString(),"text/html;charset=UTF-8");
			message.setSentDate(new Date()); //������¥
			
			//�����ϱ�
			Transport trans=mailSession.getTransport("smtp");
			trans.connect(myAccountMail,password);
			trans.send(message,message.getAllRecipients());
			trans.close();
			System.out.println("���Ϲ߼ۼ���");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	//�̸��ϰ� ������ȣ
	public void sendMail(String to, String num) {
		
		String subject="�̸���������ȣ"; //���� ����
		String from ="zino1187@naver.com"; //���� ��� �̸���
		String fromName="SoMi"; //���� ���
		//String to="haeyoung425@gamil.com"; //���� ��� �̸���, (�޸��� �ټ��� ���� ����)
		
		StringBuffer content=new StringBuffer();
		content.append("<h1>�ȳ��ϼ���, SoMi�Դϴ�</h1>");
		content.append("<p>������ ������ȣ�� �˾�â�� �Է����ּ���</p></br>");
		content.append("<h3>"+num+"<h3>");
		
		//���� ����
		Properties props=new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //���Ϻ��� �� ���� Properties, Gmail ���� ���
		props.put("mail.smtp.port", "587"); //��Ʈ��ȣ ����
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); //�����������, TLS���
		
		//���� ������ ����ϱ� ���� ��������
		//java ���� api���� �����ϴ� Session ��ü ����
		Session mailSession=Session.getInstance(props,new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountMail, password);
				
			}
		}); //�ܺ� api
		
		//������ ���� ����
		MimeMessage message=new MimeMessage(mailSession);
		
		//�����»��, �޴� ��� ���� ����
		try {
			message.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName,"UTF-8","B")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject); //����������
			//HTML�������� ����
			message.setContent(content.toString(),"text/html;charset=UTF-8");
			message.setSentDate(new Date()); //������¥
			
			//�����ϱ�
			Transport trans=mailSession.getTransport("smtp");
			trans.connect(myAccountMail,password);
			trans.send(message,message.getAllRecipients());
			trans.close();
			System.out.println("���Ϲ߼ۼ���");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
