
package com.msg.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class MsgVO implements Serializable{
	private Integer msgNo;
	private Integer sendNo;
	private Integer recNo;
	private Integer dateItemNo;
	private String msgContent;
	private Timestamp msgTime;
	private Integer msgStatus;
	
	public MsgVO() {};
	
	public MsgVO(Integer msgNo, Integer sendNo, Integer recNo, Integer dateItemNo, String msgContent, Timestamp msgTime,
			Integer msgStatus) {
		super();
		this.msgNo = msgNo;
		this.sendNo = sendNo;
		this.recNo = recNo;
		this.dateItemNo = dateItemNo;
		this.msgContent = msgContent;
		this.msgTime = msgTime;
		this.msgStatus = msgStatus;
	}

	public Integer getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(Integer msgNo) {
		this.msgNo = msgNo;
	}

	public Integer getSendNo() {
		return sendNo;
	}

	public void setSendNo(Integer sendNo) {
		this.sendNo = sendNo;
	}

	public Integer getRecNo() {
		return recNo;
	}

	public void setRecNo(Integer recNo) {
		this.recNo = recNo;
	}

	public Integer getDateItemNo() {
		return dateItemNo;
	}

	public void setDateItemNo(Integer dateItemNo) {
		this.dateItemNo = dateItemNo;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Timestamp getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Timestamp msgTime) {
		this.msgTime = msgTime;
	}

	public Integer getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}
	
	
	
	
}
