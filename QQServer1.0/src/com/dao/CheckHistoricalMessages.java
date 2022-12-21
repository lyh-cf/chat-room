package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;
import com.utils.FlieIO;
import com.utils.JdbcUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CheckHistoricalMessages {
    public static Data CheckFriendHistoricalMessages(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String userword =message.getUser().getUserId();
            String friendId=message.getFriend().getUserId();
            String s1= "SELECT `users`.pk_id,`users`.idx_name,`users`.idx_headimage,`private_msg`.idx_content,`private_msg`.idx_send_time,`private_msg`.idx_msg_type\n"+
                    " FROM `users` INNER JOIN `private_msg`\n" +
                    " ON `private_msg`.idx_sender_id=`users`.pk_id WHERE (`private_msg`.idx_sender_id='"+userword+"' AND `private_msg`.idx_getter_id='"+friendId+"') OR (`private_msg`.idx_sender_id='"+friendId+"' AND `private_msg`.idx_getter_id='"+userword+"')";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            List<Message> privateMessage=new ArrayList<>();
            while (resultSet1.next()){
                Message temp=new Message();
                temp.getUser().setUserId(resultSet1.getString("pk_id"));
                temp.getUser().setHeadimage(resultSet1.getString("idx_headimage"));
                temp.getUser().setName(resultSet1.getString("idx_name"));
                temp.setTime(resultSet1.getString("idx_send_time"));
                temp.getMsg().setContent(resultSet1.getString("idx_content"));
                temp.getMsg().setMsgType(resultSet1.getInt("idx_msg_type"));
                if(temp.getMsg().getMsgType()==1)temp.getMsg().setImage(FlieIO.get(new File("D:\\BaiduNetdiskDownload\\qqServerWareHouse\\"+temp.getMsg().getContent())));
                privateMessage.add(temp);
            }
            data.setMessage(privateMessage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
    public static Data CheckGroupHistoricalMessages(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String friendId=message.getFriend().getUserId();
            String s1= "SELECT `users`.pk_id,`users`.idx_name,`users`.idx_headimage,`group_msg`.idx_content,`group_msg`.idx_send_time,`group_msg`.idx_msg_type\n"+
                    " FROM `users` INNER JOIN `group_msg`\n" +
                    " ON `group_msg`.idx_sender_id=`users`.pk_id WHERE `group_msg`.idx_group_id='"+friendId+"'";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            List<Message> groupMessage=new ArrayList<>();
            while (resultSet1.next()){
                Message temp=new Message();
                temp.getUser().setUserId(resultSet1.getString("pk_id"));
                temp.getUser().setHeadimage(resultSet1.getString("idx_headimage"));
                temp.getUser().setName(resultSet1.getString("idx_name"));
                temp.setTime(resultSet1.getString("idx_send_time"));
                temp.getMsg().setContent(resultSet1.getString("idx_content"));
                temp.getMsg().setMsgType(resultSet1.getInt("idx_msg_type"));
                if(temp.getMsg().getMsgType()==1)temp.getMsg().setImage(FlieIO.get(new File("D:\\BaiduNetdiskDownload\\qqServerWareHouse\\"+temp.getMsg().getContent())));
                groupMessage.add(temp);
            }
            data.setMessage(groupMessage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
