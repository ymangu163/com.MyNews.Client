package com.ymangu.mynews.newspages;


import java.util.ArrayList;
import java.util.HashSet;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.utils.SharePrefUtil;

public class ItemNewsPage extends BasePage {



	public ItemNewsPage(Context context, String url) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		

	
		return null;
	}

	@Override
	public void initData() {

	}
	

	@Override
	protected void processClick(View v) {

	}



	

}
