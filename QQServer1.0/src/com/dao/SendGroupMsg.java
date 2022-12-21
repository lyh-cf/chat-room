package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.RequestEnum;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SendGroupMsg {
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
            String s="INSERT INTO `group_msg`(`pk_id`,`idx_sender_id`,`idx_group_id`,`idx_content`,`idx_send_time`,`idx_msg_type`) VALUES(NULL ,'"+userId+"','"+friendId+"','"+sendContent+"','"+sendTime+"','"+msgType+"')";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num=statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,statement,resultSet);
        }
    }
    public static List<String> sendMsg(Data data) {
        Message message=(Message) data.getDate();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        List<String> usersId=new ArrayList();
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String groupId=message.getFriend().getUserId();
            String sendId=message.getUser().getUserId();
            String s1= "SELECT `users`.idx_state,`users`.pk_id \n"+
                    " FROM `users` INNER JOIN `group_user`\n" +
                    " ON `group_user`.idx_group_userid=`users`.pk_id WHERE `group_user`.idx_group_id='"+groupId+"' AND `group_user`.idx_group_userid!='"+sendId+"'";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            while (resultSet1.next()){
                if(resultSet1.getInt("idx_state")==1){
                    usersId.add(resultSet1.getString("pk_id"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }  finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return usersId;
    }

}
