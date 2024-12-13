package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
	
	private static final String DB_URL = "jdbc:mariadb://192.168.0.18:3306/bookmall";
	private static final String DB_USER = "bookmall";
	private static final String DB_PASSWORD = "bookmall";
	
	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}		
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);		
	}
}
