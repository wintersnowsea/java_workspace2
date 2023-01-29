package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//오라클과 mysql 둘 다 처리하는 DB 매니저 정의
public class DBManager {
	
	//오라클용
	String oracle_driver = "oracle.jdbc.driver.OracleDriver";
	String oracle_url = "jdbc:oracle:thin:@localhost:1521:XE";
	String oracle_user = "java";
	String oracle_pass = "1234";
	
	//mysql용
	String mysql_driver = "com.mysql.jdbc.Driver";
	String mysql_url = "jdbc:mysql://localhost:3306/javase?characterEncording=utf-8&verifyServerCertificate=false&useSSL=true";
	String mysql_user = "root";
	String mysql_pass = "1234";
	
	//생성자 선언(private -> new 못하고 getter메소드 써야 함)
	private static DBManager instance; //null 싱글톤으로 선언하기
	private Connection connection; 
	
	//생성자 선언(private -> new 못하고 getter메소드 써야 함)
	private DBManager() {
		connectOracle();
	}
	//오라클 접속
	public void connectOracle() {
		try {
			Class.forName(oracle_driver);
			connection = DriverManager.getConnection(oracle_url, oracle_user, oracle_pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//mysql 접속
	public void connectMysql() {
		try {
			Class.forName(mysql_driver);
			connection = DriverManager.getConnection(mysql_url, mysql_user, mysql_driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static DBManager getInstance() {
		if(instance == null) instance = new DBManager();
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	//DB 관련 자원 닫기
	public void release(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
