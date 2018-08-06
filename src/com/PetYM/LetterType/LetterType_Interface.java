package com.PetYM.LetterType;

import java.util.List;

public interface LetterType_Interface {

	public void insert(LetterType letterType);
	public void update(LetterType letterType);
	public void delete(Integer letterTypeNo);
	public LetterType findByPrimaryKey(Integer letterTypeNo);
	public List<LetterType> getAll();
	
	
}
