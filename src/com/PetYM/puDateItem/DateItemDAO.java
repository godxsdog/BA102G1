package com.PetYM.puDateItem;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import sun.awt.dnd.SunDragSourceContextPeer;

import java.sql.Clob;

public class DateItemDAO implements DateItemDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO DATEITEM(DATEITEMNO,SELLERNO,RESTLISTNO,"
			+ "DATEITEMTITLE,DATEITEMIMG,DATEITEMTEXT,DATEITEMTIME,DATEMEETINGTIME,DATEITEMLOCATE,"
			+ "DATEITEMPEOPLE,HASMATE,DATEITEMPRICE,DATEITEMSTATUS,DATEITEMSHOW,DATEITEMVIEWER,BUYERNO,"
			+ "ISQRCCHECKED,BUYERREP,SELLERREP,ISINSTANTDATE,PETNO)"
			+ " VALUES(DATEITEMNO_SQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE DATEITEM SET DATEITEMNO = ?, SELLERNO = ?, RESTLISTNO = ?, "
			+ "DATEITEMTITLE = ?, DATEITEMIMG = ?, DATEITEMTEXT = ?, DATEITEMTIME = ?, DATEMEETINGTIME = ?, "
			+ "DATEITEMLOCATE = ? ,DATEITEMPEOPLE = ? , HASMATE =?, DATEITEMPRICE =? , DATEITEMSTATUS=? ,"
			+ "DATEITEMSHOW=?, DATEITEMVIEWER=?, BUYERNO=?, ISQRCCHECKED=?, BUYERREP=? , SELLERREP=?, ISINSTANTDATE=?, PETNO=? WHERE DATEITEMNO =¡@?";
	private static final String UPDATE_STMT_NOPIC = "UPDATE DATEITEM SET DATEITEMNO = ?, SELLERNO = ?, RESTLISTNO = ?, "
			+ "DATEITEMTITLE = ?, DATEITEMTEXT = ?, DATEITEMTIME = ?, DATEMEETINGTIME = ?, "
			+ "DATEITEMLOCATE = ? ,DATEITEMPEOPLE = ? , HASMATE =?, DATEITEMPRICE =? , DATEITEMSTATUS=? ,"
			+ "DATEITEMSHOW=?, DATEITEMVIEWER=?, BUYERNO=?, ISQRCCHECKED=?, BUYERREP=? , SELLERREP=?, ISINSTANTDATE=?, PETNO=? WHERE DATEITEMNO =¡@?";
	private static final String UPDATE_STMT1 = "UPDATE DATEITEM SET DATEITEMIMG = ? WHERE DATEITEMNO =¡@?";
	private static final String DELETE_STMT = "DELETE FROM DATEITEM WHERE DATEITEMNO = ?";
	private static final String FIND_BY_PK = "SELECT * FROM DATEITEM WHERE DATEITEMNO = ?";
	private static final String FIND_DATEITEMIMG_BY_PK = "SELECT DATEITEMIMG FROM DATEITEM WHERE DATEITEMNO = ?";
	private static final String GET_ALL = "SELECT * FROM DATEITEM ORDER BY DATEITEMNO DESC";
	private static final String FIND_BY_BUYER = "SELECT * FROM DATEITEM WHERE BUYERNO =? ORDER BY DATEITEMNO DESC";
	private static final String FIND_BY_SELLER = "SELECT * FROM DATEITEM WHERE SELLERNO =? ORDER BY DATEITEMNO DESC";
	private static final String FIND_BY_FASTDATEITEM = "SELECT * FROM DATEITEM WHERE isInstantDate =1 AND sellerNo  != ? AND dateItemStatus=0 ORDER BY DATEITEMNO DESC";
	private static final String FIND_BY_All = "select * from dateitem where sellerno = ? or buyerno = ? order by datemeetingtime desc";
	private static final String FIND_BY_AllMessage = "select * from dateitem where (sellerno = ? or buyerno = ?)and dateItemStatus!=0 order by datemeetingtime desc";
	private static final String FIND_BY_All_FINDMessageSELLER = "SELECT * FROM DATEITEM WHERE SELLERNO =? and dateitemstatus=1 ORDER BY DATEITEMNO DESC";
	private static final String FIND_PET_TYPE = "select * from dateitem where  petno  in ( select petno from pet where petkind like '%++%');";

	

	public void updatePicture(DateItemVO dateItemVO) {
		PreparedStatement pstmt=null;
		Connection con=null;
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(UPDATE_STMT1);
	
			pstmt.setInt(2, dateItemVO.getDateItemNo());
			
			pstmt.setBytes(1, dateItemVO.getDateItemImg());
			pstmt.executeUpdate();
			System.out.println("update success");
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
	public void add(DateItemVO dateItemVO) {
		PreparedStatement pstmt=null;
		Connection con=null;
		int i,j,k =0;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, dateItemVO.getSellerNo());
			pstmt.setInt(2, dateItemVO.getRestListNo());
			pstmt.setString(3, dateItemVO.getDateItemTitle());
			Blob blob=con.createBlob();
			blob.setBytes(1, dateItemVO.getDateItemImg());
			pstmt.setBlob(4, blob);
			Clob clob=con.createClob();
			clob.setString(1, dateItemVO.getDateItemText());
			pstmt.setClob(5, clob);
			pstmt.setTimestamp(6, dateItemVO.getDateItemTime());
			pstmt.setTimestamp(7, dateItemVO.getDateMeetingTime());
			pstmt.setString(8, dateItemVO.getDateItemLocate());
			pstmt.setInt(9, dateItemVO.getDateItemPeople());
			if(dateItemVO.getHasMate().toString().equals("true")){
				i=0;
			}else{
				i=1;
			}
			pstmt.setInt(10, i);
			pstmt.setInt(11, dateItemVO.getDateItemPrice());
			pstmt.setInt(12, dateItemVO.getDateItemStatus());
			pstmt.setInt(13, dateItemVO.getDateItemShow());
			pstmt.setInt(14, dateItemVO.getDateItemViewer());
			pstmt.setInt(15, dateItemVO.getBuyerNo());
			if(dateItemVO.getIsQRCChecked().toString().equals("false")){
				j=0;
			}else{
				j=1;
			}
			pstmt.setInt(16, j);
			pstmt.setInt(17, dateItemVO.getBuyerRep());
			pstmt.setInt(18, dateItemVO.getSellerRep());
			if(dateItemVO.getIsInstantDate().toString().equals("false")){
				k=0;
			}else{
				k=1;
			}
			pstmt.setInt(19, k);
			pstmt.setInt(20, dateItemVO.getPetNo());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
	public void update(DateItemVO dateItemVO) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, dateItemVO.getDateItemNo());
			pstmt.setInt(2, dateItemVO.getSellerNo());
			pstmt.setInt(3, dateItemVO.getRestListNo());
			pstmt.setString(4, dateItemVO.getDateItemTitle());
			Blob blob=con.createBlob();
			blob.setBytes(1, dateItemVO.getDateItemImg());
			pstmt.setBlob(5, blob);
			Clob clob=con.createClob();
			clob.setString(1, dateItemVO.getDateItemText());
			pstmt.setClob(6, clob);
			pstmt.setTimestamp(7, dateItemVO.getDateItemTime());
			pstmt.setTimestamp(8, dateItemVO.getDateMeetingTime());
			pstmt.setString(9, dateItemVO.getDateItemLocate());
			pstmt.setInt(10, dateItemVO.getDateItemPeople());
			pstmt.setBoolean(11, dateItemVO.getHasMate());
			pstmt.setInt(12, dateItemVO.getDateItemPrice());
			pstmt.setInt(13, dateItemVO.getDateItemStatus());
			pstmt.setInt(14, dateItemVO.getDateItemShow());
			pstmt.setInt(15, dateItemVO.getDateItemViewer());
			pstmt.setInt(16, dateItemVO.getBuyerNo());
			pstmt.setBoolean(17, dateItemVO.getIsQRCChecked());
			pstmt.setInt(18, dateItemVO.getBuyerRep());
			pstmt.setInt(19, dateItemVO.getSellerRep());
			pstmt.setBoolean(20, dateItemVO.getIsInstantDate());
			pstmt.setInt(21, dateItemVO.getPetNo());
			pstmt.setInt(22, dateItemVO.getDateItemNo());
			pstmt.executeUpdate();

		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
	public void updatewithoutPic(DateItemVO dateItemVO) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(UPDATE_STMT_NOPIC);
			pstmt.setInt(1, dateItemVO.getDateItemNo());
			pstmt.setInt(2, dateItemVO.getSellerNo());
			pstmt.setInt(3, dateItemVO.getRestListNo());
			pstmt.setString(4, dateItemVO.getDateItemTitle());
//			Blob blob=con.createBlob();
//			blob.setBytes(1, dateItemVO.getDateItemImg());
//			pstmt.setBlob(5, blob);
			Clob clob=con.createClob();
			clob.setString(1, dateItemVO.getDateItemText());
			pstmt.setClob(5, clob);
			pstmt.setTimestamp(6, dateItemVO.getDateItemTime());
			pstmt.setTimestamp(7, dateItemVO.getDateMeetingTime());
			pstmt.setString(8, dateItemVO.getDateItemLocate());
			pstmt.setInt(9, dateItemVO.getDateItemPeople());
			pstmt.setBoolean(10, dateItemVO.getHasMate());
			pstmt.setInt(11, dateItemVO.getDateItemPrice());
			pstmt.setInt(12, dateItemVO.getDateItemStatus());
			pstmt.setInt(13, dateItemVO.getDateItemShow());
			pstmt.setInt(14, dateItemVO.getDateItemViewer());
			pstmt.setInt(15, dateItemVO.getBuyerNo());
			pstmt.setBoolean(16, dateItemVO.getIsQRCChecked());
			pstmt.setInt(17, dateItemVO.getBuyerRep());
			pstmt.setInt(18, dateItemVO.getSellerRep());
			pstmt.setBoolean(19, dateItemVO.getIsInstantDate());
			pstmt.setInt(20, dateItemVO.getPetNo());
			pstmt.setInt(21, dateItemVO.getDateItemNo());
			pstmt.executeUpdate();

		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
	public void delete(int dateItemNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, dateItemNo);
			pstmt.executeUpdate();

		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
	public DateItemVO findByPk(int dateItemNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		DateItemVO dateItemVO=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, dateItemNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dateItemVO=new DateItemVO();
				
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return dateItemVO;
	}

	public ArrayList<DateItemVO> findItemBuyer(int dateItemNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		DateItemVO dateItemVO=null;
		List<DateItemVO> list = new ArrayList<DateItemVO>();
		try {
			
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_BY_BUYER);
			pstmt.setInt(1, dateItemNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dateItemVO=new DateItemVO();
				
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
//				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				list.add(dateItemVO);
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return (ArrayList<DateItemVO>) list;
	}

	public ArrayList<DateItemVO> findItemSeller(int dateItemNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		DateItemVO dateItemVO=null;
		List<DateItemVO> list = new ArrayList<DateItemVO>();
		try {
			
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_BY_SELLER);
			pstmt.setInt(1, dateItemNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dateItemVO=new DateItemVO();
				
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
//				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				list.add(dateItemVO);
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return (ArrayList<DateItemVO>) list;
	}
	public ArrayList<DateItemVO> findMessageSeller(int dateItemNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		DateItemVO dateItemVO=null;
		List<DateItemVO> list = new ArrayList<DateItemVO>();
		try {
			
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_BY_All_FINDMessageSELLER);
			pstmt.setInt(1, dateItemNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dateItemVO=new DateItemVO();
				
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
//				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				list.add(dateItemVO);
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return (ArrayList<DateItemVO>) list;
	}
	public ArrayList<DateItemVO> findItemFD(int dateItemNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		DateItemVO dateItemVO=null;
		List<DateItemVO> list = new ArrayList<DateItemVO>();
		try {
			
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_BY_FASTDATEITEM);
			pstmt.setInt(1, dateItemNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dateItemVO=new DateItemVO();
				
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
//				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				list.add(dateItemVO);
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return (ArrayList<DateItemVO>) list;
	}
	public ArrayList<DateItemVO> findItemAll(int dateItemNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		DateItemVO dateItemVO=null;
		List<DateItemVO> list = new ArrayList<DateItemVO>();
		try {
			
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_BY_All);
			pstmt.setInt(1, dateItemNo);
			pstmt.setInt(2, dateItemNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dateItemVO=new DateItemVO();
				
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
//				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				list.add(dateItemVO);
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return (ArrayList<DateItemVO>) list;
	}
	public ArrayList<DateItemVO> findItemAllInMessage(int dateItemNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		DateItemVO dateItemVO=null;
		List<DateItemVO> list = new ArrayList<DateItemVO>();
		try {
			
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_BY_AllMessage);
			pstmt.setInt(1, dateItemNo);
			pstmt.setInt(2, dateItemNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dateItemVO=new DateItemVO();
				
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
//				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				list.add(dateItemVO);
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return (ArrayList<DateItemVO>) list;
	}
	
	
	public ArrayList<DateItemVO> getPet(String type) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		DateItemVO dateItemVO=null;
		String petKind;
		List<DateItemVO> list = new ArrayList<DateItemVO>();
		try {
			if("cat".equals(type)){
				petKind="¿ß";
			}else if("dog".equals(type)){
				petKind="ª¯";
			}else {
				petKind="¨ä¥L";
			}
			String FIND_PET_TYPE = "select * from dateitem where dateItemStatus=0 and  petno  in ( select petno from pet where petkind like '%"+petKind+"%')";
//									select * from dateitem where  petno  in ( select petno from pet where petkind like '%   ¿ß      %');
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_PET_TYPE);
			
			
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				dateItemVO=new DateItemVO();
				
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
//				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				list.add(dateItemVO);
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return (ArrayList<DateItemVO>) list;
	}
	public byte[] getImage(int id) {
		
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		byte[] image = null;
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_DATEITEMIMG_BY_PK);
			pstmt.setInt(1, id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				
				image = rs.getBytes("dateItemImg");
				
			
			}
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return image;
	}
			
	@Override
	public List<DateItemVO> getAll() {
		List<DateItemVO> dateItemList = new ArrayList<>();
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(GET_ALL);
			rs=pstmt.executeQuery();
			while(rs.next()){
				DateItemVO dateItemVO=new DateItemVO();
				dateItemVO.setDateItemNo(rs.getInt("dateItemNo"));
				dateItemVO.setSellerNo(rs.getInt("sellerNo"));
				dateItemVO.setRestListNo(rs.getInt("restListNo"));
				dateItemVO.setDateItemTitle(rs.getString("dateItemTitle"));
//				dateItemVO.setDateItemImg(rs.getBytes("dateItemImg"));
				dateItemVO.setDateItemText(rs.getString("dateItemText"));
				dateItemVO.setDateItemTime(rs.getTimestamp("dateItemTime"));
				dateItemVO.setDateMeetingTime(rs.getTimestamp("dateMeetingTime"));
				dateItemVO.setDateItemLocate(rs.getString("dateItemLocate"));
				dateItemVO.setDateItemPeople(rs.getInt("dateItemPeople"));
				dateItemVO.setHasMate(rs.getBoolean("hasMate"));
				dateItemVO.setDateItemPrice(rs.getInt("dateItemPrice"));
				dateItemVO.setDateItemStatus(rs.getInt("dateItemStatus"));
				dateItemVO.setDateItemShow(rs.getInt("dateItemShow"));
				dateItemVO.setDateItemViewer(rs.getInt("dateItemShow"));
				dateItemVO.setBuyerNo(rs.getInt("buyerNo"));
				dateItemVO.setIsQRCChecked(rs.getBoolean("isQRCChecked"));
				dateItemVO.setBuyerRep(rs.getInt("buyerRep"));
				dateItemVO.setSellerRep(rs.getInt("SellerRep"));
				dateItemVO.setIsInstantDate(rs.getBoolean("isInstantDate"));
				dateItemVO.setPetNo(rs.getInt("petNo"));
				dateItemList.add(dateItemVO);		
			}
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
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
		return dateItemList;
	}
	
	
}
