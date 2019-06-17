package com.kh.testJDBC3.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.testJDBC3.model.dao.BoardDao;
import com.kh.testJDBC3.model.vo.Board;
import com.kh.testJDBC3.model.vo.Member;
import static com.kh.testJDBC3.common.JDBCTemplate.*;
public class BoardService {
	
	public String loginCheck(Member mem) {
		Connection conn = getConnection();
		
		BoardDao bDao = new BoardDao();
		
		String name = bDao.loginCheck(conn, mem);
		
		
		return name;
	}
	
	public int insertBoard(Board boa) {
		Connection conn = getConnection();
		
		BoardDao bDao = new BoardDao();
		
		int result = bDao.insertBoard(conn, boa);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
	}

	public List<Board> selectAll() {
		Connection conn = getConnection();
		
		BoardDao bDao = new BoardDao();
		
		List<Board> bList = bDao.selectAll(conn);
		
		return bList;
	}
	
	public Board selectBoard(int Bno) {
		Connection conn = getConnection();
		
		BoardDao bDao = new BoardDao();
		
		Board boa = bDao.selectBoard(conn, Bno);
		
		return boa;
		
	}
	
	public int checkBoard(String title) {
		Connection conn = getConnection();
		BoardDao bDao = new BoardDao();
		
		int check = bDao.checkBoard(conn, title);
		
		return check;
		
	}
	
	public int updateBoard(int sel, String title, String input) {
		Connection conn = getConnection();
		BoardDao bDao = new BoardDao();
		
		int result = bDao.updateBoard(conn, sel, title, input);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		return result;
		
	}
	
	public void deleteBoard(String title) {
		Connection conn = getConnection();
		BoardDao bDao = new BoardDao();
		
		
	}
}






















