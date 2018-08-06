package com.dateitemapp.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.appreprec.model.AppRepRecService;
import com.dateitem.model.DateItemService;
import com.dateitem.model.DateItemVO;
import com.dateitemapp.model.DateItemApp;
import com.dateitemapp.model.DateItemAppService;
import com.letter.model.LetterService;
import com.member.model.Member;
import com.member.model.MemberService;
import com.pet.model.PetService;
import com.restaurant.model.RestaurantService;


@MultipartConfig(fileSizeThreshold =500* 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 5 * 500 * 1024 * 1024)
public class DateItemAppServlet extends HttpServlet{
	
	static int fixed_width=400;
	static int fixed_height=300;
	
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
		PrintWriter out = res.getWriter();
		
		
		if("insert".equals(action)){
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			//---------申訴人編號-----------------之後改成用session取
			Integer memNo  = null;
			memNo = member.getMemNo();
			
			//---------被申訴約會商品編號-----------------
			Integer dateItemNo  = null;
			dateItemNo =new Integer(req.getParameter("dateItemNo").trim());
			
			//---------新增的標題---------------------
			String appTitle = null;
			appTitle = req.getParameter("appTitle").trim();
			
			if( appTitle==null || "".equals(appTitle) ){
				errorMsgs.add("請輸入發文名稱!");
			}
			if(appTitle!=null && appTitle.length()>30){
				errorMsgs.add("日誌標題長度過長!!");
			}
			
			//擋住script
			appTitle = appTitle.replace("<script>","");
			appTitle = appTitle.replace("</script>","");
			
			//---------新增的內文+加上把照片轉字串---------------------
			String appText = null;
			Base64.Encoder bs64 = Base64.getEncoder();
			byte[] appImg= null;			
			String appImgtoString = null;
			Part part = null;
			part = req.getPart("appImage");
			

			if(!part.equals("")){
				if(part.getContentType().substring(0,5).equals("image")){
					appImg = getByteArrayImg(part);
					appImgtoString = bs64.encodeToString(appImg);
				}
			}
			
			if(appImgtoString == null){
				appText = req.getParameter("appText").trim();
				if(appText==null || appText.equals("")){			//判斷空值(空字串)
					errorMsgs.add("請輸入內文!");
				}
				
				//擋住script
				appText = appText.replace("<script>","");
				appText = appText.replace("</script>","");
			}else{
				appText = req.getParameter("appText").trim();
				if(appText==null || appText.equals("")){			//判斷空值(空字串)
					errorMsgs.add("請輸入內文!");
				}
				appText = appText+"<br><img src=\"data:image/jpg;base64,"+appImgtoString+"\">";
				
				appText = appText.replace("<script>","");
				appText = appText.replace("</script>","");
			}
				System.out.println(appText);
			//--------申訴時間為現在-------------------
			Date appDate = new Date(System.currentTimeMillis());
			Integer appState = new Integer(0);//代表還沒有處理
			
			if (!errorMsgs.isEmpty()) {
				
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dateitem/list_buyer_future.jsp");
				failureView.forward(req, res);
				
				return;
			}
			
			
			
			//---------呼叫service前來處理---------------
			DateItemAppService dipSvc = new DateItemAppService();
			dipSvc.addApp(memNo, dateItemNo, appTitle, appText, appDate, appState);
			
			//------------導回申訴頁面-----------------------
			res.sendRedirect(req.getContextPath() + "/front_end/dateitem/list_buyer_future.jsp");
			
			
			
			
		}
		
		if("updatepass".equals(action)){
			
			//---------約會商品申訴編號-----------------
			Integer appNo  = null;
			appNo =new Integer(req.getParameter("appno").trim());
			
			//---------呼叫service前來找被申訴會員---------------
			DateItemAppService dipSvc = new DateItemAppService();
			DateItemService diSvc = new DateItemService();
			Integer memNo = diSvc.findByPK(dipSvc.findByPrimaryKey(appNo).getDateItemNo()).getSellerNo();
			Date recDate = new Date(System.currentTimeMillis());
			//---------呼叫Appreprec前來紀錄-------------------
			AppRepRecService arrSvc = new AppRepRecService();
			arrSvc.addrep(memNo, recDate);
			//-----------改變申訴處理狀態--------------------------
			DateItemApp dateItemApp = dipSvc.findByPrimaryKey(appNo);
			dipSvc.updateApp(appNo, dateItemApp.getMemNo(), dateItemApp.getDateItemNo(), dateItemApp.getAppTitle(), dateItemApp.getAppText(), dateItemApp.getAppDate(), 1);
			
			//-----------寄信給被申訴者------------------------
			DateItemVO dateItem = diSvc.findByPK(dipSvc.findByPrimaryKey(appNo).getDateItemNo());
			LetterService ltrSvc = new LetterService();
			ltrSvc.addLtrOfApp(dateItem);
		}
		
