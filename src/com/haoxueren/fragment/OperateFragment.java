package com.haoxueren.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.haoxueren.base.BaseFragment;
import com.haoxueren.bean.Motto;
import com.haoxueren.motto.R;
import com.haoxueren.utils.MyConstants;

public class OperateFragment extends BaseFragment implements OnClickListener
{
	private View view;
	private EditText et_sql;
	private EditText et_motto;
	private Button bt_execute, bt_insert;
	private TextView tv_msg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_operate, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		initView();

		initListener();
	}

	private void initListener()
	{
		bt_execute.setOnClickListener(this);
		bt_insert.setOnClickListener(this);
	}

	private void initView()
	{
		et_sql = (EditText) view.findViewById(R.id.et_sql);
		et_motto = (EditText) view.findViewById(R.id.et_motto);

		bt_execute = (Button) view.findViewById(R.id.bt_operate);
		bt_insert = (Button) view.findViewById(R.id.bt_insert);
		tv_msg = (TextView) view.findViewById(R.id.tv_feedback);
	}

	@Override
	public void onClick(View view)
	{
		try
		{
			tv_msg.setText("");
			String content = et_motto.getText().toString().trim();
			String sql = et_sql.getText().toString().trim();
			if (view.getId() == R.id.bt_operate)
			{
				dbUtils.executeSql(sql);
				tv_msg.setText("操作成功：" + sql);
			} else if (view.getId() == R.id.bt_insert)
			{
				long rowId = dbUtils.addMotto(content);
				String html = "rowId = " + addColor(rowId + "  ", "red") + content;
				tv_msg.setText(Html.fromHtml(html));

				// 保存数据到服务器数据库；
				Motto motto = new Motto(MyConstants.username, content);
				motto.save(getActivity(), new SaveListener()
				{
					@Override
					public void onSuccess()
					{
						Toast.makeText(getActivity(), "数据保存成功", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFailure(int arg0, String arg1)
					{
						Toast.makeText(getActivity(), arg0 + ": " + arg1, Toast.LENGTH_SHORT).show();
					}
				});

			}
			// 清空输入框中的内容；
			et_sql.setText(null);
		} catch (Throwable throwable)
		{
			tv_msg.setText(throwable.getMessage());
		}
	}

	/**
	 * 把文本转换成带颜色的html代码；
	 */
	public String addColor(String text, String rgb)
	{
		return "<font color=" + rgb + ">" + text + "</font>";
	}

}
