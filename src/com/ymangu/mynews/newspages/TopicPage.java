package com.ymangu.mynews.newspages;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.bean.NewsCenterBean.CenterDataBean;

public class TopicPage extends BasePage {
	private String moreUrl;
	private CenterDataBean category;
	public TopicPage(Context ct, CenterDataBean newsDataBean) {
		super(ct);
		category = newsDataBean;
	}


	@Override
	protected View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_topic, null);
		ViewUtils.inject(this, view);
		
		
		
		return view;
		
	}
		
	@Override
	protected void processClick(View v) {

	}

	@Override
	public void initData() {
		
	}

}
