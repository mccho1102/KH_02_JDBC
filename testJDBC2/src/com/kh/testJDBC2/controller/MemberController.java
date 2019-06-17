package com.kh.testJDBC2.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.kh.testJDBC2.model.dao.MemberDao;
import com.kh.testJDBC2.model.service.MemberService;
import com.kh.testJDBC2.model.vo.Member;
import com.kh.testJDBC2.view.MemberMenu;

public class MemberController {

	// 동작 중 필요한 서브메뉴, 결과를 반환할 뷰를 호출하기 위한
	// MemberMenu 객체 선언
	private MemberMenu menu = new MemberMenu();

	private MemberService mService = new MemberService();

	// 1. 새로운 회원 정보 추가
	// 입력받은 회원 정보를 DB로 전달한 후 결과에 따라
	// 알맞은 View에 연결처리하는 메소드
	public void insertMember() {

		// 1_1. 새로운 회원 정보를 입력받기 위한 서브메뉴
		// MemberMenu.insertMember() 메소드 작성

		// 1_4. 입력받은 회원 정보를 Member 변수에 저장
		Member mem = menu.insertMember();

		// 1_5. 회원 정보를 DB에 삽입하고 결과를 반환할
		// MemberDao.insertMember() 메소드 작성

		// 1_24. MemberSevice 객체를 필드에 선언

		// 1_25. MemberService.insertMember()를 호출하여
		// 반환값 저장
		int result = mService.insertMember(mem);

		// 1_26. DB 삽입 결과에 따른 View 연결처리
		if (result > 0) { // 삽입 성공

			// 1_27. DML 처리 성공 시 출력할 View
			// MemberMenu.displaySuccess() 작성

			// 1_29. insert 성공 메세지 출력
			menu.displaySuccess(result + " 개의 행이 추가되었습니다.");
		} else { // 삽입 실패
			// 1_30. 실패 및 오류 발생 출력용 View
			// MemberMenu.displayError() 작성

			// 1_32. insert 실패 메세지 출력
			menu.displayError("데이터 삽입 과정 오류 발생");

			// 1_33. mainMenu() case 1에
			// mController.insertMember() 호출 작성

		}
	}

	// 2. 모든 회원 정보 조회
	public void selectAll() {

		// 2_1. MemberService.selectAll() 메소드 작성

		// 2_18. MemberService.selectAll() 호출하여 반환 값 저장
		List<Member> mList = mService.selectAll();

		// 2_19. 조회 결과에 따라서 보여줄 View 연결처리
		if (!mList.isEmpty()) { // 조회 결과가 있을 경우

			// 2_20. 모든 회원 정보뿐만 아니라
			// 회원 정보 출력 시 사용할 View
			// MemberMenu.diplayMember() 작성

			// 2_22. 회원 정보 출력용 View 호출
			menu.displayMember(mList);
			
		} else { // 조회 결과가 없을 경우

			// 2_23. displayError 호출
			menu.displayError("조회결과가 없습니다.");

		}
		// 2_24. mainMenu() case 2 에서 mController.selectAll() 호출

	}

	// 3. 특정 조건 회원 조회
	public void selectMember() {
		
		// 3_1. 조회 결과를 저장할 임시 참조 변수 선언
		List<Member> mList = new ArrayList<Member>();
		
		// 3_2. 검색 조건을 입력받기 위한 서브 메뉴
		// MemberMenu.selectMember() 메소드 작성
		
		// 3_4. MemberMenu.selectMember() 호출 하여 반환값 저장
		int sel = menu.selectMember();
		
		// 3_5. 검색 조건에 따라 알맞은 Dao를 호출하도록 switch문 작성
		switch(sel) {
		// 3_6. 각 조건에 맞는 값을 입력받을 View
		// inputMemberId(), inputGender() 작성
		
		// 3_7. 입력받은 아이디와 커넥션 객체를 Dao로 전달할
		// MemberService.selectMemberId() 메소드 작성
		
		
		// 3_23. 1번 선택 시 아이디를 입력받고, 조회결과를 저장함
		case 1: 
				mList = mService.selectMemberId(menu.inputMemberId());
				break;
		
		case 2:mList = mService.selectMemberGender(menu.inputGender()); break;
		case 0: break;
		}
		
		// 3_24. 조회 결과에 따라 view 연결처리
		if(!mList.isEmpty()) { // 조회 결과가 있을 경우 // isEmpty == list가 비어있을때 true 아닐시 false
		
		// 3_25. 회원 정보 출력용 View 호출
				menu.displayMember(mList);
				}else { // 조회 결과가 없을 경우
		// 3_26. displayError 호출
					menu.displayError("조회결과가 없습니다.");
				}
		// 3_27. mainMenu() case 3 에서
		// mController.selectMember() 호출
		
	}
	
