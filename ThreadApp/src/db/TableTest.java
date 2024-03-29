package db;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import util.ImageManager;

public class TableTest extends JFrame implements ActionListener{
	JPanel p_west; //서쪽 영역에 붙일 패널
	JTextField t_id, t_name;
	JPasswordField t_pass; //비밀번호는 보안상 가려져야 하므로
	JButton bt;
	ImageManager imageManager;
	JTable table;
	JScrollPane scroll;
	int index;
	
	//JTable에 사용할 데이터
	//String[] d=new String[]{"a","d","c"};
	//String[] d1={"scott","1234","스캇"};
	//String[] d2={"somi","1803","소미"};
	//String[] d3={"laststring","1601","늦봄"};
	/*String[][] data= {
			{"scott","1234","스캇"},
			{"somi","1803","소미"},
			{"laststring","1601","늦봄"}
	};*/
	//JTable에 사용할 컬럼
	String[] column= {"EMPNO","ENAME","JOB","MGR","HIREDATE","SAL","COMM","DEPTNO"};
	
	//오라클 접속
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java";
	String pass="1234";
	Connection con; //접속 정보를 가진 객체

	
	public TableTest() {
		//서쪽영역 컴포넌트들
		p_west=new JPanel();
		t_id=new JTextField();
		t_pass=new JPasswordField();
		t_name=new JTextField();
		imageManager=new ImageManager();
		bt=new JButton(imageManager.getIcon("res/app/member.png", 50, 40));
		
		//스타일 설정
		p_west.setPreferredSize(new Dimension(150,500));
		Dimension d=new Dimension(130,30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_name.setPreferredSize(d);
		bt.setPreferredSize(d);
		
		//서쪽조립
		p_west.add(t_id);
		p_west.add(t_pass);
		p_west.add(t_name);
		p_west.add(bt);
		add(p_west,BorderLayout.WEST); //패널 서쪽에 붙이기
		
		
		//table=new JTable(data,column); //10층 5호수
		//scroll=new JScrollPane(table);		
		//add(scroll);
		
		setSize(600,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼과 리스너 연결
		bt.addActionListener(this);
		
		//윈도우와 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release();
				System.exit(0); //프로세스 종료
			}
		});
		
		connect();
		getData();
	}
	
	//오라클 접속
	public void connect() {
		try {
			//1) 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
			
			//2) 접속
			con=DriverManager.getConnection(url, user, pass);
			if(con != null) {
				this.setTitle("오라클에 접속한 상태");
			}else {
				this.setTitle("오라클에 접속 실패");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//System.out.println("오라클에 접속 상태");
		//this.setTitle("오라클에 접속 상태");
	}
	
	//사원테이블 가져오기!(select)
	//오라클의 select문 수행하여 jtable이 사용할 이차원배열을 생성해보자
	public void getData() {
		PreparedStatement pstmt=null; //쿼리 수행 객체
		ResultSet rs=null; //결과 테이블을 담는 객체
		
		String sql="select * from emp order by empno asc";
		try {
			pstmt=con.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery(); //select 문 실행시
			
			int row=0;
			while(rs.next()) {
				row=rs.getRow(); //현재 커서가 레코드 어디에 있는 지 알려줌
			}
			System.out.println("총 레코드 수는"+row);				
			
			String[][] data=new String[row][8];
			
			//rs를 다시 복귀 시키자
			// 코드 에러이유?  ResultSet은 생성시 옵션을 부여하지 않으면
			//전방향 전용 레코드 셋이다
			//즉 forward방향으로만 진행할 수 있다
			rs.beforeFirst();//첫번째 레코드 보다도 이전, 처음 탄생위치
			
			for(int i=0;i<row;i++) {
				rs.next();
				data[i][0]= Integer.toString(rs.getInt("empno")); //EMPNO 컬럼
				data[i][1]= rs.getString("ename"); //ENAME 컬럼
				data[i][2]= rs.getString("job"); //JOB 컬럼
				data[i][3]= Integer.toString(rs.getInt("mgr")); //MGR 컬럼
				data[i][4]= rs.getString("hiredate"); //HIREDATE 컬럼
				data[i][5]= Integer.toString(rs.getInt("sal")); //SAL 컬럼
				data[i][6]= Integer.toString(rs.getInt("comm")); //COMM 컬럼
				data[i][7]= Integer.toString(rs.getInt("deptno")); //DEPTNO 컬럼	
			}
			System.out.println("생성된 이차원 배열의 크기는 "+data.length);
			table=new JTable(data,column);
			scroll=new JScrollPane(table);
			add(scroll);
			
			SwingUtilities.updateComponentTreeUI(this
					);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	}
	
	//오라클 접속 해제
	public void release() {
		System.out.println("DB관련 자원 해제");
		if(con != null){
			try {
				con.close(); //SQL 플러스 창 닫기와 같다
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//데이터 등록하기
	public void regist() {
		System.out.println("등록할께요");
		//table.setValueAt("하이", 2, 1);
		
		//입력한 값 얻기!!
		String id=t_id.getText(); //입력한 아이디
		String pass=new String(t_pass.getPassword()); //char형 배열이기에 명시적 방법 사용, 입력한 비번
		String name=t_name.getText(); //입력한 이름
		
		//하나의 레코드 넣기
		table.setValueAt(id, 	index, 0);
		table.setValueAt(pass, index, 1);
		table.setValueAt(name, index, 2);
		
			index++;
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		regist();
	}

	public static void main(String[] args) {
		new TableTest();
	}

}
