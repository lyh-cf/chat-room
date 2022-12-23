package com.dao;

import com.utils.JdbcUtils;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckUserId {
    public static boolean checkUserId(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userword =message.getFriend().getUserId();
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"'";
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
    public static Data userMsg(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userword =message.getFriend().getUserId();
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                message.getFriend().setName(resultSet1.getString("idx_name"));
                message.getFriend().setHeadimage(resultSet1.getString("idx_headimage"));
                message.getFriend().setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
            }
            message.setFlag("1");
            data.setDate(message);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
    public static boolean checkGroupId(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userword =message.getFriend().getUserId();
            String s1= "SELECT * FROM `group` WHERE `idx_group_id`='"+userword+"'";
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
    public static Data groupMsg(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userword =message.getFriend().getUserId();
            String s1= "SELECT * FROM `group` WHERE `idx_group_id`='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                message.getFriend().setName(resultSet1.getString("idx_group_name"));
                message.getFriend().setHeadimage(resultSet1.getString("idx_group_logo"));
                message.getFriend().setPersonalizedSignature(resultSet1.getString("idx_group_profile"));
            }
            message.setFlag("2");
            data.setDate(message);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
