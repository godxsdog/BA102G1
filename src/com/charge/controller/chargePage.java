package com.charge.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.charge.model.ChargesService;
import com.member.model.Member;
import com.member.model.MemberService;

@WebServlet("/chargePage")
public class chargePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Member mem = (Member) session.getAttribute("member");
		req.setCharacterEncoding("UTF-8");
		

		java.util.Date appTime = new java.util.Date();
		java.sql.Date applyTime = new java.sql.Date(appTime.getTime());

		if (req.getParameter("action").equals("payment")) {
			Set<String> errorMsgs = new HashSet<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
				
				String numbe = req.getParameter("number").trim();
				String numbe1 = numbe.replaceAll(" ", "");
				long number = Long.parseLong(numbe1);
				System.out.println(number);
				String name = req.getParameter("name");
				System.out.println(isChineseName(name));
				
				
				String expir = req.getParameter("expiry");
				String expiry1 = expir.replaceAll("/","");
				String expiry2 = expiry1.replaceAll(" ","");
				int expiry = Integer.valueOf(expiry2);
				System.out.println(expiry);
				String cvc = req.getParameter("cvc");
				if(calc(numbe1)%10!=0){
					errorMsgs.add("請輸入正確資訊");
				}
				if(!isChineseName(name)){
					errorMsgs.add("請輸入正確資訊");
				}
				Long one = null;
				try {
					one = number;
				} catch (Exception e) {
					errorMsgs.add("請輸入正確資訊");
				}
				String two = null;
				try {
					two = new String(name);
				} catch (Exception e) {
					errorMsgs.add("請輸入正確資訊");
				}
				Integer three = null;
				try {
					three = new Integer(expiry);
				} catch (Exception e) {
					errorMsgs.add("請輸入正確資訊");
				}
				Integer ccv = null;
				try {
					ccv = new Integer(cvc);
				} catch (Exception e) {
					errorMsgs.add("請輸入正確資訊");
				}
				
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/charge/chargePage.jsp");
					dispatcher.forward(req, res);
					return;
				}
				if (mem == null) {
					RequestDispatcher rd = req.getRequestDispatcher("front_end/member/login.jsp");
					rd.forward(req, res);
				} else {

					ChargesService chargeDao = new ChargesService();
					int chargeIn = Integer.valueOf(req.getParameter("chargeNum"));
					MemberService memDao = new MemberService();
					int chargeNow = mem.getMemPoint() + chargeIn;
					Member member = memDao.updateMember(mem.getMemNo(), mem.getMemId(), mem.getMemPwd(), mem.getMemName(),
							mem.getMemSname(), mem.getMemGender(), mem.getMemIdNo(), mem.getMemBday(), mem.getMemPhone(),
							mem.getMemAddress(), mem.getMemEmail(), mem.getMemImg(), mem.getMemReported(),
							mem.getMemStatus(), mem.getMemRelation(), mem.getMemSelfintro(), mem.getMemFollowed(),
							chargeNow, mem.getMemSaleRank(), mem.getMemLongtitude(), mem.getMemLatitude(),
							mem.getMemLocTime(), mem.getMemLocStatus());
					chargeDao.addCharge(mem.getMemNo(), chargeIn, applyTime, 1, 0);
					session.setAttribute("member", member);

					RequestDispatcher rd = req.getRequestDispatcher("front_end/product/Cart.jsp");
					rd.forward(req, res);
				}
				
			
			
			
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req, res);
	}
	
	
	public static boolean isChineseName(String name) {
		Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,5}$");
		Matcher matcher = pattern.matcher(name);
		if(matcher.find()){
		return true;
		}
		return false;
		}
	public static int calc(String s){  
        int odd = 0;  
        int even = 0;  
        int t = 0;  
        char[] c = s.toCharArray();  
        if(c.length%2==0){  // 憒��銝箏�銝�,��洵銝�銝芣隞�撘�憪�絲  
            for(int i=0;i<c.length;i++){  
                t = c[i]-'0';  
                if(i%2!=0){  
                    odd += t;  
                }else{       // 蝚砌�銝芣�����  
                    if(t*2>=10){  
                        even += t*2-9;  
                    }else{  
                        even += t*2;  
                    }  
                }  
            }  
        }else{       // 憒��銝箏�銝�,��洵銝�銝芣隞�撘�憪�絲  
            for(int i=0;i<c.length;i++){  
                t = c[i]-'0';  
                if(i%2==0){ // 蝚砌�銝芣���憟  
                    odd += t;  
                }else{  
                    if(t*2>=10){  
                        even += t*2-9;  
                    }else{  
                        even += t*2;  
                    }  
                }  
            }  
        }  
        return odd+even;    // 餈��雿�餃����雿�餃��  
    }  
}
