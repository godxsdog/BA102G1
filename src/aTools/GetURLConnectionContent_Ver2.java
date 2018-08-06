package aTools;

import java.net.*;
import java.io.*;
import java.io.*;

class GetURLConnectionContent_Ver2 {
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

			// 建立URL資料流
			BufferedReader br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String data;
			while ((data = br.readLine()) != null) {
				//System.out.println(data);
				if (data.contains("<lat>")) {
					System.out.println(data.substring(data.indexOf("<lat>") + 5, data.indexOf("</lat>")));
				}
			}

			br.close();
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}