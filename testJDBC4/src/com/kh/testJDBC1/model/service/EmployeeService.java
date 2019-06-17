package com.kh.testJDBC1.model.service;

import static com.kh.testJDBC1.model.common.JDBCTemplate.*;

import java.sql.Connection;

import com.kh.testJDBC1.model.dao.EmployeeDao;
import com.kh.testJDBC1.model.vo.Employee;

public class EmployeeService {
	
	public int insertEmp(Employee emp) {
		Connection conn =getConnection();
		EmployeeDao mDao = new EmployeeDao();
		int result = mDao.insertEmp(conn,emp);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}
}
