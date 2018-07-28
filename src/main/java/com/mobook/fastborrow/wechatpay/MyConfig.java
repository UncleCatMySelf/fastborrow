package com.mobook.fastborrow.wechatpay;

import com.mobook.fastborrow.constant.AppConstant;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:30 2018\7\28 0028
 */
public class MyConfig  extends WXPayConfig{
    private byte[] certData;

    public MyConfig() throws Exception{
        String certPath = "/developer/pay/apiclient_cert.p12";
        //String certPath = "F:\\公司\\支付证书\\apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return AppConstant.APPID;
    }

    @Override
    public String getMchID() {
        return AppConstant.MERCHANT_NUMBER;
    }

    @Override
    public String getKey() {
        return AppConstant.APPKEY;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        MyDomain myDomain = new MyDomain();
        return myDomain;
    }
}
