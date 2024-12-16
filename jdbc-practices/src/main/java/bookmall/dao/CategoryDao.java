package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CategoryVo;

public class CategoryDao {

	public int insert(CategoryVo categoryVo) {
		int count = 0;
		String sql = "insert into category (name) values (?)";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);			
		){
			pstmt.setString(1, categoryVo.getName());
			
			count = pstmt.executeUpdate();
			
			if(count == 1) {
				// auto-increment 키 가져오기
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if(rs.next()) {
						categoryVo.setNo(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;	
	}

	public List<CategoryVo> findAll() {
		List<CategoryVo> result = new ArrayList<>();
		String sql = "select no, name from category";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
		){
			while(rs.next()) {
				CategoryVo vo = new CategoryVo();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;	
	}

	public int deleteByNo(int no) {
		int count = 0;
		String sql = "delete from category where no = ?";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;		
	}
}
