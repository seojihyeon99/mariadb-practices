package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.BookVo;

public class BookDao {
	public int insert(BookVo vo) {
		int count = 0;
		
		// try-with-resources
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into book(title, author_id) values(?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
		){
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setLong(2, vo.getAuthorId());
			count = pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			vo.setId(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;		
	}
	
	public int deleteById(Long id) {
		int count = 0;
		
		// try-with-resources
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("delete from author where id = ?");
		){
			pstmt1.setLong(1, id);
			count = pstmt1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;			
	}
	
	public int deleteAll() {
		int count = 0;
		
		// try-with-resources
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("delete from book");
		){
			count = pstmt1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;			
	}	
	
	public int update(Long id, String status) {
		int count = 0;
		
		// try-with-resources
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update book set status = ? where id = ?");
		){
			pstmt.setString(1, status);
			pstmt.setLong(2, id);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;	
	}

	public List<BookVo> findAll() {
		List<BookVo> result = new ArrayList<>();
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select b.id, b.title, a.name, b.status "
															+ "from book b join author a "
															+ "on b.author_id = a.id;");
		) {
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String authorName = rs.getString(3);
				String status = rs.getString(4);
				
				BookVo vo = new BookVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setAuthorName(authorName);
				vo.setStatus(status);
				
				result.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {		
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.18:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;		
	}

}
