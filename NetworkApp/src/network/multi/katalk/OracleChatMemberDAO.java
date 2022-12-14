package network.multi.katalk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import network.domain.ChatMember;
import network.util.DBManager;

//오라클의 chatmember 테이블에 대한 CRUD를 담당하는 객체
public class OracleChatMemberDAO implements ChatMemberDAO{
	
	DBManager dbManager=DBManager.getInstance(); //싱클턴
	
	@Override
	public int insert(ChatMember chatMember) {
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="insert into chatmember(chatmember_idx, id, pass, name)";
		sql+=" values(seq_chatmember.nextval, ?, ?, ?)";
		
		int result=0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, chatMember.getId());
			pstmt.setString(2, chatMember.getPass());
			pstmt.setString(3, chatMember.getName());
			
			result=pstmt.executeUpdate(); //쿼리시행
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int update(ChatMember chatMember) {
		return 0;
	}

	@Override
	public int delete(int chatmember_idx) {
		return 0;
	}

	@Override
	public List selectAll() {
		return null;
	}

	@Override
	public ChatMember select(int chatmember_idx) {
		return null;
	}

}
