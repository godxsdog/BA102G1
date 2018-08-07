package another;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TTest {

	public static void main(String[] args) throws IOException, JSONException {

		String sKeyWord = "700台灣台南市中西區建業街70號"; // 這是地址
		URL urlFromGMap = new URL(
				String.format("http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW",
						URLEncoder.encode(sKeyWord, "UTF-8")));// p=%s is
																// KeyWord in
		URLConnection connFromGMap = urlFromGMap.openConnection();
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader readerFromGMap = new BufferedReader(
				new InputStreamReader(connFromGMap.getInputStream(), "utf-8"));
		while ((line = readerFromGMap.readLine()) != null) {
			builder.append(line);
		}
		JSONObject json = new JSONObject(builder.toString()); // 轉換json格式
		JSONArray ja = json.getJSONArray("results");// 取得json的Array物件
		for (int i = 0; i < ja.length(); i++) {
			System.out.println("回傳地址:::" + ja.getJSONObject(i).getString("formatted_address"));
			System.out.println(
					(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat")));
			System.out.println(
					(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng")));

			JSONObject jsonObject = (JSONObject) ja.getJSONObject(0).getJSONArray("address_components").get(3);

			System.out.println(jsonObject.get("short_name"));
		}
	}

}
