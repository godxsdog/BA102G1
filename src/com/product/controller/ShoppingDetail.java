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

import com.product.model.Product;
import com.product.model.ProductService;



public class ShoppingDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");  
		res.setContentType("text/html; charset=UTF-8");
		String url = "front_end/product/ProductDetail.jsp";

		
		HttpSession session = req.getSession();
		
		
		
		int prodNo = Integer.parseInt(req.getParameter("prodno"));
		ProductService prodVo = new ProductService();
		Product aprod = prodVo.getOneProduct(prodNo);
		
		
		

	


			session.setAttribute("shoppingdetail", aprod);
			
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
