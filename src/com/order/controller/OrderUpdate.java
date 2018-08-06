package com.order.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letter.model.LetterService;
import com.order.model.Ord;
import com.order.model.OrdService;


@WebServlet("/OrderUpdate")
public class OrderUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		OrdService ordDao = new OrdService();
		int ordNo = Integer.valueOf(req.getParameter("ordNo"));
		int ordstate = Integer.valueOf(req.getParameter("ordstate"));
		Ord ordOne = ordDao.getOneOrd(ordNo);
		System.out.println(ordstate);
		java.util.Date prodTime = new java.util.Date();
		java.sql.Date proTime = new java.sql.Date(prodTime.getTime());
		java.util.Date cloTime = new java.util.Date();
		java.sql.Date closeTime = new java.sql.Date(cloTime.getTime());
		if(ordstate == 2) {
			
			Ord ordAlreadyShip = ordDao.updateOrd(ordNo, ordOne.getMemNo(), ordOne.getOrdDate(), ordOne.getOrdCheck(), proTime, ordOne.getOrdClose(), ordstate,ordOne.getOrdShip(), ordOne.getOrdTotal(), ordOne.getConName(), ordOne.getConAdd(), ordOne.getConTel());
			LetterService letterDao = new LetterService();
			letterDao.addLtrOfOrdDelivery(ordAlreadyShip);
			System.out.println("¥X³f¦¨¥\");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/back_end/order/OrderManage.jsp");
			dispatcher.forward(req, res);
		}else {
			ordDao.updateOrd(ordNo, ordOne.getMemNo(), ordOne.getOrdDate(), ordOne.getOrdCheck(), proTime, closeTime, ordstate,ordOne.getOrdShip(), ordOne.getOrdTotal(), ordOne.getConName(), ordOne.getConAdd(), ordOne.getConTel());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/back_end/order/OrderManage.jsp");
			dispatcher.forward(req, res);
		}
		
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doGet(req, res);
	}

}
