package com.dao;
import com.utils.BASE64;
import com.utils.JdbcUtils;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.User;
import com.qqserver.ManageClientThreads;
import com.qqserver.ServerConnectClientThread;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Login {
    public static List<String>friendOnlineId=new ArrayList<>();
    public static boolean checkLogin(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            User user=(User)data.getDate();
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String userword =user.getUserId() ;
            String password = BASE64.Encrypt(user.getPasswd());
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"' AND `idx_password`='"+password+"'";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next())return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return false;
    }
    public static boolean checkState(Data data){
        //User user=(User)data.getDate();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User)data.getDate();
            String userword =user.getUserId() ;
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"'";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                 user.setState(resultSet1.getInt("idx_state"));
            }
            data.setDate(user);
            if(user.getState()==0)return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return false;
    }
    public static void changeState(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User) data.getDate();
            String userword=user.getUserId();
            String s = "UPDATE `users` SET `idx_state`=1 WHERE `pk_id`='" + userword + "'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static Data Login(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User)data.getDate();
            ManageClientThreads.addClientThread(user.getUserId(), ServerConnectClientThread.serverConnectClientThread);
            String userword =user.getUserId() ;
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
                user.setName(resultSet1.getString("idx_name"));
                user.setHeadimage(resultSet1.getString("idx_headimage"));
                user.setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
                user.setState(resultSet1.getInt("idx_state"));
            }
            data.setDate(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
    public static Data friendList(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User)data.getDate();
            String userword =user.getUserId() ;
            String s1= "SELECT `users`.pk_id,`users`.idx_headimage,`users`.idx_name,`users`.idx_personalizedsignature,`users`.idx_state\n" +
                    "FROM `users` INNER JOIN `friend`\n" +
                    "ON `friend`.idx_userfriend_id=`users`.pk_id WHERE `friend`.idx_user_id='"+userword+"' ";
            List<User>friendList=new ArrayList<>();
            List<String>friendOnline=new ArrayList<>();
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            while (resultSet1.next()){
                User temp=new User();
                temp.setName(resultSet1.getString("idx_name"));
                temp.setHeadimage(resultSet1.getString("idx_headimage"));
                temp.setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
                temp.setUserId(resultSet1.getString("pk_id"));
                temp.setState(resultSet1.getInt("idx_state"));
                if(temp.getState()==1)friendOnline.add(temp.getUserId());
                temp.setFlag("1");
                friendList.add(temp);
            }
            friendOnlineId=friendOnline;
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
    public static Data commonPhrases(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User)data.getDate();
            String userword =user.getUserId() ;
            String s1= "SELECT * FROM `user_common_phrases` WHERE `idx_user_id`='"+userword+"' ";
            List<String>commonPhrases=new ArrayList<>();
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            while (resultSet1.next()){
               commonPhrases.add(resultSet1.getString("idx_common_phrases"));
            }
            data.setCommonPhrases(commonPhrases);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }

}
