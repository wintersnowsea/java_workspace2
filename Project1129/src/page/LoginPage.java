package page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.StringUtil;

//로그인 화면을 보여줄 페이지
public class LoginPage extends JPanel {
	JPanel p_form; //입력양식을 묶을 패널
	JLabel la_id, la_pass;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt_login;
	AppMain appMain;
	
	public LoginPage(AppMain appMain) {
		this.appMain=appMain;
		setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));
	
		p_form=new JPanel();
		la_id=new JLabel("ID");
		la_pass=new JLabel("PW");
		t_id=new JTextField();
		t_pass=new JPasswordField();
		bt_login=new JButton("로그인");
		
		p_form.setPreferredSize(new Dimension(500,120));
		
		Dimension d=new Dimension(230,25);
		la_id.setPreferredSize(d);
		la_pass.setPreferredSize(d);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		
		p_form.add(la_id);
		p_form.add(t_id);
		p_form.add(la_pass);
		p_form.add(t_pass);
		p_form.add(bt_login);
		
		add(p_form);
		
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
			}
		});
	}
	
	//로그인하기
	public void loginCheck() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	
		String sql="select * from member where id=? and pass=?";
		try {
			pstmt=appMain.con.prepareStatement(sql);
			pstmt.setString(1, t_id.getText()); //아이디
			pstmt.setString(2, StringUtil.getConvertedPassWord(new String(t_pass.getPassword()))); //암호화된 비번
			rs=pstmt.executeQuery();
			if(rs.next()) { //레코드가 있다면 회원인증 성공
				JOptionPane.showConfirmDialog(LoginPage.this, "인증 성공");
				t_id.setText("");
				t_pass.setText("");
				appMain.loginFlag=true; //로그인 상태를 true로 전환
			}else {
				JOptionPane.showConfirmDialog(LoginPage.this, "로그인 정보가 올바르지 않습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.release(pstmt, rs);
		}
		
	}
}
