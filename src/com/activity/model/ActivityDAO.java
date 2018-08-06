package com.activity.model;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.restMember.model.RestMember;

import another.CompositeQuery2;



public class ActivityDAO implements ActivityDAO_Interface{
	
	private static DataSource ds = null;
	static{
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/petym");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_ACTIVITY = "INSERT INTO ACTIVITY (ACTNO,RESTMEMID,ACTNAME,ACTCONTENT,ACTDATE,ACTFDATE,"
											+ "ACTSTATUS,ACTULIMIT,ACTLLIMIT,ACTKIND,ACTANOTHERKIND,ACTINITIMG)"
											+ "VALUES(ACTIVITY_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_ACTIVITY = "UPDATE ACTIVITY SET RESTMEMID=?,ACTNAME=?,ACTCONTENT=?,ACTDATE=?"
											+ ",ACTFDATE=?,ACTSTATUS=?,ACTULIMIT=?,ACTLLIMIT=?,ACTKIND=?,ACTANOTHERKIND=?,ACTINITIMG=? WHERE ACTNO=?";
	
	private static final String UPDATE_ACTIVITY_BACK = "UPDATE ACTIVITY SET ACTSTATUS=? WHERE ACTNO=?";
	
	private static final String DELETE_ACTIVITY = "DELETE FROM ACTIVITY WHERE ACTNO=?";
	
	private static final String FIND_BY_PK = "SELECT * FROM ACTIVITY WHERE ACTNO=?";
	private static final String FIND_BY_PK_STATUS = "SELECT * FROM ACTIVITY WHERE ACTNO=? AND ACTSTATUS=?";
	
	private static final String GET_ALL_FK = "SELECT * FROM ACTIVITY WHERE RESTMEMID=?";
	private static final String GET_ALL_STATUS = "SELECT * FROM ACTIVITY WHERE ACTSTATUS=?";
	private static final String GET_ALL = "SELECT * FROM ACTIVITY";
	private static final String GET_ALL_STATUS_CAT = "SELECT * FROM ACTIVITY WHERE ACTSTATUS=? AND ACTKIND=?";
	
	private static final String GET_ALL_MINE ="SELECT * FROM ACTIVITY JOIN ACTDETIAL ON (ACTIVITY.ACTNO = ACTDETIAL.ACTNO) "
			+ "WHERE MEMNO=? AND MEMACTSTATUS=0";
			
			
	private static final String GET_ALL_NORTH = "SELECT * FROM ACTIVITY JOIN RESTMEMBER "
			+ "ON (ACTIVITY.RESTMEMID=RESTMEMBER.RESTMEMID) JOIN REST ON  (REST.RESTNO = RESTMEMBER.RESTNO) "
			+ "WHERE actstatus=2 and restlocate='台北市' or restlocate='新北市' or restlocate='桃園市' or restlocate='新竹市' ";
	
	private static final String GET_ALL_EAST = "SELECT * FROM ACTIVITY JOIN RESTMEMBER "
			+ "ON (ACTIVITY.RESTMEMID=RESTMEMBER.RESTMEMID) JOIN REST ON  (REST.RESTNO = RESTMEMBER.RESTNO) "
			+ "WHERE actstatus=2 and restlocate='宜蘭縣' or restlocate='花蓮縣' or restlocate='台東縣' ";
	
	private static final String GET_ALL_WEST = "SELECT * FROM ACTIVITY JOIN RESTMEMBER "
			+ "ON (ACTIVITY.RESTMEMID=RESTMEMBER.RESTMEMID) JOIN REST ON  (REST.RESTNO = RESTMEMBER.RESTNO) "
			+ "WHERE actstatus=2 and restlocate='苗栗縣' or restlocate='台中市' or restlocate='彰化縣' or restlocate='南投縣' ";
	
	private static final String GET_ALL_SOUTH = "SELECT * FROM ACTIVITY JOIN RESTMEMBER "
			+ "ON (ACTIVITY.RESTMEMID=RESTMEMBER.RESTMEMID) JOIN REST ON  (REST.RESTNO = RESTMEMBER.RESTNO) "
			+ "WHERE actstatus=2 and restlocate='雲林縣' or restlocate='嘉義市' or restlocate='台南市' or restlocate='高雄市' or restlocate='屏東縣' ";
	
	
	
	@Override
	public void add(Activity activity) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			String[] cols = {"ACTNO"};
			pstmt = conn.prepareStatement(INSERT_ACTIVITY,cols);
			pstmt.setString(1, activity.getRestMemId());
			pstmt.setString(2, activity.getActName());
			pstmt.setString(3, activity.getActContent());
			pstmt.setDate(4, activity.getActDate());
			pstmt.setDate(5, activity.getActFDate());
			pstmt.setInt(6, activity.getActStatus());
			pstmt.setInt(7, activity.getActULimit());
			pstmt.setInt(8, activity.getActLLimit());
			pstmt.setInt(9, activity.getActKind());
			pstmt.setString(10, activity.getActAnotherKind());
			
			Blob blob = conn.createBlob();
			blob.setBytes(1, activity.getActInitImg());
			pstmt.setBlob(11, blob);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(Activity activity) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_ACTIVITY);
			pstmt.setString(1, activity.getRestMemId());
			pstmt.setString(2, activity.getActName());
			pstmt.setString(3, activity.getActContent());
			pstmt.setDate(4, activity.getActDate());
			pstmt.setDate(5, activity.getActFDate());
			pstmt.setInt(6, activity.getActStatus());
			pstmt.setInt(7, activity.getActULimit());
			pstmt.setInt(8, activity.getActLLimit());
			pstmt.setInt(9, activity.getActKind());
			pstmt.setString(10, activity.getActAnotherKind());
			
			Blob blob = conn.createBlob();
			blob.setBytes(1, activity.getActInitImg());
			pstmt.setBlob(11, blob);
			
			
			pstmt.setInt(12, activity.getActNo());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void delete(Integer actNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_ACTIVITY);
			pstmt.setInt(1, actNo);
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Activity findByPK(Integer actNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, actNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activity;
	}

	@Override
	public List<Activity> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activityList;
	}

