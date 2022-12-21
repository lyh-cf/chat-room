package com.dao;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Group;
import com.qqclient.pojo.User;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class SetGroupManage {
    public static void setGroupManage(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection= JdbcUtils.getConnection();
            statement=connection.createStatement();
            Group group=(Group) data.getDate();
            String groupId = group.getGroupNumber();
            String userId=group.getDisposePeople().getUserId();
            String s = "UPDATE `group_user` SET `idx_group_state`=1 WHERE `idx_group_id`='" + groupId + "' AND `idx_group_userid`='" + userId + "'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static boolean checkStatus(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Group group=(Group) data.getDate();
            String groupId = group.getGroupNumber();
            String userId=group.getDisposePeople().getUserId();
            String s1= "SELECT * FROM `group_user` WHERE `idx_group_id`='"+groupId+"' AND `idx_group_userid`='"+userId+"'";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                if(resultSet1.getInt("idx_group_state")!=0)return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return true;
    }
}
