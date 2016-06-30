package com.company.tyxo.base;

import android.graphics.Bitmap;

/**
 * 操作栏回调接口
 */
public interface BaseActionBarCallback {
 
	// 设置自定义actionbar 标题可见性
	void setActionBarTitleVisibility(int visibility);

	// 设置自定义actionbar 图标可见性
	void setActionBarIconVisibility(int visibility);

	// 设置自定义actionbar 标题
	void setActionBarTitle(String title);

	// 设置自定义actionbar 标题
	void setActionBarTitle(int resid);

	// 设置自定义actionbar 图标
	void setActionBarIcon(Bitmap bitmap);

	// 设置自定义actionbar 图标
	void setActionBarIcon(int resid);
}
