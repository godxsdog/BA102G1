package com.activity.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.activity.model.Activity;
import com.activity.model.ActivityService;
import com.pet.model.Pet;
import com.pet.model.PetService;


public class ActImgReader extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Integer actNo = Integer.parseInt(req.getParameter("actNo"));
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {
			ActivityService activityService = new ActivityService();
			Activity activity = activityService.getOneActivity(actNo);
			out.write(activity.getActInitImg());

		} catch (Exception e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
