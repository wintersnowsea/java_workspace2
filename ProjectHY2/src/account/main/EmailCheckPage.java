package account.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//�̸������� Ŭ����
public class EmailCheckPage extends JFrame{
	JPanel p_content;
	JLabel la_title;
	JTextField t_email;
	JButton bt_email;
	String num;
	JoinPage joinPage;
	
	Color yellowColor=new Color(255,230,153);
	Color grayColor=new Color(89,89,89);
	
	public EmailCheckPage(String num, JoinPage joinPage) {
		this.num=num;
		this.joinPage=joinPage;
		
		p_content=new JPanel();
		la_title=new JLabel("�� EMAIL CHECK ��");
		t_email=new JTextField();
		bt_email=new ButtonStyle("Check",130,55,"VERDANA",20, yellowColor, grayColor);
		
		p_content.setPreferredSize(new Dimension(500,300));
		p_content.setBackground(new Color(169,209,142));
		
		la_title.setPreferredSize(new Dimension(380,50));
		la_title.setForeground(Color.WHITE);
		la_title.setFont(new Font("VERDANA", Font.BOLD, 25));
		la_title.setHorizontalAlignment(JLabel.CENTER); //�� �������
		
		t_email.setPreferredSize(new Dimension(200,50));
		t_email.setBorder(BorderFactory.createEmptyBorder()); //�׵θ����ֱ�
		t_email.setFont(new Font("VERDANA", 0, 15));
		
		p_content.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 40));
		
		p_content.add(la_title);
		p_content.add(t_email);
		p_content.add(bt_email);
		
		add(p_content);
		
		setSize(500,300);
		setVisible(true);
		
		bt_email.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check();
			}
		});
	}
	
	public void check() {
		if(t_email.getText().equals(num)) {
			joinPage.emailCheckOk();
			JOptionPane.showMessageDialog(this, "���� ����");
			this.dispose();
		}else {
			JOptionPane.showMessageDialog(this, "������ȣ�� ��ġ���� �ʽ��ϴ�");
		}
	}

}
