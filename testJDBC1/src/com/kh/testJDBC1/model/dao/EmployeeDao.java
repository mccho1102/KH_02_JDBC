package com.kh.testJDBC1.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.testJDBC1.model.vo.Employee;

public class EmployeeDao {

	public EmployeeDao() {
	} // 기본 생 성 자
	
	// 1_3. 사원 정보 전체 조회
	public ArrayList<Employee> selectAll() {

		// JDBC 객체 선언(java.sql)
		Connection conn = null;
		// - DB의 연결 정보를 담은 객체
		// - JDBC 드라이버와 DB 사이를 연결해주는 일종의 통로
		// - 직접 객체 생성 불가 -> DriverManager.getConnection() 이용하여 생성

		Statement stmt = null;
		// - Connection 객체를 통해 DB에 SQL문을 전달하여 실행시키고
		// SQL 실행 결과를 반환받는 역할을 하는 객체
		// - Connection.createStatement() 이용하여 객체 생성

		ResultSet rset = null;
		// - SELECT문을 사용한 질의(쿼리) 성공 시 반환되는 객체
		// - SELECT 결과로 생성된 테이블을 담고 있으며
		// 커서(CURSOR)를 이용하여 특정 행에 대한 참조를 조작함.

		ArrayList<Employee> empList = null;
		// DB에서 조회한 결과를 저장할 ArrayList 생성

		// 1_4. 해당 DB에 대한 라이브러리(JDBC 드라이버) 등록 작업
		// -> JDBC 드라이버의 클래스 메모리에 로드
		// Class.forName("클래스명")를 이용하여 메모리에 로드
		// --> ClassNotFoundException 발생 가능성이 있음
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 1_5. DBMS 연결 작업
			/*
			 * 연결 정보를 담을 Connection 연결 처리를 위한 DriverManager
			 * 
			 * - jdbc:oracle:thin -> JDBC드라이버 thin 타입 의미 - @127.0.0.1 -> 접속하려는 오라클이 설치된 서버의
			 * ip 입력 127.0.0.1은 자신의 컴퓨터 ip를 의미 (@localhost 로 대체 가능) - 1521 : 오라클 Listener의
			 * 포트번호 - xe : 접속할 오라클 DB명 (Express -> xe) - scott : DB 접속 계정 - tiger : 접속 비밀번호
			 */

			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "scott", "tiger");

			// 1_6. DB 접속 성공 시 conn을 출력하면 DB 정보를 확인 가능
			// 실패 시 null, 에러 발생 시 SQLException 발생
			System.out.println(conn);

			// 1_8. DB에 쿼리문을 전달하고 실행시킨 후
			// 결과를 반환받아올 Statement 생성
			stmt = conn.createStatement();

			String query = "SELECT * FROM EMP";

			// 1_9. 쿼리문을 Statement 객체를 이용하여 실행 후
			// 반환된 결과를 Resultset rset 에 저장
			rset = stmt.executeQuery(query);
			// executeQuery() : DB로 SELECT문을 전달하여 실행하고 결과를 반환 받음
			// executeUpdate() : DML(INSERT, UPDATE, DELETE) 실행 시 사용

			// 1_10. rset에 있는 SELECT 결과를
			// ArrayList에 담아주기

			empList = new ArrayList<Employee>(); // 결과를 저장할 ArrayList 생성
			Employee emp = null; // 조회 결과의 한 행(row) 값을 저장할 임시변수 선언

			while (rset.next()) {
				// ResultSet.next() : 반환 받은 조회 결과(테이블)에
				// 커서(CURSOR)을 이용하여 한 행씩 접근함
				// 이 때, 행이 존재하면 true, 없으면 false 반환

				// get[Type]("컬럼명") : 해당 컬럼의 값을 가져옴.
				// [Type]은 가져올 값의 자료형

				int empNo = rset.getInt("EMPNO");
				String empName = rset.getString("ENAME");
				String job = rset.getString("JOB");
				int mgr = rset.getInt("MGR");
				Date hireDate = rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptNo = rset.getInt("DEPTNO");

				// 조회 결과를 매개변수로 하여 Employee 객체 생성
				emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);

