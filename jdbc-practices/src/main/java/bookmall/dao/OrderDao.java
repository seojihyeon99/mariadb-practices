package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {

	public int insert(OrderVo orderVo) {
		int count = 0;
		String sql = "insert into orders (number, status, payment, shipping, user_no) values (?, ?, ?, ?, ?)";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		){
			pstmt.setString(1, orderVo.getNumber());
			pstmt.setString(2, orderVo.getStatus());
			pstmt.setInt(3, orderVo.getPayment());
			pstmt.setString(4, orderVo.getShipping());
			pstmt.setInt(5, orderVo.getUserNo());
			// 추후 '비회원주문'을 고려하여 주문자(이름/이메일)도 저장할 수 있게 확장 필요
			
			count = pstmt.executeUpdate();
			
			if(count == 1) {
				// auto-increment 키 가져오기
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						orderVo.setNo(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;	
		
	}

	public int insertBook(OrderBookVo orderBookVo) {
		int count = 0;
		String sql = "insert into orders_book (orders_no, book_no, quantity, price) values(?, ?, ?, ?)";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setInt(1, orderBookVo.getOrderNo());
			pstmt.setInt(2, orderBookVo.getBookNo());
			pstmt.setInt(3, orderBookVo.getQuantity());
			pstmt.setInt(4, orderBookVo.getPrice());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;	
	}

	public int deleteByNo(int no) {
		int count = 0;
		String sql = "delete from orders where no = ?";
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

	public int deleteBooksByNo(int no) {
		int count = 0;
		String sql = "delete from orders_book where orders_no = ?";
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

	public OrderVo findByNoAndUserNo(long no, int userNo) {
		OrderVo vo = null;
		String sql = "select number, payment, status, shipping "
					+ "from orders "
					+ "where no = ? and user_no = ?";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setLong(1, no);
			pstmt.setInt(2, userNo);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					vo = new OrderVo();
					vo.setNumber(rs.getString(1));
					vo.setPayment(rs.getInt(2));
					vo.setStatus(rs.getString(3));
					vo.setShipping(rs.getString(4));
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return vo;
	}

	public List<OrderBookVo> findBooksByNoAndUserNo(int ordersNo, int userNo) {
		List<OrderBookVo> result = new ArrayList<>();
		String sql = "select ob.orders_no, ob.quantity, ob.price, ob.book_no, b.title " 
					+ "from orders_book ob join orders o join book b " 
					+ "where ob.orders_no = o.no and ob.book_no = b.no and o.no = ? and o.user_no = ?";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setInt(1, ordersNo);
			pstmt.setInt(2, userNo);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					OrderBookVo vo = new OrderBookVo();
					vo.setOrderNo(rs.getInt(1));
					vo.setQuantity(rs.getInt(2));
					vo.setPrice(rs.getInt(3));
					vo.setBookNo(rs.getInt(4));
					vo.setBookTitle(rs.getString(5));
					
					result.add(vo);
				}
				
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;	
	}
}
