package com.dateitem.controller;

import com.dateitem.model.DateItemService;
import com.dateitem.model.DateItemVO;
import com.member.model.*;

public class BuyDateItem {
	
	synchronized public Boolean buyAItem(int buyerNo, DateItemVO dateItemVO) {
		Boolean sucessBuy = false;
		if(dateItemVO.getDateItemShow()==0&&
				dateItemVO.getDateItemStatus()==0){
						
		dateItemVO.setBuyerNo(buyerNo);
		dateItemVO.setDateItemStatus(1);
		dateItemVO.setDateItemShow(1);
		DateItemService dSvc = new DateItemService();
		dSvc.updateByVO(dateItemVO);
		
		//¦©´Ú
		MemberService mSvc = new MemberService();
		int currentPoint = mSvc.getOneMember(buyerNo).getMemPoint();
		mSvc.getOneMember(buyerNo).setMemPoint(currentPoint-dateItemVO.getDateItemPrice());
		sucessBuy=true;
		} 
		return sucessBuy;
	}
}
