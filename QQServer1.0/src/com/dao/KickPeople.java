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

public class KickPeople {
    public static boolean compareStatus(Data data){
         Group group=(Group) data.getDate();
         if(group.getUserStatus()>group.getDisposePeople().getUserStatus())return true;
         return false;
    }
    public static void deleteGroupMember(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Group group=(Group)data.getDate();
            String disposePeopleId =group.getDisposePeople().getUserId();
            String  groupNumbers=group.getGroupNumber();
            String s = "DELETE FROM `group_user` WHERE `idx_group_id`='"+groupNumbers+"' AND `idx_group_userid`='"+disposePeopleId+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static Data sendMsg(Data data) {
        Group temp=(Group) data.getDate();
        Message message=new Message();
        //被踢的那个人的账号
        message.setUser(temp.getDisposePeople());
        //设置群账号
        message.getFriend().setUserId(temp.getGroupNumber());
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String disposePeopleId=temp.getDisposePeople().getUserId();
            String s1= "SELECT `idx_state` FROM `users` WHERE `pk_id`='"+disposePeopleId+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                if(resultSet1.getInt("idx_state")==1){
                    data.setMesType(RequestEnum.MESSAGE_PassKickPeople);
                    data.setDate(message);
                    data=DeleteFriend.checkFriendList(data);
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
