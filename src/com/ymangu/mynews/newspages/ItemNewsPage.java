package com.ymangu.mynews.newspages;


import java.util.ArrayList;
import java.util.HashSet;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.NewsAdapter;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.bean.CountList;
import com.ymangu.mynews.bean.NewsCenterBean;
import com.ymangu.mynews.bean.NewsListBean;
import com.ymangu.mynews.bean.NewsListBean.News;
import com.ymangu.mynews.bean.NewsListBean.NewsList;
import com.ymangu.mynews.bean.NewsListBean.TopNews;
import com.ymangu.mynews.utils.CommonUtil;
import com.ymangu.mynews.utils.Constants;
import com.ymangu.mynews.utils.QLParser;
import com.ymangu.mynews.utils.SharePrefUtil;
import com.ymangu.mynews.view.RollViewPager;
import com.ymangu.mynews.view.RollViewPager.OnPagerClickCallback;
import com.ymangu.mynews.view.pullrefreshview.PullToRefreshBase;
import com.ymangu.mynews.view.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.ymangu.mynews.view.pullrefreshview.PullToRefreshListView;
import com.ymangu.mynews.view.ui.NewsDetailActivity;

public class ItemNewsPage extends BasePage {
	@ViewInject(R.id.lv_item_news)
	private PullToRefreshListView ptrLv;
	@ViewInject(R.id.top_news_title)
	private TextView topNewsTitle;
	@ViewInject(R.id.top_news_viewpager)
	private LinearLayout mViewPagerLay;
	@ViewInject(R.id.dots_ll)
	private LinearLayout dotLl;
	private String url;
	private String moreUrl;
	private View topNewsView;
	private String hasReadIds;
	private HashSet<String> readSet = new HashSet<String>();
	private String countCommentUrl;
	private ArrayList<TopNews> topNews;
	private ArrayList<String> titleList;
	private ArrayList<String> urlList;
	private ArrayList<View> dotList;
	private RollViewPager rollViewPager;
	private ArrayList<News> news_list=new ArrayList<NewsListBean.News> ();
	private NewsAdapter adapter;
	
	public ItemNewsPage(Context context, String url) {
		super(context);
		this.url = url;      // 传递进来的url
	}  

	@Override
	public View initView(LayoutInflater inflater) {
		/*
		 *  一个Page中怎么能载入两个 Layout 布局呢？
		 *  
		 */
		View view = inflater.inflate(R.layout.frag_item_news, null);
		 // 这个 topNewsView 是要加到ListView中充当Header.
	     topNewsView = inflater.inflate(R.layout.layout_roll_view, null);
	     ViewUtils.inject(this, view);
		 ViewUtils.inject(this, topNewsView);
		// 上拉加载不可用
		ptrLv.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		ptrLv.setScrollLoadEnabled(true);
		// 得到实际的ListView  并设置点击
		ptrLv.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
						
						
						
					}
		});
		
