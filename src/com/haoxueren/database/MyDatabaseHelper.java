package com.haoxueren.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 这个类用来创建SQLite数据库；
 */
public class MyDatabaseHelper extends SQLiteOpenHelper
{
	/**
	 * @param context
	 *                上下文对象；
	 * @param name
	 *                数据库的名称；
	 * @param factory
	 *                游标工厂，null表示默认的游标工厂；
	 * @param version
	 */
	public MyDatabaseHelper(Context context)
	{
		super(context, "Haoxueren", null, 2);
	}

	/**
	 * 数据库被创建时执行此方法；可以在这里初始化数据库的表；
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		/*
		 * 初始化数据库中的表；
		 */
		String sql = "create table mottos (id integer primary key autoincrement,motto varchar(99) unique not null,add_time TimeStamp DEFAULT (datetime('now','localtime')))";
		db.execSQL(sql);
	}

	/**
	 * 数据库更新时执行些方法；
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		System.out.println("数据库升级啦！");
	}

}
