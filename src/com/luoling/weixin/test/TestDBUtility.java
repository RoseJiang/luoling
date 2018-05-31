package com.luoling.weixin.test;
import java.sql.SQLException;

import org.junit.Test;

import com.luoling.weixin.util.DBUtility;
public class TestDBUtility {
    /**
    * 方法名：testgetConnection</br>
    * 详述：测试是否连接</br>
    * @throws SQLException
    * @throws
     */
    @Test
    public void testgetConnection() throws SQLException {
        DBUtility db = new DBUtility();
        System.out.println(db.getConnection());
    }
}
