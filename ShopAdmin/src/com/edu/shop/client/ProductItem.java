package com.edu.shop.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

//하나의 상품을 표현할 패널을 정의
public class ProductItem extends JPanel{
	
	public ProductItem() {
		this.setPreferredSize(new Dimension(850,80));
	
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 850, 80);
	}
	
}
