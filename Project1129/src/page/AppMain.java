package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.ImageManager;

public class AppMain extends JFrame{
	
	JPanel p_north; //네비게이션 버튼을 올려놓을 북쪽 패널
	//JButton[] menu=new JButton[4];
	Menu[] menu=new Menu[4];
	ImageManager imageManager;
	String[] path= {
			"res/app/company.png",
			"res/app/member.png",
			"res/app/login.png",
			"res/app/notice.png",
	}; //이미지아이콘을 담아 놓을 배열
	//우리가 사용할 페이지들이 공존할 수 있도록
	//FlowLayout을 적용할 영역때문에 프레임의 센터에 부착될 것임(대왕만하게)
	JPanel p_center;
	
	//우리가 사용할 페이지들, 
	//규칙을 만들면 활용도가 높으므로
	//모든 페이지들을 상위 자료형인 JPanel로 묶자
	JPanel[] pageList=new JPanel[4];
	
	
	
	
	
	
	
	
	//모든 페이지의 너비와 높이의 기준을 정한 상수정의
	public static final int PAGE_WIDTH=800;
	public static final int PAGE_HEIGHT=500;
	
	//db관련정보
	FileInputStream fis; //파일을 대상으로 한 입력스트림
	Properties props; //Map의 손자다!! key-value
	String driver;
	String url2;
	String user;
	String pass;
	Connection con;
	
	boolean loginFlag; //false 로그인여부 확인할 논리값
	
	public AppMain() {
		loadInfo();
		connect();
		
		p_north=new JPanel();
		p_north.setBackground(Color.YELLOW);
		imageManager=new ImageManager();
		
		//아이콘이 있는 버튼들을 생성한다!!
		for(int i=0;i<menu.length;i++) {
			menu[i]=new Menu(this,imageManager.getIcon(path[i],60,60),60,60,i);
			p_north.add(menu[i]);
			
			//내부익명클래스는 외부클래스의 멤버변수는 맘대로 접근이 가능하지만
			//메서드의 지역변수는 final로 선언된 변수를 읽기만 가능
			/*
			 * menu[i].addMouseListener(new MouseAdapter() { public void
			 * mouseClicked(MouseEvent e) { System.out.println("눌렀어?"); } });
			 */
		}
		
		//센터에 붙을 패널 생성
		p_center=new JPanel();
		p_center.setBackground(Color.GRAY );
		
		//4개의 페이지를 미리 화면에 생성하여 붙이기
		pageList[0]=new CompanyPage();
		pageList[1]=new MemberPage(this);
		pageList[2]=new LoginPage(this);
		pageList[3]=new NoticePage();
		
		//생성된 페이지들을 센터 p_center 패널에 붙이자
		for(int i=0;i<pageList.length;i++) {
			p_center.add(pageList[i]);
		}
		
		add(p_north, BorderLayout.NORTH); //북쪽에 패널 붙이기
		add(p_center); //센터에 붙이기
		
		
		//화면에 띄우기
		setSize(800,500);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		showHide(2); //시작페이지 고정
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});
	}
	
	//페이지 보여주기 전에 로그인 완료여부를 확인하기
	public void checkLogin(int currentPage) {
		
		//회원가입과 로그인의 경우 검사하지 않는다!
		if(currentPage==1 || currentPage==2) {
			showHide(currentPage);
		}else {
			//검증이 필요한 나머지 것들!
			if(loginFlag==false) {
				JOptionPane.showMessageDialog(this, "로그인이 필요한 서비스입니다");
			}else {
				showHide(currentPage);				
			}
			
			
		}
	}
	                    
	//메뉴를 눌렀을 때 해당 페이지 보여주기
	public void showHide(int currentPage) {
		for(int i=0;i<pageList.length;i++) {
			if(currentPage==i) {
				pageList[i].setVisible(true);					
			}else {
				pageList[i].setVisible(false);
			}
		}		
	}
	
	//스트림 생성
	public void loadInfo() {
		//우리가 스트림을 생성할 자원이 package경로에 있으므로
		//URL : 자원의 위치를 알고있는 객체
		URL url=this.getClass().getClassLoader().getResource("res/db/db.properties");
		try {
			URI uri=url.toURI();
			File file=new File(uri);
			fis=new FileInputStream(file);
			props=new Properties(); //맵의 손자 생성
			props.load(fis); //props와 fis가 연결됨, 파일을 인식한 맵이 되는 순간!
			System.out.println(props);
			driver=props.getProperty("driver");
			url2=props.getProperty("url");
			user=props.getProperty("user");
			pass=props.getProperty("pass");
			
//			int data=-1;
//			while(true) {
//				data=fis.read();
//				if(data==-1) break;
//				System.out.println((char)data);
//			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void connect() {
		try {
			//드라이버로드 & 접속
			Class.forName(driver);
			con=DriverManager.getConnection(url2, user, pass);
			if(con!=null) {
				setTitle("접속 성공");
			}else {
				setTitle("접속 실패");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//윈도우와 리스너 연결시 사용
	public void release(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//DML인 경우 사용
	public void release(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//select인 경우 사용
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		new AppMain();
	}
}
