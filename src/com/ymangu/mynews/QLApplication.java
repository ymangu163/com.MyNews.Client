package com.ymangu.mynews;

import java.util.HashSet;
import java.util.Set;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

public class QLApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		
		//表示周一到周五、上午10点到晚上23点，都可以推送
		Set<Integer> days = new HashSet<Integer>();
		days.add(1);
		days.add(2);
		days.add(3);
		days.add(4);
		days.add(5);
		JPushInterface.setPushTime(getApplicationContext(), days, 10, 23);
		
		
		
		
	}

}
