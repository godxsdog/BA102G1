package com.diary.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.diary.model.Diary;
import com.diary.model.DiaryService;


public class ShowVideo extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		doPost(req,res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		Integer diaNo=Integer.parseInt(req.getParameter("diano"));
		
		DiaryService diaSvc=new DiaryService();
		res.setBufferSize(1024*1024);
		res.setContentType("video/*");
		ServletOutputStream out = res.getOutputStream();
		try {			
			Diary diary = diaSvc.getOneDia(diaNo);
			BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(diary.getDiaImg()));
			//ByteArrayInputStream in = new ByteArrayInputStream(diary.getDiaImg());
			byte[] buf = new byte[8 * 1024]; // 4K buffer
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			in.close();
		} catch (Exception e) {

		}
	}
	
	
	
	
}
