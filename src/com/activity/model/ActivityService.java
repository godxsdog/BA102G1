package com.activity.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class ActivityService {
	private ActivityDAO_Interface activityDao;
	
	public ActivityService(){
		activityDao = new ActivityDAO();
	}
	
	public Activity addActivity(String restMemId, String actName, String actContent, Date actDate, Date actFDate,
			Integer actStatus, Integer actULimit, Integer actLLimit, Integer actKind, String actAnotherKind,
			byte[] actInitImg){
			Activity activity = new Activity();
			activity.setRestMemId(restMemId);
			activity.setActName(actName);
			activity.setActContent(actContent);
			activity.setActDate(actDate);
			activity.setActFDate(actFDate);
			activity.setActStatus(actStatus);
			activity.setActULimit(actULimit);
			activity.setActLLimit(actLLimit);
			activity.setActKind(actKind);
			activity.setActAnotherKind(actAnotherKind);
			activity.setActInitImg(actInitImg);
			activityDao.add(activity);
			return activity;
		}
	
	public Activity updateActivity(Integer actNo,String restMemId, String actName, String actContent, Date actDate, Date actFDate,
			Integer actStatus, Integer actULimit, Integer actLLimit, Integer actKind, String actAnotherKind,
			byte[] actInitImg){
			Activity activity = new Activity();
			activity.setActNo(actNo);
			activity.setRestMemId(restMemId);
			activity.setActName(actName);
			activity.setActContent(actContent);
			activity.setActDate(actDate);
			activity.setActFDate(actFDate);
			activity.setActStatus(actStatus);
			activity.setActULimit(actULimit);
			activity.setActLLimit(actLLimit);
			activity.setActKind(actKind);
			activity.setActAnotherKind(actAnotherKind);
			activity.setActInitImg(actInitImg);
			activityDao.update(activity);
			return activity;
		}
	
	public Activity updateBack(Integer actStatus,Integer actNo){
		Activity activity = new Activity();
		activity.setActStatus(actStatus);
		activity.setActNo(actNo);
		activityDao.updateBack(actStatus, actNo);
		return activity;
	}
	
	public void deleteActivity(Integer actNo){
		activityDao.delete(actNo);
	}
	
	public Activity getOneActivity(Integer actNo){
		return activityDao.findByPK(actNo);
	}
	
	public Activity getOneActivityByActStatus(Integer actNo,Integer actStatus){
		return activityDao.findByPKStatus(actNo, actStatus);
	}
	
	public List<Activity> getAll(){
		return activityDao.getAll();
	}
	
	public List<Activity> getAll(Map<String, String[]> map){
		return activityDao.getAll(map);
	}
	
	public List<Activity> getAllById(String restMemId){
		return activityDao.getAllById(restMemId);
	}
	
	public List<Activity> getAllByStatus(Integer actStatus){
		return activityDao.getAllByStatus(actStatus);
	}
	
	public List<Activity> getAllByStatusAnimal(Integer actStatus,Integer actKind){
		return activityDao.getAllByStatusAnimal(actStatus, actKind);
	}
	
	
	public List<Activity> getAllNorth(){
		return activityDao.getAllByNorth();
	}
	
	public List<Activity> getAllEast(){
		return activityDao.getAllByEast();
	}
	
	public List<Activity> getAllWest(){
		return activityDao.getAllByWest();
	}
	
	public List<Activity> getAllSouth(){
		return activityDao.getAllBySouth();
	}
	
	public List<Activity> getAllOfMine(Integer memNo){
		return activityDao.getAllOfMine(memNo);
	}
}






