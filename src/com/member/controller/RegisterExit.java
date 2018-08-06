package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.Member;
import com.member.model.MemberService;

@WebServlet("/front_end/member/RegisterExit.do")
public class RegisterExit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();

		// 檢驗會員id是否存在
		if ("memId".equals(action)) {
			String memId = req.getParameter("memId");
			MemberService memSvc = new MemberService();
			Member member = memSvc.getOneMemberById(memId);

			if (memId.length() < 6) {
				out.print("帳號長度需大於6");
			} else if (!memId.matches(".*[a-zA-Z]+.*")) {
				out.print("帳號需含英文字");
			} else if (memId.length() > 20) {
				out.print("帳號過長");
			} else {
				if (member != null) {
					out.print("很抱歉,帳號已存在");
				} else {
					out.print("合格的帳號");
				}
			}

		}

		// 檢驗會員email是否存在
		if ("memEmail".equals(action)) {
			String memEmail = req.getParameter("memEmail");
			MemberService memSvc = new MemberService();
			Member member = memSvc.getMemberByEmail(memEmail);
			System.out.println(validate(memEmail));

			if(!validate(memEmail)){
				out.println("抱歉，Email格式不正確");
				return;
			}
			if (member != null) {
				out.print("抱歉，此Email已經註冊過了");
			} 
			else {
				out.print("emailOK");
			}

		}

	}

	
	
	
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		public static boolean validate(String emailStr) {
				java.util.regex.Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		        return matcher.find();
		}
	
	
	
	
	
}
