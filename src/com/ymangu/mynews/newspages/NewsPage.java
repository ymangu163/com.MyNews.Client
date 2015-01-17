package com.ymangu.mynews.newspages;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.bean.NewsCenterBean;
import com.ymangu.mynews.bean.NewsCenterBean.CenterDataBean;
import com.ymangu.mynews.home.NewsCenterPage;

public class NewsPage extends BasePage {

	private CenterDataBean category;

	public NewsPage(Context ct, CenterDataBean newsDataBean) {
		super(ct);
		category = newsDataBean;
	}
	private NewsCenterPage newsCenterFragment ;
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_news, null);
		ViewUtils.inject(this, view);
		return view;
	}

	

	@Override
	public void initData() {
 
	}
	


	@Override
	protected void processClick(View v) {
		
	}



}
