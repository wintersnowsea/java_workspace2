package account.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;


public class ClientPage extends Page{
	JPanel p_north, p_center;
	ButtonStyle bt_back;
	JPanel p_content;
	JTable table;
	JScrollPane scroll;
	
	public static final int TITLELAWIDTH=80; //titleLaTitle
	public static final int TITLELAHEIGHT=30; //titleLaTitle
	
	String[] title= {"일반거래처", "금융기관", "신용카드"};
	
	public ClientPage(AccountMain accountMain) {
		super(accountMain);
		//북쪽
		p_north=new JPanel();
		p_north.setPreferredSize(new Dimension(1450,50));
		//p_north.setBackground(Color.YELLOW);
		//p_north.add(accountMain.header);
		add(p_north,BorderLayout.NORTH);
		
		//센터
		p_center=new JPanel();
		p_content=new JPanel();
		table=new JTable(3,6);
		scroll=new JScrollPane(table);
		
		scroll.setPreferredSize(new Dimension(680,820));
		p_center.setPreferredSize(new Dimension(1450,870));
		p_center.setLayout(null);
		
		LineBorder lb_lightGray=new LineBorder(accountMain.lightGray);
		p_content.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		p_content.setBorder(lb_lightGray);
		p_content.setBackground(Color.white);
		p_content.setBounds(0, TITLELAHEIGHT, 1450, 870);
		
		
		p_content.add(scroll);
		p_center.add(p_content);
		add(p_center);
		
		createTitleLabel();
		
		bt_back=new ButtonStyle("x", 50, 30, "VERDANA", 13, accountMain.dipGreen, Color.white);
		bt_back.setBounds(1400, 0, 50, 30);
		p_center.add(bt_back);
		
		bt_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountMain.showHide(AccountMain.MENUPAGE);
			}
		});
	}
	
	public void createTitleLabel() {
		MenuJLabelStyle menuJLabelStyle;
		for(int i=0;i<title.length;i++) {
			int la_x=(i+1)+(TITLELAWIDTH*i);
			menuJLabelStyle=new MenuJLabelStyle(title[i], accountMain.darkGreen, Color.white, 13, TITLELAWIDTH, TITLELAHEIGHT);
			menuJLabelStyle.setBounds(la_x, 0, TITLELAWIDTH, TITLELAHEIGHT);
			p_center.add(menuJLabelStyle);
		}
	}
}
