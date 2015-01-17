package com.ymangu.mynews.newspages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.bean.NewsCenterBean.CenterDataBean;

public class PicPage extends BasePage {


	private CenterDataBean category;
	public PicPage(Context ct, CenterDataBean newsDataBean) {
		super(ct);
		category = newsDataBean;
	}

	@Override
	protected View initView(LayoutInflater inflater) {
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
