//package com.dateitem.controller;
//
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//import javax.servlet.*;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.http.*;
//
////import com.dateitem.model.*;
////import com.dateitem.model.DateItemService;
////import com.dateitem.model.DateItemVO;
////import com.member.model.Member;
////import com.member.model.MemberService;
////import com.pet.model.Pet;
////import com.restaurant.model.Restaurant;
////import com.restaurant.model.RestaurantService;
//
////以下android package
//import com.PetYM.*;
//import com.PetYM.Member.Member;
//import com.PetYM.Member.MemberService;
//import com.PetYM.Pet.Pet;
//import com.PetYM.Restaurant.Restaurant;
//import com.PetYM.Restaurant.RestaurantService;
//import com.PetYM.puDateItem.DateItemService;
//import com.PetYM.puDateItem.DateItemVO;
//@MultipartConfig(fileSizeThreshold =500* 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 5 * 500 * 1024 * 1024)
//public class DateItemServlet extends HttpServlet {
//
//	public void doGet(HttpServletRequest req, HttpServletResponse res)
//			throws ServletException, IOException {
//		doPost(req, res);
//	}
//
//	public void doPost(HttpServletRequest req, HttpServletResponse res)
//			throws ServletException, IOException {
//
//		req.setCharacterEncoding("UTF-8");
//		res.setContentType("text/html; charset=Big5");
//		String action = req.getParameter("action");
//		
//		
//		
//		
//		//賣家同一個時間內是已經有上架商品
//		if("QRC".equals(action)){	
//				try {
//					int dateItemNo = Integer.parseInt(req.getParameter("dateImteNo"));
//					DateItemService dSvc = new DateItemService();
//					DateItemVO  dateItemVO = dSvc.getOneDateItem(dateItemNo);
//					dateItemVO.setDateItemStatus(3);
//                                        dSvc.updateByVO(dateItemVO);
//
//					
////	                撥款的部分	
////					MemberService mSvc = new MemberService();
////					Member seller =  mSvc.getOneMember(dateItemVO.getSellerNo());
////					int currentPoint = seller.getMemPoint();
////					seller.setMemPoint(currentPoint+dateItemVO.getDateItemPrice());
//					
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					System.out.println("Parse Error");
//				}
//		}		
//
//		// 檢查是否有寵物
//		if ("check_Seller".equals(action)) { 
//
//			
//			
//			MemberService memSvc = new MemberService();
//			List<Pet> myPetList = new ArrayList<Pet>();
//			
//			HttpSession session = req.getSession();
//			Member member = (Member) session.getAttribute("member");
//
//			myPetList = memSvc.getPetsByMemNo(member.getMemNo());
//			
//			
//			//沒寵物的使用者導到寵物註冊
//			if(myPetList.size()==0){
//				String url = "/front_end/pet/petRegister.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//			}else{//有寵物的使用者到上架
//			req.setAttribute("myPetList", myPetList);
//			String url = "/front_end/dateitem/addDateItem.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url);
//			successView.forward(req, res);
//		}								
//		}	
//		
//		//買家檢視已購買但未完成交易的商品
//		if ("list_buyer_future".equals(action)) { // 來自select_page.jsp上架約會商品
//			       
//			String url = "/front_end/dateitem/list_buyer_future.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
//			successView.forward(req, res);
//										
//		}
//		
//		//買家檢視已完成交易的歷史商品
//		if ("list_buyer_history".equals(action)) { // 來自select_page.jsp上架約會商品
//			        
//			String url = "/front_end/dateitem/list_buyer_history.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
//			successView.forward(req, res);
//										
//		}
//		
//		if("checkCharge".equals(action)){
//			HttpSession session = req.getSession();
//			Member member = (Member) session.getAttribute("member");
//			DateItemService dSvc = new DateItemService();
//			
//			System.out.println(req.getParameter("dateItemNo"));
//			DateItemVO dateItemVO =dSvc.findByPK(Integer.parseInt(req.getParameter("dateItemNo")));
//			if(member.getMemPoint()< dateItemVO.getDateItemPrice()) {
//				PrintWriter out = res.getWriter();
//				out.print("餘額不足餘額不足");
//			}else{
//				PrintWriter out = res.getWriter();
//				out.print(" ");
//			}
//			
//		}
//		
//		
//		//賣家同一個時間內是已經有上架商品
//		if("checkTime".equals(action)){
//			
//				HttpSession session = req.getSession();
//				Member member = (Member) session.getAttribute("member");
//				DateItemService dSvc = new DateItemService();
//				List<DateItemVO> list = dSvc.findBySeller_onsale(member.getMemNo());
//				List<DateItemVO> list2 = dSvc.findBySeller_future(member.getMemNo());
//				System.out.println("共有約會"+list.size());
//				String timeStr = req.getParameter("time");
//				System.out.println(timeStr);
//				
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//				try {
//					Date t1 = sdf.parse(timeStr);
//					System.out.println("t1"+t1);
//					int count=0;
//					for(DateItemVO dateItemVO:list){
//						//把每個商品跟目前要上架的時間都轉成long比較是否差距四小時
//						long diff = t1.getTime()- dateItemVO.getDateMeetingTime().getTime();
//						int diffhour = (int)(diff/(60 * 60 * 1000)) ;
//						if (diffhour<4 && diffhour>-4){
//							count++;
//						}
//						
//					}
//					for(DateItemVO dateItemVO:list2){
//						//把每個商品跟目前要上架的時間都轉成long比較是否差距四小時
//						long diff = t1.getTime()- dateItemVO.getDateMeetingTime().getTime();
//						int diffhour = (int)(diff/(60 * 60 * 1000)) ;				
//						if (diffhour<4 && diffhour>-4){
//							count++;
//						}
//						
//					}					
//					PrintWriter out = res.getWriter();
//					
//					out.print(count);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					System.out.println("Parse Error");
//				}
//		}					
//		
//		
//		
//		//買家購買一個商品
//		if("buy_date".equals(action)){
//		//檢查儲值
//			
//			
//			HttpSession session = req.getSession();
//			Member member = (Member) session.getAttribute("member");
//			int buyerNo = member.getMemNo();
//						
//						
//			Integer dateItemNo = new Integer(req.getParameter("dateItemNo").trim());
//			DateItemService dSvc = new DateItemService();
//			DateItemVO dateItemVO = dSvc.findByPK(dateItemNo);
//			
////			再次檢查該商品是否還沒被購買
//			if(dateItemVO.getDateItemShow()==0&&
//					dateItemVO.getDateItemStatus()==0){
//							
//			dateItemVO.setBuyerNo(buyerNo);
//			dateItemVO.setDateItemStatus(1);
//			dateItemVO.setDateItemShow(1);
//			dSvc.updateByVO(dateItemVO);
//			
//			//扣款
//			int currentPoint = member.getMemPoint();
//			member.setMemPoint(currentPoint-dateItemVO.getDateItemPrice());
//						
//			//把剛剛購買的物件setAttribute,並轉購買紀錄檢視
//			req.setAttribute("itemPurchased", dateItemVO);
//			String url = "/front_end/dateitem/list_buyer_future.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url);
//			successView.forward(req, res);
//			}
//			else{
//				req.setAttribute("itemNotFound", dateItemVO);
//				String url = "/front_end/dateitem/list_buyer_future.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);	
//			}
//			
//		}
//		
//		//取消一個商品
//		if("cancel_date".equals(action)){
//		//先跳過檢查儲值的部分
//			
//			
//						
//			Integer dateItemNo = new Integer(req.getParameter("dateItemNo").trim());
//			DateItemService dSvc = new DateItemService();
//			MemberService mSvc = new MemberService();
//			DateItemVO dateItemVO = dSvc.findByPK(dateItemNo);
//			
//			//修改狀態成為取消
//			dateItemVO.setDateItemStatus(2);
//			dateItemVO.setDateItemShow(1);
//			dSvc.updateByVO(dateItemVO);
//			
//			//儲值金額返還
//			int currentPoint = mSvc.getOneMember(dateItemVO.getBuyerNo()).getMemPoint();
//			mSvc.getOneMember(dateItemVO.getBuyerNo()).setMemPoint(currentPoint+dateItemVO.getDateItemPrice());
//			
//			//分辨取消來自買方還是賣方,分別導回買賣方的歷史紀錄
//			try {
//				
//				req.setAttribute("itemCanceled", dateItemVO);
//				if (req.getParameter("fromwho").equals("buyer")){
//					String url = "/front_end/dateitem/list_buyer_history.jsp";
//					RequestDispatcher successView = req.getRequestDispatcher(url);
//					successView.forward(req, res);
//					}
//				if	(req.getParameter("fromwho").equals("seller")){
//					String url = "/front_end/dateitem/list_seller_history.jsp";
//					RequestDispatcher successView = req.getRequestDispatcher(url);
//					successView.forward(req, res);
//					}
//			} catch (Exception e) {
//					String url = "/front_end/dateitem/select_page.jsp";
//					RequestDispatcher successView = req.getRequestDispatcher(url);
//					successView.forward(req, res);
//					}
//			}			
//		
//		
//		
//		
//            if ("insert".equals(action)) { // 來自addDateItem.jsp的請求  
//			
//            	List<String> errorMsgs = new LinkedList<String>();
//            
//            	// Store this set in the request scope, in case we need to
//            	// send the ErrorPage view.
//            	req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				
//				if (req.getParameter("time") == null || (req.getParameter("time").trim()).length() == 0) {
//					errorMsgs.add("請選擇時間");
//				}
//				
//				String htmltime = req.getParameter("time");
//				;
//				
//				Timestamp dateMeetingTime = getTimestamp(htmltime);
//				System.out.println(getTimestamp(htmltime));
//				Timestamp dateItemTime = new Timestamp(System.currentTimeMillis());
//				
//				// Send the use back to the form, if there were errors
//				
//				String dateItemTitle = req.getParameter("dateItemTitle");
//				if (dateItemTitle == null || (dateItemTitle.trim()).length() == 0) {
//					errorMsgs.add("請輸入標題");
//					dateItemTitle = "";
//				}
//				// Send the use back to the form, if there were errors
//				
//				String dateItemText = req.getParameter("dateItemText");
//				if (dateItemText == null || (dateItemText.trim()).length() == 0) {
//					errorMsgs.add("請輸入內容");
//					dateItemText = "";
//				}
//				// Send the use back to the form, if there were errors
//						
//				//驗證圖片跟日期=====================================================
//				byte[] dateItemImg= null;
//				String fileName=null;
//				Part part = req.getPart("dateItemImg");
//				
//				
//				
//					if(part.getSize()!=0){
//						fileName = getFileNameFromPart(part);
//						System.out.println(part.equals(null));
//						if(getServletContext().getMimeType(fileName).substring(0,5).equals("image")){
//							dateItemImg = getByteArrayImg(part);
//							System.out.println("圖片格式正確!");
//						}
//					}else {
//					errorMsgs.add("請上傳圖片");
//				}
//				
//				
//				
//
//		        //============================================================================		
//				
//				//如果錯誤回傳一個VO=====================================
//				
//				HttpSession session = req.getSession();
//				Member member = (Member) session.getAttribute("member");
//				DateItemVO dateItemVO = new DateItemVO();
//				dateItemVO.setSellerNo(member.getMemNo());
//				dateItemVO.setRestListNo(Integer.parseInt(req.getParameter("restListNo")));
//				dateItemVO.setPetNo(Integer.parseInt(req.getParameter("petNo")));
//				dateItemVO.setDateItemTitle(dateItemTitle);
//				dateItemVO.setDateItemText(dateItemText);
//				dateItemVO.setDateItemPrice(Integer.parseInt(req.getParameter("dateItemPrice")));
//			
//				
//				
//				
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					//重新夾帶寵物資訊
//					MemberService memSvc = new MemberService();
//					List<Pet> myPetList = new ArrayList<Pet>();
//					myPetList = memSvc.getPetsByMemNo(member.getMemNo());
//					
//					req.setAttribute("myPetList", myPetList);
//					
//					req.setAttribute("dateItemVO", dateItemVO); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/front_end/dateitem/addDateItem.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				
//
//				
//				/***************************2.開始新增資料***************************************/
//				//sellerno轉型
//				
//				int sellerno = member.getMemNo();
//				int restListNo = Integer.parseInt(req.getParameter("restListNo"));
//				int petNo = Integer.parseInt(req.getParameter("petNo"));
//				int dateItemPeople = Integer.parseInt(req.getParameter("dateItemPeople"));
//				Boolean hasMate = Boolean.parseBoolean((req.getParameter("hasMate")));
//				int dateItemPrice = Integer.parseInt(req.getParameter("dateItemPrice"));
//				//給變數在資料庫中的初始值
//				int dateItemStatus = 0 ;
//				int dateItemShow = 0;
//				int dateItemViewer = 0;
//				int buyerNo = 5010 ;
//				boolean isQRCChecked = false;
//				int buyerRep = 0 ;
//				int sellerRep = 0 ;
//				boolean isInstantDate = false;
//
//				//檢查是否抓到值
////				System.out.println(sellerno);
////				System.out.println(restListNo);
////				System.out.println(dateItemTitle);
////				System.out.println(dateItemText);
////				System.out.println(dateItemTime);
////				System.out.println(dateMeetingTime);
////				System.out.println(dateItemPeople);
////				System.out.println(hasMate);
////				System.out.println(dateItemPrice);
////				System.out.println(dateItemStatus);
////				System.out.println("===================");
////				System.out.println(dateItemShow);
////				System.out.println(dateItemViewer);
////				System.out.println(buyerNo);
////				System.out.println(isQRCChecked);
////				System.out.println(buyerRep);
////				System.out.println(sellerRep);
////				System.out.println(isInstantDate);
////				System.out.println(petNo);
//				
//				
//				RestaurantService rSvc = new RestaurantService();
//				Restaurant restaurant = rSvc.getOneRest(Integer.parseInt(req.getParameter("restListNo"))); 
//				String dateItemLocate = (restaurant.getRestAdd()).substring(0, 3);
//				System.out.println(dateItemLocate);
//				
//				
//				DateItemService dateItemSvc = new DateItemService();
//				System.out.println("準備新增");
//				dateItemVO = dateItemSvc.addDateItem(sellerno, restListNo, dateItemTitle, dateItemImg, dateItemText, dateItemTime, dateMeetingTime, dateItemLocate, dateItemPeople, hasMate, dateItemPrice, dateItemStatus, dateItemShow, dateItemViewer, buyerNo, isQRCChecked, buyerRep, sellerRep, isInstantDate, petNo);
//				
//				/***************************3.新增完成,準備轉交(Send the Success view)***********/
//				req.setAttribute("itemAdded", dateItemVO);
//				String url = "/front_end/dateitem/list_seller_onsale.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
//				successView.forward(req, res);				
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				HttpSession session = req.getSession();
//				Member member = (Member) session.getAttribute("member");
//				MemberService memSvc = new MemberService();
//				List<Pet> myPetList = new ArrayList<Pet>();
//				myPetList = memSvc.getPetsByMemNo(member.getMemNo());
//				
//				req.setAttribute("myPetList", myPetList);
//				errorMsgs.add("請輸入完整商品資訊");
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front_end/dateitem/addDateItem.jsp");
//				failureView.forward(req, res);
//			}
//		}
//	}
//	
//	public byte[] getByteArrayImg(Part part){
//		
//		ByteArrayOutputStream diaimg=null;
//		try{
//			java.io.InputStream in =part.getInputStream(); 
//			diaimg = new ByteArrayOutputStream();
//			byte[] buffer = new byte[8192];
//			int i;
//			while ((i = in.read(buffer)) != -1) {
//				diaimg.write(buffer, 0, i);
//			}
//			diaimg.close();
//			in.close();
//			
//			
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		
//		return diaimg.toByteArray();
//	}
//	
//	public String getFileNameFromPart(Part part) {
//		String header = part.getHeader("content-disposition");
//		String filename = header.substring(header.lastIndexOf("=") + 2, header.length() - 1);
//		if (filename.length() == 0) {
//			return null;
//		}
//		return filename;
//	}
//	
//	//將Html5 date轉為Timestamp的Method
//	
////	public Timestamp getTimestamp(String dateStr){	
////		int yyyy= Integer.parseInt((dateStr.substring(0, 4)));
////		int mm= Integer.parseInt((dateStr.substring(5, 7)));
////		int dd= Integer.parseInt((dateStr.substring(8, 10)));
////		int hh= Integer.parseInt((dateStr.substring(11, 13)));
////		int minute= Integer.parseInt((dateStr.substring(14, 16)));
////	GregorianCalendar cal = new GregorianCalendar(yyyy,mm-1,dd,hh,minute,0);
////	java.util.Date ud = cal.getTime();
////	Timestamp ts= new Timestamp(ud.getTime());
////	return ts;	
////	}
//	
//	public Timestamp getTimestamp(String dateStr){	
//		int yyyy= Integer.parseInt((dateStr.substring(0, 4)));
//		int mm= Integer.parseInt((dateStr.substring(5, 7)));
//		int dd= Integer.parseInt((dateStr.substring(8, 10)));
//		int hh= Integer.parseInt((dateStr.substring(11, 13)));
//		int minute= Integer.parseInt((dateStr.substring(14, 16)));
//	GregorianCalendar cal = new GregorianCalendar(yyyy,mm-1,dd,hh,minute,0);
//	java.util.Date ud = cal.getTime();
//	Timestamp ts= new Timestamp(ud.getTime());
//	return ts;	
//	}
//			
////  調整圖片大小	
////	private static BufferedImage resizeImage(BufferedImage originalImage,int type){
////		BufferedImage resizedImage = new BufferedImage(150,150,type);
////		Graphics2D g = resizedImage.createGraphics();
////		g.drawImage(originalImage, 0, 0, 150, 150, null);
////		g.dispose();
////		
////		return resizedImage;
////	}
////	
//}
//	
//
