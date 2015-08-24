package com.haoxueren.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MyActivity extends FragmentActivity
{
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		boolean checkSDK = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		if (checkSDK)
		{
			translucentMode(false, false);
			// fitsSystemWindows(this);
		}
	}

	/**
	 * 设置顶部状态栏和底部导航栏的透明样式；
	 */
	public void translucentMode(boolean status, boolean navigation)
	{
		if (status)
		{
			// Translucent status bar
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		if (navigation)
		{
			// Translucent navigation bar
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	/**
	 * 将当前Activity的界面设置为适应窗体；
	 */
	public void fitsSystemWindows(Activity activity)
	{
		ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
		viewGroup.getChildAt(0).setFitsSystemWindows(true);
	}

	/**
	 * 查找Activity中的View对象；
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id)
	{
		return (T) findViewById(id);
	}

	/*********************** 【以下是测量工具类】 ***********************/

	/**
	 * 把dp值转成px；
	 */
	protected int dp2px(Resources resources, float dp)
	{
		final float density = resources.getDisplayMetrics().density;
		return (int) (dp * density + 0.5f);
	}

	/**
	 * 把sp值转成px；
	 */
	protected int sp2px(Resources resources, float sp)
	{
		final float density = resources.getDisplayMetrics().scaledDensity;
		return (int) (sp * density);
	}

	/*********************** 【以下是Html转换工具类】 ***********************/

	/**
	 * 把文本转换成带颜色的html代码；
	 */
	public String addColor(String text, String rgb)
	{
		return "<font color=" + rgb + ">" + text + "</font>";
	}

}
