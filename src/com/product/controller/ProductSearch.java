package com.product.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product.model.ProductService;
import com.product.model.Product;


@WebServlet("/ProductSearch")
public class ProductSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");  
		res.setContentType("text/html; charset=UTF-8");
		String search = String.valueOf(req.getParameter("search"));
		System.out.println(search);
		ProductService prodDao = new ProductService();
		List<Product> searchProd = new ArrayList<>();
		searchProd = prodDao.getAllByName(search);
		HttpSession session = req.getSession();
		session.setAttribute("searchProd", searchProd);
		RequestDispatcher rd = req.getRequestDispatcher("/front_end/product/SearchResult.jsp");
		rd.forward(req, res);
		}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
