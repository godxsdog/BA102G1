package com.msg.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.dateitem.model.DateItemVO;


public class MsgService {

	private MsgDAO_interface dao;
	
	public MsgService() {
		dao = new MsgDAO();
	}
	
	public MsgVO addMsg(Integer sendNo, Integer recNo, Integer dateItemNo,
			String msgContent, Timestamp msgTime, Integer msgStatus) {
		
		MsgVO msgVO = new MsgVO();
		
		msgVO.setSendNo(sendNo);
		msgVO.setRecNo(recNo);
		msgVO.setDateItemNo(dateItemNo);
		msgVO.setMsgContent(msgContent);
		msgVO.setMsgTime(msgTime);
		msgVO.setMsgStatus(msgStatus);
		dao.add(msgVO);
		
		return msgVO;
	}
	
	
	public MsgVO updateMsg(Integer msgNo, Integer sendNo, Integer recNo, Integer dateItemNo,
			String msgContent, Timestamp msgTime, Integer msgStatus){
		
		MsgVO msgVO = new MsgVO();
		
		msgVO.setMsgNo(msgNo);
		msgVO.setSendNo(sendNo);
		msgVO.setRecNo(recNo);
		msgVO.setDateItemNo(dateItemNo);
		msgVO.setMsgContent(msgContent);
		msgVO.setMsgTime(msgTime);
		msgVO.setMsgStatus(msgStatus);
		dao.update(msgVO);
		
		return msgVO;
	}
	
	public void updateMsgByVO(MsgVO MsgVO){
		dao.updateMsgByVO(MsgVO);
	}
	
	public void deleteMsg(Integer msgNo) {
		dao.delete(msgNo);
	}

	public MsgVO getOneEmp(Integer msgNo) {
		return dao.findByPk(msgNo);
	}

	public List<MsgVO> getAll() {
		return dao.getAll();
	}
	
	public Integer unread(int recNo) {
		return dao.unread(recNo);
	}
	
	public List<MsgVO> unreadList(int recNo) {
		return dao.unreadList(recNo);
	}
	
	public List<MsgVO> findByDateItemNo(Integer dateItemNo) {
		return dao.findByDateItemNo(dateItemNo);
	}
}
