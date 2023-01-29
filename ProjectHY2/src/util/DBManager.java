package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//����Ŭ�� mysql �� �� ó���ϴ� DB �Ŵ��� ����
public class DBManager {
	
	//����Ŭ��
	String oracle_driver = "oracle.jdbc.driver.OracleDriver";
	String oracle_url = "jdbc:oracle:thin:@localhost:1521:XE";
	String oracle_user = "java";
	String oracle_pass = "1234";
	
	//mysql��
	String mysql_driver = "com.mysql.jdbc.Driver";
	String mysql_url = "jdbc:mysql://localhost:3306/javase?characterEncording=utf-8&verifyServerCertificate=false&useSSL=true";
	String mysql_user = "root";
	String mysql_pass = "1234";
	
	//������ ����(private -> new ���ϰ� getter�޼ҵ� ��� ��)
	private static DBManager instance; //null �̱������� �����ϱ�
	private Connection connection; 
	
	//������ ����(private -> new ���ϰ� getter�޼ҵ� ��� ��)
	private DBManager() {
		connectOracle();
	}
	//����Ŭ ����
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
	//mysql ����
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
	
	//DB ���� �ڿ� �ݱ�
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
