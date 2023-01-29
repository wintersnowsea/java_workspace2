package account.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import account.domain.AccountMember;
import util.MailSender;
import util.StringUtil;

public class JoinPage extends Page implements ActionListener{
	
	JPanel p_center, p_south;
	JLabel la_name, la_id, la_pass, la_email, la_pass2;
	JTextField t_id, t_email;
	JPasswordField t_pass, t_pass2;
	JButton bt_id, bt_email, bt_join, bt_login;
	boolean idCheck=false;
	
	EmailCheckPage emailCheckPage;
	MailSender mailSender;
	boolean emailCheck=false;
	Thread mailThread;
	
	Color yellowColor=new Color(255,230,153);
	Color grayColor=new Color(89,89,89);
	
	
	public JoinPage(AccountMain accountMain) {
		super(accountMain);
		
		mailThread=new Thread() {
			public void run() {
				mailSendCheck();
			}
		};
		
		p_center=new JPanel();
		la_name=new JLabel("�� JOIN ��");
		la_id=new JLabel("ID");
		t_id=new JTextField();
		bt_id=new ButtonStyle("Check", 100, 55,"VERDANA",15, yellowColor, grayColor);
		la_pass=new JLabel("PASS");
		t_pass=new JPasswordField() {
			public void setBorder(Border border) { //�׵θ� ���ֱ�
			}
		};
		la_pass2=new JLabel("PASS CHECK");
		t_pass2=new JPasswordField() {
			public void setBorder(Border border) { //�׵θ� ���ֱ�
			}
		};
		la_email=new JLabel("E-MAIL");
		t_email=new JTextField();
		bt_email=new ButtonStyle("Check", 100, 55,"VERDANA", 15, yellowColor, grayColor);
		
		p_south=new JPanel();
		bt_join=new ButtonStyle("Join", 120, 60,"VERDANA", 25, yellowColor, grayColor);
		bt_login=new ButtonStyle("Login", 120, 60,"VERDANA", 25, yellowColor, grayColor);
		
		//style
		Dimension d=new Dimension(100,50);
		Font font1=new Font("VERDANA", Font.BOLD, 20);
		Font font2=new Font("VERDANA", 0, 15);
		//p_center.setPreferredSize(new Dimension(800,530));
		p_center.setBackground(new Color(169,209,142));
		p_south.setBackground(new Color(169,209,142));
		
		la_name.setPreferredSize(new Dimension(480,55));
		la_name.setForeground(Color.WHITE);
		la_name.setFont(new Font("VERDANA", Font.BOLD, 50));
		la_name.setHorizontalAlignment(JLabel.CENTER); //�� �������
		la_id.setPreferredSize(d);
		la_id.setForeground(Color.WHITE);
		la_id.setFont(font1);
		t_id.setPreferredSize(new Dimension(200,50));
		t_id.setBorder(BorderFactory.createEmptyBorder()); //�׵θ����ֱ�
		t_id.setFont(font2);
		la_pass.setPreferredSize(new Dimension(150,50));
		la_pass.setForeground(Color.WHITE);
		la_pass.setFont(font1);
		t_pass.setPreferredSize(new Dimension(250,50));
		t_pass.setFont(font2);
		la_pass2.setPreferredSize(new Dimension(150,50));
		la_pass2.setForeground(Color.WHITE);
		la_pass2.setFont(font1);
		t_pass2.setPreferredSize(new Dimension(250,50));
		t_pass2.setFont(font2);
		la_email.setPreferredSize(d);
		la_email.setForeground(Color.WHITE);
		la_email.setFont(font1);
		t_email.setPreferredSize(new Dimension(200,50));
		t_email.setBorder(BorderFactory.createEmptyBorder()); //�׵θ����ֱ�
		t_email.setFont(font2);
		
		setLayout(null);
		p_center.setBounds(450, 150, 600, 630);
		
		p_center.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 40));
		p_south.setPreferredSize(new Dimension(480,80));
		p_south.setLayout(new FlowLayout(FlowLayout.LEFT, 75, 5));
		
		//����
		p_south.add(bt_join);
		p_south.add(bt_login);
		
		p_center.add(la_name);
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(bt_id);
		p_center.add(la_pass);
		p_center.add(t_pass);
		p_center.add(la_pass2);
		p_center.add(t_pass2);
		p_center.add(la_email);
		p_center.add(t_email);
		p_center.add(bt_email);
		p_center.add(p_south);
		
		add(p_center);
		
		//��ư�� ������ ����
		bt_id.addActionListener(this);
		bt_email.addActionListener(this);
		bt_join.addActionListener(this);
		bt_login.addActionListener(this);
		
		// ���̵� ���� �� �ٽ� �ߺ��˻�����
		t_id.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				bt_id.setEnabled(true); //��ư Ȱ��ȭ
				idCheck=false;
			}
		});
		
		//�̸��� ���� �� �ٽ� �����˻�
		t_email.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				bt_email.setEnabled(true); //��ư Ȱ��ȭ
				emailCheck=false;
			}
		});
		
	}


	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj==bt_id) {
			//id �ߺ��˻�
			if(!accountMain.accountMemberDAO.idOverCheck(t_id.getText())) {
				JOptionPane.showMessageDialog(accountMain, "���̵� �ߺ��Ǿ����ϴ�");
				return;
			}else {
				bt_id.setEnabled(false); //��ư ��Ȱ��ȭ
				idCheck=true;
			}
		}else if(obj==bt_email){
			mailThread.start();
		}else if(obj==bt_join) {
			regist();
		}else if(obj==bt_login) {
			login();
		}
	}
	
	//�̸��� ������ȣ üũ
	public void mailSendCheck() {
		mailSender=new MailSender();
		StringBuffer sb=new StringBuffer();
		//�������� �����ϱ�
		for(int i=0;i<6;i++) {
			int random=(int)(Math.random()*10);
			sb.append(random);
		}
		mailSender.sendMail(t_email.getText(), sb.toString());
		emailCheckPage=new EmailCheckPage(sb.toString(),this);
	}
	
	//�̸���üũ ��ư ��Ȱ��ȭ_������
	public void emailCheckOk() {
		emailCheck=true;
		bt_email.setEnabled(false);
	}
	
	//�����ϱ�
	public void regist() {
		if(!inputCheck()) {
			return;
		}
		AccountMember dto=new AccountMember();
		
		dto.setId(t_id.getText());
		dto.setPass(StringUtil.getConvertedPassWord(new String(t_pass.getPassword())));
		dto.setEmail(t_email.getText());
		
		int result=accountMain.accountMemberDAO.insert(dto);
		if(result>0) {
			//ȸ������ ����
			JOptionPane.showMessageDialog(accountMain, "ȸ������ ����!");
			accountMain.showHide(accountMain.LOGINPAGE);
			t_id.setText("");
			t_pass.setText("");
			t_email.setText("");
		}
	}
	
	//��ȿ���˻�
	public boolean inputCheck() {
		//id ��ĭ�˻�
		if(t_id.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(accountMain, "id�� �Է��ϼ���");
			t_id.requestFocus();
			return false;
		}
		
		//pass ��ĭ�˻�
		String pass=new String(t_pass.getPassword());
		String pass2=new String(t_pass2.getPassword());
		if(pass.trim().equals("")) {
			JOptionPane.showMessageDialog(accountMain, "pass�� �Է��ϼ���");
			t_pass.requestFocus();
			return false;
		}
		if(pass2.trim().equals("")) {
			JOptionPane.showMessageDialog(accountMain, "pass�� �Է��ϼ���");
			t_pass2.requestFocus();
			return false;
		}
		
		//email ��ĭ�˻�
		if(t_email.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(accountMain, "email�� �Է��ϼ���");
			t_email.requestFocus();
			return false;
		}
		
		//pass Ȯ��
		if(!pass.trim().equals(pass2)) {
			JOptionPane.showMessageDialog(accountMain, "��й�ȣ�� Ȯ���ϼ���");
			return false;
		}
		
		//��ȿ���˻� ������ ��
		if(!idCheck) {
			JOptionPane.showMessageDialog(accountMain, "�ߺ�Ȯ�� ���ּ���");
			return false;
		}
		
		//email �����˻�
		if(!emailCheck) {
			JOptionPane.showMessageDialog(accountMain, "email ���� ���ּ���");
			return false;
		}
		
		return true;
	}
	
	public void login() {
		int result=JOptionPane.showConfirmDialog(this, "�α����������� �̵��Ͻðڽ��ϱ�?");
		if(result==JOptionPane.OK_OPTION) {
			accountMain.showHide(accountMain.LOGINPAGE);
			t_id.setText("");
			t_pass.setText("");
			t_email.setText("");
		}
	}

}
