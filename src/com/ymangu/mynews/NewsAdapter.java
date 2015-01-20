package com.ymangu.mynews;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.ymangu.mynews.base.HMBaseAdapter;
import com.ymangu.mynews.bean.NewsListBean.News;
import com.ymangu.mynews.utils.CommonUtil;
import com.ymangu.mynews.utils.Constants;
import com.ymangu.mynews.utils.SharePrefUtil;

public class NewsAdapter  extends HMBaseAdapter<News, ListView> {
	BitmapUtils bitmapUtil;
	int type;
	
	public NewsAdapter(Context context, List<News> list, int type) {
		super(context, list);
		bitmapUtil=new BitmapUtils(context);
		this.type=type;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		News news=list.get(position);  //从传过来的List(在父类中处理的)中得到News对象，
		if(convertView==null){
			holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.layout_news_item, null);// 载入布局
			holder.iv=(ImageView)convertView.findViewById(R.id.iv_img);
			holder.title=(TextView)convertView.findViewById(R.id.tv_title);
			holder.pub_date=(TextView)convertView.findViewById(R.id.tv_pub_date);
			holder.comment_count = (TextView) convertView	.findViewById(R.id.tv_comment_count);
			convertView.setTag(holder);			
	}else{
		
		holder=(ViewHolder)convertView.getTag();
	}		
		// 标记已阅的为不同颜色
		if(news.isRead){
			holder.title.setTextColor(context.getResources().getColor(R.color.news_item_has_read_textcolor));
		}else{
			holder.title.setTextColor(context.getResources().getColor(R.color.news_item_no_read_textcolor));
	
		}
		holder.title.setText(news.title);
		holder.pub_date.setText(news.pubdate);
		if (news.comment) {  // 有评论
			holder.comment_count.setVisibility(View.VISIBLE);
			if (news.commentcount > 0) {
				holder.comment_count.setText(news.commentcount + "");			
				
			}else{
				holder.comment_count.setText("");				
			}
		}else{
			holder.comment_count.setVisibility(View.INVISIBLE);			
		}
		
		if (type == 0) {
			if (TextUtils.isEmpty(news.listimage)) {
				holder.iv.setVisibility(View.GONE);
			} else{
				int read_model = SharePrefUtil.getInt(context,Constants.READ_MODEL, 1);
				switch (read_model) {
				case 1:
					int type = CommonUtil.isNetworkAvailable(context);
					if(type==1){
						holder.iv.setVisibility(View.VISIBLE);
						bitmapUtil.display(holder.iv, news.listimage);   // 使用 xUtils 的BitmapUtil 展示图片
					}else{
						holder.iv.setVisibility(View.GONE);
					}
					break;
				case 2:
					holder.iv.setVisibility(View.VISIBLE);
					bitmapUtil.display(holder.iv, news.listimage);
					break;
				case 3:
					holder.iv.setVisibility(View.GONE);
					break;

				default:
					break;
				
				}
			}
			
		}else{
			holder.iv.setVisibility(View.GONE);
			
		}
		
		
		
		return convertView;
	}
	
	
	class ViewHolder{
		ImageView iv;
		TextView title;
		TextView pub_date;
		TextView comment_count;		
		
	}

}
