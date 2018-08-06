package com.submem.model;

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

public class SubMemDAO implements SubMem_Interface{

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
			"INSERT INTO SUBMEM(ACTSUBMEMNO, BESUBMEMNO, SUBSTATE) VALUES (?,?,?)";
	private static final String GETALL = 
			"SELECT ACTSUBMEMNO, BESUBMEMNO, SUBSTATE FROM SUBMEM ORDER BY ACTSUBMEMNO";
	private static final String GETONE =
			"SELECT ACTSUBMEMNO, BESUBMEMNO, SUBSTATE FROM SUBMEM WHERE ACTSUBMEMNO = ? and SUBSTATE = 0";
	private static final String UPDATE =
			"UPDATE SUBMEM SET SUBSTATE=? WHERE ACTSUBMEMNO = ? AND BESUBMEMNO = ?";
	private static final String DELETE=
			"DELETE FROM SUBMEM WHERE ACTSUBMEMNO = ? AND BESUBMEMNO = ?";

	@Override
	public void insert(SubMem subMem) {

		Connection con = null;
		PreparedStatement pstmt =null;
		
		try{
			con =ds.getConnection();
			pstmt = con.prepareStatement(INSERT);	
			pstmt.setInt(1, subMem.getActSubMemNo());
			pstmt.setInt(2, subMem.getBeSubMemNo());
			pstmt.setInt(3, subMem.getSubState());
			pstmt.executeUpdate();
		}catch(SQLException se){	
			//防止取消追蹤後再重新加入會發生ORA-00001: unique constraint (PETYM.SUBMEM_PK) violated
				SubMem submem = new SubMem();
				submem.setActSubMemNo(subMem.getActSubMemNo());
				submem.setBeSubMemNo(subMem.getBeSubMemNo());
				submem.setSubState(0);
				update(submem);	
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
	public void update(SubMem subMem) {
		
		Connection con = null;
		PreparedStatement pstmt =null;		
		
		try{
			con =ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, subMem.getSubState());
			pstmt.setInt(2, subMem.getActSubMemNo());
			pstmt.setInt(3, subMem.getBeSubMemNo());
			
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
	public void delete(SubMem subMem) {

		Connection con = null;
		PreparedStatement pstmt =null;
		
		try{
			
			con =ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, subMem.getActSubMemNo());
			pstmt.setInt(2, subMem.getBeSubMemNo());
			
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
	public List<SubMem> findByPrimaryKey(Integer actSubMemNo) {

		List<SubMem> list =new ArrayList<SubMem>();
		SubMem subMem = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con =ds.getConnection();
			pstmt = con.prepareStatement(GETONE);
			pstmt.setInt(1, actSubMemNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				subMem = new SubMem();
				subMem.setActSubMemNo(rs.getInt("actsubmemno"));
				subMem.setBeSubMemNo(rs.getInt("besubmemno"));
				subMem.setSubState(rs.getInt("substate"));
				list.add(subMem);
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
	public List<SubMem> getAll() {

		List<SubMem> list =new ArrayList<SubMem>();
		SubMem subMem = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con =ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				subMem = new SubMem();
				subMem.setActSubMemNo(rs.getInt("actsubmemno"));
				subMem.setBeSubMemNo(rs.getInt("besubmemno"));
				subMem.setSubState(rs.getInt("substate"));
				list.add(subMem);
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
