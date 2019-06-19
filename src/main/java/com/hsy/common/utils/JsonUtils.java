package com.hsy.common.utils;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


/**
 * @author 张梓枫
 * @Description json操作类
 * @date:   2019年1月2日 上午10:18:44
 */
public final class JsonUtils {
	
	private static final ThreadLocal<Gson> gsonLocal = new ThreadLocal<Gson>() {
		@Override
		protected Gson initialValue() {
			return new GsonBuilder().create();
		}
	};
	public static String toJson(Object obj){
		Gson gson = gsonLocal.get();
		return gson.toJson(obj);
	}

	public static <T> T toBean(String json, Class<T> clazz) {
		Gson gson = gsonLocal.get();
		return gson.fromJson(json, clazz);
	}

	public  static List<?> toList(String json){
		 Gson gson = gsonLocal.get();
		 return gson.fromJson(json, new TypeToken<List<?>>(){}.getType());
	}
	
	public static Map<?, ?> toMap(String json){
		 Gson gson = gsonLocal.get();
		 return gson.fromJson(json, new TypeToken<Map<?, ?>>(){}.getType());
	}
}
