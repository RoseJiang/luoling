package com.luoling.weixin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;
public class SMSUtil {
	
	private static Logger log = LoggerFactory.getLogger(SMSUtil.class);
	
	public static void SmsSingleSender(int appid, String appkey, String[] phoneNumbers) {
		try {
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],
		        "【腾讯云】您的验证码是: 5678", "", "");
		    System.out.print(result);
		} catch (HTTPException e) {
		    // HTTP响应码错误
		    e.printStackTrace();
		} catch (JSONException e) {
		    // json解析错误
		    e.printStackTrace();
		} catch (IOException e) {
		    // 网络IO错误
		    e.printStackTrace();
		}
	}
	
	
	public static void main(String[]args) {
		int appid = 1400100587; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "11305d59a070d9ed8f5b76e672e895d2";

		// 需要发送短信的手机号码
		String[] phoneNumbers = {"17772702679"};

		// 短信模板ID，需要在短信应用中申请
		int templateId = 134046; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

		// 签名
		String smsSign = "腾讯云"; 
		
		SmsSingleSender(appid, appkey, phoneNumbers);
	}
}
