package com.PetYM.DateItem;


import java.sql.Date;

public class DateItemVO implements java.io.Serializable {
	public DateItemVO(){}
	public DateItemVO(Integer dateItemNo, Integer sellerNo, Integer restListNo, String dateItemTitle,
			byte[] dateItemImg, String dateItemText, Date dateItemTime, Date dateMeetingTime, String dateItemLocate,
			Integer dateItemPeople, Integer hasMate, Integer dateItemPrice, Integer dateItemStatus,
			Integer dateItemShow, Integer dateItemViewer, Integer buyerNO, String isQRCChecked, Integer buyerRep,
			Integer sellerRep, Integer isInstantDate) {
		
		super();
		this.dateItemNo = dateItemNo;
		this.sellerNo = sellerNo;
		this.restListNo = restListNo;
		this.dateItemTitle = dateItemTitle;
		this.dateItemImg = dateItemImg;
		this.dateItemText = dateItemText;
		this.dateItemTime = dateItemTime;
		this.dateMeetingTime = dateMeetingTime;
		this.dateItemLocate = dateItemLocate;
		this.dateItemPeople = dateItemPeople;
		this.hasMate = hasMate;
		this.dateItemPrice = dateItemPrice;
		this.dateItemStatus = dateItemStatus;
		this.dateItemShow = dateItemShow;
		this.dateItemViewer = dateItemViewer;
		this.buyerNO = buyerNO;
		this.isQRCChecked = isQRCChecked;
		this.buyerRep = buyerRep;
		this.sellerRep = sellerRep;
		this.isInstantDate = isInstantDate;
	}
	private Integer dateItemNo;
	private Integer sellerNo;
	private Integer restListNo;
	private String dateItemTitle;
	private byte[] dateItemImg;
	private String dateItemText;
	private Date dateItemTime;
	private Date dateMeetingTime;
	private String dateItemLocate;
	private Integer dateItemPeople;
	private Integer hasMate;
	private Integer dateItemPrice;
	private Integer dateItemStatus;
	private Integer dateItemShow;
	private Integer dateItemViewer;
	private Integer buyerNO;
	private String isQRCChecked;
	private Integer buyerRep;
	private Integer sellerRep;
	private Integer isInstantDate;
	public Integer getDateItemNo() {
		return dateItemNo;
	}
	public void setDateItemNo(Integer dateItemNo) {
		this.dateItemNo = dateItemNo;
	}
	public Integer getSellerNo() {
		return sellerNo;
	}
	public void setSellerNo(Integer sellerNo) {
		this.sellerNo = sellerNo;
	}
	public Integer getRestListNo() {
		return restListNo;
	}
	public void setRestListNo(Integer restListNo) {
		this.restListNo = restListNo;
	}
	public String getDateItemTitle() {
		return dateItemTitle;
	}
	public void setDateItemTitle(String dateItemTitle) {
		this.dateItemTitle = dateItemTitle;
	}
	public byte[] getDateItemImg() {
		return dateItemImg;
	}
	public void setDateItemImg(byte[] dateItemImg) {
		this.dateItemImg = dateItemImg;
	}
	public String getDateItemText() {
		return dateItemText;
	}
	public void setDateItemText(String dateItemText) {
		this.dateItemText = dateItemText;
	}
	public Date getDateItemTime() {
		return dateItemTime;
	}
	public void setDateItemTime(Date dateItemTime) {
		this.dateItemTime = dateItemTime;
	}
	public Date getDateMeetingTime() {
		return dateMeetingTime;
	}
	public void setDateMeetingTime(Date dateMeetingTime) {
		this.dateMeetingTime = dateMeetingTime;
	}
	public String getDateItemLocate() {
		return dateItemLocate;
	}
	public void setDateItemLocate(String dateItemLocate) {
		this.dateItemLocate = dateItemLocate;
	}
	public Integer getDateItemPeople() {
		return dateItemPeople;
	}
	public void setDateItemPeople(Integer dateItemPeople) {
		this.dateItemPeople = dateItemPeople;
	}
	public Integer getHasMate() {
		return hasMate;
	}
	public void setHasMate(Integer hasMate) {
		this.hasMate = hasMate;
	}
	public Integer getDateItemPrice() {
		return dateItemPrice;
	}
	public void setDateItemPrice(Integer dateItemPrice) {
		this.dateItemPrice = dateItemPrice;
	}
	public Integer getDateItemStatus() {
		return dateItemStatus;
	}
	public void setDateItemStatus(Integer dateItemStatus) {
		this.dateItemStatus = dateItemStatus;
	}
	public Integer getDateItemShow() {
		return dateItemShow;
	}
	public void setDateItemShow(Integer dateItemShow) {
		this.dateItemShow = dateItemShow;
	}
	public Integer getDateItemViewer() {
		return dateItemViewer;
	}
	public void setDateItemViewer(Integer dateItemViewer) {
		this.dateItemViewer = dateItemViewer;
	}
	public Integer getBuyerNO() {
		return buyerNO;
	}
	public void setBuyerNO(Integer buyerNO) {
		this.buyerNO = buyerNO;
	}
	public String getIsQRCChecked() {
		return isQRCChecked;
	}
	public void setIsQRCChecked(String isQRCChecked) {
		this.isQRCChecked = isQRCChecked;
	}
	public Integer getBuyerRep() {
		return buyerRep;
	}
	public void setBuyerRep(Integer buyerRep) {
		this.buyerRep = buyerRep;
	}
	public Integer getSellerRep() {
		return sellerRep;
	}
	public void setSellerRep(Integer sellerRep) {
		this.sellerRep = sellerRep;
	}
	public Integer getIsInstantDate() {
		return isInstantDate;
	}
	public void setIsInstantDate(Integer isInstantDate) {
		this.isInstantDate = isInstantDate;
	}

}
