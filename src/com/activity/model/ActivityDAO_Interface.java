package com.activity.model;

import java.util.List;
import java.util.Map;

import com.restaurant.model.Restaurant;



public interface ActivityDAO_Interface {
	void add(Activity activity);
	
	void update(Activity activity);
	void updateBack(Integer actStatus,Integer actNo);
	
	void delete(Integer actNo);
	Activity findByPK(Integer actNo);
	Activity findByPKStatus(Integer actNo,Integer actStatus);
	
	List<Activity> getAll();
	List<Activity> getAllById(String restMemId);
	List<Activity> getAllByStatus(Integer actStatus);
	List<Activity> getAllByStatusAnimal(Integer actStatus,Integer actKind);
	
	List<Activity> getAllByNorth();
	List<Activity> getAllByEast();
	List<Activity> getAllByWest();
	List<Activity> getAllBySouth();
	
	List<Activity> getAllOfMine(Integer memNo);
	
	List<Activity> getAll(Map<String, String[]> map);
}