	// 4. 회원 정보 수정
	public void updateMember() {
		// 4_1. 정보를 수정할 회원 선택
		// PK로 설정된 memberId를 이용해 회원 선택
		// MemberMenu.inputMemberId() 호출 후 저장
		String memberId = menu.inputMemberId();
		
		// 4_2. 입력받은 아이디와 일치하는 회원이 있는지
		// 존재 여부 확인을 위하여 
		// MemberService.checkMember() 작성
		
		

		// 4_17. MemberService.checkMember()
		// 호출 후 반환값 저장
		int check = mService.checkMember(memberId);
		
		// 4_18. 회원 존재 여부에 따라 처리 방법 지정
		if(check != 1) { // 조회 결과가 잘못되거나 없는 경우
			menu.displayError("아이디 조회 실패!!!");
			
		}else { // 조회결과가 1인 경우
			
			// 4_19. 수정 내용을 선택할 서브메뉴 view
			// MemberMenu.updateMember() 메소드 작성
			
			// 4_21 MemberMenu.updateMember() 호출 후 반환값 
			int sel = menu.updateMember();
			
			// 4_22. 0번(메인 메뉴로 돌아가기)이 선택된 경우
			if(sel == 0) return;
			
			// 4_23. 수정할 값을 입력받을 view
			// MemberMenu.inputUpdate()	작성
			
			
			// 4_25. MemberMenu.inputUpdate() 호출 후 저장
			String input = menu.inputUpdate();
			
			// 4_26. 입력받은 값들과 커넥션 객체를 전달하는 Service
			// MemberService.updateMember() 작성
			
			
			// 4_41.  MemberService.updateMember() 호출 후 저장
			int result = mService.updateMember(sel, memberId, input);
			
			// 4_42. 수정 결과에 따른 view 연결 처리
			if(result > 0) {
				menu.displaySuccess(result + " 개의 행이 수정되었습니다.");
			}else {
				menu.displayError("데이터 수정 과정 오류 발생");
			}
		}
	}
	
	// 5. 회원 정보 삭제
	public void deleteMeber() {
		// 5_1. 회원 아이디 입력 view 호출하여 저장
		String memberId = menu.inputMemberId(); // 아이디를 받아오는 메소드
		
		// 5_2. 입력받은 아이디 존재 여부 확인
		int check = mService.checkMember(memberId);
		
		// 5_3. 회원 존재 여부에 따라 처리방법 지정
		if(check != 1) { // 조회결과가 없을 경우
			
			menu.displayError("입력한 아이디가 존재하지 않습니다.");
		} else {
			// 5_4. 삭제 여부를 입력받을 view
			// Membermenu.checkDelete() 작성
			
			//5_6. Membermenu.checkDelete() 호출 후 저장
			char yn = menu.checkDelete();
			
			// 5_7. 'N'이 입력된 경우 메인메뉴로 돌아가기
			if(yn == 'N') return;
			
			// 5_8. 커넥션 객체와 memberId를 Dao로 전달할
			// MemberService.deleteMember() 작성
			
			// 5_20. MemberDao.deleteMember() 호출 후 결과 저장
			int result = mService.deleteMember(memberId);
			
			// 5_21. 결과에 따른 view 연결처리
			if(result > 0) {
				menu.displaySuccess(result + " 개의 행이 삭제되었습니다.");
			}else {
				menu.displayError("데이터 삭제 과정 오류 발생");
			}
		}
	}
	
	// 6. 프로그램 종료
	public void exitProgram() {
		
	}
}




























