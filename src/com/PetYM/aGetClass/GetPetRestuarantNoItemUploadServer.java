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
@WebServlet("/GetPetRestuarantNoItemUpload")
public class GetPetRestuarantNoItemUploadServer extends HttpServlet {
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	GetRestuarantPetNameVO getRestuarantPetNameVO;
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

	}

	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		List<Integer> getNo = new ArrayList<Integer>();
		Integer petNo;
		Integer restNo;
		RestaurantDAO restaurantDAO = new RestaurantDAO();
		PetDAO petDAO = new PetDAO();
		rq.setCharacterEncoding("UTF-8");
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//		Gson gson = new Gson();
		BufferedReader br = rq.getReader();
		StringBuffer jsonIn = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("----------jsonIn-70行--------"+jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		//獲的android的memberId的String
		String restuarantSelected = jsonObject.get("restuarantSelected").getAsString();
		String petSelected = jsonObject.get("petSelected").getAsString();
		System.out.println("----------memberId-75行--------"+restuarantSelected);
		System.out.println("----------memberId-75行--------"+petSelected);
		//獲得String
		restNo = restaurantDAO.getRestNo(restuarantSelected);
		petNo = petDAO.getPetNo(petSelected);
		System.out.println("----------restNo-79行--------"+restNo.toString());
		System.out.println("----------petNo-79行--------"+petNo.toString());
		getNo.add(restNo);//5
		getNo.add(petNo);//5
		System.out.println("----------petList-81行--------"+getNo.size());

		String outStr = "";
		outStr = gson.toJson(getNo);
		System.out.println("outStr::::"+outStr);
	    doGet(rq, rp);
		rp.setContentType(CONTENT_TYPE);
		PrintWriter out = rp.getWriter();
		out.println(outStr);
		System.out.println(outStr);
		
	}

	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		Gson gson = new Gson();

		String outStr = gson.toJson(getRestuarantPetNameVO);
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
