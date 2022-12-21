package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.RequestEnum;
import com.qqclient.pojo.User;
import com.utils.JdbcUtils;
import com.utils.SnowFlake;

import java.sql.*;

public class SendFriendMsg {
    public static void addMsg(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection= JdbcUtils.getConnection();
            statement=connection.createStatement();
            Message message=(Message) data.getDate();
            String userId = message.getUser().getUserId();
            String friendId=message.getFriend().getUserId();
            String sendContent=message.getMsg().getContent();
            int msgType=message.getMsg().getMsgType();
            String sendTime=message.getTime();
            String s="INSERT INTO `private_msg`(`pk_id`,`idx_sender_id`,`idx_getter_id`,`idx_content`,`idx_send_time`,`idx_msg_type`) VALUES(NULL ,'"+userId+"','"+friendId+"','"+sendContent+"','"+sendTime+"','"+msgType+"')";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
//            PreparedStatement preparedStatement = null;
//            preparedStatement.setObject(s,sendContent);
            int num=statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,statement,resultSet);
        }
    }
    public static Data sendMsg(Data data) {
        Message message=(Message) data.getDate();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String userword=message.getFriend().getUserId();
            String s1= "SELECT `idx_state` FROM `users` WHERE `pk_id`='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                if(resultSet1.getInt("idx_state")==1){
                    data.setMesType(RequestEnum.MESSAGE_SendOnlineFriend_SUCCEED);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
