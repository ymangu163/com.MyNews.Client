package com.ymangu.mynews.fragment;

import com.ymangu.mynews.R;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends BaseFragment {

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_home, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {

	}

}
