package jdbc_1.com.mc.jdbc.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTemplate {
	// singleton pattern 임으로 프로그램내에서 한번만 호출되는 생성자
	private static final JDBCTemplate INSTANCE = new JDBCTemplate();		// private로 제한하여 외부의 new연산자를 이용한 접근을 막는다.
	
	private JDBCTemplate() {
		try {
			// JVM에 com.mysql.cj.jdbc.Driver 클래스의 정보를 올리는 코드.
			// 클래스 정보 데이터는 static영역에 올라가기 때문에, 한번만 드라이버를 등록하면 프로그램 종료 때 까지 메모리에 내려오지 않는다.
			Class.forName("com.mysql.cj.jdbc.Driver"); // 클래스 명으로 긁어온다.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static JDBCTemplate getInstance() { // 외부에 제공할 인스턴스
		return INSTANCE;
	}
	
	
	public Connection getConnection() {	// sql과 연결하기위한 Connection 인터페이스
		
		String url = "jdbc:mysql://localhost:3306/bookmanager?useUnicode=true&characterEncoding=utf8";
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url, "bm", "123qwe!@#QWE");
			// 개발자가 트랜잭션관리를 직접할 수 있도록 auto commit을 종료
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void commit(Connection conn) { // commit 메서드 구현
		
		try {
			conn.commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void rollback(Connection conn) { // 롤백 메서드 구현, 예외발생 시 이전 커밋지점으로 돌아간다.
		
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void close(ResultSet rset) { // close 메서드 구현
		
		try {
			
			if(rset != null && !rset.isClosed()) rset.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void close(Statement stmt) { // stms를 닫는 close메서드 구현
		
		try {
			
			if(stmt != null && !stmt.isClosed()) stmt.close(); // stmt가 null이 아니면서 stmt가 닫혀있지 않을 때
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection conn) { // conn을 닫는 close 메서드 구현
		
		try {
			
			if(conn != null && !conn.isClosed()) conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void close(ResultSet rset, Statement stmt) { // 
		close(rset);
		close(stmt);
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
