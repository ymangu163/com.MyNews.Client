package com.ymangu.mynews.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ymangu.mynews.R;

/*
 * . 1 建Fragment 类时 要导support.v4的包
 * 	2 用MenuFragment 替换 slidingMenu中的内容
 * 3 slidingMenu中的内容可以用 TextView，也可以用ListView；用TextView的话就写死了
 * 4.  Fragment 生命周期：
 * 		onCreate() --->onCreateView() -->onActivityCreated().
 */
public class MenuFragment extends Fragment {

	private View view;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 得到菜单中的 ListView
		ListView list_view = (ListView) view.findViewById(R.id.list_view);   
		/*
		 * . 只是Text 的话就用一个简单的 ArrayAdapter
		 *  参数1： Context
		 *  参数2：Layout xml文件
		 *  参数3： Layout 中的控件id
		 *  参数4：数据源
		 */
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				initData());
		list_view.setAdapter(adapter);
		
	}

	private List<String> initData() {
		List<String> list = new ArrayList<String>();
		list.add("fragment1");
		list.add("fragment2");
		list.add("fragment3");
		list.add("fragment4");
		list.add("fragment5");
		return list;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// 相当于 setContentView()
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.menu_list_view, null);	
		
		return view;   // 把View 返回回去
	}

}
