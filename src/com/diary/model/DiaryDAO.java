package com.diary.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.UnavailableException;
import javax.sql.DataSource;

import oracle.sql.CLOB;

public class DiaryDAO implements DiaryDAO_Interface{

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
			"INSERT INTO DIARY(diano, memno, dianame, diatext, diaimg, diacretime, diamodtime, diastate,diaImgExtName)"+
					"VALUES (DIARY_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";
	private static final String GETALL = 
			"SELECT DIANO,MEMNO,DIANAME, DIATEXT, DIAIMG, DIACRETIME, DIAMODTIME, DIASTATE, diaImgExtName FROM DIARY WHERE DIASTATE = 0 ORDER BY DIACRETIME DESC";
//			"SELECT DIANO,MEMNO,DIANAME, DIATEXT, DIAIMG, TO_CHAR(DIACREDATE,'YYYY-MM-DD HH12:MI:SS AM') DIACREDATE, TO_CHAR(DIAMODDATE,'YYYY-MM-DD HH12:MI:SS AM') DIAMODDATE, DIASTATE FROM DIARY";
	private static final String GETONE =
			"SELECT DIANO,MEMNO,DIANAME, DIATEXT, DIAIMG, DIACRETIME, DIAMODTIME, DIASTATE,diaImgExtName FROM DIARY WHERE DIANO = ? AND DIASTATE = 0";
	private static final String UPDATE =
			"UPDATE DIARY SET DIANAME=?,DIATEXT=?,DIAIMG=?,diamodtime=?,diaimgextname=? WHERE DIANO=?";
	private static final String DELETE=
			"UPDATE DIARY SET DIASTATE=? WHERE DIANO=?";
	private static final String GETALLFROMMEMNO =
			"SELECT * FROM DIARY WHERE MEMNO = ? AND DIASTATE = 0 ORDER BY DIACRETIME DESC";
	
	@Override
	public void insert(Diary diary) {
			
		Connection con = null;
		PreparedStatement pstmt =null;
//		Base64.Encoder bs64 = Base64.getEncoder();
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);			
		
//			String test = bs64.encodeToString(diary.getDiaImg());
			
			pstmt.setInt(1, diary.getMemNo());
			pstmt.setString(2, diary.getDiaName());
			
			Clob clob =con.createClob();
			clob.setString(1, diary.getDiaText());
			
			pstmt.setClob(3, clob);
			
			if(diary.getDiaImg()!=null)
				pstmt.setBytes(4, diary.getDiaImg());
			else
				pstmt.setBytes(4, null);
			
			pstmt.setTimestamp(5, diary.getDiaCreTime());
			pstmt.setTimestamp(6, diary.getDiaCreTime());
			pstmt.setInt(7, diary.getDiaState());
			pstmt.setString(8, diary.getDiaImgExtName());
			
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
	public void update(Diary diary) {
		
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try{
			
			con = ds.getConnection();
			pstmt =con.prepareStatement(UPDATE);
			
			pstmt.setString(1, diary.getDiaName());
			pstmt.setString(2, diary.getDiaText());
			pstmt.setBytes(3, diary.getDiaImg());
			pstmt.setTimestamp(4, diary.getDiaModTime());
			pstmt.setString(5, diary.getDiaImgExtName());
			pstmt.setInt(6, diary.getDiaNo());
			
			pstmt.executeUpdate();
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			if(pstmt!=null){
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			if(con!=null){
				try{
					con.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(Integer diaNo) {

		Connection con = null;
		PreparedStatement pstmt= null;
		
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, 1);
			pstmt.setInt(2, diaNo);
			
			pstmt.executeUpdate();
		}catch (SQLException se) {
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
		ResultSet rs =null;
		
		try{
			con =ds.getConnection();
			pstmt =con.prepareStatement(GETONE);
			pstmt.setInt(1, diaNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
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
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				if(rs!=null){
					try{
						rs.close();
					}catch(SQLException e){
						e.printStackTrace();
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
		Diary diaryVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				diaryVO= new Diary();
				diaryVO.setDiaNo(rs.getInt("diano"));
				diaryVO.setMemNo(rs.getInt("memno"));
				diaryVO.setDiaName(rs.getString("dianame"));
				diaryVO.setDiaText(rs.getString("diatext"));
				diaryVO.setDiaImg(rs.getBytes("diaimg"));
				diaryVO.setDiaCreTime(rs.getTimestamp("diacretime"));
				diaryVO.setDiaModTime(rs.getTimestamp("diamodtime"));
				diaryVO.setDiaState(rs.getInt("diastate"));
				diaryVO.setDiaImgExtName(rs.getString("diaImgExtName"));
				
				list.add(diaryVO);
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
	public List<Diary> getOneMemNo(Integer memNo) {
		
		List<Diary> list = new ArrayList<Diary>();
		Diary diaryVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALLFROMMEMNO);
			pstmt.setInt(1, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				diaryVO= new Diary();
				diaryVO.setDiaNo(rs.getInt("diano"));
				diaryVO.setMemNo(rs.getInt("memno"));
				diaryVO.setDiaName(rs.getString("dianame"));
				diaryVO.setDiaText(rs.getString("diatext"));
				diaryVO.setDiaImg(rs.getBytes("diaimg"));
				diaryVO.setDiaCreTime(rs.getTimestamp("diacretime"));
				diaryVO.setDiaModTime(rs.getTimestamp("diamodtime"));
				diaryVO.setDiaState(rs.getInt("diastate"));
				diaryVO.setDiaImgExtName(rs.getString("diaImgExtName"));
				
				list.add(diaryVO);
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
