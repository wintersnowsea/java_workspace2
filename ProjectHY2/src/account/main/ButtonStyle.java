package account.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JButton;

//버튼 디자인
public class ButtonStyle extends JButton{
	Color color;
	Color fontColor;
	int width;
	int height;
	int fontSize;
	
	public ButtonStyle(String bt_name,int width, int height, String fontName, int fontSize, Color color, Color fontColor) {
		this.width=width;
		this.height=height;
		this.fontSize=fontSize;
		this.color=color;
		this.fontColor=fontColor;
		
		setText(bt_name);
		setFont(new Font(fontName, Font.BOLD, fontSize));
		setPreferredSize(new Dimension(width,height));
		setBackground(color); //배경색
		setForeground(fontColor); //글자색
		
		
		setBorderPainted(false); //테두리설정
		setOpaque(false);
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		
		g2.setColor(color);
		g2.fillRoundRect(0, 0, width, height, 10, 10);
		

		super.paintComponent(g2);
	}
	
}
