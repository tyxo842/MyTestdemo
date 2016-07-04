package com.company.tyxo.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MyApp extends BaseApplication{

	private boolean isLogin;
	private static Object mCurrentUser;
	private static MyApp singleton;
	private static Context mContext;
	private static final String TAG = "MyApp";
	public static String AppName="ENT_PLAT_APP";
	public static final String USER_DATA_FILE = "user_data";
	private static final String TAG_FOR_LOGGER = "Logger";

	/**
	 * 初始化事件（重载）
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		singleton = this;
		mContext = getApplicationContext();

	}

	public static MyApp getInstance() {
		return singleton;
	}

	/**
	 * 设置当前登录用户，启动消息监听服务
	 * @param user
	 */
	public void setCurrentUser(Object user) {
		this.mCurrentUser = user;

		/*if (user != null) {
			JpushManager.getInstance(mContext).initJpush();
//			JpushManager.getInstance(mContext).setAlias("TESTAPP_HHYT00000000000000582570");
			JpushManager.getInstance(mContext).setAlias("ENT_PLAT_APP_"+user.userId);
		}else {
			JpushManager.getInstance(mContext).stopJpush();
		}*/

//		HDbManager.create(this, DATEBASENAME).replace(user);
//		测试svn检测本地修改
		//erce
	}
	public boolean isLogin() {
		return isLogin;
	}

	public void setIsLogin(boolean b) {
		isLogin = b;
	}

	public static Context getAppContext() {
		return MyApp.mContext;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static DisplayMetrics md = null;

	public static DisplayMetrics getDM(Context context) {
		if (md == null) {
			md = new DisplayMetrics();
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(md);
		}
		return md;
	}

	/**
	 * 检查网络状态
	 */
	public boolean CheckNetworkState(Context context) {

		ConnectivityManager manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mMobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo mWifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		// 如果3G、WIFI、2G等网络状态是连接的，则退出，否则显示提示信息进入网络设置界面
		if (mMobile != null && mMobile.isConnected()) {
			return true;
		}
		if (mWifi != null && mWifi.isConnected()) {
			return true;
		}

//		showTips(context);
//		DialogUtil.showTips(context);
//		DialogUtil.showTipsMy(context);

		return false;
	}

	/**
	 * 显示网络状态及设置
	 *
	 * @param context
	 */
	private void showTips(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("没有可用网络");
		builder.setMessage("当前网络不可用，是否设置网络？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 如果没有网络连接，则进入网络设置界面
				Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				// AccountManageActivity.this.finish();
			}
		});
		builder.create();
		builder.show();
	}
}
