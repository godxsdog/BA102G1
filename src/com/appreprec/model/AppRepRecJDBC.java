package com.appreprec.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AppRepRecJDBC implements AppRepRec_Interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "petym";
	String passwd = "123456";
	
	private static final String INSERT = 
			"INSERT INTO APPREPREC(MEMNO,RECDATE) VALUES (?,?)";
	private static final String GETALL = 
			"SELECT MEMNO,RECDATE FROM APPREPREC ORDER BY MEMNO";
	private static final String GETONE =
			"SELECT MEMNO,RECDATE FROM APPREPREC WHERE MEMNO = ?";
	private static final String GETONEMEMTIMES =
			"select memno,recdate from appreprec where memno=? order by recdate desc";
	private static final String DELETE=
			"DELETE FROM APPREPREC WHERE MEMNO = ? AND RECDATE = ?";
	
	
	
	@Override
	public void insert(AppRepRec appRepRec) {
		
		Connection con =null;
		PreparedStatement pstmt =null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1,appRepRec.getMemNo());
			pstmt.setDate(2, appRepRec.getRecDate());
			
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
	public void delete(AppRepRec appRepRec) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1,appRepRec.getMemNo());
			pstmt.setDate(2, appRepRec.getRecDate());
			
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
	public List<AppRepRec> findByPrimaryKey(Integer memNo) {
		
		List<AppRepRec> list = new ArrayList<AppRepRec>();
		AppRepRec appRepRec =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONE);
			pstmt.setInt(1,memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				appRepRec = new AppRepRec();
				appRepRec.setMemNo(rs.getInt("memno"));
				appRepRec.setRecDate(rs.getDate("recdate"));
				list.add(appRepRec);
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

	@Override
	public List<AppRepRec> getAll() {
		
		List<AppRepRec> list = new ArrayList<AppRepRec>();
		AppRepRec appRepRec =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				appRepRec = new AppRepRec();
				appRepRec.setMemNo(rs.getInt("memno"));
				appRepRec.setRecDate(rs.getDate("recdate"));
				list.add(appRepRec);
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

		AppRepRecJDBC dao = new AppRepRecJDBC();
		
		//add
//		AppRepRec appRepRec = new AppRepRec();
//		appRepRec.setMemNo(5004);
//		appRepRec.setRecDate(java.sql.Date.valueOf("2012-5-7"));
//		dao.insert(appRepRec);
		
		//delete
//		AppRepRec appRepRec = new AppRepRec();
//		appRepRec.setMemNo(5004);
//		appRepRec.setRecDate(java.sql.Date.valueOf("2012-5-7"));
//		dao.delete(appRepRec);
		
		//query 1
//		List<AppRepRec> list =dao.findByPrimaryKey(5001);
//		for(AppRepRec appRepRec : list){
//			System.out.print(appRepRec.getMemNo()+", ");
//			System.out.print(appRepRec.getRecDate());
//			System.out.println();
//		}
		
		
		//query all 
//		List<AppRepRec> list =dao.getAll();
//		for(AppRepRec appRepRec : list){
//			System.out.print(appRepRec.getMemNo()+", ");
//			System.out.print(appRepRec.getRecDate());
//			System.out.println();
//		}
		
		//query 1
		Integer rows =dao.findOneMonthTimes(5001);
		
			System.out.print(rows);
			
		
		
		
		
		
	}


	@Override
	public Integer findOneMonthTimes(Integer memNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer rows = 0; 
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONEMEMTIMES);
			Calendar nextrow = null;			
			pstmt.setInt(1,memNo);
			rs = pstmt.executeQuery();
			
			Calendar cal = new GregorianCalendar();			
			cal.setTimeInMillis(System.currentTimeMillis());
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)-30);//找出以上架當天前30天內的檢舉申訴
			System.out.println(cal.getTime());
			while(rs.next()){
				nextrow = new GregorianCalendar();
				nextrow.setTime(rs.getDate("recdate"));
				if(nextrow.getTimeInMillis() > cal.getTimeInMillis() ){
					rows++;
				}	
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
		return rows;
	}






	
	
	
	
	

}
