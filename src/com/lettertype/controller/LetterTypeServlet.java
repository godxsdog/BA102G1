package com.lettertype.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lettertype.model.LetterTypeService;

public class LetterTypeServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException{
		
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=Big5");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		
		if("insert".equals(action)){
			
			//---------新增種類名稱-----------------
			String letterTypeName = null;
			letterTypeName = req.getParameter("lettertypename").trim();
			
			//---------新增的內文---------------------
			String letterTypeText = null;
			letterTypeText = req.getParameter("lettertypetext").trim();
			
			//---------呼叫service前來處理---------------
			LetterTypeService ltpSvc = new LetterTypeService();
			ltpSvc.addLtrType(letterTypeName, letterTypeText);
			
			//轉成json的字串讓ajax取用裡面資料
			String json = "{\"curr\":\""+ltpSvc.getCurr()+"\"}";
			out.println(json);
			out.close();
			
		}
		
		if("update".equals(action)){
			
			//---------接收修改編號-----------------
			Integer letterTypeNo = null;
			letterTypeNo =new Integer(req.getParameter("lettertypeno"));
			
			//---------修改種類名稱-----------------
			String letterTypeName = null;
			letterTypeName = req.getParameter("lettertypename");
			
			//---------修改種類內文-----------------
			String letterTypeText = null;
			letterTypeText = req.getParameter("lettertypetext");
			
			//---------呼叫service前來處理---------------
			LetterTypeService ltpSvc = new LetterTypeService();
			ltpSvc.update(letterTypeNo, letterTypeName, letterTypeText);
			
			
		}
		
		
		
	}

}