		if("updatedeny".equals(action)){
			
			//---------約會商品申訴編號-----------------
			Integer appNo  = null;
			appNo =new Integer(req.getParameter("appno").trim());
			
			//-----------改變申訴處理狀態--------------------------
			DateItemAppService dipSvc = new DateItemAppService();
			DateItemApp dateItemApp = dipSvc.findByPrimaryKey(appNo);
			dipSvc.updateApp(appNo, dateItemApp.getMemNo(), dateItemApp.getDateItemNo(), dateItemApp.getAppTitle(), dateItemApp.getAppText(), dateItemApp.getAppDate(), 1);
			
		}
		
if("getOneDateItem".equals(action)){
			
			//-----------呼叫約會商品----------------------
			Integer dateItemNo = null;
			dateItemNo = new Integer(req.getParameter("dateItemNo").trim());
			DateItemService dSvc = new DateItemService();
			DateItemVO dateItem = dSvc.getOneDateItem(dateItemNo);
			RestaurantService restSvc = new RestaurantService();
			PetService pSvc = new PetService();
			MemberService memSvc = new MemberService();
			
//			JSONObject json =new JSONObject(dateItem);
			
			String sellerName = memSvc.getOneMember(dateItem.getSellerNo()).getMemName();
			String petName = pSvc.getOnePet(dateItem.getPetNo()).getPetName();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String restName = restSvc.getOneRest(dateItem.getRestListNo()).getRestName();
			String hasMate = null;
			if(dateItem.getHasMate()==true){
				hasMate="有";
			}else{
				hasMate="沒有";
			}
			
			String json = "{\"dateItemTitle\":\""+dateItem.getDateItemTitle()+"\",\"dateMeetingTime\":\""+sdFormat.format(dateItem.getDateMeetingTime())
			+"\",\"sellerNo\":\""+dateItem.getSellerNo()+"\",\"sellerName\":\""+sellerName+"\",\"petName\":\""+petName+"\",\"dateItemPeople\":\""+dateItem.getDateItemPeople()
			+"\",\"dateItemText\":\""+dateItem.getDateItemText()+"\",\"dateItemPrice\":\""+dateItem.getDateItemPrice()+"\",\"restName\":\""+restName+"\",\"hasMate\":\""+hasMate+"\"}";
			
			out.println(json);
			out.close();
			
			
		}
		
		
		
		
	}
	
//	public byte[] getByteArrayImg(Part part){
//			
//			ByteArrayOutputStream diaimg=null;
//			try{
//				java.io.InputStream in =part.getInputStream(); 
//				diaimg = new ByteArrayOutputStream();
//				byte[] buffer = new byte[8192];
//				int i;
//				while ((i = in.read(buffer)) != -1) {
//					diaimg.write(buffer, 0, i);
//				}
//				diaimg.close();
//				in.close();	
//			}catch(IOException e){
//				e.printStackTrace();
//			}
//			
//			return diaimg.toByteArray();
//		}
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = null;
		Graphics2D g = null;
		
		if(originalImage.getWidth()>originalImage.getHeight()){
			resizedImage = new BufferedImage(fixed_width, fixed_height, type);
			g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, fixed_width, fixed_height, null);
		}
		else{
			resizedImage = new BufferedImage(fixed_height, fixed_width, type);
			g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, fixed_height, fixed_width, null);
		}
		g.dispose();

		return resizedImage;
	    }
	
	public byte[] getByteArrayImg(Part part){
		
		ByteArrayOutputStream diaImg = null;
		try{
			java.io.InputStream in =part.getInputStream();			
			BufferedImage originalImage = ImageIO.read(in);
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			BufferedImage resizeImageJpg = resizeImage(originalImage, type);
			diaImg = new ByteArrayOutputStream();
			ImageIO.write( resizeImageJpg, "jpg", diaImg );
			diaImg.flush();
			diaImg.close();
			
		}catch(IOException ie){
			ie.getMessage();
		}
		
		return diaImg.toByteArray();
	}
	
	
	
	
}
