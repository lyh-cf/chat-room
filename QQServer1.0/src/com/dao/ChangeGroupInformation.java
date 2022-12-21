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

public class ChangeGroupInformation {
    public static void changeGroupInformation(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection= JdbcUtils.getConnection();
            statement=connection.createStatement();
            Group group=(Group) data.getDate();
            String groupId = group.getGroupNumber();
            String groupName=group.getName();
            String groupProfile=group.getGroupProfile();
            String s = "UPDATE `group` SET `idx_group_name`='" + groupName + "',`idx_group_profile`='" + groupProfile + "' WHERE `idx_group_id`='" + groupId + "'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
}
