package com.luoling.weixin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luoling.weixin.pojo.SNSUserInfo;
import com.luoling.weixin.pojo.WeixinOauth2Token;
import com.luoling.weixin.thread.TokenThread;
import com.luoling.weixin.util.AdvancedUtil;
import com.luoling.weixin.util.WeixinUtil;

/**
 * 类名: OAuthServlet </br>
 * 描述: 授权后的回调请求处理 </br>
 * 发布版本：V1.0 </br>
 */
public class OAuthServlet extends HttpServlet {
	
	private static Logger log = LoggerFactory.getLogger(OAuthServlet.class);
	private static final long serialVersionUID = -1847238807216447030L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("com.luoling.weixin.servlet.OAuthServlet");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		

		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String state = request.getParameter("state");

		// 用户同意授权
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(TokenThread.appid,
					TokenThread.appsecret, code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			log.debug("accessToken: " + accessToken);
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			log.debug("openId: " + openId);
			// 获取用户信息
			SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
			log.debug("snsUserInfo: " + snsUserInfo);

			// 设置要传递的参数
			request.setAttribute("snsUserInfo", snsUserInfo);
			request.setAttribute("state", state);
		}
		// 跳转到index.jsp
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
