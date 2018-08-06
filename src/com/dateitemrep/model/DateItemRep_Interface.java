package com.dateitemrep.model;

import java.util.List;



public interface DateItemRep_Interface {
	
	public void insert(DateItemRep dateItemRep);
	public void update(DateItemRep dateItemRep);
	public void delete(Integer repNo);
	public DateItemRep findByPrimaryKey(Integer repNo);
	public List<DateItemRep> getAll();
	

}
