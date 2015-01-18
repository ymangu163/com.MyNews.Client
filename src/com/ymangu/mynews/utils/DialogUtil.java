package com.ymangu.mynews.utils;




import android.app.Dialog;
import android.content.Context;


public class DialogUtil {



	public static Dialog createProgressDialog(Context context,String content){
		return new CustomProgressDialog(context, content);
	}
	
}
