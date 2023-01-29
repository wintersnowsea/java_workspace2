package account.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import account.domain.AccountMember;
import util.StringUtil;

public class LoginPage extends Page implements ActionListener{
	
	JPanel p_center,p_south;
	JLabel la_title, la_id, la_pass;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt_join, bt_login;
	
	Color yellowColor=new Color(255,230,153);
	Color grayColor=new Color(89,89,89);
	
	public LoginPage(AccountMain accountMain) {
		super(accountMain);
		
		p_center=new JPanel();
		la_title=new JLabel("SoMi");
		la_id=new JLabel("ID");
		t_id=new JTextField();
		la_pass=new JLabel("PASS");
		t_pass=new JPasswordField() {
			public void setBorder(Border border) { //테두리 없애기
			}
		};
		
		p_south=new JPanel(); //버튼들 부착할 패널
		bt_join=new ButtonStyle("Join",130,55,"VERDANA",25, yellowColor, grayColor);
		bt_login=new ButtonStyle("Login",130,55,"VERDANA",25, yellowColor, grayColor);
		
		
		//style
		Dimension d=new Dimension(140,50); //라벨크기
		Dimension d2=new Dimension(280,50); //textfield 크기
		Font font1=new Font("VERDANA", Font.BOLD, 25);
		Font font2=new Font("VERDANA", 0, 20);
		p_center.setPreferredSize(new Dimension(600,400));
		p_center.setBackground(new Color(169,209,142));
		la_title.setPreferredSize(new Dimension(480,50));
		la_title.setForeground(Color.WHITE);
		la_title.setFont(new Font("VERDANA", Font.BOLD, 55));
		la_title.setHorizontalAlignment(JLabel.CENTER); //라벨 가운데정렬
		la_id.setPreferredSize(d);
		la_id.setForeground(Color.WHITE);
		la_id.setFont(font1);
		t_id.setPreferredSize(d2);
		t_id.setBorder(BorderFactory.createEmptyBorder()); //테두리없애기
		t_id.setFont(font2);
		la_pass.setPreferredSize(d);
		la_pass.setForeground(Color.WHITE);
		la_pass.setFont(font1);
		t_pass.setPreferredSize(d2);
		t_pass.setFont(font2);
		
		setLayout(null);
		p_center.setBounds(450, 250, 600, 400);
		
		//버튼들 부착할 패널 style
		p_south.setPreferredSize(new Dimension(480,80));
		p_south.setBackground(new Color(169,209,142));
		p_south.setLayout(new FlowLayout(FlowLayout.LEFT, 70, 5));
		
		//부착
		p_center.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 40));
		p_center.add(la_title);
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(la_pass);
		p_center.add(t_pass);
		
		p_south.add(bt_join);
		p_south.add(bt_login);
		p_center.add(p_south);
		
		add(p_center);
		
		//버튼들 리스너연결
		bt_join.addActionListener(this);
		bt_login.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_join) {
			//System.out.println("회원가입페이지이동!");
			accountMain.showHide(accountMain.JOINPAGE);
			t_id.setText("");
			t_pass.setText("");
		}else {
			AccountMember accountMember=new AccountMember();
			accountMember.setId(t_id.getText());
			accountMember.setPass(StringUtil.getConvertedPassWord(new String(t_pass.getPassword())));
			
			AccountMember result=accountMain.accountMemberDAO.select(accountMember);
			
			if(result==null) {
				JOptionPane.showMessageDialog(accountMain, "아이디 또는 비밀번호가 일치하지 않습니다");
				return;
			}else {
				JOptionPane.showMessageDialog(accountMain, "로그인 성공!");
				accountMain.header.la_id.setText(t_id.getText());
				accountMain.showHide(accountMain.MENUPAGE);
				t_id.setText("");
				t_pass.setText("");
			}
			
		}
	}

}
