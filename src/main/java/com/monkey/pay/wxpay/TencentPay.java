package com.monkey.pay.wxpay;

import com.github.wxpay.sdk.WXPay;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: demo
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-01-24 11:28
 **/
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/wxpay")
public class TencentPay {

    TenPayConfig tenPayConfig = new TenPayConfig();
    WXPay wxPay = new WXPay(tenPayConfig);
    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String date = dateFormat.format(now);


    @RequestMapping(value = "/")
    public Map<String, String> pay(Model model, HttpServletRequest request) throws Exception {

        String orderNo = date;//订单号
        System.out.println(date);
        int money = 100;//分
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("body", "测试接口试试效果");//自己定义
        sParaTemp.put("out_trade_no", orderNo);
        sParaTemp.put("total_fee", String.valueOf(money));
//        sParaTemp.put("spbill_create_ip", getIpAddress(request)); //
        sParaTemp.put("notify_url", "http://localhost:8080/wxnotify");//回调地址
        sParaTemp.put("trade_type", "NATIVE");//扫码
//        sParaTemp.put("openid", openid);
        System.out.println("统一下单参数：" + sParaTemp);
        sParaTemp.put("sign", "C380BEC2BFD727A4B6845133519F3AD6");
        Map<String, String> stringMap = wxPay.unifiedOrder(sParaTemp);

        System.out.println("统一下单结果：" + stringMap);
        if (stringMap.get("result_code").equals("FAIL")) {
            String msg = stringMap.get("err_code_des");
            throw new RuntimeException(msg);
        }
        model.addAttribute("paramMap", stringMap);
        System.out.println(stringMap);
        return stringMap;
    }
}
