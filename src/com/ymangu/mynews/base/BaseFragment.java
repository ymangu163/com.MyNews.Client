package com.ymangu.mynews.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
	 * . fragment 不能独立存在，是依赖Activity的。 
	fragment生命周期:  与Activity生命周期对应关系：
	created --  >onAttach(),onCreate(),onCreateView(),onActivityCreated().
	started -- > onStart();
	resumed -- > onResume();
	paused -- > onPause();
	stopped -- > onStop();
	destroyed -- > onDestroyView(),onDestroy(),onDetach().
	 * 
	 *  Fragment 方法抽取： 把相同功能的代码丢到父类中
	 *  *  */

public abstract class BaseFragment extends Fragment {
	 
	public View view;
	public Context context;

	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();   // 把上下文传下去
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = initView(inflater);	
		
		return view;
	}

	// 初始化 View ；抽象方法  ，由子类实现
	public  abstract View initView(LayoutInflater inflater);
	// 初始化数据
	public  abstract  void initData(Bundle savedInstanceState);	

}
