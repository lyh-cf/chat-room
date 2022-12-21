package com.dao;

import com.qqclient.pojo.Data;
import com.qqclient.pojo.Group;
import com.utils.JdbcUtils;
import com.utils.SnowFlake;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateGroup {
    public static void addgroup(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection= JdbcUtils.getConnection();
            statement=connection.createStatement();
            Group group=(Group)data.getDate();
            String groupMasterId=group.getGroupMaster().getUserId();
            String createTime=group.getRegisterTime();
            String groupProfile=group.getGroupProfile();
            String groupName=group.getName();
            String groupNumber=group.getGroupNumber();
            String s="INSERT INTO `group`(`pk_id`,`idx_group_name`,`idx_group_id`,`idx_created_time`,`idx_group_profile`,`idx_master_id`) VALUES(NULL,'"+groupName+"','"+groupNumber+"','"+createTime+"','"+groupProfile+"','"+groupMasterId+"')";
            int num=statement.executeUpdate(s);
            String s2="INSERT INTO `group_user`(`pk_id`,`idx_group_id`,`idx_group_userid`,`idx_group_state`) VALUES(NULL,'"+groupNumber+"','"+groupMasterId+"',2)";
            int num2=statement.executeUpdate(s2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,statement,resultSet);
        }
    }
    public static Data workID(Data data) throws IllegalAccessException {
        SnowFlake snowFlake= new SnowFlake(1,2);
        Group group=(Group) data.getDate();
        group.setGroupNumber(String.valueOf(snowFlake.getNextID()));
        data.setDate(group);
        return data;
    }
}