				// 조회된 사원 정보를 empList에 추가
				empList.add(emp);
			}

			// 조회 결과를 모두 empList에 저장했다면
			// DB 연결에 사용되었던 모든 객체 반환

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 1_11. DB 연결 끊기
			// DB 연결에 사용된 객체는 사용 후 반드시 반환
			// 반환 순서 : 나중에 생성된 객체를 먼저 반환
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// 1_12. 조회된 사원 정보가 담겨진 empList 반환
		return empList;

	}

	// 2_6. 매개변수로 사원번호를 전달받아 직원 정보 조회
	public Employee selectEmployee(int empNo) {

		// 2_7. JDBC 드라이버 등록 및 DB연결, 조회결과를 저장할 객체 선언
		Connection conn = null;
		// Statement stmt = null;
		ResultSet rset = null;

		// 조회된 회원 정보를 저장할 Employee 선언
		Employee emp = null;

		// 2_19 Statement -> PreparedStatement 교체
		// stmt 관련 코드 모두 주석처리
		PreparedStatement pstmt = null;
		/*
		 * PreparedStatement - Statement와 같은 SQL문을 전달하여 실행하고 결과를 반환 받는 객체 - 차이점은 실행 시간동안
		 * 인수값을 위한 공간을 확보할 수 있음 - 각각의 인수에 대해 위치 홀더(?)를 사용하여 SQL문장을 정의할 수 있게함
		 * 
		 * - (?) 홀더 : SQL 문장에 나타나는 토큰으로 SQL 구문이 실행되기 전에 실제값으로 대체되어야 함.
		 * 
		 */

		// 2_8. JDBC 드라이버 등록 작업
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

			/*
			 * //
			 * -----------------------------------------------------------------------------
			 * --- // 2_9. Statement 객체를 이용하여 조회
			 * 
			 * // 해당 사번의 사원 정보 조회 쿼리문 작성 String query = "SELECT * FROM EMP WHERE EMPNO = " +
			 * empNo;
			 * 
			 * stmt = conn.createStatement(); rset = stmt.executeQuery(query); // 조회 결과 저장
			 * 
			 * //
			 * -----------------------------------------------------------------------------
			 * -----
			 */

			// ----------------------------------------------------------------------------------
			// 2_20. PreparedStatement 객체를 이용하여 조회
			String query = "SELECT * FROM EMP WHERE EMPNO = ?";

			pstmt = conn.prepareStatement(query);

			// 객체 생성 후 쿼리문을 완성시킴
			// 객체명.set[type](?순번, 적용할 값);
			pstmt.setInt(1, empNo);
			// 쿼리의 1번 홀더 자리에 empNo 값을 대입

			// 쿼리 완성 후 실행
			rset = pstmt.executeQuery();

			// ----------------------------------------------------------------------------------

			// 2_10. 조회된 결과가 있을 경우
			// Employee 객체를 생성하여 emp 참조변수에 저장.
			if (rset.next()) {
				// 조회된 결과가 한 행 이므로 if문으로 처리

				// empNo는 매개변수 재활용
				String empName = rset.getString("ENAME");
				String job = rset.getString("JOB");
				int mgr = rset.getInt("MGR");
				Date hireDate = rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptNo = rset.getInt("DEPTNO");

				emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 2_11. DB 연결 자원 반환
			try {
				rset.close();
				// stmt.close();

				// 2_21. pstmt 반환
				pstmt.close();

				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 2_12. 조회 정보를 저장한 emp 반환
		return emp; //
	}

	// 3_6. 새로운 사원 정보 추가
	public int inserrEmployee(Employee emp) {

		// 3_7. JDBC 드라이버 등록, DB연결, 삽입결과 저장용 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0; // 삽입 결과 저장용 변수

		// 3.8 JDBC 드라이버 등록 작업
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

			// 3_9. PreparedStatement 를 사용한 사원 정보 삽입을 위한
			// INSERT 구문 작성
			String query = "INSERT INTO EMP VALUES" + "(?, ?, ?, ?, SYSDATE, ?, ?, ?)";

			// 3_10. INSERT문 전달을 위한 PreparedStatement 객체 생성 후
			// 각 혿더(?) 자리에 알맞은 값 대입
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getJob());
			pstmt.setInt(4, emp.getMgr());
			pstmt.setInt(5, emp.getSal());
			pstmt.setInt(6, emp.getComm());
			pstmt.setInt(7, emp.getDeptNo());

			// 3_11. SQL문을 실행하고 결과를 받아옴
			// DML구문 (INSERT, UPDATE, DELETE)의 실행 결과는
			// 처리 성공한 행의 개수를 반환 (int형)
			// 처리 실패 시 0 반환(처리된 행이 없음)
			result = pstmt.executeUpdate();

			// 3_12. SQL 실행 결과에 따라 commit, rollback 지정
			if (result > 0) { // SQL 수행 성공
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 3_14. INSERT 수행 결과 반환
		return result;

	}

	// 4_8. 매개변수로 전달받은 회원정보 중
	// 사번이 일치하는 사원의 정보 수정
	public int updateEmployee(Employee emp) {

		// 4_9. JDBC 드라이버 등록 및 DB연결,
		// 수정 결과를 저장하기 위한 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		// 4_10. JDBC 드라이버 등록 작업
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("" + "jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

			// 4_11. PreparedStatement를 사용 한 사원 수정을 위한
			// SQL 구문 작성
			String query = "UPDATE EMP SET " + "JOB = ?, SAL = ?, COMM = ? " + "WHERE EMPNO = ?";

			// 4_12. SQL 전달을 위한 PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(query);

			// 4_13. 각 홀더(?)에 알맞은 값 대입
			pstmt.setString(1, emp.getJob());
			pstmt.setInt(2, emp.getSal());
			pstmt.setInt(3, emp.getComm());
			pstmt.setInt(4, emp.getEmpNo());

			// 4_14. SQL 실행 후 결과를 반환받음
			result = pstmt.executeUpdate();

			// 4_15. SQL 실행 결과에 따라 commit, rollback 지정
			if (result > 0) { // 수정에 성공한 경우
				conn.commit();
			} else {
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4_16. DB 연결 해재(자원 반환)
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 4_17. Update 결과 반환
		return result;
	}

	// 5_8. 전달받은 사번의 사원 정보 삭제
	public int deleteEmployee(int empNo) {

		// 5_9. JDBC 드라이버 등록 및 DB연결,
		// 수정 결과를 저장하기 위한 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		// 5_10. JDBC 드라이버 등록 작업
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 5_11. DB 연결 작업
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

			// 5_12. 사원 정보 삭제를 위한 SQL 구문 작성
			String query = "DELETE FROM EMP WHERE EMPNO = ?";

			// 5_13. Preparedstatement 객체 생성 후 홀더에 값 대입
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empNo);

			// 5_14. SQL 실행 결과를 받아옴
			result = pstmt.executeUpdate();

			// 5_15. SQL 실행 결과에 따라 commit, rollback 지정
			if (result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5_16. DB 연결 자원 반환
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 5_17. DELETE 결과 반환
		return result;
	}
}
