package io.basic;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MemoApp extends JFrame{
	JButton bt;
	JButton bt_open; //파일 탐색기 띄우는 버튼
	JButton bt_save; //area의 내용을 파일에 출력하기 위한 버튼
	JFileChooser chooser;
	JTextArea area;
	JScrollPane scroll;
	String path="C:/java_workspace2/data/NetworkApp/res/memo.txt";
	File file; //현재 열어놓았던 파일
	
	public MemoApp() {
		bt=new JButton("Load");
		bt_open=new JButton("파일열기");
		bt_save=new JButton("저장");
		chooser=new JFileChooser();
		area=new JTextArea();
		scroll=new JScrollPane(area);
		setLayout(new FlowLayout());
		scroll.setPreferredSize(new Dimension(570, 320));
		add(bt); //프레임에 부착
		add(bt_open); //프레임에 부착
		add(bt_save); //프레임에 부착
		add(scroll); //프레임에 부착
		
		setVisible(true);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData2();
			}
		});
		
		bt_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});

		bt_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
	}
	
	public void saveFile() {
		FileWriter writer=null; //파일을 대상으로 한 문자기반 출력스트림
		BufferedWriter buffw=null; //버퍼처리된 문자기반 스트림
		
		try {
			writer=new FileWriter(file);
			buffw=new BufferedWriter(writer); //빨대가 덧씌워지는 순간!
			buffw.write(area.getText());
			JOptionPane.showMessageDialog(this, "저장완료");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(buffw!=null) {
				try {
					buffw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void openFile() {
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION) {
			file=chooser.getSelectedFile();
			
			//아래 객체의 정체는 ? InputStreamReader 자식!!
			FileReader reader=null; //문자기반_읽는 클래스
			BufferedReader buffr=null; //숟가락을 쥐어주자 -> 한줄씩 모아서 처리함
			
			try {
				reader=new FileReader(file);
				buffr=new BufferedReader(reader);
				//int data=-1;
				String data=null;
				int count=0;
				
				while(true) {
//					data=reader.read();
//					if(data==-1)break;
//					count++;
//					area.append(Character.toString((char)data));
					data=buffr.readLine();
					if(data==null)break;
					//data는 한줄의 스트링이 들어있으며 한줄을 area에 출력시마다 area의 개행을 처리하자
					area.append(data+"\n");
					count++;
				}
				System.out.println("읽어드린 횟수는 "+count);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(buffr != null) {
					try {
						buffr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//개발자가 문자기반 스트림의 존재를 모를경우...
	public void loadData() {
		//바이트 기반(문자이해못함)
		FileInputStream fis=null;
		try {
			fis=new FileInputStream(path);
			int data=-1;
			
			data=fis.read(); //j
			System.out.println((char)data);
			data=fis.read(); //a
			System.out.println((char)data);
			data=fis.read(); //v
			System.out.println((char)data);
			data=fis.read(); //a
			System.out.println((char)data);
			data=fis.read(); //' '
			System.out.println((char)data);
			data=fis.read(); //프
			System.out.println((char)data);
			
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
		}
	}
	
	//문자기반 스트림 이용할 경우
	//문자 기반 스트림을 사용하는 경우 :  기존 바이트 기반 스트림  + 바이너리 스트림 
    //바이트 기반 스트림을 보완하여 사용하는 개념이라 문자를 이해한다. 2바이트씩 해석이 가능한 것.
	public void loadData2() {
		InputStreamReader reader=null;
		FileInputStream fis=null;
		
		try {
			//바이트 기반 스트림 생성
			fis=new FileInputStream(path);
			
			//바이트 기반 스트림을 문자기반 스트림으로 업그레이드
			reader=new InputStreamReader(fis); 
			int data=-1;
			
			while(true) {
				data=reader.read();
				if(data==-1)break;
				area.append(Character.toString(data)); //기본자료형을 객체자료형으로 변환
			}
//			data=reader.read(); //j
//			System.out.println((char)data);
//			data=reader.read(); //a
//			System.out.println((char)data);
//			data=reader.read(); //v
//			System.out.println((char)data);
//			data=reader.read(); //a
//			System.out.println((char)data);
//			data=reader.read(); //' '
//			System.out.println((char)data);
//			data=reader.read(); //프
//			System.out.println((char)data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new MemoApp();
	}
}
