package com.kh.testJDBC1.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.testJDBC1.model.vo.Employee;
import static com.kh.testJDBC1.model.common.JDBCTemplate.*;


public class EmployeeDao {
	
	
	private Properties prop = new Properties();
	
	public EmployeeDao() {
		super();
		try {
			prop.load(new FileReader("query.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insertEmp(Connection conn,Employee emp) {
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertEmp");
		int result=0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getJob());
			pstmt.setInt(4, emp.getMgr());
			pstmt.setInt(5, emp.getSal());
			pstmt.setInt(6, emp.getComm());
			pstmt.setInt(7, emp.getDeptCode());
			 result= pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
}
