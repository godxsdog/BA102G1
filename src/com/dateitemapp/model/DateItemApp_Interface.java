package com.dateitemapp.model;

import java.util.List;

public interface DateItemApp_Interface {

	public void insert(DateItemApp dateItemApp);
	public void update(DateItemApp dateItemApp);
	public void delete(Integer appNo);
	public DateItemApp findByPrimaryKey(Integer appNo);
	public List<DateItemApp> getAll();
	
}
