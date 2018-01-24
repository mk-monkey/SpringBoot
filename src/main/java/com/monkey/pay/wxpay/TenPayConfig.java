package com.monkey.pay.wxpay;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * @program: demo
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-01-24 11:05
 **/
public class TenPayConfig implements WXPayConfig {
    private String APP_ID = "wx24d4b824d48b70b1";
    private String SECRET = "9345ec385c153f067ed26d1c394ceafb";

    private String MCH_ID = "1420707402";
    private String KEY = "1f90d4361508750761f9cee0a097a3d9";//商户appKey


    public String getSecret() {
        return SECRET;
    }

    @Override
    public String getAppID() {
        return APP_ID;
    }

    @Override
    public String getMchID() {
        return MCH_ID;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 1000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
