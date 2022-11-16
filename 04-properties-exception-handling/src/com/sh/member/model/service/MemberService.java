package com.sh.member.model.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static com.sh.member.common.JDBCTemplate.*;
import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.dto.Member;

public class MemberService {
	private MemberDao memberDao = new MemberDao();

	
	public List<Member> findAll() {
		Connection conn = getConnection();
		List<Member> members = memberDao.findAll(conn);
		close(conn);
		return members;
	}


	public int insertMember(Member member) {
		int result = 0;
		Connection conn = getConnection();
		try{
			result = memberDao.insertMember(conn,member);
			commit(conn);
		}catch(Exception e) {
			rollback(conn);
		}finally {
			close(conn);
		}
		return result;
	}


	public int deleteMember(String id) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.deleteMember(conn,id);
			commit(conn);
		}catch(Exception e) {
			rollback(conn);
		}finally {
			close(conn);
		}
		return result;
	}


	public Member findById(String id) {
		Connection conn = getConnection();
		Member member = memberDao.findById(conn,id);
		close(conn);
		return member;
	}


	public List<Member> findByName(String name) {
		Connection conn = getConnection();
		List<Member> members = memberDao.findByName(conn,name);
		close(conn);
		return members;
	}


	public int updateName(String id, String name) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.updateName(conn,id,name);
			commit(conn);
		}catch(Exception e) {
			rollback(conn);
		}finally {
			close(conn);
		}
		return result;
	}


	public int updateBirthday(String id, Date birthday) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.updateBirthday(conn,id,birthday);
			commit(conn);
		}catch(Exception e) {
			rollback(conn);
		}finally {
			close(conn);
		}
		return result;
	}


	public int updateEmail(String id, String email) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.updateEmail(conn,id,email);
			commit(conn);
		}catch(Exception e) {
			rollback(conn);
		}finally {
			close(conn);
		}
		return result;
	}


	public List<Member> findDelAll() {
		Connection conn = getConnection();
		List<Member> members = memberDao.findDelAll(conn);
		close(conn);
		return members;
	}
}
