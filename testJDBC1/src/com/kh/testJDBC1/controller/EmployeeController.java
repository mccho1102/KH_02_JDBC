package com.kh.testJDBC1.controller;

import java.util.ArrayList;

import com.kh.testJDBC1.model.dao.EmployeeDao;
import com.kh.testJDBC1.model.vo.Employee;
import com.kh.testJDBC1.view.EmployeeMenu;

public class EmployeeController {
	// View에서 전달받은 데이터를 가공처리(데이터변환, 디코딩) 후 Dao로 전달
	
	// Dao로 부터 전달받은 결과에 따라 View(출력화면)를 결정하여
	// 인코딩 후 전송
	
	// DB 처리 결과에 따른 View 출력을 위한 EmployeeMenu 객체 생성
	private EmployeeMenu menu = new EmployeeMenu();
	
	// 1. 전체 사원 정보 조회
	public void selectAll() {
		
		// 1_1. View의 요청을 DB에 전달하고 결과를 반환받을
		// EmployeeDao 객체 생성
		EmployeeDao empDao = new EmployeeDao();
		
		// 1_2. DBMS 접속 및 전체 사원 정보 조회 결과를 반환
		// EmployeeDao.selectAll()을 작성
		
		// 1_7. DB 접속이 제대로 이루어 졌는지 확인하기 위하여
		// Dao에 selectAll() 호출해보기
		ArrayList<Employee> empList = empDao.selectAll();
		// 메인 메뉴 1번에서 controller.selectAll() 호출 작성
		
		
		// 1_13. 조회 결과에 따라 보여줄 View를 지정
		// 1) 반환된 empList에 저장된 데이터가 있을 경우
		// -> 조회 결과를 모두 출력하는 View 메소드 호출

		// 2) empList가 비어있을 경우
		// -> 조회결과가 없다는 메세지를 출력할 View 메소드 호출

		if (!empList.isEmpty()) { // 조회 결과가 있을 경우

			// 1_14. 전체 조회 결과를 출력할 View 작성하기
			// -> EmployeeMenu.selectAll() 작성

			// 1_16. 전체 사원 정보를 출력하는 View 호출
			menu.selectAll(empList);
		} else { // 조회 결과가 없을 경우

			// 1_17. 조회 뿐만 아니라 DB처리 작업이
			// 결과값이 없거나 오류가 발생하는 경우
			// 문제 내용을 출력해줄 별도의 View 호출
			// -> EmployeeMeny.displayError() 작성

			// 1_19. 조회결과가 없을 경우 메세지를 출력하는 View 호출
			menu.displayError("조회결과가 없습니다.");

		}
	}

	// 2. 사번으로 사원 정보 조회
	public void selectEmployee() {

		// 2_1. EmployeeDAO 객체 생성
		EmployeeDao empDao = new EmployeeDao();

		// 2_2. 사번을 입력받아야 하는데,
		// 사번 조회, 수정, 삭제 기능에 모두 사번을 입력받아야 됨.
		// --> 별도의 사번 입력 View 작성
		// --> EmployeeMenu.selectEmpNo() 작성

		// 2_4. 사번 입력 View를 호출하여 조회할 사번을 입력받음
		int empNo = menu.selectEmpNo();

		// 2_5. 입력된 사번을 매개변수로 전달하여 해당 사번을 가진 사원의 정보 조회
		// --> EmployeeDao.selectEmployee(empNO) 작성

		// 2_13. DB에서 조회된 사원 정보를 저장
		Employee emp = empDao.selectEmployee(empNo);

		// 2_14. 조회결과 유무에 따른 View 연결 지정
		if (emp != null) { // 조회 결과가 있을 경우
			// 2_15. 사원 한명의 정보를 출력하는 View
			// EmployeeMenu.selectEmployee() 작성

			// 2_17. EmployeeMenu.selectEmployee() 호출
			menu.selectEmployee(emp);
			// 메인 메뉴 case 2에서 호출하도록 작성.

		} else { // 조회 결과가 없을 경우

			// 2_18. displayError()에 "조회결과없음"을 매개변수로 전달
			menu.displayError("해당 사번의 검색 결과가 없습니다.");
		}
	}

