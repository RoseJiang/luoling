package com.luoling.weixin.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.junit.Test;

import com.luoling.weixin.pojo.Token;
import com.luoling.weixin.thread.TokenThread;
import com.luoling.weixin.util.CommonUtil;
import com.luoling.weixin.util.MyX509TrustManager;
import com.luoling.weixin.util.TokenUtil;

public class TokenTest {

	//通过微信服务器端
	@Test
	public void testGetToken1() throws Exception {
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + TokenThread.appid + "&secret=" + TokenThread.appsecret;
		// 建立连接
		URL url = new URL(tokenUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		httpUrlConn.setSSLSocketFactory(ssf);
		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);

		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod("GET");

		// 取得输入流
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		// 读取响应内容
		StringBuffer buffer = new StringBuffer();
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		httpUrlConn.disconnect();
		// 输出返回结果
		System.out.println(buffer);
	}

	//微信后台
	@Test
	public void testGetToken2() {
		Token token = CommonUtil.getToken("appID", "appsecret");
		System.out.println("access_token:" + token.getAccessToken());
		System.out.println("expires_in:" + token.getExpiresIn());
	}
	
	//数据库
	@Test
    public void testGetToken3() {
        /*Map<String, Object> token=TokenUtil.getToken();
        System.out.println(token.get("access_token"));
        System.out.println(token.get("expires_in"));*/
    }
    
    //发起GET请求获取凭证
    @Test
    public void testSaveToken4() {
        Token token=CommonUtil.getToken("appID", "appsecret");
        TokenUtil.saveToken(token);
    }
}
