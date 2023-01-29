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
	
	
	String[] topLaTitle= {"재무회계","부가가치세","자동전표","기초데이터"};
	String[] topBTitle= {"회계","인사급여","법인조정","개인조정","세무대리"};
	
	String[] title= {"전표관리","장부관리","결산관리","고정자산 및 감가상각","전기분재무제표","기초정보관리"}; //타이틀
	String[][] mainMenu= {{"일반전표입력","매입매출전표","전자세금계산서발급","영수증수취명세서","업무용승용차운행기록부"},
			{"총계정원장","현금출납장","일계표(월계표)","계정별원장","거래처원장","총괄원장","매입매출장"},
			{"결산자료입력","합계잔액시산표","대차대조표(재무상태표)","손익계산서","제조원가명세서","이익잉여금처분계산서","현금흐름표","결산부속명세서","재무제표 일괄출력"},
			{"고정자산등록","월별감가상각비계상","미상각분감가상각비","양도자산감가상각비","고정자산관리대장"},
			{"전기분재무제표","전기분손익계산서","전기분원가명세서","전기분잉여금처분계산서","거래처별초기이월","전기분현금흐름표","전기분자본변동표"},
			{"회사등록","거래처등록","계정과목및적요등록","부서 ㆍ 사원등록","현장등록","환경등록","권환설정","업무용승용차등록","주주명부"}};
	
	
	public MenuPage(AccountMain accountMain) {
		super(accountMain);
		//북쪽
		p_north=new JPanel();
		p_north.setPreferredSize(new Dimension(1450,50));
		p_north.add(accountMain.header);
		add(p_north,BorderLayout.NORTH);
		
		//센터영역
		p_center=new JPanel();
		p_topMenu=new JPanel();
		p_mainMenu=new JPanel();
		
		//style
		//센터
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
	
	//mainMenu에 붙일 패널
	public void createMainTitle() {
		int rowIndex=0;
		for(int i=0;i<title.length;i++) {
			MenuJPanelStyle menuJPanelStyle=new MenuJPanelStyle(this, title[i], mainMenu[i], rowIndex);
			mainPTitleList.add(menuJPanelStyle);
			p_mainMenu.add(menuJPanelStyle);
			rowIndex++;
		}
	}
	
	//topTitle에 붙일 라벨 & 버튼
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
			ButtonStyle topButtonStyle=new ButtonStyle(topBTitle[a],TOPWIDTH2, TOPHEIGHT, "맑은 고딕", TOPFONT, accountMain.yellowColor, Color.black);
			topButtonStyle.setBounds(bt_x, 10, TOPWIDTH2, TOPHEIGHT);
			p_topMenu.add(topButtonStyle);
		}
	}
}
