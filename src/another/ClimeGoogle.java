package another;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClimeGoogle {

	public List<Double> climeLat(String sKeyWord) {
		List<Double> lat = new ArrayList<>();

		try {

			URL urlFromGMap = new URL(String.format(
					"http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW",
					URLEncoder.encode(sKeyWord, "UTF-8")));// p=%s is KeyWord in
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

				lat.add(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
				// lng.add(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lat;
	}

	public List<Double> climeLng(String sKeyWord) {
		List<Double> lng = new ArrayList<>();

		try {

			URL urlFromGMap = new URL(String.format(
					"http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW",
					URLEncoder.encode(sKeyWord, "UTF-8")));// p=%s is KeyWord in
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

				lng.add(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
				// lng.add(ja.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lng;
	}
}
