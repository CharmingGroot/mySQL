package jdbc_1.com.mc.jdbc.member.service;

import java.sql.Connection;

import jdbc_1.com.mc.jdbc.common.util.JDBCTemplate;
import jdbc_1.com.mc.jdbc.member.dao.MemberDao;
import jdbc_1.com.mc.jdbc.member.dto.Member;

// MVC2패턴
// Model
// Service

// 역할
// 비지니스 로직(기능 구현)을 구현
// DB transaction 관리
//		transaction : 논리적 최소 작업 단위.
//	    commit/rollback을 Service에서 결정

public class MemberService {
	
	private JDBCTemplate jdt = JDBCTemplate.getInstance();
	private MemberDao memberDao = new MemberDao();
	
	// 사용자인증에 대한 비지니스 로직
	// 사용자인증에 대한 트랜잭션을 시작하고 종료
	// Connection 객체의 생성과 종료를 담당, commit/rollback
	public Member userAuthenticate(String userId, String password) {
		
		Connection conn = jdt.getConnection();
		
		try {
			// DataAccessObject에게 사용자의 아이디와 password로 데이터를 조회할 것을 요청
			Member member = memberDao.userAuthenticate(conn, userId, password);
			return member;
			
		}finally {
			jdt.close(conn);
		}
		
	}

	
	
	
	
	
	
	
	
	
	
}
