package com.dao;

import com.utils.JdbcUtils;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FriendApplication {
    public static boolean checkFriend(Data data){//看是否已经有,在用户加好友的时候
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userWord =message.getUser().getUserId() ;
            String friendUserWord=message.getFriend().getUserId();
            String s1= "SELECT * FROM `friend` WHERE `idx_user_id`='"+userWord+"' AND `idx_userfriend_id`='"+friendUserWord+"'";
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
    public static boolean checkGroup(Data data){//看是否已经有,在用户申请入群的时候
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userWord =message.getUser().getUserId() ;
            String friendUserWord=message.getFriend().getUserId();
            String s1= "SELECT * FROM `group_user` WHERE `idx_group_userid`='"+userWord+"' AND `idx_group_id`='"+friendUserWord+"'";
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

    public static boolean checkApplication(Data data){//看是否已经有，之前是否发过
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userWord =message.getUser().getUserId() ;
            String friendUserWord=message.getFriend().getUserId();
            String s1= "SELECT * FROM `friend_application` WHERE `idx_sender_id`='"+userWord+"' AND `idx_getter_id`='"+friendUserWord+"'";
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
    public static boolean checkGroupApplication(Data data){//看是否已经有，之前是否发过
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userWord =message.getUser().getUserId() ;
            String friendUserWord=message.getFriend().getUserId();
            String s1= "SELECT * FROM `group_application` WHERE `idx_sender_id`='"+userWord+"' AND `idx_group_id`='"+friendUserWord+"'";
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
    public static void addApplication(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String user = message.getUser().getUserId();
            String friendID=message.getFriend().getUserId();
            String s = "INSERT INTO `friend_application`(`pk_id`,`idx_sender_id`,`idx_getter_id`) VALUES(NULL,'" + user + "','" + friendID + "')";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static void addGroupApplication(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String user = message.getUser().getUserId();
            String friendID=message.getFriend().getUserId();
            String s = "INSERT INTO `group_application`(`pk_id`,`idx_sender_id`,`idx_group_id`) VALUES(NULL,'" + user + "','" + friendID + "')";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }

}
