package account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import account.domain.AccountMember;
import util.DBManager;

//회원가입 테이블
public class AccountMemberDAO {
	DBManager dbManager=DBManager.getInstance();
	
	//회원가입
	public int insert(AccountMember accountMember) {
		int result=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="insert into accountmember(accountmember_idx,id,pass,email)";
		sql+=" values(seq_accountmember.nextval, ?, ?, ?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, accountMember.getId());
			pstmt.setString(2, accountMember.getPass());
			pstmt.setString(3, accountMember.getEmail());
			
			result=pstmt.executeUpdate(); //쿼리실행
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
	
	//로그인하기
	public AccountMember select(AccountMember accountMember) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		AccountMember actm=null; //로그인 성공 시 회원정보를 담아 둘 DTO
		
		con=dbManager.getConnection();
		String sql="select * from accountmember where id=? and pass=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, accountMember.getId());
			pstmt.setString(2, accountMember.getPass());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				actm=new AccountMember();
				actm.setAccountmember_idx(rs.getInt("accountmember_idx"));
				actm.setId(rs.getString("id"));
				actm.setPass(rs.getString("pass"));
				actm.setEmail(rs.getString("email"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return actm;
	}
	
	//id 중복검사
	public boolean idOverCheck(String id) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		AccountMember actm=null; //로그인 성공 시 회원정보를 담아 둘 DTO
		
		boolean check=false;
		
		con=dbManager.getConnection();
		String sql="select * from accountmember where id=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(!rs.next()) check=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return check;
	}
}
