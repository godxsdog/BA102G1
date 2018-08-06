package com.restMember.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.restMember.model.RestMember;
import com.restMember.model.RestMemberService;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantService;








public class RestMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected RestMember allowUser(String restMemId,String restMemPsw){
		RestMemberService restMemberService = new RestMemberService();
		RestMember restMember = restMemberService.getOneRestMember(restMemId);
		
		if(restMember==null){
			return null;
		} else if(!restMember.getRestMemPsw().equals(restMemPsw)){
			return null;
		} else {
			return restMember;
		}
	}
	
	
	
	 
	protected Restaurant restUser(RestMember restMember){
		RestaurantService restaurantService = new RestaurantService();
		Restaurant restaurant = restaurantService.getOneRest(restMember.getRestNo());
		return restaurant;
	}
	
	
    public RestMemberServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		/////////////////////////////////登入/////////////////////////////
		if("login".equals(action)){
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
				///////////////////////////檢查////////////////////////////
				String restMemId = req.getParameter("restMemId");
				
				
				
				String restMemPsw = req.getParameter("restMemPsw");
				
				String restMemReg = "^[a-zA-Z0-9_]{2,10}$";
				if(restMemId == null || (restMemId.trim()).length() == 0 ){
					errorMsgs.put("","餐廳會員帳號不能空白");
				} else if(!restMemId.trim().matches(restMemReg)){
					errorMsgs.put("","餐廳會員帳號只能英文、數字和_，長度2到10");
				}
				
				
				
				if(restMemPsw == null || (restMemPsw.trim()).length() == 0 ){
					errorMsgs.put("","餐廳會員密碼不能空白");
				}else if(!restMemPsw.trim().matches(restMemReg)){
					errorMsgs.put("","餐廳會員密碼只能英文、數字和_，長度2到10");
				}
				
				
				
				
			
				
				
				///////////////////////////登入成功///////////////////////////////
				
				if(allowUser(restMemId, restMemPsw)==null){
					errorMsgs.put("","會員帳號、密碼錯誤");
					RequestDispatcher requestDispatcher =req.getRequestDispatcher("/front_end/restMember/restMemberLogin.jsp");
					requestDispatcher.forward(req, res) ;
					return;
				}else{
					HttpSession session = req.getSession();
					
					RestMember restMember = allowUser(restMemId, restMemPsw);
					Restaurant restaurant = restUser(restMember);
					session.setAttribute("restMember", restMember);
					session.setAttribute("restaurant", restaurant);
					session.setAttribute("restMemId", restMemId);
					
					
					try {
						String location = (String)session.getAttribute("location");
							if(location!=null){
								
								session.removeAttribute("location");
								res.sendRedirect(location);
								return;
							}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					if(!errorMsgs.isEmpty()){
						RequestDispatcher requestDispatcher =req.getRequestDispatcher("/front_end/restMember/restMemberLogin.jsp");
						requestDispatcher.forward(req, res) ;
						return;
					}
					
					RequestDispatcher requestDispatcher =req.getRequestDispatcher("/front_end/activity/activityManagent.jsp");
					requestDispatcher.forward(req, res);
				}
				
				
				
				
				
				
				
			} 
		
		
		
		
		////////////////////////註冊/////////////////////////////
		else if("register".equals(action)){
			
			List<String> hasAUser = new ArrayList<>();
			req.setAttribute("hasAUser", hasAUser);
			
			/////////////////////驗證帳號有無被註冊/////////////////////
			String restMemId = req.getParameter("restMemId");
			String restMemPsw = req.getParameter("restMemPsw");
			
			
			Integer restNo = Integer.parseInt(req.getParameter("restNo").trim()) ;
			
			String restMemReg = "^[a-zA-Z0-9_]{2,10}$";
			if(restMemId == null || (restMemId.trim()).length() == 0 ){
				hasAUser.add("餐廳會員帳號不能空白");
			} else if(!restMemId.trim().matches(restMemReg)){
				hasAUser.add("餐廳會員帳號只能英文、數字和_，長度2到10");
			}
			
			
			
			if(restMemPsw == null || (restMemPsw.trim()).length() == 0 ){
				hasAUser.add("餐廳會員密碼不能空白");
			}else if(!restMemPsw.trim().matches(restMemReg)){
				hasAUser.add("餐廳會員密碼只能英文、數字和_，長度2到10");
			}
			
			
			if(!hasAUser.isEmpty()){
				RequestDispatcher requestDispatcher1 =req.getRequestDispatcher("/front_end/restMember/restMemberRegister.jsp");
				requestDispatcher1.forward(req, res);
				return;
			}
			
			
			
			////////////////////存取帳號////////////////////////////
			RestMemberService restMemberServicea = new RestMemberService();
			RestMember restMember = restMemberServicea.addRestMember(restMemId, restNo, restMemPsw);
			
			
			
			req.setAttribute("restMember", restMember);
			
			RestaurantService restaurantService = new RestaurantService();
			Restaurant restaurant = restaurantService.updateBack(3, restNo);
			
			req.setAttribute("restaurant", restaurant);
			
			////////////////////準備轉交////////////////////////////
			hasAUser.add("恭喜註冊成功");
			
			RequestDispatcher requestDispatcher2 = req.getRequestDispatcher("/front_end/restMember/restMemberLogin.jsp");
			requestDispatcher2.forward(req, res);
			
			
		}
		
		
		//////////////////////登出////////////////////////////////
		else if("logout".equals(action)){
			
			
			HttpSession session = req.getSession();
			
			session.removeAttribute("restMember");
			session.removeAttribute("restMemId");
			session.removeAttribute("restaurant");
//			System.out.println("ddddddd::::"+req.getAttribute("restMember"));
		
			RequestDispatcher requestDispatcher3 = req.getRequestDispatcher("/front_end/restMember/restMemberLogin.jsp");
			requestDispatcher3.forward(req, res);
		}
		
		
		
		
		
		///////////////////////改密碼////////////////////////////
		else if("changeRestMemPsw".equals(action)){
			
			
			List<String> changePswError = new ArrayList<>();
			req.setAttribute("changePswError", changePswError);
			
			
			///////////////////驗證帳號密碼//////////////////////
			String restMemId = req.getParameter("restMemId");
			
			Integer restNo = Integer.parseInt(req.getParameter("restNo").trim());
			
			String restMemPsw = req.getParameter("restMemPsw");
			String restMemOldPsw = req.getParameter("restMemOldPsw");
			String restMemNewPsw = req.getParameter("restMemNewPsw");
			
			String restMemReg = "^[a-zA-Z0-9_]{2,10}$";
			
			if(restMemId == null || (restMemId.trim()).length() == 0 ){
				changePswError.add("餐廳會員帳號不能空白");
			} else if(!restMemId.trim().matches(restMemReg)){
				changePswError.add("餐廳會員帳號只能英文、數字和_，長度2到10");
			}
			
			if(restMemPsw == null || (restMemPsw.trim()).length() == 0 ){
				changePswError.add("餐廳會員密碼不能空白");
			}else if(!restMemPsw.trim().matches(restMemReg)){
				changePswError.add("餐廳會員密碼只能英文、數字和_，長度2到10");
			}
			
			if(!restMemNewPsw.equals(restMemPsw)){
				changePswError.add("新密碼輸入不一致");
			}
			
			if(allowUser(restMemId, restMemOldPsw)==null){
				changePswError.add("原始密碼輸入錯誤");
			}
			
			
			if(!changePswError.isEmpty()){
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/front_end/restMember/restMemberChangePsw.jsp");
				requestDispatcher.forward(req, res);
				return;
			}
			
			
			
			/////////////////////////更改密碼////////////////////////
			RestMemberService restMemberService = new RestMemberService();
			RestMember restMember = restMemberService.updateRestMember(restMemId, restNo, restMemPsw);
			
			HttpSession session = req.getSession();
			
			session.getAttribute("restMember");
			session.removeAttribute("restMember");
			session.setAttribute("restMember", restMember);	
			
			
			//////////////////轉交//////////////////////////////////////////
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/front_end/activity/activityManagent.jsp");
			requestDispatcher.forward(req, res);
		}
		
		
		/////////////////////忘記密碼//////////////////////////////
		
		else if("findPsw".equals(action)){
//			
//			String restMemId = req.getParameter("restMemId");
//			String Email = req.getParameter("restMemEmail");
			
			
			
		}
		
		
		
		
		
		
		
	}

}
