package com.ymangu.mynews.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ymangu.mynews.MainActivity;
import com.ymangu.mynews.R;
import com.ymangu.mynews.base.BasePage;
import com.ymangu.mynews.intface.DownFlagInterface;

public class SmartServicePage  extends BasePage{

	public SmartServicePage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view=inflater.inflate(R.layout.share_btn, null);		
		TextView textView = (TextView)view.findViewById(R.id.text_share);
		Button btn=(Button)view.findViewById(R.id.share_btn);
		textView.setText("我是智能城市");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OnekeyShare oks = new OnekeyShare();
			//  分享时 Notification 的图标和文字
			oks.setNotification(R.drawable.ic_launcher, 
					context.getString(R.string.app_name));
			// address 是接收人地址，仅在信息和邮件使用
			oks.setAddress("12345678901");
			// title 标题，印象笔记、邮箱、信息、微信、人人网和 QQ 空间使用
			oks.setTitle(context.getString(R.string.share));
			// titleUrl 是标题的网络链接，仅在人人网和 QQ 空间使用
			oks.setTitleUrl("http://sharesdk.cn");
			// text 是分享文本，所有平台都需要这个字段
			oks.setText(context.getString(R.string.share_content));
			// imagePath 是图片的本地路径，Linked- In 以外的平台都支持此参数
//			oks.setImagePath(MainActivity.TEST_IMAGE);
			// imageUrl 是图片的网络路径，新浪微博、人人网、QQ 空间、
			//  微信、易信、Linked-In 支持此字段
			oks.setImageUrl("http://sharesdk.cn/ rest.png");
			//微信、易信中使用，表示视屏地址或网页地址
			oks.setUrl("http://sharesdk.cn");
			// appPath 是待分享应用程序的本地路劲，仅在微信中使用
//			oks.setAppPath(MainActivity.TEST_IMAGE);
			// comment 是我对这条分享的评论，仅在人人网和 QQ 空间使用
			oks.setComment(context.getString(R.string.share));
			// site 是分享此内容的网站名称，仅在 QQ 空间使用
			oks.setSite(context.getString(R.string.app_name));
			// siteUrl 是分享此内容的网站地址，仅在 QQ 空间使用
			oks.setSiteUrl("http://sharesdk.cn");
			// venueName 是分享社区名称，仅在 Foursquare 使用
			oks.setVenueName("Southeast in China");
			// venueDescription 是分享社区描述，仅在 Foursquare 使用
			oks.setVenueDescription("This is a beautiful place!");
			// latitude 是维度数据，仅在新浪微博、腾讯微博和 Foursquare 使用
			oks.setLatitude(23.122619f);
			// longitude 是经度数据，仅在新浪微博、腾讯微博和 Foursquare 使用
			oks.setLongitude(113.372338f);
			//  是否直接分享（true 则直接分享）
			oks.setSilent(true);
			//  指定分享平台，和 slient 一起使用可以直接分享到指定的平台
//			if (platform !=  null) {
//			oks.setPlatform(platform);
//			}
			//  去除注释可通过 OneKeyShareCallback 来捕获快捷分享的处理结果
			// oks.setCallback(new OneKeyShareCallback());
			//通过 OneKeyShareCallback 来修改不同平台分享的内容 
//			oks.setShareContentCustomizeCallback(
//					new ShareContentCustomizeDemo());
					oks.show(context);
				
				
				
			}
		});
		return view;
	}

	@Override
	public void initData() {
		
	}

	@Override
	protected void processClick(View v) {
		
	}

}
