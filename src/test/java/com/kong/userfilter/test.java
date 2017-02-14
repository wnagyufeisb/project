package com.kong.userfilter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;


public class test {

	public static void main(String[] args) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("{\"ver\":\"2.0\",\"data\":{\"gameId\":\"731592\",\"accountId\":\"8247e3f4b3c7951d24fb688416cd3afb\",\"creator\":\"JY\",\"amount\":\"0.01\",\"orderId\":\"20161226145146001584\",\"failedDesc\":\"\",\"callbackInfo\":\"http://www.baidu.com\",\"payWay\":\"999\",\"orderStatus\":\"S\",\"cpOrderId\":\"201612261451479190GT\"},\"sign\":\"f9002b4318c208fce68178efdb307838\"}");
		JSONObject json = JSONObject.fromObject(stringBuilder.toString());
		getHttpInterenceStream("http://localhost:8080/PaymentKong/ucnewcb", "post", json.toString());
	}
	
	public static String getHttpInterenceStream(String url, String requesType,
			String jsonString) {
		if (!"post".equals(requesType.toLowerCase())
				&& !"get".equals(requesType.toLowerCase())) {
			return "requesType error";
		}
		try {
			URL urlFormat = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlFormat
					.openConnection();
			connection.setDoOutput(true);

			connection.setDoInput(true);
			if ("post".equals(requesType.toLowerCase())) {
				connection.setRequestMethod("POST");
			}
			connection.setRequestProperty("Content-type", "application/json");
			OutputStreamWriter outs = new OutputStreamWriter(
					connection.getOutputStream());
			outs.write(jsonString);
			outs.flush();
			outs.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String res = "", line = null;
			while ((line = in.readLine()) != null) {
				res += line;
			}
			connection.disconnect();
			return res.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "requesType error";

	}


}
