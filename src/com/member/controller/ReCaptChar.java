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
@WebServlet("/ReCaptChar2")
public class ReCaptChar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		System.out.println("123456");
//		String remoteAddr = req.getRemoteAddr();
//        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
//        reCaptcha.setPrivateKey("6Lcybi0UAAAAAL_Y7GHvZtjZUkNabKyCO4x7ukDy");
// 
//        String challenge = req.getParameter("recaptcha_challenge_field");
//        String uresponse = req.getParameter("recaptcha_response_field");
//        System.out.println(challenge+"  "+uresponse);
//        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
// 
//        if (reCaptchaResponse.isValid()) 
//        {
//            System.out.print("Answer was entered correctly!");
//        } else
//        {
//            System.out.print("Answer is wrong");
//        }
		String user = req.getParameter("user");
		String pwd = req.getParameter("pwd");
		// get reCAPTCHA request param
		String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
		System.out.println("123"+gRecaptchaResponse);
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		System.out.println(verify);
        
        
        
        
        
        
        
        
        
        
        
	}

}
