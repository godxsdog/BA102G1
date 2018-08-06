package com.PetYM.Letter;

import java.util.List;


public interface Letter_Interface {

	public void insert(Letter letter);
	public void update(Letter letter);
	public void delete(Integer letterNo);
	public Letter findByPrimaryKey(Integer letterNo);
	public List<Letter> getAll();
	
	
}
