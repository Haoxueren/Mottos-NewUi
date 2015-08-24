package com.haoxueren.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import cn.bmob.v3.Bmob;

import com.haoxueren.base.MyActivity;
import com.haoxueren.fragment.AccountFragment;
import com.haoxueren.fragment.OperateFragment;
import com.haoxueren.fragment.SearchFragment;
import com.haoxueren.fragment.ShowFragment;
import com.haoxueren.motto.R;
import com.haoxueren.service.ToastService;
import com.haoxueren.tab.PagerSlidingTabStrip;

/**
 * @author 好学人
 */
public class MainActivity extends MyActivity
{
	private String[] titles;
	private Fragment[] fragments;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// fits the system window；
		fitsSystemWindows(this);

		initApp();

		initData();

		initViewPager();

	}

	/*********************** 【以下是封装方法区】 ***********************/

	/**
	 * 初始化数据；
	 */
	private void initData()
	{
		titles = new String[] { "首页", "操作", "搜索", "账户" };
		fragments = new Fragment[] { new ShowFragment(), new OperateFragment(), new SearchFragment(), new AccountFragment() };
	}

	/**
	 * 初始化APP正常使用所需要的数据；
	 */
	private void initApp()
	{
		// 初始化 Bmob SDK，注意传入APP KEY；
		Bmob.initialize(this, "6db95a073969000173ce02e61ac59611");

		// 启动后台监听屏幕解锁广播的服务；
		startMyService(this, ToastService.class);
	}

	/**
	 * 初始化顶部标签；
	 */
	private void initViewPager()
	{
		// 初始化ViewPager；
		ViewPager vp_main = findView(R.id.vp_main);
		PagerAdapter adapter = new MyAdapter(getSupportFragmentManager());
		vp_main.setAdapter(adapter);
		// 初始化ViewPagerIndicator；
		PagerSlidingTabStrip tabStrip = findView(R.id.tabs);
		tabStrip.setViewPager(vp_main);
	}

	/**
	 * 这是启动一个服务的方法；
	 */
	public void startMyService(Context context, Class<?> clazz)
	{
		Intent intent = new Intent(context, clazz);
		context.startService(intent);
	}

	class MyAdapter extends FragmentPagerAdapter
	{
		public MyAdapter(FragmentManager manager)
		{
			super(manager);
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			// 返回对应位置的标题；
			return titles[position];
		}

		@Override
		public Fragment getItem(int position)
		{
			// 返回对应位置的Fragment；
			return fragments[position];
		}

		@Override
		public int getCount()
		{
			// 返回ViewPager条目的数量；
			return Math.min(titles.length, fragments.length);
		}
	}

}
