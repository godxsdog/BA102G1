package com.PetYM.puDateItem;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


public class DateItemService {

	private DateItemDAO_interface dao;
	
	public DateItemService() {
		dao = new DateItemDAO();
	}
	
	public DateItemVO addDateItem(Integer sellerNo, Integer restListNo, String dateItemTitle, byte[] dateItemImg,
			String dateItemText, Timestamp dateItemTime, Timestamp dateMeetingTime, String dateItemLocate, Integer dateItemPeople,
			Boolean hasMate, Integer dateItemPrice, Integer dateItemStatus, Integer dateItemShow, Integer dateItemViewer, Integer buyerNo,
			Boolean isQRCChecked, Integer buyerRep, Integer sellerRep, Boolean isInstantDate , Integer petNo) {
		
		DateItemVO dateItemVO = new DateItemVO();
		
		dateItemVO.setSellerNo(sellerNo);
		dateItemVO.setRestListNo(restListNo);
		dateItemVO.setDateItemTitle(dateItemTitle);
		dateItemVO.setDateItemImg(dateItemImg);
		dateItemVO.setDateItemText(dateItemText);
		dateItemVO.setDateItemTime(dateItemTime);
		dateItemVO.setDateMeetingTime(dateMeetingTime);
		dateItemVO.setDateItemLocate(dateItemLocate);
		dateItemVO.setDateItemPeople(dateItemPeople);
		dateItemVO.setHasMate(hasMate);
		dateItemVO.setDateItemPrice(dateItemPrice);
		dateItemVO.setDateItemStatus(dateItemStatus);
		dateItemVO.setDateItemViewer(dateItemViewer);
		dateItemVO.setBuyerNo(buyerNo);
		dateItemVO.setIsQRCChecked(isQRCChecked);
		dateItemVO.setBuyerRep(buyerRep);
		dateItemVO.setSellerRep(sellerRep);
		dateItemVO.setIsInstantDate(isInstantDate);
		dateItemVO.setPetNo(petNo);
		dao.add(dateItemVO);
		
		return dateItemVO;
	}
	
	
	public DateItemVO updateDateItem(Integer dateItemNo, Integer sellerNo, Integer restListNo, String dateItemTitle, byte[] dateItemImg,
			String dateItemText, Timestamp dateItemTime, Timestamp dateMeetingTime, String dateItemLocate, Integer dateItemPeople,
			Boolean hasMate, Integer dateItemPrice, Integer dateItemStatus, Integer dateItemShow, Integer dateItemViewer, Integer buyerNo,
			Boolean isQRCChecked, Integer buyerRep, Integer sellerRep, Boolean isInstantDate , Integer petOrdNo) {
		
		DateItemVO dateItemVO = new DateItemVO();
		
		dateItemVO.setDateItemNo(dateItemNo);
		dateItemVO.setSellerNo(sellerNo);
		dateItemVO.setRestListNo(restListNo);
		dateItemVO.setDateItemTitle(dateItemTitle);
		dateItemVO.setDateItemImg(dateItemImg);
		dateItemVO.setDateItemText(dateItemText);
		dateItemVO.setDateItemTime(dateItemTime);
		dateItemVO.setDateMeetingTime(dateMeetingTime);
		dateItemVO.setDateItemLocate(dateItemLocate);
		dateItemVO.setDateItemPeople(dateItemPeople);
		dateItemVO.setHasMate(hasMate);
		dateItemVO.setDateItemPrice(dateItemPrice);
		dateItemVO.setDateItemStatus(dateItemStatus);
		dateItemVO.setDateItemViewer(dateItemViewer);
		dateItemVO.setBuyerNo(buyerNo);
		dateItemVO.setIsQRCChecked(isQRCChecked);
		dateItemVO.setBuyerRep(buyerRep);
		dateItemVO.setSellerRep(sellerRep);
		dateItemVO.setIsInstantDate(isInstantDate);
		dateItemVO.setPetNo(petOrdNo);
		dao.update(dateItemVO);
		
		return dateItemVO;
	}
	
	public void deleteDateItem(Integer dateItemNo) {
		dao.delete(dateItemNo);
	}

	public DateItemVO getOneDateItem(Integer dateItemNo) {
		return dao.findByPk(dateItemNo);
	}

	public List<DateItemVO> getAll() {
		return dao.getAll();
	}
}
