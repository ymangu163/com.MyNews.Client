package com.ymangu.mynews.bean;

import java.util.List;

public class NewsCenterBean {
	
	// 数组就定义成一个集合
	public List<CenterDataBean> data; 
	// extend 中的数据全是 int型
	public List<Integer> extend;
	public int retcode;
	

	/*
	 * . 1   集合里面只能放对象，不能放基础数据类型
	 * 2 定义成 static 是它独特的妙处
	 * 3  CenterDataBean 隶属于data ;属于data 的数据都在这里面定义
	 */
	public static class CenterDataBean{
		public List<CenterDataChildrenBean> children;
		public int id;
		public String title;
		public int type;
		public String url;
    	public String url1;
    	public String dayurl;
    	public String excurl;
    	public String weekurl;
	} 
	
	/*
	 * . CenterDataBean 中又有数组类型的元素，也定义成一个Bean
	 */
	public  static class CenterDataChildrenBean{
		public String id;
    	public String title;
    	public String type;
    	public String url;
		
	}
	

}
