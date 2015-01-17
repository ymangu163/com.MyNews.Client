package com.ymangu.mynews.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
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
import com.ymangu.mynews.intface.DownFlagInterface;
import com.ymangu.mynews.utils.GsonUtils;
import com.ymangu.mynews.utils.HMApi;
import com.ymangu.mynews.utils.SharePrefUtil;

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
  
	/*
	 *  1  没有网络的时候，提高用户体验，因为不缓存数据的话，会成一片空白
	 *  2  有可能，如果能联网的话，我们需要去更新数据
	 *  3  在开发的过程中，首先，必须先从本地去找数据
	 *    ① 如果本地有就显示
	 *    ② 然后再通过去与服务器交互，从服务器取数据
	 *    ③ 然后再显示
	 *    
	 */
	@Override
	public void initData(DownFlagInterface downFlagInterface) {
		String value=SharePrefUtil.getString(context, HMApi.NEWS_CENTER_CATEGORIES);
		if(!TextUtils.isEmpty(value)){
			ProcessData(downFlagInterface,value);       // 在这里去渲染数据
		}
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
						
				    // 使用SharePreference 缓存数据，
					SharePrefUtil.saveString(context, HMApi.NEWS_CENTER_CATEGORIES, responseInfo.result);
						
						
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
	protected void ProcessData(DownFlagInterface downFlagInterface,String result) {
		NewsCenterBean newsCenterBean=GsonUtils.jsonToBean(result, NewsCenterBean.class);  //写得更通用
		
		if (200 == newsCenterBean.retcode) {
			List<CenterDataBean> data = newsCenterBean.data;
			for (CenterDataBean dataBean : data) {
				menuNewCenterList.add(dataBean.title);    // 拿到 title 数据
			}
			
			MenuFragment2 menuFragment2 = ((MainActivity)context).getMenuFragment2();
			menuFragment2.initMenu(menuNewCenterList);   //把数据传过去
			
			downFlagInterface.setDownFlag(true);  //设置让它不再下载了
			
			
		}
		
		
	}

}
