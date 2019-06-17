package com.kh.testJDBC3.view;

import java.util.List;
import java.util.Scanner;

import com.kh.testJDBC3.controller.BoardController;
import com.kh.testJDBC3.model.vo.Board;
import com.kh.testJDBC3.model.vo.Member;

public class BoardView {

	private Scanner sc = new Scanner(System.in);
	private static Member mem = null;

	public BoardView() {
	}

	public void mainView() {

		BoardController bController = new BoardController();

		int select = 0;

		do {
			System.out.println("\n *** 게시판 프로그램 ***\n");
			System.out.println("1. 로그인");
			System.out.println("0. 프로그램 종료");
			System.out.print("번호 선택 : ");
			select = sc.nextInt();

			switch (select) {
			case 1:
				bController.loginView();
				break;
			case 0:
				break;
			default:
				System.out.println("잘못입력하셨습니다. 다시입력해주세요.");
			}
			System.out.println();

		} while (select != 0);
	}

	public Member loginBoard() {
		System.out.println("★★★★★ 로그인 ★★★★★");
		System.out.print("USER ID : ");
		String userId = sc.nextLine();

		System.out.print("USER PW : ");
		String userPwd = sc.nextLine();

		Member mem = new Member(userId, userPwd);
		
		return mem;
	}

	public void loginView(String name) {

		BoardController bController = new BoardController();
		mem = new Member(name);
		if (mem.getMemberName() != null) {
			System.out.println(mem.getMemberName() + "님 로그인 되셨습니다.");
		}
		int select = 0;
		do {
			System.out.println("=== 게시판 프로그램 ===");
			System.out.println("1. 로그아웃");
			System.out.println("2. 글 목록 조회");
			System.out.println("3. 게시글 조회(글번호)");
			System.out.println("4. 글쓰기");
			System.out.println("5. 글수정");
			System.out.println("6. 글삭제");
			System.out.println("0. 프로그램 종료");
			System.out.print("번호 선택 : ");
			select = sc.nextInt();
			sc.nextLine();

			switch (select) {
			case 1: mem = null;
			System.out.println("로그아웃 되었습니다.");
					mainView();
				break;
			case 2:bController.selectAll();
				break;
			case 3:bController.selectBoard();
				break;
			case 4:bController.insertBoard();
				break;
			case 5:bController.updateBoard();
				break;
			case 6:
				break;
			case 0:
				System.out.println("프로그램 종료");
				break;
			default:
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				break;
			}
		} while (select != 0);
		System.out.println();
	}

	public void displaySuccess(String boa) {
		System.out.println("서브시 요청 성공 : " + boa);
	}

	public void displayError(String boa) {
		System.out.println("서비스 요청 실패 : " + boa);
	}

	public Board insertBoard() {

		System.out.println("제목 : ");
		String boardTitle = sc.nextLine();

		System.out.println("내용 : ");
		String boardContent = sc.nextLine();

		Board boa = new Board(boardTitle, boardContent);

		return boa;
	}

	public void selectAll(List<Board> bList) {
		for (Board b : bList) {
			System.out.println(b);
		}
	}

	// 게시글 조회 검색용 view
	public int selectBoard() {
		int sel = 0;

		while (true) {
			System.out.print("1. 글번호조회");
			System.out.print("2. 제목조회");
			System.out.print("3. 내용조회");
			System.out.println("0. 메인메뉴로 돌아가기");
			System.out.println("입력 : ");
			sel = sc.nextInt();
			sc.nextLine();
			
			switch (sel) {
			case 1:
			case 2:
			case 3:
			case 0:
				return sel;
			default:
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
	}

	// 글번호조회 입력용 메소드
	public int inputBno() {
		System.out.println("글번호 : ");
		int bno = sc.nextInt();
		return bno;
	}
	
	public String inputTitle() {
		System.out.print("제목을 입력해주세요 : ");
		String title = sc.nextLine();
		return title;
	}

	public String inputContent() {
		System.out.println("내용을 입력주세요 : ");
		String con = sc.nextLine();
		return con;
	}

	public void selectBoa(Board boa) {
		System.out.println("제목 : " + boa.getTitle());
		System.out.println("=======================");
		System.out.println(boa.getContent());

	}
	
	public int updateBoard() {
		System.out.println("\n === 검색이 완료되었습니다.=== \n");
		
		int sel = 0;
		while(true) {
			System.out.println("1. 제목 수정");
			System.out.println("2. 내용 수정");
			System.out.println("0. 메인 메뉴로 돌아가기");
			System.out.print("번호를 입력해주세요 : ");
			sel = sc.nextInt();
			sc.nextLine();
			
			switch(sel) {
			case 1: case 2: case 0: return sel;
			default : System.out.println("번호를 잘못입력하셨습니다. 다시입력주세요.");
			}
		}
	}
	
	public String inputUpdate() {
		System.out.print("수정할 값 입력 : ");
		String input = sc.nextLine();
		return input;
	}
	
	public char deleteBoard() {
		System.out.println(" \n 입력하신 내용이 확인되었습니다 \n");
		System.out.println("정말로 삭제하시겠습니까?(Y/N) : ");
		char yn = sc.nextLine().toUpperCase().charAt(0);
		return yn;
	}
}



















