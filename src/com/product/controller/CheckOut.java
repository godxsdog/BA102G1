package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.Member;
import com.product.model.Product;



public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
 
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");  
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		HttpSession session = req.getSession();
		Map<String, Vector<Product>> CartMap = (Map<String, Vector<Product>>) session.getAttribute("shoppingcartMap");
		Map<String,Integer> qtyMap = (Map<String,Integer>) session.getAttribute("qtyMap");
		Member mem = (Member) session.getAttribute("member");

		int total = 0;
		Vector<Product> buylist1 = CartMap.get(mem.getMemName());
		if(req.getParameter("action").equals("changeNum")) {
			
		}else {
		for (int i = 0; i < buylist1.size(); i++) {
			Product order = buylist1.get(i);
			int price = order.getProdPrice();
			String prodName = order.getProdName();
			int quality = qtyMap.get(prodName);
			total += (price * quality);
		}

		String amount = String.valueOf(total);
		req.setAttribute("amount", amount);
		
		String url = "front_end/product/CheckOut.jsp";
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, res);
		}
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
