package com.diamsg.model;

import java.sql.Timestamp;
import java.util.List;

public class DiaMsgService {

	private DiaMsgDAO_Interface dao;
	
	public DiaMsgService(){
		dao = new DiaMsgDAO();
	}
	
	public DiaMsg addDiaMsg(Integer diaNo, Integer memNo, String diaMsgText, Timestamp diaMsgTime, Integer diaMsgState){
		
		DiaMsg diaMsg = new DiaMsg();
		
		diaMsg.setDiaNo(diaNo);
		diaMsg.setMemNo(memNo);
		diaMsg.setDiaMsgText(diaMsgText);
		diaMsg.setDiaMsgTime(diaMsgTime);
		diaMsg.setDiaMsgState(diaMsgState);
		
		dao.insert(diaMsg);
		
		return diaMsg;
		
	}
	
	public DiaMsg updateDiaMsg(Integer diaMsgNo,Integer diaNo, Integer memNo, String diaMsgText, Timestamp diaMsgTime, Integer diaMsgState ){
		
		DiaMsg diaMsg = new DiaMsg();
		
		diaMsg.setDiaMsgNo(diaMsgNo);
		diaMsg.setDiaNo(diaNo);
		diaMsg.setMemNo(memNo);
		diaMsg.setDiaMsgText(diaMsgText);
		diaMsg.setDiaMsgTime(diaMsgTime);
		diaMsg.setDiaMsgState(diaMsgState);
		
		dao.update(diaMsg);
		
		return diaMsg;
		
	}
	
	public void deleteDiaMsg(Integer diaMsgNo){
		dao.delete(diaMsgNo);
	}
	
	public DiaMsg getOneDiaMsg(Integer diaMsgNo){
		return dao.findByPrimaryKey(diaMsgNo);
	}
	
	public List<DiaMsg> getAll(){
		return dao.getAll();
	}
	
	public List<DiaMsg> getAllMsgFromDia(Integer diaNo){
		return dao.getAllMsgFromDia(diaNo);
	}
	
	public Integer getCurrNo(){
		return dao.getCurrDiaMsgNo();
	}
	
}
