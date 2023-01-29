package account.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MenuJLabelStyle extends JLabel{
	Color color;
	Color textColor;
	String text;
	int fontSize;
	int width;
	int height;
	
	
	public MenuJLabelStyle(String text, Color color, Color textColor, int fontSize, int width, int height) {
		this.text=text;
		this.color=color;
		this.textColor=textColor;
		this.fontSize=fontSize;
		this.width=width;
		this.height=height;
		
		//LineBorder lb_lightGray=new LineBorder(lightGray);
		//setBorder(lb_lightGray);
		setPreferredSize(new Dimension(width,height));
		setBackground(color);
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(new Font("¸¼Àº °íµñ",Font.BOLD,fontSize));
		setText(text);
		setForeground(textColor);
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
			}
		});
	}
}
