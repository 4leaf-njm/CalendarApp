package com.calendar.dao;

import java.sql.SQLException;
import java.util.List;

import com.calendar.domain.ScheduleVO;

/* Schedule 테이블 관련 추상 메소드 정의 */
public interface ScheduleDAO {

	// 일정 목록조회
	List<ScheduleVO> selectScheduleList(String mem_id) throws SQLException;
	
	// 일정 하나조회
	ScheduleVO selectScheduleByNo(int schedule_no) throws SQLException;
	
	// 일정 추가
	void insertSchedule(ScheduleVO schedule) throws SQLException;
	
	// 일정 수정
	void updateSchedule(ScheduleVO schedule) throws SQLException;
	
	// 일정 삭제
	void deleteSchedule(int schedule_no) throws SQLException;
	
	// 해당 날짜의 일정 목록 조회
	List<ScheduleVO> selectScheduleListByDate(String mem_id, String date) throws SQLException;
	
}



