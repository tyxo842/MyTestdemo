package com.company.tyxo.ui.fragment;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.tyxo.base.BaseActionBarCallback;
import com.company.tyxo.base.IBaseInitFragment;

public abstract class BaseFragment extends Fragment implements IBaseInitFragment, BaseActionBarCallback {

	/** 获取类名 */
	public String mPageName = this.getClass().getSimpleName();
	protected Activity mActivity;
	private View mView;

	public View getView() {
		return mView;
	}

	/**
	 * fragment传递的参数 如果fragment已经启动，再使用setArguments会报错 已启动。
	 */
	private Bundle args;

	public Bundle getArgs() {

		if(args == null){
			args = new Bundle();
			if(getArguments() != null)
			args.putAll(getArguments());
		}
		return args;
	}

	public void setArgs(Bundle args) {
		Log.i(mPageName,"Base  setArgs  args = "+args);
		this.args = args;
	}

	
	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);

//		Bundle bundle = getArgs();
//
//		if (bundle != null) {
//			bundle.putAll(args);
//		} else {
//			bundle = args;
//		}
//		HLog.i(mPageName,"setArguments  bundle = "+bundle);
//		setArgs(bundle);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mView = inflateView(inflater, container, savedInstanceState);
		mView.setClickable(true);
		initView(mView);
		initData();
		initListener();
		return mView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// 在Fragment中设置ActionBar的Action可见
		setHasOptionsMenu(isHasOptionsMenu());
		// fragment所挂载到的activity
		mActivity = this.getActivity();
	}

	@Override
	public void onResume() {
		super.onResume();
		// 如果本Activity是继承基类BaseActivity的，可注释掉此行。
		// StatService.trackBeginPage(getActivity(),
		// getClass().getCanonicalName());
	}

	@Override
	public void onPause() {
		super.onPause();
		// 如果本Activity是继承基类HHBaseActivity的，可注释掉此行。
		// StatService.trackEndPage(getActivity(),
		// getClass().getCanonicalName());
	}

	/**
	 * 是否显示ActionBar的菜单操作项
	 * 
	 * @return
	 */
	protected abstract boolean isHasOptionsMenu();

	@Override
	public void setActionBarTitleVisibility(int visibility) {
	}

	@Override
	public void setActionBarIconVisibility(int visibility) {
	}

	@Override
	public void setActionBarTitle(String title) {
	}

	@Override
	public void setActionBarTitle(int resid) {
	}

	@Override
	public void setActionBarIcon(Bitmap bitmap) {
	}

	@Override
	public void setActionBarIcon(int resid) {
	}
}
