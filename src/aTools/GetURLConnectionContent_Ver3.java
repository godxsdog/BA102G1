package aTools;

import java.net.*;
import java.io.*;
import java.io.*;

class GetURLConnectionContent_Ver3 {
	public static void main(String args[]) {
		
		URL url = null;

		try {
			url = new URL("http://maps.googleapis.com/maps/api/geocode/xml?address="+java.net.URLEncoder.encode("台北市中山區中山北路一段120號","UTF-8")+"&sensor=false&language=zh-TW"); // 建立URL物件url , 以 中文台北市(之地址換算經緯度為例)
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			// 以URL物件建立URLConnection物件
			URLConnection urlConn = url.openConnection();
			// 以URLConnection物件取得輸入資料流
			InputStream ins = urlConn.getInputStream();
			
			// 建立經緯度.xml
			//C:\Android_workStation\PetYM_SQL_Web
			BufferedReader in = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			BufferedWriter out = new BufferedWriter(new FileWriter("經緯度.xml"));
			char[] buf = new char[1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
			ins.close();
			System.out.println("已建立經緯度.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}