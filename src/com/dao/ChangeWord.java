package com.dao;

import com.utils.BASE64;
import com.utils.JdbcUtils;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeWord {
    public static boolean checkOldPassword(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User)data.getDate();
            String userword =user.getUserId() ;
            String password = BASE64.Encrypt(user.getOldPassWord());
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"' AND `idx_password`='"+password+"'";
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
    public static void changeWord(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User)data.getDate();
            String userword=user.getUserId();
            String password = BASE64.Encrypt(user.getPasswd());
            String s = "UPDATE `users` SET `idx_password`='" + password + "' WHERE `pk_id`='" + userword + "'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
}
