package com.kh.testJDBC3.model.dao;

import static com.kh.testJDBC3.common.JDBCTemplate.close;

import java.io.FileReader;
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
public class BoardDao {
	
	private Properties prop = null;
	
	public BoardDao() {
		try {
			prop = new Properties();
			
			prop.load(new FileReader("query.properties"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int insertBoard(Connection conn, Board boa) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("insertMember");
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, boa.getTitle());
			pstmt.setString(2, boa.getContent());
			pstmt.setString(3, boa.getWriter());
			
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
		
	}
	
	
	public String loginCheck(Connection conn, Member mem) {
		PreparedStatement pstmt = null;
		ResultSet rset =null;
		String name =null;
		String query = prop.getProperty("loginCheck");
		
		try {
	
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, mem.getMemberId());
		pstmt.setString(2, mem.getMemberPwd());
		rset=pstmt.executeQuery();
		if(rset.next()) {
			name =rset.getString("MEMBER_NAME");
		}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return name;
		
		}
	
	public List<Board> selectAll(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		List<Board> bList = null;
		
		String query = prop.getProperty("selectAll");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			bList = new ArrayList<Board>();
			
			Board boa = null;
			
			while(rset.next()) {
				int bno = rset.getInt("BNO");
				String title = rset.getString("TITLE");
				String content = rset.getString("CONTENT");
				String writer = rset.getString("WRITER");
				char delete_yn = rset.getString("DELETE_YN").charAt(0);
				
				boa = new Board(bno, title, content, writer, delete_yn);
				
				bList.add(boa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		return bList;
	}

	// 입력받은 글번호가 포함된 모든 글번호 조회
	public Board selectBoard(Connection conn,int Bno) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selectBoard");
	
		
	
		
		Board boa = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Bno);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				int bno = rset.getInt("BNO");
				String Title = rset.getString("TITLE");
				String Content = rset.getString("CONTENT");
				Date Create_Date = rset.getDate("CREATE_DATE");
				String Writer = rset.getString("WRITER");
				char Delete_yn = rset.getString("DELETE_YN").charAt(0);
				
				boa = new Board(bno, Title, Content, Create_Date, 
								Writer, Delete_yn);
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return boa;
		}
	
	
	public int checkBoard(Connection conn, String title) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int check = 0;
		
		String query = prop.getProperty("checkBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				check = rset.getInt(1);
			}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				close(rset);
				close(pstmt);
			}
		
		return check;
	}
	
	public int updateBoard(Connection conn, int sel, 
							String title, String input) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("updateBoard" + sel);
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, input);
			pstmt.setString(2, title);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally	 {
			close(pstmt);
		}
		return result;
			
	}
	
	
	
	public void deleteBoard(Connection conn, String title) {
		PreparedStatement pstmt = null;
		int result = 0;
		
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	


