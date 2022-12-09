package com.edu.shop.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.edu.shop.domain.Product;
import com.edu.shop.domain.SubCategory;
import com.edu.shop.domain.TopCategory;
import com.edu.shop.model.repository.ProductDAO;
import com.edu.shop.model.repository.SubCategoryDAO;
import com.edu.shop.model.repository.TopCategoryDAO;
import com.edu.shop.model.table.ProductModel;
import com.edu.shop.util.DBManager;
import com.edu.shop.util.ImageManager;
import com.edu.shop.util.StringUtil;

public class AdminMain extends JFrame implements ActionListener{
	TopCategoryDAO topCategoryDAO;
	SubCategoryDAO subCategoryDAO;
	public ProductDAO productDAO;
	
	DBManager dbManager=DBManager.getInstance();
	
	//서쪽 영역 
	JPanel p_west;
	JComboBox<String> box_top; //상위 카테고리 
 	JComboBox<String> box_sub; //하위 카테고리 
	JTextField t_name; //상품명
	JTextField t_brand; //브랜드명 
	JTextField t_price; //가격 
	JPanel preview; //이미지 미리보기 영역 145 x 145
	JTextField t_url;//이미지의 웹 주소
	JButton bt_preview; //이미지 미리보기 버튼
	JButton bt_regist; // 등록버튼
	JButton bt_list; // 
	
	//센터영역 
	JPanel p_center;//북쪽과 센터로 구분되어질 패널
	JPanel p_search;//검색기능이 올려질 패널 
	JComboBox<String> box_category; // 검색 구분
	JTextField t_keyword;//검색어
	JButton bt_search; //검색 버튼
	ProductModel model; //jtable이 표 구성에 참조할 객체
	JTable table;
	JScrollPane scroll;
	
	//동쪽 영역 
	JPanel p_east;
	JComboBox<String> box_top2; //상위 카테고리 
	JComboBox<String> box_sub2; //하위 카테고리 
	JTextField t_name2; //상품명
	JTextField t_brand2; //브랜드명 
	JTextField t_price2; //가격 
	JPanel preview2; //이미지 미리보기 영역
	JTextField t_url2;//이미지의 웹 주소
	JButton bt_preview2; //
	JButton bt_edit; //
	JButton bt_del; //
	
	//하위카테고리 선택시 담아놓을 pk (subcategory_idx)
	List<Integer> subIdxList=new ArrayList<Integer>();
	
	//동쪽영역용
	List<Integer> subIdxList2=new ArrayList<Integer>();

	String dir="C:/java_workspace2/data/shop/product/";
	String filename;//서쪽영역에서 미리보기될 이미지명
	Image image; //서쪽영역에서 미리보기될 이미지
	Image image2; //동쪽영역에서 미리보기될 이미지
	
	Product currentProduct; //현재 보고있는 상품
	
