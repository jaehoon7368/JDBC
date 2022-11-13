package com.sh.member.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sh.member.model.dto.Member;

/**
 *  DAO
 *   - Datebase
 *   - Access
 *   - Object
 *
 */
public class MemberDao {

	String driverClass = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "student";
	String password = "student";
	
	/**
	 * DML
	 */
	public int insertMember(Member member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into member values(?,?,?,?,?,default,default)";
		
		try {
			// 1. 클래스 등록
			Class.forName(driverClass);
			// 2. Connection 생성(autocommit - false설정)
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			// 3. PreparedStatement 생성 - 미완성쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBrithday());
			pstmt.setString(5, member.getEmail());
			
			
			// 4. 실행 - int 반환
			result = pstmt.executeUpdate();
			// 5. 트랜잭션처리
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			// 6. 자원반납
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * DQL
	 * 
	 * 여러건 조회하는 경우
	 * - n행이 조회된 경우, ArrayList에 요소추가 후 반환
	 * - 0행이 조횔된 경우, 빈 ArrayList 반환
	 * @return
	 */
	public List<Member> findAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by reg_date desc";
		List<Member> members = new ArrayList<>(); //0행이 리턴되어도 빈 list객체를 반환
		
		try {
			//1. 클래스 등록
			Class.forName(driverClass);
			//2. Connection 생성
			conn = DriverManager.getConnection(url, user, password);
			//3. PreparedStatement 생성 - 미완성쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			//4. 실행 - ResultSet 반환
			rset = pstmt.executeQuery();
			//5. ResultSet -> List<Member>
			while(rset.next()) {
				String id = rset.getString("id"); // 컬럼명 대소문자 구분안함.
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				int point = rset.getInt("point");
				Timestamp regDate = rset.getTimestamp("reg_date");
				Member member = new Member(id,name,gender,birthday,email,point,regDate);
				members.add(member);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			//6. 자원반납
			try {
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return members;
	}

	/**
	 * 한건 조회인 경우
	 * -1행 조회되면, member객체 반환
	 * -0행 조회되면 ,null반환
	 * @param id
	 * @return
	 */
	public Member findById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where id = ?";
		Member member = null;
		
		try {
			//1. 클래스 등록
			Class.forName(driverClass);
			//2. Connection생성
			conn = DriverManager.getConnection(url, user, password);
			//3. PreparedStatement 생성 - 미완성쿼리 / 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); //select * from member where id = 'honggd'
			//4. 실행 - ResultSet 반환
			rset = pstmt.executeQuery();
			//5. ResultSet -> Member
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
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally
		{
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//6. 자원반납
		return member;
	}

	public Member findByName(String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where name like '%' || ? || '%'";
		Member member = null;
		
		try {
			//1. 클래스 등록
			Class.forName(driverClass);
			//2. Connection생성
			conn = DriverManager.getConnection(url, user, password);
			//3. PreparedStatement 생성 - 미완성쿼리 / 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,name);
			//4. 실행 - ResultSet 반환
			rset = pstmt.executeQuery();
			//5. ResultSet -> Member
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
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally
		{
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//6. 자원반납
		return member;
	}

	/**
	 * 회원삭제
	 */
	public int deleteMember(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from member where id = ?";
		
		try {
			// 1. 클래스 등록
			Class.forName(driverClass);
			// 2. Connection 생성(autocommit - false설정)
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			// 3. PreparedStatement 생성 - 미완성쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			// 4. 실행 - int 반환
			result = pstmt.executeUpdate();
			// 5. 트랜잭션처리
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			// 6. 자원반납
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 회원 이름 수정
	 * @param name2 
	 */
	public int updateName(String id, String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update member set name = ? where id = ?";
		
		try {
			// 1. 클래스 등록
			Class.forName(driverClass);
			// 2. Connection 생성(autocommit - false설정)
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			// 3. PreparedStatement 생성 - 미완성쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,name);
			pstmt.setString(2,id);
			
			// 4. 실행 - int 반환
			result = pstmt.executeUpdate();
			// 5. 트랜잭션처리
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			// 6. 자원반납
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 회원 생일 수정
	 */
	public int updateBirthday(String id, Date birthday) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update member set birthday = ? where id = ?";
		
		try {
			// 1. 클래스 등록
			Class.forName(driverClass);
			// 2. Connection 생성(autocommit - false설정)
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			// 3. PreparedStatement 생성 - 미완성쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1,birthday);
			pstmt.setString(2,id);
			
			// 4. 실행 - int 반환
			result = pstmt.executeUpdate();
			// 5. 트랜잭션처리
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			// 6. 자원반납
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int updateEmail(String id, String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update member set email = ? where id = ?";
		
		try {
			// 1. 클래스 등록
			Class.forName(driverClass);
			// 2. Connection 생성(autocommit - false설정)
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			// 3. PreparedStatement 생성 - 미완성쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,email);
			pstmt.setString(2,id);
			
			// 4. 실행 - int 반환
			result = pstmt.executeUpdate();
			// 5. 트랜잭션처리
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			// 6. 자원반납
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
