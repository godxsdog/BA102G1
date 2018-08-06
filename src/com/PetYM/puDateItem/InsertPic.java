package com.PetYM.puDateItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/InsertPic")
public class InsertPic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	byte[] a0,a1,a2,a3,a4;
	@Override
	public void destroy() {
	
		super.destroy();
	}
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
//			 a0 = getPictureByteArray("C:\\Android_workStation\\Android_ProjectData\\luludog\\lulu10.jpg");
//			 a1 = getPictureByteArray("C:\\Android_workStation\\Android_ProjectData\\luludog\\lulu9.jpg");
//			 a2 = getPictureByteArray("C:\\Android_workStation\\Android_ProjectData\\luludog\\lulu8.jpeg");
//			 a3 = getPictureByteArray("C:\\Android_workStation\\Android_ProjectData\\luludog\\lulu7.jpg");
//			 a4 = getPictureByteArray("C:\\Android_workStation\\Android_ProjectData\\luludog\\lulu6.jpg");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0;i<11;i++){
			DateItemVO dateItemVO = new DateItemVO();
			dateItemVO.setDateItemNo((4001+i));
			try {
				//第一行有加縮圖
				dateItemVO.setDateItemImg(ImageUtil.shrink(getPictureByteArray("C:\\Android_workStation\\Android_ProjectData\\luludog\\lulu"+(i)+".jpg"), 700));
//				dateItemVO.setDateItemImg(getPictureByteArray("C:\\Android_workStation\\Android_ProjectData\\luludog\\lulu"+(i)+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DateItemDAO dateItemDAO = new DateItemDAO();
			dateItemDAO.updatePicture(dateItemVO);
		}

		
		
		
	}
       
  
    public InsertPic() {
        super();
     
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
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

}
