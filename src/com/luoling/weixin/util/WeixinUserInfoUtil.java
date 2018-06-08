package com.luoling.weixin.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luoling.weixin.pojo.WeixinUserInfo;

public class WeixinUserInfoUtil {
	
	private static Logger log = LoggerFactory.getLogger(WeixinUserInfoUtil.class);
	 
	public static void saveWeixinUser(WeixinUserInfo weixinuser) {
		Connection con = null;
		PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql ="insert into luoling_weixinuserinfo(openId, subscribe, subscribeTime, nickname, country, province, city, language, headImgUrl) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
        	con = DBUtility.getConnection();
        	stmt = con.prepareStatement(sql); 
        	stmt.setString(1, weixinuser.getOpenId());
        	stmt.setInt(2, weixinuser.getSubscribe());
        	stmt.setString(3, weixinuser.getSubscribeTime());
        	stmt.setString(4, weixinuser.getNickname());
        	stmt.setString(5, weixinuser.getCountry());
        	stmt.setString(6, weixinuser.getProvince());
        	stmt.setString(7, weixinuser.getCity());
        	stmt.setString(8, weixinuser.getLanguage());
        	stmt.setString(9, weixinuser.getHeadImgUrl());
        	log.info(sql);
        	stmt.executeUpdate();
		} catch (SQLException ex) {
			log.info("WeixinUserInfoUtil#saveWeixinUser 数据库操作异常：" + ex.getMessage());
		} finally {
			DBUtility.closeConnection(con);
		}
	}
	
	public static boolean isExists(String openid) {
		log.info("com.luoling.weixin.util.WeixinUserInfoUtil#isExists  openid: " + openid);
		if(null == openid) {
			return false;
		}
		Connection con = null;
		PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from luoling_weixinuserinfo where openid = ?";
        boolean flag = false;
        try {
			con = DBUtility.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, openid);
			log.info(sql);
			rs = stmt.executeQuery();
			log.info("id: " + rs.getInt(1) 
					+ ", openId: " + rs.getString(2) 
					+ ", subscribe: " + rs.getInt(3)
					+ ", subscribeTime: " + rs.getString(4)
					+ ", nickname: " + rs.getString(5)
					+ ", country: " + rs.getString(6)
					+ ", province: " + rs.getString(7)
					+ ", city: " + rs.getString(8)
					+ ", language: " + rs.getString(9)
					+ ", headImgUrl: " + rs.getString(10));
			
			if(rs.next()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (SQLException ex) {
			log.info("WeixinUserInfoUtil#isExists 数据库操作异常：" + ex.getMessage());
			flag = false;
		} finally {
			DBUtility.closeConnection(con);
		}

        return flag;
	}
	
	public static void main(String[]args) {
		WeixinUserInfo user = new WeixinUserInfo();
		user.setOpenId("3423423");
		user.setSubscribe(1);
		user.setSubscribeTime("2018-6-7");
		user.setNickname("Rose");
		user.setCountry("huzhou");
		user.setProvince("zhejiang");
		user.setCity("changxing");
		user.setLanguage("zh");
		user.setHeadImgUrl("../11.png");
		saveWeixinUser(user);
		//System.out.println(isExists("3423423"));
	}
	
}
