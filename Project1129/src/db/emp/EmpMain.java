package db.emp;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

//부서와 사원을 동시에 등록할 때의 처리방법을 학습한다
//업무를 하다보면 insert와 동시에 primary key 값을 반환받는 빈도수가 높다
//왜? pk는 중복되지 않는 값이기 때문에 활용도가 높다
//ex) 이미지명 pk --> 36.png,
//ex) 자식테이블에 레코드를 넣을 때 자식이 보유할 부모의 pk값
public class EmpMain extends JFrame {
	JPanel p_west;
	JTextField t_dname; //부서명
	JTextField t_loc; //부서위치
	
	//사원정보
	JTextField t_ename; //사원명
	JTextField t_sal; //급여
	JTextField t_job; //업무
	
	JButton bt_regist;
	JTable table;
	JScrollPane scroll;
	
	//db관련
	Connection con;
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="javase";
	String pass="1234";
	DeptModel model;
	EmpModel empModel; //메모리에 올라가야함
	
	
	public EmpMain() {
		connect(); //오라클접속여부
		model=new DeptModel(this); //JTable과 연결안할거임!
		
		p_west=new JPanel();
		t_dname=new JTextField(12);
		t_loc=new JTextField(12);
		
		t_ename=new JTextField(12);
		t_sal=new JTextField(12);
		t_job=new JTextField(12);
		
		bt_regist=new JButton("등록");
		table=new JTable(empModel=new EmpModel(this));
		scroll=new JScrollPane(table);
		
		p_west.add(t_dname);	
		p_west.add(t_loc);	
		p_west.add(t_ename);	
		p_west.add(t_sal);	
		p_west.add(t_job);	
		p_west.add(bt_regist);	
		
		p_west.setPreferredSize(new Dimension(150,500));
		p_west.setBackground(Color.white);
		
		add(p_west,BorderLayout.WEST);
		add(scroll);
		
		setSize(900,500);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});
		
		//등록버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
		
		
	}
	
	//등록하기
	public void regist() {
		int deptno=model.insert(t_dname.getText(), t_loc.getText());
		System.out.println("방금 등록된 부서 번호는 "+deptno);
		if(deptno>0) { //부서가 제대로 등록되었다면
			//사원도 1명 등록!!
			int result=empModel.insert(deptno, t_ename.getText(),
					Integer.parseInt(t_sal.getText()), t_job.getText());
			if(result>0) { //사원마저도 등록 성공!
				empModel.select();
				table.updateUI();
			}
		}
	}
	
	//오라클 접속하기
	public void connect() {
		try {
			//1) 드라이버로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2) 접속
			con=DriverManager.getConnection(url, user, pass);
			if(con != null) {
				setTitle(user+"로 접속 성공");
			}else {
				setTitle("접속 실패");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//윈도우 닫을 때 오라클도 접속정료
	public void release(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//DML에서 사용
	public void release(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//select 사용 후 해제
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
		new EmpMain();
	}
}
