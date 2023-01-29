package account.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import account.dao.AccountMemberDAO;
import util.DBManager;

public class AccountMain extends JFrame{
	DBManager dbManager=DBManager.getInstance();
	Header header=Header.getInstance();
	
	JPanel p_main;
	
	Page[] pages=new Page[6];
	
	public static final int LOGINPAGE=0;
	public static final int JOINPAGE=1;
	public static final int MENUPAGE=2;
	public static final int GENERALPAGE=3;
	public static final int CLIENTPAGE=4;
	public static final int ACCOUNTTITLEPAGE=5;

	AccountMemberDAO accountMemberDAO=new AccountMemberDAO();
	
	Color yellowColor=new Color(255,230,153);
	Color grayColor=new Color(89,89,89);
	Color dipGreen=new Color(56,87,35);
	Color darkGreen=new Color(84,130,53);
	Color lightGreen=new Color(169,209,142);
	Color MGray=new Color(127,127,127);
	Color lightGray=new Color(217,217,217);
	
	public AccountMain() {
		header.setMain(this);
		
		p_main=new JPanel();
		
		pages[0]=new LoginPage(this);
		pages[1]=new JoinPage(this);
		pages[2]=new MenuPage(this);
		pages[3]=new GeneralPage(this);
		pages[4]=new ClientPage(this);
		pages[5]=new AccountTitlePage(this);

		
		//∆‰¿Ã¡ˆ∫Œ¬¯
		for(int i=0;i<pages.length;i++) {
			p_main.add(pages[i]);
		}
		add(p_main);
		
		showHide(LOGINPAGE);
		
		setSize(1500,1000);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
	}
	
	public void showHide(int n) {
		for(int i=0;i<pages.length;i++) {
			if(n==i) {
				pages[i].setVisible(true);
			}else {
				pages[i].setVisible(false);
			}
		}
	}
	
	
	public static void main(String[] args) {
		new AccountMain();
	}
}
