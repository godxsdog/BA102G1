package com.appreprec.model;

import java.sql.Date;

public class AppRepRecService {
	
	private AppRepRec_Interface dao;
	
	public AppRepRecService(){
		dao = new AppRepRecDAO();
	}
	
	public Integer findOneMonthTimes(Integer memNo){
		return dao.findOneMonthTimes(memNo);
	}
	
	public AppRepRec addrep(Integer memNo,Date recDate){
		
		AppRepRec appRepRec = new AppRepRec();
		
		appRepRec.setMemNo(memNo);
		appRepRec.setRecDate(recDate);
		
		dao.insert(appRepRec);
		
		return appRepRec;
	}

}
