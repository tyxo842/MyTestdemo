package com.company.tyxo.bean;

import java.io.Serializable;

/**
 * Created by baoli on 2015/12/29.
 *
 * 预支付订单信息
 * 实现Serializable的原因是可以序列号存储与通过Intent传递
 */

public class PrePayList implements Serializable{

    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    //注意:由于package是Java的关键字,所以无法将其作为标识符,这里更改packageName,这一点在Json转换时要特别注意
    private String packageValue;

    private String sign;

    public PrePayList(String appid, String partnerid, String prepayid, String noncestr, String timestamp, String packageValue, String sign) {
        this.appid = appid;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.noncestr = noncestr;
        this.timestamp = timestamp;
        this.packageValue = packageValue;
        this.sign = sign;
    }

    public PrePayList() {
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "PrePayList{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", packageValue='" + packageValue + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
