package com.dateitemapp.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.UnavailableException;
import javax.sql.DataSource;

public class DateItemAppDAO implements DateItemApp_Interface{

	
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
			"INSERT INTO DATEITEMAPP(APPNO, MEMNO, DATEITEMNO, APPTITLE, APPTEXT, APPDATE, APPSTATE)"+
					"VALUES (DATEITEMAPP_SEQ.NEXTVAL,?,?,?,?,?,?)";
	private static final String GETALL = 
			"SELECT APPNO, MEMNO, DATEITEMNO, APPTITLE, APPTEXT, APPDATE, APPSTATE FROM DATEITEMAPP ORDER BY APPDATE";
	private static final String GETONE =
			"SELECT APPNO, MEMNO, DATEITEMNO, APPTITLE, APPTEXT, APPDATE, APPSTATE FROM DATEITEMAPP WHERE APPNO = ? ";
	private static final String UPDATE =
			"UPDATE DATEITEMAPP SET DATEITEMNO=?, APPTITLE=?, APPTEXT=?, APPDATE=?, APPSTATE=? WHERE APPNO=?";
	private static final String DELETE=
			"DELETE FROM DATEITEMAPP WHERE APPNO=?";
	
	
	
	@Override
	public void insert(DateItemApp dateItemApp) {

		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob=null;
		try{
			con =ds.getConnection();
			pstmt = con.prepareStatement(INSERT);			
			clob =con.createClob();
			clob.setString(1,dateItemApp.getAppText());
			
			pstmt.setInt(1, dateItemApp.getMemNo());
			pstmt.setInt(2, dateItemApp.getDateItemNo());
			pstmt.setString(3, dateItemApp.getAppTitle());
			pstmt.setClob(4, clob);			
			pstmt.setDate(5, dateItemApp.getAppDate());
			pstmt.setInt(6, dateItemApp.getAppState());
			
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
	public void update(DateItemApp dateItemApp) {

		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob=null;
		
		try{
			con =ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);	
			clob =con.createClob();
			clob.setString(1,dateItemApp.getAppText());
			
			pstmt.setInt(1, dateItemApp.getDateItemNo());
			pstmt.setString(2, dateItemApp.getAppTitle());
			pstmt.setClob(3, clob);			
			pstmt.setDate(4, dateItemApp.getAppDate());
			pstmt.setInt(5, dateItemApp.getAppState());
			pstmt.setInt(6, dateItemApp.getAppNo());
			
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
	public void delete(Integer appNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			con =ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, appNo);
			
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
	public DateItemApp findByPrimaryKey(Integer appNo) {

		DateItemApp dateItemApp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con =ds.getConnection();
			pstmt = con.prepareStatement(GETONE);

			pstmt.setInt(1, appNo);

			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dateItemApp = new DateItemApp();
				dateItemApp.setAppNo(rs.getInt("appno"));
				dateItemApp.setMemNo(rs.getInt("memno"));
				dateItemApp.setDateItemNo(rs.getInt("dateitemno"));
				dateItemApp.setAppTitle(rs.getString("apptitle"));
				dateItemApp.setAppText(rs.getString("apptext"));
				dateItemApp.setAppDate(rs.getDate("appdate"));
				dateItemApp.setAppState(rs.getInt("appstate"));
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
		return dateItemApp;
	}

	@Override
	public List<DateItemApp> getAll() {
		
		List<DateItemApp> list  = new ArrayList<DateItemApp>();
		DateItemApp dateItemApp =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			con =ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dateItemApp = new DateItemApp();
				dateItemApp.setAppNo(rs.getInt("appno"));
				dateItemApp.setMemNo(rs.getInt("memno"));
				dateItemApp.setDateItemNo(rs.getInt("dateitemno"));
				dateItemApp.setAppTitle(rs.getString("apptitle"));
				dateItemApp.setAppText(rs.getString("apptext"));
				dateItemApp.setAppDate(rs.getDate("appdate"));
				dateItemApp.setAppState(rs.getInt("appstate"));
				list.add(dateItemApp);
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
		return list;
	}

}
