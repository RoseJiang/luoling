package com.luoling.weixin.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luoling.weixin.pojo.Token;
import com.luoling.weixin.pojo.WeixinOauth2Token;
import com.luoling.weixin.thread.TokenThread;

/**
* 类名: TokenUtil </br>
* 描述: Token </br>
* 发布版本：V1.0  </br>
 */
public class TokenUtil {
	
	private static Logger log = LoggerFactory.getLogger(TokenUtil.class);
	 /**
	    * 方法名：getToken</br>
	    * 详述：从数据库里面获取token</br>
	    * @return
	    * @throws
	    
	    public static Map<String, Object> getToken(){
	        Connection con = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        String sql ="select * from luoling_token order by createTime desc limit 0,1";
	        String access_token="";
	        Map<String, Object> map=new HashMap<String, Object>();
	        Integer expires_in=0;
	        try {  
	            //创建数据库链接  
	            con = DBUtility.getConnection();  
	            //创建处理器  
	            stmt = con.prepareStatement(sql); 
	            //查询Token，读取1条记录  
	            rs = stmt.executeQuery();
	            if (rs.next()) {  
	                access_token = rs.getString("access_token"); 
	                expires_in=rs.getInt("expires_in");
	                map.put("access_token", access_token);
	                map.put("expires_in", expires_in);
	            }  
	           
	        } catch (SQLException ex) {  
	            System.out.println("数据库操作异常：" + ex.getMessage());  
	        } finally {  
	            DBUtility.closeConnection(con);
	        }
	        return map;
	    } */
	    public static WeixinOauth2Token getToken(HttpSession session, String code){ 
	    	WeixinOauth2Token weixinOauth2Token = (WeixinOauth2Token) session.getAttribute("access_token");
	    	String access_tokenValue = weixinOauth2Token == null? null : weixinOauth2Token.getAccessToken();
	    	
	    	if(null == access_tokenValue) {
				weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(TokenThread.appid,
						TokenThread.appsecret, code);
				TokenUtil.saveToken(new Token(weixinOauth2Token.getAccessToken(), 7200));
				session.setAttribute("access_token", weixinOauth2Token);
			} else {
				if(TokenUtil.isExpired(weixinOauth2Token.getAccessToken())) {
					weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(TokenThread.appid,
							TokenThread.appsecret, code);
					TokenUtil.updateToken("access_token", weixinOauth2Token.getAccessToken());
					session.setAttribute("access_token", weixinOauth2Token);
				}
				weixinOauth2Token = (WeixinOauth2Token) session.getAttribute("access_token");
			}
	    	
	    	return weixinOauth2Token;
	    }
	    
	    public static boolean isExpired(String previousCreateTime) {
	    	boolean flag = false;
	    	long previousTime = Long.valueOf(previousCreateTime).longValue();
	    	log.info("com.luoling.weixin.util.TokenUtil#isExpired previousTime: " + previousTime);
	    	long currentTime = System.currentTimeMillis();
	    	log.info("com.luoling.weixin.util.TokenUtil#isExpired currentTime: " + currentTime);
	    	long distance = (currentTime - previousTime)/1000;
	    	log.info("com.luoling.weixin.util.TokenUtil#isExpired distance: " + distance);
	    	if(distance > 7000) {
	    		flag = true;
	    	} else {
	    		flag = false;
	    	}
	    	return flag;
	    }
	    
	    public static Map<String, Object> getTokenDB() {
	    	Connection con = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	    	String sql = "select * from luoling_token where identifier='access_token'";
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String access_token= "";
	    	String createtime = "";
	    	try {
		    	//创建数据库链接  
	            con = DBUtility.getConnection();  
	            //创建处理器  
	            stmt = con.prepareStatement(sql); 
	            //查询Token，读取1条记录  
	            rs = stmt.executeQuery();
	            if (rs.next()) {
	            	access_token = rs.getString("identifiervalue"); 
	            	createtime = rs.getString("createTime");
	            	map.put("access_token", access_token);
	            	map.put("createtime", createtime);
	            }
	    	} catch (SQLException ex) {  
	            System.out.println("数据库操作异常：" + ex.getMessage());  
	        } finally {  
	            DBUtility.closeConnection(con);
	        }
	    	return map;
	    }
	    
	    /**
	    * 方法名：saveToken</br>
	    * 详述：保存token</br>
	    * @return
	    * @throws
	     */
	    public static void saveToken(Token token){
	        //存入数据库中
	        Connection conn = null;  
	        PreparedStatement pst = null;  
	        try {  
	            //创建数据库链接  
	            conn = DBUtility.getConnection(); 
	            //创建预处理器  
	            pst = conn.prepareStatement("insert into luoling_token(identifier, identifiervalue,expires_in,createTime)values(?, ?,?,?)");  
	             
	            pst.setString(1, "access_token");
	            pst.setString(2, token.getAccessToken()); 
	            pst.setInt(3, token.getExpiresIn());  
	            //long now = new Date().getTime();  
	            //pst.setTimestamp(3, new java.sql.Timestamp(now));  
	            pst.setString(4, System.currentTimeMillis() + "");
	            pst.execute();  
	        } catch (SQLException ex) {  
	            System.out.println("数据库操作异常：" + ex.getMessage());  
	        } finally {   
	            DBUtility.closeConnection(conn);
	        }
	    }
	    
	    public static void updateToken(String identifier, String access_token) {
	    	Connection conn = null;  
	        PreparedStatement pst = null;  
	        String sql = "update luoling_token set identifiervalue=? , createTime = ?  where identifier = ?";
	        try {  
	            //创建数据库链接  
	            conn = DBUtility.getConnection(); 
	            //创建预处理器  
	            pst = conn.prepareStatement(sql);
	            pst.setString(1, access_token);
	            pst.setString(2, System.currentTimeMillis() + "");
	            pst.setString(3, identifier);
	            pst.executeUpdate();
	        } catch (SQLException ex) {  
	            System.out.println("数据库操作异常：" + ex.getMessage());  
	        } finally {   
	            DBUtility.closeConnection(conn);
	        }
	    }
	    
	    
	    
	    public static void main(String []args) {
	    	Token token = new Token();
	    	token.setAccessToken("342423d4234");
	    	token.setExpiresIn(7200);
	    	//saveToken(token);
	    	//updateToken("access_token", "11111111111");
	    	Map<String, Object> map = getTokenDB();
	    	System.out.println(map.get("access_token"));
	    	System.out.println(map.get("createtime"));
	    	System.out.println(isExpired((String) map.get("createtime")));
	    }
}
