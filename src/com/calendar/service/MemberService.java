package com.calendar.service;

import java.util.List;

import com.calendar.domain.MemberVO;
import com.calendar.domain.ScheduleVO;

/* 사용자 기능 관련 추상 메소드 정의 */
public interface MemberService {
	
	// 로그인
	int login(String id, String pw);
	
	// 회원가입
	void join(MemberVO member);
	
	// 아이디 중복체크
	boolean checkId(String id);
	
	// 사용자 정보 조회
	MemberVO getMemberById(String mem_id);
	
	// 일정 목록 조회
	List<ScheduleVO> getScheduleList(String mem_id);

	// 일정 상세 조회
	ScheduleVO getScheduleByNo(int schedule_no);
	
	// 일정 추가
	void addSchedule(ScheduleVO schedule);
	
	// 일정 수정
	void modifySchedule(ScheduleVO schedule);
	
	// 일정 삭제
	void removeSchedule(int schedule_no);
	
	// 해당 날짜의 일정 목록 조회
	List<ScheduleVO> getScheduleListByDate(String mem_id, String date);
}
