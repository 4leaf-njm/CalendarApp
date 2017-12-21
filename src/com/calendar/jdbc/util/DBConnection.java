package com.calendar.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* Connection 객체를 생성해주는 클래스 */
public class DBConnection {
	public static Connection getConnection()
    {
        Connection conn = null;
        try {
            String username = "nscompany"; 
            String password = "nscompany123";
            String url = "jdbc:mysql://my5008.gabiadb.com:3306/nscompany"; // DB서버의 URL 주소
            
            Class.forName("com.mysql.jdbc.Driver"); // 드라이버 로드
            conn = DriverManager.getConnection(url, username, password); // Connection 생성 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;     
    }
}


