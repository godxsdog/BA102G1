package com.faq.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.Emp;
import com.emp.model.EmpService;
import com.faq.model.*;
import com.faq.model.FaqService;
import com.member.model.Member;

//@WebServlet("/back_end/faq/faq.do")
public class FaqServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost( req,  res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session=req.getSession();
		Emp emp=(Emp)session.getAttribute("emp");
		
		
//		用pk查
		if ("getFaqNo_For_Display".equals(action)) { // 來自back_end/about_us/back_end/about_us/faq/faq_select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("faqNo");
				System.out.println(str);
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer faqNo = null;
				try {
					faqNo = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FaqService faqService = new FaqService();
				Faq faq = faqService.findByPrimaryKey(Integer.valueOf(faqNo));
				if (faqNo == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("faq", faq); // 資料庫取出的empVO物件,存入req
				String url = "/back_end/about_us/faq/listFaqNo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
//		用category查
		if ("getRealtive_For_Category".equals(action)) { // 來自back_end/about_us/faq/faq_select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String faqCategory = req.getParameter("faqCategory");
				if (faqCategory == null || (faqCategory.trim()).length() == 0) {
					errorMsgs.add("請輸入類別");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FaqService faqService = new FaqService();
				List<Faq> faqList = faqService.findByCategory(faqCategory);
				if (faqList.isEmpty()) {
					errorMsgs.add("找不到這個類別喔");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("faqList", faqList); // 資料庫取出的empVO物件,存入req
				String url = "/back_end/about_us/faq/listRealtive.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
//		給前端用的category
		if ("faq_for_user".equals(action)) { // 來自back_end/about_us/faq/faq_select_page.jsp的請求

				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String faqCategory = req.getParameter("faqCategory");
				System.out.println(faqCategory);
				/***************************2.開始查詢資料*****************************************/
				FaqService faqService = new FaqService();
				List<Faq> faqList = faqService.findByCategory(faqCategory);

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("faqList", faqList); // 資料庫取出的empVO物件,存入req
				String url = "/front_end/about_us/faq/faq_user.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

		}
		
//		給前端用的(按category)				吳神說留著紀念
//		if ("faq_for_user".equals(action)) { // 來自back_end/about_us/faq/faq_select_page.jsp的請求
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String faqCategory = req.getParameter("faqCategory");
//				
//				System.out.println(faqCategory);
//				/***************************2.開始查詢資料*****************************************/
//				FaqService faqService = new FaqService();
//				List<Faq> faqList = faqService.findByCategory(faqCategory);
//
//				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("faqList", faqList); // 資料庫取出的empVO物件,存入req
//				String url = "/front_end/about_us/faq/faq_user.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
//				successView.forward(req, res);
//				return;
//
//		}

//		用title查
		if ("getRealtive_For_Title".equals(action)) { // 來自back_end/about_us/faq/faq_select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String faqTitle = req.getParameter("faqTitle");
				if (faqTitle == null || (faqTitle.trim()).length() == 0) {
					errorMsgs.add("請輸入標題");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FaqService faqService = new FaqService();
				List<Faq> faqList = faqService.findByTitle(faqTitle);
				if (faqList.isEmpty()) {
					errorMsgs.add("沒有這個標題喔");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("faqList", faqList); // 資料庫取出的empVO物件,存入req
				String url = "/back_end/about_us/faq/listRealtive.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		

		
//		用answer查
		if ("getRealtive_For_Answer".equals(action)) { // 來自back_end/about_us/faq/faq_select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String faqAnswer = req.getParameter("faqAnswer");
				if (faqAnswer == null || (faqAnswer.trim()).length() == 0) {
					errorMsgs.add("請輸入解答");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FaqService faqService = new FaqService();
				List<Faq> faqList = faqService.findByAnswer(faqAnswer);
				if (faqList.isEmpty()) {
					errorMsgs.add("找不到這個答案欸");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("faqList", faqList); // 資料庫取出的empVO物件,存入req
				String url = "/back_end/about_us/faq/listRealtive.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/about_us/faq/faq_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
//		還沒改完
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】		
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer faqNo = new Integer(req.getParameter("faqNo"));
				
				/***************************2.開始查詢資料****************************************/
				FaqService faqService = new FaqService();
				Faq faq = faqService.findByPrimaryKey(faqNo);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("faq", faq); // 資料庫取出的empVO物件,存入req
//				這邊還沒改
				String url = "/back_end/about_us/faq/update_Faq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
//		update
		if("update".equals(action)){
			Integer faqNo = new Integer(req.getParameter("faqNo"));
			FaqService faqService = new FaqService();
			Faq updateByPk = faqService.findByPrimaryKey(faqNo);
			req.setAttribute("faqUpdate", updateByPk);
			RequestDispatcher update = req.getRequestDispatcher("/back_end/about_us/faq/update_Faq.jsp");
			update.forward(req, res);
		}
			
//		update success
		if("updateSuccess".equals(action)){
			Integer faqNo = new Integer(req.getParameter("faqNo"));
			Integer empNo = emp.getEmpNo();
			String faqCategory = new String(req.getParameter("faqCategory"));
			String faqTitle = new String(req.getParameter("faqTitle"));
			String faqAnswer = new String(req.getParameter("faqAnswer"));
			FaqService faqService = new FaqService();
			faqService.update(faqNo, empNo, faqCategory, faqTitle, faqAnswer);
			
//			req.setAttribute("list", safeUpdate);
//			RequestDispatcher backToList = req.getRequestDispatcher("/back_end/about_us/faq/listAllFaq.jsp");
//			
//			backToList.forward(req, res);
			res.sendRedirect(req.getContextPath()+"/back_end/about_us/faq/listAllFaq.jsp");
		}

//		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
//						
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
//			
//			RequestDispatcher update_news = req.getRequestDispatcher("/back_end/news/update_News.jsp");
//			update_news.forward(req,res);
//			
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String newsTitle = req.getParameter("newsTitle").trim();
//				if(newsTitle.isEmpty()){
//					errorMsgs.add("請填入標題");
//				}
//							
//				String newsContent = req.getParameter("newsContent").trim();								
//				if(newsContent.isEmpty()){
//					errorMsgs.add("請填入內容");
//				}
//				
//				News news = new News();
//				news.setNewsTitle(newsTitle);
//				news.setNewsContent(newsContent);
//				
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("news", news); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back_end/news/update_News.jsp");
//					failureView.forward(req, res);
//					return; //程式中斷
//				}
//				
//				/***************************2.開始修改資料*****************************************/
//				NewsService newsService = new NewsService();
//				Integer newsNo = new Integer(req.getParameter("newsNo"));
//				Integer empNo = new Integer(req.getParameter("empNo"));
//				String newsTitle = new String(req.getParameter("newsTitle"));
//				String newsContent = new String (req.getParameter("newsContent"));
//				Timestamp newsDate = java.sql.Timestamp.valueOf(req.getParameter("newsDate"));
//				news = newsService.update(newsNo, empNo, newsTitle, newsContent, newsDate);
//				
//				/***************************3.修改完成,準備轉交(Send the Success view)*************/				
//				if(requestURL.equals("/back_end/news/listAllNews.jsp"))
//					req.setAttribute("/back_end/news/listAllNews.jsp", newsService.getAll()); // 資料庫取出的list物件,存入request
//
//                String url = requestURL;
//				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back_end/about_us/news/update_News.jsp");
//				failureView.forward(req, res);
//			}
//		}

//		新增還沒改完
        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			
				Integer empNo = emp.getEmpNo();				
				
				String faqCategory = req.getParameter("faqCategory").trim();
				if(faqCategory.isEmpty()){
					errorMsgs.add("請填入標題");
				}
				System.out.println(faqCategory);
				
				String faqTitle = req.getParameter("faqTitle").trim();
				if(faqTitle.isEmpty()){
					errorMsgs.add("請填入標題");
				}
							
				String faqAnswer = req.getParameter("faqAnswer").trim();								
				if(faqTitle.isEmpty()){
					errorMsgs.add("請填入內容");
				}
				
				Faq faq = new Faq();
				faq.setEmpNo(empNo);
				faq.setFaqCategory(faqCategory);
				faq.setFaqTitle(faqTitle);
				faq.setFaqAnswer(faqAnswer);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("faq", faq); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/about_us/faq/addFaq.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始新增資料***************************************/
				FaqService faqService = new FaqService();
				faq = faqService.insert( empNo, faqCategory, faqTitle, faqAnswer);
				System.out.println(req.getParameter("safe_submit"));
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				
				String url = "/back_end/about_us/faq/listAllFaq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/about_us/faq/addFaq.jsp");
				failureView.forward(req, res);
			}
		}
        
//      user用的新增
        if ("user_insert".equals(action)) { // 來自addEmp.jsp的請求  
			System.out.println(req.getParameter("faqCategory").trim());
			System.out.println(req.getParameter("faqTitle").trim());
			System.out.println(req.getParameter("faqAnswer").trim());
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println(session.getAttribute("errorMsgs"));
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			
				Integer empNo = emp.getEmpNo();				
				
				String faqCategory = req.getParameter("faqCategory").trim();
				if(faqCategory.isEmpty()){
					errorMsgs.add("請填入標題");
				}
				System.out.println(faqCategory);
				
				String faqTitle = req.getParameter("faqTitle").trim();
				if(faqTitle.isEmpty()){
					errorMsgs.add("請填入標題");
				}
							
				String faqAnswer = req.getParameter("faqAnswer").trim();								
				if(faqTitle.isEmpty()){
					errorMsgs.add("請填入內容");
				}
				
				Faq faq = new Faq();
//				faq.setEmpNo(empNo);
//				faq.setFaqCategory(faqCategory);
//				faq.setFaqTitle(faqTitle);
//				faq.setFaqAnswer(faqAnswer);
				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("faq", faq); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back_end/about_us/faq/addFaq.jsp");
//					failureView.forward(req, res);
//					return; //程式中斷
//				}
				
				/***************************2.開始新增資料***************************************/
				System.out.println(req.getParameter("safe_submit"));
				FaqService faqService = new FaqService();
				faqService.insert( empNo, faqCategory, faqTitle, faqAnswer);
				System.out.println(req.getParameter("safe_submit"));
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				
				String url = "/back_end/about_us/faq/listAllFaq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/about_us/faq/faq_user.jsp");
				failureView.forward(req, res);
			}
		}
		
     
		if ("delete".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】

			try {
				/***************************1.接收請求參數***************************************/
				Integer faqNo = new Integer(req.getParameter("faqNo"));
				
				/***************************2.開始刪除資料***************************************/
				FaqService faqService = new FaqService();
				Faq faq = faqService.findByPrimaryKey(faqNo);
				faqService.delete(faqNo);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				
//				這邊還沒改
				if(requestURL.equals("/faq/listAllFaq.jsp")){
					List<Faq> faqList = faqService.getAll();
					req.setAttribute("listAllFaq", faq);  //資料庫取出的list物件,存入request
				}
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
//				req.setAttribute("faq", faq); // 資料庫取出的empVO物件,存入req
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
}