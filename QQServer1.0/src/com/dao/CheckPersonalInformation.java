package com.dao;

import com.utils.JdbcUtils;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class CheckPersonalInformation {
    public static Data CheckPersonalMsg(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User)data.getDate();
            String userword =user.getUserId() ;
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                user.setBirthday(resultSet1.getString("idx_birthday"));
                user.setName(resultSet1.getString("idx_name"));
                user.setSex(resultSet1.getString("idx_sex"));
                user.setAge(resultSet1.getInt("idx_age"));
                user.setHeadimage(resultSet1.getString("idx_headimage"));
                user.setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
            }
            data.setDate(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
