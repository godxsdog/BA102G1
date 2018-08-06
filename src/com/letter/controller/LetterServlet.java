package com.letter.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letter.model.Letter;
import com.letter.model.LetterService;

public class LetterServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException{
		
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=Big5");
		String action = req.getParameter("action");
		
		if("updateTag".equals(action)){
			
			//---------接收修改編號-----------------
			Integer letterNo = null;
			letterNo =  Integer.valueOf(req.getParameter("letterno"));
			
			//---------呼叫svc來處理----------------
			LetterService ltrSvc = new LetterService();
			Letter letter = ltrSvc.getOneByPrimary(letterNo);			
			ltrSvc.update(letterNo, letter.getLetterTypeNo(), letter.getMemNo(), letter.getLetterTime(), letter.getLetterState(), 1, letter.getLetterText());
			
		}
		if("updateNotTag".equals(action)){
			
			//---------接收修改編號-----------------
			Integer letterNo = null;
			letterNo =  Integer.valueOf(req.getParameter("letterno"));
			
			//---------呼叫svc來處理----------------
			LetterService ltrSvc = new LetterService();
			Letter letter = ltrSvc.getOneByPrimary(letterNo);			
			ltrSvc.update(letterNo, letter.getLetterTypeNo(), letter.getMemNo(), letter.getLetterTime(), letter.getLetterState(), 0, letter.getLetterText());
			
		}
		if("updateRead".equals(action)){
			
			//---------接收修改編號-----------------
			Integer letterNo = null;
			letterNo =  Integer.valueOf(req.getParameter("letterno"));
			
			//---------呼叫svc來處理----------------
			LetterService ltrSvc = new LetterService();
			Letter letter = ltrSvc.getOneByPrimary(letterNo);			
			ltrSvc.update(letterNo, letter.getLetterTypeNo(), letter.getMemNo(), letter.getLetterTime(), 1, letter.getLetterTag(), letter.getLetterText());
			
		}
		
	
	}

}
