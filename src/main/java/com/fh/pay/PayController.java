package com.fh.pay;

import com.fh.common.ServerResponse;
import com.fh.indent.service.order.OrderService;
import com.fh.sdk.MyConfig;
import com.fh.sdk.WXPay;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("pay")
public class PayController {

    @Autowired
    private OrderService orderService;


    @RequestMapping("createNative")
    @ResponseBody
    public ServerResponse createNative(String orderNo, BigDecimal totalprice){

        try {
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "飞狐购物中心");
            data.put("out_trade_no", orderNo);
            data.put("device_info", "WEB");
            data.put("fee_type", "CNY");
            data.put("total_fee", "1");
            data.put("spbill_create_ip", "192.168.13.41");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", "12");
            SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
            Date newDate = DateUtils.addMonths(new Date(), 2);
            //data.put("time_end", sim.format(newDate));


            Map<String, String> resp = wxpay.unifiedOrder(data);

            System.out.println(resp);


            if(!resp.get("return_code").equalsIgnoreCase("SUCCESS")){

                return ServerResponse.error("微信支付平台报错了"+resp.get("return_msg"));

            }
            if(!resp.get("result_code").equalsIgnoreCase("SUCCESS")){

                return ServerResponse.error("微信支付平台报错了"+resp.get("err_code_des"));

            }

            String code_url = resp.get("code_url");
            return ServerResponse.success(code_url);


        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(e.getMessage());
        }
    }

    @RequestMapping("queryOrderNo")
    @ResponseBody
    public ServerResponse queryOrderNo(String orderNo){
        try {
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", orderNo);

                int count = 0;
                for (;;){

                    Map<String, String> resp = wxpay.orderQuery(data);
                    System.out.println(resp);

                    if(!resp.get("return_code").equalsIgnoreCase("SUCCESS")){

                        return ServerResponse.error("微信支付平台报错了"+resp.get("return_msg"));

                    }
                    if(!resp.get("result_code").equalsIgnoreCase("SUCCESS")){

                        return ServerResponse.error("微信支付平台报错了"+resp.get("err_code_des"));

                    }
                    if(resp.get("trade_state").equalsIgnoreCase("SUCCESS")){
                        orderService.updateorderStatus(1,orderNo);
                        return ServerResponse.success();
                    }
                    count++;
                    System.out.println(count);
                    Thread.sleep(3000);
                    if(count>30){
                        return ServerResponse.error("支付超时");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            return ServerResponse.error(e.getMessage());
            }
    }

}
