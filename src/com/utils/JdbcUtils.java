package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    private static String driver = null;//黄块代表没用上，不会报错
    private static String url = null;
    private static String username = null;
    private static String password = null;
    static{
        //反射的知识
        InputStream inputStream=JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
        //读取Properties配置文件
        Properties properties=new Properties();
        try {
            properties.load(inputStream);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            //驱动只加载一次
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接
     */
    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url, username, password);
    }
    /**
     * 释放资源
     */
    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        if(connection!=null){
            try {
               connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(statement!=null){
            try {
               statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
