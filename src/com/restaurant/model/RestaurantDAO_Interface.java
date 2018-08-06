package com.restaurant.model;

import java.util.List;
import java.util.Map;

public interface RestaurantDAO_Interface {
	 void add(Restaurant rest);
	 void updateRestForManager(Restaurant rest);
	 void updateBack(Integer restReviewStatus,Integer restNo);
	 void delete(Integer restNo);
	 Restaurant findByPK(Integer restNo);
	 List<Restaurant> getAll();
	 List<Restaurant> getAll(Map<String, String[]> map);
	
}
