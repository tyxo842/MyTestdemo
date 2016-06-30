package com.company.tyxo.util;

import android.content.Context;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import com.company.tyxo.bean.PrePayList;

/**
 * Created by baoli on 2015/12/29.
 */
public class WXPayUtil {

    private static WXPayUtil wxPayUtil=null;
    private Context mContext;
    private IWXAPI wxapi;

    private WXPayUtil() {

    }

    /**
     * 单例模式获取WXPayUtil对象
     * @param context
     * @return
     */
    public WXPayUtil getInstance(Context context) {
        this.mContext=context;
        if (wxPayUtil == null) {
            wxPayUtil=new WXPayUtil();
        }

        return wxPayUtil;
    }

    /**
     * 将应用注册到微信支付
     * @param APP_ID
     */
    public void registWXPay(final String APP_ID) {
        wxapi = WXAPIFactory.createWXAPI(mContext, APP_ID);
    }


    public void pay(PrePayList prePayList) {
        PayReq req = new PayReq();
        req.appId			= prePayList.getAppid();
        req.partnerId		= prePayList.getPartnerid();
        req.prepayId		= prePayList.getPrepayid();
        req.nonceStr		= prePayList.getNoncestr();
        req.timeStamp		= prePayList.getTimestamp();
        req.packageValue	= prePayList.getPackageValue();
        req.sign			= prePayList.getSign();
        req.extData			= "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        wxapi.sendReq(req);
    }
}
