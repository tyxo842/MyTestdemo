package com.company.tyxo.utils.log;

import android.util.Log;

/**
 * 打印日志到LogCat的日志类
 */
public class HPrintToLogCatLogger implements HILogger
{
	@Override
	public void d(String tag, String message)
	{
		Log.d(tag, message);
	}

	@Override
	public void e(String tag, String message)
	{
		Log.e(tag, message);
	}

	@Override
	public void i(String tag, String message)
	{
		Log.i(tag, message);
	}

	@Override
	public void v(String tag, String message)
	{
		Log.v(tag, message);
	}

	@Override
	public void w(String tag, String message)
	{
		Log.w(tag, message);
	}

	@Override
	public void err(String tag, String message) {
		System.out.println(tag+" : "+message);
	}
	
	@Override
	public void out(String tag, String message) {
		System.err.println(tag+" : "+message);
	}
	
	
	@Override
	public void println(int priority, String tag, String message)
	{
		Log.println(priority, tag, message);
	}

	@Override
	public void open()
	{

	}

	@Override
	public void close()
	{

	}
}
