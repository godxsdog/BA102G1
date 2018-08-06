package com.diary.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.diary.model.Diary;
import com.diary.model.DiaryService;
import com.member.model.Member;

@MultipartConfig(fileSizeThreshold =500* 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 5 * 500 * 1024 * 1024)
public class DiaryServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException{
			doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException{
		
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=Big5");
		String action = req.getParameter("action");
		Integer whichPage = null;
		try{
			whichPage = new Integer(req.getParameter("whichPage"));
		}catch(Exception e){
			whichPage = 1;
		}
		
		
		if("insert".equals(action)){
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Diary diaErr = new Diary();

			//----------------判斷名稱是否為空值-------------------
			String diaName = null;
			try{
				diaName = req.getParameter("dianame").trim();
				
				//diaName.isEmpty()
				if( diaName==null || "".equals(diaName) ){
					errorMsgs.add("請輸入發文名稱!");
				}
				if(diaName!=null && diaName.length()>30){
					errorMsgs.add("日誌標題長度過長!!");
				}
				
				//擋住script
				diaName = diaName.replace("<script>","");
				diaName = diaName.replace("</script>","");
				
				diaErr.setDiaName(diaName);
				
			}catch(NullPointerException e){
				e.printStackTrace(System.err);	
			}
			//----------------判斷內文是否為空值-------------------
			String diaText = null;
			try{
				diaText = req.getParameter("diatext").trim();
				if(diaText==null || diaText.equals("")){			//判斷空值(空字串)
					errorMsgs.add("請輸入內文!");
				}
				
				//擋住script
				diaText = diaText.replace("<script>","");
				diaText = diaText.replace("</script>","");
				
				diaErr.setDiaText(diaText);
				
			}catch(Exception ne){
				ne.printStackTrace(System.err);
			}
			//---------------判斷圖片的格式----------------------
			byte[] diaImg= null;
			String diaImgExtName = null;
			
			Part part = req.getPart("diaimg");
			
			
			if(!part.equals("")){
				if(part.getContentType().substring(0,5).equals("image")||part.getContentType().substring(0,5).equals("video")){
					diaImg = getByteArrayImg(part);
					diaImgExtName =  part.getContentType().substring(0,5);
				}
			}
//				GregorianCalendar speday = new GregorianCalendar(2011,12,3,23,49,22);
//				Timestamp diaCreTime =new Timestamp(speday.getTimeInMillis());
				
				Timestamp diaCreTime =new Timestamp(System.currentTimeMillis());
				
				Timestamp diaModTime =null;
				Integer diaState = 0; //indicate appearance
				
//				Diary diary = new Diary();
//				diary.setMemNo(member.getMemNo());
//				diary.setDiaName(diaName);
//				diary.setDiaCreTime(diaCreTime);
//				diary.setDiaModTime(diaModTime);
//				diary.setDiaText(diaText);
//				diary.setDiaImg(diaImg);
//				diary.setDiaState(diaState);
				
				//抓出來源網址的網頁
				String originSource = req.getParameter("originSource");
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("diaErr", diaErr);
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/diary/"+originSource+"?whichPage="+whichPage);
					failureView.forward(req, res);
					
					return;
				}
				
				DiaryService dsv =new DiaryService();
				dsv.addDia(member.getMemNo(), diaName, diaText, diaImg, diaCreTime, diaModTime, diaState, diaImgExtName);
				
				res.sendRedirect(req.getContextPath() + "/front_end/diary/"+originSource+"?whichPage="+whichPage);
				
		}
		
