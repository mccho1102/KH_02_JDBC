package com.kh.testJDBC2.view;

import java.util.List;
import java.util.Scanner;

import com.kh.testJDBC2.controller.MemberController;
import com.kh.testJDBC2.model.vo.Member;

public class MemberMenu {

	private Scanner sc = new Scanner(System.in);
	
	public MemberMenu() {} // 기본 생성자
	
	public void mainMenu() {
		
		MemberController mController = new MemberController();
		
		int select = 0;
		
		do {
			System.out.println("\n *** 회원 관리 프로그램 *** \n");
			System.out.println("1. 새 회원등록");
			System.out.println("2. 모든 회원 조회");
			System.out.println("3. 특정 조건 회원 조회");
			System.out.println("4. 회원 정보 수정");
			System.out.println("5. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");
			System.out.print("번호 선택 : ");
			select = sc.nextInt();
			
			switch(select) {
			case 1 : mController.insertMember(); break;
			case 2 : mController.selectAll(); break;
			case 3 : mController.selectMember(); break;
			case 4 : mController.updateMember(); break;
			case 5 : mController.deleteMeber(); break;
			case 0 : mController.exitProgram();
					 System.out.println("프로그램 종료"); break;
			default :System.out.println("잘못 입력하셨습니다. 다시 입력해주세요."); break;
			}
			System.out.println();
			
		}while(select != 0);
	}
	
	// =============================================================================
	// 서브 메뉴
	
	// 메소드 내부에 커서 올려두고 art + shift + j
	
	/** 1_2. 회원 정보 입력용 View
	 * @return
	 */
	public Member insertMember() {
		System.out.print("회원 아이디 : ");
		String memberId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String memberPwd = sc.nextLine();
		
		System.out.print("이름 : ");
		String memberName = sc.nextLine();
		
		System.out.print("성별(남:M/여:F) : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호(-포함) : ");
		String phone = sc.nextLine();
		
		System.out.print("나이 : ");
		int age = sc.nextInt();
		sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		// 1_3. 입력된 회원 정보를 Member 객체에 저장하여 반환
		Member mem = new Member(memberId, memberPwd, memberName, gender, email, phone, age, address);
	
		return mem;
	}
	
	// 1_28. DML 성공 결과 출력용 View
	public void displaySuccess(String msg) {
		System.out.println("서비스 요청 성공 :" + msg);
	}
	
	// 1_31. Error 메세지 출력용 View
	public void displayError(String msg) {
		System.out.println("서비스 요청 실패 :" + msg);
	}
	
	
	// 2_21. 회원 정보 출력용 View
	public void displayMember(List<Member> mList) {
		
		System.out.printf("%-10s %-10s %-5s %-5s %-20s %-15s %-4s %-20s %-15s\n",
				"ID", "PWD", "NAME", "GENDER", "EMAIL", 
				"PHONE", "AGE", "ADDRESS", "EROLLDATE");
		
		for(Member m : mList) {
			System.out.printf("%-10s %-10s %-8s %-5c %-20s %-15s %-4d %-20s %-15s\n",
				m.getMemberId(), m.getMemberPwd(), m.getMemberName(),
				m.getGender(), m.getEmail(), m.getPhone(),
				m.getAge(), m.getAddress(), m.getEnrollDate());
		}
	}
	
	
	// 3_3. 특정 조건 회원 검색용 View
	public int selectMember() {
		
		int sel = 0;
		
		while(true) {
		System.out.println("1. 아이디 조회");
		System.out.println("2. 성별 조회");
		System.out.println("0. 메인 메뉴로 돌아가기");
		System.out.print("입력 : ");
		sel = sc.nextInt();
		sc.nextLine();
		
		switch(sel) {
		case 1:	case 2:	case 0: return sel;
		default : System.out.println("잘못 입력하셨습니다. 다시입력해주세요.");
		}
		}
	}
	
	// (3_6) 아이디 입력용 메소드
	public String inputMemberId() {
		System.out.print("회원 아이디 : ");
		String id = sc.nextLine();
		return id;
	
	
	}
	
	
	// (3_6) 성별 입력용 메소드
	public char inputGender() {
		char gen = ' ';
		
		
		while(true) {
		System.out.print("성별 입력(M / F) : ");
		gen = sc.nextLine().toUpperCase().charAt(0);
		
		if(gen == 'M' || gen == 'F') break;
		else System.out.print("M 또는 F만 입력해주세요.");
		}
		
		return gen;
	}
	
	
	// 4_20. 회원 정보 수정 서브메뉴 View
	public int updateMember() {
		
		System.out.println("\n*** ID가 확인되었습니다. ***");
	
		// 알맞은 값이 입력될때 까지 while문으로 반복
		int sel = 0;
		while(true) {
			System.out.println("1. 비밀번호 변경");
			System.out.println("2. 이메일 변경");
			System.out.println("3. 전화번호 변경");
			System.out.println("4. 주소변경");
			System.out.println("0. 메인 메뉴로 돌아가기");
			System.out.print("번호 선택 : ");
			sel = sc.nextInt();
			sc.nextLine();
			
			switch(sel) {
			case 1: case 2: case 3: case 4: case 0: return sel;
			default : System.out.println("잘못 입력 하셨습니다. 다시 입력해주세요.");
			}
		}
	}

	// 4_24. 수정할 값을 입력받을 view
	public String inputUpdate() {
		System.out.print("수정할 값 입력 : ");
		String input = sc.nextLine();
		return input;
	}
	
	
	// 5_5. 삭제 여부 입력용 view
	public char checkDelete() {
		System.out.println("\n*** ID가 확인되었습니다. ***\n");
		
		System.out.println("정말로 삭제 하시겠습니까?(Y/N) : ");
		char yn = sc.nextLine().toUpperCase().charAt(0);
		return yn;
	}
	
	
	
	
}

