package com.diary.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DiaryJDBCDAO implements DiaryDAO_Interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "petym";
	String passwd = "123456";
	
	private static final String INSERT = 
			"INSERT INTO DIARY(diano, memno, dianame, diatext, diaimg, diacretime, diamodtime, diastate,diaImgExtName)"+
					"VALUES (DIARY_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";
	private static final String GETALL = 
			"SELECT DIANO,MEMNO,DIANAME, DIATEXT, DIAIMG, DIACRETIME, DIAMODTIME, DIASTATE,diaImgExtName FROM DIARY";
//			"SELECT DIANO,MEMNO,DIANAME, DIATEXT, DIAIMG, TO_CHAR(DIACREDATE,'YYYY-MM-DD HH12:MI:SS AM') DIACREDATE, TO_CHAR(DIAMODDATE,'YYYY-MM-DD HH12:MI:SS AM') DIAMODDATE, DIASTATE FROM DIARY";
	private static final String GETONE =
			"SELECT DIANO,MEMNO,DIANAME, DIATEXT, DIAIMG, DIACRETIME, DIAMODTIME, DIASTATE,diaImgExtName FROM DIARY WHERE DIANO = ? ";
	private static final String UPDATE =
			"UPDATE DIARY SET DIANAME=?,DIATEXT=?,DIAIMG=?,diamodtime=?,diaImgExtName=? WHERE DIANO=?";
	private static final String DELETE=
			"DELETE FROM DIARY WHERE DIANO=?";	
	
	
	@Override
	public void insert(Diary diary) {
		
		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob=null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT);			
			clob =con.createClob();
			clob.setString(1, diary.getDiaText());
			
			pstmt.setInt(1, diary.getMemNo());
			pstmt.setString(2, diary.getDiaName());
			pstmt.setClob(3, clob);
			
			if(diary.getDiaImg()!=null)
				pstmt.setBytes(4, diary.getDiaImg());
			else
				pstmt.setBytes(4, diary.getDiaImg());
			
			pstmt.setTimestamp(5, diary.getDiaCreTime());
			pstmt.setTimestamp(6, diary.getDiaModTime());
			pstmt.setInt(7, diary.getDiaState());
			pstmt.setString(8, diary.getDiaImgExtName());
			
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
	public void update(Diary diary) {
		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob=null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			
			pstmt = con.prepareStatement(UPDATE);	
			
			clob =con.createClob();
			clob.setString(1, diary.getDiaText());
			
			
			pstmt.setString(1, diary.getDiaName());
			pstmt.setClob(2, clob);
			pstmt.setBytes(3, diary.getDiaImg());
			pstmt.setTimestamp(4, diary.getDiaModTime());
			pstmt.setInt(6, diary.getDiaNo());
			pstmt.setString(5, diary.getDiaImgExtName());
			
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
	public void delete(Integer diaNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, diaNo);
			
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	}

	@Override
	public Diary findByPrimaryKey(Integer diaNo) {

		Diary diary = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONE);

			pstmt.setInt(1, diaNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo ¤]ºÙ¬° Domain objects
				diary = new Diary();
				diary.setDiaNo(rs.getInt("diano"));
				diary.setMemNo(rs.getInt("memno"));
				diary.setDiaName(rs.getString("dianame"));
				diary.setDiaText(rs.getString("diatext"));
				diary.setDiaImg(rs.getBytes("diaimg"));
				diary.setDiaCreTime(rs.getTimestamp("diacretime"));
				diary.setDiaModTime(rs.getTimestamp("diamodtime"));
				diary.setDiaState(rs.getInt("diastate"));
				diary.setDiaImgExtName(rs.getString("diaImgExtName"));
				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
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
		return diary;
	}

	@Override
	public List<Diary> getAll() {
		
		List<Diary> list = new ArrayList<Diary>();
		Diary diary = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				diary= new Diary();
				diary.setDiaNo(rs.getInt("diano"));
				diary.setMemNo(rs.getInt("memno"));
				diary.setDiaName(rs.getString("dianame"));
				diary.setDiaText(rs.getString("diatext"));
				diary.setDiaImg(rs.getBytes("diaimg"));
				diary.setDiaCreTime(rs.getTimestamp("diacretime"));
				diary.setDiaModTime(rs.getTimestamp("diamodtime"));
				diary.setDiaState(rs.getInt("diastate"));
				diary.setDiaImgExtName(rs.getString("diaImgExtName"));
				list.add(diary);
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
	
	@Override
	public List<Diary> getOneMemNo(Integer memNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
