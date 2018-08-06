package com.PetYM.puDateItem;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

//@SuppressWarnings("serial")
@WebServlet("/DataUploadServletTest")
public class TestJDBC extends HttpServlet {
	
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "godxsdog";
	private static final String PASSWORD = "123456";
	private static final String FIND_BY_PK = "SELECT * FROM DateItem";
	private List<DateItemVO> dateItemVOList;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	 private ServletContext context;

	@Override
	public void init() throws ServletException {
		dateItemVOList = new ArrayList<DateItemVO>();
		DateItemVO dateItemVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		super.init();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dateItemVO = new DateItemVO();
				dateItemVO.setDateItemNo(rs.getInt("DATEITEMNO"));
				System.out.println(rs.getInt("DATEITEMNO"));
				dateItemVO.setSellerNo(rs.getInt("SELLERNO"));
				dateItemVO.setRestListNo(rs.getInt("RESTLISTNO"));
				dateItemVO.setDateItemImg(rs.getBytes("DATEITEMIMG"));
				dateItemVOList.add(dateItemVO);
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (Exception se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception se) {
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

	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		System.out.println(dateItemVOList.size());
		Gson gson = new Gson();
		BufferedReader br = rq.getReader();
		StringBuffer jsonIn = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
			JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			String param = jsonObject.get("dateItem").getAsString();
			String outStr = "";
			if ("dateItem".equals(param)) {
				outStr = gson.toJson(dateItemVOList);
			} else {
				doGet(rq, rp);
			}
			rp.setContentType(CONTENT_TYPE);
			PrintWriter out = rp.getWriter();
			out.println(outStr);
			System.out.println(outStr);

		}
	}



	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		Gson gson = new Gson();
		String outStr = gson.toJson(dateItemVOList);
		rp.setContentType(CONTENT_TYPE);
		PrintWriter out = rp.getWriter();
		out.println("<H3>Category</H3>");
		out.println("<H3>Computer Books</H3>");
		out.println("<H3>Comic Books</H3>");
		out.println(dateItemVOList);
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

	public static String getPictureByteArray1(String path) throws IOException {
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

		return Base64.getMimeEncoder().encodeToString((baos.toByteArray()));

	}
}