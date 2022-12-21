package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.User;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckFriendPersonalInformation {
    public static Data CheckFriendPersonalMsg(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userword =message.getFriend().getUserId() ;
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            User friend=new User();
            if(resultSet1.next()){
                friend.setBirthday(resultSet1.getString("idx_birthday"));
                friend.setName(resultSet1.getString("idx_name"));
                friend.setSex(resultSet1.getString("idx_sex"));
                friend.setAge(resultSet1.getInt("idx_age"));
                friend.setHeadimage(resultSet1.getString("idx_headimage"));
                friend.setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
            }
            friend.setUserId(userword);
            message.setFriend(friend);
            data.setDate(message);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
