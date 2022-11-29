package db.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.StringUtil;

public class GalleryMain extends JFrame implements ActionListener{
	//서쪽영역
	JPanel p_west;
	JTextField t_title;
	JTextField t_writer;
	JTextArea t_content;
	JPanel p_preview; //이미지 미리보기 패널
	JButton bt_open; //첨부 이미지 찾기
	JButton bt_regist;
	
	//센터영역
	JTable table;
	JScrollPane scroll;
	
	//동쪽영역
	JPanel p_east;
	JTextField t_title2;
	JTextField t_writer2;
	JTextArea t_content2;
	JPanel p_preview2; //이미지 미리보기 패널
	JButton bt_edit;
	JButton bt_del;
	
	JFileChooser chooser;
	Image image; //패널이 그릴 수 있도록 멤버변수로 선언함
	File file; //유저가 탐색기를 통해 선택한 바로 그 파일
	String dir="C:/java_workspace2/data/project1129/images";
	String filename; //개발자가 새롭게 정의한 파일명
	
	//DB관련
	Connection con;
	
	public GalleryMain() {
		connect();
		//서쪽영역
		p_west=new JPanel();
		t_title=new JTextField(12);
		t_writer=new JTextField(12);
		t_content=new JTextArea();
		p_preview=new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				g2.setColor(Color.GRAY);
				g2.fillRect(0, 0, 150, 130);
				
				g2.setColor(Color.WHITE);
				g2.drawString("choose your file", 20, 60);
				if(image != null) {
					g2.drawImage(image, 0, 0, 150, 130, GalleryMain.this);					
				}
			}
		};
		bt_open=new JButton("첨부");
		bt_regist=new JButton("등록");
		chooser=new JFileChooser();
		
		p_west.setPreferredSize(new Dimension(150, 500));
		t_content.setPreferredSize(new Dimension(150, 130));
		t_content.setBackground(Color.PINK);
		p_preview.setPreferredSize(new Dimension(150, 130));
		
		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(p_preview);
		p_west.add(bt_open);
		p_west.add(bt_regist);
		
		add(p_west,BorderLayout.WEST);
		
		//센터영역
		table=new JTable();
		scroll=new JScrollPane(table);
		
		add(scroll);
		
		//동쪽영역
		p_east=new JPanel();
		t_title2=new JTextField(12);
		t_writer2=new JTextField(12);
		t_content2=new JTextArea();
		p_preview2=new JPanel();
		bt_edit=new JButton("수정");
		bt_del=new JButton("삭제");
		p_east.setPreferredSize(new Dimension(150, 500));
		t_content2.setPreferredSize(new Dimension(150, 130));
		t_content2.setBackground(Color.PINK);
		p_preview2.setPreferredSize(new Dimension(150, 130));
		p_preview2.setBackground(Color.GRAY);
		
		p_east.add(t_title2);
		p_east.add(t_writer2);
		p_east.add(t_content2);
		p_east.add(p_preview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		
		add(p_east,BorderLayout.EAST);
		
		setSize(900,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼 리스너 연결
		bt_open.addActionListener(this);
		bt_regist.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);
	}

	public void openFile() {
		//파일탐색기를 띄우고, 사용자가 선택한 이미지 파일을 미리보게 하자
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION) {
			//유저가 선택한 파일 반환받기
			file=chooser.getSelectedFile();
			System.out.println("당신이 선택한 파일은 "+file.getName());
			try {
				image=ImageIO.read(file);
				p_preview.repaint();
				
				//이미지명에 사용할 현재시간 (밀리세컨드까지..)
				long time=System.currentTimeMillis();
				System.out.println(time);
				filename=time+"."+StringUtil.getExtend(file.getName());
				System.out.println(filename);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//이미지 복사하기
	public void copy() {
		FileInputStream fis=null; //파일을 대상으로 한 입력스트림
		FileOutputStream fos=null; //파일을 대상으로 한 출력스트림
		
		try {
			fis=new FileInputStream(file);
			//empty상태 빈파일 생성
			fos=new FileOutputStream(dir+"/"+filename);
			
			int data=-1; //읽혀지지 않았다는 초기값
			while(true) {
				data=fis.read();
				if(data==-1)break;
				//break문 아래 쪽은 break문을 만나지 않은 유요한 영역이다
				fos.write(data);
				System.out.println(data);//읽혀진 데이터
			}
			System.out.println("복사완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void regist() {
		copy(); //이미지 복사
		//insert();//오라클 등록
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_open) {
			openFile();
		}else if(obj==bt_regist) {
			regist();
		}else if(obj==bt_edit) {
			
		}else if(obj==bt_del) {
			
		}
	}
	
	public void connect() {
		try {
			//1) 드라이버로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2) 접속
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String user="javase";
			String pass="1234";
			con=DriverManager.getConnection(url, user, pass);
			if(con != null){
				this.setTitle("오라클 접속 성공됨");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void release() {
		
	}
	
	public static void main(String[] args) {
		new GalleryMain();
	}

}