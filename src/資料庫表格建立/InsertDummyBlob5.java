package 資料庫表格建立;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import com.diary.model.*;



public class InsertDummyBlob5 {
	static int fixed_width=400;
	static int fixed_height=300;
	
	
	public static void main(String[] args) {


		// 日誌圖片修改
		//讀取日誌照片延伸附檔名	
		Set<String> diaSet=new HashSet<String>();	
		for (File file : new File("WebContent/DummyImg/Diary").listFiles()) { 
			String fileName=file.getName();
			String fileExtName=fileName.substring(fileName.lastIndexOf("."));
			diaSet.add(fileExtName);
		}	
		
	    //對資料庫的編號跑回圈
		for (int i=10001;i<=10015;i++) { 
	    	DiaryJDBCDAO dao=new DiaryJDBCDAO();
	    	//取得File檔名 (ex.1 2 3) 故要記得更改減的數字(10000)
	    	String fileN=String.valueOf((i-10000));
	    	
	    	//附檔名不確定，故對可能的副檔名跑回圈跑到檔案存在為止
	    	File file = null;	    	
	    	for(String fileExtName:diaSet){
	    		file=new File("WebContent/DummyImg/Diary/"+fileN+fileExtName);
	    		if(file.exists()){
	    			break;
	    		}
	    	}
	    	
	    	Diary diary=dao.findByPrimaryKey(i);
	        try {
	        	byte[] b = getPictureByteArray(file);
				diary.setDiaImg(b);
				dao.update(diary);
			} catch (IOException e) {
				e.printStackTrace();
			}    
	    }
	    System.out.println("=============日誌新增完畢================");

	    
	    
	    

	     
    
	    
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type,int fixed_width,int fixed_height ){
		BufferedImage resizedImage = new BufferedImage(fixed_width, fixed_height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, fixed_width, fixed_height, null);
		g.dispose();

		return resizedImage;
	    }
	
	
	public static byte[] getPictureByteArray(File file) throws IOException {
		BufferedImage originalImage = ImageIO.read(file);
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizeImageJpg = resizeImage(originalImage, type,fixed_width,fixed_height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( resizeImageJpg, "jpg", baos );
		baos.flush();
		baos.close();

		return baos.toByteArray();
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
	
	
	public static byte[] getPictureByteArrayForMemberAndDateItem(File file) throws IOException {
		BufferedImage originalImage = ImageIO.read(file);
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizeImageJpg = resizeImage(originalImage, type,300,300);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( resizeImageJpg, "jpg", baos );
		baos.flush();
		baos.close();

		return baos.toByteArray();
	}
	
}