//		setLastUpdateTime();   // 设置上一次刷新的时间
		
		// 设置下拉刷新的listener  
		ptrLv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getNewsList(url,true);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getNewsList(moreUrl, false);
			}
			
			
		});
		
	
		return view;    // 返回包含 PullToRefreshListView 的View
	}

	// 去更新数据
	protected void getNewsList(String loadUrl, final boolean isRefresh) {
		LogUtils.i("==============================="+loadUrl);
		// 放到父类Base中去了，这样的框架很多应用都是这样子的
		loadData(HttpMethod.GET,loadUrl,null,new RequestCallBack<String>(){

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				LogUtils.d("fail_json---" + arg1);
				onLoaded();		 // 下载失败，去处理
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> info) {
				LogUtils.d("response_json---" + info.result);
				if(isRefresh){  // isRefresh 刷新过就去处理数据
					// 下载成功之后，去存贮数据  
					SharePrefUtil.saveString(context, url, info.result);
				}
				ProcessData(isRefresh,info.result);   //下载好数据后 去处理
				
			}			
		});
	}

	protected void ProcessData(boolean isRefresh, String result) {
		NewsListBean newsList=QLParser.parse(result, NewsListBean.class);
		// 在解析前先判断一下是不是200； 这就是定义BaseBean的用意
		if (newsList.retcode != 200) {
		}else{
			isLoadSuccess = true;    // 让它不再去下载
			countCommentUrl = newsList.data.countcommenturl;    //评论的Url
			if (isRefresh) {
				topNews = newsList.data.topnews;
				if (topNews != null) {
					titleList = new ArrayList<String>();
					urlList = new ArrayList<String>();
					for (TopNews news : topNews) {
						titleList.add(news.title);     // title数据
						urlList.add(news.topimage);  // 对应图片的url
					}
					initDot(topNews.size());    // 初始化点.  topNews 是广告页的集合
				
					// 自定义的 可以滚动的ViewPager
				rollViewPager = new RollViewPager(context, dotList,
							R.drawable.dot_focus, R.drawable.dot_normal,
							new OnPagerClickCallback(){
								@Override
								public void onPagerClick(int position) {
									TopNews news = topNews.get(position);
									if (news.type.equals("news")) {
										// 点击时，跳到详情页
										Intent intent = new Intent(context,NewsDetailActivity.class);
										String url = topNews.get(position).url;
										String title = topNews.get(position).title;
										intent.putExtra("url", url);
										intent.putExtra("title", title);
										context.startActivity(intent);
										
									}else if(news.type.equals("topic")){
										
									}
									
								}
				} );
				rollViewPager.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT,	LayoutParams.WRAP_CONTENT));	
				//top新闻的图片地址
				rollViewPager.setUriList(urlList);
				rollViewPager.setTitle(topNewsTitle, titleList);
				rollViewPager.startRoll();
				mViewPagerLay.removeAllViews();
				mViewPagerLay.addView(rollViewPager);   // 上边部分已经有数据了
				if (ptrLv.getRefreshableView().getHeaderViewsCount() < 1) {
					ptrLv.getRefreshableView().addHeaderView(topNewsView);  // 给ListView加头，让上边与下边连成一体
				} 
				}
			}
			moreUrl = newsList.data.more;
			if (newsList.data.news != null) {
				// 加载评论
				getNewsCommentCount(newsList.data.countcommenturl,newsList.data.news, isRefresh);
			}
			
		}
		
		
	}
	
	private void getNewsCommentCount(String countcommenturl,	final ArrayList<News> newsList, final boolean isRefresh) {
		StringBuffer sb=new StringBuffer(countcommenturl);
		for(News news:newsList){
			sb.append(news.id+",");						
		}
		loadData(HttpMethod.GET,sb.toString(),null,new RequestCallBack<String>() {

		

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				LogUtils.d("fail_json---" + arg1);
				onLoaded();
			}

			@Override
			public void onSuccess(ResponseInfo<String> info) {
				LogUtils.d("response_json---" + info.result);
				CountList countList = QLParser.parse(info.result,	CountList.class);
				for(News news: newsList){
							news.commentcount = countList.data.get(news.id + "");
					 if(readSet.contains(news.id)){  //已经读过
						 news.isRead=true;
					 }else{
						 news.isRead=false;
					 }
					
				}
				if(isRefresh){
					news_list.clear();
					news_list.addAll(newsList);
					
				}else{
					news_list.addAll(newsList);
				}
				if(adapter==null){
					adapter = new NewsAdapter(context,news_list,0);
					ptrLv.getRefreshableView().setAdapter(adapter);   // 适配后，PullToRefreshListView就可见了
				}else{
					adapter.notifyDataSetChanged();
				}
				onLoaded();
				LogUtils.d("moreUrl---" + moreUrl);
				if (TextUtils.isEmpty(moreUrl)) {
					ptrLv.setHasMoreData(false);
				} else{
					ptrLv.setHasMoreData(true);
				}
				
//				setLastUpdateTime();
			}
		});
		
		
		
		
		
	}

	// 初始化广告页的点,   使用java 代码动态适配,而不是写在xml中
	private void initDot(int size) {
		dotList = new ArrayList<View>();
		dotLl.removeAllViews();
		for (int i = 0; i < size; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					CommonUtil.dip2px(context, 6), CommonUtil.dip2px(context, 6));
			params.setMargins(5, 0, 5, 0);
			View mview = new View(context);
			if (i == 0) {
				mview.setBackgroundResource(R.drawable.dot_focus);   // 选中
			} else {
				mview.setBackgroundResource(R.drawable.dot_normal);
			}
			mview.setLayoutParams(params);
			dotLl.addView(mview);
			dotList.add(mview);
		}
		
	}

	protected void onLoaded() {
		dismissLoadingView();    // 下载时，转圈的view消失
		ptrLv.onPullDownRefreshComplete();
		ptrLv.onPullUpRefreshComplete();		
	}

	@Override
	public void initData() {
		// 获取保存的已阅读newsid
		hasReadIds = SharePrefUtil.getString(context,  Constants.READ_NEWS_IDS, "");
		String[] ids=hasReadIds.split(",");   // 将String 分割成数组
		for(String id:ids){
			readSet.add(id);     // 把id保存到set中			
		}
		
		if(!TextUtils.isEmpty(url)){
			String result=SharePrefUtil.getString(context, url, "");  // 先去获取缓存中以url为key的数据
			if(!TextUtils.isEmpty(result)){
				processDataFromCache(true, result);    // 用缓存中的数据去初始化控件
				
			}
			getNewsList(url, true);   // 去拿数据
		}
		
		
	}
	
	// 用缓存中的数据去初始化控件
	private void processDataFromCache(boolean isRefresh, String result) {
		NewsListBean newsList = QLParser.parse(result, NewsListBean.class);
		if (newsList.retcode != 200) {
		}else{
			isLoadSuccess = true;   // 下载成功，就不再下载了
			countCommentUrl = newsList.data.countcommenturl;
			if (isRefresh) {
				topNews = newsList.data.topnews;
				if(topNews!=null){
					titleList = new ArrayList<String>();
					urlList = new ArrayList<String>();
					for(TopNews news:topNews){
						titleList.add(news.title);
						urlList.add(news.topimage);			
						
					}
					initDot(topNews.size());
					rollViewPager=new RollViewPager(context,dotList,R.drawable.dot_focus, R.drawable.dot_normal,
							new OnPagerClickCallback() {
								
								@Override
								public void onPagerClick(int position) {
									TopNews news = topNews.get(position);
									if (news.type.equals("news")) {
										Intent intent = new Intent(context,NewsDetailActivity.class);
										String url = topNews.get(position).url;
										String commentUrl = topNews.get(position).commenturl;
										String newsId = topNews.get(position).id;
										String commentListUrl = topNews
												.get(position).commentlist;
										String title = topNews.get(position).title;
										String imgUrl = topNews.get(position).topimage;
										boolean comment = topNews.get(position).comment;
										intent.putExtra("url", url);
										intent.putExtra("commentUrl",	commentUrl);
										intent.putExtra("newsId", newsId);
										intent.putExtra("imgUrl", imgUrl);
										intent.putExtra("title", title);
										intent.putExtra("comment", comment);
										intent.putExtra("countCommentUrl",countCommentUrl);
										intent.putExtra("commentListUrl",commentListUrl);
										context.startActivity(intent);									
										
									}else if(news.type.equals("topic")){
										
									}
								}
							});
					rollViewPager.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));
					rollViewPager.setUriList(urlList);
					rollViewPager.setTitle(topNewsTitle, titleList);
					rollViewPager.startRoll();
					mViewPagerLay.removeAllViews();
					mViewPagerLay.addView(rollViewPager);
					if (ptrLv.getRefreshableView().getHeaderViewsCount() < 1) {
						ptrLv.getRefreshableView().addHeaderView(topNewsView);
					} 
					
					
				}				
				
			}
			moreUrl = newsList.data.more;
			LogUtils.d("111111="+newsList.data.news.size());
			if (isRefresh) {
				news_list.clear();
				news_list.addAll(newsList.data.news);
			} else {
				news_list.addAll(newsList.data.news);
			}
			
			for (News newsItem : news_list) {
				if(readSet.contains(newsItem.id)){
					newsItem.isRead= true;
				}else{
					newsItem.isRead = false;
				}
			}
			if (adapter == null) {
				adapter = new NewsAdapter(context, news_list, 0);
				ptrLv.getRefreshableView().setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
			onLoaded();
			LogUtils.d("moreUrl---" + moreUrl);
			if (TextUtils.isEmpty(moreUrl)) {
				ptrLv.setHasMoreData(false);
			} else {
				ptrLv.setHasMoreData(true);
			}
//			setLastUpdateTime();
		}
		
		
	}

	@Override
	protected void processClick(View v) {

	}



	

}
