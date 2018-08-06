package com.dateitemapp.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DateItemAppJDBC implements DateItemApp_Interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "petym";
	String passwd = "123456";
	
	private static final String INSERT = 
			"INSERT INTO DATEITEMAPP(APPNO, MEMNO, DATEITEMNO, APPTITLE, APPTEXT, APPDATE, APPSTATE)"+
					"VALUES (DATEITEMAPP_SEQ.NEXTVAL,?,?,?,?,?,?)";
	private static final String GETALL = 
			"SELECT APPNO, MEMNO, DATEITEMNO, APPTITLE, APPTEXT, APPDATE, APPSTATE FROM DATEITEMAPP";
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
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
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
	public void update(DateItemApp dateItemApp) {

		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob=null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			
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
	public void delete(Integer appNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, appNo);
			
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
	public DateItemApp findByPrimaryKey(Integer appNo) {

		DateItemApp dateItemApp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		return list;
	}
	
	public static void main(String[] args) {

		DateItemAppJDBC dao = new DateItemAppJDBC();
		
		//add
//		DateItemApp dateitemapp = new DateItemApp();
//		dateitemapp.setMemNo(5008);
//		dateitemapp.setDateItemNo(4007);
//		dateitemapp.setAppText("我好想肥家");
//		dateitemapp.setAppTitle("今天星期五");
//		
//		dateitemapp.setAppDate( java.sql.Date.valueOf("2014-3-21"));
//		dateitemapp.setAppState(0);
//		dao.insert(dateitemapp);
		
		//update
//		DateItemApp dateitemapp = new DateItemApp();
//		dateitemapp.setAppNo(60006);
//		dateitemapp.setDateItemNo(4003);
//		dateitemapp.setAppText("家");
//		dateitemapp.setAppTitle("星期五");
//		
//		dateitemapp.setAppDate( java.sql.Date.valueOf("2012-3-5"));
//		dateitemapp.setAppState(1);
//		dao.update(dateitemapp);
		
		//delete
//		dao.delete(60006);
		
		//query 1
//		DateItemApp dateitemapp = dao.findByPrimaryKey(60003);
//		System.out.print(dateitemapp.getAppNo()+" ,");
//		System.out.print(dateitemapp.getMemNo()+" ,");
//		System.out.print(dateitemapp.getDateItemNo()+" ,");
//		System.out.print(dateitemapp.getAppTitle()+" ,");
//		System.out.print(dateitemapp.getAppText()+" ,");
//		System.out.print(dateitemapp.getAppDate()+" ,");
//		
//		System.out.print(dateitemapp.getAppState());
//		System.out.println();
		
		
		//query all
		List<DateItemApp> list = dao.getAll();
		for(DateItemApp dateitemapp:list){
			System.out.print(dateitemapp.getAppNo()+" ,");
			System.out.print(dateitemapp.getMemNo()+" ,");
			System.out.print(dateitemapp.getDateItemNo()+" ,");
			System.out.print(dateitemapp.getAppTitle()+" ,");
			System.out.print(dateitemapp.getAppText()+" ,");
			System.out.print(dateitemapp.getAppDate()+" ,");
			
			System.out.print(dateitemapp.getAppState());
			System.out.println();
		}
		
		
		
		
	}
	

}
