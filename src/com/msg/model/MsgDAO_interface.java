package com.msg.model;

import java.util.List;


	public interface MsgDAO_interface {
		void add(MsgVO msgVO);
		void update(MsgVO msgVO);
		void delete(int msgNo);
		MsgVO findByPk(int msgNo);
		List<MsgVO> findByDateItemNo(int dateItemNo);
		void updateMsgByVO(MsgVO msgVO);
		List<MsgVO> getAll();
		int unread(int memNo);
		List<MsgVO> unreadList(int memNo);
	}
	

