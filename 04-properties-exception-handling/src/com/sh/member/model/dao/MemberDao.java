package com.sh.member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.member.model.dto.Member;
import com.sh.member.model.exception.MemberException;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	

	public MemberDao() {
		try {
			prop.load(new FileReader("resources/member-sql.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Member> findAll(Connection conn) {
		String sql = prop.getProperty("findAll");
		List<Member> members = new ArrayList<>();
		
		//1.PreparedStatement 생성 - 미완성쿼리 & 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			//2. 실행 - ResultSet
			try(ResultSet rset = pstmt.executeQuery()){
				//3. ResultSet -> List<Member>
				while(rset.next()) {
					Member member = handleMemberResultSet(rset);
					members.add(member);
				}
			}
		}catch(Exception e) {
			throw new MemberException("회원 전체조회 오류!",e);
		}
		return members;
	}

	private Member handleMemberResultSet(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setId(rset.getString("id"));
		member.setName(rset.getString("name"));
		member.setGender(rset.getString("gender"));
		member.setBrithday(rset.getDate("birthday"));
		member.setEmail(rset.getString("email"));
		member.setPoint(rset.getInt("point"));
		member.setRegDate(rset.getTimestamp("reg_date"));
		return member;
	}

	public int insertMember(Connection conn, Member member) {
		String sql = prop.getProperty("insertMember");
		int result = 0;
		
		//1. PreparedStatement - 미완성쿼리 & 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBrithday());
			pstmt.setString(5, member.getEmail());
			
			//2. 실행 - int
			result = pstmt.executeUpdate();
		}
		catch (Exception e) {
			throw new MemberException("회원가입오류!",e);
		}
		return result;
	}

	public int deleteMember(Connection conn, String id) {
		String sql = prop.getProperty("deleteMember");
		int result = 0;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			throw new MemberException("회원탈퇴오류!",e);
		}
		return result;
	}

	public Member findById(Connection conn, String id) {
		String sql = prop.getProperty("findById");
		Member member = null;
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					member = handleMemberResultSet(rset);
				}
			}
			
		} catch(Exception e) {
			throw new MemberException("회원 아이디조회 오류!",e);
	}
		return member;
	}

	public List<Member> findByName(Connection conn, String name) {
		String sql = prop.getProperty("findByName");
		List<Member> members = new ArrayList<>();
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, "%" + name + "%");
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					Member member = handleMemberResultSet(rset);
					members.add(member);
				}
			}
		}catch(Exception e) {
			throw new MemberException("회원 이름조회 오류!",e);
		}
		return members;
	}

	public int updateName(Connection conn, String id, String name) {
		int result = 0;
		String sql = prop.getProperty("updateName");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			throw new MemberException("회원 이름수정 오류!",e);
		}
		return result;
	}

	public int updateBirthday(Connection conn, String id, Date birthday) {
		int result = 0;
		String sql = prop.getProperty("updateBirthday");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setDate(1, birthday);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			throw new MemberException("회원 생일수정 오류!",e);
		}
		return result;
	}

	public int updateEmail(Connection conn, String id, String email) {
		int result = 0;
		String sql = prop.getProperty("updateEmail");
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			throw new MemberException("회원 이메일수정 오류!",e);
		}
		return result;
	}

	public List<Member> findDelAll(Connection conn) {
		List<Member> members = new ArrayList<>();
		String sql = prop.getProperty("findDelAll");
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					Member member = handleMemberResultSet(rset);
					member.setDelDate(rset.getTimestamp("del_date"));
					members.add(member);
				}
			}
		}catch(Exception e) {
			throw new MemberException("삭제회원 조회 오류!",e);
		}
		return members;
	}

}

