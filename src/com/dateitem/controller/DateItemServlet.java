package com.dateitem.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.lettertype.model.LetterTypeService;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.dateitem.model.*;
import com.dateitem.model.DateItemService;
import com.dateitem.model.DateItemVO;
import com.letter.model.LetterService;
import com.member.model.Member;
import com.member.model.MemberService;
import com.pet.model.Pet;
import com.pet.model.PetService;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantService;
@MultipartConfig(fileSizeThreshold =500* 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 5 * 500 * 1024 * 1024)
public class DateItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=Big5");
		String action = req.getParameter("action");
		
		
		
		
		//賣家同一個時間內是已經有上架商品
		if("QRC".equals(action)){	
				try {
					int dateItemNo = Integer.parseInt(req.getParameter("dateImteNo"));
					DateItemService dSvc = new DateItemService();
					DateItemVO  dateItemVO = dSvc.getOneDateItem(dateItemNo);
					dateItemVO.setDateItemStatus(3);
					dSvc.updateByVO(dateItemVO);

					
//	                撥款的部分	
//					MemberService mSvc = new MemberService();
//					Member seller =  mSvc.getOneMember(dateItemVO.getSellerNo());
//					int currentPoint = seller.getMemPoint();
//					seller.setMemPoint(currentPoint+dateItemVO.getDateItemPrice());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Parse Error");
				}
		}		

		// 檢查是否有寵物
		if ("check_Seller".equals(action)) { 

			
			
			MemberService memSvc = new MemberService();
			List<Pet> myPetList = new ArrayList<Pet>();
			
			HttpSession session = req.getSession();
			Member member = (Member) session.getAttribute("member");

			if(member==null){
				String url = "/front_end/member/login.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}else{
			
			
			myPetList = memSvc.getPetsByMemNo(member.getMemNo());
			
			
			//沒寵物的使用者導到寵物註冊
			if(myPetList.size()==0){
				String url = "/front_end/pet/petRegister.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}else{//有寵物的使用者到上架
			req.setAttribute("myPetList", myPetList);
			String url = "/front_end/dateitem/addDateItem.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
			}
		}	
		
		//買家檢視已購買但未完成交易的商品
		if ("list_buyer_future".equals(action)) { // 來自select_page.jsp上架約會商品
			       
			String url = "/front_end/dateitem/list_buyer_future.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
										
		}
		
		//買家檢視已完成交易的歷史商品
		if ("list_buyer_history".equals(action)) { // 來自select_page.jsp上架約會商品
			        
			String url = "/front_end/dateitem/list_buyer_history.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
										
		}
		
		if("checkCharge".equals(action)){
			HttpSession session = req.getSession();
			Member member = (Member) session.getAttribute("member");
			DateItemService dSvc = new DateItemService();
			
			System.out.println(req.getParameter("dateItemNo"));
			DateItemVO dateItemVO =dSvc.findByPK(Integer.parseInt(req.getParameter("dateItemNo")));
			if(member.getMemPoint()< dateItemVO.getDateItemPrice()) {
				PrintWriter out = res.getWriter();
				out.print("餘額不足餘額不足");
			}else{
				PrintWriter out = res.getWriter();
				out.print(" ");
			}
			
		}
		
		
		//賣家同一個時間內是已經有上架商品
		if("checkTime".equals(action)){
			
				HttpSession session = req.getSession();
				Member member = (Member) session.getAttribute("member");
				DateItemService dSvc = new DateItemService();
				List<DateItemVO> list = dSvc.findBySeller_onsale(member.getMemNo());
				List<DateItemVO> list2 = dSvc.findBySeller_future(member.getMemNo());
				System.out.println("共有約會"+list.size());
				String timeStr = req.getParameter("time");
				System.out.println(timeStr);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date t1 = sdf.parse(timeStr);
					System.out.println("t1"+t1);
					int count=0;
					for(DateItemVO dateItemVO:list){
						//把每個商品跟目前要上架的時間都轉成long比較是否差距四小時
						long diff = t1.getTime()- dateItemVO.getDateMeetingTime().getTime();
						int diffhour = (int)(diff/(60 * 60 * 1000)) ;
						if (diffhour<4 && diffhour>-4){
							count++;
						}
						
					}
					for(DateItemVO dateItemVO:list2){
						//把每個商品跟目前要上架的時間都轉成long比較是否差距四小時
						long diff = t1.getTime()- dateItemVO.getDateMeetingTime().getTime();
						int diffhour = (int)(diff/(60 * 60 * 1000)) ;				
						if (diffhour<4 && diffhour>-4){
							count++;
						}
						
					}					
					PrintWriter out = res.getWriter();
					
					out.print(count);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println("Parse Error");
				}
		}					
		
		
		
		//買家購買一個商品
		if("buy_date".equals(action)){
		//檢查儲值
			
			
			HttpSession session = req.getSession();
			Member member = (Member) session.getAttribute("member");
			int buyerNo = member.getMemNo();
			
			Integer dateItemNo = new Integer(req.getParameter("dateItemNo").trim());
			DateItemService dSvc = new DateItemService();
			DateItemVO dateItemVO = dSvc.findByPK(dateItemNo);
			
			if(member.getMemPoint()>dateItemVO.getDateItemPrice()){
			
			//call另一個類別中 執行續安全的方法:購買
			BuyDateItem buyDateItem = new BuyDateItem();
			Boolean result= buyDateItem.buyAItem(buyerNo, dateItemVO);
			//把剛剛購買的物件setAttribute,並轉購買紀錄檢視
			if (result==true){
				req.setAttribute("itemPurchased", dateItemVO);
				String url = "/front_end/dateitem/list_buyer_future.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				LetterService lSvc = new LetterService();
				lSvc.addLtrOfDateBeBought(dateItemVO);
				lSvc.addLtrOfBuyDateItemSucess(dateItemVO);
				MemberService mSvc = new MemberService();
				int currentPoint = member.getMemPoint();
				member.setMemPoint(currentPoint-dateItemVO.getDateItemPrice());
				session.setAttribute("member", member);
				}else{
					req.setAttribute("itemNotFound", dateItemVO);
					String url = "/front_end/dateitem/list_buyer_future.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);	
				}
			
			}else{
				req.setAttribute("itemNotFound", dateItemVO);
				String url = "/front_end/dateitem/list_buyer_future.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);	
			}
		}
				
				
		if("reinsert".equals(action)){
		//重新上架商品
			
			HttpSession session = req.getSession();
			Member member = (Member) session.getAttribute("member");
			int sellerNo = member.getMemNo();
			int dateItemNo = Integer.parseInt(req.getParameter("dateItemNo"));
			DateItemService dSvc = new DateItemService();
			DateItemVO dateItemVO = dSvc.findByPK(dateItemNo);
			
			try {

				
				//檢查按下連結的確實為賣家並且該商品確實被取消
				if(sellerNo==dateItemVO.getSellerNo()&& dateItemVO.getDateItemShow()==1 && dateItemVO.getDateItemStatus()==2){
					//檢查該商品未過期
					if(dateItemVO.getDateMeetingTime().getTime()>System.currentTimeMillis()){
						dSvc.addDateItem(sellerNo, dateItemVO.getRestListNo(), dateItemVO.getDateItemTitle(), dateItemVO.getDateItemImg(),
						dateItemVO.getDateItemText(), new Timestamp(System.currentTimeMillis()), dateItemVO.getDateMeetingTime(), dateItemVO.getDateItemLocate(), dateItemVO.getDateItemPeople(),
						dateItemVO.getHasMate(), dateItemVO.getDateItemPrice(), 0, 0, dateItemVO.getDateItemViewer(), 5010,
						dateItemVO.getIsQRCChecked(), dateItemVO.getBuyerRep(), dateItemVO.getSellerRep(), dateItemVO.getIsInstantDate() , dateItemVO.getPetNo());
						req.setAttribute("itemAdded", dateItemVO);
						String url = "/front_end/dateitem/list_seller_onsale.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
						successView.forward(req, res);	
					}
				}else{
					req.setAttribute("canNotReinsert", dateItemVO);
					String url = "/front_end/dateitem/list_seller_onsale.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);	
				}
			} catch (Exception e) {
					req.setAttribute("canNotReinsert", dateItemVO);
					String url = "/front_end/dateitem/list_seller_onsale.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					}
			}				
			
			

			
						
			

		
		
		
		//取消一個商品
		if("cancel_date".equals(action)){
		//先跳過檢查儲值的部分
			
			
						
			Integer dateItemNo = new Integer(req.getParameter("dateItemNo").trim());
			DateItemService dSvc = new DateItemService();
			MemberService mSvc = new MemberService();
			DateItemVO dateItemVO = dSvc.findByPK(dateItemNo);
			
			//修改狀態成為取消
			dateItemVO.setDateItemStatus(2);
			dateItemVO.setDateItemShow(1);
			dSvc.updateByVO(dateItemVO);
			
			//儲值金額返還
			int currentPoint = mSvc.getOneMember(dateItemVO.getBuyerNo()).getMemPoint();
			mSvc.getOneMember(dateItemVO.getBuyerNo()).setMemPoint(currentPoint+dateItemVO.getDateItemPrice());
			HttpSession session = req.getSession();
			Member member = (Member) session.getAttribute("member");
			member.setMemPoint(currentPoint+dateItemVO.getDateItemPrice());
			session.setAttribute("member", member);
			
			//分辨取消來自買方還是賣方,分別導回買賣方的歷史紀錄
			try {
				
				req.setAttribute("itemCanceled", dateItemVO);
				if (req.getParameter("fromwho").equals("buyer")){
					LetterService lSvc = new LetterService();
					lSvc.addLtrOfBuyerCancelDate(dateItemVO);
					
					String url = "/front_end/dateitem/list_buyer_history.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					}
				if	(req.getParameter("fromwho").equals("seller")){
					String url = "/front_end/dateitem/list_seller_history.jsp";
					LetterService lSvc = new LetterService();
					lSvc.addLtrOfSellerCancelDate(dateItemVO);
					
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					}
			} catch (Exception e) {
					String url = "/front_end/dateitem/select_page.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					}
			}			
		
		
		
		
            if ("insert".equals(action)) { // 來自addDateItem.jsp的請求  
			
            	List<String> errorMsgs = new LinkedList<String>();
            
            	// Store this set in the request scope, in case we need to
            	// send the ErrorPage view.
            	req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				if (req.getParameter("time") == null || (req.getParameter("time").trim()).length() == 0) {
					errorMsgs.add("請選擇時間");
				}
				
				String htmltime = req.getParameter("time");
				;
				
				Timestamp dateMeetingTime = getTimestamp(htmltime);
				System.out.println(getTimestamp(htmltime));
				Timestamp dateItemTime = new Timestamp(System.currentTimeMillis());
				
				// Send the use back to the form, if there were errors
				
				String dateItemTitle = req.getParameter("dateItemTitle");
				if (dateItemTitle == null || (dateItemTitle.trim()).length() == 0 ) {
					errorMsgs.add("請輸入標題");
					dateItemTitle = "";
				}
				// Send the use back to the form, if there were errors
				
				String dateItemText = req.getParameter("dateItemText");
				if (dateItemText == null || (dateItemText.trim()).length() == 0) {
					errorMsgs.add("請輸入內容");
					dateItemText = "";
				}
				// Send the use back to the form, if there were errors
						
				//驗證圖片跟日期=====================================================
				byte[] dateItemImg= null;
				String fileName=null;
				Part part = req.getPart("dateItemImg");
				
				
				
					if(part.getSize()!=0){
						fileName = getFileNameFromPart(part);
						System.out.println(part.equals(null));
						if(getServletContext().getMimeType(fileName).substring(0,5).equals("image")){
							dateItemImg = getByteArrayImg(part);
							System.out.println("圖片格式正確!");
						}
					}else {
					errorMsgs.add("請上傳圖片");
				}
				
				
				

		        //============================================================================		
				
				//如果錯誤回傳一個VO=====================================
				
				HttpSession session = req.getSession();
				Member member = (Member) session.getAttribute("member");
				DateItemVO dateItemVO = new DateItemVO();
				
				dateItemVO.setSellerNo(member.getMemNo());
				
				dateItemVO.setRestListNo(Integer.parseInt(req.getParameter("restListNo")));
			
				dateItemVO.setPetNo(Integer.parseInt(req.getParameter("petNo")));
				dateItemVO.setDateItemTitle(dateItemTitle);
			
				dateItemVO.setDateItemText(dateItemText);
				dateItemVO.setDateItemPrice(Integer.parseInt(req.getParameter("dateItemPrice")));
			
			
				
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					//重新夾帶寵物資訊
					MemberService memSvc = new MemberService();
					List<Pet> myPetList = new ArrayList<Pet>();
					myPetList = memSvc.getPetsByMemNo(member.getMemNo());
					
					req.setAttribute("myPetList", myPetList);
					
					req.setAttribute("dateItemVO", dateItemVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/dateitem/addDateItem.jsp");
					failureView.forward(req, res);
					return;
				}
				

				
				/***************************2.開始新增資料***************************************/
				//sellerno轉型
				
				int sellerno = member.getMemNo();
				int restListNo = Integer.parseInt(req.getParameter("restListNo"));
				int petNo = Integer.parseInt(req.getParameter("petNo"));
				int dateItemPeople = Integer.parseInt(req.getParameter("dateItemPeople"));
				Boolean hasMate = Boolean.parseBoolean((req.getParameter("hasMate")));
				int dateItemPrice = Integer.parseInt(req.getParameter("dateItemPrice"));
				//給變數在資料庫中的初始值
				int dateItemStatus = 0 ;
				int dateItemShow = 0;
				int dateItemViewer = 0;
				int buyerNo = 5100 ;
				boolean isQRCChecked = false;
				int buyerRep = 0 ;
				int sellerRep = 0 ;
				boolean isInstantDate = false;

				//檢查是否抓到值
//				System.out.println(sellerno);
//				System.out.println(restListNo);
//				System.out.println(dateItemTitle);
//				System.out.println(dateItemText);
//				System.out.println(dateItemTime);
//				System.out.println(dateMeetingTime);
//				System.out.println(dateItemPeople);
//				System.out.println(hasMate);
//				System.out.println(dateItemPrice);
//				System.out.println(dateItemStatus);
//				System.out.println("===================");
//				System.out.println(dateItemShow);
//				System.out.println(dateItemViewer);
//				System.out.println(buyerNo);
//				System.out.println(isQRCChecked);
//				System.out.println(buyerRep);
//				System.out.println(sellerRep);
//				System.out.println(isInstantDate);
//				System.out.println(petNo);
//				
//				System.out.println("66");
				RestaurantService rSvc = new RestaurantService();
//				System.out.println("===================2");
				Restaurant restaurant = rSvc.getOneRest(Integer.parseInt(req.getParameter("restListNo"))); 
//				System.out.println("===================1");
				String dateItemLocate = restaurant.getRestLocate();
//				System.out.println(dateItemLocate);
				
				System.out.println("77");
				DateItemService dateItemSvc = new DateItemService();
				System.out.println("準備新增");
				System.out.println("88");
				dateItemVO = dateItemSvc.addDateItem(sellerno, restListNo, dateItemTitle, dateItemImg, dateItemText, dateItemTime, dateMeetingTime, dateItemLocate, dateItemPeople, hasMate, dateItemPrice, dateItemStatus, dateItemShow, dateItemViewer, buyerNo, isQRCChecked, buyerRep, sellerRep, isInstantDate, petNo);
				System.out.println("99");
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("itemAdded", dateItemVO);
				String url = "/front_end/dateitem/list_seller_onsale.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				HttpSession session = req.getSession();
				Member member = (Member) session.getAttribute("member");
				MemberService memSvc = new MemberService();
				List<Pet> myPetList = new ArrayList<Pet>();
				myPetList = memSvc.getPetsByMemNo(member.getMemNo());
				
				req.setAttribute("myPetList", myPetList);
				errorMsgs.add("請輸入完整商品資訊");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/dateitem/addDateItem.jsp");
				failureView.forward(req, res);
			}
		}
            
            
            
            
    		// 來自select_page.jsp複合查詢約會商品
    		if ("listDItems_ByCompositeQuery".equals(action)) {

    			List<String> errorMsgs = new LinkedList<String>();
    			// Store this set in the request scope, in case we need to
    			// send the ErrorPage view.
    			req.setAttribute("errorMsgs", errorMsgs);

    			try {

    				/*************************** 1.將輸入資料轉為Map **********************************/
    				// 採用Map<String,String[]> getParameterMap()的方法
    				// 注意:an immutable java.util.Map
    				// Map<String, String[]> map = req.getParameterMap();
//    				HashMap<String, String[]> map = (HashMap<String, String[]>) req.getParameterMap();
    				HttpSession session = req.getSession();
    				Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
    				if (req.getParameter("whichPage") == null) {
    					HashMap<String, String[]> map1 = (HashMap<String, String[]>) req.getParameterMap();
    					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
    					map2 = (HashMap<String, String[]>) map1.clone();
    					session.setAttribute("map", map2);
    					map = (HashMap<String, String[]>) req.getParameterMap();
    				}
    				
    				/*************************** 2.開始複合查詢 ***************************************/
    				DateItemService dateItemSvc = new DateItemService();
    				List<DateItemVO> list = dateItemSvc.getAll(map);
    				MemberService memSvc=new MemberService();
    				PetService petSvc=new PetService();
    				System.out.println("******************************新測試*************************************");
    				for(DateItemVO dateItem:list){
    					System.out.println("商品編號: :"+dateItem.getDateItemNo());
    					System.out.println("商品約會時間: :"+dateItem.getDateMeetingTime());
    					System.out.println("會元性別: "+memSvc.getOneMember(dateItem.getBuyerNo()).getMemGender());
    					System.out.println("寵物種類: "+petSvc.getOnePet(dateItem.getPetNo()).getPetKind());
    					System.out.println("===================================");
    				}
    				/**************************** 3.查詢完成,準備轉交(Send the Success view)************/
    				req.setAttribute("listEmps_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
    				RequestDispatcher successView = req.getRequestDispatcher("/front_end/dateitem/compositeQuery.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
    				successView.forward(req, res);

    				/*************************** 其他可能的錯誤處理 **********************************/
    			} catch (Exception e) {
    				errorMsgs.add(e.getMessage());
    				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dateitem/compositeQuery.jsp");
    				failureView.forward(req, res);
    			}
    		}
    		
    		
    		// 來自googleMapQuery.jsp複合查詢約會商品
    		if ("googleMapQuery".equals(action)) {

    			List<String> errorMsgs = new LinkedList<String>();
    			// Store this set in the request scope, in case we need to
    			// send the ErrorPage view.
    			req.setAttribute("errorMsgs", errorMsgs);
    			
    			try {

    				/*************************** 1.將輸入資料轉為Map **********************************/
    				String date = null;
    				try{
    					date=req.getParameter("dateMeetingTime");
    				}
    				catch(Exception e){
    					errorMsgs.add("日期有誤");
    				}
    				/*************************** 2.開始複合查詢 ***************************************/
    				DateItemService dateItemSvc = new DateItemService();
    				List<SDateItemVO> list = dateItemSvc.findByDate(date);
    			    
    				//下面四行是印印看此list共多大
    				ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			    ObjectOutputStream out = new ObjectOutputStream(baos);
    			    out.writeObject(list);
    			    out.close();
    			    System.out.print(list.size());
    			    System.out.println(list.getClass().getSimpleName() +" used " + baos.toByteArray().length + " bytes");
    				


    				/**************************** 3.查詢完成,準備轉交(Send the Success view)************/
    				HttpSession session=req.getSession();
    				req.setAttribute("googleMaplist", list); // 資料庫取出的list物件,存入request
    				req.setAttribute("date", date);
    				session.setAttribute("result",list);
    				RequestDispatcher successView = req.getRequestDispatcher("/front_end/dateitem/gMapSearchResult.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
    				successView.forward(req, res);

    				/*************************** 其他可能的錯誤處理 **********************************/
    			} catch (Exception e) {
    				errorMsgs.add(e.getMessage());
    				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dateitem/googleMapQuery.jsp");
    				failureView.forward(req, res);
    			}
    		}
    		
    		
    		
    		
    		// 來自googleMapQuery.jsp查詢各餐廳約會商品
    		if ("showDItemFromMap".equals(action)) {

    			List<String> errorMsgs = new LinkedList<String>();
    			// Store this set in the request scope, in case we need to
    			// send the ErrorPage view.
    			req.setAttribute("errorMsgs", errorMsgs);

    			try {

    				/*************************** 1.取得輸入資料 **********************************/

    			
    				HttpSession session = req.getSession();
    				List<String> list = (List<String>) session.getAttribute("dateItemNo");
    				if (req.getParameter("whichPage") == null) {
    					String dateItemNos=req.getParameter("dateItemNo");
    					list=Arrays.asList(dateItemNos.split(","));
    					session.setAttribute("dateItemNo", list);
    				}
    				
    				
    				/*************************** 2.開始查詢 ***************************************/
    				DateItemService dateItemSvc = new DateItemService();
    				List<DateItemVO> dlist=new ArrayList<DateItemVO>();
    				try{
    				for(String dItemNo:list){
    					Integer dNo=Integer.parseInt(dItemNo);
    					DateItemVO dIVO=dateItemSvc.findByPK(dNo);
    					dlist.add(dIVO);
    				}
    				}
    				catch(Exception e){
    					errorMsgs.add("查詢發生了一點小問題");
    				}

    				/**************************** 3.查詢完成,準備轉交(Send the Success view)************/
    				req.setAttribute("listEmps_ByCompositeQuery", dlist); // 資料庫取出的list物件,存入request
    				RequestDispatcher successView = req.getRequestDispatcher("/front_end/dateitem/gMapShowDItem.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
    				successView.forward(req, res);

    				/*************************** 其他可能的錯誤處理 **********************************/
    			} catch (Exception e) {
    				errorMsgs.add(e.getMessage());
    				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dateitem/gMapShowDItem.jsp");
    				failureView.forward(req, res);
    			}
    		}
    		
    		
    		
    		
    		
    		
    		// 來自googleMapQuery.jsp塞選約會商品
    		if ("googleMapFilter".equals(action)) {
    		
    			List<String> errorMsgs = new LinkedList<String>();
    			// Store this set in the request scope, in case we need to
    			// send the ErrorPage view.
    			req.setAttribute("errorMsgs", errorMsgs);
    			try {

    				/*************************** 1.接收請求參數**********************************/
    				Integer memGender=null;
    				try{
    				memGender=Integer.parseInt(req.getParameter("memGender"));	
    				}
    				catch(Exception e){
    					errorMsgs.add("查詢發生問題");
    				}

    				String petKind=req.getParameter("petKind");
//    				if(petKind==null){
//    					errorMsgs.add("查詢發生問題");
//    				}
    				
    				String date=req.getParameter("date");
    		
    				
    				/*************************** 2.開始塞選 ***************************************/
    				HttpSession session=req.getSession();
    				List<SDateItemVO> list=(List<SDateItemVO>)session.getAttribute("result");


    				Predicate<SDateItemVO> predicate_gender = sDate -> (sDate.getMemGender()==0||sDate.getMemGender()==1||sDate.getMemGender()==2);
    				if(memGender==0){
    					predicate_gender = sDate -> sDate.getMemGender()==0;
    				}
    				if(memGender==1){
    					predicate_gender = sDate -> sDate.getMemGender()==1;
    				}
    				if(memGender==2){
    					predicate_gender = sDate -> sDate.getMemGender()==2;	
    				}
    				

    				Predicate<SDateItemVO> predicate_petKind = sDate -> (sDate.getPetKind().equals("狗")||sDate.getPetKind().equals("貓")||sDate.getPetKind().equals("其他"));
    				if(petKind.equals("狗")){
    					predicate_petKind = sDate -> sDate.getPetKind().equals("狗");
    				}
    				if(petKind.equals("貓")){
    					predicate_petKind = sDate -> sDate.getPetKind().equals("貓");
    				}
    				if(petKind.equals("其他")){
    					predicate_petKind = sDate -> sDate.getPetKind().equals("其他");	
    				}
    				
    				List<SDateItemVO> fList = 
    					     list
    					     .stream()
    					     .filter(predicate_gender)
    					     .filter(predicate_petKind)
    					     .distinct()
    					     .collect(Collectors.toList());

    				/**************************** 3.修改完成,準備轉交(Send the Success view)************/
    				req.setAttribute("filterList",fList); // 資料庫取出的list物件,存入request
    				req.setAttribute("memGender", memGender);
    				req.setAttribute("petKind", petKind);
    				req.setAttribute("date", date);
    				RequestDispatcher successView = req.getRequestDispatcher("/front_end/dateitem/gMapSearchResult.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
    				successView.forward(req, res);

    				/*************************** 其他可能的錯誤處理 **********************************/
    			} catch (Exception e) {
    				errorMsgs.add(e.getMessage());
    				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dateitem/gMapSearchResult.jsp");
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
	
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = header.substring(header.lastIndexOf("=") + 2, header.length() - 1);
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	
	//將Html5 date轉為Timestamp的Method
	
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
	
	public Timestamp getTimestamp(String dateStr){	
		int yyyy= Integer.parseInt((dateStr.substring(0, 4)));
		int mm= Integer.parseInt((dateStr.substring(5, 7)));
		int dd= Integer.parseInt((dateStr.substring(8, 10)));
		int hh= Integer.parseInt((dateStr.substring(11, 13)));
		int minute= Integer.parseInt((dateStr.substring(14, 16)));
	GregorianCalendar cal = new GregorianCalendar(yyyy,mm-1,dd,hh,minute,0);
	java.util.Date ud = cal.getTime();
	Timestamp ts= new Timestamp(ud.getTime());
	return ts;	
	}
			
//  調整圖片大小	
//	private static BufferedImage resizeImage(BufferedImage originalImage,int type){
//		BufferedImage resizedImage = new BufferedImage(150,150,type);
//		Graphics2D g = resizedImage.createGraphics();
//		g.drawImage(originalImage, 0, 0, 150, 150, null);
//		g.dispose();
//		
//		return resizedImage;
//	}
//	
}
	

