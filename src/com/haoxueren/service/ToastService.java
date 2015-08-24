package com.haoxueren.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.haoxueren.receiver.ScreenOnReceiver;

/**
 * 本服务用来实现长期监听屏幕解锁广播；
 */
public class ToastService extends Service
{
	private ScreenOnReceiver receiver;

	@Override
	public void onCreate()
	{
		super.onCreate();
		registerScreenReceiver(this);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		this.unregisterReceiver(receiver);
	}

	/**
	 * 这是注册广播接收者的方法；
	 */
	private void registerScreenReceiver(Context context)
	{
		receiver = new ScreenOnReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		this.registerReceiver(receiver, filter);
	}
}
