package com.ymangu.mynews.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.MainActivity;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.bean.NewsCenterBean;
import com.ymangu.mynews.bean.NewsCenterBean.CenterDataBean;
import com.ymangu.mynews.fragment.MenuFragment2;
import com.ymangu.mynews.intface.DownFlagInterface;
import com.ymangu.mynews.newspages.InteractPage;
import com.ymangu.mynews.newspages.NewsPage;
import com.ymangu.mynews.newspages.PicPage;
import com.ymangu.mynews.newspages.TopicPage;
import com.ymangu.mynews.newspages.VotePage;
import com.ymangu.mynews.utils.GsonTools;
import com.ymangu.mynews.utils.HMApi;
import com.ymangu.mynews.utils.SharePrefUtil;
import com.ymangu.mynews.utils.SharePrefUtil.KEY;

public class NewsCenterPage extends BasePage {
	
	
	public NewsCenterPage(Context context) {
		super(context);
	}
	
	public void onResume(){
		super.onResume();
	}

	@Override
	public View initView(LayoutInflater inflater) {
	  View  contextView=inflater.inflate(R.layout.news_center_frame, null);
	  ViewUtils.inject(this, contextView);
	  initTitleBar(contextView);  //在父类中完成
		return contextView;
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
	public void initData() {
		pageList = new ArrayList<BasePage>();
		if (newsCenterMenuList.size() == 0) {
			String result = SharePrefUtil.getString(context,	HMApi.NEWS_CENTER_CATEGORIES, "");
			if (!TextUtils.isEmpty(result)) {
				ProcessData(result);
			}
			
			getNewsCenterCategories(); // 下载数据
		}
				
	}
	
	private void getNewsCenterCategories() {
		loadData(HttpMethod.GET, HMApi.NEWS_CENTER_CATEGORIES, null,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> info) {
						LogUtils.d("response_json---" + info.result);
						 SharePrefUtil.saveString(context,
								 HMApi.NEWS_CENTER_CATEGORIES, info.result);
						ProcessData(info.result);

					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						LogUtils.d("response_fail---" + arg1);

					}
				});	
		
	}

	public ArrayList<BasePage> pageList;
	public ArrayList<String> newsCenterMenuList = new ArrayList<String>();
	

	// 用于保存 slidingMenu菜单数据
	private List<String> menuNewCenterList = new ArrayList<String>();
	private List<CenterDataBean> dataList;

	/*
	 * . 我们在 NewsCenterPage中拿到菜单的数据之后，需要设置到菜单--MenuFragment2中去 ；实现方法是在
	 * MainActivity 中暴露一个方法得到 MenuFragment2 对象(不 new 是为了代码更清洁)；拿到
	 * MenuFragment2对象后就可以调用它的方法去初始化菜单了。
	 */
	protected void ProcessData(String result) {
		NewsCenterBean newsCenterBean=GsonTools.changeGsonToBean(result, NewsCenterBean.class);  //写得更通用
		if (newsCenterBean.retcode != 200) {
			return;
		}
	
			dataList = newsCenterBean.data;
			newsCenterMenuList.clear();
			
			for (CenterDataBean dataBean : dataList) {
				newsCenterMenuList.add(dataBean.title);    // 拿到 title 数据
			}
			
			MenuFragment2 menuFragment2 = ((MainActivity)context).getMenuFragment2();
			menuFragment2.initMenu(newsCenterMenuList);   //把数据传过去初始化菜单内容
			
//			isLoadSuccess=true;
			
			CenterDataBean newsDataBean=dataList.get(0);
			// 使用SharePreference 缓存数据,只存相关的部分
			SharePrefUtil.saveString(context, KEY.CATE_ALL_JSON, GsonTools.createGsonString(newsDataBean.children));
			SharePrefUtil.saveString(context, KEY.CATE_EXTEND_ID, GsonTools.createGsonString(newsCenterBean.extend));
			
			/*
			 *  新闻中心对应的菜单中，每项对应的页
			 *  菜单页的内容
			 */
			pageList.clear();
			BasePage newsPage = new NewsPage(context, newsDataBean);
			BasePage topicPage = new TopicPage(context, dataList.get(1));
			BasePage picPage = new PicPage(context, dataList.get(2));
			BasePage interactPage = new InteractPage(context, dataList.get(3));
			BasePage votePage = new VotePage(context, dataList.get(4));
			
			pageList.add(newsPage);
			pageList.add(topicPage);
			pageList.add(picPage);
			pageList.add(interactPage);
			pageList.add(votePage);
			switchFragment(MenuFragment2.newsCenterPosition);   // 切换到不同的页
			
	}
	
	@ViewInject(R.id.news_center_fl)
	private FrameLayout news_center_fl;  //帧布局
	
	public  void switchFragment(int newsCenterPosition) {
		
		LogUtils.d("newsCenterPosition="+String.valueOf(newsCenterPosition));   // 打印
		BasePage page = pageList.get(newsCenterPosition);
		
		switch (newsCenterPosition) {
		case 0:
			// 先清空一下，再加入View
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		case 1:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		case 2:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		case 3:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		case 4:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		}
		page.initData();
		
		
	}

	@Override
	protected void processClick(View v) {
		
	}

}
