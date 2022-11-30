package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//Dept 테이블에 대한 CRUD 및 JTable에 정보제공
public class DeptModel extends AbstractTableModel{
	String[] column= {"deptno","dname","loc"};
	String[][] data;
	
	EmpMain empMain; //EmpMain에 Connection이 있으므로
	
	public DeptModel(EmpMain empMain) {
		this.empMain=empMain;
		selectAll();
	}
	
	//CRUD 중 select
	public void selectAll() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from dept order by deptno asc";
		
		try {
			pstmt=empMain.con.prepareStatement(sql
					, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			
			rs.last();
			int total=rs.getRow();
			
			data=new String[total][column.length];
			rs.beforeFirst();
			for(int i=0;i<total;i++) {
				rs.next();
				data[i][0]=Integer.toString(rs.getInt("deptno"));
				data[i][1]=rs.getString("dname");
				data[i][2]=rs.getString("loc");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs); //해제
		}
	}
	//1건 등록
	public int insert(String dname, String loc) {
		PreparedStatement pstmt=null;		
		ResultSet rs=null; //시퀀스 담기 위해
		
		String sql="insert into dept(deptno, dname,loc)";
		sql+=" values(seq_dept.nextval, '"+dname+"', '"+loc+"')";
		int deptno=0; //성공여부따지고, 기왕이면 시컨스도 넣기
		try {
			pstmt=empMain.con.prepareStatement(sql);
			int result=pstmt.executeUpdate();
			
			if(result>0) {
				sql="select seq_dept.currval as deptno from dual";
				//PreparedStatement, ResultSet는 쿼리문 마다 1:1 생성하여 사용하면 된다
				pstmt=empMain.con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				rs.next(); //커서 한칸 전진
				deptno=rs.getInt("deptno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			empMain.release(pstmt, rs);
		}
		return deptno;
	}
	
	
	
	
	
	
	@Override
	public int getRowCount() {
		return data.length; //층수
	}

	@Override
	public int getColumnCount() {
		return column.length; //호수
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col]; //들어가는 값
	}
	
	@Override
	public String getColumnName(int col) {
		return column[col];
	}

}
