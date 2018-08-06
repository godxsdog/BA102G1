package com.diary.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Diary implements Serializable{
	
	private Integer diaNo;
	private Integer memNo;
	private String diaName;
	private String diaText;
	private byte[] diaImg;
	private Timestamp diaCreTime;
	private Timestamp diaModTime;
	private Integer diaState;
	private String diaImgExtName;
	
	public String getDiaImgExtName() {
		return diaImgExtName;
	}
	public void setDiaImgExtName(String diaImgExtName) {
		this.diaImgExtName = diaImgExtName;
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
	public String getDiaName() {
		return diaName;
	}
	public void setDiaName(String diaName) {
		this.diaName = diaName;
	}
	public String getDiaText() {
		return diaText;
	}
	public void setDiaText(String diaText) {
		this.diaText = diaText;
	}
	public byte[] getDiaImg() {
		return diaImg;
	}
	public void setDiaImg(byte[] diaImg) {
		this.diaImg = diaImg;
	}
	public Timestamp getDiaCreTime() {
		return diaCreTime;
	}
	public void setDiaCreTime(Timestamp diaCreTime) {
		this.diaCreTime = diaCreTime;
	}
	public Timestamp getDiaModTime() {
		return diaModTime;
	}
	public void setDiaModTime(Timestamp diaModTime) {
		this.diaModTime = diaModTime;
	}
	public Integer getDiaState() {
		return diaState;
	}
	public void setDiaState(Integer diaState) {
		this.diaState = diaState;
	}
	
	
	
	
	

	
	
}
