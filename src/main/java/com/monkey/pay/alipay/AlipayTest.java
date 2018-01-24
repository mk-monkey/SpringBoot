package com.monkey.pay.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: demo
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-01-22 16:12
 **/
@RestController
@EnableAutoConfiguration
@RequestMapping("/alipay")
public class AlipayTest {
    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String date = dateFormat.format(now);
    String qian = "500";
    String dingdan = "没有订单名称";


    @RequestMapping(value = "/")
    public String alipay() throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayUtil.gatewayUrl, AliPayUtil.app_id,
                AliPayUtil.merchant_private_key, "json", AliPayUtil.charset, AliPayUtil.alipay_public_key,
                AliPayUtil.sign_type);

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(AliPayUtil.return_url);
        request.setNotifyUrl(AliPayUtil.notify_url);

        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

       /* //产品描述
        model.setBody("我就是我");
        //产品标题
        model.setSubject("App alipay java Demo");
        //订单编号
        model.setOutTradeNo(dateFormat.format(now));
        //超时关闭时间
        model.setTimeoutExpress("300m");
        //产品金额
        model.setTotalAmount("50");
        //销售产品码,商家和支付宝签约的产品码,为固定值;
        model.setProductCode("QUICK_MSECURITY_PAY");

        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println("支付成功");*/

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = date;
        //付款金额，必填
        String total_amount = qian;
        //订单名称，必填
        String subject = dingdan;
        //商品描述，可空
        String body = "这是个骗子卖给你东西呢你要不要知道啊";
        request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
                + "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//        System.out.println(request);
        String result = alipayClient.pageExecute(request).getBody();
//        String result = "测试成功后";

        return result;
    }


    @RequestMapping("/accomplish")
    protected String doGet(HttpServletRequest request, HttpServletResponse resp) throws AlipayApiException {
        System.out.println("进来了");
        Map<String, String> param = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        //获取支付宝GET过来反馈信息
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            param.put(name, valueStr);
        }
        System.out.println(param);
        AlipaySignature alipaySignature = new AlipaySignature();
        boolean signVerified = alipaySignature.rsaCheckV1(param, AliPayUtil.alipay_public_key, AliPayUtil.charset,
                AliPayUtil.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            //支付宝交易号
            String trade_no = request.getParameter("trade_no");

            //付款金额
            String total_amount = request.getParameter("total_amount");

            return "trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" +
                    total_amount;
        } else {
            return "验签失败";
        }
    }

    /*
    @RequestMapping("/accomplish")
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进来了来来来");
        String alp_public_key = "";//支付宝公钥
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        if (null != requestParams && requestParams.size() > 0) {
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            System.out.println("【支付宝回调】 开始  参数：" + params);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            String out_trade_no = request.getParameter("out_trade_no");//商户订单号
            String trade_no = request.getParameter("trade_no");//支付宝交易号
            String trade_status = request.getParameter("trade_status"); //交易状态
            String seller_id = request.getParameter("seller_id");//卖家支付宝账户号
            String total_fee = request.getParameter("total_fee");//交易金额
            String buyer_email = request.getParameter("buyer_email");//买家支付宝账号
            boolean checgSign = false;
            try {
                checgSign = AlipaySignature.rsaCheckV1(params, alp_public_key, "utf-8", "RSA2");
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            if (checgSign) {//验证成功
                System.out.println("【支付宝验证】  成功");
                *//**
     * TRADE_FINISHED 交易完成 TRADE_SUCCESS 支付成功 WAIT_BUYER_PAY 交易创建 TRADE_CLOSED 交易关闭
     **//*

                if (trade_status == null || trade_status.equals("TRADE_FINISHED") || trade_status.equals
                        ("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序
                }
            } else {
                System.err.println("【支付宝验证】  失败");
            }
        }
        // return "success";//fail  返回给支付宝  json
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write("success");//直接将完整的表单html输出到页面
        resp.getWriter().flush();
        resp.getWriter().close();
    }
*/
}
