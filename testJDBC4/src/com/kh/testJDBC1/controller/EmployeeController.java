package com.kh.testJDBC1.controller;

import com.kh.testJDBC1.model.service.EmployeeService;
import com.kh.testJDBC1.model.vo.Employee;
import com.kh.testJDBC1.view.EmployeeMenu;

public class EmployeeController {
	
	private EmployeeMenu em = new EmployeeMenu();
	private EmployeeService es =new EmployeeService();
	
	public void insertEmp() {
		Employee emp = null;
		emp=em.insertEmp();
		int result = es.insertEmp(emp);
		if(result > 0) {
			em.messege("성공했습니다.");
		}else {
			em.messege("실패했습니다.");
		}
	}
	
	
	
}
