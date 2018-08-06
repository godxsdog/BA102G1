package com.PetYM.aGetClass;


import com.PetYM.*;
import com.PetYM.Member.MemberDAO;
import com.PetYM.Pet.Pet;
import com.PetYM.Pet.PetDAO;
import com.PetYM.Restaurant.Restaurant;
import com.PetYM.Restaurant.RestaurantDAO;
import com.PetYM.puDateItem.DateItemDAO;
import com.PetYM.puDateItem.DateItemVO;
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
@WebServlet("/GetdateItemMemberForFastDate")
public class GetdateItemMemberForFastDate extends HttpServlet {
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	GetRestuarantPetNameVO getRestuarantPetNameVO;
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

	}

	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		PetDAO petDAO = new PetDAO();
		DateItemVO dateItemVO = new DateItemVO();
		DateItemDAO dateItemDAO = new DateItemDAO();
		RestaurantDAO restaurantDAO = new RestaurantDAO();
		System.out.println("----------jsonIn-99行--------");
		ArrayList<DateItemVO> dateItemList = new ArrayList<DateItemVO>();
		ArrayList<Member> membersList = new ArrayList<Member>();
		ArrayList<Pet> petList = new ArrayList<Pet>();
		ArrayList<Restaurant> restaurantList = new  ArrayList<Restaurant>();
		
		MemberDAO memberDAO = new MemberDAO();
		rq.setCharacterEncoding("UTF-8");
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//		Gson gson = new Gson();
		BufferedReader br = rq.getReader();
		StringBuffer jsonIn = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		//獲的android的memberId的String
		String memberId = jsonObject.get("memberId").getAsString();
		Integer memberInteger = Integer.valueOf(memberId);
		dateItemList = dateItemDAO.findItemFD(memberInteger);
		for(int i=0;i<dateItemList.size();i++){
			Member member= new Member();
			Pet pet = new Pet();
			Restaurant restaurant = new Restaurant();
			member= memberDAO.findByPk(dateItemList.get(i).getSellerNo());
			pet = petDAO.findByPk(dateItemList.get(i).getPetNo());
			restaurant=restaurantDAO.findByPK(dateItemList.get(i).getRestListNo());
			restaurantList.add(restaurant);
			membersList.add(member);
			petList.add(pet);
		}
		FastDateItemMemPetRestVO fastDateItemMemPetRestVO = new FastDateItemMemPetRestVO(dateItemList,membersList,petList,restaurantList);
		
		System.out.println("----------jsonIn-99行--------"+dateItemList.get(1).getSellerNo());
		System.out.println("----------jsonIn-99行--------"+membersList.get(1).getMemLatitude());
		System.out.println("----------jsonIn-99行--------"+membersList.get(1).getMemLongtitude());
		System.out.println("----------jsonIn-99行--------"+jsonIn);
		String outStr="";
		
		outStr = gson.toJson(fastDateItemMemPetRestVO);
//		System.out.println("----------outStr-70行--------"+outStr);
		rp.setContentType(CONTENT_TYPE);
		PrintWriter out = rp.getWriter();
		out.println(outStr);
		System.out.println(outStr);
		
	}

	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		Gson gson = new Gson();

//		String outStr = gson.toJson(getRestuarantPetNameVO);
		rp.setContentType(CONTENT_TYPE);
		PrintWriter out = rp.getWriter();

		
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
