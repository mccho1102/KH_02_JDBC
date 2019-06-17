package com.kh.testJDBC1.view;

import java.util.Scanner;

import com.kh.testJDBC1.controller.EmployeeController;
import com.kh.testJDBC1.model.vo.Employee;

public class EmployeeMenu {
	private Scanner sc = new Scanner(System.in);
	public void mainMenu() {
		
		EmployeeController ec = new EmployeeController();
		
		System.out.println("1. 삽입");
		System.out.println("2. 조회");
		System.out.println("3. 수정");
		System.out.println("4. 삭제");
		int sel =sc.nextInt();
		switch(sel) {
		case 1: ec.insertEmp(); break;
		case 2:  break;
		case 3:  break;
		case 4:	 break;
		}
	
	}
	public Employee insertEmp() {
		
		System.out.print("사번 :");
		int empNo =sc.nextInt();
		sc.nextLine();
		System.out.print("이름 :");
		String empName = sc.nextLine();
		System.out.print("직급 :");
		String job = sc.nextLine();
		System.out.print("매니져의 사번 :");
		int mgr = sc.nextInt();
		System.out.print("급여 :");
		int sal = sc.nextInt();
		System.out.print("인센티브 :");
		int comm = sc.nextInt();
		System.out.print("부서번호 :");
		int deptCode = sc.nextInt();
		Employee emp = new Employee(empNo, empName, job, mgr, sal, comm, deptCode);
		return emp;
	}
	
	public void messege(String msg) {
		System.out.println(msg);
	}

}
