package com.PetYM.Pet;

import com.PetYM.*;
import com.PetYM.Member.MemberDAO;
import com.PetYM.Member.MemberRegister;
import com.PetYM.Pet.Pet;

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
@WebServlet("/PetRegister")
public class PetRegister extends HttpServlet {
//	List<Object> all=new ArrayList<Object>();
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final long serialVersionUID = 1L;
	private Pet pet;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
	}

	
	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
	
		
		
		rq.setCharacterEncoding("UTF-8");
//		Gson gson = new Gson();
		Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

		BufferedReader br = rq.getReader();
		StringBuffer jsonIn = new StringBuffer();
		
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println(jsonIn);
		
            pet=gson.fromJson(jsonIn.toString(), Pet.class);
			System.out.println(pet.getPetName());
			System.out.println(pet.getPetIntro());
			
			
			MemberDAO memberDAO = new MemberDAO();
			
//			memberDAO.add(MemberRegister.member);
			memberDAO.addWithPet(MemberRegister.member, pet);
			
			rp.setContentType(CONTENT_TYPE);
			PrintWriter out = rp.getWriter();
			
			
			
			
			

			
				doGet(rq, rp);
			}

	



	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
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
