package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CategoryVo;
import bookmall.vo.UserVo;

public class CategoryDao {

	public Boolean insert(CategoryVo categoryVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "insert into category (name) values(?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, categoryVo.getName());
			
			int count = pstmt.executeUpdate();
			
			if(count == 1) {
				result = true;
				
				rs = pstmt.getGeneratedKeys(); // auto increment 키 값 가져오기
				
	            if (rs.next()) {
	                int no = rs.getInt(1);
	                categoryVo.setNo(no);
	            }
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;	
	}

	public List<CategoryVo> findAll() {
		List<CategoryVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "select no, name" + 
						 "  from category";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				
				CategoryVo vo = new CategoryVo(name);
				vo.setNo(no);
				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;	
	}
}
