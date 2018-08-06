package com.restaurant.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
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

import org.json.JSONArray;
import org.json.JSONObject;

import com.restMember.model.RestMember;
import com.restMember.model.RestMemberService;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantService;



public class RestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected Double getLongtitude(String restAdd){
		Double longtitude = null;
		try {
			String sKeyWord = restAdd;
			
			URL urlFromGMap  = new URL(String.format("http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW", 
			URLEncoder.encode(sKeyWord, "UTF-8")));//p=%s is KeyWord in	            
			URLConnection connFromGMap = urlFromGMap.openConnection();
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader readerFromGMap = new BufferedReader(new InputStreamReader(connFromGMap.getInputStream(),"utf-8"));
			while ((line = readerFromGMap.readLine()) != null) {builder.append(line);}
				JSONObject json = new JSONObject(builder.toString()); //轉換json格式
			    JSONArray ja = json.getJSONArray("results");//取得json的Array物件
			        for (int i = 0; i < ja.length(); i++) {
		                  
//		            lat.add(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
		            longtitude = (ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
		                 
			}
			              
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return longtitude;       
	}
    
	protected Double getLatitude(String restAdd){
		Double latitude = null;
		try {
			String sKeyWord = restAdd;
			
			URL urlFromGMap  = new URL(String.format("http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW", 
			URLEncoder.encode(sKeyWord, "UTF-8")));//p=%s is KeyWord in	            
			URLConnection connFromGMap = urlFromGMap.openConnection();
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader readerFromGMap = new BufferedReader(new InputStreamReader(connFromGMap.getInputStream(),"utf-8"));
			while ((line = readerFromGMap.readLine()) != null) {builder.append(line);}
				JSONObject json = new JSONObject(builder.toString()); //轉換json格式
			    JSONArray ja = json.getJSONArray("results");//取得json的Array物件
			        for (int i = 0; i < ja.length(); i++) {
		                  
			        latitude = (ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
//		            longtitude = (ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
		                 
			}
			              
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return latitude;       
	}
	
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
	
	
    public RestaurantServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		/////////////////////修改餐廳資料///////////////////////////
		
			if("updateRestMemData".equals(action)){
			
			
			//////////////////檢查更改資料///////////////////////////
			List<String> updateError = new ArrayList<>();
			req.setAttribute("updateError", updateError);
			
			
			Integer restNo = Integer.parseInt(req.getParameter("restNo").trim());
			
			
			
			String restName = req.getParameter("restName");
			if( (restName.trim()).length()==0 || restName == null){
				updateError.add("餐廳名稱請勿留空");
			}
			
			
			String restAdd = req.getParameter("restAdd");
			if( (restAdd.trim()).length()==0 || restAdd == null){
				updateError.add("餐廳地址請勿留空");
			}
			
			String restPhone = req.getParameter("restPhone");
			if( (restPhone.trim()).length()==0 || restPhone == null){
				updateError.add("餐廳電話請勿留空");
			}
			
			String restIntro = req.getParameter("restIntro");
			if( (restIntro.trim()).length()==0 || restIntro == null){
				updateError.add("餐廳介紹請勿留空");
			}
			
			Integer restKind = null;
			try {
				restKind = Integer.parseInt(req.getParameter("restKind").trim());
			} catch (Exception e) {
				updateError.add("餐廳種類請勿留空");
			}
			
			String restLocate = req.getParameter("restAdd").substring(0,3);
			if( (restLocate.trim()).length()==0 || restLocate == null ){
				updateError.add("餐廳縣市轉換錯誤");
			}
			
			Integer restReviewStatus = null;
			try {
				restReviewStatus = Integer.parseInt(req.getParameter("restReviewStatus"));
			} catch (Exception e) {
				updateError.add("餐廳狀態輸入錯誤");
			}
			
			Double restLongtitude = null;
			try {
				restLongtitude = Double.parseDouble(req.getParameter("restLongtitude"));
			} catch (Exception e) {
				updateError.add("餐廳經度輸入錯誤");
			}
			
			Double restLatitude = null;
			try {
				restLatitude = Double.parseDouble(req.getParameter("restLatitude"));
			} catch (Exception e) {
				updateError.add("餐廳緯度輸入錯誤");
			}
			
			if(!updateError.isEmpty()){
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/front_end/restMember/restMemberManagement.jsp");
				requestDispatcher.forward(req, res);
				return;
			}
			
			
			////////////////////更改資料///////////////////////////
			
			
			
			RestaurantService restaurantService = new RestaurantService();
			Restaurant restaurant = restaurantService.updateRestForManager(restNo, restName, 
					restAdd, restLocate, restPhone, restIntro, restKind, restReviewStatus, 
					restLongtitude, restLatitude);  
			
			HttpSession session = req.getSession();
			
			
			session.getAttribute("restaurant");
			session.removeAttribute("restaurant");
			session.setAttribute("restaurant", restaurant);
			
			
			
			
			//////////////////轉交//////////////////////////////////////////
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/front_end/activity/activityManagent.jsp");
			requestDispatcher.forward(req, res);
			
			}
			
			
			
			
			///////////////////////////新增餐廳//////////////////////////////
			else if("newRestaurant".equals(action)){
				
				List<String> newRestErr = new ArrayList<>();
				req.setAttribute("newRestErr", newRestErr);
				
				
				/////////////////////////驗證//////////////////////////////
				
				
				
				
				String restName = req.getParameter("restName");
				if( (restName.trim()).length()==0 || restName == null){
					newRestErr.add("餐廳名稱請勿留空");
				}
				
				String county = req.getParameter("county").trim();
				if( county.length()==0 || county == null || county.isEmpty()){
					newRestErr.add("餐廳地址有誤或留空");
				}
				String district = req.getParameter("district").trim();
				if( district.length()==0 || district == null || district.isEmpty()){
					newRestErr.add("餐廳地址有誤或留空");
				}
				String restAddEnd = req.getParameter("restAdd").trim();
				if( restAddEnd.length()==0 || restAddEnd == null || restAddEnd.isEmpty()){
					newRestErr.add("餐廳地址有誤或留空");
				}
				
				
				String restAdd = county+district+restAddEnd;
				
				
				
				String restPhone = req.getParameter("restPhone");
				if( (restPhone.trim()).length()==0 || restPhone == null){
					newRestErr.add("餐廳電話請勿留空");
				}
				
				String restIntro = "qqq";
				if( (restIntro.trim()).length()==0 || restIntro == null){
					
					newRestErr.add("餐廳介紹請勿留空");
				}
				
				Integer restKind = null;
				try {
					restKind = Integer.parseInt(req.getParameter("restKind").trim());
				} catch (Exception e) {
					newRestErr.add("餐廳種類請勿留空");
				}
				
				String restLocate = null;
				try {
					
					restLocate = restAdd.substring(0,3);
					
						if( (restLocate.trim()).length()==0 || restLocate == null ){
							newRestErr.add("請輸入正確縣市");
						}
				} catch (Exception e) {
					newRestErr.add("餐廳縣市轉換錯誤");
				}
				
				Integer restReviewStatus = null;
				try {
					restReviewStatus = Integer.parseInt(req.getParameter("restReviewStatus"));
				} catch (Exception e) {
					newRestErr.add("餐廳狀態輸入錯誤");
				}
				
				Double restLongtitude = null;
				
				try {
					
					restLongtitude = getLongtitude(restAdd);
					System.out.println("restAdd::::"+restAdd);
					System.out.println("restLongtitude::::"+restLongtitude);
						if(restLongtitude>180.0 || restLongtitude<0.0){
							newRestErr.add("請輸入正確地址");
						}
				} catch (Exception e) {
					newRestErr.add("GoogleQuota到達上限");
				}
				
				Double restLatitude = null;
				try {
					restLatitude = getLatitude(restAdd);
						if(restLatitude>90.0 || restLatitude<0.0){
							newRestErr.add("請輸入正確地址");
						}
				} catch (Exception e) {
					newRestErr.add("GoogleQuota到達上限");
				}
				
				if(!newRestErr.isEmpty()){
					RequestDispatcher requestDispatcher = req.getRequestDispatcher("/front_end/restaurant/newRestaurant.jsp");
					requestDispatcher.forward(req, res);
					return;
				}
				
				///////////////新增資料///////////////////////////////
				
				RestaurantService restaurantService = new RestaurantService();
				Restaurant restaurant = restaurantService.addRest(restName, restAdd, restLocate, 
						restPhone, restIntro, restKind, restReviewStatus, restLongtitude, restLatitude);
				req.setAttribute("restaurant", restaurant);
				
				////////////////轉交////////////////////////////////
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/front_end/restMember/restMemberList.jsp");
				requestDispatcher.forward(req, res);
				
			}
			
			else if("restManagement".equals(action)){
				List<String> reatManeErr = new ArrayList<>();
				req.setAttribute("reatManeErr", reatManeErr);
				
				Integer restReviewStatus = null;
				try {
					restReviewStatus = Integer.parseInt(req.getParameter("restReviewStatus"));
					
				} catch (Exception e) {
					reatManeErr.add("餐廳狀態錯誤");
				}
				
				Integer restNo = null;
				try {
					restNo = Integer.parseInt(req.getParameter("restNo"));
					
				} catch (Exception e) {
					reatManeErr.add("餐廳編號錯誤");
				}
				
				if(!reatManeErr.isEmpty()){
					
					RequestDispatcher requestDispatcher = req.getRequestDispatcher("/back_end/restMemberManagement/restMemberManagement.jsp");
					requestDispatcher.forward(req, res);
					return;
				}
				
				RestaurantService restaurantService = new RestaurantService();
				Restaurant restaurant = restaurantService.updateBack(restReviewStatus, restNo);
				req.setAttribute("restaurant", restaurant);
				////////////////轉交////////////////////////////////
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/back_end/restMemberManagement/restMemberManagement.jsp");
				requestDispatcher.forward(req, res);
			}
			
			else if("CompositeQuery".equals(action)){
				
				List<String> queryError = new LinkedList<String>();
				req.setAttribute("queryError", queryError);
				
				/////////////////資料轉MAP//////////////////////////
				Map<String, String[]> map = req.getParameterMap();
				
				////////////////複合查詢///////////////////////////
				RestaurantService restaurantService = new RestaurantService();
				List<Restaurant> restaurantList = restaurantService.getAll(map);
				req.setAttribute("list", restaurantList);
				
				///////////////轉交//////////////////////////////
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/front_end/restMember/restMemberList.jsp");
				requestDispatcher.forward(req, res);
				
				
				
			}
			
			else if("CompositeQueryBack".equals(action)){
				
				List<String> queryError = new LinkedList<String>();
				req.setAttribute("queryError", queryError);
				
				/////////////////資料轉MAP//////////////////////////
				Map<String, String[]> map = req.getParameterMap();
				
				////////////////複合查詢///////////////////////////
				RestaurantService restaurantService = new RestaurantService();
				List<Restaurant> restaurantList = restaurantService.getAll(map);
				
				req.setAttribute("restauranBacktList", restaurantList);
				
				///////////////轉交//////////////////////////////
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/back_end/restMemberManagement/restMemberManagement.jsp");
				requestDispatcher.forward(req, res);
				
				
				
			}
		}
}
