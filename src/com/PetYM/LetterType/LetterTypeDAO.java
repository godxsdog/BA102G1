package com.PetYM.LetterType;

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

public class LetterTypeDAO implements LetterType_Interface{

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
			"INSERT INTO LETTERTYPE(LETTERTYPENO, LETTERTYPENAME , LETTERTYPETEXT ) VALUES (LETTERTYPE_SEQ.NEXTVAL,?,?)";
	private static final String GETALL = 
			"SELECT LETTERTYPENO, LETTERTYPENAME , LETTERTYPETEXT FROM LETTERTYPE ORDER BY LETTERTYPENO";
	private static final String GETONE =
			"SELECT LETTERTYPENO, LETTERTYPENAME , LETTERTYPETEXT FROM LETTERTYPE WHERE LETTERTYPENO = ?";
	private static final String UPDATE =
			"UPDATE LETTERTYPE SET LETTERTYPENAME=? , LETTERTYPETEXT=? WHERE LETTERTYPENO = ?";
	private static final String DELETE=
			"DELETE FROM LETTERTYPE WHERE LETTERTYPENO = ?";
	
	
	@Override
	public void insert(LetterType letterType) {

		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob =null;
		try{
			con = ds.getConnection();
			clob = con.createClob();
			clob.setString(1, letterType.getLetterTypeText());
			
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, letterType.getLetterTypeName());
			pstmt.setClob(2, clob);
			
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
	public void update(LetterType letterType) {

		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob = null;
		try{
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);
			clob = con.createClob();
			clob.setString(1, letterType.getLetterTypeText());
			
			pstmt.setString(1, letterType.getLetterTypeName());
			pstmt.setClob(2, clob);
			pstmt.setInt(3, letterType.getLetterTypeNo());
			
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
	public void delete(Integer letterTypeNo) {

		Connection con = null;
		PreparedStatement pstmt =null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, letterTypeNo);
			
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
	public LetterType findByPrimaryKey(Integer letterTypeNo) {

		LetterType letterType =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETONE);
			
			pstmt.setInt(1, letterTypeNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				letterType = new LetterType();
				letterType.setLetterTypeNo(rs.getInt("lettertypeno"));
				letterType.setLetterTypeName(rs.getString("lettertypename"));
				letterType.setLetterTypeText(rs.getString("lettertypetext"));
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
		return letterType;
	}

	@Override
	public List<LetterType> getAll() {

		List<LetterType> list = new ArrayList<LetterType>();
		LetterType letterType = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
		
			while(rs.next()){
				letterType = new LetterType();
				letterType.setLetterTypeNo(rs.getInt("lettertypeno"));
				letterType.setLetterTypeName(rs.getString("lettertypename"));
				letterType.setLetterTypeText(rs.getString("lettertypetext"));
				list.add(letterType);
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
