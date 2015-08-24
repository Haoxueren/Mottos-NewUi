package com.haoxueren.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Button;
import cn.bmob.v3.listener.SaveListener;

import com.haoxueren.bean.Motto;
import com.haoxueren.database.MyDatabaseHelper;

public class DbUtils
{
	private Context context;
	private MyDatabaseHelper helper;

	// 通过构造传入上下文对象并初始化数据；
	public DbUtils(Context context)
	{
		this.context = context;
		helper = new MyDatabaseHelper(context);
	}

	/**
	 * 这是获取一个可写数据库的方法；
	 * 
	 * @return SQLiteDatabase数据库对象；
	 */
	public SQLiteDatabase writeDatabase()
	{
		return helper.getWritableDatabase();
	}

	/**
	 * 这是获取一个可读数据库的方法；
	 * 
	 * @return SQLiteDatabase数据库对象；
	 */
	public SQLiteDatabase readDatabase()
	{
		return helper.getReadableDatabase();
	}

	/**
	 * 这是向数据库中添加一条Motto的方法；
	 * 
	 * @return the row ID of the newly inserted row, or -1 if an error occurred；
	 */
	public long addMotto(String motto)
	{
		SQLiteDatabase database = writeDatabase();
		ContentValues values = new ContentValues();
		values.put(MyConstants.MOTTO, motto);
		long result = database.insert(MyConstants.TABLE, null, values);
		database.close();
		return result;
	}

	/**
	 * 这是执行指定的的sql语句的方法；
	 */
	public void executeSql(String sql)
	{
		SQLiteDatabase database = writeDatabase();
		database.execSQL(sql);
		database.close();
	}

	/**
	 * 这是按关键词搜索数据库内容的方法；
	 * 
	 * @return 符合条件的记录的集合；
	 */
	public List<String> queryMotto(String keyword)
	{
		SQLiteDatabase database = readDatabase();
		Cursor cursor = database.query(MyConstants.TABLE, new String[] { MyConstants.MOTTO, MyConstants.ID }, MyConstants.MOTTO + " like ?", new String[] { "%" + keyword + "%" }, null, null, null);
		List<String> mottos = new ArrayList<String>();
		while (cursor.moveToNext())
		{
			String id = cursor.getString(cursor.getColumnIndex(MyConstants.ID));
			String motto = cursor.getString(cursor.getColumnIndex(MyConstants.MOTTO));
			// 对个位id进行加0处理；
			id = (id.length() == 1) ? 0 + id : id;
			String result = id + "." + motto;
			mottos.add(result);
		}
		return mottos;
	}

	/**
	 * 这是查询数据库内容的方法；
	 * 
	 * @return 符合条件的记录的集合；
	 */
	public List<String> search(String sql)
	{
		List<String> mottos = new ArrayList<String>();
		SQLiteDatabase database = readDatabase();
		Cursor cursor = database.rawQuery(sql, null);
		cursor.moveToPosition(cursor.getCount());
		while (cursor.moveToPrevious())
		{
			String id = cursor.getString(cursor.getColumnIndex(MyConstants.ID));
			String motto = cursor.getString(cursor.getColumnIndex(MyConstants.MOTTO));
			// 对个位id进行加0处理；
			id = (id.length() == 1) ? 0 + id : id;
			String result = id + "." + motto;
			mottos.add(result);
		}
		return mottos;
	}

	/**
	 * 这是修改数据库记录的方法；
	 * 
	 * @return 受影响的行数；
	 */
	public int updateMotto(String id, String value)
	{
		SQLiteDatabase database = writeDatabase();
		ContentValues values = new ContentValues();
		values.put(MyConstants.MOTTO, value);
		int affectRows = database.update(MyConstants.TABLE, values, "id=?", new String[] { id });
		return affectRows;
	}

	/**
	 * 这是从数据库中读取数据的方法；<br>
	 * 一次性将数据全部读取出来；<br>
	 * 并把游标集中的数据添加到ArrayList集合中；<br>
	 * 记得关闭游标集和数据库连接；
	 */
	public List<String> getMottoList(List<String> list)
	{
		SQLiteDatabase database = readDatabase();
		Cursor cursor = database.query(MyConstants.TABLE, new String[] { MyConstants.MOTTO }, null, null, null, null, null);
		while (cursor.moveToNext())
		{
			String motto = cursor.getString(cursor.getColumnIndex(MyConstants.MOTTO));
			list.add(motto);
		}
		cursor.close();
		database.close();
		return list;
	}

	/**
	 * 从集合中随机取出一条数据；
	 * 
	 * @return 获取到的数据或提示信息；
	 */
	public String getRandomMotto(List<String> list)
	{
		Random random = new Random();
		int n = list.size();
		if (n == 0)
		{
			return MyConstants.NO_DATA;
		}
		int position = random.nextInt(n);
		String motto = list.get(position);
		if (!TextUtils.isEmpty(motto))
		{
			return motto;
		}
		return MyConstants.POSITION_NOT_EXIST;
	}

	/**
	 * 把本地数据库同步到云端；<br>
	 */
	// 已同步到服务器的记录数；
	int uploadCount = 0;

	public void uploadData(final Button button)
	{
		// 把按钮置为不可用，防止用户重复点击；
		button.setEnabled(false);
		button.setText("正在准备数据");

		SQLiteDatabase database = readDatabase();
		final Cursor cursor = database.query(MyConstants.TABLE, new String[] { MyConstants.MOTTO }, null, null, null, null, null);
		while (cursor.moveToNext())
		{
			// 从数据库把所有的数据都查询出来；
			String mottoStr = cursor.getString(cursor.getColumnIndex(MyConstants.MOTTO));

			final Motto mottoBean = new Motto(MyConstants.username, mottoStr);
			mottoBean.save(context, new SaveListener()
			{
				@Override
				public void onSuccess()
				{
					uploadCount++;
					button.setText(uploadCount + "/" + cursor.getCount() + "：" + mottoBean.motto);
					if (uploadCount == cursor.getCount())
					{
						button.setText("数据同步完毕");
						button.setEnabled(true);
						uploadCount = 0;
					}
				}

				@Override
				public void onFailure(int arg0, String arg1)
				{
					uploadCount++;
					button.setText(arg0 + "：" + arg1);
					if (uploadCount == cursor.getCount())
					{
						button.setText("数据同步完毕");
						button.setEnabled(true);
						uploadCount = 0;
					}
					MyLog.info(uploadCount + "/" + cursor.getCount());
				}
			});
		}
		cursor.close();
		database.close();
	}
}
