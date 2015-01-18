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
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.bean.NewsCenterBean;
import com.ymangu.mynews.bean.NewsCenterBean.CenterDataBean;
import com.ymangu.mynews.bean.NewsCenterBean.CenterDataChildrenBean;
import com.ymangu.mynews.home.FunctionPage;
import com.ymangu.mynews.home.GovAffairsPage;
import com.ymangu.mynews.home.NewsCenterPage;
import com.ymangu.mynews.home.SettingPage;
import com.ymangu.mynews.home.SmartServicePage;
import com.ymangu.mynews.view.pagerindicator.TabPageIndicator;
/*
 *  框架清理：
 *  1  整个App包含SlidingMenu，所以继承 SlidingFragmentActivity，第一层分为两个Fragment.就是代码中的MenuFragment
 *  和 HomeFragment.
 *  2  HomeFragment中包含一个ViewPage,ViewPage中包含5个Page页 ---->这是第二层 Fragment+ViewPager：FunctionPage,
 *     NewsCenterPage,SmartServicePage,GovAffairsPage,和SettingPage
 *  3  以 NewsCenterPage为例，其包含一个FrameLayout,当点击NewsCenterPage对应的slidingMenu时，用不同的Page去
 *  渲染这个FrameLayout. 这些Page包括：NewsPage，TopicPage，PicPage，InteractPage，VotePage. --->这是第三层
 *     
 *  4  每个Page中又包含一个ViewPager,以NewsPage为例---- 该子 ViewPager中又包含多个page （北京、中国、国际、文娱、
 *  体育、生活、旅游、科技、军事、财经、女性、倍儿逗）--->这是第四层  Fragment+  ViewPager+ViewPager
 *  
 */
public class NewsPage extends BasePage {
	
	@ViewInject(R.id.indicator)
	private TabPageIndicator indicator;     //pagerIndicator
	@ViewInject(R.id.pager)
	private ViewPager viewPager;  //子ViewPager
	private int curIndex = 0;   //  记录当前页位置
	
	private CenterDataBean category;

	public NewsPage(Context ct, CenterDataBean newsDataBean) {
		super(ct);
		category = newsDataBean;
	}
	
	private NewsCenterPage newsCenterFragment ;  // 没用上
	
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_news, null);
		ViewUtils.inject(this, view);
		return view;
	}
	 
	@Override
	public void initData() {
		initIndicator();
	}
		
	private ArrayList<ItemNewsPage> list_pages = new ArrayList<ItemNewsPage>();
	private NewsPagerAdapter adapter;
	
	

	private void initIndicator() {
		list_pages.clear();   // 渲染前先清空，让它没有杂质
		for(CenterDataChildrenBean cate:category.children){  // 获取到数据
			list_pages.add(new ItemNewsPage(context,cate.url));	 //把对应的url传递过去		
			
		}		
		
		adapter = new NewsPagerAdapter(context,list_pages);
		viewPager.removeAllViews();
		viewPager.setAdapter(adapter);
		
		// TabPageIndicator 监听事件
		indicator.setOnPageChangeListener(new OnPageChangeListener() {			
			@Override
			public void onPageSelected(int position) {
				// 只有第0页才滑出
				if(position==0){
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else{
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
				BasePage page = list_pages.get(position);
				if(!page.isLoadSuccess){
					page.initData();   //去初始化数据
				}
				curIndex = position;
				
			}			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
								
			}			
			@Override
			public void onPageScrollStateChanged(int arg0) {
								
			}
		});
		list_pages.get(0).initData();
		indicator.setViewPager(viewPager);   // 给TabPageIndicator设置绑定的ViewPager
		indicator.setCurrentItem(curIndex);  //设置当前位置
		isLoadSuccess = true;
		
	}



	@Override
	protected void processClick(View v) {	
		
	}

	class NewsPagerAdapter extends PagerAdapter{
		private ArrayList<ItemNewsPage> list_pages;

		public NewsPagerAdapter(Context context,ArrayList<ItemNewsPage> list){
			this.list_pages=list;	
			
		}
		
		@Override
		public int getCount() {
			return list_pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg1 == arg0;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);
			if(position>=list_pages.size()) return;
			((ViewPager)container).removeView(list_pages.get(position).getContentView());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager)container).addView(list_pages.get(position).getContentView(),0);		
			
			return list_pages.get(position).getContentView();
		}			
	}


}
