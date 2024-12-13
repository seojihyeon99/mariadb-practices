package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bookmall.vo.BookVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {

	public Boolean insert(OrderVo orderVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "insert into order (number, status, payment, shipping, user_no) values(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, orderVo.getNumber());
			// Orderer??!?!?!?!?@@@@@@@@@@@@@@@
			pstmt.setString(2, orderVo.getStatus());
			pstmt.setInt(3, orderVo.getPayment());
			pstmt.setString(4, orderVo.getShipping());
			pstmt.setInt(5, orderVo.getUserNo());
			
			int count = pstmt.executeUpdate();
			
			if(count == 1) {
				result = true;
				
				rs = pstmt.getGeneratedKeys(); // auto increment 키 값 가져오기
				
	            if (rs.next()) {
	                int no = rs.getInt(1);
	                orderVo.setNo(no);
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

	public Boolean insertBook(OrderBookVo orderBookVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "insert into orders_book (orders_no, book_no, qunatity, price) values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, orderBookVo.getOrderNo());
			pstmt.setInt(2, orderBookVo.getBookNo());
			pstmt.setInt(3, orderBookVo.getQuantity());
			pstmt.setInt(4, orderBookVo.getPrice());
			
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
}
