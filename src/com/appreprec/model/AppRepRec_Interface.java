package com.appreprec.model;

import java.util.List;


public interface AppRepRec_Interface {

	public void insert(AppRepRec appRepRec);//新增紀錄
	public void delete(AppRepRec appRepRec);//刪除該會員的某一筆紀錄
	public List<AppRepRec> findByPrimaryKey(Integer memNo);//秀出該會員所有的檢舉申訴日期
	public Integer findOneMonthTimes(Integer memNo);//秀出該會員近一個月的次數
	public List<AppRepRec> getAll();
	
	
}
