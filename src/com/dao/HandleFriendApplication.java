package com.dao;

import com.qqclient.pojo.RequestEnum;
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

import static com.dao.CheckFriendApplication.checkGroupMsg;
import static com.dao.CheckFriendApplication.checkUsrMsg;

public class HandleFriendApplication {
    public static void reduceApplication(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String user = message.getUser().getUserId();
            String friendID=message.getFriend().getUserId();
            String s = "DELETE FROM `friend_application` WHERE `idx_getter_id`='"+user+"' AND `idx_sender_id`='"+friendID+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static void reduceGroupApplication(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String user = message.getUser().getUserId();
            //message.getUser().getUserId()存的是群账号
            //message.getUser().getUserId()存的是群账号
            //message.getUser().getUserId()存的是群账号
            String friendID=message.getFriend().getUserId();
            String s = "DELETE FROM `group_application` WHERE `idx_group_id`='"+user+"' AND `idx_sender_id`='"+friendID+"'";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static void addFriend(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            String user = message.getUser().getUserId();
            String friendID=message.getFriend().getUserId();
            //String friendName=message.getFriend().getName();
            String s = "INSERT INTO `friend`(`pk_id`,`idx_user_id`,`idx_userfriend_id`) VALUES(NULL,'" + user + "','" + friendID + "')";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
            String user2 = message.getFriend().getUserId();
            String friendID2=message.getUser().getUserId();
            //String friendName2=message.getUser().getName();
            String s2 = "INSERT INTO `friend`(`pk_id`,`idx_user_id`,`idx_userfriend_id`) VALUES(NULL,'" + user2 + "','" + friendID2 + "')";
            int num2 = statement.executeUpdate(s2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static void addGroupUser(Data data) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            //message.getUser().getUserId()存的是群账号
            //message.getUser().getUserId()存的是群账号
            //message.getUser().getUserId()存的是群账号
            String groupId = message.getUser().getUserId();
            String senderID=message.getFriend().getUserId();
            String s = "INSERT INTO `group_user`(`pk_id`,idx_group_id,`idx_group_userid`,`idx_group_state`) VALUES(NULL,'" + groupId + "','" + senderID + "',0)";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num = statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet);
        }
    }
    public static Data checkFriendApplication(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            Message message=(Message) data.getDate();
            //message.getUser().getUserId()存的是群账号
            //message.getUser().getUserId()存的是群账号
            //message.getUser().getUserId()存的是群账号
            String userword=null;
            if(message.getFlag().equals("1")) userword =message.getUser().getUserId();
            else if(message.getFlag().equals("2"))userword=message.getTempUserWord();
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
                    Message temp = new Message();
                    temp.setFriend(item);
                    temp.setUser(groupMsg.get(0));//群相当于user,以群主，管理员的角度
                    groupMsg.remove(0);
                    temp.setFlag("2");
                    applicationList.add(temp);
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
    //若对方在线，更新列表
    public static Data agreeFriendApplication(Data data) {
        Message message=new Message();
        Message temp=(Message) data.getDate();
        message.getUser().setUserId(temp.getFriend().getUserId());
       // System.out.println(message.getUser().getUserId());
        message.getFriend().setUserId(temp.getUser().getUserId());
       // System.out.println(message.getFriend().getUserId());
        message.getFriend().setPersonalizedSignature(temp.getUser().getPersonalizedSignature());
        //System.out.println(message.getFriend().getPersonalizedSignature());
        message.getFriend().setHeadimage(temp.getUser().getHeadimage());
       // System.out.println(message.getFriend().getHeadimage());
        message.getFriend().setName(temp.getUser().getName());
       // System.out.println(message.getFriend().getName());
        data.setDate(message);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            String userword=message.getUser().getUserId();
            String s1= "SELECT `idx_state` FROM `users` WHERE `pk_id`='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            if(resultSet1.next()){
               if(resultSet1.getInt("idx_state")==1){
                   if("1".equals(temp.getFlag())) data.setMesType(RequestEnum.MESSAGE_PassFriendApplication_SUCCEED);
                   else if("2".equals(temp.getFlag()))data.setMesType(RequestEnum.MESSAGE_PassGroupApplication_SUCCEED);
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
