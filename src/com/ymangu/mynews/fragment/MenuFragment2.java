package com.ymangu.mynews.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BaseFragment;
import com.ymangu.mynews.base.HMBaseAdapter;
/*
 * . 因为多个Page 对应的菜单不一样，最简单的方法就是 建对应的 xxMenuFragment 去替换SlidingMenu的id;
 *   还有更简洁的方法是 建立多个 ListView，迭在同一个FrameLayout 中，控件哪个 Visible 就可以。
 */
public class MenuFragment2 extends BaseFragment implements OnItemClickListener {
	@ViewInject(R.id.lv_menu_news_center)
	private ListView lv_menu_news_center;
	
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.left_menu_layout, null);
		ViewUtils.inject(this, view);   // 在 Fragment 中注入view
		// 设置 ListView Item的点击事件
		lv_menu_news_center.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {

	}

	private List<String> menuList=new ArrayList<String>();
	private HMMenuAdpter adapter;
	/*
	 * . 初始化菜单数据
	 */
	public void initMenu(List<String> menuNewCenterList) {
		menuList.clear();   // 先清空一下，保证它没有杂质
		menuList.addAll(menuNewCenterList);
		// 提高性能
		if(adapter==null){
			adapter = new HMMenuAdpter(context,menuList);
			lv_menu_news_center.setAdapter(adapter);			
		}else{
			adapter.notifyDataSetChanged();   //如果存在的话，只需要刷新一下就好了
		}
		adapter.setSelectedPosition(0);  // 调用指定位置的方法
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
			tv.setText(menuList.get(position));
			
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
		adapter.setSelectedPosition(position);
		sm.toggle();
		
	}


	
}
