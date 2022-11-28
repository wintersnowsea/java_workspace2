package boardmodel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

//응용프로그램개발시 디자인 영역과 데이터 및 그 처리영역(업무로직)은
//서로 분리시켜 개발해야 좋다(유지보수성==시간==돈)
public class BoardMain extends JFrame implements ActionListener {
	JPanel p_north;
	JTextField t_keyword; //검색어 입력
	JButton bt_search; //검색버튼
	
	JPanel p_west;
	JTextField t_title; //제목
	JTextField t_writer; //작성자
	JTextArea t_content; //내용
	JButton bt_regist; //등록버튼
	
	JPanel p_east;
	JTextField t_title2; //제목
	JTextField t_writer2; //작성자
	JTextArea t_content2; //내용
	JButton bt_edit, bt_del; //수정,삭제버튼
	
	JTable table;
	JScrollPane scroll;
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="javase";
	String pass="1234";
	Connection con; //원할때 닫기위하여
	TableModel model; //상위자료형으로 선언
	int selected_id; //유저가 선택한 바로 그 게시물의 board_id
	
	public BoardMain() {
		connect();
		
		//북쪽영역
		p_north=new JPanel();
		t_keyword=new JTextField(25);
		bt_search=new JButton("검색");
		p_north.add(t_keyword);
		p_north.add(bt_search);
		add(p_north, BorderLayout.NORTH);
		
		//서쪽영역
		p_west=new JPanel();
		t_title=new JTextField(12);
		t_writer=new JTextField(12);
		t_content=new JTextArea();
		bt_regist=new JButton("등록");
		p_west.setPreferredSize(new Dimension(150, 500));
		t_content.setPreferredSize(new Dimension(140, 150));
		
		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(bt_regist);
		add(p_west, BorderLayout.WEST);
		
		//센터영역
		table=new JTable(model=new BoardModel(this));
		scroll=new JScrollPane(table);
		add(scroll);
		
		//동쪽영역
		p_east=new JPanel();
		t_title2=new JTextField(12);
		t_writer2=new JTextField(12);
		t_content2=new JTextArea();
		bt_edit=new JButton("수정");
		bt_del=new JButton("삭제");
		p_east.setPreferredSize(new Dimension(150,500));
		t_content2.setPreferredSize(new Dimension(140,150));
		
		p_east.add(t_title2);
		p_east.add(t_writer2);
		p_east.add(t_content2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		add(p_east, BorderLayout.EAST);
		
		setSize(900,500);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0); //프로세스 종료
			}
		});
		
		//버튼들과 리스너 연결
		bt_regist.addActionListener(this);
		bt_search.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);
		
		//테이블에 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("클릭");
				int row=table.getSelectedRow();
				//System.out.println("선택된 층수는 "+row);
				BoardModel boardModel=(BoardModel)model;
				String[] record=boardModel.data[row]; //게시물 한 건, 즉 일차원 배열 반환
				//System.out.println(record[1]);
				getDetail(record); //레코드 한 건 전달
			}
		});
	}
	
	//접속
	public void connect() {
		try {
			//드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//접속
			con=DriverManager.getConnection(url, user, pass);
			if(con != null) {
				this.setTitle(user+"로 접속됨");
			}else {
				this.setTitle("접속실패");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//접속끊기
	public void release(Connection con) {
		if(con !=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//DML 사용 후 해제
	public void release(PreparedStatement pstmt) {
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Select 사용 후 해제
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs !=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void regist() {
		//모델이 보유한 insert 메서드 호출하여 등록처리하자
		BoardModel boardModel=(BoardModel)model;
		int result=boardModel.insert(t_title.getText(),t_writer.getText(),t_content.getText());
		if(result >0) {
			boardModel.select(); //이차원 갱신때문에
			table.updateUI(); //테이블 갱신			
		}
	}
	
	//상세보기
	public void getDetail(String[] record) {
		//선택된 row에 해당하는 배열을 우측영역에 출력하자
		//board_id는 출력대상이 아니므로 어딘가에 보관해놓자
		selected_id=Integer.parseInt(record[0]);
		t_title2.setText(record[1]);
		t_writer2.setText(record[2]);
		t_content2.setText(record[3]);
	}
	
	//한건 삭제
	public void del() {
		if(JOptionPane.showConfirmDialog(this, "삭제하시겠어요?")==JOptionPane.OK_OPTION) {
			BoardModel boardModel=(BoardModel)model;
			int result=boardModel.delete(selected_id);
			if(result !=0) {
				boardModel.select(); //select 갱신
				table.updateUI();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource(); //이벤트 일으킨 컴포넌트
		if(obj==bt_regist) {
			regist();
		}else if(obj==bt_search) {
			
		}else if(obj==bt_edit) {
			
		}else if(obj==bt_del) {
			del();
		}
	}
	
	public static void main(String[] args) {
		new BoardMain();
	}

}
