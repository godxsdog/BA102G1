package com.dateitemrep.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.appreprec.model.AppRepRecService;
import com.dateitem.model.DateItemService;
import com.dateitem.model.DateItemVO;
import com.dateitemrep.model.DateItemRep;
import com.dateitemrep.model.DateItemRepService;
import com.letter.model.LetterService;
import com.member.model.Member;
import com.member.model.MemberService;
import com.pet.model.PetService;
import com.restaurant.model.RestaurantService;


public class DateItemRepServlet extends HttpServlet{
	
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
			
			
			//---------���|�H�s��-----------------����令��session��
			Integer memNo  = null;
			memNo =member.getMemNo();
			
			//---------�Q���|���|�ӫ~�s��-----------------
			Integer dateItemNo  = null;
			dateItemNo =new Integer(req.getParameter("dateItemNo").trim());
			
			//---------�s�W������---------------------
			String repText = null;
			repText = req.getParameter("repText").trim();
			if( repText==null || "".equals(repText) ){
				errorMsgs.add("�п�J���|����!");
			}
			repText = repText.replace("<script>","");
			repText = repText.replace("</script>","");
			//--------���|�ɶ����{�b-------------------
			Date repDate = new Date(System.currentTimeMillis());
			Integer repState = new Integer(0);//�N���٨S���B�z
			
			if (!errorMsgs.isEmpty()) {
				
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/dateitem/select_page.jsp");
				failureView.forward(req, res);
				
				return;
			}
				
			//---------�I�sservice�e�ӳB�z---------------
			DateItemRepService dirSvc = new DateItemRepService();
			dirSvc.addRep(memNo, dateItemNo, repText, repDate, repState);
			
			//---------�ɦ^���|�ӫ~����---------------------
			Integer whichPage = null;
			whichPage =new Integer(req.getParameter("whichPage"));
			res.sendRedirect(req.getContextPath() + "/front_end/dateitem/select_page.jsp?whichPage="+whichPage);
			
			
		}
		
		
		if("updatepass".equals(action)){
			
			//---------���|�ӫ~���|�s��-----------------
			Integer repNo  = null;
			repNo =new Integer(req.getParameter("repNo").trim());
			
			//---------�I�sservice�e�ӧ�Q���|�|��---------------
			DateItemRepService dirSvc = new DateItemRepService();
			DateItemService diSvc = new DateItemService();
			Integer memNo = diSvc.findByPK(dirSvc.findByPrimaryKey(repNo).getDateItemNo()).getSellerNo();
			Date recDate = new Date(System.currentTimeMillis());
			//---------�I�sAppreprec�e�Ӭ���-------------------
			AppRepRecService arrSvc = new AppRepRecService();
			arrSvc.addrep(memNo, recDate);
			//-----------�������|���A--------------------------
			DateItemRep dateItemRep = dirSvc.findByPrimaryKey(repNo);
			dirSvc.updateRep(repNo, dateItemRep.getMemNo(), dateItemRep.getDateItemNo(), dateItemRep.getRepText(), dateItemRep.getRepDate(), 1);
			
			//------------�N�ӫ~�אּ�U�[���A--------------------
			DateItemVO dateItem = diSvc.findByPK(dirSvc.findByPrimaryKey(repNo).getDateItemNo());
			dateItem.setDateItemShow(1);
			diSvc.updateByVO(dateItem);
			
			//-----------�H�H���Q���|��------------------------
			LetterService ltrSvc = new LetterService();
			ltrSvc.addLtrOfRep(dateItem);
			
			
			
		}
		if("updatedeny".equals(action)){
			
			//---------���|�ӫ~���|�s��-----------------
			Integer repNo  = null;
			repNo =new Integer(req.getParameter("repNo").trim());
			
			//-----------�������|���A--------------------------
			DateItemRepService dirSvc = new DateItemRepService();
			DateItemRep dateItemRep = dirSvc.findByPrimaryKey(repNo);
			dirSvc.updateRep(repNo, dateItemRep.getMemNo(), dateItemRep.getDateItemNo(), dateItemRep.getRepText(), dateItemRep.getRepDate(), 1);
			
		}
		
		if("getOneDateItem".equals(action)){
			
			//-----------�I�s���|�ӫ~----------------------
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
				hasMate="��";
			}else{
				hasMate="�S��";
			}
			
			String json = "{\"dateItemTitle\":\""+dateItem.getDateItemTitle()+"\",\"dateMeetingTime\":\""+sdFormat.format(dateItem.getDateMeetingTime())
			+"\",\"sellerNo\":\""+dateItem.getSellerNo()+"\",\"sellerName\":\""+sellerName+"\",\"petName\":\""+petName+"\",\"dateItemPeople\":\""+dateItem.getDateItemPeople()
			+"\",\"dateItemText\":\""+dateItem.getDateItemText()+"\",\"dateItemPrice\":\""+dateItem.getDateItemPrice()+"\",\"restName\":\""+restName+"\",\"hasMate\":\""+hasMate+"\"}";
			
			out.println(json);
			out.close();
			
			
		}
		
		
	}
	

}
