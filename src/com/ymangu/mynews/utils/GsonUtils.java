package com.ymangu.mynews.utils;

import com.google.gson.Gson;

public class GsonUtils {
	
	// T 就是我们的 Bean
	public static<T> T jsonToBean(String jsonResult,Class<T> clz){
		Gson gson = new Gson();
		T t = gson.fromJson(jsonResult, clz);
		 return t;
	}	

}
