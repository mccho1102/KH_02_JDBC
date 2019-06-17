package com.kh.testJDBC2.model.dao;

import static com.kh.testJDBC2.common.JDBCTemplate.close;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.testJDBC2.model.vo.Member;

public class MemberDao {

	// 1_10. MemberDao 기본 생성자 작성방법
	// Dao클래스에서는 SQL 구문을 실행하는데
	// 이전 프로젝트에서는 Dao코드에 SQL문을 작성하였지만
	// SQL구문에 대한 수정이 필요한 경우 코드를 다시 수정하고 컴파일 해야함
	// --> 유지보수가 불편

	// 이를 해결하기 위하여 SQL구문도 별도의 .properties 파일을 만들어 관하여
	// 동적으로 SQL구문을 읽어오게 할 것인데
	// Service클래스에서 Dao를 필드로 선언해두면
	// 프로그램 실행 시 단 하나의 Dao만 생성되어
	// SQL구문이 작성된 .properties 파일도 한번만 읽어오게 됨.
	
	// 만일 프로그램 수행 중 SQL구문의 수정이 필요한 경우
	// SQL구문이 작성된 .properties 파일을 수정하면 될 것 같지만
	// 프로그램은 이미 한번 파일을 읽었기 때문에 프로그램에 수정된 SQL이 반영되지 않음
	// --> 이를 해결하기 위하여 Dao의 생성자에 .properties를 읽어드리는 기능을 작성하고
	// Service에서 Dao객체를 지속적으로 새로 생성하여
	// 수정된 내용을 읽어들이고 이전에 읽어들인 Propertis객체를 갱신하는 동작을 만듦!

	// 1_11. 기본 생성자 작성 전 먼저 SQL 구문을 읽어들일
	// Properties 참조변수 선언
	private Properties prop = null;
	
