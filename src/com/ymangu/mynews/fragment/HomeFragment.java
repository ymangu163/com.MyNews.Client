package com.ymangu.mynews.fragment;

import com.ymangu.mynews.R;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
   /*
   * . xml 是怎么转换成　View的呢？
   *  通过 XmlPULLParser 解析
   *  看源码小技巧：
   *   ① if 里的可以不看
   * 
 */
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	    // 将 xml　转换成　Ｖiew
		View view = inflater.inflate(R.layout.frag_home, null);   // 方式① 
		
//		View view1=LayoutInflater.from(getActivity()).inflate(R.layout.frag_home, null); // 方式②
		
//		LayoutInflater inflater2=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
//		View view2=inflater.inflate(R.layout.frag_home, null);  // 方式③
		/*
		 * . 方式 ① ② ③ 有什么区别呢？
		 *  方式① 是② 的一种简写，没有区别
		 *  ③是通过系统的服务，其它都是一样的　②封装了　③　
		 */
		
		return view;
	}

	
	
}