	// 3. 새로운 사원 정보 추가
	public void insertEmployee() {

		// 3_1. EmployeeDao 객체 생성
		EmployeeDao empDao = new EmployeeDao();

		// 3_2. 사원 정보를 입력받을 수 있는 View 작성
		// --> EmployeeMenu.InsertEmployee() 작성

		// 3_4 사원 정보 입력 View 호출하고 반환값 지정
		Employee emp = menu.insertEmployee();

		// 3_5. 입력받은 사원정보를 DB에 삽입하기 위한
		// EmployeeDao.insertEmployee() 메소드 작성

		// 3_15. 사원 정보 삽입 후 결과를 반환받음
		int result = empDao.inserrEmployee(emp);

		// 3_16. 삽입 결과에 따른 View 연결 처리
		if (result > 0) {
			// 3_17. DML 성공 시 메세지를 출력할 View 작성
			// --> EmployeeMenu.displaySuccess()
			menu.displaySuccess(result + "개의 행이 삽입되었습니다.");
		}

		// 3_19. 성공 메세지 출력 View 호출
		else { // 삽입 실패 시
			// 3_20. displayError() 호출
			menu.displayError("데이터 삽입 과정 오류 발생");
		}
	}

	// 4. 사번으로 사원 정보 수정
	public void updateEmployee() {

		// 4_1. EmployeeDao 객체 생성
		EmployeeDao empDao = new EmployeeDao();

		// 4_2. 사번을 입력받는 View 호출
		int empNo = menu.selectEmpNo();

		// 4_3. 사원 정보 수정 내용 입력용 View 작성
		// - -> EmployeeMenu.updateEmployee() 메소드 작성

		// 4_5. 사원 정보 수정 내용 입력 View 호출
		Employee emp = menu.updateEmployee();

		// 4_6. 입력받은 사번을 emp에 저장
		emp.setEmpNo(empNo);

		// 4_7. 입력받은 사번과 일치하는 사원의 정보를
		// DB에서 수정하여 결과를 반환하는 메소드
		// EmployeeDao.updateEmployee() 작성

		// 4_18. DB 수정 결과를 반환받아 저장
		int result = empDao.updateEmployee(emp);

		// 4_19. 수정 결과에 따른 View 연결 처리
		if (result > 0) { // 사원 정보 수정 성공 시
			menu.displaySuccess(result + " 개의 행이 수정되었습니다.");
		} else { // 수정 실패 시
			menu.displayError("데이터 수정 과정 오류 발생");

		}

		// 5. 사번으로 사원 정보 삭제

	}

	public void deleteEmployee() {

		// 5_1. EmployeeDao 객체 생성
		EmployeeDao empDao = new EmployeeDao();

		// 5_2. 사번 입력 View 호출
		int empNo = menu.selectEmpNo();

		// 5_3. 사원 정보를 정말로 삭제할 것인지 확인하는
		// EmployeeMenu.deleteEmployee() 메소드 작성

		// 5_5. 입력받은 check 저장
		char check = menu.deleteEmployee();

		// 5_6. check 값에 따라서
		// Dao 실행 또는 메인 메뉴로 돌아가도록 처리
		if (check == 'Y') {
			// 5_7. 입력 받은 사번과 일치하는 사원의 정보를
			// DB에서 삭제하는 EmployeeDao.deleteEmployee() 메소드 작성

			// 5_18. DB 사원 정보 삭제 결과 저장
			int result = empDao.deleteEmployee(empNo);

			// 5_19. 삭제 결과에 따른 View 연결처리
			if (result > 0) { // 삭제 성공
				menu.displaySuccess(result + " 개의 행이 삭제되었습니다.");
			} else { // 삭제 실패
				menu.displayError("데이터 삭제 과정 오류 발생");
			}

		} else if (check == 'N') { // 입력값이 'N'일 경우
			menu.displaySuccess("사원 정보 삭제 취소");

		} else {
			menu.displayError("잘못 입력하셨습니다.(Y/N 입력");
		}
	}
}
