package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.User;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonPhrases {
    public static boolean checkAgain(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userId = message.getUser().getUserId();
            String commonPhrases=message.getMsg().getContent();
            String s1= "SELECT * FROM `user_common_phrases` WHERE `idx_user_id`='"+userId+"' AND `idx_common_phrases`='"+commonPhrases+"'";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return true;
    }
    public static Data addCommonPhrases(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection= JdbcUtils.getConnection();
            statement=connection.createStatement();
            Message message=(Message) data.getDate();
            String userId = message.getUser().getUserId();
            String commonPhrases=message.getMsg().getContent();
            String s="INSERT INTO `user_common_phrases`(`pk_id`,`idx_user_id`,`idx_common_phrases`) VALUES(NULL,'"+userId+"','"+commonPhrases+"')";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num=statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,statement,resultSet);
        }
        return data;
    }
    public static void deleteCommonPhrases(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection= JdbcUtils.getConnection();
            statement=connection.createStatement();
            Message message=(Message) data.getDate();
            String userId = message.getUser().getUserId();
            String commonPhrases=message.getMsg().getContent();
            String s = "DELETE FROM `user_common_phrases` WHERE `idx_user_id`='"+userId+"' AND `idx_common_phrases`='"+commonPhrases+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num=statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,statement,resultSet);
        }
    }
}
