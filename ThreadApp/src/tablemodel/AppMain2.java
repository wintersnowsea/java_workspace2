package tablemodel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

//JTable에서 TableModel 사용이 상당히 큰 비중을 차지하는 데,
//왜 사용해야하는지를 이해하기 위한 예제
//사용했을경우와 사용하지 않았을 경우를 비교
/* JDBC 프로그래밍 절차
1) 드라이버 로드
2) 접속
3) 쿼리문
4) 자원해제 
 * */
//JTable에서 TableModel 사용할 때
public class AppMain2 extends JFrame {
	JTable table;
	JScrollPane scroll; //스크롤을 이용해야 컬럼 제목이 노출된다
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java";
	String pass="1234";
	Connection con; //멤버변수로 선언한 이유? 원할 때 접속을 끊기 위해
	TableModel model; //emp테이블에 대한 데이터를 가진 객체
	
	
	public AppMain2() {
		//오라클 연동하기!!
		connect(); //오라클 접속
		
		
		table=new JTable(model=new DeptModel(this));
		scroll=new JScrollPane(table);
		add(scroll);
		
		setSize(600,400);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE); //무조건 프로세스 종료함
		
		//윈도우와 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(con); //오라클 연결해제
				System.exit(0);//프로세스 종료
			}
		});
	}
	
	//오라클 접속하기
	public void connect() {
		try {
			//드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//접속
			con=DriverManager.getConnection(url, user, pass);
			if(con==null) {
				this.setTitle("접속실패");
			}else {
				this.setTitle(user+" 계정으로 접속됨");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//접속을 해제
	public void release(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//DML 호출한 사람만 -> 오버로딩
	public void release(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//오버로딩
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs != null){
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
		new AppMain2();
	}
}
