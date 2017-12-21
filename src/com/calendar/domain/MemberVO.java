package com.calendar.domain;

/* 사용자 정보를 담을 VO 클래스 */
public class MemberVO {
	private String mem_id;
	private String mem_pw;
	private String mem_name;
	private String mem_birth;
	private String mem_phone;
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_birth() {
		return mem_birth;
	}
	public void setMem_birth(String mem_birth) {
		this.mem_birth = mem_birth;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}

	@Override
	public String toString() {
		return "MemberVO [mem_id=" + mem_id + ", mem_pw=" + mem_pw + ", mem_name=" + mem_name + ", mem_birth="
				+ mem_birth + ", mem_phone=" + mem_phone + "]";
	}
}
