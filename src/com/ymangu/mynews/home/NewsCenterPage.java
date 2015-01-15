package com.ymangu.mynews.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.ymangu.mynews.MainActivity;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.bean.NewsCenterBean;
import com.ymangu.mynews.bean.NewsCenterBean.CenterDataBean;
import com.ymangu.mynews.fragment.MenuFragment2;
import com.ymangu.mynews.utils.GsonUtils;
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

//						 Gson gson = new Gson();
//						 // 把 json 数据解析成 Bean
//						 NewsCenterBean category = gson.fromJson(responseInfo.result, NewsCenterBean.class);
						 LogUtils.d(responseInfo.result);   // 打印 json 数据
						
						
						ProcessData(responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
	}
	// 用于保存 slidingMenu菜单数据
	private List<String> menuNewCenterList = new ArrayList<String>();

	/*
	 * . 我们在 NewsCenterPage中拿到菜单的数据之后，需要设置到菜单--MenuFragment2中去 ；实现方法是在
	 * MainActivity 中暴露一个方法得到 MenuFragment2 对象(不 new 是为了代码更清洁)；拿到
	 * MenuFragment2对象后就可以调用它的方法去初始化菜单了。
	 */
	protected void ProcessData(String result) {
		NewsCenterBean newsCenterBean=GsonUtils.jsonToBean(result, NewsCenterBean.class);  //写得更通用
		
		if (200 == newsCenterBean.retcode) {
			List<CenterDataBean> data = newsCenterBean.data;
			for (CenterDataBean dataBean : data) {
				menuNewCenterList.add(dataBean.title);    // 拿到 title 数据
			}
			
			MenuFragment2 menuFragment2 = ((MainActivity)context).getMenuFragment2();
			menuFragment2.initMenu(menuNewCenterList);   //把数据传过去
			
			
			
		}
		
		
	}

}
