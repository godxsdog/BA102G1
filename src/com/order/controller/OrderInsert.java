package com.order.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.charge.model.ChargesService;
import com.member.model.Member;
import com.member.model.MemberService;
import com.order.model.Ord;
import com.order.model.OrdService;
import com.orderlist.model.OrderList;
import com.orderlist.model.OrderListService;
import com.product.model.Product;


@WebServlet("/OrderInsert")
public class OrderInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = "/front_end/product/OrderView.jsp";
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		Member mem=(Member)session.getAttribute("member");
		OrdService dao = new OrdService();
		String amount = (session.getAttribute("amount")).toString();
		int num= Integer.valueOf(amount);
		java.util.Date utilDate =new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		Map<String, Integer> qtyMap = (Map<String, Integer>)session.getAttribute("qtyMap");
		Vector<Product> buylist = (Vector<Product>) session.getAttribute("shoppingcart");
		java.util.Date appTime = new java.util.Date();
		java.sql.Date applyTime = new java.sql.Date(appTime.getTime());
		String county = req.getParameter("county").trim();
		String name = req.getParameter("name").trim();
		String tel = req.getParameter("tel").trim();
		System.out.println("縣市: " + county);
		String district = req.getParameter("district").trim();
		System.out.println("區: " + district);
		String memAddress = req.getParameter("memAddress").trim();
		System.out.println("街名: " + memAddress);
		String totalAddress = county +"，" + district +"，" +  memAddress;
		System.out.println("地址: " + totalAddress);
		
		
		//判斷儲值金額是否足夠
		
		if(mem.getMemPoint()<num) {
			RequestDispatcher rd = req.getRequestDispatcher("/front_end/charge/chargePage.jsp");
			rd.forward(req, res);
		}
			
			
			else {
		//扣除會員點數
			MemberService memDao = new MemberService();
			int chargeNow = mem.getMemPoint() - num;
			Member member = memDao.updateMember(mem.getMemNo(), mem.getMemId(), mem.getMemPwd(), name,mem.getMemSname(), mem.getMemGender(), mem.getMemIdNo(), mem.getMemBday(), tel,totalAddress, mem.getMemEmail(), mem.getMemImg(), mem.getMemReported(),mem.getMemStatus(), mem.getMemRelation(), mem.getMemSelfintro(), mem.getMemFollowed(),chargeNow, mem.getMemSaleRank(), mem.getMemLongtitude(), mem.getMemLatitude(),mem.getMemLocTime(), mem.getMemLocStatus());
			session.setAttribute("member", member);
		//-------設定新增訂單資料--------------------
			Ord ord = new Ord();
			ord.setMemNo(mem.getMemNo());
			ord.setOrdDate(sqlDate);
			ord.setOrdCheck(sqlDate);
			ord.setOrdClose(null);
			ord.setOrdProduct(null);
			ord.setOrdStatus(1);
			ord.setOrdShip(50);
			ord.setOrdTotal(num);
			ord.setConAdd(totalAddress);
			ord.setConName(mem.getMemName());
			ord.setConTel(mem.getMemPhone());
		//--------設定訂單清單資料--
		List<OrderList> list=new ArrayList<OrderList>();
		for(Product prod:buylist) {
			OrderList olist=new OrderList();
			olist.setProdNo(prod.getProdNo());
			olist.setProPrice(prod.getProdPrice());
			System.out.println(prod.getProdNo());
			olist.setProQua(qtyMap.get(prod.getProdName()));
			list.add(olist);
		}
			OrdService ordSvc=new  OrdService();
			ordSvc.insertWithOrderLists(ord, list);
			session.removeAttribute("qtyMap");
			session.removeAttribute("shoppingcart");
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
