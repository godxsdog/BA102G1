package com.restaurant.model;

import java.util.List;
import java.util.Map;

public class RestaurantService {
	private RestaurantDAO_Interface restaurantDao;
	
	public RestaurantService(){
		restaurantDao = new RestaurantDAO();
	}
	
	public Restaurant addRest(String restName, String restAdd,String restLocate, String restPhone, String restIntro,
			Integer restKind,  Integer restReviewStatus, Double restLongtitude, Double restLatitude){
		
		Restaurant rest = new Restaurant();
		
		rest.setRestName(restName);
		rest.setRestAdd(restAdd);
		rest.setRestLocate(restLocate);
		rest.setRestPhone(restPhone);
		rest.setRestIntro(restIntro);
		rest.setRestKind(restKind);
		rest.setRestReviewStatus(restReviewStatus);
		rest.setRestLongtitude(restLongtitude);
		rest.setRestLatitude(restLatitude);
		restaurantDao.add(rest);
		
		return rest;
	}
	
	public Restaurant updateRestForManager(Integer restNo, String restName, String restAdd,String restLocate, String restPhone, String restIntro,
			Integer restKind,  Integer restReviewStatus, Double restLongtitude, Double restLatitude){
		
		Restaurant rest = new Restaurant();
		
		rest.setRestNo(restNo);
		rest.setRestName(restName);
		rest.setRestAdd(restAdd);
		rest.setRestLocate(restLocate);
		rest.setRestPhone(restPhone);
		rest.setRestIntro(restIntro);
		rest.setRestKind(restKind);
		rest.setRestReviewStatus(restReviewStatus);
		rest.setRestLongtitude(restLongtitude);
		rest.setRestLatitude(restLatitude);
		restaurantDao.updateRestForManager(rest);
		
		return rest;
	}
	
	public Restaurant updateBack(Integer restReviewStatus,Integer restNo){
		
		Restaurant rest = new Restaurant();
		
		rest.setRestReviewStatus(restReviewStatus);
		rest.setRestNo(restNo);
		
		
		restaurantDao.updateBack(restReviewStatus, restNo);
		
		return rest;
	}
	
	public void deleteRest(Integer restNo){
		restaurantDao.delete(restNo);
	}
	
	public Restaurant getOneRest(Integer restNo){
		return restaurantDao.findByPK(restNo);
	}
	
	public List<Restaurant> getAll(){
		return restaurantDao.getAll();
	}
	
	public List<Restaurant> getAll(Map<String, String[]> map){
		return restaurantDao.getAll(map);
	}
	
	
}
