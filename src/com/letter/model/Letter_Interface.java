package com.letter.model;

import java.util.List;


public interface Letter_Interface {

	public void insert(Letter letter);
	public void update(Letter letter);
	public void delete(Integer letterNo);
	public Letter findByPrimaryKey(Integer letterNo);
	public List<Letter> getAll();
	public List<Letter> getOneMemLtrs(Integer memNo);
	public List<Letter> getTagLtrs(Integer memNo);
	public List<Letter> getNotReadLtrs(Integer memNo);
	
}
