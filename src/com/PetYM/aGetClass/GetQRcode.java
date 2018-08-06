
package com.PetYM.aGetClass;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.lettertype.model.LetterTypeService;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.dateitem.model.*;
import com.dateitem.model.DateItemService;
import com.dateitem.model.DateItemVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.letter.model.LetterService;
import com.member.model.Member;
import com.member.model.MemberService;
import com.pet.model.Pet;
import com.pet.model.PetService;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantService;


@WebServlet("/QRCODE")
public class GetQRcode extends HttpServlet {
	
	private List<DateItemVO> dateItemVOList;
	private byte[] dateItemVOListImage;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	private static final long serialVersionUID = 1L;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
	}
	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		System.out.println("jsonObject++++123:");
		rq.setCharacterEncoding("UTF-8");
		rp.setContentType("text/html; charset=Big5");
		Gson gson = new Gson();
		System.out.println("jsonObject++++123:");
		BufferedReader br = rq.getReader();
		System.out.println("jsonObject++++123:"+br.toString());
		StringBuffer jsonIn = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
			System.out.println("jsonObject++++123:"+jsonIn);

		}
		System.out.println("jsonObject++++123:");

			JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			System.out.println("jsonObject++++:"+jsonObject.toString());
			String QRcodeAction = jsonObject.get("QRcodeAction").getAsString();
		if("QRcodeAction".equals(QRcodeAction)){	
			try {
				String outStr="";
				int dateItemNo = jsonObject.get("dateImteNo").getAsInt();
				DateItemService dSvc = new DateItemService();
				DateItemVO  dateItemVO = dSvc.getOneDateItem(dateItemNo);
				dateItemVO.setDateItemStatus(3);
				dSvc.updateByVO(dateItemVO);
				outStr = "ok";
				rp.setContentType(CONTENT_TYPE);
				PrintWriter out = rp.getWriter();
				out.println(outStr);
				System.out.println(outStr);
				

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Parse Error");
			}
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
