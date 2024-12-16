package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;

public class CartDao {

	public int insert(CartVo cartVo) {
		int count = 0;
		String sql = "insert into cart (quantity, book_no, user_no) values(?, ?, ?)";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setInt(1, cartVo.getQuantity());
			pstmt.setInt(2, cartVo.getBookNo());
			pstmt.setInt(3, cartVo.getUserNo());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;	
	}

	public List<CartVo> findByUserNo(int userNo) {
		List<CartVo> result = new ArrayList<>();
		String sql = "select c.quantity, c.book_no, b.title "
					+ "from cart c join book b "
					+ "where c.book_no = b.no and c.user_no = ?";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setInt(1, userNo);
			
			try (ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					CartVo vo = new CartVo();
					vo.setQuantity(rs.getInt(1));
					vo.setBookNo(rs.getInt(2));
					vo.setBookTitle(rs.getString(3));
					
					result.add(vo);
				}				
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;	
	}

	public int deleteByUserNoAndBookNo(int userNo, int bookNo) {
		int count = 0;
		String sql = "delete from cart where user_no = ? and book_no = ?";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, bookNo);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;		
	}
}
