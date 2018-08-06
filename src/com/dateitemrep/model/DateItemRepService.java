package com.dateitemrep.model;

import java.sql.Date;
import java.util.List;


public class DateItemRepService {

private DateItemRep_Interface dao;
	
	public DateItemRepService(){
		dao = new DateItemRepDAO();
	}

	public List<DateItemRep> getAll(){
		return dao.getAll();
	}
	
	public DateItemRep addRep(Integer memNo,Integer dateItemNo,String repText,Date repDate,Integer repState){
		
		DateItemRep dateItemRep = new DateItemRep();
		dateItemRep.setMemno(memNo);
		dateItemRep.setRepDate(repDate);
		dateItemRep.setDateItemNo(dateItemNo);
		dateItemRep.setRepText(repText);
		dateItemRep.setRepState(repState);
		
		dao.insert(dateItemRep);
		
		return dateItemRep;
	}
	
	public DateItemRep findByPrimaryKey(Integer repNo){
		return dao.findByPrimaryKey(repNo);
	}
	
	public DateItemRep updateRep(Integer repNo,Integer memNo,Integer dateItemNo,String repText,Date repDate,Integer repState){
		
		DateItemRep dateItemRep = new DateItemRep();
		dateItemRep.setRepNo(repNo);
		dateItemRep.setMemno(memNo);
		dateItemRep.setRepDate(repDate);
		dateItemRep.setDateItemNo(dateItemNo);
		dateItemRep.setRepText(repText);
		dateItemRep.setRepState(repState);
		
		dao.update(dateItemRep);
		
		return dateItemRep;
	}
	
	
}
