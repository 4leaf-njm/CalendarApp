package com.calendar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.calendar.dao.MemberDAO;
import com.calendar.domain.MemberVO;
import com.calendar.jdbc.util.DBConnection;
import com.calendar.jdbc.util.JDBCUtil;

/* Member 테이블에 접근할 MemberDAO 클래스 */
public class MemberDAOImpl implements MemberDAO{

	// 싱글톤 패턴
	private static MemberDAOImpl instance = null;
	
	private MemberDAOImpl() {}
	public static MemberDAOImpl getInstance() {
		if(instance == null)
			instance = new MemberDAOImpl();
		return instance;
	}
	
	/* 사용자 정보 조회 */
	@Override
	public MemberVO selectMemberById(String mem_id) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		MemberVO member = null;
		String query = "select * from member where mem_id = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_pw(rs.getString("mem_pw"));
				member.setMem_name(rs.getString("mem_name"));
				member.setMem_birth(rs.getString("mem_birth"));
				member.setMem_phone(rs.getString("mem_phone"));
			}
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return member;
	}

	/* 사용자 추가 */
	@Override
	public void insertMember(MemberVO member) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "insert into member values(?, ?, ?, ?, ?)";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMem_id());
			pstmt.setString(2, member.getMem_pw());
			pstmt.setString(3, member.getMem_name());
			pstmt.setString(4, member.getMem_birth());
			pstmt.setString(5, member.getMem_phone());
			pstmt.executeUpdate();
		}finally {
			JDBCUtil.close(conn, pstmt);
		}
	}
}
