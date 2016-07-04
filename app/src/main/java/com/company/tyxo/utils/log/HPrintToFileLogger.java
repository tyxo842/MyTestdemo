package com.company.tyxo.utils.log;

import android.os.Build;
import android.os.Environment;

import com.company.tyxo.base.MyApp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 打印日志到sdcard的日志类
 */
public class HPrintToFileLogger implements HILogger {

	 
	private String mPath;
	private Writer mWriter;

	private static final SimpleDateFormat TIMESTAMP_FMT = new SimpleDateFormat(
			"[yyyy-MM-dd HH:mm:ss] ");
	private String basePath = "";
	private static String LOG_DIR = "log";
	private static String BASE_FILENAME = "ta.log";
	private File logDir;

	public HPrintToFileLogger() {

	}

	public void open() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			logDir = MyApp.getInstance().getExternalCacheDir();
		} else {
			String cacheDir = "/Android/data/"
					+  getPackageName()
					+ "/cache/";
			logDir = new File(Environment.getExternalStorageDirectory()
					.getPath() + cacheDir);
		}
		if (!logDir.exists()) {
			logDir.mkdirs();
			// do not allow media scan
			try {
				new File(logDir, ".nomedia").createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		basePath = logDir.getAbsolutePath() + "/" + BASE_FILENAME;
		try {
			File file = new File(basePath + "-" + getCurrentTimeString());
			mPath = file.getAbsolutePath();
			mWriter = new BufferedWriter(new FileWriter(mPath), 2048);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getCurrentTimeString() {
		Date now = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(now);
	}

	public String getPath() {
		return mPath;
	}

	@Override
	public void d(String tag, String message) {
		println(HConfig.LOG_DEBUG, tag, message);
	}

	@Override
	public void e(String tag, String message) {
		println(HConfig.LOG_ERROR, tag, message);
	}

	@Override
	public void i(String tag, String message) {
		println(HConfig.LOG_INFO, tag, message);
	}

	@Override
	public void v(String tag, String message) {
		println(HConfig.LOG_VERBOSE, tag, message);
	}

	@Override
	public void w(String tag, String message) {
		println(HConfig.LOG_WARN, tag, message);
	}
	
	@Override
	public void err(String tag, String message) {
		println(HConfig.LOG_WARN, tag, message);
	}
	
	@Override
	public void out(String tag, String message) {
		println(HConfig.LOG_WARN, tag, message);
	}

	@Override
	public void println(int priority, String tag, String message) {
		String printMessage = "";
		switch (priority) {
		case HConfig.LOG_VERBOSE:
			printMessage = "[V]|"
					+ tag
					+ "|"
					+  getPackageName() + "|" + message;
			break;
		case HConfig.LOG_DEBUG:
			printMessage = "[D]|"
					+ tag
					+ "|"
					+  getPackageName() + "|" + message;
			break;
		case HConfig.LOG_INFO:
			printMessage = "[I]|"
					+ tag
					+ "|"
					+  getPackageName() + "|" + message;
			break;
		case HConfig.LOG_WARN:
			printMessage = "[W]|"
					+ tag
					+ "|"
					+  getPackageName() + "|" + message;
			break;
		case HConfig.LOG_ERROR:
			printMessage = "[E]|"
					+ tag
					+ "|"
					+  getPackageName() + "|" + message;
			break;
			
		case HConfig.SYS_OUT:
			printMessage = "[OUT]|"
					+ tag
					+ "|"
					+  getPackageName() + "|" + message;
			break;
			
		case HConfig.SYS_ERR:
			printMessage = "[ERR]|"
					+ tag
					+ "|"
					+  getPackageName() + "|" + message;
			break;
		default:

			break;
		}
		println(printMessage);

	}
	
	
	

	public void println(String message) {
		try {
			mWriter.write(TIMESTAMP_FMT.format(new Date()));
			mWriter.write(message);
			mWriter.write('\n');
			mWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private String  getPackageName() {
		return MyApp.getInstance().getPackageName();
	}

	public void close() {
		try {
			mWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
