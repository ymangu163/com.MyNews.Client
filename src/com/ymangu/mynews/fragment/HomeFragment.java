package com.ymangu.mynews.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BaseFragment;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.home.FunctionPage;
import com.ymangu.mynews.home.GovAffairsPage;
import com.ymangu.mynews.home.NewsCenterPage;
import com.ymangu.mynews.home.SettingPage;
import com.ymangu.mynews.home.SmartServicePage;
import com.ymangu.mynews.view.CustomViewPager;
import com.ymangu.mynews.view.LazyViewPager;
import com.ymangu.mynews.view.LazyViewPager.OnPageChangeListener;

/*
 * . 架构： Fragment + ViewPager
 *  HomeFragment 是 Fragment， Fragment 中嵌套一个 ViewPager。 
 */

/*
 * .Android 内存优化
布局中的内容太多了，运行是崩溃了

分析。。。由于new出来的对象太多了。每new一个 之后会在堆中产生一块空间。。所以崩溃了。。。

解决方案：：1 在开发的过程当中。。如果能实现一模一样的需求，尽可能的使用相对布局。。
            2 比较灵活。。
			3 用户体验比较好。
		    4 在解析XML的时候，可能相对布局打印出来的时候，比线性布局打印出来的时间长，但是那个是假象。。
			5 核心功能就是减少冗余的层次从而达到优化UI的目的！	
			6 ViewStub 是一个隐藏的，不占用内存空间的视图对象，它可以在运行时延迟加载布局资源文件。
merge 目的 ：核心功能就是减少冗余的层次从而达到优化UI的目的！		
      Activity ：	控制器。。
	  window   ：   窗体 才是我们能看到的，把view画到window上我们就能看到
	  view     ：   view
ViewStub  控件。。	   
	是一个隐藏的，不占用内存空间的视图对象，它可以在运行时延迟加载布局资源
	用法：通过 findbyid找到viewstub之后，调用 viewstub.inflate(); 就会返回一个View对象。
	
	SDK 下看UI 更本质的工具 ---- Hierarchy viewer.
 */
public class HomeFragment extends BaseFragment {

	private View view;
	@ViewInject(R.id.viewpager)
	private CustomViewPager viewPager;
	@ViewInject(R.id.main_radio)
	private RadioGroup radio_group;
	
	private int checkedId = R.id.rb_function;
	
	@Override
	public View initView(LayoutInflater inflater) {
		// xml 转换成 View
		view = inflater.inflate(R.layout.frag_home, null);
		//  得到 LazyViewPager 
//		viewPager = (CustomViewPager) view.findViewById(R.id.viewpager);
		// 得到 RadioGroup
//		radio_group = (RadioGroup) view.findViewById(R.id.main_radio);
		
		 ViewUtils.inject(this, view); // Fragment 中注入view和事件
		 
		return view;
	}

	private List<BasePage> list = new ArrayList<BasePage>();
	/*
	 * . 因为 ViewPager 继承自 ViewGroup，所以加入的元素是 View
	 *   定义 5个 View，还要定义一个集合 来装载这5个 View,并把它设置到 ViewPager中
	 *   怎么把这5个普通的 类 装载到 一个集合中呢？
	 *    -----》定义一个 BasePage类，然后让5个 page类都继承于它，就可以装载进集合了
	 */
	@Override
	public void initData(Bundle savedInstanceState) {
		/*
		 * . 把5 个 Page 装载进集合
		 *  注意：
		 *     	这里ViewPager 中载入的 是5个普通 的类，不是 Activity 也不是 Fragment
		 */
		list.add(new FunctionPage(context));
		list.add(new NewsCenterPage(context));
		list.add(new SmartServicePage(context));
		list.add(new GovAffairsPage(context));
		list.add(new SettingPage(context));
		
		HomePageAdapter adapter = new HomePageAdapter(context,list);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//如果位置是0的话，才能出现滑动菜单。。如果是其他的tab出现的时候，滑动菜单就给屏蔽掉。
				if(0==position){
					  sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else{
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);  // 不出来
				}
				
				/*
				 * . 找到 相应 page,
				 *  调用它的 initData(),就调回去了
				 */
				BasePage page=list.get(position);
				page.initData();
				
			}			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		// 添加它radio_group 的 点击事件
		radio_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch ( checkedId) {
				case R.id.rb_function:
					viewPager.setCurrentItem(0, false);  // false 是不经过中间页
					checkedId = 0;
					break;

				case R.id.rb_news_center:
					// ViewPager 的 setCurrentItem 方法反方便;
					viewPager.setCurrentItem(1, false);
					checkedId = 1;
					break;
				case R.id.rb_smart_service:
					viewPager.setCurrentItem(2, false);
					checkedId = 2;
					break;
				case R.id.rb_gov_affairs:
					viewPager.setCurrentItem(3, false);
					checkedId = 3;
					break;
				case R.id.rb_setting:
					viewPager.setCurrentItem(4, false);
					checkedId = 4;
					break;
				}
			}
			
		});
		radio_group.check(checkedId);   // 设置选中的 RadioButton
	}
	
	
	
	
	
	
	
	
	

	
	class HomePageAdapter extends PagerAdapter {
		
		private Context context;
		private List<BasePage> list;

		public HomePageAdapter(Context ct, List<BasePage> list) {
			this.context = ct;
			this.list = list;
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);
			((LazyViewPager) container).removeView(list.get(position)	.getRootView());			
			
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((LazyViewPager)container).addView(list.get(position).getRootView(),0);
			return list.get(position).getRootView();
		}		
		
	}
	
}
