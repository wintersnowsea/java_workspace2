package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

//로그인 화면을 보여줄 페이지
public class LoginPage extends JPanel {
	public LoginPage() {
		setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));
	}
}
