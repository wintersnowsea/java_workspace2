package com.edu.shopclient.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.edu.shopclient.domain.TopCategory;
import com.edu.shopclient.model.repository.TopCategoryDAO;
import com.edu.shopclient.util.DBManager;

public class CllentMain extends JFrame{
	DBManager dbManager=DBManager.getInstance();
	TopCategoryDAO topCategoryDAO=new TopCategoryDAO();
	
	JPanel p_north; //메뉴버튼들이 올 패널
	List<MenuButton> menuList;
	MenuButton m_login;
	MenuButton m_join;
	MenuButton m_cart;
	JPanel container; //화면 전환에 사용될 루트 컨테이너
							//여기에 화면들 여러개를 부착할 예정
	Page[] pageList=new Page[4];
	
	public static final int PRODUCTPAGE=0; //상수
	public static final int LOGINPAGE=1; //상수
	public static final int JOINPAGE=2; //상수
	public static final int CARTPAGE=3; //상수
	
	public CllentMain() {
		p_north=new JPanel();
		menuList=new ArrayList<MenuButton>();
		
		createMenu(); //상품 카테고리 수 만큼의 버튼 생성
		//로그인, 회원가입, 장바구니
		m_login=new MenuButton(this,LOGINPAGE,"로그인");
		m_join=new MenuButton(this,JOINPAGE,"회원가입");
		m_cart=new MenuButton(this,CARTPAGE,"장바구니");
		p_north.add(m_login);
		p_north.add(m_join);
		p_north.add(m_cart);
		
		
		//모든 page들을 품고 있을 녀석
		container=new JPanel();
		container.setBackground(Color.green);
		
		//페이지 생성하여 붙여넣기
		pageList[0]=new ProductPage();
		pageList[1]=new LoginPage();
		pageList[2]=new JoinPage();
		pageList[3]=new CartPage();
		for(int i=0;i<pageList.length;i++) {
			container.add(pageList[i]);
		}
		
		add(p_north,BorderLayout.NORTH);
		add(container);
		
		setSize(900,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);//임시적으로
		
		//디폴트로 보여주고 싶은 페이지 지정
		showHide(0); //상품페이지
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
			
		
	}
	
	public void createMenu() {
		//추후 db연동하여 가져올 예정
		List<TopCategory> topList=topCategoryDAO.selectAll();
		
		for(TopCategory t : topList) {
			MenuButton menu=new MenuButton(this, PRODUCTPAGE, t.getTopcategory_name());
			p_north.add(menu); //북쪽패널의 부착
		}
	}
	
	//보여줄 페이지와 가려질 페이지를 처리하여 화면전환효과를 냄
	//n은 보여질 index를 결정
	//예) 2를 넘기면 3번째 페이지가 보여짐
	public void showHide(int n) {
		for(int i=0;i<pageList.length;i++) {
			if(i==n) { //n과 i가 같을 때 보여짐
				pageList[i].setVisible(true);
			}else {
				pageList[i].setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new CllentMain();
	}
}
