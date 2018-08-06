package com.msg.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Clob;

public class MsgDAO implements MsgDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/petym");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO MSG(MSGNO,SENDNO,RECNO,"
			+ "DATEITEMNO,MSGCONTENT,MSGTIME,MSGSTATUS)"
			+ " VALUES(MSGNO_SQ.NEXTVAL,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE MSG SET MSGNO = ?, SENDNO = ?, RECNO = ?, "
			+ "DATEITEMNO = ?, MSGCONTENT = ?, MSGTIME = ?, MSGSTATUS = ? WHERE MSGNO =¡@?";
	private static final String UPDATEBYVO = "UPDATE MSG SET MSGNO = ?, SENDNO = ?, RECNO = ?, "
			+ "DATEITEMNO = ?, MSGCONTENT = ?, MSGTIME = ?, MSGSTATUS = ? WHERE MSGNO =¡@?";
	private static final String DELETE_STMT = "DELETE FROM MSG WHERE MSGNO = ?";
	private static final String FIND_BY_PK = "SELECT * FROM MSG WHERE MSGNO = ?";
	private static final String GET_ALL = "SELECT * FROM MSG";
	private static final String FROMDATEITEMNO = "SELECT * FROM MSG WHERE DATEITEMNO=?";
	private static final String UNREAD = "SELECT * FROM MSG WHERE RECNO=? and MSGSTATUS=0";
	private static final String UNREADLIST = "SELECT * FROM MSG WHERE RECNO=? and MSGSTATUS=0";
	
	
	@Override
	public void add(MsgVO msgVO) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, msgVO.getSendNo());
			pstmt.setInt(2, msgVO.getRecNo());
			pstmt.setInt(3, msgVO.getDateItemNo());
			pstmt.setString(4, msgVO.getMsgContent());
			pstmt.setTimestamp(5, msgVO.getMsgTime());
			pstmt.setInt(6, msgVO.getMsgStatus());
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
	public void update(MsgVO msgVO) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, msgVO.getMsgNo());
			pstmt.setInt(2, msgVO.getSendNo());
			pstmt.setInt(3, msgVO.getRecNo());
			pstmt.setInt(4, msgVO.getDateItemNo());
			pstmt.setString(5, msgVO.getMsgContent());
			pstmt.setTimestamp(6, msgVO.getMsgTime());
			pstmt.setInt(7, msgVO.getMsgStatus());
			pstmt.setInt(8, msgVO.getMsgNo());
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
	public void updateMsgByVO(MsgVO msgVO) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(UPDATEBYVO);
			pstmt.setInt(1, msgVO.getMsgNo());
			pstmt.setInt(2, msgVO.getSendNo());
			pstmt.setInt(3, msgVO.getRecNo());
			pstmt.setInt(4, msgVO.getDateItemNo());
			pstmt.setString(5, msgVO.getMsgContent());
			pstmt.setTimestamp(6, msgVO.getMsgTime());
			pstmt.setInt(7, msgVO.getMsgStatus());
			pstmt.setInt(8, msgVO.getMsgNo());
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
	public void delete(int msgNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, msgNo);
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
	public MsgVO findByPk(int msgNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		MsgVO msgVO=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, msgNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				msgVO=new MsgVO();
				msgVO.setMsgNo(rs.getInt("msgNo"));
				msgVO.setSendNo(rs.getInt("sendNo"));
				msgVO.setRecNo(rs.getInt("recNo"));
				msgVO.setDateItemNo(rs.getInt("dateItemNo"));
				msgVO.setMsgContent(rs.getString("msgContent"));
				msgVO.setMsgTime(rs.getTimestamp("msgTime"));
				msgVO.setMsgStatus(rs.getInt("msgStatus"));		
				
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
		return msgVO;
	}
	
	@Override
	public int unread(int memNo) {
		List<MsgVO> msgList = new ArrayList<>();
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		Integer number=0;
		try {			
			con = ds.getConnection();
			pstmt=con.prepareStatement(UNREAD);
			pstmt.setInt(1, memNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				number++;
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
		return number;
	}
	
	@Override
	public List<MsgVO> findByDateItemNo(int dateItmeNo) {
		List<MsgVO> msgList = new ArrayList<>();
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(FROMDATEITEMNO);
			pstmt.setInt(1, dateItmeNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				MsgVO msgVO=new MsgVO();
				msgVO.setMsgNo(rs.getInt("msgNo"));
				msgVO.setSendNo(rs.getInt("sendNo"));
				msgVO.setRecNo(rs.getInt("recNo"));
				msgVO.setDateItemNo(rs.getInt("dateItemNo"));
				msgVO.setMsgContent(rs.getString("msgContent"));
				msgVO.setMsgTime(rs.getTimestamp("msgTime"));
				msgVO.setMsgStatus(rs.getInt("msgStatus"));				
				msgList.add(msgVO);				
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
		return msgList;
	}
	
	@Override
	public List<MsgVO> unreadList(int memNo) {
		List<MsgVO> unreadList = new ArrayList<>();
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(UNREADLIST);
			pstmt.setInt(1, memNo);
			rs=pstmt.executeQuery();
			while(rs.next()){
				MsgVO msgVO=new MsgVO();
				msgVO.setMsgNo(rs.getInt("msgNo"));
				msgVO.setSendNo(rs.getInt("sendNo"));
				msgVO.setRecNo(rs.getInt("recNo"));
				msgVO.setDateItemNo(rs.getInt("dateItemNo"));
				msgVO.setMsgContent(rs.getString("msgContent"));
				msgVO.setMsgTime(rs.getTimestamp("msgTime"));
				msgVO.setMsgStatus(rs.getInt("msgStatus"));				
				unreadList.add(msgVO);				
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
		return unreadList;
	}

	@Override
	public List<MsgVO> getAll() {
		List<MsgVO> msgList = new ArrayList<>();
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(GET_ALL);
			rs=pstmt.executeQuery();
			while(rs.next()){
				MsgVO msgVO=new MsgVO();
				msgVO.setMsgNo(rs.getInt("msgNo"));
				msgVO.setSendNo(rs.getInt("sendNo"));
				msgVO.setRecNo(rs.getInt("recNo"));
				msgVO.setDateItemNo(rs.getInt("dateItemNo"));
				msgVO.setMsgContent(rs.getString("msgContent"));
				msgVO.setMsgTime(rs.getTimestamp("msgTime"));
				msgVO.setMsgStatus(rs.getInt("msgStatus"));				
				msgList.add(msgVO);				
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
		return msgList;
	}
	
}
