package com.dateitemapp.model;

import java.sql.Date;
import java.util.List;

public class DateItemAppService {
	
	private DateItemApp_Interface dao;
	
	public DateItemAppService(){
		dao = new DateItemAppDAO();
	}

	public List<DateItemApp> getAll(){
		return dao.getAll();
	}
	
	public DateItemApp addApp(Integer memNo,Integer dateItemNo, String appTitle, String appText,Date appDate, Integer appState){
		
		DateItemApp dateItemApp = new DateItemApp();
		dateItemApp.setAppDate(appDate);
		dateItemApp.setAppState(appState);
		dateItemApp.setAppText(appText);
		dateItemApp.setAppTitle(appTitle);
		dateItemApp.setDateItemNo(dateItemNo);
		dateItemApp.setMemNo(memNo);
		
		dao.insert(dateItemApp);

		return dateItemApp;
	}
	
	public DateItemApp findByPrimaryKey(Integer appNo){
		return dao.findByPrimaryKey(appNo);
	}
	
	public DateItemApp updateApp(Integer appNo,Integer memNo,Integer dateItemNo, String appTitle, String appText,Date appDate, Integer appState){
		
		DateItemApp dateItemApp = new DateItemApp();
		dateItemApp.setAppNo(appNo);
		dateItemApp.setAppDate(appDate);
		dateItemApp.setAppState(appState);
		dateItemApp.setAppText(appText);
		dateItemApp.setAppTitle(appTitle);
		dateItemApp.setDateItemNo(dateItemNo);
		dateItemApp.setMemNo(memNo);
		
		dao.update(dateItemApp);
		
		return dateItemApp;
	}
	
}
