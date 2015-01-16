package com.ymangu.mynews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePrefUtil {
	private static SharedPreferences sp;
	private static String SP_NAME="config";  // sp的名字抽取出来，方便改变

	// 保存String数据
	public static void saveString(Context context,String key,String value){
		
		if(sp==null){
			sp = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);			
		}
		
//		 Editor edit = sp.edit();
//		 edit.putString(key,value);
//		 edit.commit();
		sp.edit().putString(key, value).commit();   //提交
	}
	
	// 取出String 数据
	public static String getString(Context context,String key){
		if(sp==null){
			sp=context.getSharedPreferences(SP_NAME, 0);			
		}
		return sp.getString(key, "");
	}
	

}
