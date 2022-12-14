package network.multi.katalk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	@Override
	public ChatMember select(ChatMember chatMember) { //파라미터가 두개이상이라 DTO로 매개변수 선언
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ChatMember obj=null; //로그인 성공 시 회원정보를 담아 둘 DTO
		
		con=dbManager.getConnection();
		String sql="select * from chatmember where id=? and pass=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, chatMember.getId());
			pstmt.setString(2, chatMember.getPass());
			rs=pstmt.executeQuery();
			
			if(rs.next()) { //레코드가 있다면 조건에 일치하는 회원이 존재한다는 것이고, 회원이 존재한다는 것은 로그인 성공을 뜻함
				//로그인이 성공한다면, 로그인 한 회원의 정보를 담아서 반환해준다
				obj=new ChatMember(); //empty 상태의 DTO 생성
				obj.setChatmember_idx(rs.getInt("chatmember_idx"));
				obj.setId(rs.getString("id"));
				obj.setPass(rs.getString("pass"));
				obj.setName(rs.getString("name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		
		return obj;
	}

}
