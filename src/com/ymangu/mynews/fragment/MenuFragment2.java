package com.ymangu.mynews.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BaseFragment;
/*
 * . 因为多个Page 对应的菜单不一样，最简单的方法就是 建对应的 xxMenuFragment 去替换SlidingMenu的id;
 *   还有更简洁的方法是 建立多个 ListView，迭在同一个FrameLayout 中，控件哪个 Visible 就可以。
 */
public class MenuFragment2 extends BaseFragment {
	@ViewInject(R.id.lv_menu_news_center)
	private ListView lv_menu_news_center;
	
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.left_menu_layout, null);
		ViewUtils.inject(this, view);   // 在 Fragment 中注入view
		
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {

	}

	private List<String> menuList=new ArrayList<String>();
	/*
	 * . 初始化菜单数据
	 */
	public void initMenu(List<String> menuNewCenterList) {
		menuList.clear();   // 先清空一下，保证它没有杂质
		menuList.addAll(menuNewCenterList);
		
		// 拿到数据源之后，用 Adapter 去适配 ListView.
		MenuAdapter adapter=new MenuAdapter(context,menuList);
		lv_menu_news_center.setAdapter(adapter);
		
	}

	class MenuAdapter extends BaseAdapter{
		private Context context;
		private List<String> menuList;
		public MenuAdapter(Context context, List<String> menuList) {
			this.context=context;
			this.menuList=menuList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return menuList.size();
		}

		@Override
		public Object getItem(int position) {
			return menuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=View.inflate(context, R.layout.layout_item_menu, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.tv_menu_item);
			ImageView iv = (ImageView) convertView	.findViewById(R.id.iv_menu_item);
			tv.setText(menuList.get(position));
			
			
			return convertView;
		}
		
		
	}
	
	
}
