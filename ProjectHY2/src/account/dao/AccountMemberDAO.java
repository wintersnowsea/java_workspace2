package account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import account.domain.AccountMember;
import util.DBManager;

//ȸ������ ���̺�
public class AccountMemberDAO {
	DBManager dbManager=DBManager.getInstance();
	
	//ȸ������
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
			
			result=pstmt.executeUpdate(); //��������
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
	
	//�α����ϱ�
	public AccountMember select(AccountMember accountMember) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		AccountMember actm=null; //�α��� ���� �� ȸ�������� ��� �� DTO
		
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
	
	//id �ߺ��˻�
	public boolean idOverCheck(String id) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		AccountMember actm=null; //�α��� ���� �� ȸ�������� ��� �� DTO
		
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
