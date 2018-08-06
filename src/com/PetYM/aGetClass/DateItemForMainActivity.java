
package com.PetYM.aGetClass;

import com.PetYM.*;
import com.PetYM.Member.MemberDAO;
import com.PetYM.puDateItem.DateItemDAO;
import com.PetYM.puDateItem.DateItemVO;
import com.PetYM.puDateItem.ImageUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.lang.reflect.Type;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import oracle.net.aso.d;



@SuppressWarnings("serial")
@WebServlet("/MainActivity")
public class DateItemForMainActivity extends HttpServlet {
	
	private List<DateItemVO> dateItemVOList;
	private byte[] dateItemVOListImage;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	DateItemDAO dateItemDAO = new DateItemDAO();
	MemberDAO memberDAO = new MemberDAO();
	private static final long serialVersionUID = 1L;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		
		
	
	}

	
	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		
		
//		dateItemVOList = (ArrayList<DateItemVO>) dateItemDAO.getAll();
		rq.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = rq.getReader();
		StringBuffer jsonIn = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
			JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			System.out.println("jsonObject:"+jsonObject);
			String dateItem = jsonObject.get("dateItem").getAsString();
		

			String outStr = "";
			
			if ("dateItem".equals(dateItem)) {
				String type = jsonObject.get("type").getAsString();
				System.out.println("type取回Size:"+type);
				dateItemVOList = (ArrayList<DateItemVO>) dateItemDAO.getPet(type);
				outStr = gson.toJson(dateItemVOList);
				System.out.println("DataBase取回Size:"+outStr);
			}else if ("getImage".equals(dateItem)) {
				OutputStream os = rp.getOutputStream();
				int id = jsonObject.get("id").getAsInt();
				int imageSize = jsonObject.get("imageSize").getAsInt();
				dateItemVOListImage = (byte[]) dateItemDAO.getImage(id);
				
				if (dateItemVOListImage != null) {
					dateItemVOListImage = ImageUtil.shrink(dateItemVOListImage, imageSize);//shrink轉換圖片size
					rp.setContentType("image/jpeg");
					rp.setContentLength(dateItemVOListImage.length);
				}
				os.write(dateItemVOListImage);
				return;
			} else if ("getmemPic".equals(dateItem)){
				OutputStream os = rp.getOutputStream();
				int id = jsonObject.get("id").getAsInt();
				int imageSize = jsonObject.get("imageSize").getAsInt();
				dateItemVOListImage = (byte[]) memberDAO.getMemImage(id);
				
				if (dateItemVOListImage != null) {
					dateItemVOListImage = ImageUtil.shrink(dateItemVOListImage, imageSize);//shrink轉換圖片size
					rp.setContentType("image/jpeg");
					rp.setContentLength(dateItemVOListImage.length);
				}
				os.write(dateItemVOListImage);
				return;
				
			}
			else {
				doGet(rq, rp);
			}
			rp.setContentType(CONTENT_TYPE);
			PrintWriter out = rp.getWriter();
			out.println(outStr);
			System.out.println(outStr);

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
