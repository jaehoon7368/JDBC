package com.sh.member.model.service;

import static com.sh.member.common.JDBCTemplate.*;
import java.sql.*;
import java.util.List;

import com.sh.member.common.JDBCTemplate;
import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.dto.Member;

/**
 * DAO
 * 1. 클래스 등록
 * 2. Connection 생성
 * 3. PreparedStatement 생성 - 미완성쿼리 & 값대입
 * 4. 실행
 * 5. 트랜잭션처리
 * 6. 자원반납 (conn,pstmt)
 * 
 * Service
 * 1. 클래스등록
 * 2. Connection 생성
 * 3. Dao 위임
 * 4. 트랜잭션처리
 * 5. 자원반납 (conn)
 * 
 * Dao
 * 1.PreparedStatement생성 - 미완성쿼리 & 값대입
 * 2.실행
 * 3.자원반납 (pstmt,rset)
 */
public class MemberService {
	
	private MemberDao  memberDao = new MemberDao();
	
	String driverClass = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "student";
	String password = "student";
	
	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result  = 0;
		try{
			result = memberDao.insertMember(conn,member);
			commit(conn);
		}catch(Exception e){
			rollback(conn);
			e.printStackTrace();
		}finally {
			close(conn);
			
		}
		
		return result;
	}

	/**
	 * 1. Connection 생성
	 * 2. PreparedStatement 생성 - 미완성쿼리 & 값대입
	 * 3. 실행 - ResultSet
	 * 4. Result -> List<Member>
	 * 5. 자원반납
	 * 
	 * service
	 * 1. Connection생성
	 * 2. Dao 위임
	 * 3. 자원반납 (conn)
	 * 
	 * dao
	 * 1. PerparedStatement 생성 - 미완성쿼리 & 값대입
	 * 2. 실행 - ResultSet
	 * 3. ResultSet -> List<Member>
	 * 4. 자원반납(pstmt,rset)
	 */
	public List<Member> findByName(String name) {
		Connection conn = getConnection();
		List<Member> members = memberDao.findByName(conn,name);
		close(conn);
		return members;
	}

	public Member findById(String id) {
		Connection conn = getConnection();
		Member member = memberDao.findById(conn,id);
		close(conn);
		return member;
	}

	public List<Member> findAll() {
		Connection conn = getConnection();
		List<Member> members = memberDao.findAll(conn);
		close(conn);
		return members;
	}

	public int updateName(String id, String name) {
		Connection conn = getConnection();
		int result = memberDao.updateName(conn,id,name);
		close(conn);
		return result;
	}

	public int updateBirthday(String id, Date birthday) {
		Connection conn = getConnection();
		int result = memberDao.updateBirthday(conn,id,birthday);
		close(conn);
		return result;
	}

	public int updateEmail(String id, String email) {
		Connection conn = getConnection();
		int result = memberDao.updateEmail(conn,id,email);
		close(conn);
		return result;
	}

	public int deleteMember(String id) {
		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn,id);
		close(conn);
		return result;
	}

	public List<Member> findDelAll() {
		Connection conn = getConnection();
		List<Member> members = memberDao.findDelAll(conn);
		close(conn);
		return members;
	}

}
