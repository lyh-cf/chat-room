package com.dao;

import com.qqserver.ManageClientThreads;
import com.qqserver.ServerConnectClientThread;
import com.utils.BASE64;
import com.utils.JdbcUtils;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LookingForWord {
    //看有没有该邮箱
    public static boolean checkEmail(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User) data.getDate();
            String email=user.getEmail();
            String s1= "SELECT * FROM `users` WHERE `idx_qqemail`='"+email+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next())return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return false;
    }
    public static void change(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User) data.getDate();
            String email=user.getEmail();
            String password = BASE64.Encrypt(user.getPasswd());
            String s = "UPDATE `users` SET `idx_password`='" + password + "' WHERE `idx_qqemail`='" + email + "'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static Data checkPersonalInformation(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User) data.getDate();
            String email =user.getEmail();
            String s1= "SELECT * FROM `users` WHERE `idx_qqemail`='"+email+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            ManageClientThreads.addClientThread(user.getUserId(), ServerConnectClientThread.serverConnectClientThread);
            if(resultSet1.next()){
                user.setUserId(resultSet1.getString("pk_id"));
                user.setName(resultSet1.getString("idx_name"));
                user.setHeadimage(resultSet1.getString("idx_headimage"));
                user.setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
            }
            data.setDate(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
