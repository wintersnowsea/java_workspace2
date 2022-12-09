package com.edu.shopclient.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

//쇼핑몰의 메뉴기능을 담당할 버튼을 커스텀하자!
public class MenuButton extends JButton implements ActionListener{
	CllentMain cllentMain;
	int pageNum;
	String title;
	
	public MenuButton(CllentMain cllentMain, int pageNum, String title) {
		super(title); //버튼의 제목 설정
		this.cllentMain=cllentMain;
		this.pageNum=pageNum;
		this.title=title;
		
		this.addActionListener(this);//버튼과 리스너 연결
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("나 눌렀어?");
		cllentMain.showHide(pageNum);
		
		//해당 상품에 알맞는 쿼리 요청
		//단, 상품관련 버튼메뉴에만 적용
		if(pageNum == cllentMain.PRODUCTPAGE) {
			//ProductPage의 selectAll()을 호출하되
			//title을 넘겨야 '상의', '하의' 텍스트가 전달된다
			//ProductPage는 현재 나한테 없다!
			//누구에게 있는가? ClientMain에 있다
			//ClientMain을 통해 접근하자
			ProductPage productPage=(ProductPage)cllentMain.pageList[0];
			productPage.getProductList(title);
			//패널 새로고침
			cllentMain.container.updateUI();
			
		}
	}
}
