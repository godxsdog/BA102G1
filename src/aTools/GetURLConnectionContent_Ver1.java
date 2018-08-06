package aTools;

import java.net.*;
import java.io.*;

class GetURLConnectionContent_Ver1 {
	public static void main(String args[]) {
		
		URL url = null;

		try {
			url = new URL("https://tw.yahoo.com"); // 建立URL物件url , 以 ISBN 找書為例
		} catch (MalformedURLException e) {
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
				if (data.contains("<title>")) {
					System.out.println(data.substring(data.indexOf("<title>") + 7, data.indexOf("</title>")));
				}
			}

			br.close();
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}