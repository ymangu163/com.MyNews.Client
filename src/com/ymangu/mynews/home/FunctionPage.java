package com.ymangu.mynews.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.intface.DownFlagInterface;

public class FunctionPage  extends BasePage{

	public FunctionPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		TextView textView = new TextView(context);
		textView.setText("我是首页");
		return textView;
	}

	@Override
	public void initData(DownFlagInterface downFlagInterface) {
		
	}

}
