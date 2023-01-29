package account.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MenuLaDetailMenuStyle extends JLabel{
	String text;
	int width;
	int height;
	MenuJPanelStyle menuJPanelStyle;
	int rowIndex;
	int colIndex;
	
	
	public MenuLaDetailMenuStyle(MenuJPanelStyle menuJPanelStyle, String text, int width, int height, int rowIndex, int colIndex) {
		this.menuJPanelStyle=menuJPanelStyle;
		this.text=text;
		this.width=width;
		this.height=height;
		this.rowIndex=rowIndex;
		this.colIndex=colIndex;

		setPreferredSize(new Dimension(width,height));
		setHorizontalAlignment(SwingConstants.LEFT);
		setFont(new Font("¸¼Àº °íµñ",Font.BOLD,10));
		setText(text);
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				menuJPanelStyle.clickEventRow(rowIndex, colIndex);
			}
		});
	}
	
}
