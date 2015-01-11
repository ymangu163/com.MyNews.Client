package com.ymangu.mynews;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.view.Window;

/**
 * . Activity 与Fragment 方式使用 SlidingMenu的不同
① 得到SlidingMenu对象的区别：
    	Activity: 获得 SlidingMenu对象是 new SlidingMenu()
    	Fragment: Activity 继承自SlidingFragmentActivity,通过 getSlidingMenu得到 SlidingMenu
 ② 设置SlidingMenu使用的布局的区别：
 	Activity:  sm.setMenu(layout  res  id)
 	Fragment:  setBehindContentView(R.layout.menu_frame);  FramLayout 用来装载Fragment 
     继承自 SlidingFragmentActivity 使用的布局文件是一个全屏的FramLayout,
 	 slidingMenu 的主页面的内容也是Framement来显示；
 **/
public class MainActivity extends SlidingFragmentActivity {
	
	private SlidingMenu sm;

	/**
     * 1 得到滑动菜单
     * 2 设置滑动菜单是在左边出来还是右边出来
     * 3 设置滑动菜单出来之后，内容页，显示的剩余宽度
     * 4 设置滑动菜单的阴影 设置阴影，阴影需要在开始的时候，特别暗，慢慢的变淡
     * 5 设置阴影的宽度
     * 6 设置滑动菜单的范围
     */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  // 无Title
		
		setBehindContentView(R.layout.menu_lay); // slidingMenu的布局
		setContentView(R.layout.activity_main);  // 内容布局
		
		sm = getSlidingMenu(); // 1 得到滑动菜单
		// 2设置滑动菜单是在左边出来还是右边出来
		// 参数可以设置左边LEFT，也可以设置右边RIGHT ，还能设置左右LEFT_RIGHT
		sm.setMode(SlidingMenu.LEFT);
		// 3设置滑动菜单出来之后，内容页，显示的剩余宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 4 设置滑动菜单的阴影 设置阴影，阴影需要在开始的时候，特别暗，慢慢的变淡
		sm.setShadowDrawable(R.drawable.shadow);
		// 5 设置阴影的宽度
		sm.setShadowWidth(R.dimen.shadow_width);
		// 6 设置滑动菜单的范围
		// 第一个参数 SlidingMenu.TOUCHMODE_FULLSCREEN 可以全屏滑动
		// 第二个参数 SlidingMenu.TOUCHMODE_MARGIN 只能在边沿滑动
		// 第三个参数 SlidingMenu.TOUCHMODE_NONE 不能滑动
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
	}
}
