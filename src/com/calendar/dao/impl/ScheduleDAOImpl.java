package com.calendar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.calendar.dao.ScheduleDAO;
import com.calendar.domain.ScheduleVO;
import com.calendar.jdbc.util.DBConnection;
import com.calendar.jdbc.util.JDBCUtil;

/* Schedule 테이블에 접근할 ScheduleDAO 클래스 */
public class ScheduleDAOImpl implements ScheduleDAO{

	/* 싱글톤 패턴 */
	private static ScheduleDAOImpl instance = null;
	
	private ScheduleDAOImpl(){}
	public static ScheduleDAOImpl getInstance() {
		if(instance == null)
			instance = new ScheduleDAOImpl();
		return instance;
	}
	
	/* 해당 사용자의 일정 전체 조회 */
	@Override
	public List<ScheduleVO> selectScheduleList(String mem_id) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ScheduleVO schedule = null;
		List<ScheduleVO> scheduleList = new ArrayList<ScheduleVO>();
		String query = "select * from schedule where mem_id = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				schedule = new ScheduleVO();
				schedule.setSchedule_no(rs.getInt("schedule_no"));
				schedule.setSchedule_title(rs.getString("schedule_title"));
				schedule.setSchedule_content(rs.getString("schedule_content"));
				schedule.setSchedule_date(rs.getTimestamp("schedule_date"));
				schedule.setSchedule_start(rs.getString("schedule_start"));
				schedule.setSchedule_end(rs.getString("schedule_end"));
				schedule.setMem_id(rs.getString("mem_id"));
				schedule.setSchedule_import_yn(rs.getString("schedule_import_yn").charAt(0));
				schedule.setSchedule_update_yn(rs.getString("schedule_update_yn").charAt(0));
				scheduleList.add(schedule);
			}
			return scheduleList;
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
	}

	/* 해당 사용자의 일정 한개 조회 */
	@Override
	public ScheduleVO selectScheduleByNo(int schedule_no) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ScheduleVO schedule = null;
		String query = "select * from schedule where schedule_no = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, schedule_no);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				schedule = new ScheduleVO();
				schedule.setSchedule_no(rs.getInt("schedule_no"));
				schedule.setSchedule_title(rs.getString("schedule_title"));
				schedule.setSchedule_content(rs.getString("schedule_content"));
				schedule.setSchedule_date(rs.getTimestamp("schedule_date"));
				schedule.setSchedule_start(rs.getString("schedule_start"));
				schedule.setSchedule_end(rs.getString("schedule_end"));
				schedule.setMem_id(rs.getString("mem_id"));
				schedule.setSchedule_import_yn(rs.getString("schedule_import_yn").charAt(0));
				schedule.setSchedule_update_yn(rs.getString("schedule_update_yn").charAt(0));
			}
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return schedule;
	}
	
	/* 일정 추가 */
	@Override
	public void insertSchedule(ScheduleVO schedule) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "insert into schedule(schedule_title, schedule_content, schedule_date, mem_id, schedule_import_yn, "
				     + "schedule_start, schedule_end) values(?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, schedule.getSchedule_title());
			pstmt.setString(2, schedule.getSchedule_content());
			pstmt.setTimestamp(3,  schedule.getSchedule_date());
			pstmt.setString(4, schedule.getMem_id());
			pstmt.setString(5, String.valueOf(schedule.getSchedule_import_yn()));
			pstmt.setString(6, schedule.getSchedule_start());
			pstmt.setString(7, schedule.getSchedule_end());
			pstmt.executeUpdate();
		}finally {
			JDBCUtil.close(conn, pstmt);
		}
	}

	/* 일정 수정 */
	@Override
	public void updateSchedule(ScheduleVO schedule) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query1 = "update schedule set schedule_update_yn = 'N'";
		String query2 = "update schedule set schedule_title = ?, schedule_content = ?, schedule_date = ?,"
				      + "schedule_import_yn = ?, schedule_start = ?, schedule_end = ?, schedule_update_yn = 'Y' where schedule_no = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query1);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(query2);
			pstmt.setString(1, schedule.getSchedule_title());
			pstmt.setString(2, schedule.getSchedule_content());
			pstmt.setTimestamp(3, schedule.getSchedule_date());
			pstmt.setString(4, String.valueOf(schedule.getSchedule_import_yn()));
			pstmt.setString(5, schedule.getSchedule_start());
			pstmt.setString(6, schedule.getSchedule_end());
			pstmt.setInt(7, schedule.getSchedule_no());
			pstmt.executeUpdate();
		}finally {
			JDBCUtil.close(conn, pstmt);
		}
	}

	/* 일정 삭제 */
	@Override
	public void deleteSchedule(int schedule_no) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "delete from schedule where schedule_no = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, schedule_no);
			pstmt.executeUpdate();
		}finally {
			JDBCUtil.close(conn, pstmt);
		}
	}
	
	/* 해당 날짜의 일정 조회 */
	@Override
	public List<ScheduleVO> selectScheduleListByDate(String mem_id, String date) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ScheduleVO schedule = null;
		List<ScheduleVO> scheduleList = new ArrayList<ScheduleVO>();
		String query = "select * from schedule where mem_id = ? and date_format(schedule_date, '%Y-%m-%d') = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				schedule = new ScheduleVO();
				schedule.setSchedule_no(rs.getInt("schedule_no"));
				schedule.setSchedule_title(rs.getString("schedule_title"));
				schedule.setSchedule_content(rs.getString("schedule_content"));
				schedule.setSchedule_date(rs.getTimestamp("schedule_date"));
				schedule.setSchedule_start(rs.getString("schedule_start"));
				schedule.setSchedule_end(rs.getString("schedule_end"));
				schedule.setMem_id(rs.getString("mem_id"));
				schedule.setSchedule_import_yn(rs.getString("schedule_import_yn").charAt(0));
				schedule.setSchedule_update_yn(rs.getString("schedule_update_yn").charAt(0));
				scheduleList.add(schedule);
			}
			return scheduleList;
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
	}
}
