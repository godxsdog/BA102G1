package com.product.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.charge.model.ChargesService;
import com.member.model.Member;
import com.member.model.MemberService;
import com.product.model.ProductService;

@MultipartConfig
@WebServlet("/ProductUpload")
public class ProductUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");  
		res.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		java.util.Date AdTime = new java.util.Date();
		java.sql.Date AddTime = new java.sql.Date(AdTime.getTime());
		
		if (req.getParameter("action").equals("addprod")) {
			List<String> errorMsg = new LinkedList<String>();
			Set<String> errorMsgs = new HashSet<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				
				String one = null;
				try {
					one = new String(req.getParameter("proName"));
				} catch (Exception e) {
					errorMsgs.add("請輸入正確資訊");
				}
				String two = null;
				try {
					two = new String(req.getParameter("proType"));
				} catch (Exception e) {
					errorMsgs.add("請輸入正確資訊");
				}
				String three = null;
				try {
					three = new String(req.getParameter("proDescpt"));
				} catch (Exception e) {
					errorMsgs.add("請輸入正確資訊");
				}
				Integer four = null;
				try {
					four = new Integer(req.getParameter("proPrice"));
				} catch (Exception e) {
					errorMsgs.add("請輸入正確資訊");
				}
				
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher dispatcher = req.getRequestDispatcher("/back_end/product/productUpdate.jsp");
					dispatcher.forward(req, res);
					return;
				} else {
					ProductService prodDao = new ProductService();
					String proName = req.getParameter("proName");
					String proType = req.getParameter("proType");
					String proDescpt = req.getParameter("proDescpt");
					int proPrice = Integer.valueOf(req.getParameter("proPrice"));
					
					byte[] proimg=null;
					String filename=null;
					Part part = req.getPart("prodimg");
						if(part.getSize()!=0){
							filename = getFileNameFromPart(part);
							System.out.println(part.equals(null));
							if(getFileNameFromPart(part) != null && part.getContentType()!=null){
								 proimg = getByteArrayImg(part);
								System.out.println("圖片格式正確!");
							}
						}
						prodDao.addProduct( proName, proPrice, proimg, proDescpt, AddTime, null, 0, proType);
						RequestDispatcher dispatcher = req.getRequestDispatcher("/back_end/product/productManage.jsp");
						dispatcher.forward(req, res);
					
				}
				
			} 
			
			catch (Exception e) {
				System.out.println("error");
			}
			
			
		}
			
			}
		
		
	

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}
	
public byte[] getByteArrayImg(Part part){
		
		ByteArrayOutputStream diaimg=null;
		try{
			java.io.InputStream in =part.getInputStream(); 
			diaimg = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int i;
			while ((i = in.read(buffer)) != -1) {
				diaimg.write(buffer, 0, i);
			}
			diaimg.close();
			in.close();
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return diaimg.toByteArray();
	}
	
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = header.substring(header.lastIndexOf("=") + 2, header.length() - 1);
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

}
