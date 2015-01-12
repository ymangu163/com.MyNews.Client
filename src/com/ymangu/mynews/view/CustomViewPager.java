package com.ymangu.mynews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/*
 * . ViewPager + ViewPager 嵌套时 让底层的ViewPager划不动 -- 360手机助手。
 */
public class CustomViewPager extends LazyViewPager {
	
	// 定义一个标记，
	private boolean setTouchModel = false;  

	public CustomViewPager(Context context) {
		super(context);
	}
	
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * . 一碰到它就 截断，不让它把事件消耗掉
	 *  返回 false ,让它把事件传给子控件
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(setTouchModel){
			return super.onInterceptTouchEvent(ev);			
		}else{
			return false;
		}
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(setTouchModel){
			return super.onTouchEvent(ev);
		}else{
			return false;
		}
	}
	
}
