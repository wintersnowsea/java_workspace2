package account.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MenuPage extends Page{
	
	JPanel p_north, p_center;
	JPanel p_topMenu, p_mainMenu;

	
	public static final int TOPWIDTH=100; //topLaTitle
	public static final int TOPHEIGHT=40; //topLaTitle
	
	public static final int TOPWIDTH2=100; //topBTitle
	public static final int TOPFONT=13; //topBTitle
	

	ArrayList<MenuJPanelStyle> mainPTitleList=new ArrayList<MenuJPanelStyle>();
	
	
	String[] topLaTitle= {"�繫ȸ��","�ΰ���ġ��","�ڵ���ǥ","���ʵ�����"};
	String[] topBTitle= {"ȸ��","�λ�޿�","��������","��������","�����븮"};
	
	String[] title= {"��ǥ����","��ΰ���","������","�����ڻ� �� ������","������繫��ǥ","������������"}; //Ÿ��Ʋ
	String[][] mainMenu= {{"�Ϲ���ǥ�Է�","���Ը�����ǥ","���ڼ��ݰ�꼭�߱�","�������������","������¿��������Ϻ�"},
			{"�Ѱ�������","�����ⳳ��","�ϰ�ǥ(����ǥ)","����������","�ŷ�ó����","�Ѱ�����","���Ը�����"},
			{"����ڷ��Է�","�հ��ܾ׽û�ǥ","��������ǥ(�繫����ǥ)","���Ͱ�꼭","������������","�����׿���ó�а�꼭","�����帧ǥ","���μӸ���","�繫��ǥ �ϰ����"},
			{"�����ڻ���","���������󰢺���","�̻󰢺а����󰢺�","�絵�ڻ갨���󰢺�","�����ڻ��������"},
			{"������繫��ǥ","����м��Ͱ�꼭","����п�������","������׿���ó�а�꼭","�ŷ�ó���ʱ��̿�","����������帧ǥ","������ں�����ǥ"},
			{"ȸ����","�ŷ�ó���","���������������","�μ� �� ������","������","ȯ����","��ȯ����","������¿������","���ָ��"}};
	
	
	public MenuPage(AccountMain accountMain) {
		super(accountMain);
		//����
		p_north=new JPanel();
		p_north.setPreferredSize(new Dimension(1450,50));
		p_north.add(accountMain.header);
		add(p_north,BorderLayout.NORTH);
		
		//���Ϳ���
		p_center=new JPanel();
		p_topMenu=new JPanel();
		p_mainMenu=new JPanel();
		
		//style
		//����
		LineBorder lb_MGray=new LineBorder(accountMain.MGray);
		p_center.setPreferredSize(new Dimension(1450,880));
		p_center.setLayout(null);
		
		p_topMenu.setBackground(Color.WHITE);
		p_topMenu.setBounds(50, 0, 1350, 60);
		p_topMenu.setBorder(lb_MGray);
		p_topMenu.setLayout(null);
		
		createtopTitle();
		
		p_mainMenu.setBackground(Color.WHITE);
		p_mainMenu.setBounds(50, 70, 1350, 810);
		p_mainMenu.setBorder(lb_MGray);
		p_mainMenu.setLayout(new FlowLayout(FlowLayout.LEFT,30,22));
		
		createMainTitle();
		
		p_center.add(p_topMenu);
		p_center.add(p_mainMenu);

		add(p_center);
	}
	
	//mainMenu�� ���� �г�
	public void createMainTitle() {
		int rowIndex=0;
		for(int i=0;i<title.length;i++) {
			MenuJPanelStyle menuJPanelStyle=new MenuJPanelStyle(this, title[i], mainMenu[i], rowIndex);
			mainPTitleList.add(menuJPanelStyle);
			p_mainMenu.add(menuJPanelStyle);
			rowIndex++;
		}
	}
	
	//topTitle�� ���� �� & ��ư
	public void createtopTitle() {
		for(int i=0;i<topLaTitle.length;i++) {
			MenuJLabelStyle topLabelStyle;
			int la_x=10*(i+1)+(TOPWIDTH*i);
			if(i==0) {
				topLabelStyle=new MenuJLabelStyle(topLaTitle[i], accountMain.darkGreen, Color.white, TOPFONT, TOPWIDTH, TOPHEIGHT);
			}else {
				topLabelStyle=new MenuJLabelStyle(topLaTitle[i], accountMain.lightGreen, Color.white, TOPFONT, TOPWIDTH, TOPHEIGHT);
			}
			topLabelStyle.setBounds(la_x, 10, TOPWIDTH, TOPHEIGHT);
			p_topMenu.add(topLabelStyle);
		}
		
		for(int a=0;a<topBTitle.length;a++) {
			int bt_x=790+10*(a+1)+(TOPWIDTH2*a);
			ButtonStyle topButtonStyle=new ButtonStyle(topBTitle[a],TOPWIDTH2, TOPHEIGHT, "���� ���", TOPFONT, accountMain.yellowColor, Color.black);
			topButtonStyle.setBounds(bt_x, 10, TOPWIDTH2, TOPHEIGHT);
			p_topMenu.add(topButtonStyle);
		}
	}
}
