package com.calendar.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.calendar.dao.MemberDAO;
import com.calendar.dao.ScheduleDAO;
import com.calendar.dao.impl.MemberDAOImpl;
import com.calendar.dao.impl.ScheduleDAOImpl;
import com.calendar.domain.MemberVO;
import com.calendar.domain.ScheduleVO;
import com.calendar.service.MemberService;

/* 사용자에게 제공되는 기능들의 로직을 구현할 Service 클래스 */
public class MemberServiceImpl implements MemberService{
	
	private MemberDAO memberDAO;
	private ScheduleDAO scheduleDAO;
	
	// 싱글톤 패턴
	private static MemberServiceImpl instance = null;
	
	private MemberServiceImpl() {
		memberDAO = MemberDAOImpl.getInstance();
		scheduleDAO = ScheduleDAOImpl.getInstance();
	}
	
	public static MemberServiceImpl getInstance() {
		if(instance == null) 
			instance = new MemberServiceImpl();
		return instance;
	}
	
	/*
	 * 로그인
	 * 아이디 존재 x : 0
	 * 비밀번호 불일치 : -1
	 * 로그인 성공 : 1
	 */
	@Override
	public int login(String id, String pw) {
		MemberVO member = null;
		
		try {
			member = memberDAO.selectMemberById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(member == null) 
			return 0;
		else {
			if(member.getMem_pw().equals(pw))
				return 1;
			else
				return -1;
		}
	}

	// 회원가입
	@Override
	public void join(MemberVO member) {
		try {
			memberDAO.insertMember(member);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 아이디 중복체크
	 * 아이디 존재 x (사용 가능) : true
	 * 아이디 존재 (사용 불가능) : false 
	 */
	@Override
	public boolean checkId(String id) {
		MemberVO member = null;
		
		try {
			member = memberDAO.selectMemberById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(member == null)
			return true;
		else 
			return false;
	}
	
	// 사용자 정보 조회
	@Override
	public MemberVO getMemberById(String mem_id) {
		MemberVO member = null;
		
		try {
			member = memberDAO.selectMemberById(mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}
	
	// 일정 목록조회
	@Override
	public List<ScheduleVO> getScheduleList(String mem_id) {
		List<ScheduleVO> scheduleList = null;
		
		try {
			scheduleList = scheduleDAO.selectScheduleList(mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return scheduleList;
	}

	// 일정 상세조회
	@Override
	public ScheduleVO getScheduleByNo(int schedule_no) {
		ScheduleVO schedule = null;
		
		try {
			schedule = scheduleDAO.selectScheduleByNo(schedule_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return schedule;
	}

	// 일정 추가
	@Override
	public void addSchedule(ScheduleVO schedule) {
		try {
			scheduleDAO.insertSchedule(schedule);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 일정 수정 
	@Override
	public void modifySchedule(ScheduleVO schedule) {
		try {
			scheduleDAO.updateSchedule(schedule);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 일정 삭제
	@Override
	public void removeSchedule(int schedule_no) {
		try {
			scheduleDAO.deleteSchedule(schedule_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 해당 날짜의 일정 목록 조회
	@Override
	public List<ScheduleVO> getScheduleListByDate(String mem_id, String date) {
		List<ScheduleVO> scheduleList = null;
		
		try {
			scheduleList = scheduleDAO.selectScheduleListByDate(mem_id, date);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return scheduleList;
	}
}