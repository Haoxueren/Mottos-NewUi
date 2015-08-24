package com.haoxueren.receiver;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.haoxueren.utils.DbUtils;

/**
 * @author Haoxueren
 *         手机屏幕解锁广播；
 */
public class ScreenOnReceiver extends BroadcastReceiver
{
	protected List<String> list;
	private DbUtils dbUtils;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_SCREEN_ON))
		{
			if (dbUtils == null || list == null)
			{
				dbUtils = new DbUtils(context);
				list = new ArrayList<String>();
				dbUtils.getMottoList(list);
			}
			// 每小时更新一次数据；
			if (System.currentTimeMillis() % (1000 * 60 * 60) == 0)
			{
				dbUtils.getMottoList(list);
			}
			String motto = dbUtils.getRandomMotto(list);
			Toast.makeText(context, motto, Toast.LENGTH_LONG).show();
		}
	}
}
