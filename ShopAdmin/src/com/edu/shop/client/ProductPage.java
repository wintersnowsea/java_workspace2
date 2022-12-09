package com.edu.shop.client;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.edu.shop.domain.Product;
import com.edu.shop.model.repository.ProductDAO;

//상품을 출력해주는 페이지
public class ProductPage extends Page{
	
	//스크롤 안에는 하나의 컴포넌트만 올 수 있으므로
	//상품패널들을 품을 수 있는 패널이 필요하며, 바로 이 패녈이 스크롤에 들어갈 것임
	JPanel wrapper; //상품 아이템들을 포함할 패널
	JScrollPane scroll;
	List<ProductItem> itemList;
	
	
	public ProductPage() {
		wrapper=new JPanel();
		wrapper.setPreferredSize(new Dimension(850,1200));
		//wrapper.setLayout(new GridLayout(10, 1));
		itemList=new ArrayList<ProductItem>();
	
		
	
		
		scroll=new JScrollPane(wrapper);
		scroll.setPreferredSize(new Dimension(880,400));
		add(scroll);
	}
	

	
	

}
