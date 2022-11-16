package com.sh.member.controller;

import java.sql.Date;
import java.util.List;

import com.sh.member.model.dto.Member;
import com.sh.member.model.service.MemberService;

public class MemberController {
	private MemberService memberService = new MemberService();

	public List<Member> findAll() {
		List<Member> members = null;
		
		try{
			members = memberService.findAll();
		}catch(Exception e) {
			// 1.예외로그
			e.printStackTrace();
			//2. 사용자에게 적절한 메세지 전달
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return members;
	}

	public int insertMember(Member member) {
		int result = 0;
		try {
		result = memberService.insertMember(member);
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return result;
	}

	public int deleteMember(String id) {
		int result = 0;
		try {
		result = memberService.deleteMember(id);
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return result;
	}

	public Member findById(String id) {
		Member member = null;
		
		try {
			member = memberService.findById(id);
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return member;
	}

	public List<Member> findByName(String name) {
		List<Member> members = null;
		try {
			members = memberService.findByName(name);
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return members;
	}

	public int updateName(String id, String name) {
		int result = 0;
		try {
			result = memberService.updateName(id,name);
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return result;
	}

	public int updateBirthday(String id, Date birthday) {
		int result = 0;
		try {
			result = memberService.updateBirthday(id,birthday);
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return result;
	}

	public int updateEmail(String id, String email) {
		int result = 0;
		try {
			result = memberService.updateEmail(id,email);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return result;
	}

	public List<Member> findDelAll() {
		List<Member> members = null;
		try {
			members = memberService.findDelAll();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 관리자에게 문의바랍니다.");
		}
		return members;
	}
}
