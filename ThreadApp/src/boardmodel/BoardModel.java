package boardmodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//오라클의 Board 테이블에 관한 정보를 가진 모델 객체
public class BoardModel extends AbstractTableModel{
	BoardMain boardMain;
	String[] column= {"board_id","title","writer","content","regdate","hit"};
	String[][] data;
	
	public BoardModel(BoardMain boardMain) {
		this.boardMain=boardMain;
		select(null,null);
	}
	public void select(String category, String keyword) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql=null;
		if(category==null && keyword==null) {
			//검색하지 않을 경우
			sql="select * from board order by board_id desc";
		}else {
			//검색한 경우
			sql="select * from board where "+category+" like '%"+keyword+"%'";
		}
		System.out.println(sql);
		
		try {
			//쿼리객체 생성
			pstmt=boardMain.con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			
			rs.last();
			int total=rs.getRow(); //총 몇건인지 반환
			
			//rs는 곧 죽으므로, rs를 대신하여 데이터를 담게 될 이차원 배열 만들기
			data=new String[total][column.length];
			rs.beforeFirst();
			for(int i=0;i<total;i++) {
				rs.next();
				//String[] column= {"board_id","title","writer","content","regdate","hit"};
				data[i][0]=Integer.toString(rs.getInt("board_id"));
				data[i][1]=rs.getString("title"); //제목
				data[i][2]=rs.getString("writer"); //작성자
				data[i][3]=rs.getString("content"); //내용
				data[i][4]=rs.getString("regdate"); //날짜
				data[i][5]=Integer.toString(rs.getInt("hit")); //조회수				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			boardMain.release(pstmt, rs);
		}
	}
	
	//레코드 1건 넣기!!(DML)
	public int insert(String title, String writer, String content) {
		PreparedStatement pstmt=null;
		int result=0;
		
		String sql="insert into board(board_id, title, writer, content)";
		sql+=" values(seq_board.nextval, '"+title+"', '"+writer+"', '"+content+"')";
		System.out.println(sql);
		try {
			pstmt=boardMain.con.prepareStatement(sql);
			//반환값 int형의 의미 : 이 DML에 의해 영향을 받은 레코드 수를 반환
			//따라서 insert의 경우 성공시 언제나 1, 실패시 0
			result=pstmt.executeUpdate(); //insert, update, delete용
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			boardMain.release(pstmt);
		}
		return result;
	}
	
	//레코드 1건 삭제하기
	//이 메서드를 호출하는 자는  board_id를 넘겨야 한다
	public int  delete(int board_id) {
		PreparedStatement pstmt=null;
		String sql="delete from board where board_id="+board_id;
		//System.out.println(sql);
		int result=0;
		
		try {
			pstmt=boardMain.con.prepareStatement(sql);
			result=pstmt.executeUpdate(); //DML 수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			boardMain.release(pstmt);//자원해제
		}
		return result;
	}
	
	//1건 수정
	public int update(String title, String writer, String content, int board_id) {
		PreparedStatement pstmt=null;
		String sql="update board set title='"+title+"', writer='"+writer+"', content='"+content+"'";
		sql+=" where board_id="+board_id;
		//System.out.println(sql);
		int result=0;
		try {
			pstmt=boardMain.con.prepareStatement(sql);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			boardMain.release(pstmt);
		}
		return result;
	}
	
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	//컬럼제목 재정의
	@Override
	public String getColumnName(int col) {
		return column[col];
	}

}
