package com.company.tyxo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface IBaseInitFragment {

	// 填充视图
	View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	// 初始化视图
	void initView(View view);

	/** 设置View的监听 */
	void initListener();

	/** 设置View的数据,网络请求等 */
	void initData();
}
