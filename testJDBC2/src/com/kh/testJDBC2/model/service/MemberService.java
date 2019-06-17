package com.kh.testJDBC2.model.service;

import static com.kh.testJDBC2.common.JDBCTemplate.commit;
import static com.kh.testJDBC2.common.JDBCTemplate.*;
import static com.kh.testJDBC2.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.kh.testJDBC2.model.dao.MemberDao;
import com.kh.testJDBC2.model.vo.Member;
// static 필드, 메소드 호출 시 클래스 명을 생략하고 호출 가능.

public class MemberService {

	// Service는 여러 Dao를 호출하여
	// 여러번의 데이터 접근/갱신을 진행하며
	// 그렇게 읽은 데이터에 대한 비즈니스 로직(가공)을 수행하고,
	// 그것을 하나의(혹은 여러개) 트랜잭션으로 묶어 처리.
	
	/* Service 클래스에서 메소드 작성하는 방법
	 * 1) Controller로 부터 매개변수를 전달받음
	 * 2) Connection 객체 생성
	 * 3) Dao 객체 생성
	 * 4) Dao메소드에 Connection 객체와, Controller 매개변수를 전달
	 * 5) Dao 수행 결과를 가지고 비즈니스 로직 및 트랜잭션 처리
	 * 
	 */
	
	
	// 1_7. 새로운 회원 정보를 추가하는 Service
	// 매개변수로 Controller로 부터 인자를 전달받음
	public int insertMember(Member mem) {
		
		// 1_8. Connection 객체 생성
		//Connection conn = JDBCTemplate.getConnection();
		// 지속적으로 JDBCTemplate의 멤버를 호출할껀데
		// 매번 JDBCTemplate 쓰긴 불편함
		// -> 상단 import단에 static import를 선언
		Connection conn = getConnection();
		
		// 1_9. Dao객체 생성
		MemberDao mDao = new MemberDao();
		// Service 클래스의 모든 메소드에서 MemberDao를 생성할 예정
		// 그럼 필드로 묶어 공용으로 사용하면 안되나? 싶죠? 안되요!
		// --> 이유는 MemberDao 생성자 작성하면서 설명
		
		
		// 1_13. 알맞은 Dao 메소드를 호출하여
		// 매개변수로 커넥션 객체와, 컨트롤러 인자값을 전달하고
		// DB 처리 결과를 저장
		int result = mDao.insertMember(conn, mem);
	
		// 1_22. DB 처리결과에 따라 트랜잭션 처리
		if(result > 0) { // 삽입 성공
			commit(conn);
		}else {
			rollback(conn);
		}
		
		// 1_23. DB 처리 결과를 컨트롤러로 반환
		return result;
	}
	
	
	// 2_2. 전체 회원 정보 조회 Service
	public List<Member> selectAll() {
		
		// 2_3. 커넥션 객체를 얻어옴.
		Connection conn = getConnection();
		
		// 2_4. Dao 객체 생성
		MemberDao mDao = new MemberDao();
		
		// 2_5. 커넥션 객체를 매개변수 전달받아 
		// 모든 회원 정보를 조회하고 반환할 메소드
		// MemeberDao.selectAll() 작성
		
		
		// 2_16. MemeberDao.selectAll() 호출 후 반환 값 저장
		List<Member> mList = mDao.selectAll(conn);
		
		// 2_17. 별도의 트랜잭션 처리가 필요하지 않으므로 
		// 바로 컨트롤러로 반환
		return mList;
	}
	
	
	// 3_18. 입력받은 아이디가 포함된 모든 회원 정보 조회용 Service
	public List<Member> selectMemberId(String Id) {
		
		// 3_9. 커넥션, Dao 객체 생성
		Connection conn = getConnection();
		MemberDao mDao = new MemberDao();
		
		// 3_10. 커넥션 객체와, 입력된 id를 전달받아
		// 회원정보를 조회하는 MemberDao.selectMemberId() 메소드 작성
		
	
		// 3_21. MemberDao.selectMemberId() 호출 후 반환값 저장
		List<Member> mList = mDao.selectMembers(conn, Id);
				
		// 3_22. 별도의 트랜잭션 처리 X, 바로 반환
		return mList;
	}
	public List<Member> selectMemberGender(char gender) {
		Connection conn = getConnection();
		MemberDao mDao = new MemberDao(); 
		ArrayList<Member> mList = mDao.selectMemberGender(conn,gender);
		return mList;
		
	}
	// 4_3. 아이디 존재 여부 확인 Service
	public int checkMember(String memberId) {
		
		// 4_4. 커넥션, Dao 생성
		Connection conn = getConnection();
		MemberDao mDao = new MemberDao();
		
		// 4_5. 커넥션 객체와 입력받은 memberId를
		// 매개변수로 아이디 존재 여부를 확인하는
		// MemberDao.checkMember() 메소드 작성
		
		// 4_15. MemberDao.checkMember() 호출 후 반환값 저장
		int check = mDao.checkMember(conn, memberId);
			
		// 4_16. 별도의 트랜잭션 처리없이 반환
		
		return check;
	}
	// 4_27. 회원 정보 수정 Service
	public int updateMember(int sel, String memberId, String input) {
		
		// 4_28. 커넥션, Dao 생성
		Connection conn = getConnection();
		MemberDao mDao = new MemberDao();
		
		// 4_29. 커넥션 객체와, 입력받은 값들을 매개변수로 하여
		// 회원정보를 수정하는
		// MemberDao.updateMember() 작성
		
		
		// 4_38. MemberDao.updateMember() 호출 후 저장
		int result = mDao.updateMember(conn, sel, memberId, input);
		
		// 4_39. 트랜잭션 처리
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		//4_40. 수정 결과 반환
		return result;
		
	}
	
	
	// 5_9. 회원 정보 삭제 Service
	public int deleteMember(String memberId) {
		
		// 5_10. 커넥션, Dao 객체 생성
		Connection conn = getConnection();
		MemberDao mDao = new MemberDao();
		
		// 5_11. 커넥션 객체와, memberId를 매개변수로 하여
		// 회원정보를 삭제하는
		// MemberDao.deleteMember() 작성.
		
		// 5_17. MemberDao.deleteMember() 호출 후 결과 저장
		int result = mDao.deleteMember(conn, memberId);
		
		// 5_18. 트랜잭션 처리
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		// 5_19. 결과 반환
		return result;
			
		
	}
	
	
	// 6_1. 프로그램 종료 Service
	public void exitProgram() {
		Connection conn = getConnection();
		
		close(conn);
	}
	
}





































