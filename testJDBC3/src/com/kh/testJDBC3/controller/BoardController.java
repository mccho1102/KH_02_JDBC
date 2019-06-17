package com.kh.testJDBC3.controller;

import java.util.ArrayList;
import java.util.List;

import com.kh.testJDBC3.model.service.BoardService;
import com.kh.testJDBC3.model.vo.Board;
import com.kh.testJDBC3.model.vo.Member;
import com.kh.testJDBC3.view.BoardView;

public class BoardController {

		private BoardView bview = new BoardView();
		
		private BoardService bService = new BoardService();
		
		
	public void loginView() {
		
		Member mem = bview.loginBoard();
		
		String name = bService.loginCheck(mem);
		
		if(name != null) {
			bview.loginView(name);
		}else {
		}
		
	}
	
	public void insertBoard() {
		
		Board boa = bview.insertBoard();
		
		int result = bService.insertBoard(boa);
		if(result > 0) {
			bview.displaySuccess(result + "내용이 추가되었습니다.");
		}else {
			bview.displayError("@ 데이터 삽입 과정 오류 발생@ ");
		}
	}
	
	public void selectAll() {
		List<Board> bList = bService.selectAll();
		
		if(!bList.isEmpty()) {
			
			bview.selectAll(bList);
		}else {
			bview.displayError("조회결과가 없습니다.");
		}
	}
	
	// 게시글 조회(글번호)
	public void selectBoard() {
		
		Board boa = null;
		int sel = bview.selectBoard();
		
		switch(sel) {
		case 1:
				 boa= bService.selectBoard(bview.inputBno());
				break;
		case 0: break;
		}
		
		if(boa!=null) {
			bview.selectBoa(boa);
		}else {
			bview.displayError("조회결과가 없습니다.");
		}
	}
	
	// 글 수정
	public void updateBoard() {
		
		String title = bview.inputTitle();
		
		int check = bService.checkBoard(title);
		
		if(check != 1) {
			bview.displayError("글조회를 실패하였습니다.");
		}else {
			int sel = bview.updateBoard();
			if(sel == 0) return;
			String input = bview.inputUpdate();
			
			int result = bService.updateBoard(sel, title, input);
			
			if(result > 0) {
				bview.displaySuccess(result + "내용이 수정되었습니다.");
			}else {
				bview.displayError("데이터 수정 과정 오류 발생");
			}
		}
	}
	
	public void deleteMember() {
		String title = bview.inputTitle();
		int check = bService.checkBoard(title);
		
		if(check != 1) {
			bview.displayError("입력한 내용이 없습니다.");
		}else {
			char yn = bview.deleteBoard();
			if(yn == 'N') return;
		}
		
	}
}