		if ("update".equals(action)) { // 來自jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Diary diaErr = new Diary();
			
			try {
				Integer diaNo = Integer.valueOf(req.getParameter("diano"));
				diaErr.setDiaNo(diaNo);
				//----------------判斷名稱是否為空值-------------------
				String diaName = null;
				try{
					diaName = req.getParameter("dianame").trim();
					if(diaName==null || diaName.isEmpty()){
						errorMsgs.add("請輸入發文名稱!");
					}
					if(diaName!=null && diaName.length()>30){
						errorMsgs.add("日誌標題長度過長!!");
					}
					
					//擋住script
					diaName = diaName.replace("<script>","");
					diaName = diaName.replace("</script>","");
					
					diaErr.setDiaName(diaName);
					
				}catch(NullPointerException e){
					e.printStackTrace(System.err);	
				}
				//----------------判斷內文是否為空值-------------------
				String diaText = null;
				
				try{
					diaText = req.getParameter("diatext").trim();
					if(diaText !=null && diaText.equals("")){
						errorMsgs.add("請輸入內文!");
					}
	
					//擋住script
					diaText = diaText.replace("<script>","");
					diaText = diaText.replace("</script>","");
					
					diaErr.setDiaText(diaText);
					
				}catch(Exception ne){
					ne.printStackTrace(System.err);
				}
				//---------------判斷圖片----------------------
				byte[] diaImg= null;	
				String diaImgExtName = null;
				try{
					Part part = req.getPart("diaimg");
						if(part.getContentType().substring(0,5).equals("image")||part.getContentType().substring(0,5).equals("video") ){
							diaImg = getByteArrayImg(part);
							diaImgExtName =  part.getContentType().substring(0,5);
							
						}else if(part.getSize()!=0){
							//格式錯誤
							errorMsgs.add("此非圖片或影片格式!");
						}else {
							//判斷如果沒上傳新的就沿用舊的
							DiaryService diaSvc = new DiaryService();
							Diary diaryOld = diaSvc.getOneDia(diaNo);
							diaImg = diaryOld.getDiaImg();
							diaImgExtName = diaryOld.getDiaImgExtName();
						}
			
				}catch(Exception e){
					e.printStackTrace(System.err);					
				}
					
					Timestamp diaModTime = new Timestamp(System.currentTimeMillis());;
					Integer diaState = 0; //indicate appearance

					
				String backpath = req.getParameter("backpath");	
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("backpath", backpath);
					req.setAttribute("diaErr", diaErr);
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/diary/updateDiary.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				DiaryService diaSvc = new DiaryService();
				Diary diary = diaSvc.updateDia(member.getMemNo(), diaName, diaText, diaImg, diaModTime, diaState, diaNo, diaImgExtName);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				
				
				req.setAttribute("backpath", backpath);
				req.setAttribute("diary", diary);
				String url = "/front_end/diary/"+backpath;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				req.setAttribute("diaErr", diaErr);
				errorMsgs.add("修改資料失敗: "+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/diary/updateDiary.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) { // 來自mydiary.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String originSource = null;
			try {
				/***************************1.接收請求參數****************************************/
				Integer diano = Integer.valueOf(req.getParameter("diano"));
				
				/***************************2.開始查詢資料****************************************/
				DiaryService diaSvc = new DiaryService();
				Diary diary = diaSvc.getOneDia(diano); 
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				//抓出來源網址的網頁
				originSource = req.getParameter("originSource");
				req.setAttribute("originSource", originSource+"?whichPage="+whichPage);         // 資料庫取出的diary物件,存入req
//				System.out.println("getONe: "+originSource+"?whichPage="+whichPage);
				req.setAttribute("diary", diary);
				String url = "/front_end/diary/updateDiary.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				//抓出來源網址的網頁
				originSource = req.getParameter("originSource");
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/diary/"+originSource+"?whichPage="+whichPage);
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String originSource = req.getParameter("originSource");
			
			try {
				/***************************1.接收請求參數***************************************/
				Integer diaNo = new Integer(req.getParameter("diano"));
				
				/***************************2.開始刪除資料***************************************/
				DiaryService diaSvc = new DiaryService();
				diaSvc.deleteDia(diaNo);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				
				String url = "/front_end/diary/"+originSource+"?whichPage="+whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/diary/"+originSource+"?whichPage="+whichPage);
				failureView.forward(req, res);
			}
		}
		
		
		
		
		
		
	}
	
	public byte[] getByteArrayImg(Part part){
		
		ByteArrayOutputStream diaimg=null;
		try{
			java.io.InputStream in =part.getInputStream(); 
			diaimg = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int i;
			while ((i = in.read(buffer)) != -1) {
				diaimg.write(buffer, 0, i);
			}
			diaimg.close();
			in.close();
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return diaimg.toByteArray();
	}
	
	
	
	

}
