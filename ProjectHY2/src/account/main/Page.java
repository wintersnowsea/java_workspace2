package account.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

//모든 페이지들의 최상위 클래스
public class Page extends JPanel{
	AccountMain accountMain;
	
	public Page(AccountMain accountMain) {
		this.accountMain=accountMain;
		
		this.setPreferredSize(new Dimension(1500,1000));
	}
}