	@Override
	public List<Activity> getAllById(String restMemId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_FK);
			pstmt.setString(1, restMemId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activityList;
	}

	@Override
	public List<Activity> getAllByStatus(Integer actStatus) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<>();
		
	 try{
		 conn = ds.getConnection();
		pstmt = conn.prepareStatement(GET_ALL_STATUS); 
		pstmt.setInt(1, actStatus);
		rs = pstmt.executeQuery();
		while(rs.next()){
			activity = new Activity();
			activity.setActNo(rs.getInt("ACTNO"));
			activity.setRestMemId(rs.getString("RESTMEMID"));
			activity.setActName(rs.getString("ACTNAME"));
			activity.setActContent(rs.getString("ACTCONTENT"));
			activity.setActDate(rs.getDate("ACTDATE"));
			activity.setActFDate(rs.getDate("ACTFDATE"));
			activity.setActStatus(rs.getInt("ACTSTATUS"));
			activity.setActULimit(rs.getInt("ACTULIMIT"));
			activity.setActLLimit(rs.getInt("ACTLLIMIT"));
			activity.setActKind(rs.getInt("ACTKIND"));
			activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
			activity.setActInitImg(rs.getBytes("ACTINITIMG"));
			activityList.add(activity);
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	return activityList;
		
	}

	@Override
	public Activity findByPKStatus(Integer actNo, Integer actStatus) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(FIND_BY_PK_STATUS);
			pstmt.setInt(1, actNo);
			pstmt.setInt(2, actStatus);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activity;
	}

	@Override
	public List<Activity> getAllByStatusAnimal(Integer actStatus, Integer actKind) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<>();
		
	 try{
		 conn = ds.getConnection();
		pstmt = conn.prepareStatement(GET_ALL_STATUS_CAT); 
		pstmt.setInt(1, actStatus);
		pstmt.setInt(2, actKind);
		rs = pstmt.executeQuery();
		while(rs.next()){
			activity = new Activity();
			activity.setActNo(rs.getInt("ACTNO"));
			activity.setRestMemId(rs.getString("RESTMEMID"));
			activity.setActName(rs.getString("ACTNAME"));
			activity.setActContent(rs.getString("ACTCONTENT"));
			activity.setActDate(rs.getDate("ACTDATE"));
			activity.setActFDate(rs.getDate("ACTFDATE"));
			activity.setActStatus(rs.getInt("ACTSTATUS"));
			activity.setActULimit(rs.getInt("ACTULIMIT"));
			activity.setActLLimit(rs.getInt("ACTLLIMIT"));
			activity.setActKind(rs.getInt("ACTKIND"));
			activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
			activity.setActInitImg(rs.getBytes("ACTINITIMG"));
			activityList.add(activity);
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	return activityList;
	}

	@Override
	public List<Activity> getAllByNorth() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_NORTH);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activityList;
	}

	@Override
	public List<Activity> getAllByEast() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_EAST);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activityList;
	}

	@Override
	public List<Activity> getAllByWest() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_WEST);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activityList;
	}

	@Override
	public List<Activity> getAllBySouth() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_SOUTH);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activityList;
	}

	@Override
	public List<Activity> getAllOfMine(Integer memNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_MINE);
			pstmt.setInt(1, memNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activityList;
	}

	@Override
	public void updateBack(Integer actStatus,Integer actNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_ACTIVITY_BACK);
			pstmt.setInt(1, actStatus);
			pstmt.setInt(2, actNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public List<Activity> getAll(Map<String, String[]> map) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Activity activity = null;
		ResultSet rs = null;
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			conn = ds.getConnection();
			String finalSQL = "select * from activity "
			          + CompositeQuery2.get_WhereCondition(map)
			          + "order by actNo";
			pstmt = conn.prepareStatement(finalSQL);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				activity = new Activity();
				activity.setActNo(rs.getInt("ACTNO"));
				activity.setRestMemId(rs.getString("RESTMEMID"));
				activity.setActName(rs.getString("ACTNAME"));
				activity.setActContent(rs.getString("ACTCONTENT"));
				activity.setActDate(rs.getDate("ACTDATE"));
				activity.setActFDate(rs.getDate("ACTFDATE"));
				activity.setActStatus(rs.getInt("ACTSTATUS"));
				activity.setActULimit(rs.getInt("ACTULIMIT"));
				activity.setActLLimit(rs.getInt("ACTLLIMIT"));
				activity.setActKind(rs.getInt("ACTKIND"));
				activity.setActAnotherKind(rs.getString("ACTANOTHERKIND"));
				activity.setActInitImg(rs.getBytes("ACTINITIMG"));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return activityList;
	}

}
