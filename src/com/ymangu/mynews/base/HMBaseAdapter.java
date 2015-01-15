package com.ymangu.mynews.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/*
 * 记住抽取的话就是要解决 Data 和 View.
 *  把 HMBaseAdapter 定义为 abstract，让子类去实现 getView()方法
 */
public abstract class HMBaseAdapter<T, Q> extends BaseAdapter {

	public Context context;
	public List<T>  list;  // T只是一个标志，可以随便定，表示范型
	public Q view;
	
	public HMBaseAdapter(Context context, List<T> list, Q view) {
		this.context = context;
		this.list = list;
		this.view = view;
	}
	
	public HMBaseAdapter(Context context, List<T> list) {
		this.context=context;
		this.list=list;
	}
	
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}



}
