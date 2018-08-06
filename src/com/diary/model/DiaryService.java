package com.diary.model;

import java.sql.Timestamp;
import java.util.List;

public class DiaryService {
	
	private DiaryDAO_Interface dao;
	
	public DiaryService(){
		dao = new DiaryDAO();
	}
	
	public Diary addDia(Integer memNo, String diaName,String diaText, byte[] diaImg, 
					Timestamp diaCreTime, Timestamp diaModTime, Integer diaState, String diaImgExtName){
		Diary diary = new Diary();
		
		diary.setMemNo(memNo);
		diary.setDiaName(diaName);
		diary.setDiaText(diaText);
		diary.setDiaImg(diaImg);
		diary.setDiaCreTime(diaCreTime);
		diary.setDiaModTime(diaModTime);
		diary.setDiaState(diaState);
		diary.setDiaImgExtName(diaImgExtName);
		
		dao.insert(diary);
	
		return diary;
	}
	
	public Diary updateDia(Integer memNo, String diaName,String diaText, byte[] diaImg, 
					 Timestamp diaModTime, Integer diaState, Integer diaNo, String diaImgExtName){
		
		Diary diary = new Diary();
		
		diary.setMemNo(memNo);
		diary.setDiaName(diaName);
		diary.setDiaText(diaText);
		diary.setDiaImg(diaImg);
		diary.setDiaModTime(diaModTime);
		diary.setDiaState(diaState);
		diary.setDiaNo(diaNo);
		diary.setDiaImgExtName(diaImgExtName);
		
		dao.update(diary);
		
		return diary;
	}
	
	public void deleteDia(Integer diaNo){
		dao.delete(diaNo);
	}
	
	
	public Diary getOneDia(Integer diaNo){
		return dao.findByPrimaryKey(diaNo);
	}
	
	
	public List<Diary> getAll(){
		return dao.getAll();
	}
	
	public List<Diary> getAllFromMemNo(Integer memNo){
		return dao.getOneMemNo(memNo);
	}

}
