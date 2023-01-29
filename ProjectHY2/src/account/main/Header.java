package account.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Header extends JPanel {
	
	JPanel p_north;

	ButtonStyle bt_type, bt_num, bt_date, bt_type2, bt_year;
	
	JLabel la_mainTitle, la_company, la_id;
	
	Color yellowColor=new Color(255,230,153);
	Color grayColor=new Color(89,89,89);
	Color dipGreen=new Color(56,87,35);
	Color darkGreen=new Color(84,130,53);
	Color lightGreen=new Color(169,209,142);
	Color MGray=new Color(127,127,127);
	Color lightGray=new Color(217,217,217);
	
    AccountMain accountMain;
	
	private static Header instance;
	
	public static Header getInstance() {
		if(instance==null) instance = new Header();
			
		return instance;
	}
	
	public void setMain(AccountMain accountMain) {
		this.accountMain=accountMain;
	}
	
	private Header() {
		createHeader();
	}
	
	public void createHeader() {
		p_north=new JPanel();
		
		//ºÏÂÊ
		//ButtonStyle(String bt_name,int width, int height, String fontName, int font, Color color, Color fontColor)
		la_mainTitle=new JLabel("SoMi");
		la_id=new JLabel(); //·Î±×ÀÎÇÑ id
		la_company=new JLabel("[0001] ²¿¹ÌÀÇ ¼ö³­");
		bt_type=new ButtonStyle("¹ýÀÎ",70,30,"¸¼Àº°íµñ",12,darkGreen,Color.WHITE);
		bt_num=new ButtonStyle("2±â",60,30,"¸¼Àº°íµñ",12,lightGreen,Color.WHITE);
		bt_date=new ButtonStyle("2022.01.01~2022.12.31",180,30,"¸¼Àº°íµñ",12,lightGreen,Color.WHITE);
		bt_type2=new ButtonStyle("¿øÃµ",70,30,"¸¼Àº°íµñ",12,darkGreen,Color.WHITE);
		bt_year=new ButtonStyle("2022",70,30,"¸¼Àº°íµñ",12,lightGreen,Color.WHITE);

		
		//style
		//ºÏÂÊ
		p_north.setPreferredSize(new Dimension(1450,50));
		p_north.setLayout(null);
		la_mainTitle.setBounds(0, -9, 70, 45);
		la_mainTitle.setForeground(dipGreen);
		la_mainTitle.setFont(new Font("VERDANA", Font.BOLD, 25));
		la_id.setBounds(80,-9,100,45);
		la_id.setFont(new Font("VERDANA", Font.BOLD, 12));
		la_company.setBounds(780, -1, 200, 30);
		bt_type.setBounds(950, -1, 70, 30);
		bt_num.setBounds(1030, -1, 60, 30);
		bt_date.setBounds(1100, -1, 180, 30);
		bt_type2.setBounds(1290, -1, 70, 30);
		bt_year.setBounds(1370, -1, 70, 30);
		
		
		//ºÏÂÊºÎÂø
		p_north.add(la_mainTitle);
		p_north.add(la_id);
		p_north.add(la_company);
		p_north.add(bt_type);
		p_north.add(bt_num);
		p_north.add(bt_date);
		p_north.add(bt_type2);
		p_north.add(bt_year);

		add(p_north,BorderLayout.NORTH);
		
		la_id.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result=JOptionPane.showConfirmDialog(accountMain, "·Î±×¾Æ¿ô ÇÏ½Ã°Ú½À´Ï±î?");
				if(result==JOptionPane.OK_OPTION) {
					accountMain.showHide(accountMain.LOGINPAGE);
				}
			}
		});
	}
	
}