	public AdminMain() {
		topCategoryDAO = new TopCategoryDAO();
		subCategoryDAO = new SubCategoryDAO();
		productDAO = new ProductDAO();
		
		//서쪽
		p_west = new JPanel();
		box_top = new JComboBox<String>();
		box_sub = new JComboBox<String>();
		t_name = new JTextField();
		t_brand = new JTextField();
		t_price = new JTextField("0"); //가격 실수방지 차원
		preview = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				
				g2.drawImage(image, 0, 0, 140, 140, AdminMain.this);
			}
		};
		t_url = new JTextField();
		bt_preview = new JButton("가져오기");
		bt_regist = new JButton("등록");
		bt_list = new JButton("목록");
		
		p_west.setPreferredSize(new Dimension(150, 500));
		Dimension d = new Dimension(140, 25);
		box_top.setPreferredSize(d);
		box_sub.setPreferredSize(d);
		t_name.setPreferredSize(d);
		t_brand.setPreferredSize(d);
		t_price.setPreferredSize(d);
		preview.setPreferredSize(new Dimension(140, 140));
		preview.setBackground(Color.BLACK);
		t_url.setPreferredSize(d);
		bt_preview.setPreferredSize(d);
		
		p_west.add(box_top);
		p_west.add(box_sub);
		p_west.add(t_name);
		p_west.add(t_brand);
		p_west.add(t_price);
		p_west.add(preview);
		p_west.add(t_url);
		p_west.add(bt_preview);
		p_west.add(bt_regist);
		p_west.add(bt_list);
		
		//센터영역
		p_center = new JPanel();
		
		p_search = new JPanel();
		box_category = new JComboBox<String>();
		t_keyword = new JTextField(25);
		bt_search = new JButton("검색");
		p_search.add(box_category);
		p_search.add(t_keyword);
		p_search.add(bt_search);
		
		table = new JTable(model=new ProductModel(this));
		scroll = new JScrollPane(table);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p_search, BorderLayout.NORTH);
		p_center.add(scroll);
		
		
		//동쪽 
		p_east = new JPanel();
		box_top2 = new JComboBox<String>();
		box_sub2 = new JComboBox<String>();
		t_name2 = new JTextField();
		t_brand2 = new JTextField();
		t_price2 = new JTextField();
		preview2 = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				g2.clearRect(0, 0, 140, 140);
				
				g2.drawImage(image2, 0, 0, 140, 140, AdminMain.this);
			}
		};
		t_url2 = new JTextField();
		bt_preview2 = new JButton("가져오기");
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");
		
		p_east.setPreferredSize(new Dimension(150, 500));
		Dimension d2 = new Dimension(140, 25);
		box_top2.setPreferredSize(d2);
		box_sub2.setPreferredSize(d2);
		t_name2.setPreferredSize(d2);
		t_brand2.setPreferredSize(d2);
		t_price2.setPreferredSize(d2);
		preview2.setPreferredSize(new Dimension(140, 140));
		preview2.setBackground(Color.BLACK);
		t_url2.setPreferredSize(d2);
		bt_preview2.setPreferredSize(d);
		
		p_east.add(box_top2);
		p_east.add(box_sub2);
		p_east.add(t_name2);
		p_east.add(t_brand2);
		p_east.add(t_price2);
		p_east.add(preview2);
		p_east.add(t_url2);
		p_east.add(bt_preview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		
		add(p_west, BorderLayout.WEST);
		add(p_center);
		add(p_east, BorderLayout.EAST);
		
		getTopList();
		
		setSize(900,500);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
			}
		});
		
		box_top.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					//System.out.println(e.getItem());
					int topCategory_idx = topCategoryDAO.getTopCategoryIdx((String)e.getItem());
					System.out.println(topCategory_idx);
					
					//하위 카테고리 출력
					getSubList(box_sub, subIdxList, topCategory_idx);
				}
			}
		});
		
		//동쪽 영역의 이벤트 처리
		box_top2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					//System.out.println(e.getItem());
					int topCategory_idx = topCategoryDAO.getTopCategoryIdx((String)e.getItem());
					System.out.println(topCategory_idx);
					
					//하위 카테고리 출력
					getSubList(box_sub2, subIdxList2, topCategory_idx);
				}
			}
		});
		
		//버튼들과 리스너 연결
		bt_regist.addActionListener(this);
		bt_preview.addActionListener(this);
		bt_search.addActionListener(this);
		bt_preview2.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);
		
		//마우스 리스너 연결 
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();//유저가 선택한 행(층)
				//이 정보로  ArrayList의 index를 접근할 수 있다!!
				//int col=table.getSelectedColumn();//유저가 선택한 열(호)
				//System.out.println("row는 "+row+", col 은"+col);
				String product_idx=(String)table.getValueAt(row ,2);
				System.out.println("product_idx="+product_idx);
				
				getDetail(Integer.parseInt(product_idx));
			}
		});
		
	}
	
	//선택한 레코드 한건 가져오기 (결과를 우측에 반영)
	public void getDetail(int product_idx) {
		Product product=productDAO.select(product_idx);
		currentProduct=product;//현재 보고있는 상품 대입
		
		//우측영역에 반영하기!!
		
		//1)  TopCategory DTO 에 들어있는 카테고리 이름을 추출
		SubCategory subCategory=product.getSubcategory();
		TopCategory topCategory=subCategory.getTopCategory();
		String  topName=topCategory.getTopcategory_name();
		
		//2) 추출한 이름이 콤보박스에 몇번째 한글과 동일한지 그 index알아맞추기
		for(int i=0;i<box_top2.getItemCount();i++) {
			String value=box_top2.getItemAt(i);//0-안내, 1-상의,2-하의..
			if(value.equals(topName)) {//동일이름 발견되면
				System.out.println(topName+"이 "+i+" 번째 index에서 발견됨");
				box_top2.setSelectedIndex(i); 
			}
		}
		
		getSubList(box_sub2, subIdxList2, topCategory.getTopcategory_idx());
		
		//하위 카테고리 이름을 추출 
		String subName=subCategory.getSubcategory_name();
		for(int i=0;i<box_sub2.getItemCount();i++) {
			String value=box_sub2.getItemAt(i);//0-안내, 1-상의,2-하의..
			if(value.equals(subName)) {//동일이름 발견되면
				System.out.println(subName+"이 "+i+" 번째 index에서 발견됨");
				box_sub2.setSelectedIndex(i); 
			}
		}
		
		//상품명, 브랜드 , 가격 
		t_name2.setText(product.getProduct_name());
		t_brand2.setText(product.getBrand());
		t_price2.setText(Integer.toString(product.getPrice()));
		
		//현재 선택한 사진 
		filename=product.getFilename();
		this.setTitle(dir+filename);
		
		//우측영역에 사진 그리기!!
		try {
			image2 = ImageIO.read(new File(dir+product.getFilename()));
			preview2.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//상위카테고리 가져오기
	public void getTopList() {
		List<TopCategory> topList = topCategoryDAO.selectAll();
		
		//서쪽영역
		box_top.addItem("카테고리 선택▼");
		for(TopCategory topCategory : topList) {
			box_top.addItem(topCategory.getTopcategory_name());
		}
		
		//동쪽영역
		box_top2.addItem("카테고리 선택▼");
		for(TopCategory topCategory : topList) {
			box_top2.addItem(topCategory.getTopcategory_name());
		}		
	}
	
	//하위카테고리 가져오기 
	public void getSubList(JComboBox box, List list, int topcategory_idx) {
		//하의-> 청바지, 반바지, 면바지,..
		List<SubCategory> subList=subCategoryDAO.selectByTopCategory(topcategory_idx);
		System.out.println(subList.size());
		
		//기존 아이템 싹 비우기!!
		box.removeAllItems(); //서쪽이 될지 동쪽이 될지 알수없다
		list.removeAll(list); //서쪽이 될지 동쪽이 될지 알수없다
		
		//해당 index에서 요소로 들어있는 DTO 꺼내기!
		box.addItem("하위 카테고리▼");
		for(int i=0;i<subList.size();i++) {
			SubCategory subCategory=subList.get(i);
			box.addItem(subCategory.getSubcategory_name());
			//한글로된 아이템 이름만 출력하지 말고, 해당 pk도 보관하자
			list.add(subCategory.getSubcategory_idx());
		}
		System.out.println("보관된 하위카테고리 idx 수는 "+list);
		
	}

	
	
	public boolean downLoad(JTextField url_input) {
		//로컬에 있는 이미지가 아닌 웹의 url상의 이미지
		//를 로컬로 수집한다
		//finally에서 닫을 예정이므로 try문 밖으로 빼놓자
		InputStream is=null;
		FileOutputStream fos=null;
		boolean flag=false;
		
		try {
			URL url=new URL(url_input.getText());
			System.out.println(url);
			is=url.openStream();
			
			filename=StringUtil.createFilename(url.toString());
			fos=new FileOutputStream(dir+filename);
			
			int data=-1;
			while(true) {
				data=is.read(); //1byte 읽기
				if(data==-1)break;
				//내뱉기
				fos.write(data);
			}
			JOptionPane.showMessageDialog(this,"수집완료");
			flag=true;
			
		} catch (MalformedURLException e) {
			//e.printStackTrace();
			flag=false;
		} catch (IOException e) {
			//e.printStackTrace();
			flag=false;
		}finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return flag;
	}
	
	//이미지 미리보기 (서,동쪽 모두 처리용)
	public void previewWithURL(JTextField input_url, JPanel canvas) {
		try {
			//서쪽,동쪽 텍스트필드 중 어느것이 될지 모른다
			URL url=new URL(input_url.getText());
			if(canvas==preview) { //서쪽 미리보기 패널이라면
				image=ImageIO.read(url); //서쪽창
			}else { // 동쪽 미리보기 패널이라면
				image2=ImageIO.read(url); //동쪽창
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas.repaint();
	}
	
	public void preview(JPanel canvas) {
		File file = new File(dir+filename);
		try {
			if(canvas==preview) {
				image = ImageIO.read(file);
			}else {
				image2 = ImageIO.read(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas.repaint(); //서쪽 or 동쪽 패널 다시 그리기!
	}
	
	//모든 상품 레코드 가져오기(단 하위카테고리와 조인된 상태로..)
	public void getProductList() {
		//모델 업데이트 ,  Jtable 업데이트 
		model.getProductList();
		table.updateUI();
	}
	
	//상품 등록 
	public void regist() {
		boolean result=downLoad(t_url);
		if(result==false){
			JOptionPane .showMessageDialog(this, "다운로드 실패");
			return;
		}
		
		//ProductDAO에게 insert 시키자
		//유저가 지금 선택한 하위 카테고리 콤보박스의 index
		int index=box_sub.getSelectedIndex();
		
		int subcategory_idx=subIdxList.get(index-1);
		String product_name=t_name.getText();
		String brand=t_brand.getText();
		int price=Integer.parseInt(t_price.getText());
		
		Product product=new Product(); //empty 텅빈
		SubCategory subCategory=new SubCategory();
		product.setSubcategory(subCategory);
		
		subCategory.setSubcategory_idx(subcategory_idx);
		product.setProduct_name(product_name);
		product.setBrand(brand);
		product.setPrice(price);
		product.setFilename(filename);
		
		int n = productDAO.insert(product);
		if(n>0) {
			JOptionPane.showMessageDialog(this, "등록성공");
			getProductList();
		}
		
	}
	
	//선택한 상품 1건 삭제
	public void del() {
		if(currentProduct==null) {
			JOptionPane.showMessageDialog(this, "선택된 상품이 없습니다\n먼저 상품을 선택해주세요");
		}else {
			int op=JOptionPane.showConfirmDialog(this, "삭제하시겠어여?");
			if(op == JOptionPane.OK_OPTION){//승인을 누르면
				//파일삭제 
				boolean result=ImageManager.deleteFile(dir+currentProduct.getFilename());
				
				//db 레코드 삭제
				if(result) {
					int n=productDAO.delete(currentProduct.getProduct_idx());
					if(n >0) {
						JOptionPane.showMessageDialog(this, "삭제성공");
						//JTable은 ProductModel이 보유한 productList 만 참조
						//하고 있으므로, 갱신된 내용을 보여주려면 결국 productList가
						//변경되어야 한다!!! 따라서  db 다시 조회한 후, productList
						//를 재설정하면 됨 JTable. updateUI()
						getProductList();
						currentProduct=null;//다시 아무것도 선택하지 않은 상태로..
						reset();
					}
				}
				
			}
		}
	}
	
	//삭제 후 우측영역 리셋
	public void reset() {
		//콤보박스들 초기화 
		box_top2.setSelectedIndex(0);//안내문구
		box_sub2.setSelectedIndex(0);//안내문구
		
		//상품정보 초기화
		t_name2.setText("");
		t_brand2.setText("");
		t_price2.setText("0");
		image2=null;
		preview2.repaint();
		
	}
	
	//상품 수정
	public void update() {
		boolean result=false;
		
		if(t_url2.getText().length()>15) {
			//사용자가 파일 삭제를 원한다면 지워주자
			result=ImageManager.deleteFile(dir+currentProduct.getFilename());
			
			//지정한 URL의 파일 다운로드
			result=downLoad(t_url2);
		}else {
			//파일 교체를 희망하지 않으면 기존의 이름 유지
			filename=currentProduct.getFilename();
		}
		
//		if(result==false){
//			return;
//		}
		
		//if(result) {
			//db update 
		Product product=new Product();//emtpy
		SubCategory subCategory=new SubCategory();//empty
		product.setSubcategory(subCategory);//참조관계
		
		int subcategory_idx=subIdxList2.get(box_sub2.getSelectedIndex()-1);
		subCategory.setSubcategory_idx(subcategory_idx);
		product.setProduct_name(t_name2.getText());
		product.setBrand(t_brand2.getText());
		product.setPrice(Integer.parseInt(t_price2.getText()));
		product.setFilename(filename);
		product.setProduct_idx(currentProduct.getProduct_idx());
		
		int n=productDAO.update(product);
		if(n>0) {
			//refresh
			JOptionPane.showMessageDialog(this, "수정완료");
			currentProduct=product; //이 처리를 해주지 않으면 수정 작업
			getProductList();
		}
		//}
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj.equals(bt_preview)) {
			previewWithURL(t_url, preview);
		}else if(obj.equals(bt_regist)){
			regist();
		}else if(obj.equals(bt_list)){
			getProductList();
		}else if(obj.equals(bt_search)){
			
		}else if(obj.equals(bt_preview2)){
			
			previewWithURL(t_url2, preview2);
			
		}else if(obj.equals(bt_edit)){
			if(currentProduct !=null) {//선택한 상품이 잇다면..
				update(); //상품 정보 수정 
			}else {
				JOptionPane.showMessageDialog(this, "수정하실 상품을 선택하세요");
			}
		}else if(obj.equals(bt_del)){
			del();
		}
	}
	
	public static void main(String[] args) {
		new AdminMain();
	}
}












