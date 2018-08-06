package com.dateitemrep.model;

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

public class DateItemRepDAO implements DateItemRep_Interface{

	
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
			"INSERT INTO DATEITEMREP(REPNO, MEMNO, DATEITEMNO , REPTEXT,REPDATE, REPSTATE ) VALUES (DATEITEMREP_SEQ.NEXTVAL,?,?,?,?,?)";
	private static final String GETALL = 
			"SELECT REPNO, MEMNO, DATEITEMNO , REPTEXT,REPDATE, REPSTATE FROM DATEITEMREP ORDER BY REPDATE";
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
			con =ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1,dateItemRep.getMemNo());
			pstmt.setInt(2, dateItemRep.getDateItemNo());
			pstmt.setString(3, dateItemRep.getRepText());
			pstmt.setDate(4, dateItemRep.getRepDate());
			pstmt.setInt(5, dateItemRep.getRepState());
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
	public void update(DateItemRep dateItemRep) {

		Connection con = null;
		PreparedStatement pstmt =null;
		
		try{
			con =ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, dateItemRep.getRepText());
			pstmt.setDate(2, dateItemRep.getRepDate());
			pstmt.setInt(3, dateItemRep.getRepState());
			pstmt.setInt(4, dateItemRep.getRepNo());
			
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
	public void delete(Integer repNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			con =ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1,repNo);
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
	public DateItemRep findByPrimaryKey(Integer repNo) {
		
		DateItemRep dateItemRep =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con =ds.getConnection();
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
			
			con =ds.getConnection();
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
