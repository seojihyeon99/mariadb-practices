package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bookmall.vo.BookVo;

public class BookDao {

	public int insert(BookVo bookVo) {
		int count = 0;
		String sql = "insert into book (title, price, category_no) values (?, ?, ?)";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		){			
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setInt(2, bookVo.getPrice());
			pstmt.setInt(3, bookVo.getCategoryNo());
			
			count = pstmt.executeUpdate();
			
			if(count == 1) {
				// auto-increment 키 가져오기
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if(rs.next()) {
						bookVo.setNo(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;	
	}

	public int deleteByNo(int no) {
		int count = 0;
		String sql = "delete from book where no = ?";
		
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
