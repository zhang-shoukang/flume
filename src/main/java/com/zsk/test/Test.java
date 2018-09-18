package com.zsk.test;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Types;

/**
 * Create by zsk on 2018/9/17
 **/
public class Test {
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/test";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        PreparedStatement p = conn.prepareStatement("insert into student(id,name,sex) values (?,?,?)");
        int i=0;
        for (;i<10;i++) {
            p.setInt(1, i);
            p.setString(2, "jack"+i);
            p.setString(3, "male"+i);
            p.addBatch();
        }

        p.executeBatch();
        System.out.println(p);

        com.mysql.jdbc.PreparedStatement preparedStatement = (com.mysql.jdbc.PreparedStatement) p;
        Method getParseInfo = com.mysql.jdbc.PreparedStatement.class.getDeclaredMethod("getParseInfo");
        getParseInfo.setAccessible(true);
        Object parseInfo = getParseInfo.invoke(preparedStatement);
        Method getSqlForBatch = parseInfo.getClass().getDeclaredMethod("getSqlForBatch", int.class);
        getSqlForBatch.setAccessible(true);
        String batchSql = (String) getSqlForBatch.invoke(parseInfo, i);
        com.mysql.jdbc.PreparedStatement batchPs = null;
        batchPs = (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(batchSql);
        int j=0;
        for (; j < j + 3; j++) {
                batchPs.setNull(j + 1, Types.VARCHAR);
        }


    }
}
