package com.haoxueren.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志工具类，用来管理日志；<br>
 * 项目开发完毕，一定要记得关闭日志；
 */
public class MyLog
{
	/** true表示打开日志，false表示关闭日志 */
	public final static boolean isOpne = true;

	/**
	 * 日志的标签为Haoxueren；
	 */
	public static void info(String message)
	{
		if (isOpne)
		{
			Log.i("Haoxueren", TextUtils.isEmpty(message) ? "null" : message);
		}
	}

	/**
	 * 日志标签为Haoxueren；
	 */
	public static void error(String message)
	{
		if (isOpne)
		{
			Log.e("Haoxueren", TextUtils.isEmpty(message) ? "null" : message);
		}
	}
}
