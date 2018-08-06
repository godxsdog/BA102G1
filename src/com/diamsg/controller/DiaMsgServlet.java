package com.diamsg.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.diamsg.model.DiaMsg;
import com.diamsg.model.DiaMsgService;
import com.member.model.Member;
import com.member.model.MemberService;


public class DiaMsgServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException{
		
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=Big5");
		
		PrintWriter out = res.getWriter();
		
		String action = req.getParameter("action");
		
		
		if("insert".equals(action)){
			
			//**********從留言會員的編號***************
			Integer memNo =null;		
			memNo = member.getMemNo();
			//*********取出被留言的日誌編號************
			Integer diaNo = null;
			try{
				diaNo =new Integer(req.getParameter("diano"));
				
			}catch(Exception e){
				e.getMessage();
			}
			//**********取出留言的內容*****************
			String diaMsgText = null;
			try{
				diaMsgText =req.getParameter("diamsgtext");
				
			}catch(Exception e){
				e.getMessage();
			}
			
			//**********加入留言的時間*****************
			Timestamp diaMsgTime = new Timestamp(System.currentTimeMillis());
			
			//*********加入留言的狀態為0****代表未刪除
			Integer diaMsgState = new Integer(0);
			
			//開始新增資料給DiaMsgService
			DiaMsgService dmgSvc= new DiaMsgService();
			dmgSvc.addDiaMsg(diaNo, memNo, diaMsgText, diaMsgTime, diaMsgState);
			
			//轉成json格式傳給前端
			MemberService memSvc = new MemberService();
			String Sname = memSvc.getOneMember(memNo).getMemSname();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String json = "{\"sname\":\""+Sname+"\",\"diaMsgTime\":\""+sdFormat.format(diaMsgTime)
								+"\",\"memNo\":\""+memNo+"\",\"curr\":\""+dmgSvc.getCurrNo()+"\"}";
			out.println(json);
			out.close();
			
		}
		
		if("update".equals(action)){
			
			
			//*********取出留言的留言編號************
			Integer diaMsgNo = null;
			try{
				diaMsgNo =new Integer(req.getParameter("diamsgno"));
				
			}catch(Exception e){
				e.getMessage();
			}
			//**********取出留言的內容*****************
			String diaMsgText = null;
			try{
				diaMsgText =req.getParameter("diamsgtext");
				
			}catch(Exception e){
				e.getMessage();
			}
			
			
			
			//開始修改資料給DiaMsgService
			DiaMsgService dmgSvc= new DiaMsgService();
			DiaMsg diaMsgOld = dmgSvc.getOneDiaMsg(diaMsgNo);
			dmgSvc.updateDiaMsg(diaMsgNo, diaMsgOld.getDiaNo(), diaMsgOld.getMemNo(), diaMsgText, diaMsgOld.getDiaMsgTime(), diaMsgOld.getDiaMsgState());
	
		}
		
		if("delete".equals(action)){
			
			
			//*********取出留言的留言編號************
			Integer diaMsgNo = null;
			try{
				diaMsgNo =new Integer(req.getParameter("diamsgno"));
				
			}catch(Exception e){
				e.getMessage();
			}
						
			//開始修改資料給DiaMsgService
			DiaMsgService dmgSvc= new DiaMsgService();
			dmgSvc.deleteDiaMsg(diaMsgNo);
			
	
		}
		
		
		
		
		
	}
}
