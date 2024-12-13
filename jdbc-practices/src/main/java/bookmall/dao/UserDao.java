package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.UserVo;
import emaillist.vo.EmaillistVo;

public class UserDao {

	public Boolean insert(UserVo userVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "insert into user (name, phone, email, password) values (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, userVo.getName());
			pstmt.setString(2, userVo.getPhone());
			pstmt.setString(3, userVo.getEmail());
			pstmt.setString(4, userVo.getPassword());
			
			int count = pstmt.executeUpdate();
			
			if(count == 1) {
				result = true;
				
				rs = pstmt.getGeneratedKeys(); // auto increment 키 값 가져오기
				
	            if (rs.next()) {
	                int no = rs.getInt(1);
	                userVo.setNo(no);
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

	public List<UserVo> findAll() {
		List<UserVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionUtil.getConnection();

			String sql = "select no, name, phone, email, password " + 
						 "  from user";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				String email = rs.getString(4);
				String password = rs.getString(5);
				
				UserVo vo = new UserVo(name, email, password, phone);
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
