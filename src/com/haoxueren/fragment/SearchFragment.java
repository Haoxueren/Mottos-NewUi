package com.haoxueren.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.haoxueren.base.BaseFragment;
import com.haoxueren.motto.R;

public class SearchFragment extends BaseFragment implements OnClickListener
{
	private View searchView;
	private EditText et_sql;
	private Button bt_search;
	private ListView lv_search;
	private TextView tv_msg;

	/**
	 * 设置Fragment的布局；
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		searchView = inflater.inflate(R.layout.fragment_search, null);
		return searchView;
	}

	/**
	 * 初始化数据；
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		initView();

		initData();

		initListener();
	}

	/**
	 * 执行按钮的点击事件；
	 */
	private void initListener()
	{
		bt_search.setOnClickListener(this);
	}

	private void initView()
	{
		et_sql = (EditText) searchView.findViewById(R.id.et_sql);
		bt_search = (Button) searchView.findViewById(R.id.bt_search);
		lv_search = (ListView) searchView.findViewById(R.id.lv_search);
		tv_msg = (TextView) searchView.findViewById(R.id.tv_feedback);
	}

	@Override
	protected void initData()
	{
		super.initData();
	}

	@Override
	public void onClick(View v)
	{
		try
		{
			tv_msg.setText("");
			// 执行sql命令；
			String keyWord = et_sql.getText().toString().trim();
			String sql = "select * from mottos where motto like '%" + keyWord + "%';";
			List<String> mottos = dbUtils.search(sql);
			postToListView(mottos);
			tv_msg.setText("操作成功！结果如下：");
		} catch (Throwable throwable)
		{
			tv_msg.setText(throwable.getMessage());
		}
	}

	/**
	 * 将查询结果显示到ListView中；
	 */
	private void postToListView(List<String> mottos)
	{
		ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_search, R.id.tv_item, mottos);
		lv_search.setAdapter(adapter);
	}

}
