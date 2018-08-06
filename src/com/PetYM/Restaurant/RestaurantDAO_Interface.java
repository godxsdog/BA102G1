package com.PetYM.Restaurant;

import java.util.List;

public interface RestaurantDAO_Interface {
	 void add(Restaurant rest);
	 void update(Restaurant rest);
	 void delete(Integer restNo);
	 Restaurant findByPK(Integer restNo);
	 List<Restaurant> getAll();
}
