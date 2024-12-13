package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;
import bookmall.vo.CategoryVo;
import bookmall.vo.UserVo;

public class CartDao {

	public Boolean insert(CartVo cartVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "insert into cart (quantity, book_no, user_no) values(?, ?, ?)";
			
			pstmt.setInt(1, cartVo.getQuantity());
			pstmt.setInt(2, cartVo.getBookNo());
			pstmt.setInt(3, cartVo.getUserNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
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

	public List<CartVo> findByUserNo(int no) {
		List<CartVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "select quantity, book_no, user_no, title " + 
						 "  from cart join book " + 
						 "	where cart.book_no = book.no";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int quantity = rs.getInt(1);
				int bookNo = rs.getInt(2);
				int userNo = rs.getInt(3);
				String title = rs.getString(4);
				
				CartVo vo = new CartVo();
				vo.setQuantity(quantity);
				vo.setBookNo(bookNo);
				vo.setUserNo(userNo);
				vo.setBookTitle(title);
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
