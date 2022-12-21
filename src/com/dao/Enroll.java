package com.dao;

import com.qqserver.ManageClientThreads;
import com.qqserver.ServerConnectClientThread;
import com.utils.BASE64;
import com.utils.FlieIO;
import com.utils.JdbcUtils;
import com.qqclient.pojo.User;
import com.utils.SnowFlake;
import com.qqclient.pojo.Data;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Enroll {
    public static boolean checkEnroll(Data data){
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                connection= JdbcUtils.getConnection();
                statement=connection.createStatement();
                User user=(User)data.getDate();
                String email=user.getEmail();
                String s="SELECT `pk_id` FROM `users` WHERE `idx_qqemail`='"+email+"'";
                resultSet = statement.executeQuery(s);
                if(resultSet.next()){
                    return false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                JdbcUtils.release(connection,statement,resultSet);
            }
            return true;
    }
    public static Data workID(Data data) throws IllegalAccessException {
        SnowFlake snowFlake= new SnowFlake(1,2);
        User user=(User)data.getDate();
        user.setUserId(String.valueOf(snowFlake.getNextID()));
        data.setDate(user);
        return data;
     }
    public static Data workImage(Data data){
        File file=new File("D:\\BaiduNetdiskDownload\\qqHeadImage\\默认头像.png");
        User user=(User) data.getDate();
        user.setImage(FlieIO.get(file));
        user.setHeadimage(FlieIO.setEnrollHeadImage(user.getUserId(),user.getImage()));
        data.setDate(user);
        return data;
    }
    public static Data adduser(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection= JdbcUtils.getConnection();
            statement=connection.createStatement();
            User user=(User) data.getDate();
            String userId = user.getUserId();
//
//            File file=new File("D:\\BaiduNetdiskDownload\\qqServerWareHouse\\默认头像.png");
//            byte[] temp=ChangeImage.get(file);
//            ChangeImage.set(user.getUserId(),temp);
//            user.setImage(temp);
//            data.setDate(user);


            //String userHeadImage="D:\\BaiduNetdiskDownload\\qqServerWareHouse\\"+userId+".png";
            //加密
            String password= BASE64.Encrypt(user.getPasswd());
            String names= user.getName();
            String email= user.getEmail();
            String s="INSERT INTO `users`(`pk_id`,`idx_name`,`idx_password`,`idx_qqemail`) VALUES('"+userId+"','"+names+"','"+password+"','"+email+"')";
            //executeUpdate 的返回值是一个整数（int），指示受影响的行数（即更新计数）
            int num=statement.executeUpdate(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection,statement,resultSet);
        }
        return data;
    }
    public static Data checkPersonalInformation(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            User user=(User)data.getDate();
            String userword =user.getUserId();
            String s1= "SELECT * FROM `users` WHERE `pk_id`='"+userword+"' ";
            //ResultSet 查询的结果集，封装了所有的查询结果
            resultSet1 = statement.executeQuery(s1);
            ManageClientThreads.addClientThread(user.getUserId(), ServerConnectClientThread.serverConnectClientThread);
            if(resultSet1.next()){
                user.setName(resultSet1.getString("idx_name"));
                user.setHeadimage(resultSet1.getString("idx_headimage"));
                user.setPersonalizedSignature(resultSet1.getString("idx_personalizedsignature"));
            }
            data.setDate(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.release(connection, statement, resultSet1);
        }
        return data;
    }
}
