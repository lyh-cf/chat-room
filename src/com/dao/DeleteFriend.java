package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.RequestEnum;
import com.qqclient.pojo.User;
import com.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeleteFriend {
    public static void deleteFriend(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String user = message.getUser().getUserId();
            String friendID=message.getFriend().getUserId();
            String s = "DELETE FROM `friend` WHERE `idx_user_id`='"+user+"' AND `idx_userfriend_id`='"+friendID+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
            String s1 = "DELETE FROM `friend` WHERE `idx_user_id`='"+friendID+"' AND `idx_userfriend_id`='"+user+"'";
            int num1 = statement.executeUpdate(s1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static void deletePrivateMsg(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String user = message.getUser().getUserId();
            String s = "DELETE FROM `private_msg` WHERE `idx_sender_id`='"+user+"' OR `idx_getter_id`='"+user+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static Data checkFriendList(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userword =message.getUser().getUserId() ;
            String s1= "SELECT `users`.pk_id,`users`.idx_headimage,`users`.idx_name,`users`.idx_personalizedsignature,`users`.idx_state\n" +
                    "FROM `users` INNER JOIN `friend`\n" +
                    "ON `friend`.idx_userfriend_id=`users`.pk_id WHERE `friend`.idx_user_id='"+userword+"' ";
            List<User> friendList=new ArrayList<>();
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            while (resultSet1.next()){
                User temp=new User();
                temp.setName(resultSet1.getString("idx_name"));
                temp.setHeadimage(resultSet1.getString("idx_headimage"));
                temp.setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
                temp.setUserId(resultSet1.getString("pk_id"));
                temp.setState(resultSet1.getInt("idx_state"));
                //给user设置flag，表明是群聊还是私聊
                //给user设置flag，表明是群聊还是私聊
                //给user设置flag，表明是群聊还是私聊
                temp.setFlag("1");
                friendList.add(temp);
            }
            String s2="SELECT `group`.idx_group_name,`group`.idx_group_id,`group`.idx_group_logo,`group`.idx_group_profile\n" +
                    "FROM `group` INNER JOIN `group_user`\n" +
                    "ON `group`.idx_group_id=`group_user`.idx_group_id WHERE `group_user`.idx_group_userid='"+userword+"' ";
            resultSet1=statement.executeQuery(s2);
            while (resultSet1.next()){
                User temp=new User();
                temp.setName(resultSet1.getString("idx_group_name"));
                temp.setHeadimage(resultSet1.getString("idx_group_logo"));
                temp.setPersonalizedSignature(resultSet1.getString("idx_group_profile"));
                temp.setUserId(resultSet1.getString("idx_group_id"));
                //给user设置flag，表明是群聊还是私聊
                //给user设置flag，表明是群聊还是私聊
                //给user设置flag，表明是群聊还是私聊
                temp.setFlag("2");
                friendList.add(temp);
            }
            data.setFriendList(friendList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
    //若对方在线，更新对方列表
    public static Data passDeleteFriend(Data data) {
        Message message = new Message();
        Message temp = (Message) data.getDate();
        message.getUser().setUserId(temp.getFriend().getUserId());
        message.getFriend().setUserId(temp.getUser().getUserId());
        data.setDate(message);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String userword = message.getUser().getUserId();
            String s1 = "SELECT `idx_state` FROM `users` WHERE `pk_id`='" + userword + "' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if (resultSet1.next()) {
                if (resultSet1.getInt("idx_state") == 1) {
                    data =checkFriendList(data);
                    data.setMesType(RequestEnum.MESSAGE_PassDeleteFriend_SUCCEED);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
