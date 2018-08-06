package com.album.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.album.model.Album;
import com.album.model.AlbumService;
import com.albumimg.model.AlbumImg;
import com.albumimg.model.AlbumImgService;
import com.member.model.Member;
import com.member.model.MemberService;

@WebServlet("/front_end/album/Album.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024
		* 1024)
public class AlbumC extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		MemberService memSvc = new MemberService();
		AlbumService albumSvc = new AlbumService();
		AlbumImgService aImgSvc=new AlbumImgService();
		String action = req.getParameter("action");

		

		//新增相簿
		if ("createAlbum".equals(action)) {
	
		
			/****************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String albumTitle = req.getParameter("albumTitle");
			if(albumTitle==null||albumTitle.isEmpty()){
				errorMsgs.add("請填寫相簿名稱");
			}
			if(albumTitle!=null && albumTitle.trim().length()>30){
				errorMsgs.add("相簿名稱過長");
			}
			
			
			Collection<Part> parts = req.getParts();
			//傳過來的part至少要多於3  action 和  albumTitle 和至少一張照片
			if(parts==null||parts.size()<3){
				errorMsgs.add("請上傳檔案");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/album/albumShow.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
				return;
			}
			
			
			
			
			/*************************** 2.開始修改資料 *****************************************/
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			List<AlbumImg> aImgs = new LinkedList<AlbumImg>();

			for (Part part : parts) {
				if (getFileNameFromPart(part) != null && part.getContentType() != null) {
					AlbumImg aImg = new AlbumImg();
					aImg.setImgTitle("為此照片新增點描述吧");
					aImg.setImgDesc("為此照片新增點描述吧");
					aImg.setImgCreatedTime(currentTime);
					aImg.setImgModifiedTime(currentTime);
					aImg.setImgFileName(getFileNameFromPart(part));
					aImg.setImgExtName(part.getContentType());
					aImg.setImgFile(getPictureByteArray(part.getInputStream()));
					aImgs.add(aImg);
				}
			}

			albumSvc.addAlbumWithImg(member.getMemNo(), albumTitle, currentTime, currentTime, 0,
					aImgs.get(0).getImgFile(), aImgs);

			
			
			
			/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
			res.sendRedirect(req.getContextPath() + "/front_end/album/albumShow.jsp");

		}
		
		
		
		
		
		
		//使用者取得相簿
		if ("getUserAlbum".equals(action)) {
	
		
			/****************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			

			
			
			Integer albumNo=null;
			try{
				albumNo=Integer.parseInt(req.getParameter("albumNo"));
			}
			catch(Exception e){
				errorMsgs.add(" ");
			}
			
			
			
			//驗正請求是不是該會員所有的相簿 如我不試從導首頁
			Set<Album> albumSet = memSvc.getAlbumsByMemNo(member.getMemNo());
			List<Integer> memsAlbumNoList=new ArrayList<Integer>();
			for(Album album:albumSet){
				memsAlbumNoList.add(album.getAlbumNo());
			}
			
			if(!memsAlbumNoList.contains(albumNo)){
				errorMsgs.add("這不是你的相簿");
			}
			
			

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/album/albumShow.jsp");
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
				return;
			}
			
			
			/**************************** 2.準備轉交(Send the Success view)*************/
			RequestDispatcher dispatcher = req.getRequestDispatcher("/front_end/album/aImgShow.jsp");
			req.setAttribute("albumNo", albumNo);
			dispatcher.forward(req, res);

		}
		
		
		
		
		
		
		
		
		
		
		
		//修改封面
		if ("changeCover".equals(action)) {
			
			/****************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
			Integer albumNo=null;
			try{
				albumNo=Integer.parseInt(req.getParameter("albumNo"));
			}
			catch(Exception e){
				////代寫////
			}
			
			Integer imgNo=null;
			try{
				imgNo=Integer.parseInt(req.getParameter("imgNo"));
			}
			catch(Exception e){
				//////代寫///////
			}
			
			
			
			/*************************** 2.開始修改資料 *****************************************/
	

			AlbumImg aImg=aImgSvc.getOneAlbumImg(imgNo);
			Album album=albumSvc.getOneAlbum(albumNo);
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			albumSvc.updateAlbum(albumNo, album.getMemNo(), album.getAlbumTitle(), album.getAlbumCreatedTime(), currentTime, 0, aImg.getImgFile());
	
			
			
			/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
			RequestDispatcher successView = req.getRequestDispatcher("/front_end/album/albumShow.jsp?albumNo="+albumNo);

			successView.forward(req, res);
			
		}
		
		
		//刪除封面
		if ("deleteImg".equals(action)) {
			
			/****************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
			Integer albumNo=null;
			try{
				albumNo=Integer.parseInt(req.getParameter("albumNo"));
			}
			catch(Exception e){
				////代寫////
			}
			
			Integer imgNo=null;
			try{
				imgNo=Integer.parseInt(req.getParameter("imgNo"));
			}
			catch(Exception e){
				//////代寫///////
			}
			
			System.out.print(imgNo);
			
			/**************************** 2.修改完成,準備轉交(Send the Success view)*************/
	
			aImgSvc.deleteAlbumImg(imgNo);

	
			
			
			/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
			RequestDispatcher successView = req.getRequestDispatcher("/front_end/album/aImgShow.jsp?albumNo="+albumNo);

			successView.forward(req, res);
			
		}
		
		//編輯相片
		if ("updateImg".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
	
			/****************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
			Integer albumNo=null;
			try{
				albumNo=Integer.parseInt(req.getParameter("albumNo"));
			}
			catch(Exception e){
				errorMsgs.put("imgNo", "相簿代號錯誤");
			}
			
			Integer imgNo=null;
			try{
				imgNo=Integer.parseInt(req.getParameter("imgNo"));
			}
			catch(Exception e){
				errorMsgs.put("imgNo", "照片代號錯誤");
			}
			
			//好像也可以讓他為空 沒關係
			String imgTitle=req.getParameter("imgTitle");
			if (imgTitle == null || imgTitle.trim().isEmpty()) {
				errorMsgs.put("imgTitle", imgTitle);
			}
			
			//好像也可以讓他為空 沒關係
			String imgDesc=req.getParameter("imgDesc");
			if (imgDesc == null || imgDesc.trim().isEmpty()) {
				errorMsgs.put("imgDesc", imgDesc);
			}
			

			if(!errorMsgs.isEmpty()){
				RequestDispatcher failView = req.getRequestDispatcher("/front_end/album/aImgShow.jsp?albumNo="+albumNo);
				failView.forward(req, res);
				return;
			}

			
			/**************************** 2.修改完成,準備轉交(Send the Success view)*************/
	

			AlbumImg aImg=aImgSvc.getOneAlbumImg(imgNo);
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			aImgSvc.updateAlbumImg(imgNo, albumNo, imgTitle, imgDesc, aImg.getImgCreatedTime(), currentTime, aImg.getImgFileName(), aImg.getImgExtName(), aImg.getImgFile());



			
			/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
			RequestDispatcher successView = req.getRequestDispatcher("/front_end/album/aImgShow.jsp?albumNo="+albumNo);

			successView.forward(req, res);
			
		}
		
		
		
		
		
		//編輯相簿
		if ("updateAlbum".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			/****************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
			Integer albumNo=null;
			try{
				albumNo=Integer.parseInt(req.getParameter("albumNo"));
			}
			catch(Exception e){
				errorMsgs.put("imgNo", "相簿代號錯誤");
			}
			
			
			//好像也可以讓他為空 沒關係
			String albumTitle=req.getParameter("albumTitle");
			if (albumTitle == null || albumTitle.trim().isEmpty()) {
				errorMsgs.put("imgTitle", albumTitle);
			}
			


			if(!errorMsgs.isEmpty()){
				RequestDispatcher successView = req.getRequestDispatcher("/front_end/album/albumShow.jsp");
				successView.forward(req, res);
				return;
			}

			
			/**************************** 2.修改完成,準備轉交(Send the Success view)*************/
	

			Album album=albumSvc.getOneAlbum(albumNo);
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			albumSvc.updateAlbum(albumNo, album.getMemNo(), albumTitle, album.getAlbumCreatedTime(),currentTime,0, album.getAlbumImgFile());
			


			
			/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
			RequestDispatcher successView = req.getRequestDispatcher("/front_end/album/albumShow.jsp");

			successView.forward(req, res);
			
		}
		
		
		//刪除相簿
		if ("deleteAlbum".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			/****************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
			Integer albumNo=null;
			try{
				albumNo=Integer.parseInt(req.getParameter("albumNo"));
			}
			catch(Exception e){
				errorMsgs.put("imgNo", "相簿代號錯誤");
			}
			
			


			//我還沒做錯誤處理 map還沒送回去

			
			/**************************** 2.修改完成,準備轉交(Send the Success view)*************/
	

			Album album=albumSvc.getOneAlbum(albumNo);
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			albumSvc.updateAlbum(albumNo, album.getMemNo(), album.getAlbumTitle(), album.getAlbumCreatedTime(),currentTime,1, album.getAlbumImgFile());
			


			
			/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
			RequestDispatcher successView = req.getRequestDispatcher("/front_end/album/albumShow.jsp");

			successView.forward(req, res);
			
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

	
	
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(400, 300, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 400, 300, null);
		g.dispose();

		return resizedImage;
	}

	
	
	public static byte[] getPictureByteArrayNoChangeSize(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
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
	
	
	
	
	
	
	
	
	
	
	// 取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

}
