package account.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MenuJPanelStyle extends JPanel{
	Color lightGray=new Color(217,217,217);
	
	MenuJLabelStyle menuJLabelStyle;
	MenuLaDetailMenuStyle detailMenuStyle;
	MenuPage menuPage;
	
	public static final int MAINTITLEWIEDTH=410;
	public static final int MAINTITLEHEIGHT=30;
	public static final int MAINTITLEFONT=13;
	
	int rowIndex;
	int colIndex;
	
	public MenuJPanelStyle(MenuPage menuPage, String title, String[] mainMenu, int rowIndex) {
		this.menuPage=menuPage;
		this.rowIndex=rowIndex;
		menuJLabelStyle=new MenuJLabelStyle(title, lightGray, Color.black, MAINTITLEFONT, MAINTITLEWIEDTH, MAINTITLEHEIGHT);
		
		add(menuJLabelStyle);
		for(int i=0;i<mainMenu.length;i++) {
			colIndex=i;
			detailMenuStyle=new MenuLaDetailMenuStyle(this, mainMenu[i], MAINTITLEWIEDTH-10, 20, rowIndex, colIndex);
			add(detailMenuStyle);
			colIndex++;
		}
		
		LineBorder lb_lightGray=new LineBorder(lightGray);
		setBorder(lb_lightGray);
		setPreferredSize(new Dimension(410,370));
		setBackground(Color.WHITE);
	}
	
	public void clickEventRow(int rowIndex, int colIndex) {
		switch (rowIndex) {
			case 0: clickRow0(colIndex);break;
			case 1: ;break;
			case 2: ;break;
			case 3: ;break;
			case 4: ;break;
			case 5: clickRow5(colIndex);break;
		}
	}
	
	public void clickRow0(int colIndex) {
		switch(colIndex) {
		case 0: menuPage.accountMain.showHide(AccountMain.GENERALPAGE);break;
		}
	}
	
	public void clickRow5(int colIndex) {
		switch(colIndex) {
		case 0: ;break;
		case 1: menuPage.accountMain.showHide(AccountMain.CLIENTPAGE);break;
		case 2: menuPage.accountMain.showHide(AccountMain.ACCOUNTTITLEPAGE);break;
		}
	}
}
