package com.dao;

import com.utils.JdbcUtils;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeHeadImage {
    public static void ChangeHeadImage(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection= JdbcUtils.getConnection();
            statement=connection.createStatement();
            User user=(User)data.getDate();
            String userId = user.getUserId();
            String headimage=user.getHeadimage();
            String s = "UPDATE `users` SET `idx_headimage`='" + headimage + "' WHERE `pk_id`='" + userId + "'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
}
