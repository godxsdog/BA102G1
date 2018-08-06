package com.PetYM.aGetClass;


import com.PetYM.*;
import com.PetYM.Member.MemberDAO;
import com.PetYM.Pet.Pet;
import com.PetYM.Pet.PetDAO;
import com.PetYM.Restaurant.Restaurant;
import com.PetYM.Restaurant.RestaurantDAO;
import com.PetYM.Member.Member;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


@SuppressWarnings("serial")
@WebServlet("/MemPetRestServer")
public class MemPetRestServer extends HttpServlet {
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	MemberPetResturant memberPetResturant;
	MemberPet memberPet;
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

	}

	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		MemberDAO memberDAO = new MemberDAO();
		PetDAO petDAO = new PetDAO();
		RestaurantDAO restaurantDAO = new RestaurantDAO();
		Member member = new Member();
		Pet pet = new Pet();
		Restaurant restaurant = new Restaurant();
		rq.setCharacterEncoding("UTF-8");
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//		Gson gson = new Gson();
		BufferedReader br = rq.getReader();
		StringBuffer jsonIn = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("-----------------------------"+jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String memberPetResturantString = jsonObject.get("memberPetResturant").getAsString();
		System.out.println("-----------------------------"+memberPetResturantString);
		Integer sellerId = Integer.valueOf(jsonObject.get("sellerId").getAsString());
		Integer petid = null;
		Integer restid = null;
		
			if(!(jsonObject.get("petid")==null || jsonObject.get("restid")==null)){
			petid = Integer.valueOf(jsonObject.get("petid").getAsString());
			restid = Integer.valueOf(jsonObject.get("restid").getAsString());
			restaurant = restaurantDAO.findByPK(restid);
			pet = petDAO.findByPk(petid);
			System.out.println("-----------------------------"+restid);
			}
			System.out.println("-----------------------------"+"hiii");
		member = memberDAO.findByPk(sellerId);
	
		
		
		String outStr = "";
			
		if ("memberPetResturant".equals(memberPetResturantString)) {
			memberPetResturant = new MemberPetResturant(member, pet, restaurant);
			outStr = gson.toJson(memberPetResturant);
			System.out.println("-----------------------------"+outStr);
			//µ¹personal­¶­±¡AmemPet¥Î
		} else if("memberPet".equals(memberPetResturantString)){
			//---------------------------------------------------------------------------------------------
			pet =  memberDAO.findPetsByMemNoObject(sellerId);
			//---------------------------------------------------------------------------------------------

			memberPet = new MemberPet(member, pet);
			outStr = gson.toJson(memberPet);
			System.out.println("-----------------------------"+pet);
			System.out.println("-----------------------------"+outStr);
		}else {
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

		String outStr = gson.toJson(memberPetResturant);
		rp.setContentType(CONTENT_TYPE);
		PrintWriter out = rp.getWriter();
		out.println("<H3>Category</H3>");
		out.println("<H3>Computer Books</H3>");
		out.println("<H3>Comic Books</H3>");
		out.println(memberPetResturant);
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
