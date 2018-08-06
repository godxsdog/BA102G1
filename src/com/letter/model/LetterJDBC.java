package com.letter.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lettertype.model.LetterType;

public class LetterJDBC implements Letter_Interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "petym";
	String passwd = "123456";
	
	
	private static final String INSERT = 
			"INSERT INTO LETTER(LETTERNO, LETTERTYPENO, MEMNO, LETTERTIME , LETTERSTATE,LETTERTAG,LETTERTEXT ) VALUES (LETTER_SEQ.NEXTVAL,?,?,?,?,?,?)";
	private static final String GETALL = 
			"SELECT LETTERNO, LETTERTYPENO, MEMNO, LETTERTIME , LETTERSTATE,LETTERTAG,LETTERTEXT FROM LETTER ORDER BY LETTERNO";
	private static final String GETONE =
			"SELECT LETTERNO, LETTERTYPENO, MEMNO, LETTERTIME , LETTERSTATE,LETTERTAG,LETTERTEXT FROM LETTER WHERE LETTERNO = ?";
	private static final String UPDATE =
			"UPDATE LETTER SET  LETTERTYPENO=?, MEMNO=?, LETTERTIME=? , LETTERSTATE=? ,LETTERTAG=? ,LETTERTEXT=? WHERE LETTERNO = ?";
	private static final String DELETE=
			"DELETE FROM LETTER WHERE LETTERNO = ?";
	

	@Override
	public void insert(Letter letter) {
		
	
		Connection con =null;
		PreparedStatement pstmt =null;
		Clob clob =null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			clob = con.createClob();	
			
			clob.setString(1,letter.getLetterText());
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1, letter.getLetterTypeNo());
			pstmt.setInt(2, letter.getMemNo());
			pstmt.setTimestamp(3, letter.getLetterTime());
			pstmt.setInt(4, letter.getLetterState());
			pstmt.setInt(5, letter.getLetterTag());
			pstmt.setClob(6, clob);
			
			pstmt.executeUpdate();
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		finally{
			if(pstmt!=null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
				
			}
			if(con!=null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(Letter letter) {
		
		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob = null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			
			pstmt = con.prepareStatement(UPDATE);
			clob = con.createClob();
			clob.setString(1,letter.getLetterText());
			
			pstmt.setInt(1, letter.getLetterTypeNo());
			pstmt.setInt(2, letter.getMemNo());
			pstmt.setTimestamp(3, letter.getLetterTime());
			pstmt.setInt(4, letter.getLetterState());
			pstmt.setInt(5, letter.getLetterTag());
			pstmt.setClob(6, clob);
			pstmt.setInt(7, letter.getLetterNo());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		finally{
			if(pstmt!=null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
				
			}
			if(con!=null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace(System.err);
				}
			}
		}
			
	}

	@Override
	public void delete(Integer letterNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1,letterNo);
			pstmt.executeUpdate();
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		finally{
			if(pstmt!=null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
				
			}
			if(con!=null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public Letter findByPrimaryKey(Integer letterNo) {

		Letter letter =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONE);
			
			pstmt.setInt(1,letterNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				letter = new Letter();
				
				letter.setLetterNo(rs.getInt("letterno"));
				letter.setLetterTypeNo(rs.getInt("lettertypeno"));
				letter.setMemNo(rs.getInt("memno"));
				letter.setLetterTime(rs.getTimestamp("lettertime"));
				letter.setLetterState(rs.getInt("letterstate"));
				letter.setLetterTag(rs.getInt("lettertag"));
				letter.setLetterText(rs.getString("lettertext"));
				
			}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return letter;
	}

	@Override
	public List<Letter> getAll() {

		List<Letter> list = new ArrayList<Letter>();
		Letter letter = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				letter = new Letter();
				
				letter.setLetterNo(rs.getInt("letterno"));
				letter.setLetterTypeNo(rs.getInt("lettertypeno"));
				letter.setMemNo(rs.getInt("memno"));
				letter.setLetterTime(rs.getTimestamp("lettertime"));
				letter.setLetterState(rs.getInt("letterstate"));
				letter.setLetterTag(rs.getInt("lettertag"));
				letter.setLetterText(rs.getString("lettertext"));
				list.add(letter);
			}
		
		}catch(ClassNotFoundException ce){
			ce.printStackTrace();
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(con != null){
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace(System.err);
				}
			}
		}
		
		return list;
	}
	
	public static void main(String[] args) {

		LetterJDBC dao = new LetterJDBC();
		
		//add
//		Letter letter = new Letter();
//		letter.setLettertypeNo(30005);
//		letter.setMemNo(5001);
//		letter.setLetterTime(new java.sql.Timestamp(System.currentTimeMillis()));
//		letter.setLetterState(1);
//		letter.setLetterTag(0);
//		letter.setLetterText("我是測試");
//		dao.insert(letter);
		
		//update
//		Letter letter = new Letter();
//		letter.setLetterNo(40007);
//		letter.setLetterTypeNo(30005);
//		letter.setMemNo(5001);
//		letter.setLetterTime(new java.sql.Timestamp(System.currentTimeMillis()));
//		letter.setLetterState(0);
//		letter.setLetterTag(1);
//		letter.setLetterText("我變了");
//		dao.update(letter);
		
		//delete
//		dao.delete(40007);
		
		//query 1
//		Letter letter = dao.findByPrimaryKey(40003);
//		System.out.print(letter.getLetterNo()+", ");
//		System.out.print(letter.getLetterTypeNo()+", ");
//		System.out.print(letter.getMemNo()+", ");
//		System.out.print(letter.getLetterTime()+", ");
//		System.out.print(letter.getLetterState()+", ");
//		System.out.print(letter.getLetterTag()+", ");
//		System.out.print(letter.getLetterText());
//		System.out.println();
		
		//query all
		List<Letter> list = dao.getAll();
		for(Letter letter:list){
			System.out.print(letter.getLetterNo()+", ");
			System.out.print(letter.getLetterTypeNo()+", ");
			System.out.print(letter.getMemNo()+", ");
			System.out.print(letter.getLetterTime()+", ");
			System.out.print(letter.getLetterState()+", ");
			System.out.print(letter.getLetterTag()+", ");
			System.out.print(letter.getLetterText());
			System.out.println();
		}
		
		
		
		
		
	}

	@Override
	public List<Letter> getOneMemLtrs(Integer memNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Letter> getTagLtrs(Integer memNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Letter> getNotReadLtrs(Integer memNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
