package com.dateitemrep.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class DateItemRepJDBC implements DateItemRep_Interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "petym";
	String passwd = "123456";
	
	
	private static final String INSERT = 
			"INSERT INTO DATEITEMREP(REPNO, MEMNO, DATEITEMNO , REPTEXT,REPDATE, REPSTATE ) VALUES (DATEITEMREP_SEQ.NEXTVAL,?,?,?,?,?)";
	private static final String GETALL = 
			"SELECT REPNO, MEMNO, DATEITEMNO , REPTEXT,REPDATE, REPSTATE FROM DATEITEMREP ORDER BY REPNO";
	private static final String GETONE =
			"SELECT REPNO, MEMNO, DATEITEMNO , REPTEXT,REPDATE, REPSTATE FROM DATEITEMREP WHERE REPNO = ?";
	private static final String UPDATE =
			"UPDATE DATEITEMREP SET  REPTEXT=?, REPDATE=? , REPSTATE=?  WHERE REPNO = ?";
	private static final String DELETE=
			"DELETE FROM DATEITEMREP WHERE REPNO = ?";
	
	
	@Override
	public void insert(DateItemRep dateItemRep) {

		Connection con =null;
		PreparedStatement pstmt =null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1,dateItemRep.getMemNo());
			pstmt.setInt(2, dateItemRep.getDateItemNo());
			pstmt.setString(3, dateItemRep.getRepText());
			pstmt.setDate(4, dateItemRep.getRepDate());
			pstmt.setInt(5, dateItemRep.getRepState());
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
	public void update(DateItemRep dateItemRep) {

		Connection con = null;
		PreparedStatement pstmt =null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, dateItemRep.getRepText());
			pstmt.setDate(2, dateItemRep.getRepDate());
			pstmt.setInt(3, dateItemRep.getRepState());
			pstmt.setInt(4, dateItemRep.getRepNo());
			
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
	public void delete(Integer repNo) {
		
	
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1,repNo);
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
	public DateItemRep findByPrimaryKey(Integer repNo) {

		
		DateItemRep dateItemRep =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONE);
			
			pstmt.setInt(1,repNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dateItemRep = new DateItemRep();
				
				dateItemRep.setRepNo(rs.getInt("repno"));
				dateItemRep.setMemno(rs.getInt("memno"));
				dateItemRep.setDateItemNo(rs.getInt("dateitemno"));
				dateItemRep.setRepText(rs.getString("reptext"));
				dateItemRep.setRepDate(rs.getDate("repdate"));
				dateItemRep.setRepState(rs.getInt("repstate"));
				
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
		return dateItemRep;
		
	}


	@Override
	public List<DateItemRep> getAll() {

		List<DateItemRep> list = new ArrayList<DateItemRep>();
		DateItemRep dateItemRep = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dateItemRep = new DateItemRep();
				dateItemRep.setRepNo(rs.getInt("repno"));
				dateItemRep.setMemno(rs.getInt("memno"));
				dateItemRep.setDateItemNo(rs.getInt("dateitemno"));
				dateItemRep.setRepText(rs.getString("reptext"));
				dateItemRep.setRepDate(rs.getDate("repdate"));
				dateItemRep.setRepState(rs.getInt("repstate"));
				list.add(dateItemRep);
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

		DateItemRepJDBC dao = new DateItemRepJDBC();
		
		
		//add
//		DateItemRep dateItemRep = new DateItemRep();
//		dateItemRep.setMemno(5001);
//		dateItemRep.setDateItemNo(4001);
//		dateItemRep.setRepText("太難看了");
//		dateItemRep.setRepDate(java.sql.Date.valueOf("2011-11-3"));
//		dateItemRep.setRepState(0);
//		dao.insert(dateItemRep);
		
		
		//update
//		DateItemRep dateItemRep = new DateItemRep();
//		dateItemRep.setRepNo(50007);
//		dateItemRep.setRepText("哈哈哈哈哈哈ㄎㄎ");
//		dateItemRep.setRepDate(java.sql.Date.valueOf("2012-12-4"));
//		dateItemRep.setRepState(1);
//		dao.update(dateItemRep);
		
		//delete
//		dao.delete(50007);
		
		//query 1
//		DateItemRep dateItemRep = dao.findByPrimaryKey(50003);
//		System.out.print(dateItemRep.getRepNo()+", ");
//		System.out.print(dateItemRep.getMemNo()+", ");
//		System.out.print(dateItemRep.getDateItemNo()+", ");
//		System.out.print(dateItemRep.getRepText()+", ");
//		System.out.print(dateItemRep.getRepDate()+", ");
//		System.out.print(dateItemRep.getRepState()+", ");
//		System.out.println();
		
		
		
		//query all
		List<DateItemRep> list = dao.getAll();
		for(DateItemRep dateItemRep:list){
			System.out.print(dateItemRep.getRepNo()+", ");
			System.out.print(dateItemRep.getMemNo()+", ");
			System.out.print(dateItemRep.getDateItemNo()+", ");
			System.out.print(dateItemRep.getRepText()+", ");
			System.out.print(dateItemRep.getRepDate()+", ");
			System.out.print(dateItemRep.getRepState()+", ");
			System.out.println();
		}
		
		
		
		
		
		
	}







	
	
	
	

}
