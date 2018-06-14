package com.luoling.weixin.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luoling.weixin.pojo.SNSUserInfo;


public class SNSUserInfoUtil {
	private static Logger log = LoggerFactory.getLogger(SNSUserInfoUtil.class);
	
	public static void saveSNSUser(SNSUserInfo snsuser) {
		Connection con = null;
		PreparedStatement stmt = null;
        List<String> privilegeList = snsuser.getPrivilegeList();
        String sql = "insert into luoling_snsuserinfo(openId, nickname, sex, country, province, city, headImgUrl, privilege) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
			con = DBUtility.getConnection();
			stmt = con.prepareStatement(sql);
			log.info(sql);
			stmt.setString(1, snsuser.getOpenId());
        	log.info("OpenId: " + snsuser.getOpenId());
        	
        	stmt.setString(2, snsuser.getNickname());
        	log.info("Nickname: " + snsuser.getNickname());
        	
        	stmt.setInt(3, snsuser.getSex());
        	log.info("Sex: " + snsuser.getSex());
        	
        	stmt.setString(4, snsuser.getCountry());
        	log.info("Country: " + snsuser.getCountry());
        	
        	stmt.setString(5, snsuser.getProvince());
        	log.info("Province: " + snsuser.getProvince());
        	
        	stmt.setString(6, snsuser.getCity());
        	log.info("City: " + snsuser.getCity());
        	
        	stmt.setString(7, snsuser.getHeadImgUrl());
        	
        	if(privilegeList.size() == 0) {
        		stmt.setString(8, "");
        		stmt.executeUpdate();
        	} else {
        		for(String str : privilegeList) {
    	        	stmt.setString(8, str);
    	        	stmt.executeUpdate();
    	        }
        	}
	        
		} catch (SQLException ex) {
			log.info("SNSUserInfoUtil#saveSNSUser 数据库操作异常：" + ex.getMessage());
		} finally {
			DBUtility.closeConnection(con);
		}
	}
	
	public static boolean isExists(String openid) {
		if(null == openid) {
			return false;
		}
		
		boolean flag = false;
		Connection con = null;
		PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from luoling_snsuserinfo where openid = ?";
        try {
			con = DBUtility.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, openid);
			rs = stmt.executeQuery();
			log.info(sql);
			
			if(rs.next()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (SQLException ex) {
			log.info("SNSUserInfoUtil#isExists 数据库操作异常：" + ex.getMessage());
		} finally {
			DBUtility.closeConnection(con);
		}			
        log.info("flag: " + flag);
        return flag;
	}
	
	public static void main(String []args) {
		List privilegeList = new ArrayList();
		privilegeList.add("a");
		privilegeList.add("b");
		privilegeList.add("c");
		SNSUserInfo snsuser = new SNSUserInfo();
		snsuser.setCity("hz");
		snsuser.setCountry("CN");
		snsuser.setHeadImgUrl("image/1,png");
		snsuser.setNickname("Rose");
		snsuser.setOpenId("sdfa787fsa8f8a8dsf87sa8f8af8sd7f8a7f8dsf");
		snsuser.setSex(1);
		snsuser.setProvince("zj");
		snsuser.setPrivilegeList(privilegeList);
		saveSNSUser(snsuser);
	}
}
