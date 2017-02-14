package com.kong.center.service.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.Cookie;

public class OSUtil {

	public static String formatString(String old, int num, String value,
			boolean tag) {

		String str = "";

		if (old.length() >= num) {
			return old;
		} else {
			if (tag) {
				for (int i = 1; i <= num - old.length(); i++) {
					str = str + value;
				}
				str = str + old;
			} else {
				str = old;
				for (int i = 1; i <= num - old.length(); i++) {
					str = str + value;
				}
			}

			return str;
		}
	}

	public static String cutString(String str, int len) {

		String tmp = null;

		try {
			int count = 0;
			byte[] b = str.getBytes("GBK");

			if (b.length <= len) {
				tmp = str;
			} else {
				for (int i = 0; i < len; i++) {
					if (b[i] < 0) {
						count++;
					}
				}

				if (count % 2 == 0) {
					tmp = new String(b, 0, len, "GBK");
				} else {
					tmp = new String(b, 0, len - 1, "GBK");
				}
			}
		} catch (UnsupportedEncodingException e) {
		}

		return tmp;
	}
	public static String encodeURL(String destURL) {

		try {
			destURL = URLEncoder.encode(destURL, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}

		return destURL;
	}

	public static String decodeURL(String destURL) {

		try {
			destURL = URLDecoder.decode(destURL, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}

		return destURL;
	}

	public static boolean makeDirs(String realPath) {

		File dir = new File(realPath);
		boolean flag = false;

		try {
			if (!dir.exists()) {
				flag = dir.mkdirs();
			}
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	public static boolean delFile(String realPath) {

		File target = new File(realPath);
		boolean flag = false;

		try {
			if (target.exists()) {
				flag = target.delete();
			}
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	public static boolean delDir(String realPath) {

		boolean flag = false;
		File dir = new File(realPath);

		if (dir.isDirectory()) {
			flag = delDir(dir);
		}

		return flag;
	}

	public static boolean delDir(File dir) {

		boolean flag = false;

		try {
			File[] files = dir.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					delDir(files[i]);
				} else {
					files[i].delete();
				}
			}

			flag = dir.delete();
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	public static long getFileSize(String filePath) {

		File file = new File(filePath);

		return file.length();
	}

	public static String getFileTime(String filePath) {

		File file = new File(filePath);

		return DateUtil.getTime(file.lastModified());
	}

	public static int setFlag(int value, int pos, boolean flag) {

		if (flag) {
			value = value | pos;
		} else {
			value = ~(~value | pos);
		}

		return value;
	}

	public static boolean getFlag(int value, int pos) {

		boolean flag;

		if ((value & pos) != 0) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	public static String[] split(String str) {

		StringTokenizer tokenizer = new StringTokenizer(str);
		String[] array = new String[tokenizer.countTokens()];

		for (int i = 0; i < array.length; i++) {
			array[i] = tokenizer.nextToken();
		}

		return array;
	}

	public static String assembleSQL(String fieldName, String[] array,
			String logic) {

		String str = fieldName + " LIKE '%" + array[0] + "%'";

		if (array.length > 1) {
			for (int i = 1; i < array.length; i++) {
				str = str + " " + logic + " " + fieldName + " LIKE '%"
						+ array[i] + "%'";
			}
		}

		return "(" + str + ")";
	}

	public static String getCookieValue(Cookie[] cookies, String name) {

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) {
				return cookies[i].getValue();
			}
		}

		return null;
	}

	public static void saveFile(String filename, String content) {
		try {
			BufferedWriter out = null;
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "UTF-8"));
			if (out != null) {
				out.append(content);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getRootPath() {
		return OSUtil.class.getResource("/").getPath();
		// util.rootpath = System.getProperty("user.dir");
	}

	public static String[][] sortHttpRequestMap(Map<String, String> map) {
		String[][] ret = new String[map.size()][2];
		Map<String, String> sort = new TreeMap<String, String>();
		for (String key : map.keySet()) {
			sort.put(key, map.get(key));
		}
		List<Map.Entry<String, String>> infos = new ArrayList<Map.Entry<String, String>>(
				sort.entrySet());
		for (int i = 0; i < infos.size(); i++) {
			ret[i][0] = infos.get(i).getKey();
			ret[i][1] = infos.get(i).getValue();
		}
		return ret;
	}

	public static String addZero(String content, int len) {
		if (content == null) {
			return content;
		}

		int length = content.length();
		if (length < len) {
			int padding = len - length;
			while (padding > 0) {
				content = "0" + content;
				padding--;
			}
		}
		return content;
	}

	public static String rmvZero(String content) {
		if (content == null || content.length() <= 0) {
			return content;
		}

		String str = content.substring(0, 1);
		while (str.equals("0")) {
			content = content.substring(1, content.length());
			if (content.length() <= 0) {
				break;
			}
			str = content.substring(0, 1);
		}
		return content;
	}
}