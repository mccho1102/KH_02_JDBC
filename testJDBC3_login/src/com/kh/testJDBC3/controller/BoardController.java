package com.kh.testJDBC3.controller;

import java.util.List;

import com.kh.testJDBC3.model.service.BoardService;
import com.kh.testJDBC3.model.vo.Board;
import com.kh.testJDBC3.model.vo.Member;
import com.kh.testJDBC3.view.BoardView;

/**
 * @author Baek Dong Hyeon
 * 게시판 프로그램 Controller
 */
public class BoardController {

	private BoardService bService = new BoardService();
	private BoardView bView = new BoardView();
	
	
	/** 
	 * 회원 로그인 메소드
	 */
	public void login() {
		
		// id, pwd 입력
		Member mem = bView.inputLogin();
		
		int result = bService.login(mem);
		
		if(result > 0) {
			bView.displayLoginSuccess();
		}else {
			bView.displayLoginError();
		}
	}

}











