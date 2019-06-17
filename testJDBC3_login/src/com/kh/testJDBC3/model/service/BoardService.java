package com.kh.testJDBC3.model.service;

import com.kh.testJDBC3.model.dao.BoardDao;
import com.kh.testJDBC3.model.vo.Board;
import com.kh.testJDBC3.model.vo.Member;

import static com.kh.testJDBC3.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

/**
 * @author Baek Dong Hyeon
 * 게시판 프로그램 Service
 */
public class BoardService {
	
	public int login(Member mem) {
		
		Connection conn = getConnection();
		BoardDao bDao = new BoardDao();
		
		int result = bDao.login(conn, mem);
		
		return result;
	}
}






