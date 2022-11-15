package com.sh.member.controller;

import java.sql.Date;
import java.util.List;

import com.sh.member.model.dto.Member;
import com.sh.member.model.service.MemberService;

public class MemberController {
	private MemberService memberService = new MemberService();

	public int insertMember(Member member) {
		return memberService.insertMember(member);
	}

	public List<Member> findByName(String name) {
		return memberService.findByName(name);
	}

	public Member findById(String id) {
		return memberService.findById(id);
	}

	public List<Member> findAll() {
		return memberService.findAll();
	}

	public int uqdateName(String id, String name) {
		return memberService.updateName(id,name);
	}

	public int updateBirthday(String id, Date birthday) {
		return memberService.updateBirthday(id,birthday);
	}

	public int updateEmail(String id, String email) {
		return memberService.updateEmail(id,email);
	}

	public int deleteMember(String id) {
		return memberService.deleteMember(id);
	}

	public List<Member> findDelAll() {
		return memberService.findDelAll();
	}
}
