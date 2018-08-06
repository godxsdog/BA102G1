package com.submem.model;

import java.util.List;

public class SubMemService {

	private SubMem_Interface dao;
	
	public SubMemService(){
		dao=new SubMemDAO();
	}
	
	public SubMem addSub(Integer actSubMemNo, Integer beSubMemNo, Integer subState){
		
		SubMem submem = new SubMem();
		
		submem.setActSubMemNo(actSubMemNo);
		submem.setBeSubMemNo(beSubMemNo);
		submem.setSubState(subState);
		dao.insert(submem);
		
		return submem;
	}
	
	public void update(SubMem subMem){
		dao.update(subMem);
	}
	
	public void deleteSub(SubMem subMem){
		
		dao.update(subMem);
		
	}
	
	public List<SubMem> getMemberAct(Integer actSubMemNo){
		
		return dao.findByPrimaryKey(actSubMemNo);
		
	}
	
	public List<SubMem> getall(){
		return dao.getAll();
	}
	
	
	
}
