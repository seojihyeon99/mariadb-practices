package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.UserVo;

public class UserDao {

	public int insert(UserVo userVo) {
		int count = 0;
		String sql = "insert into user (name, phone, email, password) values (?, ?, ?, ?)";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		){
			pstmt.setString(1, userVo.getName());
			pstmt.setString(2, userVo.getPhone());
			pstmt.setString(3, userVo.getEmail());
			pstmt.setString(4, userVo.getPassword());
			
			count = pstmt.executeUpdate();
			
			if(count == 1) {
				// auto-increment 키 가져오기
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						userVo.setNo(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;	
	}

	public List<UserVo> findAll() {
		List<UserVo> result = new ArrayList<>();
		String sql = "select no, name, phone, email, password "
					+ "from user";
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			try (ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					UserVo vo = new UserVo();
					vo.setNo(rs.getInt(1));
					vo.setName(rs.getString(2));
					vo.setPhone(rs.getString(3));
					vo.setEmail(rs.getString(4));
					vo.setPassword(rs.getString(5));
					
					result.add(vo);
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;	
	}

	public int deleteByNo(int no) {
		int count = 0;
		String sql = "delete from user where no = ?";
		
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
