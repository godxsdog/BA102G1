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
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				// �ʺ�
				String memSname = req.getParameter("memSname");
				if (memSname == null || memSname.trim().isEmpty()) {
					errorMsgs.add("�ж�g�ʺ�");
				}
				if (memSname.length() > 30) {
					errorMsgs.add("�ʺ٪��׹L��");
				}

				// �m�W
				String memName = req.getParameter("memName");
				if (memName == null || memName.trim().isEmpty()) {
					errorMsgs.add("�ж�g�m�W");
				}
				if (memName.trim().length() > 30) {
					errorMsgs.add("�m�W���׽ФŤj��20");
				}

				// �ͤ�
				java.sql.Date memBday = null;
				try {
					memBday = java.sql.Date.valueOf(req.getParameter("memBday"));
				} catch (IllegalArgumentException e) {
					errorMsgs.add("����榡���~");
				}
				long memBdayInLong = memBday.getTime();
				long nowTimeInLong = System.currentTimeMillis();
				if (nowTimeInLong < memBdayInLong) {
					errorMsgs.add("�ͤ�л~�j�󤵤�");
				}

				// ���
				String memPhone = req.getParameter("memPhone");
				if (!(memPhone.matches("[09]{2}[0-9]{2}-[0-9]{6}") || memPhone.matches("[09]{2}[0-9]{8}"))) {
					errorMsgs.add("����榡���~");
				}

				// �ʧO
				Integer memGender = null;
				try {
					memGender = Integer.parseInt(req.getParameter("memGender").trim());
				} catch (IllegalArgumentException e) {
					memGender = 2;
					errorMsgs.add("�п�J�ʧO");
				}

				// �P�����A
				Integer memRelation = null;
				try {
					memRelation = Integer.parseInt(req.getParameter("memRelation").trim());
				} catch (IllegalArgumentException e) {
					memRelation = 2;
					errorMsgs.add("�п�J�P�����A");
				}

				// Email
				String memEmail = req.getParameter("memEmail");
				CrunchifyValidateEmail crunchifyCheck = new CrunchifyValidateEmail();
				boolean isValidEmail = crunchifyCheck.crunchifyEmailValidator(memEmail);
				if (!isValidEmail) {
					errorMsgs.add("�п�J���T��Email�H�c");
				}
				Member emailMember = memSvc.getMemberByEmail(memEmail);
				if (emailMember != null) {
					if (!emailMember.getMemId().equals(member.getMemId())) {
						errorMsgs.add("���H�c�w�g���H���U�L�A�Ч󴫫H�c���U");
						System.out.println("abc");
					}
				}
				if (memEmail.trim().length() > 100) {
					errorMsgs.add("�H�c���׽ФŤj��100");
				}

				// �a�}
				String county = req.getParameter("county").trim();
				System.out.println("����: " + county);
				String district = req.getParameter("district").trim();
				System.out.println("��: " + district);
				String memAddress = req.getParameter("memAddress").trim();
				if (county == null || county.isEmpty()) {
					errorMsgs.add("�п�ܿ���");
				}
				if (district == null || district.isEmpty()) {
					errorMsgs.add("�п�ܦa�}�ϰ�");
				}
				if (memAddress == null || memAddress.isEmpty()) {
					errorMsgs.add("�ж�g�a�}");
				}
				if (memAddress.trim().length() > 100) {
					errorMsgs.add("�a�}���׽ФŤj��100");
				}
				String totalAddress = county + "�A" + district + "�A" + memAddress;

				// �ۧڤ���
				String memSelfintro = req.getParameter("memSelfintro");
				if (memSelfintro == null || memSelfintro.trim().isEmpty()) {
					errorMsgs.add("�п�J�ۧڤ���");
				}
				if (memSelfintro.trim().length() > 600) {
					errorMsgs.add("�ۧڤ��Ъ��׽ФŤj��600");
				}

				byte[] memImg = member.getMemImg();
				Collection<Part> parts = req.getParts();

				for (Part part : parts) {
					if ("memImg".equals(part.getName())&&part.getSize()!=0) {
						System.out.println("�ɮפj�p:"+part.getSize());
							memImg = getPictureByteArray(part.getInputStream());
						if (!(part.getContentType().startsWith("image"))) {
							errorMsgs.add("�Ӥ��榡���~");
						}
					}
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memberInfo.jsp");
					req.setAttribute("errorMsgs", errorMsgs);
					dispatcher.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�ק��� *****************************************/

				memSvc.updateMember(member.getMemNo(), member.getMemId(), member.getMemPwd(), memName, memSname,
						memGender, member.getMemIdNo(), memBday, memPhone, totalAddress, memEmail, memImg,
						member.getMemReported(), member.getMemStatus(), memRelation, memSelfintro,
						member.getMemFollowed(), member.getMemPoint(), member.getMemSaleRank(),
						member.getMemLongtitude(), member.getMemLatitude(), member.getMemLocTime(),
						member.getMemLocStatus());

				/****************************
				 * 3.�ק粒��,�ǳ����(Send the Success view)
				 *************/
				Integer memNo = member.getMemNo();
				session.removeAttribute("member");
				Member newMember = memSvc.getOneMember(memNo);
				session.setAttribute("member", newMember);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memberInfo.jsp");
				errorMsgs.add("�ק��Ʀ��\");
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memberInfo.jsp");
				errorMsgs.add("�ק��ƥ���");
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
			}
		}

		// �ק�K�X
		if ("pwdChange".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
			 **********************/

			String memPwd = req.getParameter("memPwd");
			if (!memPwd.equals(member.getMemPwd())) {
				errorMsgs.add("�ثe���K�X���~");
			}
			if (memPwd.trim().length() > 30) {
				errorMsgs.add("�ثe���K�X�L��");
			}

			String memNewPwd = req.getParameter("memNewPwd");
			if (!(memNewPwd.matches("^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{4,20}$") && memNewPwd.trim().length() > 30)) {
				errorMsgs.add("�s�K�X�榡����");
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

			/*************************** 2.�}�l�ק��� *****************************************/
			memSvc.updateMember(member.getMemNo(), member.getMemId(), memNewPwd, member.getMemName(),
					member.getMemSname(), member.getMemGender(), member.getMemIdNo(), member.getMemBday(),
					member.getMemPhone(), member.getMemAddress(), member.getMemEmail(), member.getMemImg(),
					member.getMemReported(), member.getMemStatus(), member.getMemRelation(), member.getMemSelfintro(),
					member.getMemFollowed(), member.getMemPoint(), member.getMemSaleRank(), member.getMemLongtitude(),
					member.getMemLatitude(), member.getMemLocTime(), member.getMemLocStatus());

			/****************************
			 * 3.�ק粒��,�ǳ����(Send the Success view)
			 *************/
			Integer memNo = member.getMemNo();
			session.removeAttribute("member");
			Member newMember = memSvc.getOneMember(memNo);
			session.setAttribute("member", newMember);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/member/memPwdChange.jsp");
			req.setAttribute("success", "�K�X�ק令�\");
			dispatcher.forward(req, res);

		}

		// �|�����U
		if ("register".equals(action)) {

			/****************************
			 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
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

			// �b��
			String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{4,20}$";
			if (memId == null || memId.isEmpty()) {
				errorMsgs.add("�ж�g�b��");
			}
			if (memId.trim().length() > 30) {
				errorMsgs.add("�b�����׽ФŤj��20");
			}
			if (!memId.matches(enameReg)) {
				errorMsgs.add("�b�����׽Фj��4�p��20�A�B�����j�p�g�^��r�μƦr");
			}
			Member IDMember = memSvc.getOneMemberById(memId);
			if (IDMember != null) {
				errorMsgs.add("�b���w�s�b");
			}
			fMem.setMemId(memId);

			// �K�X
			if (memPwd == null || memPwd.isEmpty()) {
				errorMsgs.add("�ж�g�K�X");
			}
			if (memPwd.trim().length() > 30) {
				errorMsgs.add("�K�X���׽ФŤj��20");
			}
			if (!memPwd.matches(enameReg)) {
				errorMsgs.add("�K�X���׽Фj��4�p��20�A�B�����j�p�g�^��r�μƦr");
			}
			fMem.setMemPwd(memPwd);

			// �m�W
			if (memName == null || memName.isEmpty()) {
				errorMsgs.add("�ж�g�m�W");
			}
			if (memName.trim().length() > 30) {
				errorMsgs.add("�m�W���׽ФŤj��20");
			}
			fMem.setMemName(memName);

			// �ʺ�
			if (memSname == null || memSname.isEmpty()) {
				errorMsgs.add("�ж�g�ʺ�");
			}
			if (memSname.trim().length() > 30) {
				errorMsgs.add("�ʺ٪��׽ФŤj��20");
			}
			fMem.setMemSname(memSname);

			// �ʧO
			Integer memGender = null;
			try {
				memGender = Integer.parseInt(req.getParameter("memGender").trim());
			} catch (IllegalArgumentException e) {
				memGender = 1;
				errorMsgs.add("�п�J�ʧO");
			}
			fMem.setMemGender(memGender);

			// �����Ҧr��
			boolean isValidID = isValidIDorRCNumber(memIdNo);
			if (memIdNo == null || memIdNo.isEmpty()) {
				errorMsgs.add("�ж�g�����Ҧr��");
			}
			if (!isValidID || memIdNo.trim().length() > 30) {
				errorMsgs.add("���X�檺�����Ҧr��");
			}
			fMem.setMemIdNo(memIdNo);

			// �ͤ�
			java.sql.Date memBday = null;
			try {
				memBday = java.sql.Date.valueOf(req.getParameter("memBday").trim());
			} catch (IllegalArgumentException e) {
				memBday = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("�п�J�ͤ�!");
			}
			fMem.setMemBday(memBday);
			long memBdayInLong = memBday.getTime();
			long nowTimeInLong = System.currentTimeMillis();
			if (nowTimeInLong < memBdayInLong) {
				errorMsgs.add("�ͤ�л~�j�󤵤�");
			}

			// ���
			String phoneRegex1 = "[09]{2}[0-9]{2}-[0-9]{6}";
			String phoneRegex2 = "[09]{2}[0-9]{8}";
			if (memPhone == null || memPhone.isEmpty()) {
				errorMsgs.add("�ж�g���");
			}
			if (!(memPhone.matches(phoneRegex1) || memPhone.matches(phoneRegex2))) {
				errorMsgs.add("����榡���~");
			}
			fMem.setMemPhone(memPhone);

			// �a�}
			if (county == null || county.isEmpty()) {
				errorMsgs.add("�п�ܿ���");
			}
			if (district == null || district.isEmpty()) {
				errorMsgs.add("�п�ܦa�}�ϰ�");
			}
			if (memAddress == null || memAddress.isEmpty()) {
				errorMsgs.add("�ж�g�a�}");
			}
			if (memAddress.trim().length() > 100) {
				errorMsgs.add("�a�}���׽ФŤj��100");
			}
			fMem.setMemAddress(memAddress);
			String totalAddress = county + "�A" + district + "�A" + memAddress;

			// �H�c
			CrunchifyValidateEmail crunchifyCheck = new CrunchifyValidateEmail();
			boolean isValidEmail = crunchifyCheck.crunchifyEmailValidator(memEmail);
			if (memEmail == null || memEmail.isEmpty() || !isValidEmail) {
				errorMsgs.add("�ж�g�H�c");
			}
			Member emailMember = memSvc.getMemberByEmail(memEmail);
			if (emailMember != null) {
				errorMsgs.add("���H�c�w�g���H���U�L�A�Ч󴫫H�c���U");
			}
			if (memEmail.trim().length() > 100) {
				errorMsgs.add("�H�c���׽ФŤj��100");
			}
			fMem.setMemEmail(memEmail);

			byte[] memImg = null;
			Collection<Part> parts = req.getParts();
			for (Part part : parts) {
				if (part.getName().equals("memImg")) {
					memImg = getPictureByteArrayNoChangeSize(part.getInputStream());
				}
				if (part.getName().equals("memImg") && !part.getContentType().startsWith("image")) {
					errorMsgs.add("�Ӥ��W�Ǯ榡���~");
				}
				// if (getFileNameFromPart(part) != null &&
				// part.getName().equals("memImg")
				// && !(part.getContentType().startsWith("image"))) {
				// errorMsgs.add("�|���Ӥ��榡���~");
				// }
			}

			if (memImg != null) {
				if (memImg.length == 0) {
					errorMsgs.add("�ФW�ǷӤ�");
				}
			}
			if (memImg == null) {
				errorMsgs.add("�ФW�ǷӤ�");
			}

			fMem.setMemImg(memImg);

			/****************** ���d���|����U�� *****************/
			Pet fPet = new Pet();
			String petName = null;
			String petKind = null;
			Integer petGender = null;
			byte[] petImg = null;

			if (((String) req.getParameter("petOrNot")).equals("1")) {
				// �d���m�W
				petName = req.getParameter("petName").trim();
				if (petName == null || petName.isEmpty()) {
					errorMsgs.add("�п�J�d���m�W");
				}
				if (petName.trim().length() > 30) {
					errorMsgs.add("�d���m�W���׽ФŤj��30");
				}
				fPet.setPetName(petName);

				// �d������
				petKind = req.getParameter("petKind").trim();
				if (petKind == null || petKind.isEmpty()) {
					errorMsgs.add("�п�J�d�����O");
				}
				if (petKind.trim().length() > 30) {
					errorMsgs.add("�d�����O���׽ФŤj��30");
				}
				fPet.setPetKind(petKind);

				petGender = null;
				try {
					petGender = Integer.parseInt(req.getParameter("petGender").trim());
				} catch (Exception e) {
					errorMsgs.add("�п���d���ʧO");
				}
				fPet.setPetGender(petGender);

				for (Part part : parts) {
					if (part.getName().equals("petImg") && getFileNameFromPart(part) != null
							&& part.getContentType() != null) {
						petImg = getPictureByteArray(part.getInputStream());
					}
					if (getFileNameFromPart(part) != null && part.getName().equals("petImg")
							&& !(part.getContentType().startsWith("image"))) {
						errorMsgs.add("�d���Ӥ��榡���~");
					}
				}

				if (petImg != null) {
					if (petImg.length == 0) {
						errorMsgs.add("�ФW���d���Ӥ�");
					}
				}
				if (petImg == null) {
					errorMsgs.add("�ФW���d���Ӥ�");
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
				return;// �{�����_
			}

			/*************************** 2.�}�l�ק��� *****************************************/

			// �P�_�O�_���i�d��
			if (((String) req.getParameter("petOrNot")).equals("0")) {
				memSvc.addMember(memId, memPwd, memName, memSname, memGender, memIdNo, memBday, memPhone, totalAddress,
						memEmail, memImg, 0, 0, 0, "�s�W�I�ۧڤ��Чa", 0, 5000, 0, 0.00, 0.00,
						new Timestamp((new java.util.Date()).getTime()), 0);
			} else {

				memSvc.addMemberWithPet(memId, memPwd, memName, memSname, memGender, memIdNo, memBday, memPhone,
						totalAddress, memEmail, memImg, 0, 0, 0, "�s�W�I�ۧڤ��Чa", 0, 5000, 0, 0.00, 0.00,
						new Timestamp((new java.util.Date()).getTime()), 0, petName, petKind, petGender, "�s�W�d�����~�اa",
						"�s�W�I�d�������Чa", new Date(2010 - 05 - 02), petImg, 0);
			}

			/***************************
			 * 3.�ק粒��,�ǳ����(Send the Success view)
			 *************/
			Member memberR = memSvc.getOneMemberById(memId);
			session.setAttribute("member", memberR);
			res.sendRedirect(req.getContextPath() + "/front_end/index.jsp");

		}

		if ("login".equals(action)) {

			// �P�_�O�_���ŭ�
			String memId = req.getParameter("memId");
			String memPwd = req.getParameter("memPwd");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			if (allowUser(memId, memPwd) == null) {
				Member errMember = new Member();
				errMember.setMemId(memId);
				errMember.setMemPwd(memPwd);

				errorMsgs.add("�b���K�X���~");
				req.setAttribute("member", errMember);
				RequestDispatcher sendBackView = req.getRequestDispatcher("/front_end/member/login.jsp");
				sendBackView.forward(req, res);
			} else {
				Member memberl = memSvc.getOneMemberById(memId);
				session.setAttribute("member", memberl);
				String location = (String) session.getAttribute("location");
				String specialLocation = (String) session.getAttribute("specialLocation");
				// �p�G���O�S��Login �έ���

				if (location != null) {
					session.removeAttribute("location");
					res.sendRedirect(location);
					return;
				}
				res.sendRedirect(req.getContextPath() + "/front_end/index.jsp");

			}

		}

		// �|���n�X
		if ("logout".equals(action)) {
			session.invalidate();
			res.sendRedirect(req.getContextPath() + "/front_end/index.jsp");

		}

		// �|���d��
		if ("search".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
			 **********************/
			String search = req.getParameter("search");
			if (search == null || search.trim().isEmpty()) {
				errorMsgs.add("�ж�g�j�M���e");
			}
			if (search.trim().length() > 30) {
				errorMsgs.add("�j�M���e�L��");
			}

			String type = req.getParameter("type");

			String loc = req.getParameter("loc");

			if (!errorMsgs.isEmpty()) {
				// RequestDispatcher failureView =
				// req.getRequestDispatcher("/front_end/index.jsp");
				// req.setAttribute("errorMsgs", errorMsgs);
				res.sendRedirect(req.getContextPath() + "/front_end/index.jsp");
				// failureView.forward(req, res);
				return;// �{�����_
			}

			/***************************
			 * 2.�ק粒��,�ǳ����(Send the Success view)
			 *************/
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/searchResult.jsp");

			req.setAttribute("search", search);

			failureView.forward(req, res);

		}

		// �|����T�y��
		if ("viewOtherMem".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
			 **********************/
			Integer memNo = null;
			try {
				memNo = Integer.parseInt(req.getParameter("memNo"));
			} catch (Exception e) {
				errorMsgs.add("�t�ΰ��D�A�ȮɵL�k�w��");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/searchResult");
				req.setAttribute("errorMsgs", errorMsgs);
				failureView.forward(req, res);
				return;// �{�����_
			}

			/***************************
			 * 2.�ק粒��,�ǳ����(Send the Success view)
			 *************/
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/viewMemInfo.jsp");

			req.setAttribute("memNo", memNo);

			failureView.forward(req, res);

		}

		// �ѰO�K�X
		if ("forgetPwd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
			 **********************/
			String memEmail = req.getParameter("memEmail");
			if (memEmail == null || memEmail.trim().isEmpty()) {
				errorMsgs.add("�ж�gEmail");
			}

			Member memberf = memSvc.getMemberByEmail(memEmail);
			if (memberf == null) {
				errorMsgs.add("�d�L��Email");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/forgetPwd.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				failureView.forward(req, res);
				return;// �{�����_
			}

			/*************************** 2.�}�l�ק��� *****************************************/

			String memPwd = memberf.getMemPwd();
			;

			String subject = "Pet You&Me �ѰO�K�X�q��";

			java.util.Date current = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String c = sdf.format(current);
			String messageText = "��! " + memberf.getMemName() + "�z�b" + c + "���o�e�ѰO�b���K�X�ШD�C " + "\n" + "�z���b���� : "
					+ memberf.getMemId() + "�A �z���K�X�� : " + memPwd + "\n" + "�p���ШD�ëD�ѱz�o�X�A�ЦܫȪA�ϬM";

			MailService mailService = new MailService();
			mailService.sendMail(memEmail, subject, messageText);

			/***************************
			 * 2.�ק粒��,�ǳ����(Send the Success view)
			 *************/
			RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/forgetPwd.jsp");

			req.setAttribute("success", "Email�w�H�X�A�ЦܫH�c�T�{");

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

		// �쨭���ҭ^��r���ഫ��10~33�A�o�̪����@�Ӧ��*9+10
		final int[] pidIDInt = { 1, 10, 19, 28, 37, 46, 55, 64, 39, 73, 82, 2, 11, 20, 48, 29, 38, 47, 56, 65, 74, 83,
				21, 3, 12, 30 };

		// ��~�d�ҲĤ@�X�^��r���ഫ��10~33�A�Q���*1�A�Ӧ��*9�A�o�̪����@[(�Q���*1) mod 10] + [(�Ӧ��*9) mod
		// 10]
		final int[] pidResidentFirstInt = { 1, 10, 9, 8, 7, 6, 5, 4, 9, 3, 2, 2, 11, 10, 8, 9, 8, 7, 6, 5, 4, 3, 11, 3,
				12, 10 };

		// ��~�d�ҲĤG�X�^��r���ഫ��10~33�A�öȨ��Ӧ��*6�A�o�̪�����[(�Ӧ��*6) mod 10]
		final int[] pidResidentSecondInt = { 0, 8, 6, 4, 2, 0, 8, 6, 2, 4, 2, 0, 8, 6, 0, 4, 2, 0, 8, 6, 4, 2, 6, 0, 8,
				4 };

		str = str.toUpperCase();// �ഫ�j�g
		final char[] strArr = str.toCharArray();// �r���নchar�}�C
		int verifyNum = 0;

		/* �ˬd�����Ҧr�� */
		if (str.matches("[A-Z]{1}[1-2]{1}[0-9]{8}")) {
			// �Ĥ@�X
			verifyNum = verifyNum + pidIDInt[Arrays.binarySearch(pidCharArray, strArr[0])];
			// �ĤG~�E�X
			for (int i = 1, j = 8; i < 9; i++, j--) {
				verifyNum += Character.digit(strArr[i], 10) * j;
			}
			// �ˬd�X
			verifyNum = (10 - (verifyNum % 10)) % 10;

			return verifyNum == Character.digit(strArr[9], 10);
		}

		/* �ˬd�Τ@��(�~�d��)�s�� */
		verifyNum = 0;
		if (str.matches("[A-Z]{1}[A-D]{1}[0-9]{8}")) {
			// �Ĥ@�X
			verifyNum += pidResidentFirstInt[Arrays.binarySearch(pidCharArray, strArr[0])];
			// �ĤG�X
			verifyNum += pidResidentSecondInt[Arrays.binarySearch(pidCharArray, strArr[1])];
			// �ĤT~�K�X
			for (int i = 2, j = 7; i < 9; i++, j--) {
				verifyNum += Character.digit(strArr[i], 10) * j;
			}
			// �ˬd�X
			verifyNum = (10 - (verifyNum % 10)) % 10;

			return verifyNum == Character.digit(strArr[9], 10);
		}

		return false;
	}

}
