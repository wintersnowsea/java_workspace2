package network.multi.katalk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import network.domain.ChatMember;

public class LoginPage extends Page{
	JLabel la_title;
	JTextField t_id;
	JTextField t_pass;
	JButton bt_login;
	JButton bt_join;
	
	//유지보수를 위해 mysql이나 오라클로 정의하지 말고 가장 상의 객체를 사용하자
	ChatMemberDAO chatMemberDAO; //코드가 유연해진다
	//결국 호출되는 메서드는 자식의 메서드가 호출되고, 이러한 객체지향의 개발방법을 다형성이라 한다
	
	public LoginPage(ClientMain clientMain) {
		super(clientMain);
		
		chatMemberDAO=new OracleChatMemberDAO(); //생성할 때 사용할 객체로 정의한다
		
		la_title = new JLabel("KaKaoTalk");
		t_id = new JTextField();
		t_pass = new JTextField();
		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");
		
		la_title.setPreferredSize(new Dimension(350, 200));
		la_title.setFont(new Font("Verdana", Font.BOLD, 45));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);
		
		Dimension d = new Dimension(350, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		
		add(la_title);
		add(t_id);
		add(t_pass);
		
		add(bt_login);
		add(bt_join);

		setBackground(Color.YELLOW);
		
		//로그인 처리
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
			}
		});
		
		
		//회원가입 버튼과 리스너 연결
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientMain.showHide(ClientMain.JOINPAGE);
			}
		});
	}
	
	public void loginCheck() {
		//id, pass를 하나의 DTO에 담아서 전달하기!!
		ChatMember chatMember=new ChatMember(); //empty 상태 DTO
		chatMember.setId(t_id.getText());
		chatMember.setPass(t_pass.getText());
		
		chatMember=chatMemberDAO.select(chatMember);
		
		if(chatMember==null) {
			JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다");
		}else {
			JOptionPane.showMessageDialog(this, "로그인 성공");
			//clientMain에 사용자 정보 보관해두기!
			clientMain.chatMember=chatMember; 
			
			//채팅 창 보여주기
			clientMain.showHide(ClientMain.CHATPAGE);
			ChatPage chatPage=(ChatPage)clientMain.page[ClientMain.CHATPAGE]; //형변환
			chatPage.connect();
		}
		
	}
	
}
