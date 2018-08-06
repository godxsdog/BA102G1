package com.submem.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.Member;
import com.submem.model.SubMem;
import com.submem.model.SubMemService;

public class SubMemServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
			doPost(req,res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
			
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=Big5");
		String action = req.getParameter("action");
		
		if("insert".equals(action)){
			
			//**********從登入會員取值***************
			Integer actSubMemNo =null;
			actSubMemNo = member.getMemNo();
			
			
			//*********取出被加入的會員編號************
			Integer beSubMemNo = null;
			try{
				beSubMemNo =new Integer(req.getParameter("beSubMemNo"));
				
			}catch(Exception e){
				e.getMessage();
			}
			
			//*********加入的狀態為0****代表未取消
			Integer subState = new Integer(0);
			
			//開始修改資料給SubMemService
			SubMemService smSvc=null;
			
			smSvc = new SubMemService();
			smSvc.addSub(actSubMemNo, beSubMemNo, subState);
	
		}
		
		if("delete".equals(action)){
			
			//**********從登入會員取值***************
			Integer actSubMemNo =null;
			actSubMemNo = member.getMemNo();
			
			
			//*********取出被取消的會員編號************
			Integer beSubMemNo = null;
			try{
				beSubMemNo =new Integer(req.getParameter("beSubMemNo"));
				
			}catch(Exception e){
				e.getMessage();
			}
			
			//*********加入的狀態為1****代表取消
			Integer subState = new Integer(1);
			
			//開始修改資料給SubMemService
			SubMemService smSvc = new SubMemService();
			SubMem subMem =new SubMem();
			subMem.setActSubMemNo(actSubMemNo);
			subMem.setBeSubMemNo(beSubMemNo);
			subMem.setSubState(subState);
			smSvc.deleteSub(subMem);
		
	
		}
		
		
		
		
	} 
}
