package com.ymangu.mynews.base;

import com.ymangu.mynews.intface.DownFlagInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

  /*
 * . 怎么在一个普通的类中 画 View ?
 *  通过 Inflater 把 xml 转化成 View，返回
 */
public abstract class BasePage {
	public View view;
	public Context context;
	
	/*
	 * . 1 画界面
	 *   2 初始化数据
	 */
	public BasePage(Context context) {
		this.context=context;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
		view = initView(inflater);
		
	}
	
	/*
	 * .  暴露一个方法，把 view 返回回去
	 */
	public  View getRootView(){
    	return view;
    }
	
	public abstract View initView(LayoutInflater inflater);
	public abstract void initData(DownFlagInterface downFlagInterface);


	
	}
	
	
	
	
	
	

