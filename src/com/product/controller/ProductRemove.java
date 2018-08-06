package com.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product.model.Product;
import com.product.model.ProductService;

@WebServlet("/ProductRemove")
public class ProductRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int prodNo = Integer.valueOf(req.getParameter("prodNo"));
		System.out.println(prodNo);
		HttpSession session = req.getSession();
		List<Product> prodList = (List<Product>)session.getAttribute("prodList");
		ProductService prodService = new ProductService();
		Product prod = prodService.getOneProduct(prodNo);
		java.util.Date ouTime = new java.util.Date();
		java.sql.Date outTime = new java.sql.Date(ouTime.getTime());
		Product prodNew = prodService.updateProduct(prod.getProdNo(), prod.getProdName(), prod.getProdPrice(), prod.getProdImg(), prod.getProdDescpt(), prod.getProdAdd(), outTime, 1, prod.getProdType());
		RequestDispatcher rd = req.getRequestDispatcher("back_end/product/productManage.jsp");
		rd.forward(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
