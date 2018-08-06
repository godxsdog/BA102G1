package com.member.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.email.CrunchifyValidateEmail;
import com.email.MailService;
import com.member.model.Member;
import com.member.model.MemberService;
import com.pet.model.Pet;
import com.pet.model.PetService;

@WebServlet("/front_end/member/member.do")
@MultipartConfig
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		MemberService memSvc = new MemberService();

		if ("memUpdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			try {
				/*****************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				// 暱稱
				String memSname = req.getParameter("memSname");
				if (memSname == null || memSname.trim().isEmpty()) {
					errorMsgs.add("請填寫暱稱");
				}
				if (memSname.length() > 30) {
					errorMsgs.add("暱稱長度過長");
				}

				// 姓名
				String memName = req.getParameter("memName");
				if (memName == null || memName.trim().isEmpty()) {
					errorMsgs.add("請填寫姓名");
				}
				if (memName.trim().length() > 30) {
					errorMsgs.add("姓名長度請勿大於20");
				}

				// 生日
				java.sql.Date memBday = null;
				try {
					memBday = java.sql.Date.valueOf(req.getParameter("memBday"));
				} catch (IllegalArgumentException e) {
					errorMsgs.add("日期格式錯誤");
				}
				long memBdayInLong = memBday.getTime();
				long nowTimeInLong = System.currentTimeMillis();
				if (nowTimeInLong < memBdayInLong) {
					errorMsgs.add("生日請誤大於今天");
				}

				// 手機
				String memPhone = req.getParameter("memPhone");
				if (!(memPhone.matches("[09]{2}[0-9]{2}-[0-9]{6}") || memPhone.matches("[09]{2}[0-9]{8}"))) {
					errorMsgs.add("手機格式錯誤");
				}

				// 性別
				Integer memGender = null;
				try {
					memGender = Integer.parseInt(req.getParameter("memGender").trim());
				} catch (IllegalArgumentException e) {
					memGender = 2;
					errorMsgs.add("請輸入性別");
				}

				// 感情狀態
				Integer memRelation = null;
				try {
					memRelation = Integer.parseInt(req.getParameter("memRelation").trim());
				} catch (IllegalArgumentException e) {
					memRelation = 2;
					errorMsgs.add("請輸入感情狀態");
				}

				// Email
				String memEmail = req.getParameter("memEmail");
				CrunchifyValidateEmail crunchifyCheck = new CrunchifyValidateEmail();
				boolean isValidEmail = crunchifyCheck.crunchifyEmailValidator(memEmail);
				if (!isValidEmail) {
					errorMsgs.add("請輸入正確的Email信箱");
				}
				Member emailMember = memSvc.getMemberByEmail(memEmail);
				if (emailMember != null) {
					if (!emailMember.getMemId().equals(member.getMemId())) {
						errorMsgs.add("此信箱已經有人註冊過，請更換信箱註冊");
						System.out.println("abc");
					}
				}
				if (memEmail.trim().length() > 100) {
					errorMsgs.add("信箱長度請勿大於100");
				}

				// 地址
				String county = req.getParameter("county").trim();
				System.out.println("縣市: " + county);
				String district = req.getParameter("district").trim();
				System.out.println("區: " + district);
				String memAddress = req.getParameter("memAddress").trim();
				if (county == null || county.isEmpty()) {
					errorMsgs.add("請選擇縣市");
				}
				if (district == null || district.isEmpty()) {
					errorMsgs.add("請選擇地址區域");
				}
				if (memAddress == null || memAddress.isEmpty()) {
					errorMsgs.add("請填寫地址");
				}
				if (memAddress.trim().length() > 100) {
					errorMsgs.add("地址長度請勿大於100");
				}
				String totalAddress = county + "，" + district + "，" + memAddress;

				// 自我介紹
				String memSelfintro = req.getParameter("memSelfintro");
				if (memSelfintro == null || memSelfintro.trim().isEmpty()) {
					errorMsgs.add("請輸入自我介紹");
				}
				if (memSelfintro.trim().length() > 600) {
					errorMsgs.add("自我介紹長度請勿大於600");
				}

				byte[] memImg = member.getMemImg();
				Collection<Part> parts = req.getParts();

				for (Part part : parts) {
					if ("memImg".equals(part.getName())&&part.getSize()!=0) {
						System.out.println("檔案大小:"+part.getSize());
							memImg = getPictureByteArray(part.getInputStream());
						if (!(part.getContentType().startsWith("image"))) {
							errorMsgs.add("照片格式有誤");
						}
					}
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memberInfo.jsp");
					req.setAttribute("errorMsgs", errorMsgs);
					dispatcher.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/

				memSvc.updateMember(member.getMemNo(), member.getMemId(), member.getMemPwd(), memName, memSname,
						memGender, member.getMemIdNo(), memBday, memPhone, totalAddress, memEmail, memImg,
						member.getMemReported(), member.getMemStatus(), memRelation, memSelfintro,
						member.getMemFollowed(), member.getMemPoint(), member.getMemSaleRank(),
						member.getMemLongtitude(), member.getMemLatitude(), member.getMemLocTime(),
						member.getMemLocStatus());

				/****************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				Integer memNo = member.getMemNo();
				session.removeAttribute("member");
				Member newMember = memSvc.getOneMember(memNo);
				session.setAttribute("member", newMember);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memberInfo.jsp");
				errorMsgs.add("修改資料成功");
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memberInfo.jsp");
				errorMsgs.add("修改資料失敗");
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
			}
		}

		// 修改密碼
		if ("pwdChange".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/

			String memPwd = req.getParameter("memPwd");
			if (!memPwd.equals(member.getMemPwd())) {
				errorMsgs.add("目前的密碼錯誤");
			}
			if (memPwd.trim().length() > 30) {
				errorMsgs.add("目前的密碼過長");
			}

			String memNewPwd = req.getParameter("memNewPwd");
			if (!(memNewPwd.matches("^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{4,20}$") && memNewPwd.trim().length() > 30)) {
				errorMsgs.add("新密碼格式不符");
			}

			Map<String, String> falsePwd = new HashMap<String, String>();
			falsePwd.put("memPwd", memPwd);
			falsePwd.put("memNewPwd", memNewPwd);

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memPwdChange.jsp");
				req.setAttribute("falsePwd", falsePwd);
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
				return;
			}

			/*************************** 2.開始修改資料 *****************************************/
			memSvc.updateMember(member.getMemNo(), member.getMemId(), memNewPwd, member.getMemName(),
					member.getMemSname(), member.getMemGender(), member.getMemIdNo(), member.getMemBday(),
					member.getMemPhone(), member.getMemAddress(), member.getMemEmail(), member.getMemImg(),
					member.getMemReported(), member.getMemStatus(), member.getMemRelation(), member.getMemSelfintro(),
					member.getMemFollowed(), member.getMemPoint(), member.getMemSaleRank(), member.getMemLongtitude(),
					member.getMemLatitude(), member.getMemLocTime(), member.getMemLocStatus());

			/****************************
			 * 3.修改完成,準備轉交(Send the Success view)
			 *************/
			Integer memNo = member.getMemNo();
			session.removeAttribute("member");
			Member newMember = memSvc.getOneMember(memNo);
			session.setAttribute("member", newMember);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memPwdChange.jsp");
			req.setAttribute("success", "密碼修改成功");
			dispatcher.forward(req, res);

		}

		// 會員註冊
		if ("register".equals(action)) {

			/****************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			String memId = req.getParameter("memId").trim();
			String memPwd = req.getParameter("memPwd").trim();
			String memName = req.getParameter("memName").trim();
			String memSname = req.getParameter("memSname").trim();
			String memIdNo = req.getParameter("memIdNo").trim();
			String memPhone = req.getParameter("memPhone").trim();
			String county = req.getParameter("county").trim();
			String district = req.getParameter("district").trim();
			String memAddress = req.getParameter("memAddress").trim();
			String memEmail = req.getParameter("memEmail").trim();
			String gRecaptchaResponse = req.getParameter("g-recaptcha-response");

			System.out.println(gRecaptchaResponse);
			Member fMem = new Member();
			boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			// 帳號
			String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{4,20}$";
			if (memId == null || memId.isEmpty()) {
				errorMsgs.add("請填寫帳號");
			}
			if (memId.trim().length() > 30) {
				errorMsgs.add("帳號長度請勿大於20");
			}
			if (!memId.matches(enameReg)) {
				errorMsgs.add("帳號長度請大於4小於20，且應為大小寫英文字或數字");
			}
			Member IDMember = memSvc.getOneMemberById(memId);
			if (IDMember != null) {
				errorMsgs.add("帳號已存在");
			}
			fMem.setMemId(memId);

			// 密碼
			if (memPwd == null || memPwd.isEmpty()) {
				errorMsgs.add("請填寫密碼");
			}
			if (memPwd.trim().length() > 30) {
				errorMsgs.add("密碼長度請勿大於20");
			}
			if (!memPwd.matches(enameReg)) {
				errorMsgs.add("密碼長度請大於4小於20，且應為大小寫英文字或數字");
			}
			fMem.setMemPwd(memPwd);

			// 姓名
			if (memName == null || memName.isEmpty()) {
				errorMsgs.add("請填寫姓名");
			}
			if (memName.trim().length() > 30) {
				errorMsgs.add("姓名長度請勿大於20");
			}
			fMem.setMemName(memName);

			// 暱稱
			if (memSname == null || memSname.isEmpty()) {
				errorMsgs.add("請填寫暱稱");
			}
			if (memSname.trim().length() > 30) {
				errorMsgs.add("暱稱長度請勿大於20");
			}
			fMem.setMemSname(memSname);

			// 性別
			Integer memGender = null;
			try {
				memGender = Integer.parseInt(req.getParameter("memGender").trim());
			} catch (IllegalArgumentException e) {
				memGender = 1;
				errorMsgs.add("請輸入性別");
			}
			fMem.setMemGender(memGender);

			// 身分證字號
			boolean isValidID = isValidIDorRCNumber(memIdNo);
			if (memIdNo == null || memIdNo.isEmpty()) {
				errorMsgs.add("請填寫身分證字號");
			}
			if (!isValidID || memIdNo.trim().length() > 30) {
				errorMsgs.add("不合格的身分證字號");
			}
			fMem.setMemIdNo(memIdNo);

			// 生日
			java.sql.Date memBday = null;
			try {
				memBday = java.sql.Date.valueOf(req.getParameter("memBday").trim());
			} catch (IllegalArgumentException e) {
				memBday = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入生日!");
			}
			fMem.setMemBday(memBday);
			long memBdayInLong = memBday.getTime();
			long nowTimeInLong = System.currentTimeMillis();
			if (nowTimeInLong < memBdayInLong) {
				errorMsgs.add("生日請誤大於今天");
			}

			// 手機
			String phoneRegex1 = "[09]{2}[0-9]{2}-[0-9]{6}";
			String phoneRegex2 = "[09]{2}[0-9]{8}";
			if (memPhone == null || memPhone.isEmpty()) {
				errorMsgs.add("請填寫手機");
			}
			if (!(memPhone.matches(phoneRegex1) || memPhone.matches(phoneRegex2))) {
				errorMsgs.add("手機格式錯誤");
			}
			fMem.setMemPhone(memPhone);

			// 地址
			if (county == null || county.isEmpty()) {
				errorMsgs.add("請選擇縣市");
			}
			if (district == null || district.isEmpty()) {
				errorMsgs.add("請選擇地址區域");
			}
			if (memAddress == null || memAddress.isEmpty()) {
				errorMsgs.add("請填寫地址");
			}
			if (memAddress.trim().length() > 100) {
				errorMsgs.add("地址長度請勿大於100");
			}
			fMem.setMemAddress(memAddress);
			String totalAddress = county + "，" + district + "，" + memAddress;

			// 信箱
			CrunchifyValidateEmail crunchifyCheck = new CrunchifyValidateEmail();
			boolean isValidEmail = crunchifyCheck.crunchifyEmailValidator(memEmail);
			if (memEmail == null || memEmail.isEmpty() || !isValidEmail) {
				errorMsgs.add("請填寫信箱");
			}
			Member emailMember = memSvc.getMemberByEmail(memEmail);
			if (emailMember != null) {
				errorMsgs.add("此信箱已經有人註冊過，請更換信箱註冊");
			}
			if (memEmail.trim().length() > 100) {
				errorMsgs.add("信箱長度請勿大於100");
			}
			fMem.setMemEmail(memEmail);

			byte[] memImg = null;
			Collection<Part> parts = req.getParts();
			for (Part part : parts) {
				if (part.getName().equals("memImg")) {
					memImg = getPictureByteArrayNoChangeSize(part.getInputStream());
				}
				if (part.getName().equals("memImg") && !part.getContentType().startsWith("image")) {
					errorMsgs.add("照片上傳格式有誤");
				}
				// if (getFileNameFromPart(part) != null &&
				// part.getName().equals("memImg")
				// && !(part.getContentType().startsWith("image"))) {
				// errorMsgs.add("會員照片格式有誤");
				// }
			}

			if (memImg != null) {
				if (memImg.length == 0) {
					errorMsgs.add("請上傳照片");
				}
			}
			if (memImg == null) {
				errorMsgs.add("請上傳照片");
			}

			fMem.setMemImg(memImg);

			/****************** 有寵物會執行下方 *****************/
			Pet fPet = new Pet();
			String petName = null;
			String petKind = null;
			Integer petGender = null;
			byte[] petImg = null;

			if (((String) req.getParameter("petOrNot")).equals("1")) {
				// 寵物姓名
				petName = req.getParameter("petName").trim();
				if (petName == null || petName.isEmpty()) {
					errorMsgs.add("請輸入寵物姓名");
				}
				if (petName.trim().length() > 30) {
					errorMsgs.add("寵物姓名長度請勿大於30");
				}
				fPet.setPetName(petName);

				// 寵物種類
				petKind = req.getParameter("petKind").trim();
				if (petKind == null || petKind.isEmpty()) {
					errorMsgs.add("請輸入寵物類別");
				}
				if (petKind.trim().length() > 30) {
					errorMsgs.add("寵物類別長度請勿大於30");
				}
				fPet.setPetKind(petKind);

				petGender = null;
				try {
					petGender = Integer.parseInt(req.getParameter("petGender").trim());
				} catch (Exception e) {
					errorMsgs.add("請選擇寵物性別");
				}
				fPet.setPetGender(petGender);

				for (Part part : parts) {
					if (part.getName().equals("petImg") && getFileNameFromPart(part) != null
							&& part.getContentType() != null) {
						petImg = getPictureByteArray(part.getInputStream());
					}
					if (getFileNameFromPart(part) != null && part.getName().equals("petImg")
							&& !(part.getContentType().startsWith("image"))) {
						errorMsgs.add("寵物照片格式有誤");
					}
				}

				if (petImg != null) {
					if (petImg.length == 0) {
						errorMsgs.add("請上傳寵物照片");
					}
				}
				if (petImg == null) {
					errorMsgs.add("請上傳寵物照片");
				}
				fPet.setPetImg(petImg);

			}

			if (!errorMsgs.isEmpty() || !verify) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/register.jsp");
				String fCounty = county;
				String fDistrict = district;
				req.setAttribute("errorMsgs", errorMsgs);
				req.setAttribute("fMem", fMem);
				req.setAttribute("fPet", fPet);
				req.setAttribute("fCounty", fCounty);
				req.setAttribute("fDistrict", fDistrict);
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/

			// 判斷是否有養寵物
			if (((String) req.getParameter("petOrNot")).equals("0")) {
				memSvc.addMember(memId, memPwd, memName, memSname, memGender, memIdNo, memBday, memPhone, totalAddress,
						memEmail, memImg, 0, 0, 0, "新增點自我介紹吧", 0, 5000, 0, 0.00, 0.00,
						new Timestamp((new java.util.Date()).getTime()), 0);
			} else {

				memSvc.addMemberWithPet(memId, memPwd, memName, memSname, memGender, memIdNo, memBday, memPhone,
						totalAddress, memEmail, memImg, 0, 0, 0, "新增點自我介紹吧", 0, 5000, 0, 0.00, 0.00,
						new Timestamp((new java.util.Date()).getTime()), 0, petName, petKind, petGender, "新增寵物的品種吧",
						"新增點寵物的介紹吧", new Date(2010 - 05 - 02), petImg, 0);
			}

			/***************************
			 * 3.修改完成,準備轉交(Send the Success view)
			 *************/
			Member memberR = memSvc.getOneMemberById(memId);
			session.setAttribute("member", memberR);
			res.sendRedirect(req.getContextPath() + "/front_end/index.jsp");

		}

		if ("login".equals(action)) {

			// 判斷是否為空值
			String memId = req.getParameter("memId");
			String memPwd = req.getParameter("memPwd");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			if (allowUser(memId, memPwd) == null) {
				Member errMember = new Member();
				errMember.setMemId(memId);
				errMember.setMemPwd(memPwd);

				errorMsgs.add("帳號密碼錯誤");
				req.setAttribute("member", errMember);
				RequestDispatcher sendBackView = req.getRequestDispatcher("/front_end/member/login.jsp");
				sendBackView.forward(req, res);
			} else {
				Member memberl = memSvc.getOneMemberById(memId);
				session.setAttribute("member", memberl);
				String location = (String) session.getAttribute("location");
				String specialLocation = (String) session.getAttribute("specialLocation");
				// 如果不是特殊Login 用重導

				if (location != null) {
					session.removeAttribute("location");
					res.sendRedirect(location);
					return;
				}
				res.sendRedirect(req.getContextPath() + "/front_end/index.jsp");

			}

		}

		// 會員登出
		if ("logout".equals(action)) {
			session.invalidate();
			res.sendRedirect(req.getContextPath() + "/front_end/index.jsp");

		}

		// 會員查詢
		if ("search".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			String search = req.getParameter("search");
			if (search == null || search.trim().isEmpty()) {
				errorMsgs.add("請填寫搜尋內容");
			}
			if (search.trim().length() > 30) {
				errorMsgs.add("搜尋內容過長");
			}

			String type = req.getParameter("type");

			String loc = req.getParameter("loc");

			if (!errorMsgs.isEmpty()) {
				// RequestDispatcher failureView =
				// req.getRequestDispatcher("/front_end/index.jsp");
				// req.setAttribute("errorMsgs", errorMsgs);
				res.sendRedirect(req.getContextPath() + "/front_end/index.jsp");
				// failureView.forward(req, res);
				return;// 程式中斷
			}

			/***************************
			 * 2.修改完成,準備轉交(Send the Success view)
			 *************/
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/searchResult.jsp");

			req.setAttribute("search", search);

			failureView.forward(req, res);

		}

		// 會員資訊流覽
		if ("viewOtherMem".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			Integer memNo = null;
			try {
				memNo = Integer.parseInt(req.getParameter("memNo"));
			} catch (Exception e) {
				errorMsgs.add("系統問題，暫時無法預覽");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/searchResult");
				req.setAttribute("errorMsgs", errorMsgs);
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/***************************
			 * 2.修改完成,準備轉交(Send the Success view)
			 *************/
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/viewMemInfo.jsp");

			req.setAttribute("memNo", memNo);

			failureView.forward(req, res);

		}

		// 忘記密碼
		if ("forgetPwd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			String memEmail = req.getParameter("memEmail");
			if (memEmail == null || memEmail.trim().isEmpty()) {
				errorMsgs.add("請填寫Email");
			}

			Member memberf = memSvc.getMemberByEmail(memEmail);
			if (memberf == null) {
				errorMsgs.add("查無此Email");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/forgetPwd.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/

			String memPwd = memberf.getMemPwd();
			;

			String subject = "Pet You&Me 忘記密碼通知";

			java.util.Date current = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String c = sdf.format(current);
			String messageText = "嗨! " + memberf.getMemName() + "您在" + c + "曾發送忘記帳號密碼請求。 " + "\n" + "您的帳號為 : "
					+ memberf.getMemId() + "， 您的密碼為 : " + memPwd + "\n" + "如此請求並非由您發出，請至客服反映";

			MailService mailService = new MailService();
			mailService.sendMail(memEmail, subject, messageText);

			/***************************
			 * 2.修改完成,準備轉交(Send the Success view)
			 *************/
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/forgetPwd.jsp");

			req.setAttribute("success", "Email已寄出，請至信箱確認");

			failureView.forward(req, res);

		}

	}

	public static byte[] getPictureByteArray(InputStream fis) throws IOException {

		BufferedImage originalImage = ImageIO.read(fis);
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizeImageJpg = resizeImage(originalImage, type);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizeImageJpg, "jpg", baos);
		baos.flush();
		baos.close();
		return baos.toByteArray();
	}

	public static byte[] getPictureByteArrayNoChangeSize(InputStream fis) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

	protected Member allowUser(String memId, String memPwd) {
		MemberService memSvc = new MemberService();
		Member member = memSvc.getOneMemberById(memId);

		if (member == null) {
			return null;
		} else if (!member.getMemPwd().equals(memPwd)) {
			return null;
		} else {
			return member;
		}
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(400, 300, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 400, 300, null);
		g.dispose();

		return resizedImage;
	}

	public boolean isValidIDorRCNumber(String str) {

		if (str == null || "".equals(str)) {
			return false;
		}

		final char[] pidCharArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
				'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		// 原身分證英文字應轉換為10~33，這裡直接作個位數*9+10
		final int[] pidIDInt = { 1, 10, 19, 28, 37, 46, 55, 64, 39, 73, 82, 2, 11, 20, 48, 29, 38, 47, 56, 65, 74, 83,
				21, 3, 12, 30 };

		// 原居留證第一碼英文字應轉換為10~33，十位數*1，個位數*9，這裡直接作[(十位數*1) mod 10] + [(個位數*9) mod
		// 10]
		final int[] pidResidentFirstInt = { 1, 10, 9, 8, 7, 6, 5, 4, 9, 3, 2, 2, 11, 10, 8, 9, 8, 7, 6, 5, 4, 3, 11, 3,
				12, 10 };

		// 原居留證第二碼英文字應轉換為10~33，並僅取個位數*6，這裡直接取[(個位數*6) mod 10]
		final int[] pidResidentSecondInt = { 0, 8, 6, 4, 2, 0, 8, 6, 2, 4, 2, 0, 8, 6, 0, 4, 2, 0, 8, 6, 4, 2, 6, 0, 8,
				4 };

		str = str.toUpperCase();// 轉換大寫
		final char[] strArr = str.toCharArray();// 字串轉成char陣列
		int verifyNum = 0;

		/* 檢查身分證字號 */
		if (str.matches("[A-Z]{1}[1-2]{1}[0-9]{8}")) {
			// 第一碼
			verifyNum = verifyNum + pidIDInt[Arrays.binarySearch(pidCharArray, strArr[0])];
			// 第二~九碼
			for (int i = 1, j = 8; i < 9; i++, j--) {
				verifyNum += Character.digit(strArr[i], 10) * j;
			}
			// 檢查碼
			verifyNum = (10 - (verifyNum % 10)) % 10;

			return verifyNum == Character.digit(strArr[9], 10);
		}

		/* 檢查統一證(居留證)編號 */
		verifyNum = 0;
		if (str.matches("[A-Z]{1}[A-D]{1}[0-9]{8}")) {
			// 第一碼
			verifyNum += pidResidentFirstInt[Arrays.binarySearch(pidCharArray, strArr[0])];
			// 第二碼
			verifyNum += pidResidentSecondInt[Arrays.binarySearch(pidCharArray, strArr[1])];
			// 第三~八碼
			for (int i = 2, j = 7; i < 9; i++, j--) {
				verifyNum += Character.digit(strArr[i], 10) * j;
			}
			// 檢查碼
			verifyNum = (10 - (verifyNum % 10)) % 10;

			return verifyNum == Character.digit(strArr[9], 10);
		}

		return false;
	}

}
