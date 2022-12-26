package jdbc_1.com.mc.jdbc.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc_1.com.mc.jdbc.common.util.JDBCTemplate;
import jdbc_1.com.mc.jdbc.member.dto.Member;

// MVC2 패턴
// Model
// DAO (Data Access Object)

// Persistence Layer 
// 영속성 계층 : 데이터를 영구적으로 저장하기 위해 DB와 상호작용하는 Layer

// 필요한 데이터를 DBMS에 요청
// DBMS로 부터 읽어온 데이터를 어플리케이션 내에서 사용하기 적합한 형태로 파싱
public class MemberDao {
	
	private JDBCTemplate jdt = JDBCTemplate.getInstance(); // Connection에서 가져온다.

	// 사용자 인증
	public Member userAuthenticate(Connection conn, String userId, String password) {

		Member member = null;		// 
		// query를 변수에 저장한다.
		String query = "select * from member where user_id = '" + userId + "' and password = '" + password + "'";
		
		try(Statement stmt = conn.createStatement()) { // createStatement데이터에 query문을 보내기위한 객체를 생성하는 메서드
			try(ResultSet rset = stmt.executeQuery(query)){ // 매개변수로 주어진 query문을 실행하여 단일 결과집합을 반환
				
				while (rset.next()) { // next() : boolean type, 커서를 현재 위치에서 한 행 앞으로 이동하는 메서드.
					member = new Member(); // 멤버객체 생성자 호출, Member클래스에서 각 데이터 의 값변경 가능
					member.setUserId(rset.getString("user_id"));	// member.userId를 rset에 저장된 user_id열의 값으로 변경한다.
					member.setPassword(rset.getString("password"));
					member.setGrade(rset.getString("grade"));
					member.setTell(rset.getString("tell"));
					member.setEmail(rset.getString("email"));
					member.setLeave(rset.getBoolean("is_leave"));
					member.setRegDate(rset.getTimestamp("reg_date").toLocalDateTime());
					member.setRentableDate(rset.getTimestamp("rentable_date").toLocalDateTime());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return member; // while 문에서 초기화한 member를 리턴한다.
	}

	public int insertMember(Member member) {
		// 쿼리문 작성하여 변수에 저장
		String sql = "insert into member(user_id, password, email, grade, tell, rentable_date)"
				+ " values('"+ member.getUserId() +"',"
					+ "'" + member.getPassword() + "',"
					+ "'" + member.getEmail() + "',"
					+ "'" + member.getGrade() + "',"
					+ "'" + member.getTell() + "',"
					+ " now() "	
					+ ")";
		
		int res = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = jdt.getConnection(); // mySQL로 연결하기 위해 구현해둔 메서드
			stmt = conn.createStatement(); // 쿼리를 담아둘 바구니 객체 생성
			res = stmt.executeUpdate(sql); // 바구니 객체에 쿼리를 담아 쿼리를 DB로 릴리즈
			
			jdt.commit(conn);	// 오토커밋은 꺼두었으므로 커밋.
		} catch (SQLException e) {
			e.printStackTrace();
			jdt.rollback(conn); // 예외발생 시 이전 커밋지점으로 회귀
		} finally {
			jdt.close(stmt); // 사용했던 리소스 객체의 close메서드를 호출하여 안전하게 리소스를 닫아준다.
			jdt.close(conn);
		}
		
		return res; // 실행결과인 res를 반환
	}
	
	//userId의 비밀번호를
	//매개변수로 받아온 password로 수정하는 코드를 작성하시오. 
	public int changePassword(String userId, String password) {
		
		Connection conn = null; // null로 초기화한 이유를 잘 모르겠다.
		PreparedStatement pstm = null;
		int res = 0;

		String sql = "update member set password = ? where user_id = ?" ;
		
		try {
			
			conn = jdt.getConnection(); // mySQL과 연결시 필요한 데이터, 로컬서버, mysql id, pw를 담은 메서드를 호출
			pstm = conn.prepareStatement(sql);	// DB로 매개변수가 있는 query를 보내기 위한 객체 생성
			pstm.setString(1, password); // 지정된 index를 매개변수로 변경한다.
			pstm.setString(2, userId);	// 지정된 index를 매개변수로 변경한다.
			res = pstm.executeUpdate(); 
			
			jdt.commit(conn);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			jdt.rollback(conn);
			
		} finally {
			jdt.close(pstm);
			jdt.close(conn);
		}
		
		return res;
	}
	
	
	// 원하는 사용자의 아이디를 삭제하는 메서드
	public int deleteUser(String userId) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		int res = 0;
		String sql = "delete from member where user_id = ?" ;
		
		try {
			
			conn = jdt.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			res = pstm.executeUpdate();
			
			jdt.commit(conn);
			
		}  catch (SQLException e) {
			
			e.printStackTrace();
			jdt.rollback(conn);
			
		} finally {
			jdt.close(pstm);
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
