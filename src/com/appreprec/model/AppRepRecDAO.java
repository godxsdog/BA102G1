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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.UnavailableException;
import javax.sql.DataSource;

public class AppRepRecDAO implements AppRepRec_Interface{

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
			"INSERT INTO APPREPREC(MEMNO,RECDATE) VALUES (?,?)";
	private static final String GETALL = 
			"SELECT MEMNO,RECDATE FROM APPREPREC ORDER BY MEMNO";
	private static final String GETONE =
			"SELECT MEMNO,RECDATE FROM APPREPREC WHERE MEMNO = ?";
	private static final String DELETE=
			"DELETE FROM APPREPREC WHERE MEMNO = ? AND RECDATE = ?";
	private static final String GETONEMEMTIMES =
			"select rownum,memno,recdate from(select memno,recdate from appreprec where memno=? order by recdate desc) where rownum <=3";
	
	
	@Override
	public void insert(AppRepRec appRepRec) {
		
		Connection con =null;
		PreparedStatement pstmt =null;
		try{
			con =ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1,appRepRec.getMemNo());
			pstmt.setDate(2, appRepRec.getRecDate());
			
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
	public void delete(AppRepRec appRepRec) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			con =ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1,appRepRec.getMemNo());
			pstmt.setDate(2, appRepRec.getRecDate());
			
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
	public List<AppRepRec> findByPrimaryKey(Integer memNo) {
		
		List<AppRepRec> list = new ArrayList<AppRepRec>();
		AppRepRec appRepRec =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con =ds.getConnection();
			pstmt = con.prepareStatement(GETONE);
			pstmt.setInt(1,memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				appRepRec = new AppRepRec();
				appRepRec.setMemNo(rs.getInt("memno"));
				appRepRec.setRecDate(rs.getDate("recdate"));
				list.add(appRepRec);
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

	@Override
	public List<AppRepRec> getAll() {
		
		List<AppRepRec> list = new ArrayList<AppRepRec>();
		AppRepRec appRepRec =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con =ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				appRepRec = new AppRepRec();
				appRepRec.setMemNo(rs.getInt("memno"));
				appRepRec.setRecDate(rs.getDate("recdate"));
				list.add(appRepRec);
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

	@Override
	public Integer findOneMonthTimes(Integer memNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer rows = 0; 
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETONEMEMTIMES);
			Calendar nextrow = null;			
			pstmt.setInt(1,memNo);
			rs = pstmt.executeQuery();
			
			Calendar cal = new GregorianCalendar();			
			cal.setTimeInMillis(System.currentTimeMillis());
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)-30);//找出以上架當天前30天內的檢舉申訴
			
			while(rs.next()){
				nextrow = new GregorianCalendar();
				nextrow.setTime(rs.getDate("recdate"));
				if(nextrow.getTimeInMillis() > cal.getTimeInMillis() ){
					rows++;
				}	
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
		return rows;
	}

}
