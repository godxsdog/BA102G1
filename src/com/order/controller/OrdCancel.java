package com.order.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.Member;
import com.member.model.MemberService;
import com.order.model.Ord;
import com.order.model.OrdService;


@WebServlet("/OrdCancel")
public class OrdCancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int ordNo = Integer.valueOf(req.getParameter("ordNo"));
		
		OrdService ordDao = new OrdService();
		MemberService memDao = new MemberService();
		HttpSession session = req.getSession();
		Member mem = (Member)session.getAttribute("member");
		Ord ordOne = ordDao.getOneOrd(ordNo);
		System.out.println(ordOne.getOrdTotal());
		System.out.println(mem.getMemPoint());
		int memPoint = ordOne.getOrdTotal() + mem.getMemPoint();
		Ord OrdFk = ordDao.updateOrd(ordNo, ordOne.getMemNo(), ordOne.getOrdDate(), ordOne.getOrdCheck(), ordOne.getOrdProduct(), ordOne.getOrdClose(), 4, ordOne.getOrdShip(), ordOne.getOrdTotal(), ordOne.getConName(), ordOne.getConAdd(), ordOne.getConTel());
		Member member = memDao.updateMember(mem.getMemNo(), mem.getMemId(), mem.getMemPwd(), mem.getMemName(), mem.getMemSname(), mem.getMemGender(), mem.getMemIdNo(), mem.getMemBday(), mem.getMemPhone(), mem.getMemAddress(), mem.getMemEmail(), mem.getMemImg(), mem.getMemReported(), mem.getMemStatus(), mem.getMemRelation(), mem.getMemSelfintro(), mem.getMemFollowed(), memPoint, mem.getMemSaleRank(), mem.getMemLongtitude(), mem.getMemLatitude(), mem.getMemLocTime(), mem.getMemLocStatus());
		session.setAttribute("member", member);
		session.setAttribute("OrdFk", OrdFk);
		
		RequestDispatcher rd = req.getRequestDispatcher("/front_end/product/OrderView.jsp");
		rd.forward(req, res);
	
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
