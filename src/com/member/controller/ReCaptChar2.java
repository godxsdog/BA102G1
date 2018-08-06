package com.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 * Servlet implementation class ReCaptChar
 */
@WebServlet("/ReCaptChar")
public class ReCaptChar2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String user = req.getParameter("user");
		String pwd = req.getParameter("pwd");
		// get reCAPTCHA request param
		String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		System.out.println(verify);

	}

}
