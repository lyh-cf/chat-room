package com.dao;

import com.qqclient.pojo.User;
import com.utils.JdbcUtils;
import com.qqclient.pojo.Data;
import com.qqclient.pojo.Message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CheckFriendApplication {
    public static Data checkFriendApplication(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User) data.getDate();
            String userword =user.getUserId() ;
            String s1= "SELECT `users`.pk_id,`users`.idx_headimage,`users`.idx_name,`users`.idx_personalizedsignature\n" +
                    "FROM `users` INNER JOIN `friend_application`\n" +
                    "ON `friend_application`.idx_sender_id=`users`.pk_id WHERE `friend_application`.idx_getter_id='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            List<Message> applicationList=new ArrayList<>();
            while (resultSet1.next()){
                Message temp=new Message();
                temp.getFriend().setUserId(resultSet1.getString("pk_id"));
                temp.getFriend().setName(resultSet1.getString("idx_name"));
                temp.getFriend().setHeadimage(resultSet1.getString("idx_headimage"));
                temp.getFriend().setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
                temp.setFlag("1");
                applicationList.add(temp);
            }
            //想进群我只需要他的头像，账号，名字
            //String s2="SELECT idx_group_id FROM `group_user` WHERE `group_user`.idx_group_userid='"+userword+"' AND `group_user`.idx_group_state>0";
            String s2="SELECT `group_application`.idx_sender_id,`group_application`.idx_group_id\n" +
                    "FROM `group_application` INNER JOIN `group_user`\n" +
                    "ON `group_application`.idx_group_id=`group_user`.idx_group_id WHERE `group_user`.idx_group_userid='"+userword+"' AND `group_user`.idx_group_state>0";
            resultSet1 = statement.executeQuery(s2);
            List<String>groupID=new ArrayList<>();
            List<String>usersID=new ArrayList<>();
            while (resultSet1.next()){
                groupID.add(resultSet1.getString("idx_group_id"));
                usersID.add(resultSet1.getString("idx_sender_id"));
            }
            if(groupID.size()!=0) {
                List<User> groupMsg = checkGroupMsg(groupID);
                List<User> userMsg = checkUsrMsg(usersID);
                for (User item : userMsg) {
                    Message message = new Message();
                    message.setFriend(item);
                    message.setUser(groupMsg.get(0));//群相当于user,以群主，管理员的角度
                    groupMsg.remove(0);
                    message.setFlag("2");
                    applicationList.add(message);
                }
            }
            data.setapplicationList(applicationList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
    public static List<User> checkGroupMsg(List<String> groupID){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        List<User>groupMsg=new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            for(String item:groupID){
                String s1="SELECT * FROM `group` WHERE `idx_group_id`='"+item+"' ";;
                resultSet1=statement.executeQuery(s1);
                if(resultSet1.next()){
                    User user=new User();
                    user.setName(resultSet1.getString("idx_group_name"));
                    user.setUserId(resultSet1.getString("idx_group_id"));
                    user.setHeadimage(resultSet1.getString("idx_group_logo"));
                    user.setPersonalizedSignature(resultSet1.getString("idx_group_profile"));
                    groupMsg.add(user);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return groupMsg;
    }
    public static List<User> checkUsrMsg(List<String> userID){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        List<User>userMsg=new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            for(String item:userID){
                String s1="SELECT * FROM `users` WHERE `pk_id`='"+item+"' ";;
                resultSet1=statement.executeQuery(s1);
                if(resultSet1.next())
                {
                    User user=new User();
                    user.setName(resultSet1.getString("idx_name"));
                    user.setHeadimage(resultSet1.getString("idx_headimage"));
                    user.setUserId(resultSet1.getString("pk_id"));
                    userMsg.add(user);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return userMsg;
    }

}
