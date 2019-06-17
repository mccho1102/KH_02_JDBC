package com.kh.testJDBC3.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.testJDBC3.model.vo.Board;
import com.kh.testJDBC3.model.vo.Member;

import static com.kh.testJDBC3.common.JDBCTemplate.*;

/**
 * @author Baek Dong Hyeon
 * 게시판 프로그램 Dao
 */
public class BoardDao {
	
	Properties prop = null;
	
	/**
	 * BoardDao 객체 생성 시 query.properties 파일을 읽어옴
	 */
	public BoardDao() {
		try {
			prop = new Properties();
			prop.load(new FileReader("query.properties"));
			// query.properties 파일을 만들어 두고
			// 추후 SQL 구문 추가가 필요하면 작성하기
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/** 로그인 Dao
	 * @param conn
	 * @param mem
	 * @return
	 */
	public int login(Connection conn, Member mem) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("login");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, mem.getMemberId());
			pstmt.setString(2, mem.getMemberPwd());
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}	
		
		return result;
	}
}








