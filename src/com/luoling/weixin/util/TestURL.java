package com.luoling.weixin.util;

public class TestURL {
    /**     
     * 方法名：main</br>
     * 详述：生成URL编码 </br>
     * @param args 说明返回值含义
     * @throws 说明发生此异常的条件
     */
    public static void main(String[] args) {
        String source="http://chiyan.duapp.com/oauthServlet";
        System.out.println(CommonUtil.urlEncodeUTF8(source));
    }
}
