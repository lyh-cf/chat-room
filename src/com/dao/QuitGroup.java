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

public class QuitGroup {
    public static void deleteFriend(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Group group=(Group) data.getDate();
            String userId = group.getUser().getUserId();
            String groupNumber=group.getGroupNumber();
            String s = "DELETE FROM `group_user` WHERE `idx_group_id`='"+groupNumber+"' AND `idx_group_userid`='"+userId+"'";
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
        data1.setMesType(RequestEnum.MESSAGE_QuitGroup);
        Message message=new Message();
        Group group=(Group)data.getDate();
        message.setUser(group.getUser());
        data1.setDate(message);
        return DeleteFriend.checkFriendList(data1);
    }

}
