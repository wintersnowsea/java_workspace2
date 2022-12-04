package db.diary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*이 객체에는 db관련 CRUD이외의 코드는 두지 않는다
 * 즉 insert, update, delete, select 실행에 대한
 * 코드만 두어야 한다.
 * 즉 쿼리 전담 객체이다
 * 이런한 목적의 클래스를 가리켜 DAO(Date Access Object)라 한다
 * ex) 대형어플리케이션 분야에서는 테이블이 25개라면 DAO도 25개 존재해야 한다*/
public class DiaryDao {
	DBManager dbManager=DBManager.getInstance();
	//CRUD 중 insert를 정의한다
	public int insert(Diary diary) {
		PreparedStatement pstmt;
		
		String sql="insert into diary(diary_idx,yy,mm,dd,content,icon)";
		sql+=" values(seq_diary.nextval, ?,?,?,?,?)";

		Connection con=dbManager.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, 0);
			pstmt.setInt(2, 0);
			pstmt.setInt(3, 0);
			pstmt.setInt(4, 0);
			pstmt.setInt(5, 0);
			
		} catch (SQLException e) {
 			e.printStackTrace();
		}
		
		return 0;
	}
}
