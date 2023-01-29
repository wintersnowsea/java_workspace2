package account.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


public class GeneralPage extends Page{
	JPanel p_north, p_center;
	ButtonStyle bt_back;
	
	public GeneralPage(AccountMain accountMain) {
		super(accountMain);
		//∫œ¬ 
		p_north=new JPanel();
		p_north.setPreferredSize(new Dimension(1450,50));
		//p_north.setBackground(Color.YELLOW);
		//p_north.add(accountMain.header);
		add(p_north,BorderLayout.NORTH);
		
		//ºæ≈Õ
		p_center=new JPanel();
		p_center.setPreferredSize(new Dimension(1450,880));
		p_center.setLayout(null);
		p_center.setBackground(Color.CYAN);
		add(p_center);
		

		
		bt_back=new ButtonStyle("x", 50, 30, "VERDANA", 13, accountMain.dipGreen, Color.white);
		bt_back.setBounds(1400, 0, 50, 30);
		p_center.add(bt_back);
		
		bt_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountMain.showHide(AccountMain.MENUPAGE);
			}
		});
	}
	
}
