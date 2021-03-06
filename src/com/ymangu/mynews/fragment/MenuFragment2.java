package com.ymangu.mynews.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.MainActivity;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BaseFragment;
import com.ymangu.mynews.base.HMBaseAdapter;
import com.ymangu.mynews.home.NewsCenterPage;
/*
 * . 因为多个Page 对应的菜单不一样，最简单的方法就是 建对应的 xxMenuFragment 去替换SlidingMenu的id;
 *   还有更简洁的方法是 建立多个 ListView，迭在同一个FrameLayout 中，控件哪个 Visible 就可以。
 */
public class MenuFragment2 extends BaseFragment implements OnItemClickListener {
	public static final int NEWS_CENTER = 1;   // 新闻中心是1
	public static int newsCenterPosition = 0;
	
	@ViewInject(R.id.lv_menu_news_center)
	private ListView newsCenterclassifyLv;   // 新闻中心
	@ViewInject(R.id.lv_menu_smart_service)
	private ListView smartServiceclassifyLv;  //智慧城市
	@ViewInject(R.id.lv_menu_govaffairs)
	private ListView govAffairsclassifyLv;  // 政要
	@ViewInject(R.id.tv_menu_classify)
	private TextView classifyTv;
	
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.left_menu_layout, null);
		ViewUtils.inject(this, view);   // 在 Fragment 中注入view
		// 设置 ListView Item的点击事件
		newsCenterclassifyLv.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
				mainActivity = (MainActivity)context;			
				newsCenterPage = new NewsCenterPage(context);
				switchMenu(menuType);
	}

	// 切换菜单
	public void switchMenu(int type) {
		newsCenterclassifyLv.setVisibility(View.GONE);
		smartServiceclassifyLv.setVisibility(View.GONE);
		govAffairsclassifyLv.setVisibility(View.GONE);
		switch (type) {
		case NEWS_CENTER:
			newsCenterclassifyLv.setVisibility(View.VISIBLE);  //设置新闻中心的可见
			fragManager = mainActivity.getSupportFragmentManager();
			newsCenterFragment = ((HomeFragment) fragManager
					.findFragmentByTag("Home")).getNewsCenterPage();    // 得到新闻中心的Page
			classifyTv.setText("分类");
			if(adapter==null){
				adapter = new HMMenuAdpter(context,newsMenuList);
				newsCenterclassifyLv.setAdapter(adapter);		
			}else{
				adapter.notifyDataSetChanged();   //如果存在的话，只需要刷新一下就好了
			}
			adapter.setSelectedPosition(newsCenterPosition);	
			break;

		default:
			break;
			
		}
		
		
		
	}

	private List<String> newsMenuList=new ArrayList<String>();
	private HMMenuAdpter adapter;
	private MainActivity mainActivity;
	private int menuType = 0;
	private FragmentManager fragManager;
	private NewsCenterPage newsCenterFragment;
	private NewsCenterPage newsCenterPage;
	
	/*
	 * . 初始化菜单数据
	 */
	public void initMenu(List<String> menuNewCenterList) {
		newsMenuList.clear();   // 先清空一下，保证它没有杂质
		newsMenuList.addAll(menuNewCenterList);
		// 提高性能
		if(adapter==null){
			adapter = new HMMenuAdpter(context,newsMenuList);
			newsCenterclassifyLv.setAdapter(adapter);			
		}else{
			adapter.notifyDataSetChanged();   //如果存在的话，只需要刷新一下就好了
		}
		adapter.setSelectedPosition(newsCenterPosition);  // 调用指定位置的方法
	}

	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("newsCenter_position", newsCenterPosition);  //记录slidingMenu上次显示的位置
		super.onSaveInstanceState(outState);
		
		
		
		
	}



	/*
	 *  使用黑马 高大上的 adapter
	 */
	class HMMenuAdpter extends HMBaseAdapter{
		private int selectedPosition=0; // 打个标记，选中的项，默认选中第1项
		
		public HMMenuAdpter(Context context, List list) {
			super(context, list);   // 在父类是已经初始化了
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
			notifyDataSetInvalidated();

		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=View.inflate(context, R.layout.layout_item_menu, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.tv_menu_item);
			ImageView iv = (ImageView) convertView	.findViewById(R.id.iv_menu_item);
			tv.setText(newsMenuList.get(position));
			
			// view 渲染的时候，如果是选中的项就变成不一样的颜色
			if (selectedPosition == position) {
				convertView.setSelected(true);
				convertView.setPressed(true);
				// 增加背景
				convertView.setBackgroundResource(R.drawable.menu_item_bg_select);
				tv.setTextColor(context.getResources().getColor(
						R.color.menu_item_text_color));
				iv.setBackgroundResource(R.drawable.menu_arr_select);
			} else {
				convertView.setSelected(false);
				convertView.setPressed(false);
				convertView.setBackgroundColor(Color.TRANSPARENT);
				iv.setBackgroundResource(R.drawable.menu_arr_normal);
				tv.setTextColor(context.getResources().getColor(R.color.white));
			}
			return convertView;
		}
		
		
		
	}	
	
	
   // ListView 的 Item 点击事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(position==newsCenterPosition){  // 点的是新闻才收起
			sm.toggle();			
		}
		adapter.setSelectedPosition(position);
		newsCenterPage.switchFragment(position);   // 点击ListView时，实现跳转
		
		
		
	}

	public void setMenuType(int menuType) {
		this.menuType=menuType;
		switchMenu(menuType);
		
	}


	
}
