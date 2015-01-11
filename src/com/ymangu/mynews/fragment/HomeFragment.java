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

import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BaseFragment;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.home.FunctionPage;
import com.ymangu.mynews.home.GovAffairsPage;
import com.ymangu.mynews.home.NewsCenterPage;
import com.ymangu.mynews.home.SettingPage;
import com.ymangu.mynews.home.SmartServicePage;
import com.ymangu.mynews.view.LazyViewPager;

public class HomeFragment extends BaseFragment {

	private View view;
	private LazyViewPager viewPager;
	private RadioGroup radio_group;

	@Override
	public View initView(LayoutInflater inflater) {
		// xml 转换成 View
		view = inflater.inflate(R.layout.frag_home, null);
		//  得到 LazyViewPager 
		viewPager = (LazyViewPager) view.findViewById(R.id.viewpager);
		// 得到 RadioGroup
		radio_group = (RadioGroup) view.findViewById(R.id.main_radio);
		
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
