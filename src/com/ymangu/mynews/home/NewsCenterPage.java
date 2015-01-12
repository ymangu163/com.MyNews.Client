package com.ymangu.mynews.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.utils.HMApi;

public class NewsCenterPage extends BasePage {

	public NewsCenterPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		TextView textView = new TextView(context);
		textView.setText("我是新闻中心");
		LogUtils.d("新闻中心到了，靖下车");
		return textView;
	}

	@Override
	public void initData() {
		TestGet();
		
	}
	
	// 使用 xUtils 的方式 获取数据
	private void TestGet() {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET, HMApi.NEWS_CENTER_CATEGORIES,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// textView.setText(responseInfo.result);

						// Gson gson = new Gson();
						// NewsCenterCategory category =
						// gson.fromJson(responseInfo.result,
						// NewsCenterCategory.class);
						LogUtils.d(responseInfo.result);   // 打印 json 数据

//						ProcessData(responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});

		
		
		
		
		
		
		
		
		
	}

}
