package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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










public class ShoppingCar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");  
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		HashMap proQua = new HashMap();	
		String url = "front_end/product/Cart.jsp";
		String delname = (req.getParameter("delname"));
		
		HttpSession session = req.getSession();
		Vector<Product> buylist = (Vector<Product>) session.getAttribute("shoppingcart");
		Map<String,Integer> qtyMap = (Map<String,Integer>) session.getAttribute("qtyMap");
		
		
		//ProductService prodVo = new ProductService();
		Product aprod = getProduct(req);
		String action = req.getParameter("action").trim();
		System.out.println(action);
		
		if(!action.equals("checkout")) {
			// 刪除購物車中的書籍
			if(session.getAttribute("member")==null) {
				RequestDispatcher rd = req.getRequestDispatcher("/front_end/member/login.jsp");
				rd.forward(req, res);
				return;
			}
			if (action.equals("delete")) {
				
				System.out.println(req.getParameter("delname").trim());
				int Qua = qtyMap.get(req.getParameter("delname").trim());
				
				
					String del = req.getParameter("del");
					int d = Integer.parseInt((del).trim());
					buylist.removeElementAt(d);
					qtyMap.remove(req.getParameter("delname").trim());
			   
			   
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			}
			// 新增書籍至購物車中
			else if (action.equals("add")) {
				boolean match=false;
				if(qtyMap==null) {
					
					
					buylist=new Vector<Product>();
					
					buylist.add(aprod);
					qtyMap =new HashMap<String,Integer>();
					qtyMap.put(aprod.getProdName(), 1);
				}else {
					
					for (int i = 0; i < buylist.size(); i++) {
						Product Product = buylist.get(i);
					if(Product.getProdName().equals(aprod.getProdName())) {
						match=true;
						if (qtyMap.get(Product.getProdName())==10) {
							int qty = qtyMap.get(Product.getProdName());
							qty=10;
							qtyMap.put(Product.getProdName(), qty);
						}else{
							int qty = qtyMap.get(Product.getProdName());
							qty = qty + 1;
							qtyMap.put(Product.getProdName(), qty);
						}
						}
					}if (!match){
						buylist.add(aprod);
						qtyMap.put(aprod.getProdName(), 1);
					}
				}

			
		

					session.setAttribute("shoppingcart", buylist);
					session.setAttribute("qtyMap", qtyMap);
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);
			}
		}else if(action.equals("checkout")) {
			
			
			float total = 0;
			for (int i = 0; i < buylist.size(); i++) {
				Product order = buylist.get(i);
				float price = order.getProdPrice();
				String prodName = order.getProdName();
				int quality = qtyMap.get(prodName);
				total += (price * quality);
			}

			String amount = String.valueOf(total);
			req.setAttribute("amount", amount);
			String url1 = "front_end/product/CheckOut.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url1);
			rd.forward(req, res);
		}
		
				}
			
		
	private Product getProduct(HttpServletRequest req) throws UnsupportedEncodingException {
		String name = req.getParameter("name");
		Integer price = new Integer(req.getParameter("price").trim());
		Integer prodNo = new Integer(req.getParameter("prodno").trim());
		Product pro = new Product();
		pro.setProdName(name);
		pro.setProdPrice(price);
		pro.setProdNo(prodNo);
		
		return pro;
	}
		
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doPost(req, res);
	}
}
