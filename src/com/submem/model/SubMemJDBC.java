package com.submem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubMemJDBC implements SubMem_Interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "petym";
	String passwd = "123456";
	
	private static final String INSERT = 
			"INSERT INTO SUBMEM(ACTSUBMEMNO, BESUBMEMNO, SUBSTATE) VALUES (?,?,?)";
	private static final String GETALL = 
			"SELECT ACTSUBMEMNO, BESUBMEMNO, SUBSTATE FROM SUBMEM ORDER BY ACTSUBMEMNO";
	private static final String GETONE =
			"SELECT ACTSUBMEMNO, BESUBMEMNO, SUBSTATE FROM SUBMEM WHERE ACTSUBMEMNO = ?";
	private static final String UPDATE =
			"UPDATE SUBMEM SET SUBSTATE=? WHERE ACTSUBMEMNO = ? AND BESUBMEMNO = ?";
	private static final String DELETE=
			"DELETE FROM SUBMEM WHERE ACTSUBMEMNO = ? AND BESUBMEMNO = ?";

	@Override
	public void insert(SubMem submem) {
			
			Connection con =null;
			PreparedStatement pstmt =null;
			try{
				Class.forName(driver);
				con = DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(INSERT);
				pstmt.setInt(1, submem.getActSubMemNo());
				pstmt.setInt(2, submem.getBeSubMemNo());
				pstmt.setInt(3, submem.getSubState());
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
	public void update(SubMem submem) {
		Connection con = null;
		PreparedStatement pstmt =null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, submem.getSubState());
			pstmt.setInt(2, submem.getActSubMemNo());
			pstmt.setInt(3, submem.getBeSubMemNo());
			
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
	public void delete(SubMem submem) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, submem.getActSubMemNo());
			pstmt.setInt(2, submem.getBeSubMemNo());
			
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
	public List<SubMem> findByPrimaryKey(Integer actSubMemNo) {
		
		List<SubMem> list =new ArrayList<SubMem>();
		SubMem submem = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONE);
			pstmt.setInt(1, actSubMemNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				submem = new SubMem();
				submem.setActSubMemNo(rs.getInt("actsubmemno"));
				submem.setBeSubMemNo(rs.getInt("besubmemno"));
				submem.setSubState(rs.getInt("substate"));
				list.add(submem);
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
	public List<SubMem> getAll() {

		List<SubMem> list =new ArrayList<SubMem>();
		SubMem submem = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				submem = new SubMem();
				submem.setActSubMemNo(rs.getInt("actsubmemno"));
				submem.setBeSubMemNo(rs.getInt("besubmemno"));
				submem.setSubState(rs.getInt("substate"));
				list.add(submem);
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

		SubMemJDBC dao = new SubMemJDBC();
		
		//add
//		SubMem submem = new SubMem();
//		submem.setActSubMemNo(5001);
//		submem.setBeSubMemNo(5005);
//		submem.setSubState(0);
//		dao.insert(submem);
		
		//UPDATE	
//		SubMem submem1 = new SubMem();
//		submem1.setSubState(1);
//		submem1.setActSubMemNo(5001);
//		submem1.setBeSubMemNo(5005);
//		dao.update(submem1);
		
		//delete
//		SubMem submem2 = new SubMem();
//		submem2.setActSubMemNo(5001);
//		submem2.setBeSubMemNo(5005);
//		dao.delete(submem2);
		
		//query 1
		List<SubMem> list = dao.findByPrimaryKey(5006);
		for(SubMem submem:list){
			System.out.print(submem.getActSubMemNo()+", ");
			System.out.print(submem.getBeSubMemNo()+", ");
			System.out.print(submem.getSubState());
			System.out.println();
			System.out.println("-----------------------");
		}
		
		//query all
//		List<SubMem> list = dao.getAll();
//		for(SubMem submem:list){
//			System.out.print(submem.getActSubMemNo()+", ");
//			System.out.print(submem.getBeSubMemNo()+", ");
//			System.out.print(submem.getSubState());
//			System.out.println();
//			System.out.println("-----------------------");
//		}
		
	}

}
