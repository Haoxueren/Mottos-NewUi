package com.haoxueren.bean;

import cn.bmob.v3.BmobObject;

public class Motto extends BmobObject
{
	public String username;
	public String motto;
	public Boolean delete;

	public Motto(String username, String mymotto)
	{
		this.username = username;
		this.motto = mymotto;
		this.delete=false;
		
		boolean isTest = true;
		if (isTest)
		{
			this.setTableName(this.getClass().getSimpleName() + "Test");
		}
	}

}
