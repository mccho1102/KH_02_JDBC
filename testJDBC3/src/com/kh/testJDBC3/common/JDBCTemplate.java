package com.kh.testJDBC3.common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	/* 기존 DB 처리 작업(select, insert, update, delete 등) 시 마다
	 * 새로운 Connection 객체를 생성하여 DB와 연결을 진행함
	 * 이렇게 되면 한 사용자가 여러 개의 커넥션 객체를 생성하여
	 * DB와 접속을 하게 됨.
	 * 	--> 여러 객체 생성으로 인한 메모리 낭비
	 * 		+ DB 접속 가능한 커넥션 객체 수 가 제한되어 있음
	 * 
	 * 	이를 해결하기 위하여
	 * 	사용자당 생성할 수 있는 커넥션 객체를 한 개로 제한하여
	 * 	메모리 낭비 및 DB연결 객체수 오버를 방지하는 패턴을 적용
	 * 	메모리 낭비 및 DB 연결 객체수 오버를 방지하는 패턴을 적용
	 * 	--> 싱글통(SingleTone) 패턴
	 * 
	 * * 싱글톤 패턴
	 * - 프로그램 구동 시 메모리에 객체를 딱 한개만 기록되게 하는 디자인 패턴
	 * 
	 * - 모든 필드, 메소드를 static으로 선언하여
	 * 	 static 영역에 고용으로 사용할 수 있는
	 * 	  단 하나의 객체를 만듦.
	 * 
	 */
	
	// 한 개의 공용 커넥션 객체를 지정할 참조 변수 선언
	// 외부에서 직접 접근을 할 수 없도록 private 선언
	private static Connection conn = null;
	
	
	// DB 연결을 위한 커넥션 요청 메소드 작성
	public static Connection getConnection() {
		
		// 최초로 메소드 호출 시 커넥션 객체는 DB와 연결되어 있지 않음
		// --> DB와의 연결 작업 진행
		if(conn == null) {
			/*	이전 프로젝트에서는
			 * 	JDBC 드라이버 로드,
			 * 	DB 연결을 위한 정보(ur1, id, pwd)를 직접 코드에 작성함. (정적코딩)-- 수정하기 어려움
			 * 	--> 추후 DB 변경 또는 연결 정보 변경 되어지는 경우
			 * 		코드 자체를 수정해서 다시 컴파일을 해야 함
			 * 	 --> 유지보수 불편...
			 * 	
			 * 	이를 해결하기 위하여 Properties 파일을 만들어
			 * 	프로그램 실행 시 동적으로 DB 연결 정보를 불러오도록 코딩.
			 * 	(동적 코딩)
			 */
		
		try {
		// 외부에서 정보를 읽어와 저장할 Properties 객체 생성
		Properties prop = new Properties();
		
		// driver.properties 파일을 읽어와서 prop에 저장
		prop.load(new FileReader("driver.properties"));
		
		// driver.properties에서 JDBC 드라이버 등록 및
		// DB 연결 정보를 얻어와 DB와 연결하기
		Class.forName(prop.getProperty("driver"));
		conn = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("user"),
					prop.getProperty("password"));
		
		// 자동 commit 비활성화
		conn.setAutoCommit(false);
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
		
		return conn;
	}
	
	
	// 처리 결과에 따른 트랜잭션 처리 업무도 공통적으로 사용함
	// ==> static으로 미리 선언하여 코드 길이 감소, 재사용성 증가
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				// Connection.isClosed()
				// --> 현재 DB와 연결이 되어있지 않으면 true
				// conn이 참조하는 객체가 있고
				// 해당 객체가 DB와 연결된 커넥션 객체일 경우
				// --> DB와 정상 연결이 되어있는 경우
				
				conn.commit();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// DB 연결 자원 반환하는 close()도 공통적으로 사용됨
	// Connection, Statement, ResultSet, PreparedStatement
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// PreparedStatement는 Statement의 자식
	// -> 매개변수에 다형성이 적용되어 한 메소드로 
	//    두 경우 모두 반환 가능
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
