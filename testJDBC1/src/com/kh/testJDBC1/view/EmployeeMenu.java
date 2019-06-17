package com.kh.testJDBC1.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.testJDBC1.controller.EmployeeController;
import com.kh.testJDBC1.model.vo.Employee;

public class EmployeeMenu {
	
	// View 전체에서 공용으로 사용될 Scanner 객체 생성
	private Scanner sc = new Scanner(System.in);
	
	// MainMenu
	public void mainMenu() {
		
		EmployeeController controller = new EmployeeController();
		// 클라이언트의 요청을 Dao에 알맞은 메소드로 전달하고
		// DB 처리결과를 반환받는 역할
		
		int select = 0; // 메뉴선택하는 임시변수
		
		do {
			System.out.println("==================================");
			System.out.println("[Main Menu]");
			System.out.println("1. 전체 사원 정보 조회");
			System.out.println("2. 사번으로 사원 정보 조회");
			System.out.println("3. 새로운 사원 정보 추가");
			System.out.println("4. 사번으로 사원 정보 수정");
			System.out.println("5. 사번으로 사원 정보 삭제");
			System.out.println("0. 프로그램 종료");
			System.out.println("==================================");
			
			System.out.print("메뉴 선택 >> ");
			select = sc.nextInt();
			sc.nextLine();	// 버퍼에 남아있는 개행문자 제거
			
			switch(select) {
			case 1 : controller.selectAll(); break;
			case 2 : controller.selectEmployee(); break;
			case 3 : controller.insertEmployee(); break;
			case 4 : controller.updateEmployee(); break;
			case 5 : controller.deleteEmployee(); break;
			case 0 : System.out.println("프로그램을 종료합니다."); break;
			default : System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
			}
			System.out.println();
		
		}while(select != 0); 
	}
	
	
	
	// -------------------------------------------------------------------------------
	// Sub Menu
	
	// 1_15. 전체 사원 정보 출력 View
	public void selectAll(ArrayList<Employee> empList) {
		System.out.println("사번 \t 이름 \t 직책 \t 직속상사 \t "
				+ "고용일 \t 급여 \t 커미션 \t 부서번호");
	
		// 전체 조회된 사원 정보 출력
		// for-each(향상된 for문)
		for(Employee emp : empList) {
			System.out.println(emp);
		}
	}
	
	// Error
	// 1_18. Error 메시지 출력 View
	public void displayError(String msg) {
		System.out.println("서비스 요청 실패 : " + msg);
	}
	
	// 2_3. 사번 입력 View(2, 4, 5메뉴에 필요)
	// --> 별도의 메소드로 작성하여 코드 길이 감소, 재사용성 증가
	public int selectEmpNo() {
		System.out.print("사번을 입력하세요 >> ");
		int empNo = sc.nextInt();
		sc.nextLine();	// 개행 문자 제거
		return empNo;
	}
	
	// 2_16. 사원 한명의 정보를 출력하는 View
	public void selectEmployee(Employee emp) {
		System.out.println("사번 : " + emp.getEmpNo());
		System.out.println("이름 : " + emp.getEmpName());
		System.out.println("직책 : " + emp.getJob());
		System.out.println("직속 상사 : " + emp.getMgr());
		System.out.println("고용일 : " + emp.getHireDate());
		System.out.println("급여 : " + emp.getSal());
		System.out.println("커미션 : " + emp.getComm());
		System.out.println("부서번호 : " + emp.getDeptNo());
	}
	
	// 3_3. 새로운 사원 정보 추가 View
	public Employee insertEmployee() {
		
		System.out.println("[새로운 사원 정보 추가]");
		
		System.out.println("사번 : ");
		int empNo = sc.nextInt();
		sc.nextLine();
		
		System.out.println("이름 : ");
		String empName = sc.nextLine();
		
		System.out.println("직책 : ");
		String job = sc.nextLine();
		
		System.out.println("직속 상사 번호 : ");
		int mgr = sc.nextInt();
		
		// HIREDATE는 오늘 날짜로 --> INSERT 시 SYSDATE로 처리
		
		System.out.println("급여 : ");
		int sal = sc.nextInt();
		
		System.out.println("커미션(인센티브) : ");
		int comm = sc.nextInt();
		
		System.out.println("부서번호 : ");
		int deptNo = sc.nextInt();
		sc.nextLine(); // 개행 문자 제거
		
		Employee emp = new Employee(empNo, empName, job, mgr, sal, comm, deptNo);
		
		return emp;
	}
	
	// 3_18. 사원 정보 삽입, 수정, 삭제 결과 출력 View
	public void displaySuccess(String msg) {
		System.out.println("서비스 요청 성공 : " + msg);
	}
	
	
	// 4_4. 사원 정보 수정 내용 입력용 View
	public Employee updateEmployee() {
		
		System.out.print("직책 : ");
		String job = sc.nextLine();
		
		System.out.print("급여 : ");
		int sal = sc.nextInt();
		
		System.out.print("커미션(인센티브) : ");
		int comm = sc.nextInt();
		sc.nextLine(); // 개행문자 제거
		
		// 매개변수 3개를 전달받는 생성자 만들기
		Employee emp = new Employee(job, sal, comm);
		
		return emp;
	}
	
	// 5_4. 사원 정보 삭제 확인 View
	public char deleteEmployee() {
		System.out.println("정말로 삭제 하시겠습니까?(Y/N) : ");
		char check = sc.nextLine().charAt(0);
		
		check = Character.toUpperCase(check); // 대문자로 바꿔는 주는 메소드
		
		return check;
		
	}
	
	public void msg(String msg) {
		System.out.println("메세지 : " + msg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
