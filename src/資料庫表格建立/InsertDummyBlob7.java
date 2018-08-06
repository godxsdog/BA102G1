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


import com.product.model.Product;
import com.product.model.ProductJDBCDAO;



public class InsertDummyBlob7 {
	static int fixed_width=400;
	static int fixed_height=300;
	
	
	public static void main(String[] args) {


		// 修改商品照片
				//讀取商品照片延伸附檔名	
				Set<String> productSet=new HashSet<String>();	
				for (File file : new File("WebContent/DummyImg/product").listFiles()) { 
					String fileName=file.getName();
					String fileExtName=fileName.substring(fileName.lastIndexOf("."));
					productSet.add(fileExtName);
				}	
				

			    for (int i=2001;i<=2016;i++) { 
			    	ProductJDBCDAO dao=new ProductJDBCDAO();
			    	String fileN=String.valueOf((i-2000));
			    	
			    	File file = null;	    	
			    	for(String fileExtName:productSet){
			    		file=new File("WebContent/DummyImg/product/"+fileN+fileExtName);
			    		if(file.exists()){
			    			break;
			    		}
			    	}
			    	
			    	Product product=dao.findByPk(i);
			        try {
			        	byte[] b = getPictureByteArrayForMemberAndDateItem(file);
			        	product.setProdImg(b);
						dao.update(product);
					} catch (IOException e) {
						e.printStackTrace();
					}    
			    }
			    System.out.println("=============商品新增完畢================");

	    
	    
	    

	     
    
	    
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
