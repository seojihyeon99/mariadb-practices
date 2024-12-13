package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bookmall.vo.BookVo;

public class BookDao {

	public Boolean insert(BookVo bookVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "insert into book (title, price) values(?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setInt(2, bookVo.getPrice());
			
			int count = pstmt.executeUpdate();
			
			if(count == 1) {
				result = true;
				
				rs = pstmt.getGeneratedKeys(); // auto increment 키 값 가져오기
				
	            if (rs.next()) {
	                int no = rs.getInt(1);
	                bookVo.setNo(no);
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

}
