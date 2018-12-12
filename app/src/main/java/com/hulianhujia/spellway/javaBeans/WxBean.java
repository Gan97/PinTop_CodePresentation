package com.hulianhujia.spellway.javaBeans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by FHP on 2017/8/22.
 */

public class WxBean {

    /**
     * appid : wx425716eb8696ec69
     * partnerid : 1487001872
     * prepayid : wx201708221529144af2b0523c0556622587
     * package : Sign=WXPay
     * noncestr : qjcke74678km5yvs18gfu65rbu30o61t
     * timestamp : 1503386957
     * sign : 2A3CE71B271E50D951301AA0D6A4BAED
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private int timestamp;
    private String sign;

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

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
