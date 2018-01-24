package com.monkey.pay.alipay;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @program: demo
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-01-23 14:04
 **/
public class AliPayUtil {

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091300500614";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key =
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCOMlX9qH6a5SvKW2KmpgwLLsfyfsv9gsa38hsbyMxJiDpStFYbELga94ALVX2RYS0b414s/01k8K8dsuvFrBYUDN+IjsDNS+tb/RiE95lV9y2PI9o0koqjfUHUvTDRt1qM0TCiQyiSjKNzmkDrJ5/7SzGUDNdR0GSMvrmjRi8BkvRN0F/E08PEsUaJPUELYoBdL/8dfzDwVdU0gWL19RumPHC3McEEUcrbMP1eQA+61A1Ia5QEYbYEsNRqDP80JNo8VqAXujPyi/fCnP8jYbYsgzmNj8e/gqUU47vTlMreBwKR8sBMg9boQeEzZpEK24I/A5Ctzk2SkYt6FFeZ31RdAgMBAAECggEARe4PSA3jJX3qyTukClzCs4UwI9ujqmr0G2ZfWoynmsn6FM5HQkw4nlp1iB1lAWgO7RYmLG7puuh61lo1hSMaQIYMMHYz70KU3spbkpjJufMzXp+9IfmnYLuq8lqQdbJ8P6BpEIOCon+zqpiDsKHuFYFnQm4GPmAD8wug8+ECIP1OAj6tf/msArtJdD32XG/KCeNkbtfmQMG7apZoPnz3BoenEjV0BznXlz3Eh0/Ef1Ca1qaen2PXVnBl5aDiUaCOa8zKho3EKT3VNDJxeKuWXWhRt7ve74ATdVyEvejy5CG2QW5Rq1+5jB5ZWV6ErrxlDbM/KVGJdHZLpG8e/NxTaQKBgQDPdn3VEFjZRvvlBM7Z++jo3scYEJKogrWLIUbH++maZmlg8NFMTuTwpDabVGgsGpTNRLRmZHgrs36K+Y1oxp/ZnmnmeFH4mMXKt6l75sQYx+XMTk889Z6Zt8b6wQlZt3R2LyojxZSvU5fDJ/zfh2FOmcbp7O5Y4EWclwHJncdBzwKBgQCvdt/I4H2Nmnu61MU+3bw6oLpIdeEBympS57QDnAn3wvTerEWvuBhVVk9bqN+47qKE8vHFWZ0jvX3++3483Ae+CV3koXxioNjq7P5NP3d7CjwuQmaZaJkWZJg/7uY9Lfhv3JuAurX9d/m9x1vrhMV8f0BkwlzP78roXjaaqWnuEwKBgAUSPm0w4xsckxe8EFpfwAfBOliqPwuDdnCaNaK507IEgtrtyA/3db00JmmRnKJHXA/QeCBwoEdhMdyv0PcYs9ub9ba+hjJveZZE+w2eTHjWmhVubJ1u25uiVGjYwyPASkaBTKRnNgU7I+CPX60PIB/NvoEFW6YshUcYoeCNzMsdAoGBAITp22LjCrh1STyhaDjS5n7Sucmh4avllNzwP2BslvBjcfIrNS6CmYwrMToRlkcUHW/wSExiMbKdHBBGmb2oPwUpO0KxQPPFPAvZMll5thwyCJxSGD4lCbH4yYvEgAvgAs1Zo0HELY+vjjLADVU+4goIqtKomH26+01B8ML/igqZAoGBALnqe26RsceGigiYE/pC/30hV2pcLLrDxpmNK3zvnnIiQnrLYq9GbdVjyFNmFxhXPri7z47vme5WTlV6J40qDZMZmuvZokee55jJo55Qw8an4McoIdXB7KcQJiQsCrqoZkTeQfYXI6/Y4c6MiATSL/QCL4MQyyUf4IJ5ujDqfWAZ";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
    // 对应APPID下的支付宝公钥。
    public static String alipay_public_key =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3TeJJ62yWby8bDfvuvqgpmFz2rxCqgwXfWxyfwt6/Bjunv" +
                    "/3iIKm4FYaxHaLg7PV8YUSYH2e7PO03TWgtoMwFjaHuKofBShp37nIu+yc+Xn8BqvJJhJ" +
                    "/52iYrNwohhT827ltza1sqMdD6v1giPj8ploqNrQYLESe2" +
                    "/agYPCR8zV9kTZegcvUJC4KSo0HQE7tXzf4ZW6cDPtzliJCFZNds34Dmz4lsML" +
                    "+mYZzIAJdSvcYE2vfA31mayqCjIg6jQ1zmo3AoDJVx6P1h7FpYobnS5tGt1nsq1t/KKGv4M7dRtKs7Ub+VL1/xZuSGJSI" +
                    "/WWHSUM4afQaHRAhyJRxipdP/wIDAQAB";

    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.0.0.1:8080/alipay/accomplish";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1:8080/alipay/accomplish";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";

    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
