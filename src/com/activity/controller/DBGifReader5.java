package com.activity.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.activity.model.Activity;
import com.activity.model.ActivityService;

public class DBGifReader5 extends HttpServlet {

	// 運用連線池，提高效能 解決交易問題  降低與資料庫的耦合
	// 小心連線的問題，這裡是用實體變數。
//	Connection con;
//	ServletOutputStream out;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {

			req.setCharacterEncoding("big5");
			Integer actNo = Integer.parseInt(req.getParameter("actNo"));
			
			ActivityService activityService = new ActivityService();
			Activity activity = activityService.getOneActivity(actNo);

//			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT actinitimg FROM activity WHERE actNo='" + actNo + "' ");
			out.write(activity.getActInitImg());
//			
//				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("actinitimg"));
//				byte[] buf = new byte[1000 * 1024]; // 4K buffer
//				int len;
//				while ((len = in.read(buf)) != -1) {
//					out.write(buf, 0, len);
//				}
//				in.close();
//			
//			rs.close();
//			stmt.close();
		} catch (Exception e) {
			//System.out.println(e);
			// 啟動網頁時，讀不到圖片可以代替顯示
			
		}
	}

	

}
