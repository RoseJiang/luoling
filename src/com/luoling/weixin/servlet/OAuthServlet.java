package com.luoling.weixin.servlet;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luoling.weixin.pojo.SNSUserInfo;
import com.luoling.weixin.pojo.Token;
import com.luoling.weixin.pojo.WeixinOauth2Token;
import com.luoling.weixin.pojo.WeixinUserInfo;
import com.luoling.weixin.thread.TokenThread;
import com.luoling.weixin.util.AdvancedUtil;
import com.luoling.weixin.util.CommonUtil;
import com.luoling.weixin.util.SNSUserInfoUtil;
import com.luoling.weixin.util.TokenUtil;
import com.luoling.weixin.util.WeixinUserInfoUtil;
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
		HttpSession session = request.getSession();
		String access_tokenValue = (String) session.getAttribute("access_token");

		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String state = request.getParameter("state");

		// 用户同意授权
		String accessToken = "";
		SNSUserInfo snsUserInfo = null;
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = TokenUtil.getToken(session, code);

			// 网页授权接口访问凭证
			accessToken = weixinOauth2Token.getAccessToken();
			log.debug("OAuthServlet#doGet accessToken: " + accessToken);
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			log.debug("OAuthServlet#doGet openId: " + openId);
			// 获取用户信息
			snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
			log.debug("OAuthServlet#doGet snsUserInfo: " + snsUserInfo);

			// 设置要传递的参数
			request.setAttribute("snsUserInfo", snsUserInfo);
			request.setAttribute("state", state);
		}
		
		log.info("OAuthServlet#doGet accessToken: " + accessToken);
		log.info("OAuthServlet#doGet openid: " + snsUserInfo.getOpenId());
		WeixinUserInfo user = CommonUtil.getUserInfo(accessToken, snsUserInfo.getOpenId());
		
		log.info(user.toString());
		if(!WeixinUserInfoUtil.isExists(user.getOpenId())) {
			user.setSubscribe(1);
			Date now = new Date();
		    DateFormat df = DateFormat.getDateInstance();
		    String s = df.format(now);
			user.setSubscribeTime(s);
			WeixinUserInfoUtil.saveWeixinUser(user);
		}
		
		log.info(snsUserInfo.toString());
		if(!SNSUserInfoUtil.isExists(snsUserInfo.getOpenId())) {
			SNSUserInfoUtil.saveSNSUser(snsUserInfo);
		}
		// 跳转到index.jsp
		//request.getRequestDispatcher("index.jsp").forward(request, response);
		log.info("OAuthServlet#doGet Subscribe: " + user.getSubscribe());
		if(1 == user.getSubscribe()) {
			request.getRequestDispatcher("member/membercenter.html").forward(request, response);
		} else {
			request.getRequestDispatcher("member/register.html").forward(request, response);
		}
		
	}
}
