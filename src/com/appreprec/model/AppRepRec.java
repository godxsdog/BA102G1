package com.appreprec.model;

import java.io.Serializable;
import java.sql.Date;

public class AppRepRec implements Serializable{
	
	private Integer memNo;
	private Date recDate;

	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public Date getRecDate() {
		return recDate;
	}
	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}
	
	

}
