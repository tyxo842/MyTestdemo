package com.company.tyxo.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.company.tyxo.R;
import com.company.tyxo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by LiYang on 2016/6/29 11: 24.
 * Mail      1577441454@qq.com
 * Describe :
 */
public class FragmentNormal extends BaseFragment implements View.OnClickListener{

    private LinearLayout ll_order_nodata_bg;
    private LinearLayout ll_order_nomal_data;
    private ProgressBar pb_order_list;

    private AdapterNormalMy mAdapter;
    private int dispatchTotalNum ; //底部总配送数量
    private Handler mHandler;
    private boolean isRefresh; //标识是否是下拉刷新操作
    private boolean isSearch; //标识是否是搜索
    private String orderId;
    private String orderDetailId;
    private int pageSize = 10;
    private int pageIndex = 1;
    private String searchContent;

    private TextView tv_or_stockup_total_num;
    private EditText searchView; // 搜索
    private TextView tv_order_normal_cancel;

    @Override
    protected boolean isHasOptionsMenu() {
        return false;
    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_normal, container, false);
    }

    @Override
    public void initView(View view) {
        ll_order_nodata_bg = (LinearLayout) view.findViewById(R.id.ll_order_nodata_bg);
        ll_order_nomal_data = (LinearLayout) view.findViewById(R.id.ll_order_nomal_data);
        pb_order_list = (ProgressBar) view.findViewById(R.id.pb_order_list);
        searchView = (EditText) view.findViewById(R.id.btn_barcode_search);
        tv_order_normal_cancel = (TextView) view.findViewById(R.id.tv_order_normal_cancel);
        tv_or_stockup_total_num = (TextView) view.findViewById(R.id.tv_or_stockup_total_num);
        Button btn_stockup_submit = (Button) view.findViewById(R.id.btn_stockup_submit); // 底部提交按钮
        btn_stockup_submit.setText("下一步");
        btn_stockup_submit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mHandler = new Handler();
        mAdapter = new AdapterNormalMy();

        if (getArgs()!=null){
            this.orderId = getArgs().getString("orderId");
            this.orderDetailId = getArgs().getString("orderDetailId");
        }

        pb_order_list.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.order_progressbar_color),
                android.graphics.PorterDuff.Mode.SRC_IN);

        // 如果订单号为空，显示未找到
        /*if (TextUtils.isEmpty(orderId.trim())||TextUtils.isEmpty(orderDetailId.trim())) {
            ll_order_nodata_bg.setVisibility(View.VISIBLE);
            ll_order_nomal_data.setVisibility(View.INVISIBLE);
        } else {
            onRefresh();
            // 检查网络,创建时第一次网络请求
            if(myApp.CheckNetworkState(getActivity())){
                onRefresh();
            }else {
                ll_order_nodata_bg.setVisibility(View.VISIBLE);
                ll_order_nomal_data.setVisibility(View.INVISIBLE);
            }
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void initListener() {
        tv_order_normal_cancel.setOnClickListener(this);
        searchView.setOnKeyListener(onKeyListener);
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tv_order_normal_cancel.setVisibility(View.VISIBLE);
                } else {
                    tv_order_normal_cancel.setVisibility(View.GONE);
                }
            }
        });

        // 无数据背景,点击事件
        ll_order_nodata_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_order_list.setVisibility(View.VISIBLE);
                ll_order_nodata_bg.setVisibility(View.GONE);
                onRefresh();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_order_normal_cancel:
                pageIndex = 0;
                searchView.setText("");
                searchContent = searchView.getText().toString();
                clearEditFocuse(searchView);
                onLoadMore();
                break;
            case R.id.btn_stockup_submit:   // 下一步
                // removeEnptyNumFromMap();
                clearEditFocuse(searchView);

                getListNextFromMap();

                if (dispatchTotalNum == 0) {
                    ToastUtil.showToastS(getActivity(),"配送条目不能为空");
                    return;
                }

                /*Bundle bun = new Bundle();
                bun.putString("orderId",orderId);
                bun.putString("orderDetailId",orderDetailId);
                bun.putSerializable("mInfoList", (Serializable) mInfoListNext);
                Intent intent = new Intent(getActivity(), ActivityNormalConfirm.class);
                intent.putExtras(bun);
                startActivity(intent);*/

                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
        }
    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                //隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputMethodManager.isActive()){
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                isSearch = true;
                onLoadMore();

                return true;
            }
            return false;
        }
    };

    /**
     * 特殊字符过滤，
     * @throws PatternSyntaxException 需要在TextWatcher的onTextChanged()中调用这个函数
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
//        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    private void clearEditFocuse(EditText et) {
        et.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//有则隐,无则显
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

    /**网络请求获取数据*/
    private void getInfoFromNet() {
        /*// 检查网络
        if(!myApp.getInstance().CheckNetworkState(getActivity())){
            stopRefreshing();
            return;
        }
        TaskHelp.OrderQueryNetQuest task = new TaskHelp.OrderQueryNetQuest();
        TaskHelp.OrderQueryNetQuest.OnRequestResultHandler handler = new TaskHelp.OrderQueryNetQuest.OnRequestResultHandler() {
            @Override
            public void onRequestResultSuccess(Object bean) {
                isShowNoDataBG(false);//不显示无数据背景
                StockupNormal stockupNormalBean = (StockupNormal) bean;
                List<StockupNormal.DataEntity> netInfoList = stockupNormalBean.getData();

                if (isRefresh){             // 刷新，添加到集合,填充list
                    mInfoList.clear();
                    mInfoMap.clear();
                    mInfoList.addAll(netInfoList);
                    mAdapter.setInfoList(mInfoList);
                }else {                     // 上拉加载
                    if (isSearch) {         // 搜索
                        setCountFromMapToInfoList(netInfoList);//将map内的count设置到list显示,
                        mAdapter.setInfoList(netInfoList);
                    }else {                 // 非搜索情况的上拉
                        mInfoList.addAll(netInfoList);
                        mAdapter.setInfoList(mInfoList);
                    }
                }
                putListToMap(netInfoList);
                mAdapter.notifyDataSetChanged();
                tv_or_stockup_total_num.setText(getTotalNum());
                stopRefreshing();
            }

            @Override
            public void onRequestResultFailure(String msg) {
                if (isRefresh){
                    stopRefreshing();
                    isShowNoDataBG(true);
                    tv_or_stockup_total_num.setText("0");
                }else {                 // 是加载更多,没有数据,不清空
                    stopRefreshing();
                    ToastUtil.showToastS(getActivity(), "没有更多数据");
                }
            }
        };
        task.getListInfoStockUpNormal(getActivity(),handler,orderId,orderDetailId,pageIndex,pageSize,searchContent);*/
    }

    // 将map内的count设置到list显示
    /*private void setCountFromMapToInfoList(List<StockupNormal.DataEntity> netInfoList) {
        for (int i = 0; i < netInfoList.size(); i++) {
            String key = netInfoList.get(i).getSTOCKID();
            int j = mInfoMap.get(key).getMyCount();
            netInfoList.get(i).setMyCount(j);
        }
    }*/

    private void onRefresh() {
        searchView.setText("");
        searchContent = searchView.getText().toString();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex = 1; //重置初始页为1，下拉刷新获取最新的10条
                isRefresh = true;
                getInfoFromNet();
            }
        }, 500);
    }

    private void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isSearch) {
                    isSearch = false;
                }else {
                    searchContent = searchView.getText().toString();
                    pageIndex = 0;
                }
                pageIndex += 1;
                isRefresh = false;
                getInfoFromNet();
            }
        }, 500);
    }

    public void stopRefreshing() {
        pb_order_list.setVisibility(View.GONE);
        /*if (mListView.isRefreshing()) {
            //解决取数据太快，刷新不停止的问题
            mListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mListView.onRefreshComplete();
                }
            }, 100);
        }*/
    }

    /** 显示查询结果为空提示 */
    private void isShowNoDataBG(boolean isNoData) {
        if (!isNoData) {
            ll_order_nodata_bg.setVisibility(View.GONE);
            ll_order_nomal_data.setVisibility(View.VISIBLE);
        } else if (isNoData) {
            ll_order_nodata_bg.setVisibility(View.VISIBLE);
            ll_order_nomal_data.setVisibility(View.INVISIBLE);
        }
    }

    /** adapter */
    private class AdapterNormalMy extends BaseAdapter {
        private LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        private List<String> mInfoListTecent = new ArrayList<>();

        public List<String> getInfoList() {
            return mInfoListTecent;
        }

        public void setInfoList(List<String> mInfoListTecent) {
            this.mInfoListTecent = mInfoListTecent;
        }

        @Override
        public int getCount() {
            return mInfoListTecent.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fragment_normal_item, null);
            }
            /**查找控件 */
            /*TextView tv_unbarcode_main_code = BaseViewHolder.get(convertView, R.id.tv_normal_main_code); // 主条码
            TextView tv_unbarcode_se_code = BaseViewHolder.get(convertView, R.id.tv_normal_se_code); // 次条码
            TextView tv_unbarcode_date = BaseViewHolder.get(convertView, R.id.tv_normal_date); // 失效日期
            TextView tv_unbarcode_sterilisatio_num = BaseViewHolder.get(convertView, R.id.tv_normal_sterilisatio_num); // 灭菌批号
            TextView tv_unbarcode_produce_num = BaseViewHolder.get(convertView, R.id.tv_normal_produce_num); //生产批号
            TextView tv_unbarcode_stock_num = BaseViewHolder.get(convertView, R.id.tv_normal_stock_num); //库存数量

            ImageButton imbtn_minus = BaseViewHolder.get(convertView, R.id.imbtn_minus); //减
            ImageButton imbtn_add = BaseViewHolder.get(convertView, R.id.imbtn_add); //加
            final TextView et_dipatch_num = BaseViewHolder.get(convertView, R.id.et_dipatch_total_num); //item中的配送数量

            *//**控件赋值*//*
            StockupNormal.DataEntity infoBean = mInfoListTecent.get(position);
            String temp = (String) Utility.isDataNull(infoBean.getEXPIRATIONDATE());
            // String oldDate = Utility.getStringTime(temp,7);//截去7个字符
            String oldDate = temp.replaceAll("/","-").substring(0,temp.indexOf(" "));
            String date = StringUtils.getDateFromate(oldDate);
            String stockNum = Utility.isDataEmpty((String)Utility.isDataNull(infoBean.getSTOCKCOUNT()));
            int stockNumb = Integer.valueOf(stockNum);
            final int stockNumbe = stockNumb;

            et_dipatch_num.setTag(R.id.et_dipatch_total_num,position);
            et_dipatch_num.setText(Utility.isDataEmpty(mInfoMap.get(infoBean.getSTOCKID()).getMyCount()+""));
            tv_unbarcode_main_code.setText((String) Utility.isDataNull(infoBean.getMAINBARCODE()));
            tv_unbarcode_se_code.setText((String) Utility.isDataNull(infoBean.getSUBBARCODE()));
            tv_unbarcode_produce_num.setText((String) Utility.isDataNull(infoBean.getPRODUCTIONBATCHNUMBER()));
            tv_unbarcode_sterilisatio_num.setText((String) Utility.isDataNull(infoBean.getSTERILIZATIONLOTNUMBER()));
            tv_unbarcode_stock_num.setText(stockNum);
            tv_unbarcode_date.setText(date);

            View.OnClickListener onClick = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.imbtn_minus :
                            int a1 = mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).getMyCount();
                            if (a1>0){
                                mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).setMyCount(a1-1);//配送数不能小于0
                                et_dipatch_num.setText(mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).getMyCount()+"");
                            }else {
                                mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).setMyCount(0);
                                et_dipatch_num.setText("0");
                                ToastUtil.showToastS("配送数量不能小于0");
                            }
                            tv_or_stockup_total_num.setText(getTotalNum());
                            break;
                        case R.id.imbtn_add :
                            int a2 = mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).getMyCount();
                            if (stockNumbe>0) {
                                if (a2 < stockNumbe) {  //小于库存
                                    mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).setMyCount(a2+1);
                                }else {
                                    mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).setMyCount(a2);
                                    ToastUtil.showToastS("配送数量不能大于库存");
                                }
                                HLog.i("lynet","mInfoMap.size() : "+mInfoMap.size());
                                et_dipatch_num.setText(""+mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).getMyCount());
                                tv_or_stockup_total_num.setText(getTotalNum());
                            }else {
                                ToastUtil.showToastS("配送数量不能大于库存");
                            }
                            break;
                        case R.id.et_dipatch_total_num:
                            CustomDialog.Builder builder = new CustomDialog.Builder(getActivity(), R.layout.dialog_et_num);
                            final EditText etDialog = builder.getEditText(R.layout.dialog_et_num,R.id.ems_num);
                            builder.setPositiveButton(new CustomDialog.EditTextListener() {
                                @Override
                                public void textContent(String billText, String remarkText) {}
                                @Override
                                public void textContent(String emsNumber) {
                                    if ("".equals(emsNumber)) {
                                        emsNumber = "0";
                                    }
                                    if (switchInputNum(emsNumber)) {//若超了Integer范围,valueOf会报错
                                        if (Integer.valueOf(emsNumber)>stockNumbe) {
                                            mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).setMyCount(stockNumbe);
                                            ToastUtil.showToastS("配送数量不能大于库存");
                                        } else if (Integer.valueOf(emsNumber)<0) {
                                            mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).setMyCount(0);
                                            ToastUtil.showToastS("配送数量不能小于0");
                                        }else {
                                            mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).setMyCount(Integer.valueOf(emsNumber));
                                        }
                                        tv_or_stockup_total_num.setText(getTotalNum());
                                    }
                                }
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    et_dipatch_num.setText(""+mInfoMap.get(mInfoListTecent.get(position).getSTOCKID()).getMyCount());
                                    dialog.dismiss();
                                    Utility.clearEditFocuse(getActivity(),etDialog);
                                }
                            });
                            final CustomDialog dialog = builder.createSave();
                            dialog.show();

                            etDialog.findFocus();
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(etDialog, 0); //显示软键盘
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); //显示软键盘

                            break;
                    }
                    clearEditFocuse(searchView);
                }
            };
            imbtn_add.setOnClickListener(onClick);
            imbtn_minus.setOnClickListener(onClick);
            et_dipatch_num.setOnClickListener(onClick);*/

            return convertView;
        }
    }

    // 控制输入数字范围  //若超了Integer范围,valueOf会报错
    private boolean switchInputNum(String num){
        //if (num.matches("[1-9]{1}[0-9]{0,9}")) {//正整数,首位非0
        if (num.matches("[0-9]{0,9}")) {
            return true;
        }else {
            ToastUtil.showToastS(getActivity(),"配送数量超出范围");
            return false;
        }
    }

    // 计算底部总数
    private String getTotalNum() {
        /*if (mInfoMap!=null && mInfoMap.size()>0) {
            Iterator it = mInfoMap.entrySet().iterator();
            int j = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                StockupNormal.DataEntity va = (StockupNormal.DataEntity) entry.getValue();
                j += va.getMyCount();
            }
            dispatchTotalNum = j;
            return String.valueOf(j);
        }
        dispatchTotalNum = 0;*/
        return "0";
    }

    // 将net返回数据,全部放入map
    private void putListToMap(List<String> list){
        /*if (list!=null&&list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                String keyStockId = list.get(i).getSTOCKID();
                mInfoMap.put(keyStockId, list.get(i));
            }
        }*/
    }

    // 从map中取出配送数大于0的
    private void getListNextFromMap() {
        /*if (mInfoMap!=null && mInfoMap.size()>0) {
            mInfoListNext.clear();
            Iterator it = mInfoMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                // String keyId = (String) entry.getKey();
                StockupNormal.DataEntity value = (StockupNormal.DataEntity) entry.getValue();
                if (value.getMyCount()>0) {
                    mInfoListNext.add(value);
                }
            }
        }*/
    }

    // 删除map中配送数不大于0 的条目 , 此方法可以删除
    private void removeEnptyNumFromMap(){
        /*if (mInfoMap!=null && mInfoMap.size()>0) {
            Iterator it = mInfoMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String keyId = (String) entry.getKey();
                StockupNormal.DataEntity value = (StockupNormal.DataEntity) entry.getValue();
                if (!(value.getMyCount() > 0)) {
                    //mInfoMap.remove(keyId); //异常:java.util.ConcurrentModificationException,在遍历中删除导致
                    it.remove();
                }
            }
        }*/
    }
}
