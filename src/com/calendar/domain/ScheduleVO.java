package com.calendar.domain;

import java.sql.Timestamp;

/* 사용자의 일정 정보를 담을 VO 클래스 */
public class ScheduleVO {
	
	private int schedule_no;
	private String schedule_title;
	private String schedule_content;
	private Timestamp schedule_date;
	private String schedule_start;
	private String schedule_end;
	private String mem_id;
	private char schedule_import_yn;
	private char schedule_update_yn;
	
	public int getSchedule_no() {
		return schedule_no;
	}
	public void setSchedule_no(int schedule_no) {
		this.schedule_no = schedule_no;
	}
	public String getSchedule_title() {
		return schedule_title;
	}
	public void setSchedule_title(String schedule_title) {
		this.schedule_title = schedule_title;
	}
	public String getSchedule_content() {
		return schedule_content;
	}
	public void setSchedule_content(String schedule_content) {
		this.schedule_content = schedule_content;
	}
	public Timestamp getSchedule_date() {
		return schedule_date;
	}
	public void setSchedule_date(Timestamp schedule_date) {
		this.schedule_date = schedule_date;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public char getSchedule_import_yn() {
		return schedule_import_yn;
	}
	public void setSchedule_import_yn(char schedule_import_yn) {
		this.schedule_import_yn = schedule_import_yn;
	}
	public String getSchedule_start() {
		return schedule_start;
	}
	public void setSchedule_start(String schedule_start) {
		this.schedule_start = schedule_start;
	}
	public String getSchedule_end() {
		return schedule_end;
	}
	public void setSchedule_end(String schedule_end) {
		this.schedule_end = schedule_end;
	}
	public char getSchedule_update_yn() {
		return schedule_update_yn;
	}
	public void setSchedule_update_yn(char schedule_update_yn) {
		this.schedule_update_yn = schedule_update_yn;
	}
	@Override
	public String toString() {
		return "ScheduleVO [schedule_no=" + schedule_no + ", schedule_title=" + schedule_title + ", schedule_content="
				+ schedule_content + ", schedule_date=" + schedule_date + ", schedule_start=" + schedule_start
				+ ", schedule_end=" + schedule_end + ", mem_id=" + mem_id + ", schedule_import_yn=" + schedule_import_yn
				+ ", schedule_update_yn=" + schedule_update_yn + "]";
	}
}
