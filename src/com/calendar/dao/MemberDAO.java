package com.calendar.dao;

import java.sql.SQLException;

import com.calendar.domain.MemberVO;

/* Member 테이블 관련 추상 메소드 정의 */
public interface MemberDAO {

	// 사용자 정보 조회
	MemberVO selectMemberById(String mem_id) throws SQLException;
	
	// 사용자 추가
	void insertMember(MemberVO member) throws SQLException;
}
