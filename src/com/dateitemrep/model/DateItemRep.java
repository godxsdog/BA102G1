package com.dateitemrep.model;

import java.io.Serializable;
import java.sql.Date;

public class DateItemRep implements Serializable{
	
	private Integer repNo;
	private Integer memNo;
	private Integer dateItemNo;
	private String repText;
	private Date repDate;
	private Integer repState;
	
	
	public Integer getRepNo() {
		return repNo;
	}
	public void setRepNo(Integer repNo) {
		this.repNo = repNo;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemno(Integer memNo) {
		this.memNo = memNo;
	}
	public Integer getDateItemNo() {
		return dateItemNo;
	}
	public void setDateItemNo(Integer dateItemNo) {
		this.dateItemNo = dateItemNo;
	}
	public String getRepText() {
		return repText;
	}
	public void setRepText(String repText) {
		this.repText = repText;
	}
	public Date getRepDate() {
		return repDate;
	}
	public void setRepDate(Date repDate) {
		this.repDate = repDate;
	}
	public Integer getRepState() {
		return repState;
	}
	public void setRepState(Integer repState) {
		this.repState = repState;
	}
	
	
	
	
	
	

}
