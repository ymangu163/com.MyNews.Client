package com.ymangu.mynews.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ymangu.mynews.base.BasePage;

public class NewsCenterPage extends BasePage {

	public NewsCenterPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		TextView textView = new TextView(context);
		textView.setText("我是新闻中心");
		return textView;
	}

	@Override
	public void initData() {
		
	}

}
