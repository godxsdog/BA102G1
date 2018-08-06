package com.msg.model;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.sql.Clob;

public class MsgJDBCDAO implements MsgDAO_interface{

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "petym";
	private static final String PASSWORD = "123456";
	
	
	private static final String INSERT_STMT = "INSERT INTO MSG(MSGNO,SENDNO,RECNO,"
			+ "DATEITEMNO,MSGCONTENT,MSGTIME,MSGSTATUS)"
			+ " VALUES(MSGNO_SQ.NEXTVAL,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE MSG SET MSGNO = ?, SENDNO = ?, RECNO = ?, "
			+ "DATEITEMNO = ?, MSGCONTENT = ?, MSGTIME = ?, MSGSTATUS = ? WHERE MSGNO =　?";
	private static final String DELETE_STMT = "DELETE FROM MSG WHERE MSGNO = ?";
	private static final String FIND_BY_PK = "SELECT * FROM MSG WHERE MSGNO = ?";
	private static final String GET_ALL = "SELECT * FROM MSG";
	
	
	
	@Override
	public void add(MsgVO msgVO) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt=con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, msgVO.getSendNo());
			pstmt.setInt(2, msgVO.getRecNo());
			pstmt.setInt(3, msgVO.getDateItemNo());
			pstmt.setString(4, msgVO.getMsgContent());
			pstmt.setTimestamp(5, msgVO.getMsgTime());
			pstmt.setInt(6, msgVO.getMsgStatus());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
			Class.forName(DRIVER);
			con=DriverManager.getConnection(URL,USER,PASSWORD);
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public void delete(int msgNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt=con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, msgNo);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public MsgVO findByPk(int msgNo) {
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		MsgVO msgVO=null;
		
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(URL,USER,PASSWORD);
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
	public List<MsgVO> getAll() {
		List<MsgVO> msgList = new ArrayList<>();
		PreparedStatement pstmt=null;
		Connection con=null;
		ResultSet rs=null;
		
		try {
			Class.forName(DRIVER);
			con=DriverManager.getConnection(URL,USER,PASSWORD);
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
	
	// Main方法
	public static void main(String [ ] args) throws IOException{
		byte[] testImg = getPictureByteArray("C:\\Users\\PSP\\Desktop\\bh.png");		
		MsgJDBCDAO dao=new MsgJDBCDAO();
		MsgVO msgVO = new MsgVO();
		
		msgVO.setSendNo(5003);
		msgVO.setRecNo(5007);
		msgVO.setDateItemNo(4003);
		msgVO.setMsgContent("測試聊天室是否正常喔");
		
		GregorianCalendar cal = new GregorianCalendar(2017,7,31,18,30,59);
		java.util.Date ud = cal.getTime();
		Timestamp ts= new Timestamp(ud.getTime());		
		msgVO.setMsgTime(ts);	
		msgVO.setMsgStatus(0);

		
		//新增
		dao.add(msgVO);		
		System.out.println("已新增第1筆");
		dao.add(msgVO);
		System.out.println("已新增第2筆");
		dao.add(msgVO);
		System.out.println("已新增第3筆");
		
		//修改
		msgVO.setMsgNo(10001);
		msgVO.setMsgContent("已修改已修改過了");	
		dao.update(msgVO);
		System.out.println("已修改第二筆");
		
		//刪除
		dao.delete(10003);
		System.out.println("已刪除10003");
		
		//查詢 by PK
		msgVO = dao.findByPk(10001);
		System.out.println(msgVO.getMsgContent());
		System.out.println(msgVO.getSendNo());
		System.out.println(msgVO.getRecNo());
		System.out.println(msgVO.getDateItemNo());
		System.out.println(msgVO.getMsgTime());
		System.out.println("已查詢到10001");
		
		
		//getAll
		List<MsgVO> msgList = dao.getAll();
		for(MsgVO msg : msgList){
		System.out.println(msg.getMsgNo());
		System.out.println(msg.getMsgTime());
	}
		
		
	}
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	@Override
	public List<MsgVO> findByDateItemNo(int dateItemNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMsgByVO(MsgVO msgVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int unread(int memNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MsgVO> unreadList(int memNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


