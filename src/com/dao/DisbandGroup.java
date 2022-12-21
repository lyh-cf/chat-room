package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Group;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.RequestEnum;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DisbandGroup {
    public static void deleteGroup(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Group group=(Group)data.getDate();
            String  groupNumbers=group.getGroupNumber();
            String s = "DELETE FROM `group` WHERE `idx_group_id`='"+groupNumbers+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static void deleteGroupMembers(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Group group=(Group)data.getDate();
            String  groupNumbers=group.getGroupNumber();
            String s = "DELETE FROM `group_user` WHERE `idx_group_id`='"+groupNumbers+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static void deleteGroupMsg(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Group group=(Group)data.getDate();
            String  groupNumbers=group.getGroupNumber();
            String s = "DELETE FROM `group_msg` WHERE `idx_group_id`='"+groupNumbers+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static Data checkFriendList(Data data) {
        Data data1=new Data();
        data1.setMesType(RequestEnum.MESSAGE_DisbandGroup);
        Message message=new Message();
        Group group=(Group)data.getDate();
        message.setUser(group.getUser());
        data1.setDate(message);
        return DeleteFriend.checkFriendList(data1);
    }

}
