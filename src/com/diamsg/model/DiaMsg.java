package com.diamsg.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DiaMsg implements Serializable{
	
	private Integer diaMsgNo;
	private Integer diaNo;
	private Integer memNo;
	private String diaMsgText;
	private Timestamp diaMsgTime;
	private Integer diaMsgState;
	
	
	
	public Integer getDiaMsgNo() {
		return diaMsgNo;
	}
	public void setDiaMsgNo(Integer diaMsgNo) {
		this.diaMsgNo = diaMsgNo;
	}
	public Integer getDiaNo() {
		return diaNo;
	}
	public void setDiaNo(Integer diaNo) {
		this.diaNo = diaNo;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getDiaMsgText() {
		return diaMsgText;
	}
	public void setDiaMsgText(String diaMsgText) {
		this.diaMsgText = diaMsgText;
	}
	public Timestamp getDiaMsgTime() {
		return diaMsgTime;
	}
	public void setDiaMsgTime(Timestamp diaMsgTime) {
		this.diaMsgTime = diaMsgTime;
	}
	public Integer getDiaMsgState() {
		return diaMsgState;
	}
	public void setDiaMsgState(Integer diaMsgState) {
		this.diaMsgState = diaMsgState;
	}
	
	

}
