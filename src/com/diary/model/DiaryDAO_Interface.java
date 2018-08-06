package com.diary.model;

import java.util.List;

public interface DiaryDAO_Interface {
		
		public void insert(Diary diary);
		public void update(Diary diary);
		public void delete(Integer diaNo);
		public Diary findByPrimaryKey(Integer diaNo);
		public List<Diary> getAll();
		public List<Diary> getOneMemNo(Integer memNo);
		

}
