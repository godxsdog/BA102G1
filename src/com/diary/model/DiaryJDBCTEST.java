package com.diary.model;

public class DiaryJDBCTEST {
	
public static void main(String[] args) {
		
		DiaryJDBCDAO dao = new DiaryJDBCDAO();
		
		//addDia
		Diary diaVO1 = new Diary();
		diaVO1.setMemNo(1012);
		diaVO1.setDiaName("好貓12");
		diaVO1.setDiaText("今天貓兒真乖");
		diaVO1.setDiaImg(null);
		diaVO1.setDiaCreTime(new java.sql.Timestamp(System.currentTimeMillis()));
		diaVO1.setDiaModTime(new java.sql.Timestamp(System.currentTimeMillis()));
		diaVO1.setDiaState(0);
		
		dao.insert(diaVO1);
		
		//modify
//		Diary diary =new Diary();
//		diary.setDiaNo(1022);
//		diary.setDiaName("貓貓");
//		diary.setDiaText("不用上課");
//		diary.setDiaModTime(new java.sql.Timestamp(System.currentTimeMillis()));
//		diary.setDiaImg(null);
//		
//		dao.update(diary);
		
		//delete
		
//		dao.delete(1022);
		
		//query
//		Diary diary1 = dao.findByPrimaryKey(1028);
//		System.out.print(diary1.getDiaNo()+" ,");
//		System.out.print(diary1.getMemNo()+" ,");
//		System.out.print(diary1.getDiaName()+" ,");
//		System.out.print(diary1.getDiaText()+" ,");
//		System.out.print(diary1.getDiaImg()+" ,");
//		System.out.print(diary1.getDiaCreTime()+" ,");
//		System.out.print(diary1.getDiaModTime()+" ,");
//		System.out.print(diary1.getDiaState()+" ,");
//		System.out.println("---------------------------------");
		
		
		//query all
//		List<Diary> list = dao.getAll();
//		for(Diary dia : list){
//			System.out.print(dia.getDiaNo()+" ,");
//			System.out.print(dia.getMemNo()+" ,");
//			System.out.print(dia.getDiaName()+" ,");
//			System.out.print(dia.getDiaText()+" ,");
//			System.out.print(dia.getDiaImg()+" ,");
//			System.out.print(dia.getDiaCreTime()+" ,");
//			System.out.print(dia.getDiaModTime()+" ,");
//			System.out.print(dia.getDiaState()+" ,");
//			System.out.println("---------------------------------");
//		}
		
		System.out.println("結束");
		
		
		
		
	}

}
