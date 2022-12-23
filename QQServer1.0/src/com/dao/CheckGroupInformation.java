package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Group;
import com.qqclient.pojo.Message;
import com.qqclient.pojo.User;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CheckGroupInformation {
    public static Data checkGroupInformation(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Group group=(Group) data.getDate();
            String groupNumber=group.getGroupNumber();
            String s1="SELECT * FROM `group` WHERE `idx_group_id`='"+groupNumber+"' ";;
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                group.setName(resultSet1.getString("idx_group_name"));
                group.setRegisterTime(resultSet1.getString("idx_created_time"));
                group.setGroupProfile(resultSet1.getString("idx_group_profile"));
                group.setGroupLogo(resultSet1.getString("idx_group_logo"));
            }
            data.setDate(group);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
    public static Data checkGroupMembers(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Group group=(Group) data.getDate();
            String userWord =group.getUser().getUserId();
            String groupNumber=group.getGroupNumber();
            String s1="SELECT `users`.pk_id,`users`.idx_headimage,`users`.idx_name,`group_user`.idx_group_state\n" +
                    "FROM `users` INNER JOIN `group_user`\n" +
                    "ON `group_user`.idx_group_userid=`users`.pk_id WHERE `group_user`.idx_group_id='"+groupNumber+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
             List<User> groupMembers=new ArrayList<>();//普通的群成员
             List<User>groupManage=new ArrayList<>();//群管理
           // System.out.println(userWord);
            while (resultSet1.next()){
                User user=new User();
                user.setUserId(resultSet1.getString("pk_id"));
                user.setHeadimage(resultSet1.getString("idx_headimage"));
                user.setName(resultSet1.getString("idx_name"));
                user.setUserStatus(resultSet1.getInt("idx_group_state"));
                if(user.getUserId().equals(userWord)){
                    group.setUserStatus(user.getUserStatus());
                    System.out.println(user.getName()+"在该群的权利"+group.getUserStatus());
                }
                if(user.getUserStatus()==2)group.setGroupMaster(user);
                else if(user.getUserStatus()==1)groupManage.add(user);
                else if(user.getUserStatus()==0)groupMembers.add(user);
            }
            group.setGroupMembers(groupMembers);
            group.setGroupManage(groupManage);
            data.setDate(group);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
