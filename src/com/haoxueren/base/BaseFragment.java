package com.haoxueren.base;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxueren.utils.DbUtils;
import com.haoxueren.utils.MyConstants;

public class BaseFragment extends Fragment
{
	protected DbUtils dbUtils;
	protected List<String> list;
	private SharedPreferences preferences;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		TextView textView = new TextView(getActivity());
		String simpleName = this.getClass().getSimpleName();
		textView.setText("这是" + simpleName);
		textView.setTextColor(0xffffffff);
		return textView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 这是初始化数据的方法；
	 */
	protected void initData()
	{
		// 实例化数据库工具类对象；
		dbUtils = new DbUtils(getActivity());
		list = new ArrayList<String>();
		dbUtils.getMottoList(list);
		preferences = getActivity().getPreferences(0);
		initParam();
	}

	/**
	 * 这是初始化设置参数的方法；
	 */
	public void initParam()
	{
		MyConstants.INTERVAL_TIME = preferences.getInt("interval", 3000);
		MyConstants.CHARACTER_NUMBER = preferences.getInt("number", 15);
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

	/**
	 * 查找View布局中的View对象；
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(View view, int id)
	{
		return (T) view.findViewById(id);
	}

}
