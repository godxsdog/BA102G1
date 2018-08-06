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


import com.album.model.Album;
import com.album.model.AlbumJDBCDAO;
import com.albumimg.model.AlbumImg;
import com.albumimg.model.AlbumImgJDBCDAO;

import com.member.model.Member;

import com.member.model.MemberJDBCDAO;


public class InsertDummyBlob3 {
	static int fixed_width=400;
	static int fixed_height=300;
	
	
	public static void main(String[] args) {
		

		// 會員照片修改
		//讀取會員照片延伸附檔名	
		Set<String> memSet=new HashSet<String>();	
		for (File file : new File("WebContent/DummyImg/member").listFiles()) { 
			String fileName=file.getName();
			String fileExtName=fileName.substring(fileName.lastIndexOf("."));
			memSet.add(fileExtName);
		}	
		
	    //對資料庫的編號跑回圈
		for (int i=5001;i<=5033;i++) { 
	    	MemberJDBCDAO dao=new MemberJDBCDAO();
	    	//取得File檔名 (ex.1 2 3) 故要記得更改減的數字(5000)
	    	String fileN=String.valueOf((i-5000));
	    	
	    	//附檔名不確定，故對可能的副檔名跑回圈跑到檔案存在為止
	    	File file = null;	    	
	    	for(String fileExtName:memSet){
	    		
	    		file=new File("WebContent/DummyImg/member/"+fileN+fileExtName);
	    		
	    		if(file.exists()){
	    			break;
	    		}
	    	}
	    	
	    	Member member=dao.findByPk(i);
	    	System.out.println(i);
	        try {
	        	byte[] b = getPictureByteArrayNoChangeSize(file);
	        	System.out.println(b);
				member.setMemImg(b);
				dao.update(member);
			} catch (IOException e) {
				e.printStackTrace();
			}    
	    }
	    System.out.println("=============會員新增完畢================");

	    
	    
	    
		
	    
	    
	    
		// 修改相簿
		//讀取相簿照片延伸附檔名	
		Set<String> albumSet=new HashSet<String>();	
		for (File file : new File("WebContent/DummyImg/album").listFiles()) { 
			String fileName=file.getName();
			String fileExtName=fileName.substring(fileName.lastIndexOf("."));
			albumSet.add(fileExtName);
		}	
		

	    for (int i=1;i<=1;i++) { 
	    	AlbumJDBCDAO dao=new AlbumJDBCDAO();
	    	String fileN=String.valueOf((i-0));
	    	
	    	File file = null;	    	
	    	for(String fileExtName:albumSet){
	    		file=new File("WebContent/DummyImg/album/"+fileN+fileExtName);
	    		if(file.exists()){
	    			break;
	    		}
	    	}
	    	
	    	Album album=dao.findByPk(i);
	        try {
	        	byte[] b = getPictureByteArrayNoChangeSize(file);
	        	album.setAlbumImgFile(b);
				dao.update(album);
			} catch (IOException e) {
				e.printStackTrace();
			}    
	    }
	    System.out.println("=============相簿新增完畢================");
	    
	    
		// 修改相片
		//讀取寵物照片延伸附檔名	
		Set<String> aImgSet=new HashSet<String>();	
		for (File file : new File("WebContent/DummyImg/albumimg").listFiles()) { 
			String fileName=file.getName();
			String fileExtName=fileName.substring(fileName.lastIndexOf("."));
			aImgSet.add(fileExtName);
		}	
		

	    for (int i=1;i<=5;i++) { 
	    	AlbumImgJDBCDAO dao=new AlbumImgJDBCDAO();
	    	String fileN=String.valueOf((i-0));
	    	
	    	File file = null;	    	
	    	for(String fileExtName:aImgSet){
	    		file=new File("WebContent/DummyImg/albumimg/"+fileN+fileExtName);
	    		if(file.exists()){
	    			break;
	    		}
	    	}
	    	
	    	AlbumImg aImg=dao.findByPk(i);
	        try {
	        	byte[] b = getPictureByteArrayNoChangeSize(file);
	        	aImg.setImgFile(b);
				dao.update(aImg);
			} catch (IOException e) {
				e.printStackTrace();
			}    
	    }
	    System.out.println("=============相片新增完畢================");
	    

		
	     
    
	    
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
