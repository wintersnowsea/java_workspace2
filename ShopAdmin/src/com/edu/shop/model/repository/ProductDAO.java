package com.edu.shop.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shop.domain.Product;
import com.edu.shop.domain.SubCategory;
import com.edu.shop.domain.TopCategory;
import com.edu.shop.util.DBManager;

//Product 테이블에 대한 CRUD를 수행할 객체
public class ProductDAO {
	DBManager dbManager=DBManager.getInstance();
	
	public int insert(Product product) {
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="insert into product(product_idx, subcategory_idx, product_name, brand, price, filename)";
		sql+=" values(seq_product.nextval,?,?,?,?,?)";
		//sql+=" values(seq_product.nextval,"+subcategory_idx+",'"+product_name+"','"+brand+"',"+price+",'"+filename+"')";
		
		int result=0;
		try {
			pstmt=con.prepareStatement(sql);
			//쿼리 수행 전 바인드변수값을 결정짓자!
			pstmt.setInt(1, product.getSubcategory().getSubcategory_idx());
			pstmt.setString(2, product.getProduct_name());
			pstmt.setString(3, product.getBrand());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFilename());
			
			result=pstmt.executeUpdate(); //DML 수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		System.out.println(sql);
		return result;
	}
	
	//하위 카테고리와 조인한 모든 상품 가져오기
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<Product> list=new ArrayList<Product>();
		
		con=dbManager.getConnection();
		
		StringBuffer sb=new StringBuffer(); //Buffer이기때문에 String형이 아님
		sb.append("select s.subcategory_idx as subcategory_idx, subcategory_name,");
		sb.append(" product_idx, product_name, brand, price, filename");
		sb.append(" from subcategory s, product p");
		sb.append(" where s.subcategory_idx=p.subcategory_idx");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Product product=new Product();
				SubCategory subCategory=new SubCategory();
				//SubCategory subCategory=product.getSubcategory();
				
				//subCategory를 product 넣어주자
				product.setSubcategory(subCategory);
				
				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));
				
				//이미 product가 가진 SubCategory DTO이므로 Product만 담으면 된다!!
				//(has a 관계)
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return list;
	}
	
	//상,하위 카테고리와 조인하여 상품 한 건 가져오기
	public Product select(int product_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Product product=null; //return하기 위해
		
		con=dbManager.getConnection();
		StringBuffer sb=new StringBuffer(); 
		sb.append("select t.topcategory_idx as topcategory_idx, topcategory_name");
		sb.append(", s.subcategory_idx as subcategory_idx, subcategory_name");
		sb.append(", product_idx, product_name, brand, price, filename");
		sb.append(" from topcategory t, subcategory s, product p");
		sb.append(" where t.topcategory_idx = s.topcategory_idx");
		sb.append(" and s.subcategory_idx = p.subcategory_idx");
		sb.append(" and p.product_idx=?");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, product_idx);
			rs=pstmt.executeQuery();
			
			//레코드가 있다면 커서 이동시 true 반환
			if(rs.next()) {
				product=new Product();
				SubCategory subCategory=new SubCategory();
				TopCategory topCategory=new TopCategory();
				subCategory.setTopCategory(topCategory);
				product.setSubcategory(subCategory);
				
				//상위 담기
				topCategory.setTopcategory_idx(rs.getInt("topcategory_idx"));
				topCategory.setTopcategory_name(rs.getString("topcategory_name"));
				
				//하위 담기
				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));

				//상품 담기
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return product;
	}
	
	//레코드 1건 삭제
	public int delete(int product_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		String sql="delete product where product_idx=?";
		int result=0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, product_idx);
			result=pstmt.executeUpdate(); //삭제
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
	
	//레코드 1건 수정
	public int update(Product product) {
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="update product set subcategory_idx=?";
		sql+=" , product_name=?, brand=?, price=?, filename=?";
		sql+="where product_idx=?";
		
		int result=0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, product.getSubcategory().getSubcategory_idx());
			pstmt.setString(2, product.getProduct_name());
			pstmt.setString(3, product.getBrand());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFilename());
			pstmt.setInt(6, product.getProduct_idx());
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
	
}
