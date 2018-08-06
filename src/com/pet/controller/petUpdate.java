package com.pet.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.model.Member;
import com.member.model.MemberService;
import com.pet.model.Pet;
import com.pet.model.PetService;

@WebServlet("/front_end/pet/pet.do")
@MultipartConfig
public class petUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		PetService petSvc = new PetService();
		System.out.println(action);

		if ("petUpdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			try {
				/*****************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer petNo = null;
				try {
					petNo = Integer.parseInt(req.getParameter("petNo").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("系統錯誤");
				}

				Pet pet = petSvc.getOnePet(petNo);

				// 寵物姓名
				String petName = req.getParameter("petName");
				if (petName == null || petName.trim().isEmpty()) {
					errorMsgs.add("請填寫寵物姓名");
				}
				if (petName.trim().length() > 30) {
					errorMsgs.add("寵物姓名長度請勿大於20");
				}

				// 寵物品種
				String petSpecies = req.getParameter("petSpecies");
				if (petSpecies == null || petSpecies.trim().isEmpty()) {
					errorMsgs.add("請填寫寵物品種");
				}
				if (petSpecies.trim().length() > 30) {
					errorMsgs.add("寵物品種長度請勿大於20");
				}

				// 寵物生日
				java.sql.Date petBday = null;
				try {
					petBday = java.sql.Date.valueOf(req.getParameter("petBday"));
				} catch (IllegalArgumentException e) {
					errorMsgs.add("寵物生日格式錯誤");
				}
				long petBdayInLong = petBday.getTime();
				long nowTimeInLong = System.currentTimeMillis();
				if (nowTimeInLong < petBdayInLong) {
					errorMsgs.add("寵物生日請誤大於今天");
				}

				// 寵物介紹
				String petIntro = req.getParameter("petIntro");
				if (petIntro == null || petIntro.trim().isEmpty()) {
					errorMsgs.add("請輸入寵物介紹");
				}
				if (petIntro.trim().length() > 300) {
					errorMsgs.add("寵物介紹請勿大於300字");
				}

				byte[] petImg = pet.getPetImg();
				Collection<Part> parts = req.getParts();

				// 寵物照片
				for (Part part : parts) {
					if ("petImg".equals(part.getName())&&part.getSize()!=0&&getFileNameFromPart(part)!=null) {
						System.out.println("檔案名稱:"+getFileNameFromPart(part));
						petImg = getPictureByteArray(part.getInputStream());
						if (!(part.getContentType().startsWith("image"))) {
							errorMsgs.add("照片格式有誤");
						}
					}
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/pet/petInfo.jsp");
					req.setAttribute("errorMsgs", errorMsgs);
					dispatcher.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/

				petSvc.updatePet(petNo, pet.getMemNo(), petName, pet.getPetKind(), pet.getPetGender(), petSpecies,
						petIntro, petBday, petImg, pet.getPetStatus());

				/**************************** * 3.修改完成,準備轉交(Send the Success view)*************/
				// 在取一次 不在上面petSvc取是因為上面的Pet 會少PETNO
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/pet/petInfo.jsp");
				errorMsgs.add("寵物資訊修改成功");
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/pet/petInfo.jsp");
				errorMsgs.add("寵物資訊修改失敗");
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
			}
		}

		if ("petDisable".equals(action)) {
			System.out.println("abc");
			/*****************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			Integer petNo = null;
			try {
				petNo = Integer.parseInt(req.getParameter("petNo").trim());
			} catch (IllegalArgumentException e) {
				System.out.println("excep");
			}
			Pet pet = petSvc.getOnePet(petNo);
			/*************************** 2.開始修改資料 *****************************************/
			petSvc.updatePet(petNo, pet.getMemNo(), pet.getPetName(), pet.getPetKind(), pet.getPetGender(),
					pet.getPetSpecies(), pet.getPetIntro(), pet.getPetBday(), pet.getPetImg(), 1);

			/****************************
			 * 3.修改完成,準備轉交(Send the Success view)
			 *************/
			res.sendRedirect(req.getContextPath() + "/front_end/pet/petInfo.jsp");

		}

		if ("petRegister".equals(action)) {

			Member member = (Member) session.getAttribute("member");
			Integer memNo = null;
			if (member != null) {
				memNo = member.getMemNo();
			}
			List<String> errorMsgs = new LinkedList<String>();

			/*****************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/

			// 檢查是否超過三隻寵物
			MemberService memSvc = new MemberService();
			List<Pet> petList = memSvc.getPetsByMemNo(memNo);
			if (petList.size() > 3) {
				errorMsgs.add("寵物不能超過三隻");
			}

			// 寵物姓名
			String petName = req.getParameter("petName");
			if (petName == null || petName.trim().isEmpty()) {
				errorMsgs.add("請填寫寵物姓名");
			}
			if (petName.trim().length() > 30) {
				errorMsgs.add("寵物姓名長度請勿大於20");
			}

			// 寵物種類
			String petKind = null;
			try {
				petKind = req.getParameter("petKind").trim();
			} catch (IllegalArgumentException e) {
				petKind = "狗";
				errorMsgs.add("請輸入寵物種類");
			}

			Integer petGender = null;
			try {
				petGender = Integer.parseInt(req.getParameter("petGender").trim());
			} catch (IllegalArgumentException e) {
				petGender = 0;
				errorMsgs.add("請輸入寵物性別");
			}

			Pet fPet = new Pet();
			fPet.setMemNo(memNo);
			fPet.setPetName(petName);
			fPet.setPetGender(petGender);
			fPet.setPetKind(petKind);

			byte[] petImg = null;
			Collection<Part> parts = req.getParts();

			for (Part part : parts) {
				if (part.getName().equals("petImg") && getFileNameFromPart(part) != null
						&& part.getContentType().startsWith("image")) {
					petImg = getPictureByteArray(part.getInputStream());
					fPet.setPetImg(petImg);
				}
				if (getFileNameFromPart(part) != null && part.getName().equals("petImg")
						&& !(part.getContentType().startsWith("image"))) {
					errorMsgs.add("照片格式有誤");
				}
			}

			if (petImg == null) {
				errorMsgs.add("請上傳寵物圖片");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/pet/petRegister.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				req.setAttribute("pet", fPet);
				dispatcher.forward(req, res);
				return;
			}

			/*************************** 2.開始修改資料 *****************************************/
			petSvc.addPet(memNo, petName, petKind, petGender, "請輸入寵物種類", "請輸入寵物介紹", java.sql.Date.valueOf("2000-01-01"),
					petImg, 0);

			/****************************
			 * 3.修改完成,準備轉交(Send the Success view)
			 *************/
			res.sendRedirect(req.getContextPath() + "/front_end/pet/petInfo.jsp");

		}

	}

	public static byte[] getPictureByteArray(InputStream fis) throws IOException {
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

}
