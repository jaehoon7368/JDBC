package com.sh.member.model.dao;

import static com.sh.member.common.JDBCTemplate.close;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sh.member.model.dto.Member;

public class MemberDao {

	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = "insert into member values(?,?,?,?,?,default,default)";
		int result = 0;
		
		try {
			//1.PreparedStatement생성 - 미완성쿼리 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBrithday());
			pstmt.setString(5, member.getEmail());
			//2.실행
			result = pstmt.executeUpdate();
		} catch(Exception e) {
	        throw new RuntimeException (e) ; //checked -> unchecked
		}finally {
			//3.자원반납 (pstmt,rset)
			try {
				close(pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<Member> findByName(Connection conn, String name) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where name like ?";
		List<Member> members = new ArrayList<>();
		
		try {
			// 1. PerparedStatement 생성 - 미완성쿼리 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%");
			// 2. 실행 - ResultSet
			rset = pstmt.executeQuery();
			// 3. ResultSet -> List<Member>
			while(rset.next()) {
				members.add(new Member(
						rset.getString("id"),
						rset.getString("name"),
						rset.getString("gender"),
						rset.getDate("birthday"),
						rset.getString("email"),
						rset.getInt("point"),
						rset.getTimestamp("reg_date")));
						
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			// 4. 자원반납(pstmt,rset)
			close(rset);
			close(pstmt);
		}
		return members;
	}

	public Member findById(Connection conn, String id) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where id = ?";
		Member member = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				member = new Member();
				member.setId(rset.getString("ID"));
				member.setName(rset.getString("NAME"));
				member.setGender(rset.getString("GENDER"));
				member.setBrithday(rset.getDate("BIRTHDAY"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPoint(rset.getInt("POINT"));
				member.setRegDate(rset.getTimestamp("REG_DATE"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return member;
	}

	public List<Member> findAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member";
		List<Member> members = new ArrayList<>();
		
		try {
			pstmt = conn.prepareCall(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				members.add(new Member(
						rset.getString("id"),
						rset.getString("name"),
						rset.getString("gender"),
						rset.getDate("birthday"),
						rset.getString("email"),
						rset.getInt("point"),
						rset.getTimestamp("reg_date")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return members;
	}

	public int updateName(Connection conn, String id, String name) {
		PreparedStatement pstmt = null;
		String sql = "update member set name = ? where id = ?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
			
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
	
		
		return result;
	}

	public int updateBirthday(Connection conn, String id, Date birthday) {
		PreparedStatement pstmt = null;
		String sql = "update member set birthday = ? where id = ?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, birthday);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
			
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
	
		
		return result;
	}

	public int updateEmail(Connection conn, String id, String email) {
		PreparedStatement pstmt = null;
		String sql = "update member set email = ? where id = ?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
			
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
	
		
		return result;
	}

	public int deleteMember(Connection conn, String id) {
		PreparedStatement pstmt = null;
		String sql = "delete from member where id = ?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
	
		
		return result;
	}

	public List<Member> findDelAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member_del";
		List<Member> members = new ArrayList<>();
		
		try {
			pstmt = conn.prepareCall(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				members.add(new Member(
						rset.getString("id"),
						rset.getString("name"),
						rset.getString("gender"),
						rset.getDate("birthday"),
						rset.getString("email"),
						rset.getInt("point"),
						rset.getTimestamp("reg_date"),
						rset.getTimestamp("del_date")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return members;
	}

	

}
