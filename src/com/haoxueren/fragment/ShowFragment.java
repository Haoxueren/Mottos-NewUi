package com.haoxueren.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoxueren.base.BaseFragment;
import com.haoxueren.motto.R;
import com.haoxueren.utils.MyConstants;

public class ShowFragment extends BaseFragment implements OnClickListener
{
	private View view;
	private TextView tv_motto;
	private RelativeLayout rl_show;
	private boolean isPlaying = true;

	/**
	 * 将ShowFragment设计成单例模式；
	 */
	private static ShowFragment showFragment;

	public ShowFragment()
	{
	}

	public static ShowFragment getInstance()
	{
		return showFragment == null ? new ShowFragment() : showFragment;
	}

	/**
	 * 更新界面的消息处理器；
	 */
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			String motto = (String) msg.obj;
			tv_motto.setText(motto);
			getMotto(MyConstants.INTERVAL_TIME);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 为当前Activity屏幕添加常亮标志；
		getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_show, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initView();
		initListener();
		getMotto(MyConstants.INTERVAL_TIME);
	}

	private void initListener()
	{
		tv_motto.setOnClickListener(this);
		rl_show.setOnClickListener(this);
	}

	/**
	 * 这是初始化View的方法；
	 */
	private void initView()
	{
		rl_show = (RelativeLayout) view.findViewById(R.id.rl_show);
		tv_motto = (TextView) view.findViewById(R.id.tv_motto);
		tv_motto.setText(MyConstants.DEFAULT_MOTTO);
	}

	/**
	 * 这是在子线程中获取一条motto的方法；
	 */
	public void getMotto(final long delayMillis)
	{
		handler.removeCallbacksAndMessages(null);
		new Thread()
		{
			public void run()
			{
				String motto = dbUtils.getRandomMotto(list);
				Message message = Message.obtain();
				message.obj = motto;
				handler.sendMessageDelayed(message, delayMillis);
			}
		}.start();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// 取消当前Activity的屏幕添加常亮标志；
		getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
		case R.id.tv_motto:
			if (isPlaying)
			{
				handler.removeCallbacksAndMessages(null);
				tv_motto.setTextColor(0xff0000ff);
				isPlaying = false;
			} else
			{
				getMotto(MyConstants.INTERVAL_TIME / 2);
				tv_motto.setTextColor(0xffffffff);
				isPlaying = true;
			}
			break;
		case R.id.rl_show:
			if (!isPlaying)
			{
				getMotto(MyConstants.INTERVAL_TIME / 2);
				tv_motto.setTextColor(0xffffffff);
				isPlaying = true;
			}
			break;
		}
	}

}
