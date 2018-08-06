package com.letter.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.UnavailableException;
import javax.sql.DataSource;

public class LetterDAO implements Letter_Interface{

	
	private static DataSource ds = null;
	static {
		try{
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/petym");
		}catch(Exception e){
			try {
				throw new UnavailableException("Couldn't get db connection");
			} catch (UnavailableException e1) {
				e1.printStackTrace();
			}
		}
	}
	
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
	private static final String GETONEMEM =
			"SELECT LETTERNO, LETTERTYPENO, MEMNO, LETTERTIME , LETTERSTATE,LETTERTAG,LETTERTEXT FROM LETTER WHERE MEMNO = ? ORDER BY LETTERTIME DESC";
	private static final String GETTAG =
			"SELECT LETTERNO, LETTERTYPENO, MEMNO, LETTERTIME , LETTERSTATE,LETTERTAG,LETTERTEXT FROM LETTER WHERE MEMNO = ? AND LETTERTAG = 1 ORDER BY LETTERTIME DESC";
	private static final String GETNOTREAD =
			"SELECT LETTERNO, LETTERTYPENO, MEMNO, LETTERTIME , LETTERSTATE,LETTERTAG,LETTERTEXT FROM LETTER WHERE MEMNO = ? AND LETTERSTATE = 0 ORDER BY LETTERTIME DESC"; //0ªí¥Ü¥¼Åª
	
	
	
	
	
	@Override
	public void insert(Letter letter) {

		Connection con =null;
		PreparedStatement pstmt =null;
		Clob clob =null;
		try{
			
			con = ds.getConnection();
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
		}finally{
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
			con = ds.getConnection();
			
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
		}finally{
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1,letterNo);
			pstmt.executeUpdate();
			
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
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

			con = ds.getConnection();
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
		}catch (SQLException se) {
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
			
			con = ds.getConnection();
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

	@Override
	public List<Letter> getOneMemLtrs(Integer memNo) {
		
		List<Letter> list = new ArrayList<Letter>();
		Letter letter = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETONEMEM);
			pstmt.setInt(1, memNo);
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

	@Override
	public List<Letter> getTagLtrs(Integer memNo) {
		List<Letter> list = new ArrayList<Letter>();
		Letter letter = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETTAG);
			pstmt.setInt(1, memNo);
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

	@Override
	public List<Letter> getNotReadLtrs(Integer memNo) {
		List<Letter> list = new ArrayList<Letter>();
		Letter letter = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETNOTREAD);
			pstmt.setInt(1, memNo);
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

}
