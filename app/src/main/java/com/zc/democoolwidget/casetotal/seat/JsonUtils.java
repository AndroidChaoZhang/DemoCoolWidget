package com.zc.democoolwidget.casetotal.seat;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 * 
 * @author Administrator
 */
public class JsonUtils {

	private static Gson mGson = new Gson();

	public static String getJsonStrFromMap(Map<String, Object> map) {
		try {
			String res = mGson.toJson(map);
			return res;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取json字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String getJsonStr(List<Map<String, Object>> list) {
		return mGson.toJson(list);
	}

	public static List<String> getJsonStr(String json) {
		return mGson.fromJson(json, new TypeToken<List<String>>() {
		}.getType());
	}
	public static String toJosn(Object list) {
		return mGson.toJson(list);
	}
	/**
	 * 返回List
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, Object>> getList(String jsonStr) {
		return mGson.fromJson(jsonStr,
				new TypeToken<List<Map<String, Object>>>() {
				}.getType());
	}

	/**
	 * 返回List
	 *
	 * @param jsonStr
	 * @return
	 */
	public static List<String> getStringList(String jsonStr) {
		return mGson.fromJson(jsonStr,
				new TypeToken<List<String>>() {
				}.getType());
	}


	private static String getExceptionDetail(Exception e) {
		StringBuffer msg = new StringBuffer("null");
		if (e != null) {
			msg = new StringBuffer("");
			String message = e.toString();
			int length = e.getStackTrace().length;
			if (length > 0) {
				msg.append(message + "\n");
				for (int i = 0; i < length; i++) {
					msg.append("\t" + e.getStackTrace()[i] + "\n");
				}
			} else {
				msg.append(message);
			}
		}
		return msg.toString();
	}

	/**
	 * 字符串转实例
     */
	public static <T> T getTFromStr(String json,Class<T> cls){
		return mGson.fromJson(json, cls);
	}

	/**字符串转list
     */
	public static <T> List<T>  getTsFromStr(String json,Class<T> clazz){
		List<T> lst = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for(final JsonElement elem : array){
			lst.add(mGson.fromJson(elem, clazz));
		}
		return lst;
	}
}
