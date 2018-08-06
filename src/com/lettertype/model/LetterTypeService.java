package com.lettertype.model;

import java.util.List;

public class LetterTypeService {
	
	private LetterType_Interface dao;
	
	public LetterTypeService(){
		dao = new LetterTypeDAO();
	}
	
	public LetterType addLtrType(String letterTypeName,String letterTypeText){
		
		LetterType lettertype = new LetterType();
		lettertype.setLetterTypeName(letterTypeName);
		lettertype.setLetterTypeText(letterTypeText);
		dao.insert(lettertype);		
		return lettertype;
	}
	
	public List<LetterType> getAll(){
		return dao.getAll();
	}
	
	public Integer getCurr(){
		return dao.getLetterTypeNo();
	}
	
	public LetterType update(Integer letterTypeNo, String letterTypeName,String letterTypeText){
		
		LetterType lettertype = new LetterType();
		lettertype.setLetterTypeNo(letterTypeNo);
		lettertype.setLetterTypeName(letterTypeName);
		lettertype.setLetterTypeText(letterTypeText);
		dao.update(lettertype);
		
		return lettertype;
	}
	public LetterType getOneByPrimaryKey(Integer letterTypeNo){
		return dao.findByPrimaryKey(letterTypeNo);
	}
	
}
