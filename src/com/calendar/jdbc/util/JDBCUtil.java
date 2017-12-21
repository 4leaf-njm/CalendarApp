package com.calendar.jdbc.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* DB 자원을 반환해주는 클래스 */
public class JDBCUtil {
	
	/* close 메소드 오버로딩 */
	public static void close(Connection conn) throws SQLException{
		if(conn != null) conn.close();
	}
	
	public static void close(Statement stmt) throws SQLException{
		if(stmt != null) stmt.close();
	}
	
	public static void close(ResultSet rs) throws SQLException{
		if(rs != null) rs.close();
	}
	
	public static void close(Connection conn, Statement stmt) throws SQLException {
		if(stmt != null) stmt.close();
		if(conn != null) conn.close();
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException{
		if(rs != null) rs.close();
		if(stmt != null) stmt.close();
		if(conn != null) conn.close();
	}
}
