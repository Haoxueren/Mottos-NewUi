package com.haoxueren.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

import com.haoxueren.base.BaseFragment;
import com.haoxueren.bean.MyUser;
import com.haoxueren.motto.R;
import com.haoxueren.utils.MyLog;

public class AccountFragment extends BaseFragment implements OnClickListener
{
	private EditText et_username;
	private EditText et_password;
	private Button bt_upload;
	private Button bt_regist;
	private Button bt_login;

	/**
	 * 设置Fragment的布局；
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_account, null);
	}

	/**
	 * 初始化数据；
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initView(getView());
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.bt_regist:
			MyLog.info("正在注册");
			final MyUser user = new MyUser();
			user.setUsername(et_username.getText().toString().trim());
			user.setPassword(et_password.getText().toString().trim());
			user.signUp(getActivity(), new SaveListener()
			{
				@Override
				public void onSuccess()
				{
					Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();

					et_username.setEnabled(false);
					et_password.setEnabled(false);
					bt_regist.setText("已注册");
					bt_regist.setEnabled(false);
				}

				@Override
				public void onFailure(int code, String msg)
				{
					Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
				}
			});
			break;
		case R.id.bt_login:
			BmobUser.loginByAccount(getActivity(), et_username.getText().toString(), et_password.getText().toString(), new LogInListener<MyUser>()
			{
				@Override
				public void done(MyUser user, BmobException e)
				{
					if (user != null)
					{
						MyLog.info("登录成功：" + user.getUsername());
						et_username.setEnabled(false);
						et_password.setEnabled(false);
						bt_regist.setText("已注册");
						bt_login.setText("已登录");
						bt_login.setEnabled(false);
						bt_regist.setEnabled(false);
					} else
					{
						Toast.makeText(getActivity(), "登录失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			});
			break;

		case R.id.bt_upload:
			BmobUser.loginByAccount(getActivity(), et_username.getText().toString(), et_password.getText().toString(), new LogInListener<MyUser>()
			{
				@Override
				public void done(MyUser user, BmobException e)
				{
					if (user != null)
					{
						MyLog.info("登录成功：" + user.getUsername());
						et_username.setEnabled(false);
						et_password.setEnabled(false);
						dbUtils.uploadData(bt_upload);
					} else
					{
						Toast.makeText(getActivity(), "登录失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			});
			break;
		case R.id.bt_download:
			Toast.makeText(getActivity(), "功能尚未实现", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	private void initView(View view)
	{
		et_username = findView(view, R.id.et_username);
		et_password = findView(view, R.id.et_password);

		bt_upload = findView(view, R.id.bt_upload);
		bt_upload.setOnClickListener(this);

		Button downlaod = findView(view, R.id.bt_download);
		downlaod.setOnClickListener(this);

		bt_regist = findView(view, R.id.bt_regist);
		bt_regist.setOnClickListener(this);

		bt_login = findView(view, R.id.bt_login);
		bt_login.setOnClickListener(this);
	}

}
