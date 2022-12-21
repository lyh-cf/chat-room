package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.User;
import com.qqserver.ManageClientThreads;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignOut {
    public static void changeState(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User) data.getDate();
            String userword=user.getUserId();
            ManageClientThreads.removeServerConnectClientThread(userword);
            String s = "UPDATE `users` SET `idx_state`=0 WHERE `pk_id`='" + userword + "'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
}