	// 1_12. 기본 생성자 내부에
	// .properties 파일을 읽어와 prop 참조변수에 저장하는 구문 작성
	public MemberDao() {
		try {
			prop = new Properties();

			prop.load(new FileReader("query.properties"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 1_6. 새로운 회원 정보 추가
	// public int insertMember(Member mem) {

	// 1_14. 매개변수에 Connection conn 추가하기
	public int insertMember(Connection conn, Member mem) {

		/*
		 * 이전 testJDBC1 프로젝트에서 Dao가 맡은 업무 1) JDBC 드라이버 등록 2) DB 연결 Connection 객체 생성 3)
		 * SQL 수행 4) 처리 결과에 따른 트랜잭션(commit, rollback) 5) DB 자원 반환
		 * 
		 * 실제 Dao가 처리해야하는 업무는 SQL문을 DB로 전달하고 실행하여 반환값을 얻어오는 3) 번 역할만을 수행해야함.
		 * 
		 * --> 3)번 이외의 업무를 별도의 클래스로 분리
		 * 
		 * + 1,2,4,5번의 업무는 어디서든 공통적으로 이루어지는 공통 업무 --> 한번에 묶어서 작성 -->
		 * com.kh.testJDBC2.common.JDBCTemplate 클래스에 작성
		 */

		/*
		 * JDBCTemplate 클래스는 DB 연결과 관련되 공통 업무를 모아둔 클래스 내부 메소드를 호출하여 실제 DB 연결작업을 진행해야 함.
		 * 
		 * Dao 연결 작업, 자원 반환 등의 작업을 진행하는 클래스가 아님! SQL을 수행하고 그 결과만을 반환하는 클래스다.
		 * 
		 * DB연결과 관련된 작업은 별도 클래스인 Service에서 수행 --> MemberService 클래스 작성
		 */

		// 1_15. SQL을 DB에 전달하고 결과를 반환받을
		// PreparedStatement와 DB 처리 결과를 저장할 변수 선언
		PreparedStatement pstmt = null;
		int result = 0;
		
		// 1_16. SQL 구문 작성
		// --> query.properties 파일에서 SQL구문 얻어오기
		String query = prop.getProperty("insertMember");

		// 1_17. 전달받은 커넥션과, query 구문을 DB로 전달할 준비
		try {

			pstmt = conn.prepareStatement(query);

			// 1_18. 각 홀더에 알맞은 값 대입
			pstmt.setString(1, mem.getMemberId());
			pstmt.setString(2, mem.getMemberPwd());
			pstmt.setString(3, mem.getMemberName());

			// getChar() 메소드가 없음
			// -> getGender()뒤에 빈문자열을 추가하여 String으로 변환
			pstmt.setString(4, mem.getGender() + "");
			pstmt.setString(5, mem.getEmail());
			pstmt.setString(6, mem.getPhone());
			pstmt.setString(7, mem.getAddress());
			pstmt.setInt(8, mem.getAge());

			// 1_19. 쿼리 실행 후 결과를 반환 받아옴
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 1_20. SQL 수행에 사용된 자원 반환
			close(pstmt);
		}

		// 1_21. insert 결과 반환
		return result;

	}

	// 2_6. DB에서 모든 회원 정보 조회
	public List<Member> selectAll(Connection conn) {

		// 2_7. SQL문을 DB에 전달하고 결과를 반환받아
		// List에 담아 변환하는 변수 선언
		Statement stmt = null;
		ResultSet rset = null;
		List<Member> mList = null;

		// 2_8 query.properties에서 SQL 구문 작성 후 얻어오기
		String query = prop.getProperty("selectAll");

		// 2_9. 전달받은 커넥션과 쿼리문을 DB로 전달 준비
		try {
			stmt = conn.createStatement();

			// 2_10. 쿼리 실행 후 반환받은 결과를 rset에 저장
			rset = stmt.executeQuery(query);

			// 2_11. 조회 결과를 저장할 ArrayList 생성
			mList = new ArrayList<Member>();

			// 2_12. 조회 결과의 한 행의 값을 임시 저장할 참조변수 선언
			Member mem = null;

			// 2_13. rset에 저장된 회원정보를 한 행씩 읽어 mList에 추가
			while (rset.next()) {
				String memberId = rset.getString("MEMBER_ID");
				String memberPwd = rset.getString("MEMBER_PWD");
				String memberName = rset.getString("MEMBER_NAME");
				
				// 컬럼 값을 읽어오는 메소드 중 getChar()는 없음
				// --> getString().charAt()을 이용하여 char형으로 추출
				char gender = rset.getString("GENDER").charAt(0);
				String email = rset.getString("EMAIL");
				String phone = rset.getString("PHONE");
				int age = rset.getInt("AGE");
				String address = rset.getString("ADDRESS");
				Date enrollDate = rset.getDate("ENROLL_DATE");
				
				// 조회된 행의 정보를 Memeber객체를 생성하여 저장
				mem = new Member(memberId, memberPwd, memberName, 
						gender, email, phone, age, address, enrollDate);
				
				// mList에 추가
				mList.add(mem);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		// 2_15. 조회 결과를 저장한 mList 반환
		return mList;

	}
	
	
	// 3_11. 입력받은 아이디가 포함된 모든 회원 정보 조회
	public List<Member> selectMembers(Connection conn, String Id){
		
		// 3_12. SQL을 DB에 전달하고 결과를 반환받아 저장할 변수선언
		Statement stmt = null;
		ResultSet rset = null;
		List<Member> mList = null;
		
		// 3_13. query.properties에 SQL 구문 작성 후 얻어오기
		String query = prop.getProperty("selectMemberId");
		
		// 3_14. 미완성된 query 완성
		// '% (id) %'
		query += "'%" + Id + "%'";
	
		// 3_15. 조회 결과를 저장할 ArrayList,
		// 한 행을 임시 저장할 참조변수 선언
		mList = new ArrayList<Member>();
		Member mem = null;
		
		// 3_16. 전달받은 커넥션 객체와, query 구문 DB 전달 준비
		try {
		stmt = conn.createStatement();

		// 3_17. 쿼리 실행 후 반환받은 값을 rset에 저장
		rset = stmt.executeQuery(query);
		
		
		// 2_13. rset에 저장된 회원정보를 한 행씩 읽어 mList에 추가
					while (rset.next()) {
						String memberId = rset.getString("MEMBER_ID");
						String memberPwd = rset.getString("MEMBER_PWD");
						String memberName = rset.getString("MEMBER_NAME");

						// 컬럼 값을 읽어오는 메소드 중 getChar()는 없음
						// --> getString().charAt()을 이용하여 char형으로 추출
						char gender = rset.getString("GENDER").charAt(0);
						String email = rset.getString("EMAIL");
						String phone = rset.getString("PHONE");
						int age = rset.getInt("AGE");
						String address = rset.getString("ADDRESS");
						Date enrollDate = rset.getDate("ENROLL_DATE");

						// 조회된 행의 정보를 Memeber객체를 생성하여 저장
						mem = new Member(memberId, memberPwd, memberName, 
								gender, email, phone, age, address, enrollDate);
						
						// mList에 추가
						mList.add(mem);
					}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 3_19. 사용 자원 반화
			close(rset);
			close(stmt);
		}
		
		// 3_20. 조회된 결과를 저장한 mList 반환
		return mList;
		}
		public ArrayList<Member> selectMemberGender(Connection conn,char gen) {
			Statement stmt = null;
			ResultSet rset = null;
			Member mem = null;
			ArrayList<Member> mList = new ArrayList<Member>();
			String query = prop.getProperty("selectMemberGender");
			query += "'"+gen+"'";
			try {
				stmt = conn.createStatement();
				rset = stmt.executeQuery(query);
				
				while(rset.next()) {
					String memberId = rset.getString("MEMBER_ID");
					String memberPwd = rset.getString("MEMBER_PWD");
					String memberName = rset.getString("MEMBER_NAME");
					char gender = rset.getString("GENDER").charAt(0);
					String email = rset.getString("EMAIL");
					String phone = rset.getString("PHONE");
					String address = rset.getString("ADDRESS");
					int age = rset.getInt("AGE");
					Date enrollDate = rset.getDate("ENROLL_DATE");
					
					mem = new Member(memberId, memberPwd, memberName, 
							gender, email, phone, age, address, enrollDate);
					
					mList.add(mem);
					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rset);
				close(stmt);
			}
			
			return mList;
		}
		
		// 4_6. 아이디 존재 여부 확인 Dao
		public int checkMember(Connection conn, String memberId) {
			
			// 4_7. SQL을 DB 전달 및 반환받을 객체 선언
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			int check = 0;
			
			// 4_8. query.properties에 SQL구문 작성 후 얻어오기
			String query = prop.getProperty("checkMember");
			
			// 4_9. 쿼리 DB 전달 준비
			try {
			pstmt = conn.prepareStatement(query);
			
			// 4_10. 홀더에 알맞는 값 대입
			pstmt.setString(1, memberId);
			
			// 4_11. 쿼리 실행 후 결과를 rset에 저장
			rset = pstmt.executeQuery();
			
			// 4_12. 그룹함수로 조회하였기 때문에
			// 결과 행이 1개! -> if문으로 rset.next() 진행
			if(rset.next()) {
				check = rset.getInt(1);
			}
			}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 4_13. 사용 자원 반환
			close(rset);
			close(pstmt);
		}
			
			// 4_14. 조회 결과 반환
			return check;
	}
		
		
		
		// 4_30. 회원 정보 수정 Dao
		public int updateMember(Connection conn, int sel,
							String memberId, String input) {
		
			// 4_31. SQL DB 전달 및 결과 반환받을 변수 선언
			PreparedStatement pstmt = null;
			int result = 0;
			
			// 4_32. query.properties에 SQL 구문 작성 후 얻어오기
			String query = prop.getProperty("updateMember"+sel);
			
			// select * from member where ? = ?
			// pstmt.setString(1, "MEMBER_ID");
			// pstmt.setString(2, "user01");
			// 1번째 홀더 -> 'MEMBER_ID'
			
			// select * from 'MEMBER_ID' = 'user01';
			// * 컬럼명 자리를 홀더(?)로 지정하여
			// setString()으로 컬럼명을 지정 할 수 없다.
			// 왜냐하면 컬럼명 자리에 ''(리터럴)형태로 들어감.
			// --> 존재하지 않는 컬럼명
			
			// 4_33. 쿼리 DB 전달 준비
			try {
			pstmt = conn.prepareStatement(query);
			
			// 4_34. 홀더에 알맞는 값 대입
			pstmt.setString(1, input);
			pstmt.setString(2, memberId);
			
			// 4_35. 쿼리 실행 후 결과를 반환받아옴.
			result = pstmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
			// 4_36. 사용 자원 반환
				close(pstmt);
			}
			return result;
		}
		
		// 5_12. 회원 정보 삭제 Dao
		public int deleteMember(Connection conn, String memberId) {
			
			// 5_13. SQL 전달/반환받고,  결과 저장할 변수 선언
			PreparedStatement pstmt = null;
			int result = 0;
			
			// 5_14. query.properties에 쿼리 작성 후 얻어옴
			String query = prop.getProperty("deleteMember");
			
			// 5_15. DB에 쿼리 전송 후 수행 결과를 저장함
			try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, memberId);
			
			result = pstmt.executeUpdate();
			
			}catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt); // 사용 자원 반환
			}
			
			// 5_16. 삭제 결과 반환
			return result;
		
		}
}	



























