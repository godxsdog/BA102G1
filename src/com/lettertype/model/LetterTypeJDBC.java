package com.lettertype.model;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LetterTypeJDBC implements LetterType_Interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "petym";
	String passwd = "123456";
	
	
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
		
		Connection con =null;
		PreparedStatement pstmt =null;
		Clob clob =null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			clob = con.createClob();
			clob.setString(1, letterType.getLetterTypeText());
			
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, letterType.getLetterTypeName());
			pstmt.setClob(2, clob);
			
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
	public void update(LetterType letterType) {
		
		Connection con = null;
		PreparedStatement pstmt =null;
		Clob clob = null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			
			pstmt = con.prepareStatement(UPDATE);
			clob = con.createClob();
			clob.setString(1, letterType.getLetterTypeText());
			
			pstmt.setString(1, letterType.getLetterTypeName());
			pstmt.setClob(2, clob);
			pstmt.setInt(3, letterType.getLetterTypeNo());
			
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
	public void delete(Integer letterTypeNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, letterTypeNo);
			
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
	public LetterType findByPrimaryKey(Integer letterTypeNo) {
		
		LetterType letterType =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONE);
			
			pstmt.setInt(1, letterTypeNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				letterType = new LetterType();
				letterType.setLetterTypeNo(rs.getInt("lettertypeno"));
				letterType.setLetterTypeName(rs.getString("lettertypename"));
				letterType.setLetterTypeText(rs.getString("lettertypetext"));
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
		
			while(rs.next()){
				letterType = new LetterType();
				letterType.setLetterTypeNo(rs.getInt("lettertypeno"));
				letterType.setLetterTypeName(rs.getString("lettertypename"));
				letterType.setLetterTypeText(rs.getString("lettertypetext"));
				list.add(letterType);
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
		
		LetterTypeJDBC dao = new LetterTypeJDBC();
		
		//add
//		LetterType letpe = new LetterType();
//		letpe.setLetterTypeName("商城壞掉");
//		letpe.setLetterTypeText("由於員工罷工所以商城壞掉");
//		dao.insert(letpe);
		
		//MODIFY
//		LetterType letpe = new LetterType();
//		letpe.setLetterTypeNo(30006);
//		letpe.setLetterTypeName("測試壞掉");
//		letpe.setLetterTypeText("由於測試罷公");
//		dao.update(letpe);
		
		
		//delete
//		dao.delete(30006);
		
		
		//query 1
//		LetterType lettertype =dao.findByPrimaryKey(30005);
//		System.out.print(lettertype.getLetterTypeNo()+", ");
//		System.out.print(lettertype.getLetterTypeName()+", ");
//		System.out.print(lettertype.getLetterTypeText());
//		System.out.println();
		
		//query all
		List<LetterType> list = dao.getAll();
		for(LetterType lettertype : list){
			System.out.print(lettertype.getLetterTypeNo()+", ");
			System.out.print(lettertype.getLetterTypeName()+", ");
			System.out.print(lettertype.getLetterTypeText());
			System.out.println();
		}
		
		
	}

	@Override
	public Integer getLetterTypeNo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
